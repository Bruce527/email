// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   VroleUserDao.java

package com.zony.app.dao;

import com.zony.app.model.VroleUserModel;
import com.zony.common.dao.CommonSpringDAOImpl;
import java.util.List;
import java.util.Map;

public class VroleUserDao extends CommonSpringDAOImpl
{

	public VroleUserDao()
	{
	}

	public void completeModel(VroleUserModel vroleusermodel, Map map)
	{
	}

	public List getAllVroleUserModel()
		throws Exception
	{
		String hql = "from VroleUser";
		List list = getModelList("from VroleUser", new Object[0]);
		return list;
	}

	public VroleUserModel getVroleUserModelByRoleId(Long userID)
		throws Exception
	{
		String hql = (new StringBuilder("from VroleUser ru where ru.userID = ")).append(userID).append(" order by ru.id desc").toString();
		List list = getModelList(hql, null, null);
		if (list.size() > 0)
			return (VroleUserModel)list.get(0);
		else
			return null;
	}

	public List getRoleUserByRoleId(Long roleId)
		throws Exception
	{
		String hql = (new StringBuilder(" select * from VroleUser ru where roleID =")).append(roleId).toString();
		List listVroleUser = getEntityListBySql(hql, null);
		return listVroleUser;
	}

	public volatile void completeModel(Object obj, Map map)
	{
		completeModel((VroleUserModel)obj, map);
	}
}
