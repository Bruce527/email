// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   OrganizationDao.java

package com.zony.app.dao;

import com.zony.app.domain.Organization;
import com.zony.app.model.OrganizationModel;
import com.zony.common.dao.CommonSpringDAOImpl;
import java.util.List;
import java.util.Map;

public class OrganizationDao extends CommonSpringDAOImpl
{

	public OrganizationDao()
	{
	}

	public void completeModel(OrganizationModel organizationmodel, Map map)
	{
	}

	public List getAllOrganization()
	{
		String hql = "from Organization order by sortID";
		List list = getEntityList("from Organization order by sortID", new Object[0]);
		return list;
	}

	public Organization getOrgByCode(String orgCode)
	{
		String hql = "From Organization where orgCode=?";
		return (Organization)getEntity("From Organization where orgCode=?", new Object[] {
			orgCode
		});
	}

	public List getSelfAndSubOrgByCodePath(String orgCodePath)
	{
		String hql = (new StringBuilder("from Organization where orgCodePath='")).append(orgCodePath).append("' or orgCodePath like '").append(orgCodePath).append("-%' order by orgCodePath,sortID").toString();
		List list = getEntityList(hql, new Object[0]);
		return list;
	}

	public List getListByOrgName(String orgName)
	{
		String hql = (new StringBuilder("from Organization where orgName like '%")).append(orgName).append("%' order by sortID").toString();
		List list = getEntityList(hql, new Object[0]);
		return list;
	}

	public volatile void completeModel(Object obj, Map map)
	{
		completeModel((OrganizationModel)obj, map);
	}
}
