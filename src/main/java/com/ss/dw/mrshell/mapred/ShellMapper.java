package com.ss.dw.mrshell.mapred;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import com.ss.dw.mrshell.annotation.AMapper;
import com.ss.dw.mrshell.annotation.AMapper.AConfig;
import com.ss.dw.mrshell.annotation.AMapper.AInput;
import com.ss.dw.mrshell.config.util.ConfigUtil;
import com.ss.dw.mrshell.exception.MrshellApplicationException;
import com.ss.dw.mrshell.exception.MrshellRuntimeException;
import com.ss.dw.mrshell.formater.LogFormaterFacotry;
import com.ss.dw.mrshell.formater.MRNameLogFormater;
import com.ss.dw.mrshell.log.ILog;

@AMapper
public abstract class ShellMapper extends Mapper<Object, Text, Text, Text> {

	private Map<String, Integer> keyIndex;
	private Map<String, Map<String, Integer>> valuesIndex = new HashMap<String, Map<String, Integer>>();
	private Date taskDate;
	private String inputDir;

	class Handler {
		private String pattern;
		private Class<? extends ILog> valueClass;
		private Method handleMethod;

		public Handler(String pattern, Class<? extends ILog> valueClass,
				Method handleMethod) {
			this.pattern = pattern;
			this.valueClass = valueClass;
			this.handleMethod = handleMethod;
		}

		public String getPattern() {
			return pattern;
		}

		public Class<? extends ILog> getValueClass() {
			return valueClass;
		}

		public Method getHandleMethod() {
			return handleMethod;
		}
	}

	private List<Handler> handlers = new ArrayList<Handler>();

	public MRFlowWriter getGroupWriter(String group) {
		return new MRFlowWriter(group, keyIndex, valuesIndex.get(group));
	}

	public String getInputDir()
	{
		return inputDir;
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

	@Override
	public void setup(Context context) throws IOException, InterruptedException {
		//pase date
		try {
			taskDate = ConfigUtil.getInputTimeFromMapper(context);
		} catch (MrshellApplicationException e1) {
			e1.printStackTrace();
		}
		// for the mrflow
		ShellMRFlow flow = ShellMRFlow.getFlow(context.getConfiguration());
		defineKey(flow.getKey());
		Map<String, String[]> values = flow.getValues();
		for (String group : values.keySet()) {
			defineValueByGroup(group, values.get(group));
		}

		// the current input
		FileSplit split = (FileSplit) context.getInputSplit();
		Path path = split.getPath();
		FileSystem hdfs = FileSystem.get(context.getConfiguration());
		inputDir = path.getParent().getName();

		Method[] methods = getClass().getDeclaredMethods();
		for (Method method : methods) {
			AInput aInput = (AInput) method.getAnnotation(AInput.class);
			if (aInput != null) {
				String[] namedInputs = context.getConfiguration().getStrings(
						aInput.name());
				// validate signature : method(Object, ? extends ILog, Context)
				Class<?> paramTypes[] = method.getParameterTypes();
				if (paramTypes.length == 3 && paramTypes[0] == Object.class
						&& ILog.class.isAssignableFrom(paramTypes[1])
						&& paramTypes[2] == Context.class) {
					for (String inputPattern : namedInputs) {
						if (ConfigUtil.matches(inputPattern, path, hdfs)) {
							// only add those handlers match the pattern, and
							// add once is enough
							handlers.add(new Handler(aInput.name(),
									paramTypes[1].asSubclass(ILog.class),
									method));
							break;
						}
					}
				} else {
					// TODO: warning?
				}
			}

			AConfig aConfig = (AConfig) method.getAnnotation(AConfig.class);
			if (aConfig != null) {
				// validate signature : method(? extends ILog, Context)
				Class<?> paramTypes[] = method.getParameterTypes();
				if (paramTypes.length == 2
						&& ILog.class.isAssignableFrom(paramTypes[0])
						&& paramTypes[1] == Context.class) {
					String[] namedConfigs = context.getConfiguration()
							.getStrings(aConfig.name());
					if (namedConfigs != null) {
						for (String config : namedConfigs) {
							List<ILog> logs = new ArrayList<ILog>();
							ConfigUtil.getConfigLog(logs, config,
									paramTypes[0].asSubclass(ILog.class), hdfs);
							for (ILog log : logs) {
								// invoke the handler
								try {
									method.invoke(this, new Object[] { log,
											context });
								} catch (SecurityException e) {
									e.printStackTrace();
								} catch (IllegalArgumentException e) {
									e.printStackTrace();
								} catch (IllegalAccessException e) {
									e.printStackTrace();
								} catch (InvocationTargetException e) {
									e.printStackTrace();
								}
							}
						}
					} else {
						// TODO: shall we warn it about missing xml for configs?
					}
				} else {
					// TODO: warning?
				}
			}
		}

		super.setup(context);
	}

	@Override
	public void map(Object key, Text line, Context context) throws IOException,
			InterruptedException {
		for (Handler handler : handlers) {
			ILog log = LogFormaterFacotry.parseLog(line.toString(),
					handler.getValueClass());
			if (log == null || !log.isValid()) {
				continue;
			}
			// invoke the handler
			try {
				handler.getHandleMethod().invoke(this,
						new Object[] { key, log, context });
			} catch (IllegalAccessException e) {
				throw new MrshellRuntimeException(
						"IllegalAccess when doing mapper", e);
			} catch (InvocationTargetException e) {
				postProcessException(e);
			}
		}
	}

	private void postProcessException(InvocationTargetException e)
			throws IOException, InterruptedException {
		if (e.getCause() != null) {
			Throwable exp = e.getCause();
			if (exp instanceof IOException) {
				throw (IOException) exp;
			} else if (exp instanceof InterruptedException) {
				throw (InterruptedException) exp;
			}
		}

		throw new MrshellRuntimeException("Failed to call", e);
	}

	private void defineKey(String[] fields) {
		keyIndex = MRNameLogFormater.getNamesIndex(fields);
	}

	private void defineValueByGroup(String group, String[] fields) {
		valuesIndex.put(group, MRNameLogFormater.getNamesIndex(fields));
	}

}
