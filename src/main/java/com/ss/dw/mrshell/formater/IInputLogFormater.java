package com.ss.dw.mrshell.formater;

import com.ss.dw.mrshell.log.ILog;

public interface IInputLogFormater {

	public boolean isTypeOf (Class<? extends ILog> clazz);
	
	public ILog parseLog(String logString, Class<? extends ILog> clazz);
}
