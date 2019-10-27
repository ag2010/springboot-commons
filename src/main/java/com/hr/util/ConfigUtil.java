package com.hr.util;

public class ConfigUtil {
	
	// SMS短信发送配置

	public static final boolean SMS_SEND_SMS=true;//是否发送短信
	// VIP短信地址
	public static final String SMS_URL="https://sapi.253.com/msg/HttpBatchSendSM";
	// 用户平台账号
	public static final String SMS_ACCOUNT = "jbr168_gxdj";
	// 用户平台密码
	public static final String SMS_PSWD = "Gxdj123456";


	
	
	//Wechat微信相关配置
	
	//获取微信code
	public static final String WECHAT_GET_WEICHAT_CODE_URL="https://open.weixin.qq.com/connect/oauth2/authorize";
	//获取微信oauth2 access_token
	public static final String WECHAT_ACCESS_TOKEN_URL="https://api.weixin.qq.com/sns/oauth2/access_token";
	//刷新微信access_token
	public static final String WECHAT_REFRESH_ACCESS_TOKEN_URL="https://api.weixin.qq.com/sns/oauth2/refresh_token";
	
	//获取 ACCESS_TOKEN
	public static final String WECHAT_GET_ACCESS_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token";

	//获取微信jsapi_ticket
	public static final String WECHAT_GET_JSAPI_TICKET_URL="https://api.weixin.qq.com/cgi-bin/ticket/getticket";
	//获取微信用户信息
	public static final String WECHAT_GET_USER_INFO="https://api.weixin.qq.com/sns/userinfo";
	
	//微信公众号
	public static final String WECHAT_APP_ID="wxdcd5ec1855a01de7";
	public static final String WECHAT_SECRET_ID="275cc2734866bab2d674b8fa93bdd203";
	

	
	//微信小程序
	public static final String WECHAT_APP_ID_MIN="wxfa52985b952dd8c5";
	public static final String WECHAT_SECRET_ID_MIN="01ac8882aa9127f6d292bcb7b2f5736e";
	
	//微信小程序获得OpenID
	public static final String WECHAT_MIN_GETOPEN_ID="https://api.weixin.qq.com/sns/jscode2session";
	
	//微信密钥
	public  static final String WECHAT_KEY="fad62090cd27f094d534be11c25fda24";
	
	//商户号
	public  static final String WECHAT_MCH_ID="1504360501";
	
	
	public static final String APP_WX_APP_ID="";//APP的APPID
	public static final String APP_WX_APP_SECRET_ID="";//APP的SecretId
	public static final String APP_WX_MCH_ID="";//APP的商户号
	public static final String APP_WX_PAY_KEY="";//APP的商户支付密钥
	
	
}
