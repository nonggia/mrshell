package com.ss.dw.mrshell.formater;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ss.dw.mrshell.log.MRNameLog;
import com.ss.dw.mrshell.mapred.ShellMRFlow;

public class MRNameLogFormater {

	public static final String MR_SEP_PAT = "\\t";
	
	public static List<String> getList(String line)
	{
		if (line == null)
		{
			return new ArrayList<String>();
		} else {
			return new ArrayList<String>(Arrays.asList(StringUtils.splitPreserveAllTokens(line, ShellMRFlow.MR_SEP)));
		}
	}
	
	public static Map<String,Integer> getNamesIndex(String[] names)
	{
		return getNamesIndex(Arrays.asList(names));
	}
	
	public static Map<String,Integer> getNamesIndex(List<String> names)
	{
		Map<String,Integer> namesIndex = new HashMap<String,Integer>();
		for (int i=0; i<names.size(); i++)
		{
			namesIndex.put(names.get(i), i);
		}
		return namesIndex;
	}
	
	public static MRNameLog parseNameLog(String line, Map<String,Integer> namesIndex)
	{
		if (namesIndex != null)
		{
			MRNameLog log = new MRNameLog(namesIndex);
			log.load(getList(line));
			return log;
		} 
		return null;
	}
}
