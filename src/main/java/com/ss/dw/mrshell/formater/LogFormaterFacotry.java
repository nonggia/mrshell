package com.ss.dw.mrshell.formater;

import java.util.ArrayList;
import java.util.List;

import com.ss.dw.mrshell.log.ILog;


public class LogFormaterFacotry {

	private static List<IInputLogFormater> formaters = new ArrayList<IInputLogFormater>();
	
	static {
		formaters.add(new XSVLogFormater());
		formaters.add(new KVLogFormater());
		formaters.add(new REGLogFormater());
	}
	
	public static ILog parseLog(String logString, Class<? extends ILog> clazz)
	{
		for (IInputLogFormater formater : formaters)
		{
			if (formater.isTypeOf(clazz))
			{
				return formater.parseLog(logString, clazz);
			}
		}
		return null;
	}
	
}
