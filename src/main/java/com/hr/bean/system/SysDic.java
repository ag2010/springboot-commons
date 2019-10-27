package com.hr.bean.system;

public class SysDic implements java.io.Serializable{
	
	private int dic_id;//系统字典编号
	private int dic_previd;//上一级编号
	private String dic_name;//系统字典类型
	private String dic_key;//系统字典KEY
	private String dic_value;//系统字典VALUE
	private String dic_desc;//说明
	private int dic_state;//状态(0:启用，1:禁用)
	
	public int getDic_id() {
		return dic_id;
	}
	public void setDic_id(int dic_id) {
		this.dic_id = dic_id;
	}
	public int getDic_previd() {
		return dic_previd;
	}
	public void setDic_previd(int dic_previd) {
		this.dic_previd = dic_previd;
	}
	public String getDic_name() {
		return dic_name;
	}
	public void setDic_name(String dic_name) {
		this.dic_name = dic_name;
	}
	public String getDic_key() {
		return dic_key;
	}
	public void setDic_key(String dic_key) {
		this.dic_key = dic_key;
	}
	public String getDic_value() {
		return dic_value;
	}
	public void setDic_value(String dic_value) {
		this.dic_value = dic_value;
	}
	public String getDic_desc() {
		return dic_desc;
	}
	public void setDic_desc(String dic_desc) {
		this.dic_desc = dic_desc;
	}
	public int getDic_state() {
		return dic_state;
	}
	public void setDic_state(int dic_state) {
		this.dic_state = dic_state;
	}
	
	

}
