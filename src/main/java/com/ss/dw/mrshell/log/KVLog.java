package com.ss.dw.mrshell.log;

import com.ss.dw.mrshell.annotation.AKVLog;
import com.ss.dw.mrshell.log.ILog;

@AKVLog
public class KVLog implements ILog {

	public boolean isValid()
	{
		return true;
	}

	public String getSeperator() 
	{
		return "&";
	}
}
