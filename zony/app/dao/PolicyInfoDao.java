// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PolicyInfoDao.java

package com.zony.app.dao;

import com.zony.app.domain.PolicyInfo;
import com.zony.app.model.PolicyInfoModel;
import com.zony.common.dao.CommonSpringDAOImpl;
import com.zony.common.util.DateUtil;
import com.zony.common.util.StringUtil;
import java.util.*;
import org.apache.commons.lang.StringUtils;

public class PolicyInfoDao extends CommonSpringDAOImpl
{

	public PolicyInfoDao()
	{
	}

	public void completeModel(PolicyInfoModel policyinfomodel, Map map)
	{
	}

	public long savePolicyInfo(PolicyInfo policy)
	{
		return ((Long)save(policy)).longValue();
	}

	public PolicyInfo getPolicyInfoById(long id)
	{
		return (PolicyInfo)getById(Long.valueOf(id));
	}

	public PolicyInfo getPolicyInfoByQueryInfo(long id)
	{
		return (PolicyInfo)getById(Long.valueOf(id));
	}

	public void update(PolicyInfo t)
	{
		super.update(t);
	}

	public boolean setSendStatus(List ids, String sendStatus, String userCode)
	{
		if (ids != null && ids.size() > 0)
		{
			String hql = (new StringBuilder("update PolicyInfo set sendStatus='")).append(sendStatus).append("',sender='").append(userCode).append("' where id in (").toString();
			for (Iterator iterator = ids.iterator(); iterator.hasNext();)
			{
				String id = (String)iterator.next();
				hql = (new StringBuilder(String.valueOf(hql))).append(id).append(",").toString();
			}

			hql = (new StringBuilder(String.valueOf(hql.substring(0, hql.length() - 1)))).append(")").toString();
			bulkUpdate(hql);
			return true;
		} else
		{
			return false;
		}
	}

	public void doForceUnLock(List selectedIdList)
	{
		String idStr = StringUtil.getQueryStrNoQuote(selectedIdList);
		if (!StringUtils.isEmpty(idStr))
		{
			String hql = (new StringBuilder("update PolicyInfo set locker=null,lockDate=null where id in(")).append(idStr).append(")").toString();
			bulkUpdate(hql);
		}
	}

	public void cancelCheck(String userCode)
	{
		String hql = (new StringBuilder("update PolicyInfo set locker=null,lockDate=null where locker='")).append(userCode).append("' and sendStatus='").append("1").append("'").toString();
		bulkUpdate(hql);
	}

	public void setCheckStatusBatch(String checkStatus, List selectedIdList)
	{
		String idStr = StringUtil.getQueryStrNoQuote(selectedIdList);
		if (!StringUtils.isEmpty(idStr))
		{
			String hql = (new StringBuilder("update PolicyInfo set checkStatus='")).append(checkStatus).append("' where id in(").append(idStr).append(") and sendStatus='").append("1").append("'").toString();
			bulkUpdate(hql);
		}
	}

	public List doFinishCheckPass(String userCode)
	{
		List idList = new ArrayList();
		String hql = (new StringBuilder("From PolicyInfo where locker='")).append(userCode).append("' and sendStatus='").append("1").append("'").toString();
		List list = getEntityList(hql, new Object[0]);
		PolicyInfo policy;
		for (Iterator iterator = list.iterator(); iterator.hasNext(); idList.add(policy.getId()))
			policy = (PolicyInfo)iterator.next();

		String checkDate = DateUtil.getNow();
		String hqlWCJ = (new StringBuilder("update PolicyInfo set checker='")).append(userCode).append("',checkDate='").append(checkDate).append("',locker=null,lockDate=null,checkStatus='").append("3").append("',sendStatus='").append("2").append("' ").append(" where locker='").append(userCode).append("' and checkStatus='").append("1").append("' and sendStatus='").append("1").append("'").toString();
		bulkUpdate(hqlWCJ);
		String hqlTG = (new StringBuilder("update PolicyInfo set checker='")).append(userCode).append("',checkDate='").append(checkDate).append("',locker=null,lockDate=null,sendStatus='").append("2").append("'").append(" where locker='").append(userCode).append("' and checkStatus in ('").append("3").append("','").append("2").append("') and sendStatus='").append("1").append("'").toString();
		bulkUpdate(hqlTG);
		String hqlBTG = (new StringBuilder("update PolicyInfo set locker=null,lockDate=null where locker='")).append(userCode).append("' and checkStatus in ('").append("-2").append("','").append("-1").append("') and sendStatus='").append("1").append("'").toString();
		bulkUpdate(hqlBTG);
		return idList;
	}

	public List doFinishCheckNoPass(String userCode)
	{
		List idList = new ArrayList();
		String hql = (new StringBuilder("From PolicyInfo where locker='")).append(userCode).append("' and sendStatus='").append("1").append("'").toString();
		List list = getEntityList(hql, new Object[0]);
		PolicyInfo policy;
		for (Iterator iterator = list.iterator(); iterator.hasNext(); idList.add(policy.getId()))
			policy = (PolicyInfo)iterator.next();

		String checkDate = DateUtil.getNow();
		String hqlWCJ = (new StringBuilder("update PolicyInfo set locker=null,lockDate=null,checkStatus='-2',checkDate='")).append(checkDate).append("' where locker='").append(userCode).append("' and checkStatus in ('").append("1").append("','").append("2").append("','").append("3").append("') and sendStatus='").append("1").append("'").toString();
		bulkUpdate(hqlWCJ);
		String hqlBTG = (new StringBuilder("update PolicyInfo set locker=null,lockDate=null,checkDate='")).append(checkDate).append("' where locker='").append(userCode).append("' and checkStatus in ('").append("-2").append("','").append("-1").append("') and sendStatus='").append("1").append("'").toString();
		bulkUpdate(hqlBTG);
		return idList;
	}

