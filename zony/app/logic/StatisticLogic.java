// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   StatisticLogic.java

package com.zony.app.logic;

import com.zony.app.dao.PolicyInfoDao;
import com.zony.app.model.StatisticsModel;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class StatisticLogic
{

	PolicyInfoDao policyInfoDao;

	public StatisticLogic()
	{
	}

	public List getStatisticByArea(String beginDate, String endDate, String policyType, String rolesHQL)
	{
		String sql = "SELECT brcd,orgName as keyName ,count(*) as keyValue FROM VpolicyInfo where  1=1";
		if (StringUtils.isNotEmpty(rolesHQL))
			sql = (new StringBuilder(String.valueOf(sql))).append(" and ").append(rolesHQL).toString();
		if (StringUtils.isNotEmpty(beginDate))
			sql = (new StringBuilder(String.valueOf(sql))).append(" and importDate>='").append(beginDate).append(" 00:00:00").append("'").toString();
		if (StringUtils.isNotEmpty(endDate))
			sql = (new StringBuilder(String.valueOf(sql))).append(" and importDate<='").append(endDate).append(" 00:00:00").append("'").toString();
		if (StringUtils.isNotEmpty(policyType))
			sql = (new StringBuilder(String.valueOf(sql))).append(" and policyType='").append(policyType).append("'").toString();
		sql = (new StringBuilder(String.valueOf(sql))).append(" group by brcd,orgName").toString();
		List feildList = new ArrayList();
		feildList.add("keyName");
		feildList.add("keyValue");
		List statisticsMList = policyInfoDao.getListBySql(sql, null, com/zony/app/model/StatisticsModel, feildList);
		return statisticsMList;
	}

	public List getStatisticByProject(String beginDate, String endDate, String policyType, String rolesHQL)
	{
		String sql = "SELECT projectName as keyName ,count(*) as keyValue FROM PolicyInfo where  1=1";
		if (StringUtils.isNotEmpty(rolesHQL))
			sql = (new StringBuilder(String.valueOf(sql))).append(" and ").append(rolesHQL).toString();
		if (StringUtils.isNotEmpty(beginDate))
			sql = (new StringBuilder(String.valueOf(sql))).append(" and importDate>='").append(beginDate).append(" 00:00:00").append("'").toString();
		if (StringUtils.isNotEmpty(endDate))
			sql = (new StringBuilder(String.valueOf(sql))).append(" and importDate<='").append(endDate).append(" 00:00:00").append("'").toString();
		if (StringUtils.isNotEmpty(policyType))
			sql = (new StringBuilder(String.valueOf(sql))).append(" and policyType='").append(policyType).append("'").toString();
		sql = (new StringBuilder(String.valueOf(sql))).append(" group by projectName").toString();
		List feildList = new ArrayList();
		feildList.add("keyName");
		feildList.add("keyValue");
		List statisticsMList = policyInfoDao.getListBySql(sql, null, com/zony/app/model/StatisticsModel, feildList);
		return statisticsMList;
	}

	public List getStatisticByProduct(String beginDate, String endDate, String policyType, String rolesHQL)
	{
		String sql = "SELECT productCode as keyName ,count(*) as keyValue FROM PolicyInfo where  1=1";
		if (StringUtils.isNotEmpty(rolesHQL))
			sql = (new StringBuilder(String.valueOf(sql))).append(" and ").append(rolesHQL).toString();
		if (StringUtils.isNotEmpty(beginDate))
			sql = (new StringBuilder(String.valueOf(sql))).append(" and importDate>='").append(beginDate).append(" 00:00:00").append("'").toString();
		if (StringUtils.isNotEmpty(endDate))
			sql = (new StringBuilder(String.valueOf(sql))).append(" and importDate<='").append(endDate).append(" 00:00:00").append("'").toString();
		if (StringUtils.isNotEmpty(policyType))
			sql = (new StringBuilder(String.valueOf(sql))).append(" and policyType='").append(policyType).append("'").toString();
		sql = (new StringBuilder(String.valueOf(sql))).append(" group by productCode").toString();
		List feildList = new ArrayList();
		feildList.add("keyName");
		feildList.add("keyValue");
		List statisticsMList = policyInfoDao.getListBySql(sql, null, com/zony/app/model/StatisticsModel, feildList);
		return statisticsMList;
	}

	public List getStatisticByReceipt(String beginDate, String endDate, String policyType, String rolesHQL)
	{
		String sql = "SELECT signStatus,signStatusName as keyName ,count(*) as keyValue FROM VpolicyInfo where  1=1";
		if (StringUtils.isNotEmpty(rolesHQL))
			sql = (new StringBuilder(String.valueOf(sql))).append(" and ").append(rolesHQL).toString();
		if (StringUtils.isNotEmpty(beginDate))
			sql = (new StringBuilder(String.valueOf(sql))).append(" and importDate>='").append(beginDate).append(" 00:00:00").append("'").toString();
		if (StringUtils.isNotEmpty(endDate))
			sql = (new StringBuilder(String.valueOf(sql))).append(" and importDate<='").append(endDate).append(" 00:00:00").append("'").toString();
		if (StringUtils.isNotEmpty(policyType))
			sql = (new StringBuilder(String.valueOf(sql))).append(" and policyType='").append(policyType).append("'").toString();
		sql = (new StringBuilder(String.valueOf(sql))).append(" group by signStatus,signStatusName").toString();
		List feildList = new ArrayList();
		feildList.add("keyName");
		feildList.add("keyValue");
		List statisticsMList = policyInfoDao.getListBySql(sql, null, com/zony/app/model/StatisticsModel, feildList);
		return statisticsMList;
	}
}
