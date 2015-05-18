package com.ss.dw.mrshell.test.log;

import com.ss.dw.mrshell.annotation.AKVLog.Param;
import com.ss.dw.mrshell.log.KVLog;

public class AppLog extends KVLog {

	@Param(name="lv")
	private String logVersion;

	@Param(name="lts")
	private String logTimestamp;

	@Param(name="lt")
	private String logType;

	@Param(name="pdu")
	private String pvDurl;

	@Param(name="pwu")
	private String pvWurl;

	@Param(name="meg")
	private String mvEventGroup;

	@Param(name="men")
	private String mvEventName;

	@Param(name="mel")
	private String mvEventLabel;

	@Param(name="lat")
	private Double locateLat;

	@Param(name="lng")
	private Double locateLng;

	@Param(name="lci")
	private Integer locateCityId;

	@Param(name="uid")
	private Integer userId;

	@Param(name="ac")
	private String appChannel;

	@Param(name="av")
	private String appVersion;

	@Param(name="di")
	private String clientDeviceId;

	@Param(name="dm")
	private String clientDeviceMac;

	@Param(name="de")
	private String clientDeviceImei;

	@Param(name="dr")
	private String clientDeviceResolution;

	@Param(name="nt")
	private String clientNetworkType;

	@Param(name="ua")
	private String clientPlatformUa;

	@Param(name="pt")
	private String pageType;

	@Param(name="ps")
	private String pageSite;

	@Param(name="pn")
	private String pageName;

	@Param(name="bv")
	private String bizVars;

	public String getLogVersion() {
		return logVersion;
	}

	public void setLogVersion(String logVersion) {
		this.logVersion = logVersion;
	}

	public String getLogTimestamp() {
		return logTimestamp;
	}

	public void setLogTimestamp(String logTimestamp) {
		this.logTimestamp = logTimestamp;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getPvDurl() {
		return pvDurl;
	}

	public void setPvDurl(String pvDurl) {
		this.pvDurl = pvDurl;
	}

	public String getPvWurl() {
		return pvWurl;
	}

	public void setPvWurl(String pvWurl) {
		this.pvWurl = pvWurl;
	}

	public String getMvEventGroup() {
		return mvEventGroup;
	}

	public void setMvEventGroup(String mvEventGroup) {
		this.mvEventGroup = mvEventGroup;
	}

	public String getMvEventName() {
		return mvEventName;
	}

	public void setMvEventName(String mvEventName) {
		this.mvEventName = mvEventName;
	}

	public String getMvEventLabel() {
		return mvEventLabel;
	}

	public void setMvEventLabel(String mvEventLabel) {
		this.mvEventLabel = mvEventLabel;
	}

	public Double getLocateLat() {
		return locateLat;
	}

	public void setLocateLat(Double locateLat) {
		this.locateLat = locateLat;
	}

	public Double getLocateLng() {
		return locateLng;
	}

	public void setLocateLng(Double locateLng) {
		this.locateLng = locateLng;
	}

	public Integer getLocateCityId() {
		return locateCityId;
	}

	public void setLocateCityId(Integer locateCityId) {
		this.locateCityId = locateCityId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getAppChannel() {
		return appChannel;
	}

	public void setAppChannel(String appChannel) {
		this.appChannel = appChannel;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getClientDeviceId() {
		return clientDeviceId;
	}

	public void setClientDeviceId(String clientDeviceId) {
		this.clientDeviceId = clientDeviceId;
	}

	public String getClientDeviceMac() {
		return clientDeviceMac;
	}

	public void setClientDeviceMac(String clientDeviceMac) {
		this.clientDeviceMac = clientDeviceMac;
	}

	public String getClientDeviceImei() {
		return clientDeviceImei;
	}

	public void setClientDeviceImei(String clientDeviceImei) {
		this.clientDeviceImei = clientDeviceImei;
	}

	public String getClientDeviceResolution() {
		return clientDeviceResolution;
	}

	public void setClientDeviceResolution(String clientDeviceResolution) {
		this.clientDeviceResolution = clientDeviceResolution;
	}

	public String getClientNetworkType() {
		return clientNetworkType;
	}

	public void setClientNetworkType(String clientNetworkType) {
		this.clientNetworkType = clientNetworkType;
	}

	public String getClientPlatformUa() {
		return clientPlatformUa;
	}

	public void setClientPlatformUa(String clientPlatformUa) {
		this.clientPlatformUa = clientPlatformUa;
	}

	public String getPageType() {
		return pageType;
	}

	public void setPageType(String pageType) {
		this.pageType = pageType;
	}

	public String getPageSite() {
		return pageSite;
	}

	public void setPageSite(String pageSite) {
		this.pageSite = pageSite;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getBizVars() {
		return bizVars;
	}

	public void setBizVars(String bizVars) {
		this.bizVars = bizVars;
	}
}
