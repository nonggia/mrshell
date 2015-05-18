package com.ss.dw.mrshell.runner;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.ToolRunner;

import com.ss.dw.mrshell.config.util.ConfigUtil;
import com.ss.dw.mrshell.config.xsd.JobsType;
import com.ss.dw.mrshell.config.xsd.ResourceType;
import com.ss.dw.mrshell.config.xsd.ResourcesType;
import com.ss.dw.mrshell.util.ShellCmdLine;

public class Runner {
	
	public int run(String[] args) throws Exception {

		ShellCmdLine jcl = ShellCmdLine.parseJobConfig(args);
		if (!StringUtils.isBlank(jcl.jobConfig)) {

			File configFile = new File(jcl.jobConfig);
			String packageName = JobsType.class.getPackage().getName();
			JAXBContext jaxb = JAXBContext.newInstance(packageName);
			Unmarshaller unmar = jaxb.createUnmarshaller();
			@SuppressWarnings("unchecked")
			JobsType jobsType = ((JAXBElement<JobsType>)unmar.unmarshal(configFile)).getValue();

			//parse the resources map
			Map<String, ResourceType> resources = new HashMap<String, ResourceType>();
			ResourcesType resourcesType = jobsType.getResources();
			if (resourcesType != null )
			{
				for (ResourceType resourceType : resourcesType.getResource())
				{
					resources.put(resourceType.getName(), resourceType);
				}
			}
			//parse the job list
			List<ShellJob> jobs = new ArrayList<ShellJob>();
			for (int i = 0; i < jobsType.getJob().size(); i++) {
				jobs.add(ShellJob.instance(resources, jobsType.getJob().get(i), ConfigUtil.getInputTime(jcl.date)));
			}
			
			//run the jobs
			for (ShellJob job : jobs)
			{
				int value = ToolRunner.run(new Configuration(), job, args);
				if (value != 0)
					return value;
			}
		} else {
			//TODO:
		}
		return 0;
	}

	public static void main(String[] args) throws Exception {
		new Runner().run(args);
	}
}
