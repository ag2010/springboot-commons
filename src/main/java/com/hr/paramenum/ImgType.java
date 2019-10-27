package com.hr.paramenum;

public enum ImgType {
	DEVICE("设备",1),
	FAULT("故障",2),
	COMMENT("评论",3);
	
	private String name;
	private Integer code;
	
	
	
	
	ImgType(String name,Integer code){
		this.name= name;
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	
	
	
}
