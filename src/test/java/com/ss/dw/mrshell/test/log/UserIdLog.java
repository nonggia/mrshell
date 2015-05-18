package com.ss.dw.mrshell.test.log;

import org.apache.commons.lang.StringUtils;

import com.ss.dw.mrshell.annotation.AXSVLog.Param;
import com.ss.dw.mrshell.log.XSVLog;

public class UserIdLog extends XSVLog {

	@Param(index=0)
	private String deviceId;

	@Param(index=1)
	private Integer userId;
	
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public boolean isValid() {
		return StringUtils.isNotBlank(deviceId) &&
				userId != null &&
				userId > 0;
	}
}
