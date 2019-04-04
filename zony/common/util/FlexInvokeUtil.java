// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FlexInvokeUtil.java

package com.zony.common.util;

import flex.messaging.io.ArrayCollection;
import flex.messaging.io.amf.ASObject;
import java.util.*;
import org.apache.commons.beanutils.BeanUtils;

public class FlexInvokeUtil
{

	public FlexInvokeUtil()
	{
	}

	public static Object getFormAsObj(Object obj, ASObject asObj)
		throws Exception
	{
		if (asObj != null)
		{
			for (Iterator it = asObj.keySet().iterator(); it.hasNext();)
			{
				String key = (String)it.next();
				Object value = asObj.get(key);
				try
				{
					setValue(obj, key, value);
				}
				catch (Exception exception) { }
			}

		}
		return obj;
	}

	public static Object getFormAsObj(Class entityClass, ASObject asObj)
		throws Exception
	{
		if (asObj != null)
		{
			Object newObj = entityClass.newInstance();
			return getFormAsObj(newObj, asObj);
		} else
		{
			return null;
		}
	}

	public static List getFormAsList(Class entityClass, ArrayCollection asList)
		throws Exception
	{
		List list = new ArrayList();
		if (asList != null)
		{
			for (int i = 0; i < asList.size(); i++)
			{
				ASObject asObj = (ASObject)asList.get(i);
				Object newObj = getFormAsObj(entityClass, asObj);
				list.add(newObj);
			}

		}
		return list;
	}

	public static List getFormAsStringList(ArrayCollection asList)
		throws Exception
	{
		List list = new ArrayList();
		if (asList != null)
		{
			for (int i = 0; i < asList.size(); i++)
			{
				String str = (String)asList.get(i);
				list.add(str);
			}

		}
		return list;
	}

	public static List getFormAsIntList(ArrayCollection asList)
		throws Exception
	{
		List list = new ArrayList();
		if (asList != null)
		{
			for (int i = 0; i < asList.size(); i++)
			{
				Integer num = Integer.valueOf(Integer.parseInt((new StringBuilder()).append(asList.get(i)).toString()));
				list.add(num);
			}

		}
		return list;
	}

	public static List getFormAsLongList(ArrayCollection asList)
		throws Exception
	{
		List list = new ArrayList();
		if (asList != null)
		{
			for (int i = 0; i < asList.size(); i++)
			{
				Long num = Long.valueOf(Long.parseLong((new StringBuilder()).append(asList.get(i)).toString()));
				list.add(num);
			}

		}
		return list;
	}

	private static void setValue(Object obj, String key, Object value)
		throws Exception
	{
		BeanUtils.setProperty(obj, key, value);
	}
}
