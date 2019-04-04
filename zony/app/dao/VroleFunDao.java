// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   VroleFunDao.java

package com.zony.app.dao;

import com.zony.app.model.VroleFunModel;
import com.zony.common.dao.CommonSpringDAOImpl;
import java.util.List;
import java.util.Map;

public class VroleFunDao extends CommonSpringDAOImpl
{

	public VroleFunDao()
	{
	}

	public void completeModel(VroleFunModel vrolefunmodel, Map map)
	{
	}

	public List getAllVroleFunModel()
		throws Exception
	{
		String hql = "from VroleFun rf order by rf.id";
		List list = getModelList("from VroleFun rf order by rf.id", new Object[0]);
		return list;
	}

	public List getVroleFunModelByRoleId(Long roleId)
		throws Exception
	{
		String hql = (new StringBuilder("from VroleFun rf where rf.roleID = ")).append(roleId).toString();
		List list = getModelList(hql, null, null);
		return list;
	}

	public List getVroleFunByRoleId(Long roleId)
	{
		String hql = (new StringBuilder("from VroleFun rf where rf.roleID = ")).append(roleId).toString();
		List list = getEntityList(hql, null);
		return list;
	}

	public volatile void completeModel(Object obj, Map map)
	{
		completeModel((VroleFunModel)obj, map);
	}
}
