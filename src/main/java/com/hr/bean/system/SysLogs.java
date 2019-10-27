package com.hr.bean.system;

import java.sql.Timestamp;

public class SysLogs implements java.io.Serializable{
	
	

	private int id;
	private int user_id;
	private String user_name;
	private String oper_act;
	private String log_memo;
	private String log_ip;
	private String user_os;
	private String browser;
	private Timestamp create_time;
	private int status;
	
	private String start;
	private String end;
	private String userIdStr;
	private String statusStr;
	
	
	
	public String getUserIdStr() {
		return userIdStr;
	}
	public void setUserIdStr(String userIdStr) {
		this.userIdStr = userIdStr;
	}
	public String getStatusStr() {
		return statusStr;
	}
	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getOper_act() {
		return oper_act;
	}
	public void setOper_act(String oper_act) {
		this.oper_act = oper_act;
	}
	public String getLog_memo() {
		return log_memo;
	}
	public void setLog_memo(String log_memo) {
		this.log_memo = log_memo;
	}
	public String getLog_ip() {
		return log_ip;
	}
	public void setLog_ip(String log_ip) {
		this.log_ip = log_ip;
	}
	public String getUser_os() {
		return user_os;
	}
	public void setUser_os(String user_os) {
		this.user_os = user_os;
	}
	public String getBrowser() {
		return browser;
	}
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	

	
	
	
	
	
	

	 
}
