// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FtpUtils.java

package com.zony.common.util;

import com.zony.common.config.ZonyConfig;
import java.io.*;
import java.util.*;
import org.apache.commons.net.ftp.*;
import org.apache.log4j.Logger;

public class FtpUtils
{

	public static FTPClient ftpClient;
	public static String strIp;
	public static int intPort;
	public static String user;
	public static String password;
	private static Logger logger = Logger.getLogger(com/zony/common/util/FtpUtils.getName());

	public FtpUtils(String strIp, int intPort, String user, String Password)
	{
		strIp = strIp;
		intPort = intPort;
		user = user;
		password = Password;
		ftpClient = new FTPClient();
	}

	public FtpUtils()
	{
		strIp = ZonyConfig.getFTP_Server();
		intPort = ZonyConfig.getFTP_Port();
		user = ZonyConfig.getFTP_userUserName();
		password = ZonyConfig.getFTP_userPassword();
		ftpClient = new FTPClient();
	}

	public static boolean ftpLogin()
	{
		boolean isLogin;
		isLogin = false;
		FTPClientConfig ftpClientConfig = new FTPClientConfig();
		ftpClientConfig.setServerTimeZoneId(TimeZone.getDefault().getID());
		ftpClient.setControlEncoding("GBK");
		ftpClient.configure(ftpClientConfig);
		if (intPort > 0)
			ftpClient.connect(strIp, intPort);
		else
			ftpClient.connect(strIp);
		int reply = ftpClient.getReplyCode();
		if (FTPReply.isPositiveCompletion(reply))
			break MISSING_BLOCK_LABEL_103;
		ftpClient.disconnect();
		logger.error("登录FTP服务失败！");
		System.out.println("connection FTP error !!!");
		return isLogin;
		try
		{
			ftpClient.login(user, password);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(2);
			logger.info((new StringBuilder("恭喜")).append(user).append("成功登陆FTP服务器").toString());
			isLogin = true;
		}
		catch (Exception e)
		{
			System.out.println("connection FTP error !!!  Bug ");
			e.printStackTrace();
			logger.error((new StringBuilder(String.valueOf(user))).append("登录FTP服务失败！").append(e.getMessage()).toString());
		}
		ftpClient.setBufferSize(2048);
		ftpClient.setDataTimeout(30000);
		return isLogin;
	}

	public static void ftpLogOut()
	{
		if (ftpClient == null || !ftpClient.isConnected())
			break MISSING_BLOCK_LABEL_139;
		try
		{
			boolean reuslt = ftpClient.logout();
			if (reuslt)
				logger.info("成功退出服务器");
			break MISSING_BLOCK_LABEL_117;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			logger.warn((new StringBuilder("退出FTP服务器异常！")).append(e.getMessage()).toString());
		}
		try
		{
			ftpClient.disconnect();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			logger.warn("关闭FTP服务器的连接异常！");
		}
		break MISSING_BLOCK_LABEL_139;
		Exception exception;
		exception;
		try
		{
			ftpClient.disconnect();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			logger.warn("关闭FTP服务器的连接异常！");
		}
		throw exception;
		try
		{
			ftpClient.disconnect();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			logger.warn("关闭FTP服务器的连接异常！");
		}
	}

