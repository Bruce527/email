// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SysFlagDao.java

package com.zony.app.dao;

import com.zony.app.domain.SysFlag;
import com.zony.app.model.SysFlagModel;
import com.zony.common.dao.CommonSpringDAOImpl;
import java.util.Map;

public class SysFlagDao extends CommonSpringDAOImpl
{

	public SysFlagDao()
	{
	}

	public void completeModel(SysFlagModel sysflagmodel, Map map)
	{
	}

	public Long saveSysFlag(SysFlag sysFlag)
	{
		return (Long)save(sysFlag);
	}

	public SysFlag getSysFlagByFlagName(String flagName)
	{
		String hql = "from SysFlag s where s.flagName = ?";
		return (SysFlag)getEntity("from SysFlag s where s.flagName = ?", new Object[] {
			flagName
		});
	}

	public void updateFlagValue(String flagName, String flagValue)
	{
		String hql = "update SysFlag set flagValue=? where flagName = ?";
		bulkUpdate("update SysFlag set flagValue=? where flagName = ?", new Object[] {
			flagValue, flagName
		});
	}

	public volatile void completeModel(Object obj, Map map)
	{
		completeModel((SysFlagModel)obj, map);
	}
}
