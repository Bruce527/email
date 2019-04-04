// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FileUtil.java

package com.zony.common.util;

import java.io.*;
import org.apache.log4j.Logger;

public class FileUtil
{

	public static Logger log = Logger.getLogger(com/zony/common/util/FileUtil);

	public FileUtil()
	{
	}

	public static boolean copyFiles(String sourcePath, String targetPath)
	{
		FileInputStream fis;
		FileOutputStream fos;
		boolean copyflag;
		fis = null;
		fos = null;
		copyflag = true;
		try
		{
			File srcfile = new File(sourcePath);
			File tarfile = new File(targetPath);
			File dirFolder = tarfile.getParentFile();
			if (!dirFolder.exists())
				dirFolder.mkdirs();
			if (srcfile.exists())
			{
				fis = new FileInputStream(srcfile);
				fos = new FileOutputStream(tarfile);
				byte buffer[] = new byte[20480];
				for (int count = 0; (count = fis.read(buffer)) != -1;)
					fos.write(buffer, 0, count);

			} else
			{
				copyflag = false;
				log.debug((new StringBuilder("电子文件不存在：")).append(sourcePath).toString());
			}
			break MISSING_BLOCK_LABEL_251;
		}
		catch (Exception e)
		{
			copyflag = false;
			e.printStackTrace();
			log.error(e.toString());
		}
		try
		{
			if (fis != null)
				fis.close();
			if (fos != null)
				fos.close();
		}
		catch (Exception e)
		{
			copyflag = false;
			e.printStackTrace();
			log.error(e.toString());
		}
		break MISSING_BLOCK_LABEL_291;
		Exception exception;
		exception;
		try
		{
			if (fis != null)
				fis.close();
			if (fos != null)
				fos.close();
		}
		catch (Exception e)
		{
			copyflag = false;
			e.printStackTrace();
			log.error(e.toString());
		}
		throw exception;
		try
		{
			if (fis != null)
				fis.close();
			if (fos != null)
				fos.close();
		}
		catch (Exception e)
		{
			copyflag = false;
			e.printStackTrace();
			log.error(e.toString());
		}
		return copyflag;
	}

	public static boolean copyFilesFromStream(InputStream fromFileStream, String to_name)
		throws IOException
	{
		boolean flag = true;
		File to_file;
		InputStream from;
		FileOutputStream to;
		to_file = new File(to_name);
		if (to_file.exists() && !to_file.canWrite())
		{
			flag = false;
			throw new IOException((new StringBuilder("目标文件不是可写文件：")).append(to_name).toString());
		}
		String parent = to_file.getParent();
		File dir = new File(parent);
		if (!dir.exists())
		{
			boolean b = dir.mkdirs();
			if (!b)
			{
				flag = false;
				throw new IOException((new StringBuilder("目标目录不存在。 ")).append(parent).toString());
			}
		}
		if (dir.isFile())
		{
			flag = false;
			throw new IOException((new StringBuilder("指定的目标不是目录 ")).append(parent).toString());
		}
		if (!dir.canWrite())
		{
			flag = false;
			throw new IOException((new StringBuilder("指定的目标目录不是可写的 ")).append(parent).toString());
		}
		from = null;
		to = null;
		from = fromFileStream;
		to = new FileOutputStream(to_file);
		byte buffer[] = new byte[20480];
		int bytes_read;
		while ((bytes_read = from.read(buffer)) != -1) 
			to.write(buffer, 0, bytes_read);
		if (from != null)
			try
			{
				from.close();
			}
			catch (IOException e)
			{
				flag = false;
			}
		if (to != null)
			try
			{
				to.flush();
				to.close();
			}
			catch (IOException e)
			{
				flag = false;
			}
		return true;
		IOException ex;
		ex;
		flag = false;
		if (from != null)
			try
			{
				from.close();
			}
			catch (IOException e)
			{
				flag = false;
			}
		if (to != null)
			try
			{
				to.flush();
				to.close();
			}
			catch (IOException e)
			{
				flag = false;
			}
		break MISSING_BLOCK_LABEL_372;
		Exception exception;
		exception;
		if (from != null)
			try
			{
				from.close();
			}
			catch (IOException e)
			{
				flag = false;
			}
		if (to != null)
			try
			{
				to.flush();
				to.close();
			}
			catch (IOException e)
			{
				flag = false;
			}
		throw exception;
		RuntimeException re;
		re;
		throw re;
		return flag;
	}

	public static boolean writeFile(String fileName, String fileBody, String charsetName)
	{
		FileOutputStream fos;
		OutputStreamWriter osw;
		fos = null;
		osw = null;
		fos = new FileOutputStream(fileName);
		osw = new OutputStreamWriter(fos, charsetName);
		osw.write(fileBody);
		if (osw != null)
			try
			{
				osw.close();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
		if (fos != null)
			try
			{
				fos.flush();
				fos.close();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
		return true;
		Exception e;
		e;
		e.printStackTrace();
		if (osw != null)
			try
			{
				osw.close();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
		if (fos != null)
			try
			{
				fos.flush();
				fos.close();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
		return false;
		Exception exception;
		exception;
		if (osw != null)
			try
			{
				osw.close();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
		if (fos != null)
			try
			{
				fos.flush();
				fos.close();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
		throw exception;
	}

	public static boolean CreateDir(String filepath)
	{
		File dir = new File(filepath);
		if (dir.isFile())
			return false;
		if (dir.exists())
			return true;
		return dir.mkdirs();
		Exception ex;
		ex;
		return false;
	}

}
