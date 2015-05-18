package com.ss.dw.mrshell.test.job;

import com.ss.dw.mrshell.mapred.ShellMRFlow;

public class TestEtlMRFlow extends ShellMRFlow {

	@Override
	public void defineKey() {
		key = new String[]{"deviceId"};
	}

	@Override
	public void defineValue() {
		values.put("logs", new String[]{"logVersion", "logTimestamp", "logType", "userLogId", "appChannel"});
		values.put("uids", new String[]{"userGuessId"});
	}
}
