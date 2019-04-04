// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   StringUtil.java

package com.zony.common.util;

import java.io.PrintStream;
import java.util.*;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

// Referenced classes of package com.zony.common.util:
//			QueryTypeUtil

public class StringUtil
{

	public StringUtil()
	{
	}

	public static String getQueryStrWithQuote(List idList)
	{
		return getQueryStr(idList, true);
	}

	public static String getQueryStrNoQuote(List idList)
	{
		return getQueryStr(idList, false);
	}

	public static String getQueryStr(List idList, boolean includeQuote)
	{
		String queryStr = "";
		if (idList != null && idList.size() > 0)
		{
			if (includeQuote)
			{
				for (Iterator iterator = idList.iterator(); iterator.hasNext();)
				{
					Object obj = iterator.next();
					queryStr = (new StringBuilder(String.valueOf(queryStr))).append("'").append(obj).append("',").toString();
				}

			} else
			{
				for (Iterator iterator1 = idList.iterator(); iterator1.hasNext();)
				{
					Object obj = iterator1.next();
					queryStr = (new StringBuilder(String.valueOf(queryStr))).append(obj).append(",").toString();
				}

			}
			queryStr = queryStr.substring(0, queryStr.length() - 1);
		}
		return queryStr;
	}

	public static Object cleanQueryField(Object obj)
	{
		Object packageNameValue = PropertyUtils.getProperty(obj, "packageName");
		if ("全部".equals(packageNameValue))
			PropertyUtils.setProperty(obj, "packageName", null);
		Object projectNameValue = PropertyUtils.getProperty(obj, "projectName");
		if ("全部".equals(projectNameValue))
			PropertyUtils.setProperty(obj, "projectName", null);
		return obj;
		Exception e;
		e;
		e.printStackTrace();
		return null;
	}

	public static String createWhereStr(Object obj)
	{
		return createWhereStr(obj, null);
	}

	public static String createWhereStr(Object obj, String alias)
	{
		String hql = "";
		try
		{
			if (StringUtils.isEmpty(alias))
				alias = "";
			else
				alias = (new StringBuilder(String.valueOf(alias))).append(".").toString();
			List fieldList = QueryTypeUtil.get(obj.getClass().getSimpleName());
			Map valueMap = PropertyUtils.describe(obj);
			for (Iterator iterator = valueMap.entrySet().iterator(); iterator.hasNext();)
			{
				java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
				String fieldName = (String)entry.getKey();
				Object fieldValue = entry.getValue();
				if (!"class".equals(fieldName) && fieldValue != null && !"".equals(fieldValue))
					if (fieldValue instanceof String)
					{
						if (fieldList != null && fieldList.contains(fieldName))
							hql = (new StringBuilder(String.valueOf(hql))).append(" and ").append(alias).append(fieldName).append(" = '").append(fieldValue).append("'").toString();
						else
							hql = (new StringBuilder(String.valueOf(hql))).append(" and ").append(alias).append(fieldName).append(" like '%").append(fieldValue).append("%'").toString();
					} else
					{
						hql = (new StringBuilder(String.valueOf(hql))).append(" and ").append(alias).append(fieldName).append(" =").append(fieldValue).toString();
					}
			}

			if (!StringUtils.isEmpty(hql))
				hql = hql.substring(4);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return hql;
	}

	public static String getString(List list)
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < list.size(); i++)
		{
			sb.append(list.get(i));
			if (i != list.size() - 1)
				sb.append("|");
		}

		return sb.toString();
	}

	public static String getUUID(int length)
	{
		String uuidInfo;
		UUID uuid;
		for (uuidInfo = ""; uuidInfo.length() < length; uuidInfo = (new StringBuilder(String.valueOf(uuidInfo))).append(uuid.toString()).toString())
			uuid = UUID.randomUUID();

		uuidInfo = uuidInfo.substring(0, length);
		return uuidInfo;
	}

	public static void main(String args[])
		throws Exception
	{
		String str = "f80e9415-07b4-4c80-a133-7f066cf8de5d1b2bbf4a-383e-42d4-b00f-9519bb52c318b359f121-8be4-4f27-85e4-70821bada06f3bd97695-f1f4-4d0f-a";
		System.out.println("f80e9415-07b4-4c80-a133-7f066cf8de5d1b2bbf4a-383e-42d4-b00f-9519bb52c318b359f121-8be4-4f27-85e4-70821bada06f3bd97695-f1f4-4d0f-a".length());
	}
}
