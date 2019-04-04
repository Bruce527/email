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
		System.out.println("��ʼɨ��ɷ��ʼ��ı���");
		if (StringUtils.isEmpty(policyId))
		{
			logger.error("�Ҳ�����Ӧ�ı�����Ϣ����ǰ�����ʼ����񽫱�ɾ��");
			emailTaskLogic.deleteFailureTask(taskID);
			continue; /* Loop/switch isn't completed */
		}
		List pid;
		pid = new ArrayList();
		pid.add(policyId);
		policyInfo = policySendLogic.getLastVerPolicyInfo(Long.valueOf(policyId));
		if (policyInfo == null || policyInfo.getId() == null)
		{
			logger.error((new StringBuilder("�Ҳ�����Ӧ�ı�����Ϣ,����Ψһ���ݿ��ʶΪ��")).append(policyId).append("����ǰ�����ʼ����񽫱�ɾ��").toString());
			emailTaskLogic.deleteFailureTask(taskID);
			continue; /* Loop/switch isn't completed */
		}
		emailaccount = emailTaskLogic.getMailAccount(policyInfo.getChn(), "SEND");
		if (emailaccount == null || emailaccount.getId() == null)
		{
			logger.error((new StringBuilder("��ǰ�ʼ������޷����ͣ��޿��õ��˻���������˻��Ѿ�����������ƣ�������")).append(policyInfo.getChn()).append("��").toString());
			emailTaskLogic.setEmailTaskStatus("-1", null, taskID);
			policyLogLogic.saveLogWithError(policyInfo.getId(), "11", "�����������ƣ��޷����ͱ����ʼ���", null);
			continue; /* Loop/switch isn't completed */
		}
		String filePath = (new StringBuilder(String.valueOf(ZonyConfig.getCheckPolicySUCCESSPath()))).append("/").append(policyInfo.getFilePath()).toString();
		File policyFile = new File(filePath);
		if (!policyFile.exists())
		{
			logger.error((new StringBuilder("�Ҳ�����Ӧ�ı�������:��")).append(filePath).append("��,��ǰ�����ʼ����񽫱�ɾ��").toString());
			policySendLogic.setSendStatus(pid, "-5", DateUtil.getNow());
			policyLogLogic.saveLogWithError(policyInfo.getId(), "21", "�����ʼ�ϵͳ����ʧ��,�������������ڣ�", null);
			emailTaskLogic.deleteFailureTask(taskID);
			continue; /* Loop/switch isn't completed */
		}
		String ownerMail = policyInfo.getOwnerMail();
		if (!SendMailUtil.isEmail(ownerMail))
		{
			logger.error((new StringBuilder("�û�����У��δͨ������ʽ����:��")).append(ownerMail).append("��,��ǰ�����ʼ����񽫱�ɾ��").toString());
			policySendLogic.setSendStatus(pid, "-4", DateUtil.getNow());
			policyLogLogic.saveLogWithError(Long.valueOf(policyId), "20", (new StringBuilder("�����ʼ�ϵͳ����ʧ��,�û�����У��δͨ������ʽ����:")).append(ownerMail).toString(), null);
			emailTaskLogic.deleteFailureTask(taskID);
			continue; /* Loop/switch isn't completed */
		}
		(new SendMailUtil()).doSendEmail(taskID, policyInfo, emailaccount);
		if ("-4".equals(policyInfo.getSendStatus()))
		{
			logger.error("�û����䲻����,��ǰ�����ʼ����񽫱�ɾ��");
			policySendLogic.setSendStatus(pid, "-4", DateUtil.getNow());
			policyLogLogic.saveLogWithError(policyInfo.getId(), "20", "�����ʼ�ϵͳ����ʧ��,�û����䲻���ڣ�", null);
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
			logger.error("�����ʼ�ϵͳ����ʧ��,ϵͳ����", e);
		}
		continue; /* Loop/switch isn't completed */
		Thread.sleep(10000L);
		continue; /* Loop/switch isn't completed */
		Exception e;
		e;
		e.printStackTrace();
		logger.error("EmailTaskthread�߳��쳣", e);
		if (true) goto _L2; else goto _L1
_L1:
	}
}
