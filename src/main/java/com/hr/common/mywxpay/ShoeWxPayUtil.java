package com.hr.common.mywxpay;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.github.wxpay.sdk.WXPayUtil;


public class ShoeWxPayUtil {
	
	/**
     * 从request的inputStream中获取参数
     * @param request
     * @return
     * @throws Exception
     */

	  public static Map<String, String> getNotifyParameter(HttpServletRequest request) throws Exception {
	        InputStream inputStream = request.getInputStream();
	        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
	        byte[] buffer = new byte[1024];
	        int length = 0;
	        while ((length = inputStream.read(buffer)) != -1) {
	            outSteam.write(buffer, 0, length);
	        }
	        outSteam.close();
	        inputStream.close();

	        // 获取微信调用我们notify_url的返回信息
	        String resultXml = new String(outSteam.toByteArray(), "utf-8");
	        Map<String, String> notifyMap = WXPayUtil.xmlToMap(resultXml);

	        return notifyMap;
	    }
	  
	  /**
	   * 对象转map
	   * @param obj
	   * @return
	   * @throws Exception
	   */
	  public static Map<String, String> objectToMap(Object obj) throws Exception {    
	        if(obj == null){    
	            return null;    
	        }   
	  
	        Map<String, String> map = new HashMap<String, String>();    
	  
	        Field[] declaredFields = obj.getClass().getDeclaredFields();    
	        for (Field field : declaredFields) {    
	            field.setAccessible(true);  
	            map.put(field.getName(), String.valueOf(field.get(obj)));  
	        }    
	  
	        return map;  
	    }   


	  

	  
	 
}
