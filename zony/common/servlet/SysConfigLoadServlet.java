// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SysConfigLoadServlet.java

package com.zony.common.servlet;

import com.zony.app.domain.SysFlag;
import com.zony.app.logic.*;
import com.zony.app.thread.*;
import com.zony.common.config.ZonyConfig;
import com.zony.common.util.Globals;
import java.io.PrintStream;
import java.math.BigDecimal;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SysConfigLoadServlet extends HttpServlet
{

	WebApplicationContext webfactory;

	public SysConfigLoadServlet()
	{
		webfactory = null;
	}

	public void destroy()
	{
		super.destroy();
	}

	public void init()
		throws ServletException
	{
		ServletContext application = getServletContext();
		webfactory = WebApplicationContextUtils.getRequiredWebApplicationContext(application);
		String path = getServletContext().getRealPath("/");
		if (path != null)
		{
			System.out.println((new StringBuilder("getServletContext().getRealPath=")).append(path).toString());
		} else
		{
			path = getInitParameter("sysRootDir");
			System.out.println((new StringBuilder("getRealPath == null.从配置文件中获取系统路径：getInitParameter('sysRootDir')=")).append(path).toString());
		}
		String sysRootDir = path;
		if (sysRootDir != null)
		{
			for (sysRootDir = sysRootDir.trim(); sysRootDir.endsWith("/") || sysRootDir.endsWith("\\"); sysRootDir = sysRootDir.substring(0, sysRootDir.length() - 1));
			Globals.setSysRootDir(sysRootDir);
			System.setProperty("sysRootDir", sysRootDir);
			System.out.println((new StringBuilder("sysRootDir=")).append(sysRootDir).toString());
		} else
		{
			System.out.println("web.xml 中配置sysRootDir的不正确！");
			System.exit(1);
		}
		String springContextConfig[] = {
			"applicationContext[2]-db-inner.xml", "applicationContext[3]-db-outter.xml", "applicationContext[4]-proxy.xml", "applicationContext[5]-service.xml"
		};
		Globals.appContext = new ClassPathXmlApplicationContext(springContextConfig);
		beanUtilsConvertRegist();
		System.out.println("邮件发送线程及回执签收线程将启动");
		startThread();
		SystemSetLogic systemsetLogic = (SystemSetLogic)Globals.appContext.getBean("systemSetLogic");
		PolicyImportThread.CHECK_POLICYNOTICE_FLAG = systemsetLogic.getCurrentImportSwitch().getFlagValue();
		PolicyImportThread.SEND_POLICYNOTICE_FLAG = systemsetLogic.getCurrentSendSwitch().getFlagValue();
		PolicyImportLogic.StartPolicyImportThread();
		EmailTaskLogic emailTaskLogic = (EmailTaskLogic)Globals.appContext.getBean("emailTaskLogic");
		emailTaskLogic.initEmailAccount();
		emailTaskLogic.reAddEmailTask();
	}

	private void beanUtilsConvertRegist()
	{
		ConvertUtils.register(new LongConverter(null), java/lang/Long);
		ConvertUtils.register(new ShortConverter(null), java/lang/Short);
		ConvertUtils.register(new IntegerConverter(null), java/lang/Integer);
		ConvertUtils.register(new DoubleConverter(null), java/lang/Double);
		ConvertUtils.register(new BigDecimalConverter(null), java/math/BigDecimal);
	}

	public void startThread()
	{
		for (int i = 0; i < ZonyConfig.getSendThreadNumber(); i++)
		{
			Thread emailTT = new EmailTaskThread();
			emailTT.start();
			System.out.println((new StringBuilder("i=")).append(i).append("<").append(ZonyConfig.getSendThreadNumber()).toString());
		}

		for (int i = 0; i < ZonyConfig.getReceiptThreadNumber(); i++)
		{
			Thread receiptCheckT = new ReceiptCheckThread();
			receiptCheckT.start();
		}

		System.out.println("邮件发送线程及回执签收线程已启动");
		TransferPassFailPolicyThread passFailThread = new TransferPassFailPolicyThread();
		passFailThread.start();
		TransferPassThread passThread = new TransferPassThread();
		passThread.start();
	}
}
