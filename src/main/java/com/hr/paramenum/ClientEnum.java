package com.hr.paramenum;

import java.util.HashMap;

/**
 * 请求终端类型枚举
 * @author Administrator
 *
 */

public enum ClientEnum {
	
	
	ANDROID("安卓App","android"),
	IPHONE("苹果手机App","iphone"),
	WAP("微信H5","wap"),
	WECHATMIN("微信小程序","wechatmin"),
	WEB("web端","web"),
	OTHER("其他","other");
	
	
	
	private String name;
	private String code;
	private static HashMap<String,ClientEnum> clientMap=new HashMap<String,ClientEnum>();
	
	static {
		try {
			clientMap.put(ANDROID.getCode(), ANDROID);
			clientMap.put(IPHONE.getCode(), IPHONE);
			clientMap.put(WAP.getCode(), WAP);
			clientMap.put(WECHATMIN.getCode(), WECHATMIN);
			clientMap.put(WEB.getCode(), WEB);
			clientMap.put(OTHER.getCode(), OTHER);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ClientEnum getClientEnumByCode(String code){
		ClientEnum clientEnum=clientMap.get(code);
		if(clientEnum!=null) {
			return clientEnum;
		}else {
			return ClientEnum.OTHER;
		}
	}
	
	ClientEnum(String name,String code) {
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
