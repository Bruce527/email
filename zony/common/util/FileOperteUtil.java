// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FileOperteUtil.java

package com.zony.common.util;

import java.io.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

// Referenced classes of package com.zony.common.util:
//			DateUtil, ExceptionUtils

public class FileOperteUtil
{

	public static Logger logger = Logger.getLogger(com/zony/common/util/FileOperteUtil);

	public FileOperteUtil()
	{
	}

	public static void newFolder(String folderPath)
	{
		try
		{
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists())
				myFilePath.mkdir();
		}
		catch (Exception e)
		{
			logger.error((new StringBuilder("新建目录操作出错,失败时间：")).append(DateUtil.getNow()).append(" 异常信息：").append(ExceptionUtils.getErrorTraceMessage(e)).toString());
		}
	}

	public static void newFile(String filePathAndName, String fileContent)
	{
		try
		{
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists())
				myFilePath.createNewFile();
			FileWriter resultFile = new FileWriter(myFilePath);
			PrintWriter myFile = new PrintWriter(resultFile);
			String strContent = fileContent;
			myFile.println(strContent);
			resultFile.close();
		}
		catch (Exception e)
		{
			logger.error((new StringBuilder("新建目录操作出错,失败时间：")).append(DateUtil.getNow()).append(" 异常信息：").append(ExceptionUtils.getErrorTraceMessage(e)).toString());
		}
	}

	public static void delFileByFilType(String filePath, String startfileName, String endtype)
	{
		try
		{
			String neddDriArr[] = getAllFilesByPath(filePath);
			if (neddDriArr != null)
			{
				String as[];
				int j = (as = neddDriArr).length;
				for (int i = 0; i < j; i++)
				{
					String fileName = as[i];
					if (!StringUtils.isEmpty(fileName) && fileName.startsWith(startfileName) && fileName.endsWith(endtype))
					{
						String delpath = (new StringBuilder(String.valueOf(filePath))).append(File.separator).append(fileName).toString();
						System.out.println((new StringBuilder(" file will be delete ")).append(delpath).toString());
						delFile(delpath);
					}
				}

			}
		}
		catch (Exception e)
		{
			logger.error((new StringBuilder("删除文件操作出错,失败时间：")).append(DateUtil.getNow()).append(" 异常信息：").append(ExceptionUtils.getErrorTraceMessage(e)).toString());
		}
	}

	public static void delFile(String filePathAndName)
	{
		try
		{
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myDelFile = new File(filePath);
			myDelFile.delete();
		}
		catch (Exception e)
		{
			logger.error((new StringBuilder("删除文件操作出错,失败时间：")).append(DateUtil.getNow()).append(" 异常信息：").append(ExceptionUtils.getErrorTraceMessage(e)).toString());
		}
	}

	public static void delFolder(String folderPath)
	{
		try
		{
			delAllFile(folderPath);
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			myFilePath.delete();
		}
		catch (Exception e)
		{
			logger.error((new StringBuilder("删除文件夹操作出错,失败时间：")).append(DateUtil.getNow()).append(" 异常信息：").append(ExceptionUtils.getErrorTraceMessage(e)).toString());
		}
	}

	public static void delAllFile(String path)
	{
		File file = new File(path);
		if (!file.exists())
			return;
		if (!file.isDirectory())
			return;
		String tempList[] = file.list();
		File temp = null;
		String as[];
		int j = (as = tempList).length;
		for (int i = 0; i < j; i++)
		{
			String element = as[i];
			if (path.endsWith(File.separator))
				temp = new File((new StringBuilder(String.valueOf(path))).append(element).toString());
			else
				temp = new File((new StringBuilder(String.valueOf(path))).append(File.separator).append(element).toString());
			if (temp.isFile())
				temp.delete();
			if (temp.isDirectory())
			{
				delAllFile((new StringBuilder(String.valueOf(path))).append("/").append(element).toString());
				delFolder((new StringBuilder(String.valueOf(path))).append("/").append(element).toString());
			}
		}

	}

	public static void copyFile(String oldPath, String newPath)
	{
		try
		{
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists())
			{
				InputStream inStream = new FileInputStream(oldPath);
				FileOutputStream fs = new FileOutputStream(newPath);
				byte buffer[] = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) 
				{
					bytesum += byteread;
					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		}
		catch (Exception e)
		{
			logger.error((new StringBuilder("复制单个文件操作出错,失败时间：")).append(DateUtil.getNow()).append(" 异常信息：").append(ExceptionUtils.getErrorTraceMessage(e)).toString());
		}
	}

	public static void copyFolder(String oldPath, String newPath)
	{
		try
		{
			(new File(newPath)).mkdirs();
			File a = new File(oldPath);
			String file[] = a.list();
			File temp = null;
			String as[];
			int j = (as = file).length;
			for (int i = 0; i < j; i++)
			{
				String element = as[i];
				if (oldPath.endsWith(File.separator))
					temp = new File((new StringBuilder(String.valueOf(oldPath))).append(element).toString());
				else
					temp = new File((new StringBuilder(String.valueOf(oldPath))).append(File.separator).append(element).toString());
				if (temp.isFile())
				{
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream((new StringBuilder(String.valueOf(newPath))).append("/").append(temp.getName().toString()).toString());
					byte b[] = new byte[5120];
					int len;
					while ((len = input.read(b)) != -1) 
						output.write(b, 0, len);
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory())
					copyFolder((new StringBuilder(String.valueOf(oldPath))).append("/").append(element).toString(), (new StringBuilder(String.valueOf(newPath))).append("/").append(element).toString());
			}

		}
		catch (Exception e)
		{
			logger.error((new StringBuilder("复制整个文件夹内容操作出错,出错路径")).append(oldPath).append("--目标路径").append(newPath).append(" 失败时间：").append(DateUtil.getNow()).append(" 异常信息：").append(ExceptionUtils.getErrorTraceMessage(e)).toString());
			e.printStackTrace();
		}
	}

	public static int getFileSize(String filePath)
	{
		int size = 0;
		try
		{
			InputStream f1 = new FileInputStream(filePath);
			size = f1.available();
		}
		catch (Exception e)
		{
			logger.error((new StringBuilder("获取文件字节数,方法出错,失败时间：")).append(DateUtil.getNow()).append(" 异常信息：").append(ExceptionUtils.getErrorTraceMessage(e)).toString());
		}
		return size;
	}

	public static void moveFile(String oldPath, String newPath)
	{
		copyFile(oldPath, newPath);
		delFile(oldPath);
	}

	public static void moveFolder(String oldPath, String newPath)
	{
		copyFolder(oldPath, newPath);
		System.gc();
		deleteDirectory(oldPath);
	}

	public static String[] getAllFilesByPath(String filepath)
	{
		File file = new File(filepath);
		String test[] = file.list();
		if (test != null)
		{
			String as[];
			int j = (as = test).length;
			for (int i = 0; i < j; i++)
			{
				String element = as[i];
				System.out.println(element);
			}

		}
		return test;
	}

	public static boolean deleteFile(String sPath)
	{
		boolean flag = false;
		File file = new File(sPath);
		if (file.isFile() && file.exists())
		{
			file.delete();
			flag = true;
		}
		return flag;
	}

	public static boolean deleteDirectory(String sPath)
	{
		if (!sPath.endsWith(File.separator))
			sPath = (new StringBuilder(String.valueOf(sPath))).append(File.separator).toString();
		File dirFile = new File(sPath);
		if (!dirFile.exists() || !dirFile.isDirectory())
			return false;
		boolean flag = true;
		File files[] = dirFile.listFiles();
		File afile[];
		int j = (afile = files).length;
		for (int i = 0; i < j; i++)
		{
			File file = afile[i];
			if (file.isFile() ? !(flag = deleteFile(file.getAbsolutePath())) : !(flag = deleteDirectory(file.getAbsolutePath())))
				break;
		}

		if (!flag)
			return false;
		return dirFile.delete();
	}

	public static void main(String args[])
	{
		delFileByFilType("E:\\apache-tomcat-6.0.37-windows-x64\\apache-tomcat-6.0.37", "temp", ".zip");
	}

}
