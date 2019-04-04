// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LogEventDao.java

package com.zony.app.dao;

import com.zony.app.model.LogEventModel;
import com.zony.common.dao.CommonSpringDAOImpl;
import java.util.List;
import java.util.Map;

public class LogEventDao extends CommonSpringDAOImpl
{

	public LogEventDao()
	{
	}

	public void completeModel(LogEventModel logeventmodel, Map map)
	{
	}

	public List getAllLogEvent()
	{
		String hql = "From LogEvent order by sortID";
		return getEntityList("From LogEvent order by sortID", new Object[0]);
	}

	public volatile void completeModel(Object obj, Map map)
	{
		completeModel((LogEventModel)obj, map);
	}
}
