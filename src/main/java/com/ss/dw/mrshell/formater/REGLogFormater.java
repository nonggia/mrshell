package com.ss.dw.mrshell.formater;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ss.dw.mrshell.annotation.AREGLog;
import com.ss.dw.mrshell.annotation.AREGLog.Param;
import com.ss.dw.mrshell.log.ILog;
import com.ss.dw.mrshell.util.ReflectionUtil;

public class REGLogFormater implements IInputLogFormater {

	public boolean isTypeOf(Class<? extends ILog> clazz) {
		Class<?> current = clazz;
		while (current != null) {
			AREGLog RegAnn = (AREGLog) current.getAnnotation(AREGLog.class);
			if (RegAnn != null) {
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

				Pattern pattern = Pattern.compile(log.getSeperator());
				Matcher matcher = pattern.matcher(logString);
				if(!matcher.find()) 
				{
					return null;
				}

				for (int i = 0; i < fields.length; ++i) {
					Field field = fields[i];
					Param param = field.getAnnotation(Param.class);
					if (param != null) {
						int groupIndex = param.group();
						String value = (matcher.groupCount() >= groupIndex ? matcher.group(groupIndex)
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
}
