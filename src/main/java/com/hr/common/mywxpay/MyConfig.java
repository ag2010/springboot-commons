package com.hr.common.mywxpay;

import java.io.InputStream;

import com.github.wxpay.sdk.WXPayConfig;

public class MyConfig implements WXPayConfig{

	private byte[] certData;
	private String myKey;
	private String appid;
	private String machid;
	private String key;
	
	
	public MyConfig(String appid,String machid,String key) {
		super();
		this.appid=appid;
		this.machid=machid;
		this.key=key;
	}
	
	public MyConfig() {
		super();
	}
	
	@Override
	public String getAppID() {
		return appid;
	}

	@Override
	public String getMchID() {
		return machid;
	}

	@Override
	public String getKey() {
		return key;
	}
	
	@Override
	public InputStream getCertStream() {
//		 ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
//	        return certBis;
	        return null;
	}

	@Override
	public int getHttpConnectTimeoutMs() {
		   return 8000;
	}

	@Override
	public int getHttpReadTimeoutMs() {
		 return 10000;
	}
	
	

}


