// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RoleInfoDao.java

package com.zony.app.dao;

import com.zony.app.domain.RoleInfo;
import com.zony.app.model.RoleInfoModel;
import com.zony.common.dao.CommonSpringDAOImpl;
import java.util.List;
import java.util.Map;

public class RoleInfoDao extends CommonSpringDAOImpl
{

	public RoleInfoDao()
	{
	}

	public void completeModel(RoleInfoModel roleinfomodel, Map map)
	{
	}

	public Long saveRoleInfo(RoleInfo roleInfo)
	{
		return (Long)save(roleInfo);
	}

	public RoleInfo checkRoleInfoByRoleName(RoleInfo roleInfo)
	{
		String hql = " from RoleInfo where roleName=?";
		if (roleInfo.getId() != null)
			hql = (new StringBuilder(String.valueOf(hql))).append(" and id <> ").append(roleInfo.getId()).toString();
		return (RoleInfo)getEntity(hql, new Object[] {
			roleInfo.getRoleName()
		});
	}

	public Long updateRoleInfo(RoleInfo roleInfo)
	{
		update(roleInfo);
		return new Long(1L);
	}

	public int delRoleInfo(Long id)
	{
		String hql = (new StringBuilder(" delete from RoleInfo where id=")).append(id).toString();
		return bulkUpdate(hql, null);
	}

	public List getAllRoleInfoModel()
		throws Exception
	{
		String hql = "from RoleInfo r order by r.id";
		List list = getModelList("from RoleInfo r order by r.id", new Object[0]);
		return list;
	}

	public volatile void completeModel(Object obj, Map map)
	{
		completeModel((RoleInfoModel)obj, map);
	}
}
