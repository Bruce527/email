// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   UploadReceiptServlet.java

package com.zony.common.servlet;

import com.zony.app.constants.AppGlobals;
import com.zony.common.util.JexcelFiles;
import com.zony.common.util.ReadXLS2Model;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;

public class UploadReceiptServlet extends HttpServlet
{

	private static final long serialVersionUID = 1L;

	public UploadReceiptServlet()
	{
	}

	public void destroy()
	{
		super.destroy();
	}

	public void init(ServletConfig config)
		throws ServletException
	{
		super.init(config);
	}

	protected synchronized void processRequest(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException
	{
		try
		{
			String type = request.getParameter("type");
			if (StringUtils.isNotEmpty(type) && type.equals("receipt"))
			{
				String userCode = request.getParameter("userCode");
				if (StringUtils.isEmpty(userCode))
					throw new IOException("上传Excel电话回执文件异常,无法获取当前用户信息");
				DiskFileItemFactory factory = new DiskFileItemFactory();
				factory.setSizeThreshold(0x500000);
				File tempFile = File.createTempFile("Custmer", ".xls");
				factory.setRepository(tempFile);
				ServletFileUpload fu = new ServletFileUpload(factory);
				List fileItems = fu.parseRequest(request);
				for (Iterator iter = fileItems.iterator(); iter.hasNext();)
				{
					FileItem item = (FileItem)iter.next();
					if (!item.isFormField())
					{
						String name = item.getName();
						long size = item.getSize();
						if (name != null && !name.equals("") || size != 0L)
							try
							{
								item.write(tempFile);
							}
							catch (Exception e)
							{
								e.printStackTrace();
								throw new IOException("上传Excel电话回执文件异常", e);
							}
					}
				}

				List reList = null;
				if (JexcelFiles.isExcel2003(tempFile.getPath()))
					reList = ReadXLS2Model.readXls(tempFile.getPath());
				else
					reList = ReadXLS2Model.readXlsx(tempFile.getPath());
				if (reList != null && reList.size() > 0)
					AppGlobals.setTempReceipt(userCode, reList);
				else
					AppGlobals.setTempReceipt(userCode, new ArrayList());
				if (tempFile != null && !tempFile.getPath().equals(""))
					tempFile.delete();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IOException("上传Excel电话回执文件异常", e);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException
	{
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException
	{
		processRequest(request, response);
	}
}
