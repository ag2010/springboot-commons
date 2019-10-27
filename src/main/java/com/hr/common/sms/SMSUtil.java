package com.hr.common.sms;

import java.util.HashMap;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

import com.hr.common.http.HttpClientUtils;
import com.hr.util.ConfigUtil;

public class SMSUtil {
	
	
	/**
	 * 通用生成6位数短信验证码
	 * @return
	 */
	public static String createCommonSMSCode() {
		try {
			String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);//生成短信验证码
			return verifyCode;
		}catch(Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	
	public static void main(String[] args) {
		SmsSendResponse rrspon=sendSMS("15271843783","短信验证码测试");
		System.out.println(rrspon.getCode());
	}
	
	public static SmsSendResponse sendSMS(String mobile,String msg) {
		SmsSendResponse smsSingleResponse=new SmsSendResponse();
		try {
			if(ConfigUtil.SMS_SEND_SMS) {
				HashMap<String,String> params=new HashMap<String,String>();
				params.put("account", ConfigUtil.SMS_ACCOUNT);
				params.put("pswd", ConfigUtil.SMS_PSWD);
				params.put("mobile", mobile);
				params.put("msg", msg);
				params.put("needstatus", "true");
				params.put("extno", "");
				String smsResponse=HttpClientUtils.createHttpInstance().post(ConfigUtil.SMS_URL, params);
				if(StringUtils.isEmpty(smsResponse)) {
					smsSingleResponse.setCode("-1");
					smsSingleResponse.setErrorMsg("短信发送失败！");
					return smsSingleResponse;
				}
				
				String[] smsRsArray=smsResponse.split(",");
				
				if(smsRsArray==null || smsRsArray.length<2) {
					smsSingleResponse.setCode("-1");
					smsSingleResponse.setErrorMsg("短信发送失败！");
					return smsSingleResponse;
				}
				
				String[] rsArray=smsRsArray[1].split("\n");
				if(rsArray==null || rsArray.length<1) {
					smsSingleResponse.setCode("-1");
					smsSingleResponse.setErrorMsg("短信发送失败！");
					return smsSingleResponse;
				}
				int ecode=-500;
				try {
					ecode=Integer.parseInt(rsArray[0]);
				}catch(Exception e) {
					e.printStackTrace();
				}
				smsSingleResponse.setCode(ecode+"");
				if(ecode==0) {
					smsSingleResponse.setErrorMsg("短信发送成功!");
				}else {
					smsSingleResponse.setErrorMsg("短信发送失败，错误码："+ecode);
				}
			}else {
				smsSingleResponse.setCode("0");
				smsSingleResponse.setErrorMsg("模拟发送短信，实际未发送。");
			}
		}catch(Exception e) {
			smsSingleResponse.setCode("-1");
			e.printStackTrace();
		}
		return smsSingleResponse;
	}

}
