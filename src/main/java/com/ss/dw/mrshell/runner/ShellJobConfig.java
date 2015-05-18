package com.ss.dw.mrshell.runner;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.OutputFormat;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;

import com.ss.dw.mrshell.mapred.ShellMRFlow;

public class ShellJobConfig {

	@SuppressWarnings("rawtypes")
	private Class<? extends Mapper> mapper;

	private Class<? extends ShellMRFlow> flow;

	@SuppressWarnings("rawtypes")
	private Class<? extends Reducer> reducer;

	@SuppressWarnings("rawtypes")
	private Class<? extends OutputFormat> outputFormat;

	@SuppressWarnings("rawtypes")
	private Class<? extends InputFormat> inputFormat;

	@SuppressWarnings("rawtypes")
	private Class<? extends Partitioner> partitioner;

	private Class<? extends WritableComparator> comparator;
	
	private Class<? extends WritableComparator> groupingComparator;

	@SuppressWarnings("rawtypes")
	private Class<? extends Reducer> combiner;
	
	private String name;

	private boolean lzoInput;

	private boolean lzoOutput;

	private int numOfReducer;

	private Map<String, List<String>> inputPath;

	private Map<String, List<String>> configPath;
	
	private Map<String, String> argMap;
	
	private Map<String, String[]> argsMap;
	
	private String outputPath;
	
	private Date taskDate;
	
	@SuppressWarnings("rawtypes")
	public Class<? extends Mapper> getMapper() {
		return mapper;
	}

	@SuppressWarnings("rawtypes")
	public void setMapper(Class<? extends Mapper> mapper) {
		this.mapper = mapper;
	}
	
	public Class<? extends ShellMRFlow> getFlow() {
		return flow;
	}

	public void setFlow(Class<? extends ShellMRFlow> flow) {
		this.flow = flow;
	}

	@SuppressWarnings("rawtypes")
	public Class<? extends Reducer> getReducer() {
		return reducer;
	}

	@SuppressWarnings("rawtypes")
	public void setReducer(Class<? extends Reducer> reducer) {
		this.reducer = reducer;
	}
	
	@SuppressWarnings("rawtypes")
	public Class<? extends OutputFormat> getOutputFormat() {
		return outputFormat;
	}

	@SuppressWarnings("rawtypes")
	public void setOutputFormat(Class<? extends OutputFormat> outputFormat) {
		this.outputFormat = outputFormat;
	}

	@SuppressWarnings("rawtypes")
	public Class<? extends InputFormat> getInputFormat() {
		return inputFormat;
	}

	@SuppressWarnings("rawtypes")
	public void setInputFormat(Class<? extends InputFormat> inputFormat) {
		this.inputFormat = inputFormat;
	}

	@SuppressWarnings("rawtypes")
	public Class<? extends Partitioner> getPartitioner() {
		return partitioner;
	}

	@SuppressWarnings("rawtypes")
	public void setPartitioner(Class<? extends Partitioner> partitioner) {
		this.partitioner = partitioner;
	}

	public Class<? extends WritableComparator> getComparator() {
		return comparator;
	}

	public void setComparator(Class<? extends WritableComparator> comparator) {
		this.comparator = comparator;
	}

	public Class<? extends WritableComparator> getGroupingComparator() {
		return groupingComparator;
	}

	public void setGroupingComparator(
			Class<? extends WritableComparator> groupingComparator) {
		this.groupingComparator = groupingComparator;
	}

	@SuppressWarnings("rawtypes")
	public Class<? extends Reducer> getCombiner() {
		return combiner;
	}

	@SuppressWarnings("rawtypes")
	public void setCombiner(Class<? extends Reducer> combiner) {
		this.combiner = combiner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isLzoInput() {
		return lzoInput;
	}

	public void setLzoInput(boolean lzoInput) {
		this.lzoInput = lzoInput;
	}

	public boolean isLzoOutput() {
		return lzoOutput;
	}

	public void setLzoOutput(boolean lzoOutput) {
		this.lzoOutput = lzoOutput;
	}

	public int getNumOfReducer() {
		return numOfReducer;
	}

	public void setNumOfReducer(int numOfReducer) {
		this.numOfReducer = numOfReducer;
	}

	public Map<String, List<String>> getInputPath() {
		return inputPath;
	}

	public void setInputPath(Map<String, List<String>> inputPath) {
		this.inputPath = inputPath;
	}

	public Map<String, List<String>> getConfigPath() {
		return configPath;
	}

	public void setConfigPath(Map<String, List<String>> configPath) {
		this.configPath = configPath;
	}

	public Map<String, String> getArgMap() {
		return argMap;
	}

	public void setArgMap(Map<String, String> argMap) {
		this.argMap = argMap;
	}

	public Map<String, String[]> getArgsMap() {
		return argsMap;
	}

	public Date getTaskDate() {
		return taskDate;
	}

	public void setTaskDate(Date taskDate) {
		this.taskDate = taskDate;
	}

	public void setArgsMap(Map<String, String[]> argsMap) {
		this.argsMap = argsMap;
	}

	public String getOutputPath() {
		return outputPath;
	}

	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

	public String toString() {
		return String.format("%s\nName:\n %s\nMapper:\n %s\nFlower:\n %s\nReducer:\n %s\nCombiner:\n %s\nInputFormat:\n %s\nOutputFormat:\n %s\nPartitioner:\n %s\nComparator:\n %s\nGroupingComparator:\n %s\n",
				super.toString(), 
				name, 
				mapper.getName(), 
				flow.getName(), 
				(reducer == null ? "none" : reducer.getName()),
				(combiner == null ? "none" : combiner.getName()),
				inputFormat.getName(),
				outputFormat.getName(),
				partitioner.getName(),
				(comparator == null ? "default" : comparator.getName()),
				(groupingComparator == null ? "default" : groupingComparator.getName()));
	}
}