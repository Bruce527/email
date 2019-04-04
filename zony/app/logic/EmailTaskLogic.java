// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EmailTaskLogic.java

package com.zony.app.logic;

import com.zony.app.constants.AppGlobals;
import com.zony.app.dao.*;
import com.zony.app.domain.*;
import com.zony.common.config.ZonyConfig;
import java.io.PrintStream;
import java.util.*;
import org.apache.commons.lang.StringUtils;

// Referenced classes of package com.zony.app.logic:
//			PolicyLogLogic, PolicySendLogic, PolicyImportLogic

public class EmailTaskLogic
{

	private EmailTaskDao emailTaskDao;
	private EmailAccountDao emailAccountDao;
	private PolicyInfoDao policyInfoDao;
	private PolicyLogLogic policyLogLogic;
	PolicySendLogic policySendLogic;
	PolicyImportLogic policyImportLogic;

	public EmailTaskLogic()
	{
	}

	public void setEmailTaskDao(EmailTaskDao emailTaskDao)
	{
		this.emailTaskDao = emailTaskDao;
	}

	public List getNoReceiptEmailTask(int hour)
	{
		return emailTaskDao.getNoReceiptEmailTask(hour);
	}

	public String getPolicyIdById(Long id)
	{
		EmailTask emailTask = (EmailTask)emailTaskDao.getEntity((new StringBuilder("from EmailTask where id=")).append(id).toString(), null);
		if (emailTask != null && emailTask.getId().longValue() > 0L)
			return emailTask.getPolicyID();
		else
			return null;
	}

	public void updateNoReceiptEmailTask(List list1)
	{
	}

	public boolean isOldPolicy(String id)
	{
		int count = policyInfoDao.getPolicyInfoByqueryStr((new StringBuilder(" and OutdateStatus=1 and id ='")).append(id).append("'").toString()).size();
		return count > 0;
	}

	public List checkAddSeedMailTaskForTM(List idList)
	{
		List newIDList = new ArrayList();
		List tmCodeList = ZonyConfig.getTMProjectCode();
		List tmChnList = ZonyConfig.getTMChnCode();
		for (int i = 0; i < idList.size(); i++)
		{
			PolicyInfo policyObj = (PolicyInfo)policyInfoDao.getById(Long.valueOf(Long.parseLong((String)idList.get(i))));
			if (!tmCodeList.contains(policyObj.getProjectName()) && !tmChnList.contains(policyObj.getChn()))
				newIDList.add((String)idList.get(i));
		}

		return newIDList;
	}

	public Map addSeedMailTask(String userCode, String taskType, String dateStr, List idList, List cntrnoList, Long priority)
		throws Exception
	{
		Map result = new HashMap();
		List oldIdList = new ArrayList();
		StringBuffer sb = new StringBuffer("已取消过时保单的发送，保单编号：\n");
		List newIDList = new ArrayList();
		for (int i = 0; i < idList.size(); i++)
			if (isOldPolicy((String)idList.get(i)))
			{
				oldIdList.add((String)idList.get(i));
				sb.append((new StringBuilder(String.valueOf((String)cntrnoList.get(i)))).append("\n").toString());
			} else
			{
				newIDList.add((String)idList.get(i));
			}

		idList = newIDList;
		cancelSendMail(userCode, "DFS", oldIdList);
		if (StringUtils.isNotEmpty(taskType) && taskType.equals("now"))
		{
			List ids = addMailTaskNow(idList, priority);
			policyInfoDao.setSendStatus(idList, "3", userCode);
			AppGlobals.addEmailTask(ids);
			List logPId = new ArrayList();
			String long1;
			for (Iterator iterator1 = idList.iterator(); iterator1.hasNext(); logPId.add(Long.valueOf(Long.parseLong(long1))))
				long1 = (String)iterator1.next();

			policyLogLogic.saveLog(logPId, "9", userCode);
			if (oldIdList.size() == 0)
				result.put(String.valueOf(1), "添加邮件立即发送任务成功！");
			else
				result.put(String.valueOf(1), (new StringBuilder("添加邮件立即发送任务成功！\n")).append(sb.toString()).toString());
		} else
		if (StringUtils.isNotEmpty(taskType) && taskType.equals("timer"))
		{
			if (StringUtils.isNotEmpty(dateStr))
			{
				addMailTaskTimer(idList, dateStr);
				policyInfoDao.setSendStatus(idList, "3", userCode);
				List logPId = new ArrayList();
				String long1;
				for (Iterator iterator = idList.iterator(); iterator.hasNext(); sb.append(long1))
				{
					long1 = (String)iterator.next();
					logPId.add(Long.valueOf(Long.parseLong(long1)));
				}

				policyLogLogic.saveLog(logPId, "10", userCode);
				if (oldIdList.size() == 0)
					result.put(String.valueOf(1), (new StringBuilder("添加邮件定时发送任务成功，发送时间为：")).append(dateStr).toString());
				else
					result.put(String.valueOf(1), (new StringBuilder("添加邮件定时发送任务成功，发送时间为：\n")).append(dateStr).append(sb.toString()).toString());
			} else
			{
				result.put(String.valueOf(2), "添加邮件发送任务失败，请重试或联系管理员。");
			}
		} else
		{
			result.put(String.valueOf(2), "添加邮件发送任务失败，请重试或联系管理员。");
		}
		return result;
	}

