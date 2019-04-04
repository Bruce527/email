// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FunctionInfoDao.java

package com.zony.app.dao;

import com.zony.app.domain.FunctionInfo;
import com.zony.app.model.FunctionInfoModel;
import com.zony.common.dao.CommonSpringDAOImpl;
import java.util.List;
import java.util.Map;

public class FunctionInfoDao extends CommonSpringDAOImpl
{

	public FunctionInfoDao()
	{
	}

	public void completeModel(FunctionInfoModel functioninfomodel, Map map)
	{
	}

	public FunctionInfo getFunctionInfoById(Long id)
	{
		return (FunctionInfo)getById(id);
	}

	public List getFunctionInfoModel()
		throws Exception
	{
		String hql = "from FunctionInfo f order by f.id";
		List list = getModelList("from FunctionInfo f order by f.id", new Object[0]);
		return list;
	}

	public List getAllFun()
	{
		String hql = "From FunctionInfo order by funCode";
		return getEntityList("From FunctionInfo order by funCode", new Object[0]);
	}

	public List getFunByUserID(Long userID)
	{
		String roleIdSql = (new StringBuilder("select roleid from vroleuser where userid = ")).append(userID).toString();
		String funIdSql = (new StringBuilder("select distinct funid from vrolefun where roleid in (")).append(roleIdSql).append(")").toString();
		String sql = (new StringBuilder("select * from functioninfo where id in (")).append(funIdSql).append(") order by funcode").toString();
		List funList = getEntityListBySql(sql, new Object[0]);
		return funList;
	}

	public volatile void completeModel(Object obj, Map map)
	{
		completeModel((FunctionInfoModel)obj, map);
	}
}
