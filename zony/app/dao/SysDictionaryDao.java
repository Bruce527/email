// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SysDictionaryDao.java

package com.zony.app.dao;

import com.zony.app.model.SysDictionaryModel;
import com.zony.common.dao.CommonSpringDAOImpl;
import java.util.List;
import java.util.Map;

public class SysDictionaryDao extends CommonSpringDAOImpl
{

	public SysDictionaryDao()
	{
	}

	public void completeModel(SysDictionaryModel sysdictionarymodel, Map map)
	{
	}

	public List getDicList()
	{
		String hql = "From SysDictionary order by typeCode,sortID";
		return getEntityList("From SysDictionary order by typeCode,sortID", new Object[0]);
	}

	public volatile void completeModel(Object obj, Map map)
	{
		completeModel((SysDictionaryModel)obj, map);
	}
}
