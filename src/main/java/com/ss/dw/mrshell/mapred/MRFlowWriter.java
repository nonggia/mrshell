package com.ss.dw.mrshell.mapred;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.Reducer;


public class MRFlowWriter {
	
	private String[] key;
	private String[] value;
	private String group;
	private Map<String, Integer> keyNameIndex;
	private Map<String, Integer> valueNameIndex;
	
	public MRFlowWriter(String group, Map<String, Integer> keyNameIndex, Map<String, Integer> valueNameIndex)
	{
		this.group = group;
		this.keyNameIndex = keyNameIndex;
		this.valueNameIndex = valueNameIndex;
		this.key = new String[this.keyNameIndex.size()];
		this.value = new String[this.valueNameIndex.size()];
	}
	
	public void writeValue(String field, Object toWrite)
	{
		value[valueNameIndex.get(field)] = String.valueOf(toWrite);
	}
	
	public void writeKey(String field, Object toWrite)
	{
		key[keyNameIndex.get(field)] = String.valueOf(toWrite);
	}
	
	@SuppressWarnings({ "rawtypes"})
	public void flush(Context context) throws IOException, InterruptedException
	{
		flush(context, true);
	}

	@SuppressWarnings({ "rawtypes"})
	public void flush(Reducer.Context context) throws IOException, InterruptedException
	{
		flush(context, true);
	}
	
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	public void flush(Context context, boolean needClear) throws IOException, InterruptedException
//	{
//		String keyStr = StringUtils.join(key, ShellMRFlow.MR_SEP);
//		String valueStr = StringUtils.join(value, ShellMRFlow.MR_SEP);
//		context.write(new Text(keyStr), new Text(group + ShellMRFlow.MR_SEP + valueStr));
//		if (needClear)
//		{
//			clear();
//		}
//	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void flush(Context context, boolean needClear) throws IOException, InterruptedException
	{
		int lastNotNull = 0;
		for (int i=0; i<key.length; i++)
		{
			if (key[i] != null)
			{
				lastNotNull = i;
			}
		}
		String keyStr = StringUtils.join(Arrays.copyOfRange(key, 0, lastNotNull+1), ShellMRFlow.MR_SEP);
		lastNotNull = 0;
		for (int i=0; i<value.length; i++)
		{
			if (value[i] != null)
			{
				lastNotNull = i;
			}
		}
		String valueStr =  StringUtils.join(Arrays.copyOfRange(value, 0, lastNotNull+1), ShellMRFlow.MR_SEP);
		context.write(new Text(keyStr), new Text(group + ShellMRFlow.MR_SEP + valueStr));
		if (needClear)
		{
			clear();
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void flush(Reducer.Context context, boolean needClear) throws IOException, InterruptedException
	{
		int lastNotNull = 0;
		for (int i=0; i<key.length; i++)
		{
			if (key[i] != null)
			{
				lastNotNull = i;
			}
		}
		String keyStr = StringUtils.join(Arrays.copyOfRange(key, 0, lastNotNull+1), ShellMRFlow.MR_SEP);
		lastNotNull = 0;
		for (int i=0; i<value.length; i++)
		{
			if (value[i] != null)
			{
				lastNotNull = i;
			}
		}
		String valueStr =  StringUtils.join(Arrays.copyOfRange(value, 0, lastNotNull+1), ShellMRFlow.MR_SEP);
		context.write(new Text(keyStr), new Text(group + ShellMRFlow.MR_SEP + valueStr));
		if (needClear)
		{
			clear();
		}
	}
	
	private void clear()
	{
		key = new String[key.length];
		value = new String[value.length];
	}

}
