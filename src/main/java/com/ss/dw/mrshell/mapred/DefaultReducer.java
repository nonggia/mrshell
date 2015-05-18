package com.ss.dw.mrshell.mapred;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Reducer;

public class DefaultReducer extends
		Reducer<Text, Text, Text, Writable> {

	@Override
	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		
		for (Text v : values)
		{
			context.write(key, v);			
		}
	}
}