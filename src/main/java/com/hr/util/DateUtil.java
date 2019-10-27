package com.hr.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	/**
	 * 获得当前日期
	 * @return 返回时间格式：yyyy-MM-dd
	 */
	public static String getCurrentDate() {
		try {
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date dt=new Date();
			return sdf.format(dt);
		}catch(Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * 获得当前月
	 * @return 返回时间格式：yyyy-MM
	 */
	public static String getCurrentMonth() {
		try {
			DateFormat sdf = new SimpleDateFormat("yyyy-MM");
			Date dt=new Date();
			return sdf.format(dt);
		}catch(Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static String toFormateStr(String str) throws ParseException {
		 DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		 Date d= sdf.parse(str);
		 return sdf.format(d);
	}
	
	public static String DateToStrYY(Date d) throws ParseException {
		 DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		 return sdf.format(d);
	}
	
	public static String DateToStr(Date d) throws ParseException {
		 DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		 return sdf.format(d);
	}
	public static Date StrToDate(String d) throws ParseException {
		 DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		 return sdf.parse(d);
	}
	
	public static Date StrToDateYY(String d) throws ParseException {
		 DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		 return sdf.parse(d);
	}
	
	public static String getDateNum() {
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");//设置日期格式
		String no = LocalDateTime.now().format(fmt);
		return no;
	}
	
	public static String getOrderTime(Date date) {
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyMMddHHmmss");//设置日期格式
		String no = LocalDateTime.now().format(fmt);
		return no;
	}
	
	public static String ttToStr(Timestamp timestamp) {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		String date=sdf.format(timestamp);
		return date;
	}
	
	public static String  getYearByNow(Date date) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date); 
		ca.add(Calendar.YEAR, -1); // 年份减1  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		return sdf.format(ca);
	}
	
	/**
	 * 两个时间天数差
	 * @param beginTime
	 * @param endTime
	 * @return
	 * @throws ParseException
	 */
	public static int betweenTime(String beginTime,String endTime) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		String fromDate = sdf.format(sdf.parse(beginTime));
		String toDate = sdf.format(sdf.parse(endTime));
		long from = sdf.parse(fromDate).getTime();
		long to = sdf.parse(toDate).getTime();
		
		int days = (int) ((to - from)/(1000 * 60 * 60 * 24));
		return days;
	}
	
	/**
	 * 获取两个时间的分钟差
	 * @param beginTime
	 * @param endTime
	 * @return
	 * @throws ParseException
	 */
   public static int betweenMin(String beginTime,String endTime) throws ParseException {
	   DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	   long from = sdf.parse(beginTime).getTime();
	   long to = sdf.parse(endTime).getTime();
	   int minutes = (int) ((to - from)/(1000 * 60));
	   return minutes;
   }
	
   /**
    * 时间比大小
    * 如果dateTime1大于dateTime2,则返回1;
	* 如果dateTime1等于dateTime2,则返回0;
	* 如果dateTime1小于dateTime2,则返回-1;
    * @param beginDate
    * @param endDate
    * @return
 * @throws ParseException 
    */
  public static int compareDate(String beginDate,String endDate) throws ParseException {
	  	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date dateTime1 = dateFormat.parse(beginDate);
		Date dateTime2 = dateFormat.parse(endDate);
		
		return dateTime1.compareTo(dateTime2); 
  }
  
  public static Date getDateAddDays(Date date, int add_days) {
	  Calendar time = Calendar.getInstance();
      time.setTime(date);
      time.add(5, add_days);
      return time.getTime();

  }
	
}
