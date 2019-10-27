package com.hr.paramenum;

public enum BaseEnum {
	
	  YES("是","1"),
	  NO("否","0");
	
	private String name;
	private String code;
	
	
	
	BaseEnum(String name,String code) {
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
