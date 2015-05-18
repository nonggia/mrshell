package com.ss.dw.mrshell.formater;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ss.dw.mrshell.annotation.AKVLog;
import com.ss.dw.mrshell.annotation.AKVLog.Param;
import com.ss.dw.mrshell.formater.IInputLogFormater;
import com.ss.dw.mrshell.log.ILog;
import com.ss.dw.mrshell.util.ReflectionUtil;

public class KVLogFormater implements IInputLogFormater {
	
	public boolean isTypeOf (Class<? extends ILog> clazz)
	{
		Class<?> current = clazz;
		while (current != null)
		{
			AKVLog KVAnn = (AKVLog) current
				.getAnnotation(AKVLog.class);
			if (KVAnn != null)
			{
				return true;
			}
			current = current.getSuperclass();
		}
		return false;
	}
	
	public ILog parseLog(String logString, Class<? extends ILog> clazz)
	{
		if (logString == null)
		{
			return null;
		}
		
		if (clazz != null) {
			Field[] fields = clazz.getDeclaredFields();
			try {
				ILog log = (ILog) clazz.newInstance();

				Map<String, String> map = new HashMap<String, String>();
				String[] pairs = StringUtils.split(logString, log.getSeperator());
				for (int i = 0; i < pairs.length; ++i) {
					String[] pair = StringUtils.split(pairs[i], "=");
					if (pair.length == 2) {
						map.put(pair[0], pair[1]);
					}
				}
				
				for (int i = 0; i < fields.length; ++i) {
					Field field = fields[i];
					Param param = field.getAnnotation(Param.class);
					if (param != null) {
						String name = param.name();
						String value = map.get(name);
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
