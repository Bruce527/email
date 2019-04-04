// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PolicyLogDao.java

package com.zony.app.dao;

import com.zony.app.model.PolicyLogModel;
import com.zony.common.dao.CommonSpringDAOImpl;
import java.util.List;
import java.util.Map;

public class PolicyLogDao extends CommonSpringDAOImpl
{

	public PolicyLogDao()
	{
	}

	public void completeModel(PolicyLogModel policylogmodel, Map map)
	{
	}

	public List getPolicyLog(Long policyId)
	{
		return getEntityList((new StringBuilder("from PolicyLog where policyID=")).append(policyId).toString(), null);
	}

	public volatile void completeModel(Object obj, Map map)
	{
		completeModel((PolicyLogModel)obj, map);
	}
}
