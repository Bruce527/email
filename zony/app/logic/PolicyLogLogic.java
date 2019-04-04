// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PolicyLogLogic.java

package com.zony.app.logic;

import com.zony.app.dao.PolicyLogDao;
import com.zony.app.domain.PolicyLog;
import com.zony.common.util.DateUtil;
import java.util.Iterator;
import java.util.List;

public class PolicyLogLogic
{

	public PolicyLogDao policyLogDao;

	public PolicyLogLogic()
	{
	}

	private PolicyLog createCommonLog(Long policyID, String logEvent, String operator)
	{
		return createCommonLog(policyID, logEvent, operator, null);
	}

	private PolicyLog createCommonLog(Long policyID, String logEvent, String operator, String note)
	{
		PolicyLog policyLog = new PolicyLog();
		policyLog.setLogEvent(logEvent);
		policyLog.setPolicyID(policyID);
		policyLog.setOperator(operator);
		policyLog.setLogDate(DateUtil.getNow());
		policyLog.setNote(note);
		return policyLog;
	}

	public void saveLog(Long policyID, String logEvent, String operator)
	{
		PolicyLog policyLog = createCommonLog(policyID, logEvent, operator);
		policyLogDao.save(policyLog);
	}

	public void saveLog(Long policyID, String logEvent, String operator, String note)
	{
		PolicyLog policyLog = createCommonLog(policyID, logEvent, operator);
		policyLog.setNote(note);
		policyLogDao.save(policyLog);
	}

	public void saveLog(List policyIDList, String logEvent, String operator)
	{
		saveLog(policyIDList, logEvent, operator, null);
	}

	public void saveLog(List policyIDList, String logEvent, String operator, String note)
	{
		if (policyIDList != null)
		{
			PolicyLog policyLog;
			for (Iterator iterator = policyIDList.iterator(); iterator.hasNext(); policyLogDao.save(policyLog))
			{
				Long pid = (Long)iterator.next();
				policyLog = createCommonLog(pid, logEvent, operator, note);
			}

		}
	}

	public void saveLogWithError(Long policyID, String logEvent, String errorInfo, String operator)
	{
		PolicyLog policyLog = createCommonLog(policyID, logEvent, operator);
		policyLog.setErrorInfo(errorInfo);
		policyLogDao.save(policyLog);
	}

	public void saveLogWithNote(Long policyID, String logEvent, String operator, String note)
	{
		if ("2".equals(logEvent) || !"-1".equals(logEvent))
		{
			PolicyLog policyLog = createCommonLog(policyID, logEvent, operator);
			policyLog.setNote(note);
			policyLogDao.save(policyLog);
		}
	}

	public List getPolicyLog(Long policyId)
	{
		return policyLogDao.getPolicyLog(policyId);
	}

	public void deletePolicylog(PolicyLog policyLog)
	{
		policyLogDao.delete(policyLog);
	}
}
