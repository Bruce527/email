// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ImportLogDao.java

package com.zony.app.dao;

import com.zony.app.model.ImportLogModel;
import com.zony.common.dao.CommonSpringDAOImpl;
import java.util.Map;

public class ImportLogDao extends CommonSpringDAOImpl
{

	public ImportLogDao()
	{
	}

	public void completeModel(ImportLogModel importlogmodel, Map map)
	{
	}

	public volatile void completeModel(Object obj, Map map)
	{
		completeModel((ImportLogModel)obj, map);
	}
}
