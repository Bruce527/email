// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SyncDataScheduler.java

package com.zony.app.scheduler;

import com.zony.app.dao.CheckBackDao;
import com.zony.app.domain.PolicyInfo;
import com.zony.app.domain.outter.CheckBack;
import com.zony.app.logic.PolicyLogLogic;
import com.zony.app.logic.PolicySendLogic;
import com.zony.common.util.ExceptionUtils;
import com.zony.common.util.Globals;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class SyncDataScheduler extends QuartzJobBean
{

	private static final Logger logger = Logger.getLogger(com/zony/app/scheduler/SyncDataScheduler);
	private static int searchIndexFlag = 0;

	public SyncDataScheduler()
	{
	}

	protected void executeInternal(JobExecutionContext arg0)
		throws JobExecutionException
	{
		doBuildIndex();
	}

	public synchronized void doBuildIndex()
	{
		if (searchIndexFlag > 0)
		{
			return;
		} else
		{
			searchIndexFlag = 1;
			System.out.println("开始构造索引库....");
			docheck();
			System.out.println("索引库构造完毕!");
			searchIndexFlag = 0;
			return;
		}
	}

	public void docheck()
	{
		try
		{
			logger.debug("同步外部数据库回签数据开始");
			CheckBackDao checkBackDao = (CheckBackDao)Globals.appContext.getBean("checkBackDao");
			List checkList = checkBackDao.getCheckBack();
			if (checkList != null && checkList.size() > 0)
			{
				PolicySendLogic policySendLogic = (PolicySendLogic)Globals.appContext.getBean("policySendLogic");
				for (Iterator iterator = checkList.iterator(); iterator.hasNext();)
				{
					CheckBack checkBack = (CheckBack)iterator.next();
					PolicyInfo policyInfo = policySendLogic.setSignStatus(checkBack.getEmailCode(), checkBack.getResult(), checkBack.getCheckBackTime());
					if (policyInfo != null && policyInfo.getId().longValue() > 0L)
					{
						PolicyLogLogic policyLogLogic = (PolicyLogLogic)Globals.appContext.getBean("policyLogLogic");
						if (checkBack.getResult().equals("2"))
						{
							policyLogLogic.saveLog(policyInfo.getId(), "12", null);
							policySendLogic.setSendStatusByEmailCode(policyInfo.getEmailCode(), "5");
						} else
						if (checkBack.getResult().equals("4") || checkBack.getResult().equals("3"))
						{
							policyLogLogic.saveLog(policyInfo.getId(), "14", null);
							policySendLogic.setSendStatusByEmailCode(policyInfo.getEmailCode(), "5");
						}
					}
				}

				checkBackDao.updateCheckBack(checkList);
			}
			logger.debug("同步外部数据库回签数据正常结束");
		}
		catch (Exception e)
		{
			logger.error((new StringBuilder("执行同步外部数据库回签信息失败，错误消息：")).append(ExceptionUtils.getErrorTraceMessage(e)).toString());
		}
	}

}
