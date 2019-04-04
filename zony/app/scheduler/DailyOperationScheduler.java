// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DailyOperationScheduler.java

package com.zony.app.scheduler;

import com.zony.app.constants.AppGlobals;
import com.zony.app.logic.EmailTaskLogic;
import com.zony.app.logic.PolicySendLogic;
import com.zony.common.util.*;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class DailyOperationScheduler extends QuartzJobBean
{

	private static final Logger logger = Logger.getLogger(com/zony/app/scheduler/DailyOperationScheduler);
	private static int searchIndexFlag = 0;

	public DailyOperationScheduler()
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
			docheck();
			searchIndexFlag = 0;
			return;
		}
	}

	public void docheck()
	{
		try
		{
			logger.debug((new StringBuilder("执行每天任务开始，开始时间：")).append(DateUtil.getNow()).toString());
			AppGlobals.vemailKeyList = null;
			ApplicationContext context = Globals.appContext;
			EmailTaskLogic emailTaskLogic = (EmailTaskLogic)context.getBean("emailTaskLogic");
			emailTaskLogic.initEmailAccount();
			emailTaskLogic.initEmailTask();
			PolicySendLogic policySendLogic = (PolicySendLogic)Globals.appContext.getBean("policySendLogic");
			policySendLogic.setSendStatusYDCS();
			logger.debug((new StringBuilder("执行每天任务成功，执行时间：")).append(DateUtil.getNow()).toString());
		}
		catch (Exception e)
		{
			logger.error((new StringBuilder("执行每天任务失败，执行时间：")).append(DateUtil.getNow()).append(" 异常信息：").append(ExceptionUtils.getErrorTraceMessage(e)).toString());
		}
	}

}
