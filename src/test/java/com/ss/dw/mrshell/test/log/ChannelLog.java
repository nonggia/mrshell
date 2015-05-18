package com.ss.dw.mrshell.test.log;

import org.apache.commons.lang.StringUtils;

import com.ss.dw.mrshell.annotation.AXSVLog.Param;
import com.ss.dw.mrshell.log.XSVLog;

public class ChannelLog extends XSVLog {

	@Param(index=0)
	private String name;

	@Param(index=1)
	private String normalizedName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNormalizedName() {
		return normalizedName;
	}

	public void setNormalizedName(String normalizedName) {
		this.normalizedName = normalizedName;
	}
	
	@Override
	public boolean isValid() {
		return StringUtils.isNotBlank(name) && StringUtils.isNotBlank(normalizedName);
	}
}
