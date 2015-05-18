package com.ss.dw.mrshell.log;

import java.util.List;

public abstract class MRLog implements ILog {

	public abstract boolean load(List<String> values);
	
	public String getSeperator()
	{
		return "\t";
	}
}
