package com.lgmn.adminapi.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {

    //1、获取当月第一天
    public static String monthFirstDate(){
//规定返回日期格式
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        Calendar calendar=Calendar.getInstance();
        Date theDate=calendar.getTime();
        GregorianCalendar gcLast=(GregorianCalendar)Calendar.getInstance();
        gcLast.setTime(theDate);
//设置为第一天
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first=sf.format(gcLast.getTime());
//打印本月第一天
        System.out.println(day_first);
        return day_first;
    }
    //2、获取当月最后一天
    public static String monthLastDate(){
//获取Calendar
        Calendar calendar=Calendar.getInstance();
//设置日期为本月最大日期
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
//设置日期格式
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        String ss=sf.format(calendar.getTime());
        System.out.println(ss);
        return ss;
    }
}