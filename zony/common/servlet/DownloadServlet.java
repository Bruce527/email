// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DownloadServlet.java

package com.zony.common.servlet;

import com.zony.app.logic.LoginLogic;
import com.zony.app.logic.PolicySendLogic;
import com.zony.common.config.ZonyConfig;
import com.zony.common.util.Globals;
import java.io.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;

public class DownloadServlet extends HttpServlet
{

	private static final long serialVersionUID = 1L;
	private ApplicationContext context;
	LoginLogic loginLogic;

	public DownloadServlet()
	{
		context = null;
	}

	public void init(ServletConfig servletconfig)
		throws ServletException
	{
	}

	public void destroy()
	{
	}

	protected void service(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException
	{
		context = Globals.appContext;
		String type = request.getParameter("type");
		if (StringUtils.isNotEmpty(type) && "pdf".equals(type.toLowerCase()))
		{
			String id = request.getParameter("id");
			if (StringUtils.isNotEmpty(id) && Integer.parseInt(id) > 0)
			{
				PolicySendLogic policySendLogic = (PolicySendLogic)context.getBean("policySendLogic");
				String filePath = policySendLogic.getFilePath(Long.valueOf(id));
				if (StringUtils.isNotEmpty(filePath))
				{
					InputStream is = new FileInputStream(new File((new StringBuilder(String.valueOf(ZonyConfig.getCheckPolicySUCCESSPath()))).append(filePath).toString()));
					OutputStream ops = response.getOutputStream();
					response.setContentType("application/pdf;charset=UTF-8");
					byte b[] = new byte[1024];
					int len;
					while ((len = is.read(b)) > 0) 
						ops.write(b, 0, len);
					is.close();
				} else
				{
					response.getWriter().print("无法获取文件".getBytes());
				}
			} else
			{
				response.getWriter().print("无法获取文件".getBytes());
			}
		} else
		if (StringUtils.isNotEmpty(type) && "xls".equals(type.toLowerCase()))
		{
			String filePath = request.getParameter("filePath");
			String flag = request.getParameter("flag");
			System.out.println((new StringBuilder(String.valueOf(filePath))).append("**************************************************").toString());
			if (StringUtils.isNotEmpty(filePath))
			{
				File xlsFile = new File(filePath);
				InputStream is = new FileInputStream(xlsFile);
				OutputStream ops = response.getOutputStream();
				if ("fsjg".equals(flag))
					response.setHeader("Content-disposition", (new StringBuilder("attachment;filename=")).append(new String("导出发送结果报表".getBytes("GBK"), "ISO-8859-1")).append(System.currentTimeMillis()).append(".xls").toString());
				else
				if ("bdcj".equals(flag))
					response.setHeader("Content-disposition", (new StringBuilder("attachment;filename=")).append(new String("导出保单抽检记录查询结果报表".getBytes("GBK"), "ISO-8859-1")).append(System.currentTimeMillis()).append(".xls").toString());
				else
				if ("bdxx".equals(flag))
					response.setHeader("Content-disposition", (new StringBuilder("attachment;filename=")).append(new String("导出保单信息查询结果报表".getBytes("GBK"), "ISO-8859-1")).append(System.currentTimeMillis()).append(".xls").toString());
				else
				if ("bdrz".equals(flag))
					response.setHeader("Content-disposition", (new StringBuilder("attachment;filename=")).append(new String("导出保单日志记录查询结果报表".getBytes("GBK"), "ISO-8859-1")).append(System.currentTimeMillis()).append(".xls").toString());
				response.setHeader("Pragma", "no-cache");
				response.setContentType("application/vnd.ms-excel;charset=UTF-8");
				response.setContentLength(Integer.parseInt(String.valueOf(xlsFile.length())));
				byte b[] = new byte[1024];
				int len;
				while ((len = is.read(b)) > 0) 
					ops.write(b, 0, len);
				is.close();
				if (xlsFile.exists())
					xlsFile.delete();
			} else
			{
				response.getWriter().print("无法获取文件".getBytes("GBK"));
			}
		} else
		if (StringUtils.isNotEmpty(type) && "laxls".equals(type.toLowerCase()))
		{
			String filePath = request.getParameter("filePath");
			if (StringUtils.isNotEmpty(filePath))
			{
				File xlsFile = new File(filePath);
				InputStream is = new FileInputStream(xlsFile);
				OutputStream ops = response.getOutputStream();
				response.setHeader("Content-disposition", (new StringBuilder("attachment;filename=")).append(new String("导出LA系统回执报表".getBytes("GBK"), "ISO-8859-1")).append(System.currentTimeMillis()).append(".xls").toString());
				response.setHeader("Pragma", "no-cache");
				response.setContentType("application/vnd.ms-excel;charset=UTF-8");
				response.setContentLength(Integer.parseInt(String.valueOf(xlsFile.length())));
				byte b[] = new byte[1024];
				int len;
				while ((len = is.read(b)) > 0) 
					ops.write(b, 0, len);
				is.close();
				if (xlsFile.exists())
					xlsFile.delete();
			} else
			{
				response.getWriter().print("无法获取文件".getBytes("GBK"));
			}
		}
	}
}
