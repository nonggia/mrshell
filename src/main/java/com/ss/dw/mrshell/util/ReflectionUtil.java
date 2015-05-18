package com.ss.dw.mrshell.util;

public final class ReflectionUtil {

	private static final String STRING_TRUE = "true";
	private static final String STRING_FALSE = "false";
	private static final String INT_TRUE = "1";
	private static final String INT_FALSE = "0";

	private ReflectionUtil(){}
	
	public static Integer parseInt(String value) {
		try {
			return Integer.valueOf(value);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static Double parseDouble(String value) {
		try {
			return Double.valueOf(value);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static Float parseFloat(String value) {
		try {
			return Float.valueOf(value);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static Short parseShort(String value) {
		try {
			return Short.valueOf(value);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static Long parseLong(String value) {
		try {
			return Long.valueOf(value);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static Byte parseByte(String value) {
		try {
			return Byte.valueOf(value);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static Boolean parseBoolean(String value) {
		if (INT_TRUE.equals(value)) {
			return Boolean.TRUE;
		} else if (INT_FALSE.equals(value)) {
			return Boolean.FALSE;
		} else if (STRING_TRUE.equals(value)) {
			return Boolean.TRUE;
		} else if (STRING_FALSE.equals(value)) {
			return Boolean.FALSE;
		} else {
			return null;
		}
	}

	public static Object parseValue(Class<?> type, String value) {
		if (String.class.equals(type)) {
			return value;
		} else if (Integer.class.equals(type)) {
			return parseInt(value);
		} else if (Long.class.equals(type)) {
			return parseLong(value);
		} else if (Boolean.class.equals(type)) {
			return parseBoolean(value);
		} else if (Short.class.equals(type)) {
			return parseShort(value);
		} else if (Double.class.equals(type)) {
			return parseDouble(value);
		} else if (Float.class.equals(type)) {
			return parseFloat(value);
		} else if (Byte.class.equals(type)) {
			return parseByte(value);
		} else {
			return null;
		}
	}

}
