// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EmailTaskThread.java

package com.zony.app.thread;

import com.zony.app.constants.AppGlobals;
import com.zony.app.domain.EmailAccount;
import com.zony.app.domain.PolicyInfo;
import com.zony.app.logic.*;
import com.zony.common.config.ZonyConfig;
import com.zony.common.util.*;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

public class EmailTaskThread extends Thread
{

	private final Logger logger = Logger.getLogger(com/zony/app/thread/EmailTaskThread);
	private final ApplicationContext context;
	private EmailTaskLogic emailTaskLogic;
	private PolicyLogLogic policyLogLogic;
	private PolicySendLogic policySendLogic;

	public EmailTaskThread()
	{
		context = Globals.appContext;
		emailTaskLogic = null;
		policyLogLogic = null;
		policySendLogic = null;
	}

	public void run()
	{
_L2:
		Long taskID;
		String policyId;
		PolicyInfo policyInfo;
		EmailAccount emailaccount;
		taskID = AppGlobals.getEmailTask();
		if (taskID.longValue() <= 0L)
			break MISSING_BLOCK_LABEL_726;
		policyId = "";
		policyInfo = null;
		emailaccount = null;
		emailTaskLogic = (EmailTaskLogic)context.getBean("emailTaskLogic");
		policySendLogic = (PolicySendLogic)context.getBean("policySendLogic");
		policyLogLogic = (PolicyLogLogic)context.getBean("policyLogLogic");
		policyId = emailTaskLogic.getPolicyIdById(taskID);
		System.out.println("开始扫描可发邮件的保单");
		if (StringUtils.isEmpty(policyId))
		{
			logger.error("找不到对应的保单信息，当前发送邮件任务将被删除");
			emailTaskLogic.deleteFailureTask(taskID);
			continue; /* Loop/switch isn't completed */
		}
		List pid;
		pid = new ArrayList();
		pid.add(policyId);
		policyInfo = policySendLogic.getLastVerPolicyInfo(Long.valueOf(policyId));
		if (policyInfo == null || policyInfo.getId() == null)
		{
			logger.error((new StringBuilder("找不到对应的保单信息,保单唯一数据库标识为：")).append(policyId).append("，当前发送邮件任务将被删除").toString());
			emailTaskLogic.deleteFailureTask(taskID);
			continue; /* Loop/switch isn't completed */
		}
		emailaccount = emailTaskLogic.getMailAccount(policyInfo.getChn(), "SEND");
		if (emailaccount == null || emailaccount.getId() == null)
		{
			logger.error((new StringBuilder("当前邮件任务无法发送，无可用的账户，或可用账户已经超出最大限制，渠道【")).append(policyInfo.getChn()).append("】").toString());
			emailTaskLogic.setEmailTaskStatus("-1", null, taskID);
			policyLogLogic.saveLogWithError(policyInfo.getId(), "11", "超过发送限制，无法发送保单邮件！", null);
			continue; /* Loop/switch isn't completed */
		}
		String filePath = (new StringBuilder(String.valueOf(ZonyConfig.getCheckPolicySUCCESSPath()))).append("/").append(policyInfo.getFilePath()).toString();
		File policyFile = new File(filePath);
		if (!policyFile.exists())
		{
			logger.error((new StringBuilder("找不到对应的保单附件:【")).append(filePath).append("】,当前发送邮件任务将被删除").toString());
			policySendLogic.setSendStatus(pid, "-5", DateUtil.getNow());
			policyLogLogic.saveLogWithError(policyInfo.getId(), "21", "保单邮件系统发送失败,保单附件不存在！", null);
			emailTaskLogic.deleteFailureTask(taskID);
			continue; /* Loop/switch isn't completed */
		}
		String ownerMail = policyInfo.getOwnerMail();
		if (!SendMailUtil.isEmail(ownerMail))
		{
			logger.error((new StringBuilder("用户邮箱校验未通过，格式不对:【")).append(ownerMail).append("】,当前发送邮件任务将被删除").toString());
			policySendLogic.setSendStatus(pid, "-4", DateUtil.getNow());
			policyLogLogic.saveLogWithError(Long.valueOf(policyId), "20", (new StringBuilder("保单邮件系统发送失败,用户邮箱校验未通过，格式不对:")).append(ownerMail).toString(), null);
			emailTaskLogic.deleteFailureTask(taskID);
			continue; /* Loop/switch isn't completed */
		}
		(new SendMailUtil()).doSendEmail(taskID, policyInfo, emailaccount);
		if ("-4".equals(policyInfo.getSendStatus()))
		{
			logger.error("用户邮箱不存在,当前发送邮件任务将被删除");
			policySendLogic.setSendStatus(pid, "-4", DateUtil.getNow());
			policyLogLogic.saveLogWithError(policyInfo.getId(), "20", "保单邮件系统发送失败,用户邮箱不存在！", null);
			emailTaskLogic.deleteFailureTask(taskID);
			continue; /* Loop/switch isn't completed */
		}
		try
		{
			policyLogLogic.saveLog(policyInfo.getId(), "11", null);
			policySendLogic.setSendStatus(pid, "4", DateUtil.getNow());
			emailTaskLogic.setEmailTaskStatus("2", emailaccount.getUserName(), taskID);
			emailTaskLogic.updateMailAccount(emailaccount, "SEND");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			List pid = new ArrayList();
			pid.add(policyId);
			policySendLogic.setSendStatus(pid, "-5", DateUtil.getNow());
			policyLogLogic.saveLogWithError(Long.valueOf(policyId), "21", e.getMessage(), null);
			emailTaskLogic.deleteFailureTask(taskID);
			logger.error("保单邮件系统发送失败,系统错误：", e);
		}
		continue; /* Loop/switch isn't completed */
		Thread.sleep(10000L);
		continue; /* Loop/switch isn't completed */
		Exception e;
		e;
		e.printStackTrace();
		logger.error("EmailTaskthread线程异常", e);
		if (true) goto _L2; else goto _L1
_L1:
	}
}
