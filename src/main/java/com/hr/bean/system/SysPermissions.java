package com.hr.bean.system;

public class SysPermissions implements java.io.Serializable{
	
	private int id;
	private String permissions_name;
	private String permissions_group;
	private String permissions_code;
	private String note;
	private int sort;
	private String ext1;
	
	private boolean hasPer;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPermissions_name() {
		return permissions_name;
	}
	public void setPermissions_name(String permissions_name) {
		this.permissions_name = permissions_name;
	}
	public String getPermissions_group() {
		return permissions_group;
	}
	public void setPermissions_group(String permissions_group) {
		this.permissions_group = permissions_group;
	}
	public String getPermissions_code() {
		return permissions_code;
	}
	public void setPermissions_code(String permissions_code) {
		this.permissions_code = permissions_code;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getExt1() {
		return ext1;
	}
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	public boolean isHasPer() {
		return hasPer;
	}
	public void setHasPer(boolean hasPer) {
		this.hasPer = hasPer;
	}
	

}
