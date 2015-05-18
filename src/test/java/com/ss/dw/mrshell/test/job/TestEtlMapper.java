package com.ss.dw.mrshell.test.job;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ss.dw.mrshell.annotation.AMapper.AConfig;
import com.ss.dw.mrshell.annotation.AMapper.AInput;
import com.ss.dw.mrshell.mapred.MRFlowWriter;
import com.ss.dw.mrshell.mapred.ShellMapper;
import com.ss.dw.mrshell.test.log.AppLog;
import com.ss.dw.mrshell.test.log.ChannelLog;
import com.ss.dw.mrshell.test.log.UserIdLog;

public class TestEtlMapper extends ShellMapper {
	
	private Map<String, String> channels = new HashMap<String, String>();
	
	@AConfig(name="conf-app-channels")
	public void config(ChannelLog log, Context context)
	{
		if (log.isValid()) {
			channels.put(log.getName(), log.getNormalizedName());
		}
	}
	
	@AInput(name="input-user-ids")
	public void map(Object key, UserIdLog log, Context context) throws IOException, InterruptedException
	{
		MRFlowWriter writer = getGroupWriter("uids");
		
		writer.writeValue("userGuessId", log.getUserId());
		writer.writeKey("deviceId", log.getDeviceId());
		writer.flush(context);
	}

	@AInput(name="input-app-log")
	public void map(Object key, AppLog log, Context context) throws IOException, InterruptedException
	{
		MRFlowWriter writer = getGroupWriter("logs");
		
		//normalize app channel
		String appChannel = log.getAppChannel();
		if (StringUtils.isNotBlank(appChannel)) {
			String normChannel = channels.get(appChannel);
			if (normChannel != null) {
				appChannel = normChannel;
			}
		}

		writer.writeValue("logVersion", log.getLogVersion());
		writer.writeValue("logTimestamp", log.getLogTimestamp());
		writer.writeValue("logType", log.getLogType());
		writer.writeValue("userLogId", log.getUserId());
		writer.writeValue("appChannel", appChannel);
		writer.writeKey("deviceId", log.getClientDeviceId());
		writer.flush(context);
	}
}
