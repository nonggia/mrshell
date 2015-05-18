package com.ss.dw.mrshell.log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MRNameLog extends MRLog {

	private Map<String,Integer> namesIndex = new HashMap<String,Integer>();
	private MRIndexLog log = new MRIndexLog();
	
	public MRNameLog(Map<String,Integer> namesIndex)
	{
		this.namesIndex = namesIndex;
	}
	
	public boolean load(List<String> values)
	{
		if (namesIndex!=null && namesIndex.size()>=values.size())
		{
			return log.load(values);
		} else {
			return false;
		}
	}

	public Integer getInt(String name) {
		try {
			Integer index = namesIndex.get(name);
			return index==null ? null : log.getInt(index);
		} catch (NullPointerException e) {
			return null;
		}
	}

	public Double getDouble(String name) {
		try {
			Integer index = namesIndex.get(name);
			return index==null ? null : log.getDouble(index);
		} catch (NullPointerException e) {
			return null;
		}
	}

	public Float getFloat(String name) {
		try {
			Integer index = namesIndex.get(name);
			return index==null ? null : log.getFloat(index);
		} catch (NullPointerException e) {
			return null;
		}
	}

	public Short getShort(String name) {
		try {
			Integer index = namesIndex.get(name);
			return index==null ? null : log.getShort(index);
		} catch (NullPointerException e) {
			return null;
		}
	}

	public Long getLong(String name) {
		try {
			Integer index = namesIndex.get(name);
			return index==null ? null : log.getLong(index);
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	public String getString(String name) {
		try {
			Integer index = namesIndex.get(name);
			return index==null ? null : log.getString(index);
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	public Boolean getBoolean(String name) {
		try {
			Integer index = namesIndex.get(name);
			return index==null ? null : log.getBoolean(index);
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	public Byte getByte(String name) {
		try {
			Integer index = namesIndex.get(name);
			return index==null ? null : log.getByte(index);
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	public Object getValue(Class<?> type, String name) {
		if (String.class.equals(type)) {
			return getString(name);
		} else if (Integer.class.equals(type)) {
			return getInt(name);
		} else if (Long.class.equals(type)) {
			return getLong(name);
		} else if (Boolean.class.equals(type)) {
			return getBoolean(name);
		} else if (Short.class.equals(type)) {
			return getShort(name);
		} else if (Double.class.equals(type)) {
			return getDouble(name);
		} else if (Float.class.equals(type)) {
			return getFloat(name);
		} else if (Byte.class.equals(type)) {
			return getByte(name);
		} else {
			return null;
		}
	}

	public Set<String> keySet()
	{
		return namesIndex.keySet();
	}
	
	public boolean isValid() {
		return (namesIndex != null && log.isValid());
	}
}
