package com.ss.dw.mrshell.util;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.ss.dw.mrshell.util.ShellCmdLine;


public class ShellCmdLine {

	public String jobConfig;
	public String date;
	public boolean retry = false;

	public static ShellCmdLine parseJobConfig(String[] args) {
		Options options = new Options();
		options.addOption("j", true, "job config");
		options.addOption("d", true, "process date");
		options.addOption("r", false, "retry");
		options.addOption("conf", false, "generic option - specify a configuration file");
		options.addOption("D", false, "generic option - use value for given property");
		options.addOption("files", false, "generic option - specify comma separated files to be copied to the map reduce cluster");
		options.addOption("libjars", false, "generic option - specify comma separated jar files to include in the classpath");
		options.addOption("archives", false, "generic option - specify comma separated archives to be unarchived on the compute machines");
		BasicParser parser = new BasicParser();
		try {
			CommandLine cl = parser.parse(options, args);
			ShellCmdLine config = new ShellCmdLine();
			if (cl.hasOption("j"))
				config.jobConfig = cl.getOptionValue("j");

			if (cl.hasOption("d"))
				config.date = cl.getOptionValue("d");

			if (cl.hasOption("r"))
				config.retry = true;

			return config;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
}
