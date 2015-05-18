package com.ss.dw.mrshell.log;

import com.ss.dw.mrshell.annotation.AXSVLog;

@AXSVLog
public class XSVLog implements ILog {

//	public enum Title
//	{
//		key, value;
//		public static Title parse(String type) {
//			return Enum.valueOf(Title.class, type.toLowerCase());
//		}
//	}
	
	public boolean isValid()
	{
		return true;
	}

	public String getSeperator() {
		return "\t";
	}
}
