package com.hr.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class TokenUtil {
	
	/**
	 * 生成用户Token
	 * @return
	 */
	public static String createToken(int uid,String clientType,Timestamp last_login_time) {
		String token="";
		try {
			long time=0L;
			if(last_login_time!=null) {
				time=last_login_time.getTime();
			}
			String tokenTemp=uid+""+clientType+""+time;
			token=MD5Util.MD5(tokenTemp);
		}catch(Exception e) {
			token=createUUID();
			e.printStackTrace();
		}
		
		return token;
	}
	
	/**
	 * 生成UUID
	 * @return
	 */
	public static String createUUID(){
		String uuid="";
		try {
		    uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
		}catch(Exception e) {
			uuid=System.currentTimeMillis()+""+new Random().nextInt(10000);
			e.printStackTrace();
		}
		return uuid;
	}
	
	/**
	 * 生成订单号
	 * @return
	 */
	public static String createOrderNumber() {
		String orderNumber="";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
			orderNumber=sdf.format(new Date(System.currentTimeMillis()))+""+new Random().nextInt(10000);
		}catch(Exception e) {
			orderNumber=System.currentTimeMillis()+""+new Random().nextInt(10000);
			e.printStackTrace();
		}
		return orderNumber;
	}

}
