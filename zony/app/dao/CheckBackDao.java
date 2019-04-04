// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CheckBackDao.java

package com.zony.app.dao;

import com.zony.app.domain.outter.CheckBack;
import com.zony.app.model.CheckBackModel;
import com.zony.common.dao.CommonSpringOutterDAOImpl;
import java.util.*;

public class CheckBackDao extends CommonSpringOutterDAOImpl
{

	public CheckBackDao()
	{
	}

	public void completeModel(CheckBackModel checkbackmodel, Map map)
	{
	}

	public List getCheckBack()
	{
		List checkBackList = getEntityList("from CheckBack where isNew='1'", null);
		return checkBackList;
	}

	public void updateCheckBack(List checkBackList)
	{
		if (checkBackList != null && checkBackList.size() > 0)
		{
			String ids = "";
			for (Iterator iterator = checkBackList.iterator(); iterator.hasNext();)
			{
				CheckBack checkBack = (CheckBack)iterator.next();
				ids = (new StringBuilder(String.valueOf(ids))).append(checkBack.getId()).append(",").toString();
			}

			bulkUpdate((new StringBuilder("update CheckBack set isNew='0' where id in(")).append(ids.substring(0, ids.length() - 1)).append(")").toString());
		}
	}

	public volatile void completeModel(Object obj, Map map)
	{
		completeModel((CheckBackModel)obj, map);
	}
}
