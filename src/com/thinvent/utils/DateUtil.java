package com.thinvent.utils;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DateUtil {
	/**
	 * 默认日期格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
	   /**
     * 默认日期格式 yyyy-MM-dd
     */
	public static String STANDARD_FORMAT = "yyyy-MM-dd";
	   /**
     * 默认日期格式 yyyy.MM.dd
     */
	public static String YYMMDD_FORMAT = "yyyy.MM.dd";
	
	   /**
     * 默认日期格式 yyyy-MM-dd HH:mm
     */
    public static String YYMMDDHHMM_FORMAT = "yyyy-MM-dd HH:mm";
	
    public static String formatDate(String formatStyle, Date date)
    {
        String sDate;
        if(date != null)
        {
            SimpleDateFormat f = new SimpleDateFormat(formatStyle);
            sDate = f.format(date);
        }
        else
            sDate = "";
        return sDate;
    }

    public static Date parseDate(String formatStyle, String date) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat(formatStyle);
        return sdf.parse(date);
    }
    
	/**
	 * 格式化日期成为"yyyy-MM-dd HH:mm:ss"格式
	 * @param date 日期对象
	 * @return String 日期字符串
	 */
	public static String formatDate(Date date){
		String sDate;
		if(date != null)
		{
			SimpleDateFormat f = new SimpleDateFormat(DEFAULT_FORMAT);
			sDate = f.format(date);
		}
		else
			sDate = "";
		return sDate;
	}
	
	/**
	 * 格式化日期成为"yyyy-MM-dd"格式
	 * @param date 日期对象
	 * @return String 日期字符串
	 */
	public static String formatStandardDate(Date date){
		String sDate;
		if(date != null)
		{
			SimpleDateFormat f = new SimpleDateFormat(STANDARD_FORMAT);
			sDate = f.format(date);
		}
		else
			sDate = "";
		return sDate;
	}
	
	/**
	 * 将"yyyy-MM-dd"格式字符串转化为日期
	 * @param date 需要转化的日期字符串
	 * @return Date 日期对象
	 * @throws ParseException 
	 */
	public static Date parseStandardDate(String date) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(STANDARD_FORMAT);
		return sdf.parse(date);
	}
	
	/**
	 * 将"yyyy-MM-dd HH:mm:ss"格式字符串转化为日期
	 * @param date 需要转化的日期字符串
	 * @return Date 日期对象
	 * @throws ParseException 
	 */
	public static Date parseDate(String date) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT);
		return sdf.parse(date);
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
	

	 /**
     * 上月第一天
     * @return
     */
    public static String getFirstDayOfLastMonth() {
        //上个月第一天
        Date nowdate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        /* 设置为当前时间 */
        cal.setTime(nowdate);
        cal.add(Calendar.MONTH, -1);
        // 得到前一个月的第一天
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));        

        String day_first = sdf.format(cal.getTime());
        StringBuffer str = new StringBuffer().append(day_first).append(" 00:00:00");
        day_first = str.toString();
        return day_first;
    }

	 /**
     * 上月最后一天
     * @return
     */
    public static String getLastDayOfLastMonth() {
        //上个月第一天
        Date nowdate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        /* 设置为当前时间 */
        cal.setTime(nowdate);
        cal.add(Calendar.MONTH, -1);
        // 得到前一个月的第一天
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));       

        String day_last = sdf.format(cal.getTime());
        StringBuffer str = new StringBuffer().append(day_last).append(" 23:59:59");
        day_last = str.toString();
        return day_last;
    }
    
	 /**
     * 当月第一天
     * @return
     */
    public static String getFirstDay() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date theDate = calendar.getTime();
        
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(day_first).append(" 00:00:00");
        return str.toString();

    }	
	/**
     * 当月最后一天
     * @return
     */
    public static String getLastDay() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date theDate = calendar.getTime();
        String s = df.format(theDate);
        StringBuffer str = new StringBuffer().append(s).append(" 23:59:59");
        return str.toString();

    }
    
    /**
     * 获取某月的最后一天
     * @Title:getLastDayOfMonth
     * @Description:
     * @param:@param year
     * @param:@param month
     * @param:@return
     * @return:String
     * @throws
     */
    public static String getLastDayOfMonth(int year,int month)
    {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        StringBuffer lastDayOfMonth = new StringBuffer().append(sdf.format(cal.getTime())).append(" 23:59:59");
        
        return lastDayOfMonth.toString();
    }

    /**
     * 获取某月的第一天
     * @Title:getLastDayOfMonth
     * @Description:
     * @param:@param year
     * @param:@param month
     * @param:@return
     * @return:String
     * @throws
     */
    public static String getFirstDayOfMonth(int year,int month)
    {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //设置日历中月份的第一天
        cal.set(Calendar.DAY_OF_MONTH, 1);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        StringBuffer firstDayOfMonth = new StringBuffer().append(sdf.format(cal.getTime())).append(" 00:00:00");
        
        return firstDayOfMonth.toString();
    }
    
	public static int daysOfTwo(Date fDate, Date oDate) {

	       Calendar aCalendar = Calendar.getInstance();

	       aCalendar.setTime(fDate);

	       int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);

	       aCalendar.setTime(oDate);

	       int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
	       
	       return day2 - day1;
	    }
	
    public static List<Date> findDates(Date dBegin, Date dEnd) {
        List lDate = new ArrayList();
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间  
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间  
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后  
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量  
            lDate.add(calBegin.getTime());
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
        }
        return lDate;
    }

}
