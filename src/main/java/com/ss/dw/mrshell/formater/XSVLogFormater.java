package com.ss.dw.mrshell.formater;

import java.lang.reflect.Field;

import org.apache.commons.lang.StringUtils;

import com.ss.dw.mrshell.annotation.AXSVLog;
import com.ss.dw.mrshell.annotation.AXSVLog.Param;
import com.ss.dw.mrshell.exception.MrshellRuntimeException;
import com.ss.dw.mrshell.log.ILog;
import com.ss.dw.mrshell.log.XSVLog;
import com.ss.dw.mrshell.util.ReflectionUtil;

public class XSVLogFormater implements IInputLogFormater {

	public static final String XSV_SEP = "\t";

	public boolean isTypeOf(Class<? extends ILog> clazz) {
		Class<?> current = clazz;
		while (current != null) {
			AXSVLog XSVAnn = (AXSVLog) current.getAnnotation(AXSVLog.class);
			if (XSVAnn != null) {
				return true;
			}
			current = current.getSuperclass();
		}
		return false;
	}

	public ILog parseLog(String logString, Class<? extends ILog> clazz) {
		if (logString == null) {
			return null;
		}

		if (clazz != null) {
			Field[] fields = clazz.getDeclaredFields();
			try {
				ILog log = (ILog) clazz.newInstance();
				String[] params = logString.split(log.getSeperator());
				for (int i = 0; i < fields.length; ++i) {
					Field field = fields[i];
					Param param = field.getAnnotation(Param.class);
					if (param != null) {
						int index = param.index();
						String value = (params.length > index ? params[index]
								: null);
						if (value != null) {
							Object realValue = ReflectionUtil.parseValue(
									field.getType(), value);
							if (realValue != null) {
								field.setAccessible(true);
								field.set(log, realValue);
							}
						}
					}
				}
				return log;
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static String getKey(XSVLog log) {
		return getFields(log, 0, 1);
	}

	public static String getValue(XSVLog log) {
		return getFields(log, 1);
	}

	private static String getFields(XSVLog log, int start) {
		return getFields(log, start, log.getClass().getDeclaredFields().length);
	}

	private static String getFields(XSVLog log, int start, int end) {
		String[] values = new String[end > start ? end - start : 0];
		Field[] fields = log.getClass().getDeclaredFields();
		for (Field field : fields) {
			Param param = field.getAnnotation(Param.class);
			if (param != null) {
				int index = param.index();
				if (index >= start && index < end) {
					field.setAccessible(true);
					try {
						values[index - start] = (field.get(log) == null ? null
								: String.valueOf(field.get(log)));
					} catch (IllegalArgumentException e) {
						throw new MrshellRuntimeException("Failed in getFields",
								e);
					} catch (IllegalAccessException e) {
						throw new MrshellRuntimeException("Failed in getFields",
								e);
					}
				}
			}
		}
		int lastNotNull = 0;
		for (int i = 0; i < values.length; i++) {
			if (values[i] != null) {
				lastNotNull = i;
			}
		}
		return StringUtils.join(values, XSV_SEP, 0, lastNotNull + 1);
	}
}
