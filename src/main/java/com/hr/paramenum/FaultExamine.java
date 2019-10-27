package com.hr.paramenum;

public enum FaultExamine {
	
	WAITE_CHECK("待审核","1"),
	PASS_CHECK("审核通过","2"),
	FAIL_CHCK("审核失败","3");
	
	private String name;
	private String code;
	
	
	
	FaultExamine(String name,String code) {
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
