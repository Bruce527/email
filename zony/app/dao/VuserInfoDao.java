// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   VuserInfoDao.java

package com.zony.app.dao;

import com.zony.app.model.VuserInfoModel;
import com.zony.common.dao.CommonSpringDAOImpl;
import java.util.List;
import java.util.Map;

public class VuserInfoDao extends CommonSpringDAOImpl
{

	public VuserInfoDao()
	{
	}

	public void completeModel(VuserInfoModel vuserinfomodel, Map map)
	{
	}

	public List getUserInfoByOrgCode(String orgCode)
	{
		String hql = (new StringBuilder("from VuserInfo  where orgCode = '")).append(orgCode).append("'").toString();
		return getEntityList(hql, null);
	}

	public List getUserInfoByOrgCodeAndRoleId(String orgCode, String roleId)
	{
		String sql = (new StringBuilder(" select * from VuserInfo  where orgCode = '")).append(orgCode).append("'  and userCode not in (select userCode  from VroleUser where roleID = ").append(roleId).append(")").toString();
		return getEntityListBySql(sql, null);
	}

	public volatile void completeModel(Object obj, Map map)
	{
		completeModel((VuserInfoModel)obj, map);
	}
}
