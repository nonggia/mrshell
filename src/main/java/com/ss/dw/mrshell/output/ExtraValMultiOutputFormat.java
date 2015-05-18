package com.ss.dw.mrshell.output;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.Text;

import com.ss.dw.mrshell.output.MultipleOutputFormat;

public class ExtraValMultiOutputFormat extends MultipleOutputFormat<Text, Text> {

	protected String generateFileNameForKeyValue(Text key, Text value, String part)
	{
		String valueFields[] = StringUtils.split(value.toString(), "\t", 2);
		String name = null;
		if (valueFields.length == 2)
		{
			name = valueFields[0]; 
			value.set(valueFields[1]);
		} else if (valueFields.length == 1)
		{
			name = valueFields[0];
			value.set("");
		} else {
			name = "null";
		}
			return name + "-" + part;
	}
}
