package com.hr.bean.system;

public class SysRole implements java.io.Serializable{

	private int id;
	private int dept_id;
	private String role_name;
	private int sort;
	private String note;
	private String role_menu;
	private String role_permissions;
	private int status;
	private int ext1;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDept_id() {
		return dept_id;
	}
	public void setDept_id(int dept_id) {
		this.dept_id = dept_id;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getRole_menu() {
		return role_menu;
	}
	public void setRole_menu(String role_menu) {
		this.role_menu = role_menu;
	}
	public String getRole_permissions() {
		return role_permissions;
	}
	public void setRole_permissions(String role_permissions) {
		this.role_permissions = role_permissions;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getExt1() {
		return ext1;
	}
	public void setExt1(int ext1) {
		this.ext1 = ext1;
	}
	  
	  

}
