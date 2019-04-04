// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RoleRightDao.java

package com.zony.app.dao;

import com.zony.app.domain.RoleRight;
import com.zony.app.model.RoleRightModel;
import com.zony.common.dao.CommonSpringDAOImpl;
import java.util.*;

public class RoleRightDao extends CommonSpringDAOImpl
{

	public RoleRightDao()
	{
	}

	public void completeModel(RoleRightModel rolerightmodel, Map map)
	{
	}

	public Long saveRoleRight(RoleRight roleRight)
	{
		return (Long)save(roleRight);
	}

	public void delRoleRightByRoleId(Long roleId)
	{
		List list = getRoleRightByRoleId(roleId);
		if (list != null && list.size() > 0)
		{
			RoleRight roleRight;
			for (Iterator iterator = list.iterator(); iterator.hasNext(); delete(roleRight))
				roleRight = (RoleRight)iterator.next();

		}
	}

	public List getAllRoleRight()
	{
		String hql = "from RoleRight r order by r.id";
		List list = getEntityList("from RoleRight r order by r.id", null);
		return list;
	}

	public List getAllRoleRightModel()
		throws Exception
	{
		String hql = "from RoleRight r order by r.id";
		List list = getModelList("from RoleRight r order by r.id", new Object[0]);
		return list;
	}

	public List getRoleRightByRoleId(Long roleId)
	{
		String hql = (new StringBuilder("from RoleRight r where r.roleID = ")).append(roleId).append(" order by r.id").toString();
		List list = getEntityList(hql, null);
		return list;
	}

	public volatile void completeModel(Object obj, Map map)
	{
		completeModel((RoleRightModel)obj, map);
	}
}
