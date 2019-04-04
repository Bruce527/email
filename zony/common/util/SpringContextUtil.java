// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SpringContextUtil.java

package com.zony.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextUtil
	implements ApplicationContextAware
{

	private static ApplicationContext applicationContext;

	public SpringContextUtil()
	{
	}

	public void setApplicationContext(ApplicationContext applicationContext)
		throws BeansException
	{
		applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext()
	{
		return applicationContext;
	}

	public static Object getBean(String name)
	{
		assertContextInjected();
		return applicationContext.getBean(name);
	}

	public static boolean containsBean(String beanName)
	{
		return applicationContext.containsBean(beanName);
	}

	private static void assertContextInjected()
	{
		if (applicationContext == null)
			throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextHolder");
		else
			return;
	}
}
