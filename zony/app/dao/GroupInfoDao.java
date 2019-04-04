// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GroupInfoDao.java

package com.zony.app.dao;

import com.zony.app.domain.UserInfo;
import com.zony.app.model.GroupInfoModel;
import com.zony.common.dao.CommonSpringDAOImpl;
import edu.emory.mathcs.backport.java.util.Arrays;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

public class GroupInfoDao extends CommonSpringDAOImpl
{

	public GroupInfoDao()
	{
	}

	public void completeModel(GroupInfoModel groupinfomodel, Map map)
	{
	}

	public List getAllGroup()
	{
		String hql = "From GroupInfo order by sortID";
		List groupList = getEntityList("From GroupInfo order by sortID", new Object[0]);
		return groupList;
	}

	public List getChannelGroupByRight(UserInfo userObj)
	{
		String groupCode = userObj.getGroupCode();
		String hql = "";
		if (groupCode.equals("Y") || groupCode.equals("Y-A") || groupCode.equals("Y-B"))
			hql = "From GroupInfo where isChannel='1' order by sortID";
		else
			hql = (new StringBuilder("From GroupInfo where groupCode ='")).append(groupCode).append("'").toString();
		List groupList = getEntityList(hql, new Object[0]);
		return groupList;
	}

	public List getChannelGroup()
	{
		String hql = "From GroupInfo where isChannel='1' order by sortID";
		List groupList = getEntityList("From GroupInfo where isChannel='1' order by sortID", new Object[0]);
		return groupList;
	}

	public List getChannelGroupByName(String groupName)
	{
		String hql = (new StringBuilder("From GroupInfo where isChannel='1' and groupName like '%")).append(groupName).append("%' order by sortID").toString();
		List groupList = getEntityList(hql, new Object[0]);
		return groupList;
	}

	public static void main(String args[])
	{
		String array[] = null;
		System.out.println(Arrays.asList(array));
	}

	public volatile void completeModel(Object obj, Map map)
	{
		completeModel((GroupInfoModel)obj, map);
	}
}
