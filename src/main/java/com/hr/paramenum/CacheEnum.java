package com.hr.paramenum;

/**
 * 缓存类型枚举
 * @author Administrator
 *
 */
public enum CacheEnum {
	
	CACHE_CUSTOMER("会员缓存","Customer"),
	CACHE_WECHAT("微信全局缓存","Wechat"),
	CACHE_CUSTOMER_TEMP("会员临时信息缓存","CustomerTemp"),
	CACHE_SMS_CODE("短信验证码缓存","SmsCode");
	
	
	private String groupName;
	private String groupCode;
	
	
	CacheEnum(String groupName,String groupCode) {
		this.groupName=groupName;
		this.groupCode=groupCode;
	}


	public String getGroupName() {
		return groupName;
	}


	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}


	public String getGroupCode() {
		return groupCode;
	}


	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	

}
