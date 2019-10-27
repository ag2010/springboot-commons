package com.hr.bean.system;

import java.sql.Timestamp;

public class SmsRecordLogs {
	
	private int id;
	private String title;
	private String content;
	private String send_to;
	private int receive_account_id;
	private int status;
	private Timestamp send_time;
	private Timestamp add_time;
	private String note;
	private int template_id;
	private int message_type;
	private int send_employee_id;
	private String sms_trigger;
	
	private String messageTypeStr;
	private String beginTime;
	private String endTime;
	private String statusStr;
	private String receiveAccountId;
	
	
	
	
	
	public String getReceiveAccountId() {
		return receiveAccountId;
	}
	public void setReceiveAccountId(String receiveAccountId) {
		this.receiveAccountId = receiveAccountId;
	}
	public String getMessageTypeStr() {
		return messageTypeStr;
	}
	public void setMessageTypeStr(String messageTypeStr) {
		this.messageTypeStr = messageTypeStr;
	}
	
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSend_to() {
		return send_to;
	}
	public void setSend_to(String send_to) {
		this.send_to = send_to;
	}
	public int getReceive_account_id() {
		return receive_account_id;
	}
	public void setReceive_account_id(int receive_account_id) {
		this.receive_account_id = receive_account_id;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Timestamp getSend_time() {
		return send_time;
	}
	public void setSend_time(Timestamp send_time) {
		this.send_time = send_time;
	}
	public Timestamp getAdd_time() {
		return add_time;
	}
	public void setAdd_time(Timestamp add_time) {
		this.add_time = add_time;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(int template_id) {
		this.template_id = template_id;
	}
	public int getMessage_type() {
		return message_type;
	}
	public void setMessage_type(int message_type) {
		this.message_type = message_type;
	}
	public int getSend_employee_id() {
		return send_employee_id;
	}
	public void setSend_employee_id(int send_employee_id) {
		this.send_employee_id = send_employee_id;
	}
	public String getSms_trigger() {
		return sms_trigger;
	}
	public void setSms_trigger(String sms_trigger) {
		this.sms_trigger = sms_trigger;
	}
	
	
	
    
}