	public boolean uploadFile(File localFile, String romotUpLoadePath)
	{
		BufferedInputStream inStream;
		boolean success;
		inStream = null;
		success = false;
		boolean flag;
		ftpClient.changeWorkingDirectory(romotUpLoadePath);
		inStream = new BufferedInputStream(new FileInputStream(localFile));
		logger.info((new StringBuilder(String.valueOf(localFile.getName()))).append("开始上传.....").toString());
		success = ftpClient.storeFile(localFile.getName(), inStream);
		if (!success)
			break MISSING_BLOCK_LABEL_232;
		logger.info((new StringBuilder(String.valueOf(localFile.getName()))).append("上传成功").toString());
		flag = success;
		if (inStream != null)
			try
			{
				inStream.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		return flag;
		FileNotFoundException e;
		e;
		e.printStackTrace();
		logger.error((new StringBuilder()).append(localFile).append("未找到").toString());
		if (inStream != null)
			try
			{
				inStream.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		break MISSING_BLOCK_LABEL_250;
		e;
		e.printStackTrace();
		if (inStream != null)
			try
			{
				inStream.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		break MISSING_BLOCK_LABEL_250;
		Exception exception;
		exception;
		if (inStream != null)
			try
			{
				inStream.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		throw exception;
		if (inStream != null)
			try
			{
				inStream.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		return success;
	}

	public static boolean downloadFile(String remoteFileName, String localDires, String remoteDownLoadPath)
	{
		String strFilePath;
		BufferedOutputStream outStream;
		boolean success;
		strFilePath = (new StringBuilder(String.valueOf(localDires))).append(remoteFileName).toString();
		outStream = null;
		success = false;
		boolean flag;
		ftpClient.changeWorkingDirectory(remoteDownLoadPath);
		outStream = new BufferedOutputStream(new FileOutputStream(strFilePath));
		logger.info((new StringBuilder(String.valueOf(remoteFileName))).append("开始下载....").toString());
		System.out.println((new StringBuilder(String.valueOf(remoteFileName))).append("开始下载....").toString());
		success = ftpClient.retrieveFile(remoteFileName, outStream);
		if (!success)
			break MISSING_BLOCK_LABEL_301;
		logger.info((new StringBuilder(String.valueOf(remoteFileName))).append("成功下载到").append(strFilePath).toString());
		System.out.println((new StringBuilder(String.valueOf(remoteFileName))).append("成功下载到").append(strFilePath).toString());
		flag = success;
		if (outStream != null)
			try
			{
				outStream.flush();
				outStream.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		return flag;
		Exception e;
		e;
		e.printStackTrace();
		System.out.println((new StringBuilder(String.valueOf(remoteFileName))).append("下载失败").toString());
		if (outStream != null)
			try
			{
				outStream.flush();
				outStream.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		break MISSING_BLOCK_LABEL_326;
		Exception exception;
		exception;
		if (outStream != null)
			try
			{
				outStream.flush();
				outStream.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		throw exception;
		if (outStream != null)
			try
			{
				outStream.flush();
				outStream.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		if (!success)
			System.out.println((new StringBuilder(String.valueOf(remoteFileName))).append("下载失败!!!").toString());
		return success;
	}

	public boolean uploadDirectory(String localDirectory, String remoteDirectoryPath)
	{
		File src = new File(localDirectory);
		try
		{
			remoteDirectoryPath = (new StringBuilder(String.valueOf(remoteDirectoryPath))).append(src.getName()).append("/").toString();
			ftpClient.makeDirectory(remoteDirectoryPath);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			logger.info((new StringBuilder(String.valueOf(remoteDirectoryPath))).append("目录创建失败").toString());
		}
		File allFile[] = src.listFiles();
		for (int currentFile = 0; currentFile < allFile.length; currentFile++)
			if (!allFile[currentFile].isDirectory())
			{
				String srcName = allFile[currentFile].getPath().toString();
				uploadFile(new File(srcName), remoteDirectoryPath);
			}

		for (int currentFile = 0; currentFile < allFile.length; currentFile++)
			if (allFile[currentFile].isDirectory())
				uploadDirectory(allFile[currentFile].getPath().toString(), remoteDirectoryPath);

		return true;
	}

	public static boolean downLoadDirectory(String localDirectoryPath, String remoteDirectory)
	{
		ftpLogin();
		try
		{
			String fileName = (new File(remoteDirectory)).getName();
			localDirectoryPath = (new StringBuilder(String.valueOf(localDirectoryPath))).append(fileName).append("//").toString();
			(new File(localDirectoryPath)).mkdirs();
			ftpClient.changeWorkingDirectory("/");
			FTPFile allFile[] = ftpClient.listFiles(remoteDirectory);
			for (int currentFile = 0; currentFile < allFile.length; currentFile++)
				if (!allFile[currentFile].isDirectory())
					downloadFile(allFile[currentFile].getName(), localDirectoryPath, remoteDirectory);

			for (int currentFile = 0; currentFile < allFile.length; currentFile++)
			{
				String pathName = allFile[currentFile].getName();
				if (allFile[currentFile].isDirectory() && !pathName.equals(".") && !pathName.equals(".."))
				{
					String strremoteDirectoryPath = (new StringBuilder(String.valueOf(remoteDirectory))).append(allFile[currentFile].getName()).append("/").toString();
					System.out.println((new StringBuilder(String.valueOf(strremoteDirectoryPath))).append("开始下载子文件夹中内容").toString());
					downLoadDirectory(localDirectoryPath, strremoteDirectoryPath);
				}
			}

		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.out.println("下载文件夹失败!");
			logger.info("下载文件夹失败");
			return false;
		}
		ftpLogOut();
		return true;
	}

	public int deleteDirectory(String ftpDirectory)
	{
		int result = 0;
		try
		{
			result = ftpClient.dele(ftpDirectory);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	public static FTPClient getFtpClient()
	{
		return ftpClient;
	}

	public void setFtpClient(FTPClient ftpClient)
	{
		ftpClient = ftpClient;
	}

	public boolean deleteFile(String path)
	{
		ftpLogin();
		if (ftpClient == null)
			break MISSING_BLOCK_LABEL_180;
		FTPFile ftpFiles[];
		ftpClient.deleteFile(path);
		System.out.println((new StringBuilder(" file  is  delete  ")).append(path).toString());
		if (!ftpClient.changeWorkingDirectory(path))
			break MISSING_BLOCK_LABEL_180;
		ftpFiles = ftpClient.listFiles();
		System.out.println((new StringBuilder(" my ip   is poss   lengt ")).append(ftpFiles.length).toString());
		if (ftpFiles != null && ftpFiles.length > 0)
			break MISSING_BLOCK_LABEL_101;
		ftpClient.removeDirectory(path);
		return true;
		try
		{
			FTPFile aftpfile[];
			int j = (aftpfile = ftpFiles).length;
			for (int i = 0; i < j; i++)
			{
				FTPFile ftpFile = aftpfile[i];
				ftpClient.deleteFile((new StringBuilder(String.valueOf(path))).append("/").append(ftpFile.getName()).toString());
				ftpClient.removeDirectory(path);
			}

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		ftpLogOut();
		return true;
	}

	public static List getFtpFiles(String path)
	{
		ftpLogin();
		List ftplist = new ArrayList();
		try
		{
			FTPFile ftpfiles[] = ftpClient.listFiles();
			System.out.println((new StringBuilder(" i read ftpfiles count is  ")).append(ftpfiles.length).toString());
			FTPFile aftpfile[];
			int j = (aftpfile = ftpfiles).length;
			for (int i = 0; i < j; i++)
			{
				FTPFile ftp = aftpfile[i];
				System.out.println("have list  but .. not!");
				if (!ftp.getName().equals(".") && !ftp.getName().equals(".."))
					ftplist.add(ftp);
			}

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		ftpLogOut();
		return ftplist;
	}

	public static void main(String args[])
		throws IOException
	{
		FtpUtils ftp = new FtpUtils("192.168.52.248", 21, "administrator", "Admin1234");
		ftpLogin();
		ftp.deleteFile("/zonysoft/tfy/123");
	}

}
