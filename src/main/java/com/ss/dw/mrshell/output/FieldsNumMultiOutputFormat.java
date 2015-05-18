package com.ss.dw.mrshell.output;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.Text;

import com.ss.dw.mrshell.output.MultipleOutputFormat;

public class FieldsNumMultiOutputFormat extends MultipleOutputFormat<Text, Text> {

	protected String generateFileNameForKeyValue(Text key, Text value, String part)
	{
		String valueFields[] = StringUtils.split(value.toString(), '\t');
		return String.valueOf(valueFields.length) + "fields-" + part;
	}
}
