// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PolicyImportThread.java

package com.zony.app.thread;

import com.zony.app.logic.PolicyImportLogic;
import com.zony.common.config.ZonyConfig;
import com.zony.common.util.*;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

public class PolicyImportThread extends Thread
{

	public static String CHECK_POLICYNOTICE_FLAG = "1";
	public static String SEND_POLICYNOTICE_FLAG = "0";
	private final ApplicationContext context;
	private final PolicyImportLogic policyImportLogic;
	private final Logger logger = Logger.getLogger(com/zony/app/thread/PolicyImportThread);

	public PolicyImportThread()
	{
		context = Globals.appContext;
		policyImportLogic = (PolicyImportLogic)context.getBean("policyImportLogic");
	}

	public void run()
	{
		do
		{
			do
			{
				logger.debug((new StringBuilder("��ǰCHECK_POLICYNOTICE_FLAG ��ʶΪ............")).append(CHECK_POLICYNOTICE_FLAG).toString());
				while (CHECK_POLICYNOTICE_FLAG.equals("1")) 
				{
					String path[] = null;
					try
					{
						path = PolicyImportLogic.getLocalFolePathName();
					}
					catch (Exception e1)
					{
						logger.error("ɨ���ĵ��ļ�ӳ���ļ��з����쳣��", e1);
					}
					if (path != null && path[0] != null)
					{
						String as[];
						int j = (as = path).length;
						for (int i = 0; i < j; i++)
						{
							String string = as[i];
							logger.debug((new StringBuilder("��ȡ�ļ�·����Ϣ��")).append(string).toString());
						}

						try
						{
							logger.debug("policyImportLogic.importPolicyDataRead(path); ��ʼ��Begin����������XML����������Ϣ�����ݿ⣡");
							policyImportLogic.importPolicyDataRead(path);
							logger.debug("policyImportLogic.importPolicyDataRead(path); ������End����������XML����������Ϣ�����ݿ⣡");
						}
						catch (Exception e)
						{
							logger.error((new StringBuilder("ִ�н������뱣��XML����ʱ����,ʧ��ʱ�䣺")).append(DateUtil.getNow()).append(" �쳣��Ϣ��").append(ExceptionUtils.getErrorTraceMessage(e)).toString());
						}
					} else
					{
						try
						{
							logger.debug((new StringBuilder("��ʱû�б�����Ҫ���룬ϵͳ������..... ����ʱ��Ϊ��")).append(ZonyConfig.getCheck_ImporPolicyWatingtime()).toString());
							Thread.sleep(ZonyConfig.getCheck_ImporPolicyWatingtime());
						}
						catch (Exception e)
						{
							logger.error((new StringBuilder("�����߳�����ʱ����")).append(DateUtil.getNow()).append(" �쳣��Ϣ��").append(ExceptionUtils.getErrorTraceMessage(e)).toString());
						}
					}
				}
			} while (CHECK_POLICYNOTICE_FLAG.equals("1"));
			try
			{
				logger.debug((new StringBuilder("��ǰCHECK_POLICYNOTICE_FLAG ��ʶΪ............")).append(CHECK_POLICYNOTICE_FLAG).append("�߳�ѭ����������ģʽ�������߳���ͣ����").toString());
				Thread.sleep(ZonyConfig.getCheck_ImporPolicyWatingtime());
			}
			catch (Exception e)
			{
				logger.error((new StringBuilder("�����߳�����ʱ����")).append(DateUtil.getNow()).append(" �쳣��Ϣ��").append(ExceptionUtils.getErrorTraceMessage(e)).toString());
			}
		} while (true);
	}

}
