package com.ss.dw.mrshell.test;
import com.ss.dw.mrshell.runner.Runner;


public class TestEtl {
	public static void main(String[] args)
	{
		args = new String[4];
		args[0] = "-j";
		args[1] = "src/test/resource/etl/job.xml";
		args[2] = "-d";
		args[3] = "2015-05-16:00";
		try {
			new Runner().run(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
