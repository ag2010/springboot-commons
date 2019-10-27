package com.hr.bean.system;

import java.sql.Timestamp;

public class SysUsers implements java.io.Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 9149542351466919855l;
	/**
	 * 
	 */
	
	private int id;
	private int dept_id;
	private int role_id;
	private String user_number;
	private String user_name;
	private String real_name;
	private String nick_name;
	private String passwd;
	private String new_pwd;
	private String comfirmPwd;
	private String gender;//性别
	private String mobile;
	private String email;
	private int status;
	private String create_ip;
	private Timestamp create_time;
	private String last_ip;
	private Timestamp last_visit;
	private int login_count;
	private int ext1;
	
	private String dept_name;
	private String role_name;
	
	
	
	
	
	
	public String getComfirmPwd() {
		return comfirmPwd;
	}
	public void setComfirmPwd(String comfirmPwd) {
		this.comfirmPwd = comfirmPwd;
	}
	public String getNew_pwd() {
		return new_pwd;
	}
	public void setNew_pwd(String new_pwd) {
		this.new_pwd = new_pwd;
	}
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
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	public String getUser_number() {
		return user_number;
	}
	public void setUser_number(String user_number) {
		this.user_number = user_number;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getReal_name() {
		return real_name;
	}
	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}
	public String getNick_name() {
		return nick_name;
	}
	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCreate_ip() {
		return create_ip;
	}
	public void setCreate_ip(String create_ip) {
		this.create_ip = create_ip;
	}

	public String getLast_ip() {
		return last_ip;
	}
	public void setLast_ip(String last_ip) {
		this.last_ip = last_ip;
	}

	public int getLogin_count() {
		return login_count;
	}
	public void setLogin_count(int login_count) {
		this.login_count = login_count;
	}
	public int getExt1() {
		return ext1;
	}
	public void setExt1(int ext1) {
		this.ext1 = ext1;
	}
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	public Timestamp getLast_visit() {
		return last_visit;
	}
	public void setLast_visit(Timestamp last_visit) {
		this.last_visit = last_visit;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}



	  
	  
	  
}
