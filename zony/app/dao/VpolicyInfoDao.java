// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   VpolicyInfoDao.java

package com.zony.app.dao;

import com.zony.app.model.VpolicyInfoModel;
import com.zony.common.config.ZonyConfig;
import com.zony.common.dao.CommonSpringDAOImpl;
import com.zony.common.util.PageUtil;
import com.zony.common.util.StringUtil;
import java.util.*;
import org.apache.commons.lang.StringUtils;

public class VpolicyInfoDao extends CommonSpringDAOImpl
{

	public VpolicyInfoDao()
	{
	}

	public void completeModel(VpolicyInfoModel vpolicyinfomodel, Map map)
	{
	}

	public List doSearchByWhereStr(String whereStr, PageUtil pageutil)
	{
		List list = new ArrayList();
		if (!StringUtils.isEmpty(whereStr))
		{
			String hql = (new StringBuilder("From VpolicyInfo where ")).append(whereStr).toString();
			list = getByPage(hql, null, pageutil);
		}
		return list;
	}

	public List doSearchByWhereStr(String whereStr)
	{
		List list = new ArrayList();
		int pageSize = ZonyConfig.getDefaultPageSize();
		if (!StringUtils.isEmpty(whereStr))
		{
			String hql = (new StringBuilder("From VpolicyInfo where ")).append(whereStr).toString();
			list = getEntityListLimited(hql, pageSize, new Object[0]);
		}
		return list;
	}

	public List doSearchALLByWhereStr(String whereStr)
	{
		List list = new ArrayList();
		if (!StringUtils.isEmpty(whereStr))
		{
			String hql = (new StringBuilder("From VpolicyInfo where ")).append(whereStr).toString();
			list = getEntityList(hql, new Object[0]);
		}
		return list;
	}

	public List getByIdListForCheck(List allIdList)
	{
		List list = new ArrayList();
		String idStr = StringUtil.getQueryStrNoQuote(allIdList);
		if (!StringUtils.isEmpty(idStr))
		{
			String hql = (new StringBuilder("From VpolicyInfo where id in(")).append(idStr).append(") and sendStatus='").append("1").append("'").toString();
			list = getEntityList(hql, new Object[0]);
		}
		return list;
	}

	public List getUserLocked(String userCode)
	{
		String hql = (new StringBuilder("From VpolicyInfo where locker='")).append(userCode).append("' and sendStatus='").append("1").append("'").toString();
		hql = (new StringBuilder(String.valueOf(hql))).append(" and newID is null and outDateStatus = '2'").toString();
		return getEntityList(hql, new Object[0]);
	}

	public List getVpolicyInfoByIdList(List idList)
	{
		String idStr = StringUtil.getQueryStrNoQuote(idList);
		String hql = (new StringBuilder("From VpolicyInfo where id in(")).append(idStr).append(")").toString();
		return getEntityList(hql, new Object[0]);
	}

	public volatile void completeModel(Object obj, Map map)
	{
		completeModel((VpolicyInfoModel)obj, map);
	}
}
