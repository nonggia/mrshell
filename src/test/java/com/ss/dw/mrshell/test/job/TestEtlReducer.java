package com.ss.dw.mrshell.test.job;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ss.dw.mrshell.log.GroupNameLog;
import com.ss.dw.mrshell.log.MRNameLog;
import com.ss.dw.mrshell.mapred.ShellReducer;

public class TestEtlReducer extends ShellReducer {
	
	public void process(MRNameLog key, GroupNameLog groupValues, Context context) throws IOException, InterruptedException
	{
		Integer uidGuess = null;
		//get device and userid map 
		if (groupValues.hasGroup("uids")) {
			uidGuess = groupValues.group("uids").get(0).getInt("userGuessId");
		}
		
		//get all logs of the current deviceId
		List<MRNameLog> appLogs = groupValues.group("logs");
		for (MRNameLog appLog : appLogs) {
			String value = StringUtils.join(new String[] {
					appLog.getString("logVersion"),
					appLog.getString("logTimestamp"),
					appLog.getString("logType"),
					appLog.getString("userId") == null ? String.valueOf(uidGuess) : appLog.getString("userId"),
					appLog.getString("appChannel")
			}, '\t');
			outputMulti("log", key.getString("deviceId"), value, context);
			//record the uid if not null
			if(uidGuess == null) {
				uidGuess = appLog.getInt("userId");
			}
		}
		
		//output the renewed device uid map
		outputMulti("uid", key.getString("deviceId"), String.valueOf(uidGuess), context);
	}
}