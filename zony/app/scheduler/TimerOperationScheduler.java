// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TimerOperationScheduler.java

package com.zony.app.scheduler;

import com.zony.app.constants.AppGlobals;
import com.zony.app.domain.EmailTask;
import com.zony.app.logic.EmailTaskLogic;
import com.zony.app.logic.PolicyCheckLogic;
import com.zony.common.config.ZonyConfig;
import com.zony.common.util.*;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class TimerOperationScheduler extends QuartzJobBean
{

	private static final Logger logger = Logger.getLogger(com/zony/app/scheduler/TimerOperationScheduler);

	public TimerOperationScheduler()
	{
	}

	protected void executeInternal(JobExecutionContext arg0)
		throws JobExecutionException
	{
		try
		{
			logger.debug((new StringBuilder("执行定时发送任务开始，开始时间：")).append(DateUtil.getNow()).toString());
			EmailTaskLogic emailTaskLogic = (EmailTaskLogic)Globals.appContext.getBean("emailTaskLogic");
			List emailList = emailTaskLogic.getTimerEmailTask();
			EmailTask emailTask;
			for (Iterator iterator = emailList.iterator(); iterator.hasNext(); AppGlobals.addEmailTask(emailTask.getId()))
				emailTask = (EmailTask)iterator.next();

			String d = DateUtil.getNow();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(df.parse(d));
			calendar.add(11, -ZonyConfig.getMaxLockPeriod());
			String date = (new StringBuilder(String.valueOf(df.format(calendar.getTime())))).append(".000").toString();
			PolicyCheckLogic policyCheckLogic = (PolicyCheckLogic)Globals.appContext.getBean("policyCheckLogic");
			policyCheckLogic.getCancelLock(date);
			logger.debug((new StringBuilder("执行定时发送任务成功，结束时间：")).append(DateUtil.getNow()).toString());
		}
		catch (Exception e)
		{
			logger.error((new StringBuilder("执行定时发送任务失败，失败时间：")).append(DateUtil.getNow()).append(" 异常信息：").append(ExceptionUtils.getErrorTraceMessage(e)).toString());
		}
	}

}
