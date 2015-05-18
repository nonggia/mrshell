package com.ss.dw.mrshell.log;

import com.ss.dw.mrshell.annotation.AREGLog;

@AREGLog
public class REGLog implements ILog {

	public boolean isValid()
	{
		return true;
	}

	//use this interface to set 
	public String getSeperator() {
		//default the while line
		return "(.*)";
	}
}