	public Map cancelSendMail(String userCode, String type, List idList)
		throws Exception
	{
		Map result = new HashMap();
		if (idList != null && idList.size() > 0 && type.equals("DFS"))
		{
			boolean flag = policySendLogic.setSendStatus(idList, "-1", null);
			if (flag)
			{
				cancelMailTask(idList);
				String pid;
				for (Iterator iterator = idList.iterator(); iterator.hasNext(); policyLogLogic.saveLogWithError(Long.valueOf(pid), "17", "发送任务已取消，发送邮件失败！", userCode))
					pid = (String)iterator.next();

				result.put(String.valueOf(1), "取消邮件发送任务成功！");
			} else
			{
				result.put(String.valueOf(2), "取消邮件发送任务操作失败！");
			}
		}
		return result;
	}

	private List addMailTaskNow(List policyIdList, Long priority)
	{
		List ids = new ArrayList();
		Long id;
		for (Iterator iterator = policyIdList.iterator(); iterator.hasNext(); ids.add(id))
		{
			String policyId = (String)iterator.next();
			id = Long.valueOf(1L);
			EmailTask emailTask = new EmailTask();
			emailTask.setPolicyID(policyId);
			emailTask.setTaskType("1");
			emailTask.setTaskStatus("1");
			emailTask.setPriority(priority);
			id = (Long)emailTaskDao.save(emailTask);
		}

		return ids;
	}

	private void addMailTaskTimer(List policyIdList, String sendDate)
	{
		EmailTask emailTask;
		for (Iterator iterator = policyIdList.iterator(); iterator.hasNext(); emailTaskDao.save(emailTask))
		{
			String policyId = (String)iterator.next();
			Long id = Long.valueOf(1L);
			emailTask = new EmailTask();
			emailTask.setPolicyID(policyId);
			emailTask.setTaskType("2");
			emailTask.setSendDate(sendDate);
			emailTask.setTaskStatus("1");
		}

	}

	private void cancelMailTask(List policyIdList)
	{
		if (policyIdList != null && policyIdList.size() > 0)
		{
			String hql = "delete from EmailTask where taskStatus='1' and policyID in (";
			for (Iterator iterator = policyIdList.iterator(); iterator.hasNext();)
			{
				String policyId = (String)iterator.next();
				hql = (new StringBuilder(String.valueOf(hql))).append("'").append(policyId).append("',").toString();
			}

			hql = (new StringBuilder(String.valueOf(hql.substring(0, hql.length() - 1)))).append(")").toString();
			emailTaskDao.bulkUpdate(hql, null);
		}
	}

	public void deleteFailureTask(Long id)
	{
		if (id.longValue() > 0L)
			emailTaskDao.bulkUpdate((new StringBuilder("delete from EmailTask where id=")).append(id).toString());
	}

	public EmailAccount getMailAccount(String Chn, String type)
	{
		return emailAccountDao.getEmailAccount(Chn, type);
	}

	public void updateMailAccount(EmailAccount emailAccount, String type)
	{
		if (type.equals("SEND"))
		{
			emailAccount.setSendCount(Long.valueOf(emailAccount.getSendCount().longValue() + 1L));
			emailAccountDao.bulkUpdate((new StringBuilder("update EmailAccount set sendCount=")).append(emailAccount.getSendCount()).append(" where id=").append(emailAccount.getId()).toString(), null);
		} else
		if (type.equals("RECEIPT"))
			emailAccountDao.bulkUpdate((new StringBuilder("update EmailAccount set isReceiving='")).append(emailAccount.getIsReceiving()).append("' where id=").append(emailAccount.getId()).toString(), null);
	}

	public void initEmailAccount()
	{
		emailAccountDao.initEmailAccount();
	}

	public void initEmailTask()
	{
		String sql = "update EmailTask set taskStatus='1' where taskStatus='-1'";
		emailTaskDao.bulkUpdate(sql);
	}

	public void setEmailTaskStatus(String emailStatus, String sendAccount, Long id)
	{
		emailTaskDao.bulkUpdate((new StringBuilder("update EmailTask set taskStatus='")).append(emailStatus).append("',sendAccount='").append(sendAccount).append("' where id=").append(id).toString(), null);
	}

	public List getTimerEmailTask()
	{
		return emailTaskDao.getEntityList("from EmailTask where sendDate<getdate() and taskStatus='1' order by priority", null);
	}

	public void reAddEmailTask()
	{
		List idList = emailTaskDao.getTaskIDsForSend();
		AppGlobals.addEmailTask(idList);
	}

	public static void main(String args[])
	{
		StringBuffer sb = new StringBuffer("已取消过时保单的发送，保单编号：\n");
		System.out.println((new StringBuilder(String.valueOf(sb.toString()))).append("----").toString());
	}
}
