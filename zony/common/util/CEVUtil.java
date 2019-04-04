// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CEVUtil.java

package com.zony.common.util;

import java.io.*;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class CEVUtil
{

	public CEVUtil()
	{
	}

	public static boolean isExcel(String filePath)
	{
		return filePath.matches("^.+\\.(?i)(xls)$") || filePath.matches("^.+\\.(?i)(xlsx)$");
	}

	public static boolean fileExist(String filePath)
	{
		if (filePath == null || filePath.trim().equals(""))
			return false;
		File file = new File(filePath);
		return file != null && file.exists();
	}

	public boolean isExcel2003(String filePath)
	{
label0:
		{
			try
			{
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath));
				if (!POIFSFileSystem.hasPOIFSHeader(bis))
					break label0;
				System.out.println("Excel版本为excel2003及以下");
			}
			catch (IOException e)
			{
				e.printStackTrace();
				return false;
			}
			return true;
		}
		return false;
	}

	public static boolean isExcel2007(String filePath)
	{
label0:
		{
			try
			{
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath));
				if (!POIXMLDocument.hasOOXMLHeader(bis))
					break label0;
				System.out.println("Excel版本为excel2007及以上");
			}
			catch (IOException e)
			{
				e.printStackTrace();
				return false;
			}
			return true;
		}
		return false;
	}
}
