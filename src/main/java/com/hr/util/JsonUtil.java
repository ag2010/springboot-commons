package com.hr.util;

import java.util.List;

import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class JsonUtil {
	
	
	/**
	 * Object 转 Json 串
	 * @param obj
	 * @return
	 */
	public static String objectToJson(Object obj){
		try{
			if(obj==null){
				return "";
			}
			return new Gson().toJson(obj);
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
		
	}
	
	
	/**
	 * JSON字符串转换成对应的对象
	 * @param jsons
	 * @param clazz
	 * @return T
	 */
	public static <T> T jsonToObject(String jsons,Class<T> clazz){
		try{
			if(StringUtils.isEmpty(jsons)){
				return null;
			}
			return new Gson().fromJson(jsons,clazz);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * JSON字符串转换成对应的List泛型
	 * @param jsons
	 * @param clazz
	 * @return List
	 * @throws Exception
	 */
	public static <T> List<T>  jsonToList(String jsons,TypeToken clazzType){
		try{
			if(StringUtils.isEmpty(jsons)){
				return null;
			}
	        return new Gson().fromJson(jsons, clazzType.getType());
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}

	}

	
}
