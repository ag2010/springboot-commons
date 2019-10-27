package com.hr.util;

import org.springframework.util.StringUtils;

/**
 * @author Administrator
 * 权限计算帮助类
 */
public class AuthUtil {


		
	/**
	 * 判断是否具有指定编码的权限
	 * @param menuCodes
	 * @param menuCode
	 * @return
	 */
	public static boolean hasAuth(String menuCodes,String menuCode){
		if(StringUtils.isEmpty(menuCodes) || StringUtils.isEmpty(menuCode)){
			return false;
		}
		if(menuCodes.contains(menuCode)){
			return true;
		}else{
			return false;
		}
	}
	
}
