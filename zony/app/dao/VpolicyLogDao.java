// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   VpolicyLogDao.java

package com.zony.app.dao;

import com.zony.app.model.VpolicyLogModel;
import com.zony.common.dao.CommonSpringDAOImpl;
import flex.messaging.util.StringUtils;
import java.util.*;

public class VpolicyLogDao extends CommonSpringDAOImpl
{

	public VpolicyLogDao()
	{
	}

	public void completeModel(VpolicyLogModel vpolicylogmodel, Map map)
	{
	}

	public List searchVpolicyLogInfo(Map values)
	{
		List vpolicyLogList = new ArrayList();
		String hql = "from VpolicyLog where 1=1";
		String cntrno = (String)values.get("cntrno");
		String prpsno = (String)values.get("prpsno");
		String productCode = (String)values.get("productCode");
		String checker = (String)values.get("checker");
		String projectName = (String)values.get("projectName");
		String PackageName = (String)values.get("PackageName");
		String groupName = (String)values.get("groupName");
		String policyTypeName = (String)values.get("policyTypeName");
		String orgName = (String)values.get("orgName");
		String checkStatus = (String)values.get("checkStatus");
		String checkDate = (String)values.get("checkDate");
		if (!StringUtils.isEmpty(cntrno))
			hql = (new StringBuilder(String.valueOf(hql))).append(" and cntrno like='%").append(cntrno).append("%'").toString();
		if (!StringUtils.isEmpty(prpsno))
			hql = (new StringBuilder(String.valueOf(hql))).append(" and prpsno like='%").append(prpsno).append("%'").toString();
		if (!StringUtils.isEmpty(productCode))
			hql = (new StringBuilder(String.valueOf(hql))).append(" and productCode like='%").append(productCode).append("%'").toString();
		if (!StringUtils.isEmpty(checker))
			hql = (new StringBuilder(String.valueOf(hql))).append(" and checker like='%").append(checker).append("%'").toString();
		if (!StringUtils.isEmpty(projectName))
			hql = (new StringBuilder(String.valueOf(hql))).append(" and projectName ='").append(projectName).append("'").toString();
		if (!StringUtils.isEmpty(PackageName))
			hql = (new StringBuilder(String.valueOf(hql))).append(" and PackageName ='").append(PackageName).append("'").toString();
		if (!StringUtils.isEmpty(groupName))
			hql = (new StringBuilder(String.valueOf(hql))).append(" and groupName ='").append(groupName).append("'").toString();
		if (!StringUtils.isEmpty(policyTypeName))
			hql = (new StringBuilder(String.valueOf(hql))).append(" and policyTypeName ='").append(policyTypeName).append("'").toString();
		if (!StringUtils.isEmpty(orgName))
			hql = (new StringBuilder(String.valueOf(hql))).append(" and orgName ='").append(orgName).append("'").toString();
		if (!StringUtils.isEmpty(checkStatus))
			hql = (new StringBuilder(String.valueOf(hql))).append(" and checkStatus ='").append(checkStatus).append("'").toString();
		if (!StringUtils.isEmpty(checkDate))
		{
			hql = (new StringBuilder(String.valueOf(hql))).append(" and checkDate>='").append(checkDate).append(" 00:00:00'").toString();
			hql = (new StringBuilder(String.valueOf(hql))).append(" and checkDate<='").append(checkDate).append(" 00:00:00'").toString();
		}
		vpolicyLogList = getEntityList(hql, null);
		return vpolicyLogList;
	}

	public List getVpolicyLogInfo(Long policyId)
	{
		String hql = (new StringBuilder("from VpolicyLog where policyID=")).append(policyId).toString();
		return getEntityList(hql, null);
	}

	public volatile void completeModel(Object obj, Map map)
	{
		completeModel((VpolicyLogModel)obj, map);
	}
}
