package com.hr.util;

import java.text.DecimalFormat;
import java.util.Random;

public class NumberUtil {
	
	
	/**
	 * 格式化保留1位小数
	 * @param number
	 * @return
	 */
	public static String doubleFormat_1(double number) {
		String result = "0.0";
		try {
			DecimalFormat df = new DecimalFormat("0.0");
			result=df.format(number);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 格式化保留2位小数
	 * @param number
	 * @return
	 */
	public static String doubleFormat_2(double number) {
		String result = "0.00";
		try {
			DecimalFormat df = new DecimalFormat("0.00");
			result=df.format(number);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	public static String genRandomNum() {

		      int  maxNum = 36;  
		      int i;  
		      int count = 0;  
		      char[] str = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',  
		        'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',  
		        'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };      
		      StringBuffer pwd = new StringBuffer("");  
		      Random r = new Random();  
		      while(count < 8){  
		       i = Math.abs(r.nextInt(maxNum));     
		       if (i >= 0 && i < str.length) {  
		        pwd.append(str[i]);  
		        count ++;  
		       }  
		      }  
		      return pwd.toString();  
		    
	}

}