	public List doFinishCheckByChoose(String userCode, List passIdList)
	{
		List idList = new ArrayList();
		String hql = (new StringBuilder("From PolicyInfo where locker='")).append(userCode).append("' and sendStatus='").append("1").append("'").toString();
		List list = getEntityList(hql, new Object[0]);
		PolicyInfo policy;
		for (Iterator iterator = list.iterator(); iterator.hasNext(); idList.add(policy.getId()))
			policy = (PolicyInfo)iterator.next();

		String checkDate = DateUtil.getNow();
		String idStr = StringUtil.getQueryStrNoQuote(passIdList);
		String hqlOne = (new StringBuilder("update PolicyInfo set checker='")).append(userCode).append("',checkDate='").append(checkDate).append("',locker=null,lockDate=null,checkStatus='").append("3").append("',sendStatus='").append("2").append("'").append(" where locker='").append(userCode).append("' and checkStatus = '").append("1").append("' and sendStatus='").append("1").append("' and id in (").append(idStr).append(")").toString();
		bulkUpdate(hqlOne);
		String hqlTwo = (new StringBuilder("update PolicyInfo set checker='")).append(userCode).append("',checkDate='").append(checkDate).append("',locker=null,lockDate=null,sendStatus='").append("2").append("'").append(" where locker='").append(userCode).append("' and checkStatus in ('").append("3").append("','").append("2").append("') and sendStatus='").append("1").append("' and id in (").append(idStr).append(")").toString();
		bulkUpdate(hqlTwo);
		String hqlThree = (new StringBuilder("update PolicyInfo set locker=null,lockDate=null where locker='")).append(userCode).append("' and sendStatus='").append("1").append("'").toString();
		bulkUpdate(hqlThree);
		return idList;
	}

	public void startCheck(List allIdList, String userCode, boolean checkContainFailed)
	{
		String idStr = StringUtil.getQueryStrNoQuote(allIdList);
		if (!StringUtils.isEmpty(idStr))
		{
			String date = DateUtil.getNow();
			String hql = (new StringBuilder("update PolicyInfo set locker='")).append(userCode).append("',lockDate='").append(date).append("' where id in(").append(idStr).append(") and locker=null and sendStatus='").append("1").append("'").toString();
			if (!checkContainFailed)
				hql = (new StringBuilder(String.valueOf(hql))).append(" and checkStatus not in ('-1','-2')").toString();
			bulkUpdate(hql);
		}
	}

	public List getPolicyInfoByCntrNO(String cntrno, long ownerid, String letterctl, String trancode)
	{
		String hql = (new StringBuilder(" From PolicyInfo where cntrno ='")).append(cntrno).append("' and id <> ").append(ownerid).append(" and LETTERCTL='").append(letterctl).append("' and Trancode='").append(trancode).append("'").toString();
		return getEntityList(hql, new Object[0]);
	}

	public int updatePolicyInfoByNewID(long oldId, long newID, String outdateStatus)
	{
		String outdateStatusUpdateStr = "";
		if (!outdateStatus.equals("3"))
			outdateStatusUpdateStr = (new StringBuilder(String.valueOf(outdateStatusUpdateStr))).append(",outdateStatus='").append(outdateStatus).append("'").toString();
		String hql = (new StringBuilder(" update PolicyInfo set locker=null, lockDate=null, newID=")).append(newID).append(outdateStatusUpdateStr).append(" where id=").append(oldId).toString();
		return bulkUpdate(hql);
	}

	public int updateOutdateStatus(long id, String outdateStatus)
	{
		String hql = (new StringBuilder(" update PolicyInfo set outdateStatus='")).append(outdateStatus).append("' where ID=").append(id).toString();
		return bulkUpdate(hql);
	}

	public List getPolicyInfoByqueryStr(String querystr)
	{
		String hql = (new StringBuilder(" from  PolicyInfo where 1=1 ")).append(querystr).toString();
		return getEntityList(hql, new Object[0]);
	}

	public void doCancelLock(PolicyInfo policyInfo)
	{
		policyInfo.setLockDate(null);
		policyInfo.setLocker(null);
		update(policyInfo);
	}

	public PolicyInfo getPolicyByCntrno(String cntrno)
	{
		String hql = (new StringBuilder("from PolicyInfo where (cntrno='")).append(cntrno).append("' and signStatus in ('").append("2").append("','").append("5").append("','").append("4").append("','").append("3").append("') and pol_AcknowledgementDate is not null ) and id in (select policyID from PolicyLog where logEvent in(").append("12").append(",").append("14").append(",").append("15").append(",").append("16").append("))").toString();
		return (PolicyInfo)getEntity(hql, new Object[0]);
	}

	public volatile void update(Object obj)
	{
		update((PolicyInfo)obj);
	}

	public volatile void completeModel(Object obj, Map map)
	{
		completeModel((PolicyInfoModel)obj, map);
	}
}
