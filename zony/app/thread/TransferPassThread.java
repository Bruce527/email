// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TransferPassThread.java

package com.zony.app.thread;

import com.zony.app.logic.TransferPASSServiceLogic;
import com.zony.common.config.ZonyConfig;
import com.zony.common.util.ExceptionUtils;
import com.zony.common.util.Globals;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

public class TransferPassThread extends Thread
{

	private final Logger logger = Logger.getLogger(com/zony/app/thread/TransferPassThread);
	private final ApplicationContext context;
	private TransferPASSServiceLogic transferPASSServiceLogic;

	public TransferPassThread()
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
					transferPASSServiceLogic.setPolicyInfoListToPass();
					int passTime = Integer.parseInt(ZonyConfig.getTranPassTime());
					logger.debug((new StringBuilder("TransferPassThread线程执行间隔:")).append(passTime).toString());
					Thread.sleep(0x36ee80 * passTime);
				} while (true);
			}
			catch (Exception e)
			{
				logger.error((new StringBuilder("TransferPassThread线程异常")).append(ExceptionUtils.getErrorTraceMessage(e)).toString());
				e.printStackTrace();
			}
		while (true);
	}
}
