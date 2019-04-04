// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ProjectNameDao.java

package com.zony.app.dao;

import com.zony.app.domain.ProjectName;
import com.zony.app.model.ProjectNameModel;
import com.zony.common.dao.CommonSpringDAOImpl;
import java.util.List;
import java.util.Map;

public class ProjectNameDao extends CommonSpringDAOImpl
{

	public ProjectNameDao()
	{
	}

	public void completeModel(ProjectNameModel projectnamemodel, Map map)
	{
	}

	public List getAllProjectName()
	{
		String hql = "From ProjectName order by projectName";
		List projectNameList = getEntityList("From ProjectName order by projectName", new Object[0]);
		return projectNameList;
	}

	public long saveProjectName(ProjectName project)
	{
		return ((Long)save(project)).longValue();
	}

	public ProjectName getProjectByName(String projectName)
	{
		String hql = " From ProjectName where projectName =?";
		return (ProjectName)getEntity(" From ProjectName where projectName =?", new Object[] {
			projectName
		});
	}

	public List getProjectNameListByCon(String projectName)
	{
		String hql = (new StringBuilder("from ProjectName where projectName like '%")).append(projectName).append("%'").toString();
		return getEntityList(hql, new Object[0]);
	}

	public volatile void completeModel(Object obj, Map map)
	{
		completeModel((ProjectNameModel)obj, map);
	}
}
