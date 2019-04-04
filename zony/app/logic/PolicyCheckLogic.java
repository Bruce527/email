// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PolicyCheckLogic.java

package com.zony.app.logic;

import com.zony.app.dao.PolicyInfoDao;
import com.zony.app.dao.VpolicyInfoDao;
import com.zony.app.domain.PolicyInfo;
import com.zony.app.domain.VpolicyInfo;
import com.zony.common.util.DateUtil;
import com.zony.common.util.StringUtil;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.*;
import org.apache.commons.lang.StringUtils;

// Referenced classes of package com.zony.app.logic:
//			LoginLogic, PolicyLogLogic

public class PolicyCheckLogic
{

	private VpolicyInfoDao vPolicyInfoDao;
	private PolicyInfoDao policyInfoDao;
	private LoginLogic loginLogic;
	private PolicyLogLogic policyLogLogic;

	public PolicyCheckLogic()
	{
	}

	public List doSearch(PolicyInfo policyInfo, String queryStr, String userCode)
	{
		StringUtil.cleanQueryField(policyInfo);
		String whereStr = "";
		if (!StringUtils.isEmpty(policyInfo.getBrcd()))
		{
			whereStr = loginLogic.getBaseSqlByOrgCode(policyInfo.getBrcd());
			policyInfo.setBrcd(null);
		}
		if (!StringUtils.isEmpty(StringUtil.createWhereStr(policyInfo)))
		{
			if (!StringUtils.isEmpty(whereStr.trim()))
				whereStr = (new StringBuilder(String.valueOf(whereStr))).append(" and ").toString();
			whereStr = (new StringBuilder(String.valueOf(whereStr))).append(StringUtil.createWhereStr(policyInfo)).toString();
		} else
		{
			whereStr = (new StringBuilder(String.valueOf(whereStr))).append(StringUtil.createWhereStr(policyInfo)).toString();
		}
		String baseRightStr = loginLogic.createUserBaseRight(userCode);
		if (!StringUtils.isEmpty(whereStr.trim()))
			whereStr = (new StringBuilder(String.valueOf(whereStr))).append(" and ").toString();
		whereStr = (new StringBuilder(String.valueOf(whereStr))).append(baseRightStr).toString();
		whereStr = (new StringBuilder(String.valueOf(whereStr))).append(" and sendStatus='1'").toString();
		whereStr = (new StringBuilder(String.valueOf(whereStr))).append(" and newID is null and outDateStatus = '2'").toString();
		if (queryStr != null)
			if (queryStr.equals("1"))
				whereStr = (new StringBuilder(String.valueOf(whereStr))).append(" and locker is null").toString();
			else
			if (queryStr.equals("2"))
				whereStr = (new StringBuilder(String.valueOf(whereStr))).append(" and locker is not null").toString();
		System.out.println(whereStr);
		List list = vPolicyInfoDao.doSearchByWhereStr(whereStr);
		return list;
	}

	public List doForceUnLock(List selectedIdList, List allIdList)
	{
		policyInfoDao.doForceUnLock(selectedIdList);
		List list = vPolicyInfoDao.getByIdListForCheck(allIdList);
		return list;
	}

	public List startCheck(List allIdList, List selectedIdList, String userCode)
	{
		if (selectedIdList == null || selectedIdList.size() == 0)
			policyInfoDao.startCheck(allIdList, userCode, false);
		else
			policyInfoDao.startCheck(selectedIdList, userCode, true);
		List list = vPolicyInfoDao.getByIdListForCheck(allIdList);
		return list;
	}

	public List viewLocked(String userCode)
	{
		List list = vPolicyInfoDao.getUserLocked(userCode);
		return list;
	}

	public List doNormalUnLock(List selectedIdList, String userCode)
	{
		policyInfoDao.doForceUnLock(selectedIdList);
		List list = viewLocked(userCode);
		return list;
	}

