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
				logger.debug((new StringBuilder("当前CHECK_POLICYNOTICE_FLAG 标识为............")).append(CHECK_POLICYNOTICE_FLAG).toString());
				while (CHECK_POLICYNOTICE_FLAG.equals("1")) 
				{
					String path[] = null;
					try
					{
						path = PolicyImportLogic.getLocalFolePathName();
					}
					catch (Exception e1)
					{
						logger.error("扫描文档文件映射文件夹发生异常；", e1);
					}
					if (path != null && path[0] != null)
					{
						String as[];
						int j = (as = path).length;
						for (int i = 0; i < j; i++)
						{
							String string = as[i];
							logger.debug((new StringBuilder("获取文件路径信息：")).append(string).toString());
						}

						try
						{
							logger.debug("policyImportLogic.importPolicyDataRead(path); 开始（Begin）解析导入XML保单数据信息到数据库！");
							policyImportLogic.importPolicyDataRead(path);
							logger.debug("policyImportLogic.importPolicyDataRead(path); 结束（End）解析导入XML保单数据信息到数据库！");
						}
						catch (Exception e)
						{
							logger.error((new StringBuilder("执行解析导入保单XML数据时出错,失败时间：")).append(DateUtil.getNow()).append(" 异常信息：").append(ExceptionUtils.getErrorTraceMessage(e)).toString());
						}
					} else
					{
						try
						{
							logger.debug((new StringBuilder("暂时没有保单需要导入，系统休眠中..... 休眠时间为：")).append(ZonyConfig.getCheck_ImporPolicyWatingtime()).toString());
							Thread.sleep(ZonyConfig.getCheck_ImporPolicyWatingtime());
						}
						catch (Exception e)
						{
							logger.error((new StringBuilder("导入线程休眠时出错：")).append(DateUtil.getNow()).append(" 异常信息：").append(ExceptionUtils.getErrorTraceMessage(e)).toString());
						}
					}
				}
			} while (CHECK_POLICYNOTICE_FLAG.equals("1"));
			try
			{
				logger.debug((new StringBuilder("当前CHECK_POLICYNOTICE_FLAG 标识为............")).append(CHECK_POLICYNOTICE_FLAG).append("线程循环进入休眠模式！导入线程暂停服务！").toString());
				Thread.sleep(ZonyConfig.getCheck_ImporPolicyWatingtime());
			}
			catch (Exception e)
			{
				logger.error((new StringBuilder("导入线程休眠时出错：")).append(DateUtil.getNow()).append(" 异常信息：").append(ExceptionUtils.getErrorTraceMessage(e)).toString());
			}
		} while (true);
	}

}
