// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EmailTaskDao.java

package com.zony.app.dao;

import com.zony.common.dao.CommonSpringDAOImpl;
import java.util.List;
import java.util.Map;

public class EmailTaskDao extends CommonSpringDAOImpl
{

	public EmailTaskDao()
	{
	}

	public List getNoReceiptEmailTask(int hour)
	{
		return null;
	}

	public void completeModel(Object obj, Map map)
	{
	}

	public List getTaskIDsForSend()
	{
		String hql = "select id from EmailTask where taskStatus='1' and taskType='1'";
		List idList = getField(hql, new Object[0]);
		return idList;
	}
}
