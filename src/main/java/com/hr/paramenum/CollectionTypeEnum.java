package com.hr.paramenum;

public enum CollectionTypeEnum {
	
	SELFHELP("自主擦鞋","1"),
	DOORTODOOR("上门擦鞋","2"),
	REPAIR("鞋包修护","3");
	
	
	

	
	
	private String name;
	private String code;
	
	
	CollectionTypeEnum(String name,String code) {
		this.name=name;
		this.code=code;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}
	 
	 
}
