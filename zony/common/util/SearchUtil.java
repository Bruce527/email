// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SearchUtil.java

package com.zony.common.util;

import java.util.*;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

public class SearchUtil
{

	public SearchUtil()
	{
	}

	public static String getSearchSqlByParamObj(Object searchParamObj)
	{
		String searchSql = "1=1";
		try
		{
			Map propMap = PropertyUtils.describe(searchParamObj);
			for (Iterator iterator = propMap.keySet().iterator(); iterator.hasNext();)
			{
				String propName = (String)iterator.next();
				if (!"class".equals(propName))
				{
					Object val = PropertyUtils.getProperty(searchParamObj, propName);
					if (!(val instanceof Class) && val != null)
						if ((val instanceof String) && !StringUtils.isEmpty(val.toString()))
							searchSql = (new StringBuilder(String.valueOf(searchSql))).append(" and ").append(propName).append(" like '%").append(val).append("%'").toString();
						else
						if (val instanceof Integer)
							searchSql = (new StringBuilder(String.valueOf(searchSql))).append(" and ").append(propName).append("=").append(val).toString();
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return searchSql;
	}
}
