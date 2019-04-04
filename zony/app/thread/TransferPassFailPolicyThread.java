// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TransferPassFailPolicyThread.java

package com.zony.app.thread;

import com.zony.app.logic.TransferPASSServiceLogic;
import com.zony.common.config.ZonyConfig;
import com.zony.common.util.Globals;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

public class TransferPassFailPolicyThread extends Thread
{

	private final Logger logger = Logger.getLogger(com/zony/app/thread/TransferPassFailPolicyThread);
	private final ApplicationContext context;
	private TransferPASSServiceLogic transferPASSServiceLogic;

	public TransferPassFailPolicyThread()
	{
		context = Globals.appContext;
		transferPASSServiceLogic = null;
	}

	public void run()
	{
		do
			try
			{
				do
				{
					transferPASSServiceLogic = (TransferPASSServiceLogic)context.getBean("transferPASSServiceLogic");
					transferPASSServiceLogic.setPassPolicyByLog();
					int againTime = Integer.parseInt(ZonyConfig.getTranAgainTime());
					logger.debug((new StringBuilder("TransferPassFailPolicyThread线程执行间隔时间：")).append(againTime).toString());
					Thread.sleep(0x36ee80 * againTime);
				} while (true);
			}
			catch (Exception e)
			{
				logger.error((new StringBuilder("TransferPassFailPolicyThread线程异常")).append(e.getMessage()).toString(), e);
				e.printStackTrace();
			}
		while (true);
	}
}
