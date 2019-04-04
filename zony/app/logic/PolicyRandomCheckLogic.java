// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PolicyRandomCheckLogic.java

package com.zony.app.logic;

import com.zony.app.dao.*;
import com.zony.app.domain.PolicyInfo;
import com.zony.app.model.PolicyInfoModel;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class PolicyRandomCheckLogic
{

	private PolicyInfoDao policyInfoDao;
	private ProjectNameDao projectNameDao;
	private PackageNameDao packageNameDao;
	public static Logger logger = Logger.getLogger(com/zony/app/logic/PolicyRandomCheckLogic);

	public PolicyRandomCheckLogic()
	{
	}

	public long savePolicyInfo(PolicyInfo policyInfo)
	{
		return policyInfoDao.savePolicyInfo(policyInfo);
	}

	public PolicyInfo getPolicyNoticeById(long id)
	{
		return policyInfoDao.getPolicyInfoById(id);
	}

	public List getPolicyByQuerypar(PolicyInfoModel query)
	{
		List list = new ArrayList();
		String hql = " From PolicyInfo where  ";
		StringUtils.isEmpty(query.getCntrno().trim());
		return list;
	}

}
