package com.ss.dw.mrshell.log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GroupNameLog {

	private Map<String, List<MRNameLog>> groupValues;

	public GroupNameLog(Map<String, List<MRNameLog>> groupValues)
	{
		this.groupValues = groupValues;
	}
	
	public List<MRNameLog> group(String group)
	{
		List<MRNameLog> res = null;
		if (group!=null && groupValues != null)
		{
			res = groupValues.get(group);
		}
		if (res == null)
		{
			res = new ArrayList<MRNameLog>();
		}
		return res;
	}
	
	public int sizeOfGroup(String group)
	{
		return group(group).size();
	}
	
	public boolean hasGroup(String group)
	{
		return sizeOfGroup(group) > 0;
	}
}
