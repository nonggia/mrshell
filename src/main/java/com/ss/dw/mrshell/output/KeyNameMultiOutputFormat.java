package com.ss.dw.mrshell.output;

import org.apache.hadoop.io.Text;

import com.ss.dw.mrshell.output.MultipleOutputFormat;

public class KeyNameMultiOutputFormat extends MultipleOutputFormat<Text, Text> {

	protected String generateFileNameForKeyValue(Text key, Text value, String part)
	{
		return key.toString() + "-" + part;
	}
}
