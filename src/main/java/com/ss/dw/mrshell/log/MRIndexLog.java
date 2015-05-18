package com.ss.dw.mrshell.log;

import java.util.List;

import com.ss.dw.mrshell.util.ReflectionUtil;

public class MRIndexLog extends MRLog {

	private List<String> values;
	
	public MRIndexLog()
	{
	}
	
	public boolean load(List<String> values)
	{
		this.values = values;
		return true;
	}

	public Integer getInt(int index) {
		try {
			return ReflectionUtil.parseInt(values.get(index));
		} catch (IndexOutOfBoundsException  e) {
			return null;
		} catch (NullPointerException e) {
			return null;
		}
	}

	public Double getDouble(int index) {
		try {
			return ReflectionUtil.parseDouble(values.get(index));
		} catch (IndexOutOfBoundsException  e) {
			return null;
		} catch (NullPointerException e) {
			return null;
		}
	}

	public Float getFloat(int index) {
		try {
			return ReflectionUtil.parseFloat(values.get(index));
		} catch (IndexOutOfBoundsException  e) {
			return null;
		} catch (NullPointerException e) {
			return null;
		}
	}

	public Short getShort(int index) {
		try {
			return ReflectionUtil.parseShort(values.get(index));
		} catch (IndexOutOfBoundsException  e) {
			return null;
		} catch (NullPointerException e) {
			return null;
		}
	}

	public Long getLong(int index) {
		try {
			return ReflectionUtil.parseLong(values.get(index));
		} catch (IndexOutOfBoundsException  e) {
			return null;
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	public String getString(int index) {
		try {
			return values.get(index);
		} catch (IndexOutOfBoundsException  e) {
			return null;
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	public Boolean getBoolean(int index) {
		try {
			return ReflectionUtil.parseBoolean(values.get(index));
		} catch (IndexOutOfBoundsException  e) {
			return null;
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	public Byte getByte(int index) {
		try {
			return ReflectionUtil.parseByte(values.get(index));
		} catch (IndexOutOfBoundsException  e) {
			return null;
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	public Object getValue(Class<?> type, int index) {
		if (String.class.equals(type)) {
			return getString(index);
		} else if (Integer.class.equals(type)) {
			return getInt(index);
		} else if (Long.class.equals(type)) {
			return getLong(index);
		} else if (Boolean.class.equals(type)) {
			return getBoolean(index);
		} else if (Short.class.equals(type)) {
			return getShort(index);
		} else if (Double.class.equals(type)) {
			return getDouble(index);
		} else if (Float.class.equals(type)) {
			return getFloat(index);
		} else if (Byte.class.equals(type)) {
			return getByte(index);
		} else {
			return null;
		}
	}

	public boolean isValid() {
		return (values != null);
	}
}
