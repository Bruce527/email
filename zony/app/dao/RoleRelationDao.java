// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RoleRelationDao.java

package com.zony.app.dao;

import com.zony.app.domain.RoleRelation;
import com.zony.app.model.RoleRelationModel;
import com.zony.common.dao.CommonSpringDAOImpl;
import com.zony.common.util.StringUtil;
import java.util.*;

public class RoleRelationDao extends CommonSpringDAOImpl
{

	public RoleRelationDao()
	{
	}

	public void completeModel(RoleRelationModel rolerelationmodel, Map map)
	{
	}

	public Long saveRoleRelation(RoleRelation roleRelation)
	{
		return (Long)save(roleRelation);
	}

	public void delRoleRelationByRoleId(Long roleId)
	{
		List list = getRoleRelationByRoleId(roleId);
		RoleRelation roleRelation;
		for (Iterator iterator = list.iterator(); iterator.hasNext(); delete(roleRelation))
			roleRelation = (RoleRelation)iterator.next();

	}

	public List getRoleRelationByRoleId(Long roleId)
	{
		String hql = (new StringBuilder("from RoleRelation r where r.roleID = ")).append(roleId).append(" order by r.id").toString();
		List list = getEntityList(hql, null);
		return list;
	}

	public void removeUserFromRole(Long roleID, List userIDs)
	{
		String userids = StringUtil.getQueryStr(userIDs, false);
		String hql = (new StringBuilder(" delete from RoleRelation  where roleID = ")).append(roleID).append(" and userID in (").append(userids).append(")").toString();
		bulkUpdate(hql, null);
	}

	public List getRoleRelationByRoleID(Long roleID)
	{
		String hql = "from RoleRelation r where r.roleID = ?";
		return getEntityList("from RoleRelation r where r.roleID = ?", new Object[] {
			roleID
		});
	}

	public int delRoleRelationByUserID(Long id)
	{
		String hql = (new StringBuilder(" delete from RoleRelation where userid=")).append(id).toString();
		return bulkUpdate(hql, null);
	}

	public List getAllRoleRelationModel()
		throws Exception
	{
		String hql = "from RoleRelation";
		List list = getModelList("from RoleRelation", new Object[0]);
		return list;
	}

	public volatile void completeModel(Object obj, Map map)
	{
		completeModel((RoleRelationModel)obj, map);
	}
}
