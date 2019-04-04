// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DateUtil.java

package com.zony.common.util;

import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil
{

	public static final String FORMATSTR = "yyyy-MM-dd HH:mm:ss";
	public static final String DATEFORMATSTR = "yyyy-MM-dd";

	public DateUtil()
	{
	}

	public static String getNow()
	{
		return getNow("yyyy-MM-dd HH:mm:ss");
	}

	public static String getToday()
	{
		String today = "00000000";
		String now = getNow();
		if (now != null && now.length() >= 10)
			today = now.substring(0, 8);
		return today;
	}

	public static String getNow(String formatstr)
	{
		String formatedatestr = "";
		try
		{
			SimpleDateFormat formatter = new SimpleDateFormat(formatstr);
			Date currentTime = new Date();
			formatedatestr = formatter.format(currentTime);
		}
		catch (Exception e)
		{
			System.out.println((new StringBuilder("FormateDate error:")).append(e).toString());
		}
		return formatedatestr;
	}

	public static String formatDate(Date d_date, String pattern)
	{
		String formatedatestr = "";
		try
		{
			SimpleDateFormat formatter = new SimpleDateFormat(pattern);
			formatedatestr = formatter.format(d_date);
		}
		catch (Exception e)
		{
			System.out.println((new StringBuilder("FormateDate error:")).append(e).toString());
		}
		return formatedatestr;
	}

	public static String dateToString(Date date)
	{
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	public static boolean before(String sdate1, String sdate2)
	{
		Date date1 = new Date(Integer.parseInt(sdate1.substring(0, 4)), Integer.parseInt(sdate1.substring(5, 7)), Integer.parseInt(sdate1.substring(8, 10)));
		Date date2 = new Date(Integer.parseInt(sdate2.substring(0, 4)), Integer.parseInt(sdate2.substring(5, 7)), Integer.parseInt(sdate2.substring(8, 10)));
		return date1.before(date2);
	}

	public static String getLastDate(long day)
	{
		long date_3_hm = System.currentTimeMillis() - 0x5265c00L * day;
		Date date_3_hm_date = new Date(date_3_hm);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String formatedatestr = formatter.format(date_3_hm_date);
		formatedatestr = formatter.format(date_3_hm_date);
		return formatedatestr;
	}

	public static String getSampleDate(String dateStr)
	{
		Date date = parseDate(dateStr, "yyyy-MM-dd HH:mm:ss");
		return formatDate(date, "yyyyMMdd");
	}

	public static long[] dateDiff(String startTime, String endTime, String format)
	{
		SimpleDateFormat sd = new SimpleDateFormat(format);
		long nd = 0x5265c00L;
		long nh = 0x36ee80L;
		long nm = 60000L;
		long ns = 1000L;
		long diffArr[] = new long[4];
		try
		{
			long diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
			long day = diff / 0x5265c00L;
			long hour = (diff % 0x5265c00L) / 0x36ee80L;
			long min = (diff % 0x5265c00L % 0x36ee80L) / 60000L;
			long sec = (diff % 0x5265c00L % 0x36ee80L % 60000L) / 1000L;
			diffArr[0] = day;
			diffArr[1] = hour;
			diffArr[2] = min;
			diffArr[3] = sec;
			System.out.println((new StringBuilder("时间相差：")).append(day).append("天").append(hour).append("小时").append(min).append("分钟").append(sec).append("秒。").toString());
		}
		catch (Exception exception) { }
		return diffArr;
	}

	public static Date parseDate(String datestr, String pattern)
	{
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Date date = null;
		try
		{
			date = format.parse(datestr);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		return date;
	}

	public static String getDate()
	{
		return getNow("yyyy-MM-dd");
	}

	public static String getSampleDate(String dateStr, String nowFormat, String getFormat)
	{
		Date date = parseDate(dateStr, nowFormat);
		return formatDate(date, getFormat);
	}

	public static String getBeforeDateByHour(int hour)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(11, calendar.get(11) - hour);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(calendar.getTime());
	}
}
