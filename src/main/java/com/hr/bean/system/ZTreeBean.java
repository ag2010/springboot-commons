package com.hr.bean.system;

public class ZTreeBean implements java.io.Serializable{
	
	private String id;
	private String pId;
	private String name;
	private boolean checked;
	private boolean open;
	private String code;
	private String pcode;
	
	
	public ZTreeBean(String id,String pId,String name){
		this.id=id;
		this.pId=pId;
		this.name=name;
	}
	
	public ZTreeBean(){
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPcode() {
		return pcode;
	}

	public void setPcode(String pcode) {
		this.pcode = pcode;
	}



	

	
	
	

}
