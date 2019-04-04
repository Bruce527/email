// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PolicySendConfigLogic.java

package com.zony.app.logic;

import com.zony.app.dao.*;
import com.zony.app.domain.*;
import java.util.*;

public class PolicySendConfigLogic
{

	PolicySendConfigDao policySendConfigDao;
	ProductCodeDao productCodeDao;
	ProjectNameDao projectNameDao;
	OrganizationDao organizationDao;
	GroupInfoDao groupInfoDao;

	public PolicySendConfigLogic()
	{
	}

	public void addPolicySendConfig(PolicySendConfig policySendConfig)
	{
		policySendConfigDao.AddPolicySendConfig(policySendConfig);
	}

	public List getListBySameConfigCon(PolicySendConfig policySendConfig)
	{
		List configList = policySendConfigDao.getListBySameConfigCon(policySendConfig);
		return configList;
	}

	public void delPolicySendConfig(List idList)
	{
		Long id;
		for (Iterator iterator = idList.iterator(); iterator.hasNext(); policySendConfigDao.delPolicySendConfig(id.longValue()))
			id = (Long)iterator.next();

	}

	public boolean updatePolicySendConfig(PolicySendConfig policySendConfig)
	{
		policySendConfigDao.updatePolicySendConfig(policySendConfig);
		return true;
	}

	public List getSameConfigCon(PolicySendConfig ponfig)
	{
		return policySendConfigDao.getSameConfigCon(ponfig);
	}

	public List getProductCodeList()
	{
		return productCodeDao.getProductCodeList();
	}

	public boolean getConfigByHql(String CHN, String projectName, Map productCodeMap, String orgCode)
	{
		List paramList = new ArrayList();
		List configList = new ArrayList();
		String hql = "from PolicySendConfig where CHN = ? ";
		if (CHN != null)
		{
			paramList.add(CHN);
			configList = policySendConfigDao.getListByHql(hql, paramList);
			if (configList != null && configList.size() == 1)
				return checkSendWay(configList);
		}
		if (projectName != null)
		{
			hql = (new StringBuilder(String.valueOf(hql))).append("and projectName = ? ").toString();
			paramList.add(projectName);
			configList = policySendConfigDao.getListByHql(hql, paramList);
			if (configList != null && configList.size() == 1)
				return checkSendWay(configList);
		}
		if (!productCodeMap.isEmpty())
		{
			Iterator it = productCodeMap.keySet().iterator();
			hql = (new StringBuilder(String.valueOf(hql))).append("and productCode in (").toString();
			String productCode;
			for (; it.hasNext(); paramList.add(productCode))
			{
				productCode = ((String)it.next()).toString();
				hql = (new StringBuilder(String.valueOf(hql))).append(" ?,").toString();
			}

			hql = hql.substring(0, hql.length() - 1);
			hql = (new StringBuilder(String.valueOf(hql))).append(" ) ").toString();
			configList = policySendConfigDao.getListByHql(hql, paramList);
			if (configList != null && configList.size() == 1)
				return checkSendWay(configList);
		}
		if (orgCode != null)
		{
			hql = (new StringBuilder(String.valueOf(hql))).append("and orgCode = ? ").toString();
			paramList.add(orgCode);
			configList = policySendConfigDao.getListByHql(hql, paramList);
			if (configList != null && configList.size() == 1)
				return checkSendWay(configList);
		}
		return true;
	}

	public boolean checkSendWay(List configList)
	{
		if (configList != null && configList.size() == 1)
		{
			PolicySendConfig config = (PolicySendConfig)configList.get(0);
			return config.getIsAutoSend().longValue() == 0L;
		} else
		{
			return false;
		}
	}

	public List getProductInfoListByCode(String code)
	{
		return productCodeDao.getProductInfoListByCon(code);
	}

	public List getProjectNameListByName(String projectName)
	{
		return projectNameDao.getProjectNameListByCon(projectName);
	}

	public List getOrgListByOrgName(String orgName)
	{
		return organizationDao.getListByOrgName(orgName);
	}

	public List getChannelGroupByName(String groupName)
	{
		return groupInfoDao.getChannelGroupByName(groupName);
	}

	public List getPolicySendConfigList()
	{
		return policySendConfigDao.getPolicySendConfigList();
	}

	public Map getConfigMap()
	{
		ProductCode tmpProductCode = new ProductCode();
		tmpProductCode.setProductName("全部");
		tmpProductCode.setProductCode("全部");
		Organization tmpOrganization = new Organization();
		tmpOrganization.setOrgName("全部");
		tmpOrganization.setOrgCode("全部");
		Map dataMap = new HashMap();
		List productList = getProductCodeList();
		productList.add(0, tmpProductCode);
		List groupInfoList = groupInfoDao.getChannelGroup();
		List orgList = organizationDao.getAllOrganization();
		orgList.add(0, tmpOrganization);
		dataMap.put("productCodeList", productList);
		dataMap.put("CHNList", groupInfoList);
		dataMap.put("orgList", orgList);
		return dataMap;
	}
}
