// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PolicySendConfigDao.java

package com.zony.app.dao;

import com.zony.app.domain.PolicySendConfig;
import com.zony.app.model.PolicySendConfigModel;
import com.zony.common.dao.CommonSpringDAOImpl;
import com.zony.common.util.StringUtil;
import java.util.List;
import java.util.Map;

public class PolicySendConfigDao extends CommonSpringDAOImpl
{

	public PolicySendConfigDao()
	{
	}

	public void completeModel(PolicySendConfigModel policysendconfigmodel, Map map)
	{
	}

	public List getPolicySendConfigList()
	{
		String hql = "from PolicySendConfig order by id ";
		List configList = getEntityList("from PolicySendConfig order by id ", new Object[0]);
		return configList;
	}

	public void AddPolicySendConfig(PolicySendConfig policySendConfig)
	{
		save(policySendConfig);
	}

	public void updatePolicySendConfig(PolicySendConfig policySendConfig)
	{
		update(policySendConfig);
	}

	public void delPolicySendConfig(long id)
	{
		deleteById(Long.valueOf(id));
	}

	public List getListBySameConfigCon(PolicySendConfig ponfig)
	{
		String con = StringUtil.createWhereStr(ponfig);
		String hql = "from PolicySendConfig ";
		if (con != null && con != "")
			hql = (new StringBuilder(String.valueOf(hql))).append("where ").append(con).toString();
		hql = (new StringBuilder(String.valueOf(hql))).append(" order by id ").toString();
		return getEntityList(hql, new Object[0]);
	}

	public List getSameConfigCon(PolicySendConfig ponfig)
	{
		String hql = "from PolicySendConfig where CHN=? and projectName=? and orgCode=? and productCode=? ";
		Object param[] = {
			ponfig.getCHN(), ponfig.getProjectName(), ponfig.getOrgCode(), ponfig.getProductCode()
		};
		return getEntityList(hql, param);
	}

	public List getListByHql(String hql, List paramList)
	{
		return getEntityList(hql, paramList.toArray());
	}

	public volatile void completeModel(Object obj, Map map)
	{
		completeModel((PolicySendConfigModel)obj, map);
	}
}
