package com.ss.dw.mrshell.config.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import com.ss.dw.mrshell.config.xsd.GranuType;
import com.ss.dw.mrshell.config.xsd.ResourceType;
import com.ss.dw.mrshell.exception.MrshellApplicationException;
import com.ss.dw.mrshell.formater.LogFormaterFacotry;
import com.ss.dw.mrshell.log.ILog;

public class ConfigUtil {

	private static String INPUT_DATE_FORMAT = "yyyy-MM-dd:hh";
	private static String DAY_VAR_PAT = "\\$\\{\\s*[dD][aA][yY]\\s*\\}";
	private static String HOUR_VAR_PAT = "\\$\\{\\s*[hH][oO][uU][rR]\\s*\\}";
	private static String DAY_FORMAT = "yyyy-MM-dd";
	private static String HOUR_FORMAT = "hh";

	public static String TASK_DATE_VAR = "TASK_DATE_VAR";

	public static void getConfigLog(List<ILog> logs, String filePattern,
			Class<? extends ILog> clazz, FileSystem hdfs) throws IOException {
		if (logs == null) {
			logs = new ArrayList<ILog>();
		}
		InputStreamReader fileReader = null;
		BufferedReader reader = null;
		FSDataInputStream inputStream = null;
		try {
			Path path = new Path(filePattern);
			FileStatus[] fileStatus = hdfs.globStatus(path);

			for (FileStatus status : fileStatus) {
				if (status.isDir()) {
					getConfigLog(logs, status.getPath().toUri().getPath()
							+ "/*", clazz, hdfs);
					continue;
				}
				inputStream = hdfs.open(status.getPath());
				fileReader = new InputStreamReader(inputStream);
				reader = new BufferedReader(fileReader);
				String line = null;
				while ((line = reader.readLine()) != null) {
					// parse it like a log
					ILog log = LogFormaterFacotry.parseLog(line, clazz);
					if (log != null && log.isValid()) {
						logs.add(log);
					}
				}
				reader.close();
				fileReader.close();
				inputStream.close();
				reader = null;
				fileReader = null;
				inputStream = null;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (fileReader != null) {
				fileReader.close();
			}
			if (reader != null) {
				reader.close();
			}
			if (inputStream != null) {
				reader.close();
			}
		}
	}

	public static List<String> getInputs(ResourceType resourceType, int offset,
			int span, Date date) {
		List<String> res = new ArrayList<String>();
		switch (resourceType.getGranu()) {
		case NONE:
			res.add(resourceType.getPath());
			break;

		case DAY:
		case HOUR:
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE, offset);
			int step = span < 0 ? -1 : 1;
			for (int i = 0; i != span; i += step) {
				String path = resourceType.getPath().replaceAll(DAY_VAR_PAT,
						new SimpleDateFormat(DAY_FORMAT).format(cal.getTime()));
				if (GranuType.HOUR == resourceType.getGranu()) {
					path = path.replaceAll(HOUR_VAR_PAT, new SimpleDateFormat(
							HOUR_FORMAT).format(cal.getTime()));
				}
				res.add(path);
				cal.add(Calendar.DATE, step);
			}
			break;

		default:
			// TODO
			break;
		}
		return res;
	}

	public static String getOutput(ResourceType resourceType, Date date) {
		String res = null;
		switch (resourceType.getGranu()) {
		case NONE:
			res = resourceType.getPath();
			break;

		case DAY:
		case HOUR:
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			res = resourceType.getPath().replaceAll(DAY_VAR_PAT,
					new SimpleDateFormat(DAY_FORMAT).format(cal.getTime()));
			if (GranuType.HOUR == resourceType.getGranu()) {
				res = res.replaceAll(HOUR_VAR_PAT, new SimpleDateFormat(
						HOUR_FORMAT).format(cal.getTime()));
			}
			break;

		default:
			// TODO
			break;
		}
		return res;
	}

	public static Date getInputTime(String dateStr) {
		Date date = null;
		try {
			// if no dateStr provided, just use current time
			date = (dateStr == null ? new Date() : new SimpleDateFormat(
					INPUT_DATE_FORMAT).parse(dateStr));
			// TODO: warning here
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String getInputTime(Date date) {
		return new SimpleDateFormat(INPUT_DATE_FORMAT).format(date);
	}

	public static String getInputDate(String dateStr) {
		Date date = getInputTime(dateStr);
		if (date != null) {
			return new SimpleDateFormat(DAY_FORMAT).format(date);
		}
		return "";
	}

	public static int getInputHour(String dateStr) {
		Date date = getInputTime(dateStr);
		if (date != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return cal.get(Calendar.HOUR_OF_DAY);
		}
		return -1;
	}

	@SuppressWarnings("rawtypes")
	public static Date getInputTimeFromMapper(
			org.apache.hadoop.mapreduce.Mapper.Context context)
			throws MrshellApplicationException {
		String dateStr = null;
		if (context == null
				|| context.getConfiguration() == null
				|| (dateStr = context.getConfiguration().get(TASK_DATE_VAR)) == null) {
			throw new MrshellApplicationException(
					"fail to getInputTimeFromMapper");
		}
		return getInputTime(dateStr);
	}

	@SuppressWarnings("rawtypes")
	public static Date getInputTimeFromReducer(
			org.apache.hadoop.mapreduce.Reducer.Context context)
			throws MrshellApplicationException {
		String dateStr = null;
		if (context == null
				|| context.getConfiguration() == null
				|| (dateStr = context.getConfiguration().get(TASK_DATE_VAR)) == null) {
			throw new MrshellApplicationException(
					"fail to getInputTimeFromReducer");
		}
		return getInputTime(dateStr);
	}

	/*
	 * inputPattern : like src/main/* path : like src/main/foo/bar/mm
	 */
	public static boolean matches(String inputPattern, Path path,
			FileSystem hdfs) throws IOException {
		String targetPath = path.toUri().getPath();
		Path patternPath = new Path(inputPattern);
		FileStatus[] fileStatus = hdfs.globStatus(patternPath);
		if (fileStatus != null) {
			for (FileStatus status : fileStatus) {
				if (status.isDir() ? targetPath.startsWith(status.getPath()
						.toUri().getPath()) : targetPath.compareTo(status
						.getPath().toUri().getPath()) == 0) {
					return true;
				}
			}
		}
		return false;
	}

	public static Set<Integer> parseIntSet(String[] strSet) {
		Set<Integer> set = new HashSet<Integer>();
		if (strSet != null) {
			for (String i : strSet) {
				set.add(Integer.parseInt(i));
			}
		}
		return set;
	}

	public static List<Integer> parseIntArray(String[] strArr) {
		List<Integer> list = new ArrayList<Integer>();
		if (strArr != null)
		{
			for (String i: strArr) {
				list.add(Integer.parseInt(i));
			}
		}
		return list;
	}
	
	public static List<String> parseStrArray(String[] strArr) {
		List<String> list = new ArrayList<String>();
		if (strArr != null)
		{
			for (String s: strArr) {
				list.add(s);
			}
		}
		return list;
	}
}
