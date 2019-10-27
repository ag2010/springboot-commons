package com.hr.paramenum;

public enum SmsTypeEnum {
	
	LOGIN_SMS("登录",1),
	RESETPWD_SMS("重置密码",2),
	BINDING_MOBILE("绑定手机号码",3);
	
	
	private String name;
	private int type;
	
	SmsTypeEnum(String name,int type) {
		this.name=name;
		this.type=type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	
	
	
}
