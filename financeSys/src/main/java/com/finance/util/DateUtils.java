package com.finance.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateUtils
{
  private static final String pattern1 = "yyyy-MM-dd";
  private static final String pattern2 = "yyyy-MM-dd HH:mm";
  private static final String pattern3 = "yyyy-MM-dd HH:mm:ss";
  private static final String pattern4 = "yyyyMMddHHmmss";

  public static String dateToStr(Date date)
  {
    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
    String str = fmt.format(date);
    return str;
  }

  public static String hourToStr(Date date)
  {
    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    String str = fmt.format(date);
    return str;
  }

  public static String secondsToStr(Date date)
  {
    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String str = fmt.format(date);
    return str;
  }

  public static String dateToStr(Date date, String pattern)
  {
    SimpleDateFormat fmt = new SimpleDateFormat(pattern);
    String str = fmt.format(date);
    return str;
  }

  public static String dateToStrNo(Date date)
  {
    SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
    String str = null;
    try {
      str = fmt.format(date);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return str;
  }

  public static Date strToDate(String str)
  {
    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
    Date date = null;
    try {
      date = fmt.parse(str);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return date;
  }

  public static Date strTohour(String str)
  {
    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    Date date = null;
    try {
      date = fmt.parse(str);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return date;
  }

  public static Date strToSeconds(String str)
  {
    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = null;
    try {
      date = fmt.parse(str);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return date;
  }

  public static Date strToDate(String str, String pattern)
  {
    SimpleDateFormat fmt = new SimpleDateFormat(pattern);
    Date date = null;
    try {
      date = fmt.parse(str);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return date;
  }
  
  /**
   * 比较两个日期是否为同一天
   *
   * @author Liugang
   * @time 2017-3-2
   * @return
 * @throws ParseException 
   */
  public static boolean compareIsSameDay(Date first, Date second) throws ParseException{
	  DateFormat fmt = new SimpleDateFormat(pattern1);
	  Date temp1 = fmt.parse(fmt.format(first));
	  Date temp2 = fmt.parse(fmt.format(second));
	  return temp1.getTime() == temp2.getTime();
  }
  
  /**
   * 获取从给定参数的年份到当年年份的集合
   *
   * @author Liugang
   * @time 2017-3-3
   * @param year
   * @return
   */
  public static List<String> arrayFirstYear2Now(String year){
	  List<String> years = new ArrayList<String>();
	  DateFormat fmt = new SimpleDateFormat("yyyy");
	  String now = fmt.format(System.currentTimeMillis());
	  if(Integer.valueOf(now) > Integer.valueOf(year)){
		  for(int i = Integer.valueOf(year); i <= Integer.valueOf(now); i++){
			  years.add(String.valueOf(i));
		  }
	  } else {
		  years.add(now);
	  }
	  return years;
  }
  
  /**
   * 获取当前年份往前推给定年数的集合
   *
   * @author Liugang
   * @time 2017-3-3
   * @param count
   * @return
   */
  public static List<String> arrayBeforeYearOfCount(int count){
	  List<String> years = new ArrayList<String>();
	  DateFormat fmt = new SimpleDateFormat("yyyy");
	  String now = fmt.format(System.currentTimeMillis());
	  if(count > 0){
		  for(int i = count; i >= 0; i--){
			  int year = Integer.valueOf(now) - i;
			  years.add(String.valueOf(year));
		  }
	  } else {
		  years.add(now);
	  }
	  return years;
  }
  
  public static void main(String[] args) {
	try {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date first = fmt.parse("2017-03-02 13:14:15");
		Date second = fmt.parse("2017-03-01 15:07:09");
		System.out.println(compareIsSameDay(first, second));
	} catch (ParseException e) {
		e.printStackTrace();
	}
  }
}
