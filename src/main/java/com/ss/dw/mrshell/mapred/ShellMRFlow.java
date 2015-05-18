package com.ss.dw.mrshell.mapred;

import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;

public abstract class ShellMRFlow {

	public static String CTX_NAME = "ShellMRFlow";
	public static String MR_SEP = "\t";
	protected String[] key;
	protected Map<String, String[]> values = new HashMap<String, String[]>();
	
	public static ShellMRFlow getFlow(Configuration configuration)
	{
		ShellMRFlow flow = null;
		try {
			flow = Class.forName(configuration.get(CTX_NAME)).asSubclass(ShellMRFlow.class).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return flow;
	}
	
	public ShellMRFlow()
	{
		defineKey();
		defineValue();
	}
	
	public abstract void defineKey();
	
	public abstract void defineValue();

	public String[] getKey()
	{
		return key;
	}
	
	public Map<String, String[]> getValues()
	{
		return values;
	}
	
}
