// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Globals.java

package com.zony.common.util;

import java.io.File;
import java.text.DateFormat;
import java.util.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;

// Referenced classes of package com.zony.common.util:
//			XMLProperties

public class Globals
{

	private static final String CONFIG_FILENAME = "zony_config.xml";
	static String sysRootDir = null;
	public static int rowsPerPage = 0;
	public static XMLProperties properties = null;
	private static Locale locale = null;
	private static TimeZone timeZone = null;
	private static DateFormat dateFormat = null;
	private static DateFormat dateTimeFormat = null;
	public static ClassPathXmlApplicationContext appContext;

	public Globals()
	{
	}

	public static Locale getLocale()
	{
		if (locale == null)
			loadLocale();
		return locale;
	}

	public static void setLocale(Locale newLocale)
	{
		locale = newLocale;
		setProperty("locale.country", locale.getCountry());
		setProperty("locale.language", locale.getLanguage());
		dateFormat = DateFormat.getDateInstance(2, locale);
		dateTimeFormat = DateFormat.getDateTimeInstance(2, 2, locale);
		dateFormat.setTimeZone(timeZone);
		dateTimeFormat.setTimeZone(timeZone);
	}

	public static TimeZone getTimeZone()
	{
		if (timeZone == null)
			loadLocale();
		return timeZone;
	}

	public static void setSysRootDir(String rootDir)
	{
		sysRootDir = rootDir;
	}

	public static String formatDate(Date date)
	{
		if (dateFormat == null)
			loadLocale();
		return dateFormat.format(date);
	}

	public static String formatDateTime(Date date)
	{
		if (dateTimeFormat == null)
			loadLocale();
		return dateTimeFormat.format(date);
	}

	public static String getsysRootDir()
	{
		if (sysRootDir == null)
			sysRootDir = System.getProperty("sysRootDir");
		return sysRootDir;
	}

	public static int getRowsPerPage()
	{
		rowsPerPage = Integer.parseInt(getProperty("pagescroll.rowsperpage"));
		return rowsPerPage;
	}

	public static boolean isHomeReadable()
	{
		return (new File(getsysRootDir())).canRead();
	}

	public static boolean isHomeWritable()
	{
		return (new File(getsysRootDir())).canWrite();
	}

	public static synchronized String getProperty(String name)
	{
		loadProperties();
		return properties.getProperty(name);
	}

	public static synchronized void setProperty(String name, String value)
	{
		loadProperties();
		properties.setProperty(name, value);
	}

	public static void deleteProperty(String name)
	{
		loadProperties();
		properties.deleteProperty(name);
	}

	public static synchronized void loadProperties()
	{
		if (sysRootDir == null)
			sysRootDir = getsysRootDir();
		properties = new XMLProperties((new StringBuilder(String.valueOf(sysRootDir))).append(File.separator).append("WEB-INF").append(File.separator).append("zony_config.xml").toString());
	}

	private static synchronized void loadLocale()
	{
		String language = getProperty("locale.language");
		if (language == null)
			language = "";
		String country = getProperty("locale.country");
		if (country == null)
			country = "";
		if (language.equals("") && country.equals(""))
			locale = Locale.US;
		else
			locale = new Locale(language, country);
		String timeZoneID = getProperty("locale.timeZone");
		if (timeZoneID == null)
			timeZone = TimeZone.getDefault();
		else
			timeZone = TimeZone.getTimeZone(timeZoneID);
		dateFormat = DateFormat.getDateInstance(2, locale);
		dateTimeFormat = DateFormat.getDateTimeInstance(2, 2, locale);
		dateFormat.setTimeZone(timeZone);
		dateTimeFormat.setTimeZone(timeZone);
	}

	public static String getTableName(Class cls)
	{
		return cls.getSimpleName();
	}

}
