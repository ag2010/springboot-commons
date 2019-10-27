package com.hr.paramenum;


/**
 * 分享类型
 * @author x1c
 *
 */
public enum ShareTypeEnum {

	//微信公众号推荐
	WX_PUBLIC_NUMBER("http://wx.gxdaojia.com/mine/login.html?commend=","1");
	
	private String name;
	private String code;
	
	ShareTypeEnum(String name,String code) {
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
