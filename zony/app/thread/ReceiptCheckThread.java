// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ReceiptCheckThread.java

package com.zony.app.thread;

import com.zony.app.constants.Constant;
import com.zony.app.domain.EmailAccount;
import com.zony.app.domain.PolicyInfo;
import com.zony.app.logic.*;
import com.zony.common.util.EmailReceiveUtil;
import com.zony.common.util.Globals;
import java.io.PrintStream;
import java.util.*;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

public class ReceiptCheckThread extends Thread
{

	private final Logger logger = Logger.getLogger(com/zony/app/thread/ReceiptCheckThread);
	private final ApplicationContext context;
	private EmailTaskLogic emailTaskLogic;
	private PolicySendLogic policySendLogic;
	private PolicyLogLogic policyLogLogic;
	private EmailReceiveLogic emailReceiveLogic;

	public ReceiptCheckThread()
	{
		context = Globals.appContext;
		emailTaskLogic = null;
		policySendLogic = null;
		policyLogLogic = null;
		emailReceiveLogic = null;
	}

	public void run()
	{
		int sleepTime = 60000;
		do
		{
			emailTaskLogic = (EmailTaskLogic)context.getBean("emailTaskLogic");
			EmailAccount emailAccount = emailTaskLogic.getMailAccount(null, "RECEIPT");
			try
			{
				if (emailAccount != null && emailAccount.getId().longValue() > 0L)
				{
					emailReceiveLogic = (EmailReceiveLogic)context.getBean("emailReceiveLogic");
					Map receiptMap = EmailReceiveUtil.receiveEmail(emailAccount.getUserName(), emailAccount.getPassword(), emailReceiveLogic.getVemailKey());
					if (receiptMap != null && receiptMap.size() > 0)
					{
						for (Iterator iterator = receiptMap.entrySet().iterator(); iterator.hasNext();)
						{
							java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
							String emailCode = (String)entry.getKey();
							String sendStatus = ((String)entry.getValue()).substring(0, ((String)entry.getValue()).indexOf(Constant.STRSEPARATOR));
							String errorMassage = ((String)entry.getValue()).substring(((String)entry.getValue()).indexOf(Constant.STRSEPARATOR) + Constant.STRSEPARATOR.length());
							policySendLogic = (PolicySendLogic)context.getBean("policySendLogic");
							PolicyInfo policyInfo = policySendLogic.setSendStatusByEmailCode(emailCode, sendStatus);
							if (policyInfo != null && policyInfo.getId().longValue() > 0L)
							{
								policyLogLogic = (PolicyLogLogic)context.getBean("policyLogLogic");
								if ("-3".equals(sendStatus))
									policyLogLogic.saveLogWithError(policyInfo.getId(), "19", errorMassage, null);
								else
								if ("-4".equals(sendStatus))
									policyLogLogic.saveLogWithError(policyInfo.getId(), "20", errorMassage, null);
								else
								if ("-5".equals(sendStatus))
									policyLogLogic.saveLogWithError(policyInfo.getId(), "21", errorMassage, null);
							}
						}

					}
					emailAccount.setIsReceiving("0");
					emailTaskLogic.updateMailAccount(emailAccount, "RECEIPT");
				}
				System.out.println("当前无任务需要处理，线程进入休眠");
				Thread.sleep(60000L);
			}
			catch (Exception e)
			{
				emailAccount.setIsReceiving("0");
				emailTaskLogic.updateMailAccount(emailAccount, "RECEIPT");
				e.printStackTrace();
				logger.error("ReceiptThread线程异常");
				try
				{
					Thread.sleep(60000L);
				}
				catch (InterruptedException e1)
				{
					e1.printStackTrace();
				}
			}
		} while (true);
	}
}
