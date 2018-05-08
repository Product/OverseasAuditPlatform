package com.tonik.util;



import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtil {
	/**
	 * 默认日期格式
	 */
	public static String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
	public static String STANDARD_FORMAT = "yyyy-MM-dd";

//	/**
//	 * 测试主方法
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		for(int i = 1951;i < 1960;i++){
//			System.out.println(formatDate(getYearFirst(i)));
//			System.out.println(formatDate(getYearLast(i)));
//		}
//		
//		System.out.println(formatDate(getCurrYearFirst()));
//		System.out.println(formatDate(getCurrYearLast()));
//
//	}
	
	/**
	 * 格式化日期
	 * @param date 日期对象
	 * @return String 日期字符串
	 */
	public static String formatDate(Date date){
		SimpleDateFormat f = new SimpleDateFormat(DEFAULT_FORMAT);
		String sDate = f.format(date);
		return sDate;
	}
	/**
	 * 格式化日期成为年月日格式
	 * @param date 日期对象
	 * @return String 日期字符串
	 */
	public static String formatStandardDate(Date date){
		SimpleDateFormat f = new SimpleDateFormat(STANDARD_FORMAT);
		String sDate = f.format(date);
		return sDate;
	}
	
	/**
	 * 获取当年自然年的第一天
	 * @param year
	 * @return
	 */
	public static Date getNatureYearFirst(){
		Calendar currCal=Calendar.getInstance();  
		int currentYear = currCal.get(Calendar.YEAR);
		return getYearFirst(currentYear);
	}
	
	/**
	 * 获取当年自然年的最后一天
	 * @param year
	 * @return
	 */
	public static Date getNatureYearLast(){
		Calendar currCal=Calendar.getInstance();  
		int currentYear = currCal.get(Calendar.YEAR);
		return getYearLast(currentYear);
	}
	
	/**
	 * 获取某自然年第一天日期
	 * @param year 年份
	 * @return Date
	 */
	public static Date getYearFirst(int year){
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date currYearFirst = calendar.getTime();
		return currYearFirst;
	}
	
	/**
	 * 获取某自然年最后一天日期
	 * @param year 年份
	 * @return Date
	 */
	public static Date getYearLast(int year){
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		Date currYearLast = calendar.getTime();
		
		return currYearLast;
	}
}
