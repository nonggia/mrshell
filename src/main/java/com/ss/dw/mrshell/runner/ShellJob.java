package com.ss.dw.mrshell.runner;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.OutputFormat;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;

import com.hadoop.mapreduce.LzoTextInputFormat;
import com.ss.dw.mrshell.config.util.ConfigUtil;
import com.ss.dw.mrshell.config.xsd.ArgType;
import com.ss.dw.mrshell.config.xsd.ArgsType;
import com.ss.dw.mrshell.config.xsd.ConfigType;
import com.ss.dw.mrshell.config.xsd.InputType;
import com.ss.dw.mrshell.config.xsd.JobType;
import com.ss.dw.mrshell.config.xsd.PartType;
import com.ss.dw.mrshell.config.xsd.ResourceType;
import com.ss.dw.mrshell.exception.MrshellApplicationException;
import com.ss.dw.mrshell.mapred.ShellMRFlow;

public class ShellJob extends Configured implements Tool {

	public static ShellJob instance(Map<String, ResourceType> resourceTypes, JobType jobType, Date date)
	{
		ShellJobConfig config = new ShellJobConfig();
		config.setTaskDate(date);
		
		config.setName(jobType.getName());
		try {
			config.setMapper(Class.forName(jobType.getMapper()).asSubclass(Mapper.class));
			if (jobType.getReducer() != null)
			{
				config.setReducer(Class.forName(jobType.getReducer()).asSubclass(Reducer.class));
			}
			if (jobType.getCombiner() != null)
			{
				config.setCombiner(Class.forName(jobType.getCombiner()).asSubclass(Reducer.class));
			}
			if (jobType.getComparator() != null)
			{
				config.setComparator(Class.forName(jobType.getComparator()).asSubclass(WritableComparator.class));
			}
			if (jobType.getGroupingComparator() != null)
			{
				config.setGroupingComparator(Class.forName(jobType.getGroupingComparator()).asSubclass(WritableComparator.class));
			}
			config.setFlow(Class.forName(jobType.getFlow()).asSubclass(ShellMRFlow.class));
			config.setInputFormat(Class.forName(jobType.getInputFormat()).asSubclass(InputFormat.class));
			config.setOutputFormat(Class.forName(jobType.getOutputFormat()).asSubclass(OutputFormat.class));
			config.setPartitioner(Class.forName(jobType.getPartitioner()).asSubclass(Partitioner.class));
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		config.setLzoInput(jobType.isLzoInput());
		config.setLzoOutput(jobType.isLzoOutput());
		config.setNumOfReducer(jobType.getReducerNum());
		//parse input list
		Map<String, List<String>> inputs= new HashMap<String, List<String>>();
		for (InputType inputType : jobType.getInputs().getInput())
		{
			List<String> input = new ArrayList<String>();
			for (PartType partType : inputType.getPart())
			{
				ResourceType resourceType = resourceTypes.get(partType.getRef());
				if (resourceType != null)
				{
					input.addAll(ConfigUtil.getInputs(resourceType, partType.getOffset(),
							partType.getSpan(), date));
				} else {
					//TODO
				}
			}
			inputs.put(inputType.getId(), input);
		}
		config.setInputPath(inputs);
		

		//parse config list
		Map<String, List<String>> configs = new HashMap<String, List<String>>();
		if (jobType.getConfigs() != null)
		{
			for (ConfigType configType : jobType.getConfigs().getConfig())
			{
				List<String> con = new ArrayList<String>();
				for (PartType partType : configType.getPart())
				{
					ResourceType resourceType = resourceTypes.get(partType.getRef());
					if (resourceType != null)
					{
						con.addAll(ConfigUtil.getInputs(resourceType, partType.getOffset(),
								partType.getSpan(), date));
					} else {
						//TODO
					}
				}
				configs.put(configType.getId(), con);
			}
			config.setConfigPath(configs);
		}

		Map<String, String> argMap = new HashMap<String, String>();
		Map<String, String[]> argsMap = new HashMap<String, String[]>();
		if (jobType.getArguments() != null)
		{
			//single value arg
			List<ArgType> argTypes = jobType.getArguments().getArg();
			if (argTypes!=null)
			{
				for (ArgType arg : argTypes)
				{
					argMap.put(arg.getId(), arg.getValue());
				}
			}
			
			//multi value arg
			List<ArgsType> argsTypes = jobType.getArguments().getArgs();
			if (argsTypes!=null)
			{
				for (ArgsType args : argsTypes)
				{
					argsMap.put(args.getId(), args.getValue().toArray(new String[0]));
				}
			}
		}
		config.setArgsMap(argsMap);
		config.setArgMap(argMap);
		
		//output path
		config.setOutputPath(ConfigUtil.getOutput(resourceTypes.get(jobType.getOutput().getRef()), date));
		
		return new ShellJob(config);
	}
	
	private ShellJobConfig config;
	
	public ShellJob(ShellJobConfig config)
	{
		this.config = config;
	}

	public int run(String[] arg0) throws Exception {

		Configuration conf = getConf();
		if (config.isLzoOutput()) {
			conf.setBoolean("mapred.output.compress", true);
			conf.set("mapred.output.compression.codec",
					"com.hadoop.compression.lzo.LzopCodec");
		}
		
		conf.set(ShellMRFlow.CTX_NAME, config.getFlow().getName());
		//for pattern matching in ShellMapper
		for (String inputName : config.getInputPath().keySet())
		{
			conf.setStrings(inputName, config.getInputPath().get(inputName).toArray(new String[0]));
		}
		if (config.getConfigPath() != null)
		{
			for (String configName : config.getConfigPath().keySet())
			{
				conf.setStrings(configName, config.getConfigPath().get(configName).toArray(new String[0]));
			}
		}
		//pass the args
		for (String arg : config.getArgMap().keySet())
		{
			conf.set(arg, config.getArgMap().get(arg));
		}
		for (String args : config.getArgsMap().keySet())
		{
			conf.setStrings(args, config.getArgsMap().get(args));
		}
		
		//pass the date
		conf.set(ConfigUtil.TASK_DATE_VAR, ConfigUtil.getInputTime(config.getTaskDate()));
		
		Job job = new Job(conf);
		job.setJobName(config.getName());
		job.setJarByClass(ShellJob.class);
		job.setMapperClass(config.getMapper());
		if (config.getReducer() != null)
		{
			job.setReducerClass(config.getReducer());
			job.setNumReduceTasks(config.getNumOfReducer());
		}
		if (config.getCombiner() != null)
		{
			job.setCombinerClass(config.getCombiner());
		}
		if (config.getComparator() != null)
		{
			job.setSortComparatorClass(config.getComparator());
		}
		if (config.getGroupingComparator() != null)
		{
			job.setGroupingComparatorClass(config.getGroupingComparator());
		}
		job.setPartitionerClass(config.getPartitioner());

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		if (config.isLzoInput())
			job.setInputFormatClass(LzoTextInputFormat.class);
		else
			job.setInputFormatClass(config.getInputFormat());
		job.setOutputFormatClass(config.getOutputFormat());

		// input path
		for (String inputName : config.getInputPath().keySet()) {
			List<String> input = config.getInputPath().get(inputName);
			//add one input
			for (int j = 0; j < input.size(); j++)
			{
				//add every part of the input
				FileInputFormat.addInputPaths(job, input.get(j));
			}
		}

		// output path
		Path outputPath = new Path(config.getOutputPath());
		FileSystem fs = FileSystem.get(conf);
		fs.delete(outputPath, true);
		FileOutputFormat.setOutputPath(job, outputPath);

		System.out.println(String
				.format("==============Start job=============="));
		System.out.println(config.toString());

		boolean success = job.waitForCompletion(true);

		System.out.println(String
				.format("==============Job finished=============="));
		if (!success) {
			throw new MrshellApplicationException(String.format(
					"HolmesJob %s failed!", job.getJobName()));
		}

		return 0;
	}
}
