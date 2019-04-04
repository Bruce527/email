// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BaseDAO.java

package com.zony.common.dao;

import com.zony.common.util.ReflectionUtils;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.beanutils.BeanUtils;

public abstract class BaseDAO
{

	protected Class domainClass;
	protected Class modelClass;

	public BaseDAO()
	{
		domainClass = ReflectionUtils.getSuperClassGenricType(getClass(), 0);
		modelClass = ReflectionUtils.getSuperClassGenricType(getClass(), 1);
	}

	public abstract void completeModel(Object obj, Map map);

	public List convertFromDomainList(List domainList, Map paramObj)
		throws Exception
	{
		List modelList = new ArrayList();
		if (domainList != null)
		{
			Object modelObj;
			for (Iterator iterator = domainList.iterator(); iterator.hasNext(); modelList.add(modelObj))
			{
				Object domainObj = (Object)iterator.next();
				modelObj = convertFromDomain(domainObj, paramObj);
			}

		}
		return modelList;
	}

	public Object convertFromDomain(Object domainObj, Map paramObj)
		throws Exception
	{
		Object modelObj = modelClass.newInstance();
		BeanUtils.copyProperties(modelObj, domainObj);
		completeModel(modelObj, paramObj);
		return modelObj;
	}

	public String removeSelect(String hql)
	{
		int beginPos = hql.toLowerCase().indexOf("from");
		return hql.substring(beginPos);
	}

	public String removeOrders(String hql)
	{
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", 2);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		for (; m.find(); m.appendReplacement(sb, ""));
		m.appendTail(sb);
		return sb.toString();
	}
}
