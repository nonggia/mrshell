package com.ss.dw.mrshell.mapred;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import com.ss.dw.mrshell.annotation.AReducer;
import com.ss.dw.mrshell.config.util.ConfigUtil;
import com.ss.dw.mrshell.exception.MrshellApplicationException;
import com.ss.dw.mrshell.formater.MRNameLogFormater;
import com.ss.dw.mrshell.formater.XSVLogFormater;
import com.ss.dw.mrshell.log.GroupNameLog;
import com.ss.dw.mrshell.log.MRNameLog;
import com.ss.dw.mrshell.log.XSVLog;

@AReducer
public abstract class ShellReducer extends Reducer<Text, Text, Text, Text> {

	private Map<String, Integer> keyIndex;
	private Map<String, Map<String, Integer>> valuesIndex = new HashMap<String, Map<String, Integer>>();
	private Date taskDate;

	public abstract void process(MRNameLog key, GroupNameLog groupNameLog,
			Context context) throws IOException, InterruptedException;

	public void setup(Context context) throws IOException, InterruptedException {
		//pase date
		try {
			taskDate = ConfigUtil.getInputTimeFromReducer(context);
		} catch (MrshellApplicationException e1) {
			e1.printStackTrace();
		}
		ShellMRFlow flow = ShellMRFlow.getFlow(context.getConfiguration());
		defineKey(flow.getKey());
		Map<String, String[]> values = flow.getValues();
		for (String group : values.keySet()) {
			defineValueByGroup(group, values.get(group));
		}
	}

	public MRFlowWriter getGroupWriter(String group) {
		return new MRFlowWriter(group, keyIndex, valuesIndex.get(group));
	}
	
	public Date getTaskDate()
	{
		return taskDate;
	}
	public String getArg(Context context, String arg)
	{
		return context.getConfiguration().get(arg);
	}
	
	public String[] getArgs(Context context, String args)
	{
		return context.getConfiguration().getStrings(args);
	}

	public void defineKey(String[] fields) {
		keyIndex = MRNameLogFormater.getNamesIndex(fields);
	}

	public void defineValueByGroup(String group, String[] fields) {
		valuesIndex.put(group, MRNameLogFormater.getNamesIndex(fields));
	}
	
	public void outputMulti(String prefix, String key, String value,
			Context context) throws IOException, InterruptedException {
		context.write(new Text(key), new Text(prefix + XSVLogFormater.XSV_SEP
				+ value));
	}

	public void output(String key, String value, Context context)
			throws IOException, InterruptedException {
		context.write(new Text(key), new Text(value));
	}

	public void output(XSVLog log, Context context) throws IOException,
			InterruptedException {
		context.write(new Text(XSVLogFormater.getKey(log)), new Text(
				XSVLogFormater.getValue(log)));
	}

	public void outputMulti(String prefix, XSVLog log, Context context)
			throws IOException, InterruptedException {
		context.write(new Text(XSVLogFormater.getKey(log)), new Text(prefix
				+ XSVLogFormater.XSV_SEP + XSVLogFormater.getValue(log)));
	}

	@Override
	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		// for key
		MRNameLog keyLog = MRNameLogFormater.parseNameLog(key.toString(),
				keyIndex);
		if (keyLog == null) {
			// maybe flow not defined
			return;
		}
		// iterate values
		Map<String, List<MRNameLog>> groupLogs = new HashMap<String, List<MRNameLog>>();
		Iterator<Text> it = values.iterator();
		while (it.hasNext()) {
			String value = it.next().toString();
			String fields[] = value.split(MRNameLogFormater.MR_SEP_PAT, 2);
			if (fields.length != 2) {
				// how come, data not from shellmapper?
				continue;
			}
			Map<String, Integer> valueIndex = valuesIndex.get(fields[0]);
			if (valueIndex == null) {
				// there is output gourp in mapper which is not been marked in
				// flow
				continue;
			}
			MRNameLog valueLog = MRNameLogFormater.parseNameLog(fields[1],
					valueIndex);
			if (valueLog == null) {
				// couldn't be here
				continue;
			}
			List<MRNameLog> oneGroupLogs = groupLogs.get(fields[0]);
			if (oneGroupLogs == null) {
				oneGroupLogs = new ArrayList<MRNameLog>();
				groupLogs.put(fields[0], oneGroupLogs);
			}
			oneGroupLogs.add(valueLog);
		}

		process(keyLog, new GroupNameLog(groupLogs), context);
	}
}