	public List doSearchCheckStatus(List idList)
	{
		List list = vPolicyInfoDao.getVpolicyInfoByIdList(idList);
		return list;
	}

	public void cancelCheck(String userCode)
	{
		policyInfoDao.cancelCheck(userCode);
	}

	public String getLogEventByCheckStatus(String checkStatus, boolean isPCPD)
	{
		String logEvent = "23";
		if ("-2".equals(checkStatus))
		{
			if (isPCPD)
				logEvent = "7";
			else
				logEvent = "5";
		} else
		if ("3".equals(checkStatus))
		{
			if (isPCPD)
				logEvent = "6";
			else
				logEvent = "3";
		} else
		if ("-1".equals(checkStatus))
			logEvent = "4";
		else
		if ("2".equals(checkStatus))
			logEvent = "2";
		return logEvent;
	}

	public List setCheckStatusBatch(String userCode, String checkStatus, List selectedIdList)
	{
		policyInfoDao.setCheckStatusBatch(checkStatus, selectedIdList);
		for (int i = 0; i < selectedIdList.size(); i++)
		{
			PolicyInfo policyEntity = (PolicyInfo)policyInfoDao.getById((Serializable)selectedIdList.get(i));
			policyEntity.setCheckStatus(checkStatus);
			policyEntity.setChecker(userCode);
			policyEntity.setCheckDate(DateUtil.getNow());
			policyInfoDao.update(policyEntity);
		}

		String logEvent = getLogEventByCheckStatus(checkStatus, false);
		policyLogLogic.saveLog(selectedIdList, logEvent, userCode);
		List list = viewLocked(userCode);
		return list;
	}

	public void doFinishCheck(String userCode, String checkStatus, String note)
	{
		List idList = new ArrayList();
		String logEvent = getLogEventByCheckStatus(checkStatus, true);
		if ("3".equals(checkStatus))
		{
			idList = policyInfoDao.doFinishCheckPass(userCode);
			policyLogLogic.saveLog(idList, logEvent, userCode);
		} else
		{
			idList = policyInfoDao.doFinishCheckNoPass(userCode);
			if (idList.size() > 0)
			{
				Long id;
				for (Iterator iterator = idList.iterator(); iterator.hasNext(); policyLogLogic.saveLogWithNote(id, logEvent, userCode, note))
					id = (Long)iterator.next();

			}
		}
	}

	public void doFinishCheckByChoose(String userCode, List passIdList)
	{
		List idList = policyInfoDao.doFinishCheckByChoose(userCode, passIdList);
		String logEvent = "8";
		policyLogLogic.saveLog(idList, "8", userCode);
	}

	public VpolicyInfo doCheckOne(String userCode, Long policyID, boolean checkFlag, String note)
	{
		PolicyInfo policyEntity = (PolicyInfo)policyInfoDao.getById(policyID);
		if (checkFlag)
			policyEntity.setCheckStatus("2");
		else
			policyEntity.setCheckStatus("-1");
		policyEntity.setChecker(userCode);
		policyEntity.setCheckDate(DateUtil.getNow());
		if (StringUtils.isEmpty(note))
			policyLogLogic.saveLog(policyID, "2", userCode);
		else
			policyLogLogic.saveLogWithNote(policyID, "4", userCode, note);
		policyInfoDao.update(policyEntity);
		VpolicyInfo policyView = (VpolicyInfo)vPolicyInfoDao.getById(policyID);
		return policyView;
	}

	public void getCancelLock(String date)
	{
		List policyinfoList = policyInfoDao.getEntityList((new StringBuilder("from PolicyInfo where lockDate<'")).append(date).append("'").toString(), null);
		PolicyInfo policyInfo;
		for (Iterator iterator = policyinfoList.iterator(); iterator.hasNext(); policyInfoDao.doCancelLock(policyInfo))
			policyInfo = (PolicyInfo)iterator.next();

	}
}
