package com.hr.paramenum;

public enum DiscountEnum {
	
	ALREADY_USER("已使用",1),
	NOT_USED("未使用",2),
	Expired("已过期",3);
	
	
	private String name;
	private Integer code;
	
	DiscountEnum(String name,Integer code){
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
