// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FTPCommon.java

package com.zony.common.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.net.ftp.*;
import org.apache.log4j.Logger;

// Referenced classes of package com.zony.common.util:
//			Globals, FtpModel, DateUtil

public class FTPCommon
{

	private FTPClient ftpClient;
	private FtpModel ftpModel;
	Logger logger;

	public static void main(String args[])
	{
		Globals.setSysRootDir("D:/003-workspace/workspace2014/MetLifeEmail/WebContent");
		FtpModel temp = new FtpModel("administrator", "password", "192.168.52.132", 21, "/");
		FTPCommon ftpCommon = new FTPCommon(temp);
		try
		{
			ftpCommon.ftpLogin();
			if (!ftpCommon.deleteDirectory("/00547180_HPOLSCDT_20141029162401"))
				throw new Exception("移除文件夹异常，文件夹名称：/zonysoft/1111111/");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public List getFolderList(String rootPath)
		throws IOException
	{
		List folderList = new ArrayList();
		ftpClient.changeWorkingDirectory(rootPath);
		FTPFile files[] = ftpClient.listFiles();
		for (int i = 0; i < files.length; i++)
			if (files[i].isDirectory() && !files[i].getName().equals(".") && !files[i].getName().equals(".."))
			{
				System.out.println((new StringBuilder("得到文件:")).append(files[i].getName()).toString());
				logger.debug((new StringBuilder("得到文件:")).append(files[i].getName()).toString());
				folderList.add(rootPath.equals("/") ? ((Object) ((new StringBuilder(String.valueOf(rootPath))).append(files[i].getName()).toString())) : ((Object) ((new StringBuilder(String.valueOf(rootPath))).append("/").append(files[i].getName()).toString())));
			}

		return folderList;
	}

	public boolean deleteDirectory(String remote)
		throws Exception
	{
		try
		{
			if (ftpClient.changeWorkingDirectory(remote))
			{
				FTPFile files[] = ftpClient.listFiles();
				FTPFile aftpfile[];
				int j = (aftpfile = files).length;
				for (int i = 0; i < j; i++)
				{
					FTPFile f = aftpfile[i];
					if (!f.getName().equals(".") && !f.getName().equals(".."))
						if (f.isFile())
						{
							if (!ftpClient.deleteFile(remote.equals("/") ? (new StringBuilder(String.valueOf(remote))).append(f.getName()).toString() : (new StringBuilder(String.valueOf(remote))).append("/").append(f.getName()).toString()))
								throw new Exception((new StringBuilder("删除文件异常，文件：")).append(f.getName()).toString());
						} else
						{
							deleteDirectory(remote.equals("/") ? (new StringBuilder(String.valueOf(remote))).append(f.getName()).toString() : (new StringBuilder(String.valueOf(remote))).append("/").append(f.getName()).toString());
						}
				}

				if (!ftpClient.removeDirectory(remote))
					logger.error((new StringBuilder("移除文件夹异常，文件夹名称：")).append(remote).toString());
			} else
			{
				throw new Exception((new StringBuilder("移除文件夹异常，设置FTP工作文件夹异常，文件夹：")).append(remote).toString());
			}
		}
		catch (IOException e)
		{
			throw new Exception((new StringBuilder("移除文件夹发生异常，文件夹：")).append(remote).append(",异常信息:").append(e.getMessage()).toString(), e);
		}
		return true;
	}

	public void List(String pathName)
		throws IOException
	{
		if (pathName.startsWith("/") && pathName.endsWith("/"))
		{
			String directory = pathName;
			ftpClient.changeWorkingDirectory(directory);
			FTPFile files[] = ftpClient.listFiles();
			for (int i = 0; i < files.length; i++)
			{
				System.out.println((new StringBuilder("得到文件:")).append(files[i].getName()).toString());
				if (!files[i].isFile() && files[i].isDirectory() && !files[i].getName().equals(".") && !files[i].getName().equals(".."))
					List((new StringBuilder(String.valueOf(directory))).append(files[i].getName()).append("/").toString());
			}

		}
	}

	public FTPCommon(FtpModel ftp)
	{
		logger = Logger.getLogger(com/zony/common/util/FTPCommon);
		ftpClient = new FTPClient();
		ftpModel = ftp;
	}

	public boolean ftpLogin()
		throws Exception
	{
		boolean isLogin;
		isLogin = false;
		if (isOpenFTPConnection())
		{
			logger.debug("FTP 连接未失效，继续使用，无需登录！");
			return true;
		}
		logger.debug((new StringBuilder("FTP 连接失效，重新登录，登录开始时间：")).append(DateUtil.getNow()).toString());
		ftpClient.setControlEncoding("GBK");
		if (ftpModel.getPort() > 0)
			ftpClient.connect(ftpModel.getUrl(), ftpModel.getPort());
		else
			ftpClient.connect(ftpModel.getUrl());
		int reply = ftpClient.getReplyCode();
		if (FTPReply.isPositiveCompletion(reply))
			break MISSING_BLOCK_LABEL_126;
		ftpClient.disconnect();
		return isLogin;
		try
		{
			ftpClient.login(ftpModel.getUsername(), ftpModel.getPassword());
			ftpClient.changeWorkingDirectory(ftpModel.getRemoteDir());
			isLogin = true;
			logger.debug((new StringBuilder("FTP 连接成功，重新登录，登录结束时间：")).append(DateUtil.getNow()).toString());
		}
		catch (Exception e)
		{
			throw new Exception((new StringBuilder("登录FTP服务器失败，失败原因：")).append(e.getMessage()).toString(), e);
		}
		ftpClient.setBufferSize(2048);
		ftpClient.setDataTimeout(10000);
		return isLogin;
	}

	public boolean closeFTPClient()
	{
		if (ftpClient == null || !ftpClient.isConnected())
			break MISSING_BLOCK_LABEL_90;
		boolean flag;
		boolean reuslt = ftpClient.logout();
		flag = reuslt;
		try
		{
			ftpClient.disconnect();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return flag;
		IOException e;
		e;
		e.printStackTrace();
		try
		{
			ftpClient.disconnect();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return false;
		Exception exception;
		exception;
		try
		{
			ftpClient.disconnect();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		throw exception;
		return true;
	}

	public boolean isOpenFTPConnection()
	{
		boolean isOpen = false;
		if (ftpClient == null)
			return false;
		return ftpClient.isConnected() || ftpClient.isAvailable();
		Exception e;
		e;
		e.printStackTrace();
		boolean isOpen = false;
		return isOpen;
	}

	public void setFileType(int fileType)
	{
		try
		{
			ftpClient.setFileType(fileType);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public boolean downloadFile(String localFilePath, String remoteFileName)
	{
		BufferedOutputStream outStream;
		boolean success;
		outStream = null;
		success = false;
		outStream = new BufferedOutputStream(new FileOutputStream(localFilePath));
		success = ftpClient.retrieveFile(remoteFileName, outStream);
		break MISSING_BLOCK_LABEL_126;
		FileNotFoundException e;
		e;
		e.printStackTrace();
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
		break MISSING_BLOCK_LABEL_148;
		e;
		e.printStackTrace();
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
		break MISSING_BLOCK_LABEL_148;
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
		return success;
	}

	public boolean downloadFile(File localFile, String remoteFileName)
	{
		BufferedOutputStream outStream;
		FileOutputStream outStr;
		boolean success;
		outStream = null;
		outStr = null;
		success = false;
		outStr = new FileOutputStream(localFile);
		outStream = new BufferedOutputStream(outStr);
		success = ftpClient.retrieveFile(remoteFileName, outStream);
		break MISSING_BLOCK_LABEL_412;
		FileNotFoundException e;
		e;
		e.printStackTrace();
		try
		{
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
			break MISSING_BLOCK_LABEL_139;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		if (outStr != null)
			try
			{
				outStr.flush();
				outStr.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		break MISSING_BLOCK_LABEL_527;
		Exception exception1;
		exception1;
		if (outStr != null)
			try
			{
				outStr.flush();
				outStr.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		throw exception1;
		if (outStr != null)
			try
			{
				outStr.flush();
				outStr.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		break MISSING_BLOCK_LABEL_527;
		e;
		e.printStackTrace();
		try
		{
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
			break MISSING_BLOCK_LABEL_264;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		if (outStr != null)
			try
			{
				outStr.flush();
				outStr.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		break MISSING_BLOCK_LABEL_527;
		exception1;
		if (outStr != null)
			try
			{
				outStr.flush();
				outStr.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		throw exception1;
		if (outStr != null)
			try
			{
				outStr.flush();
				outStr.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		break MISSING_BLOCK_LABEL_527;
		Exception exception;
		exception;
		try
		{
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
			break MISSING_BLOCK_LABEL_384;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		if (outStr != null)
			try
			{
				outStr.flush();
				outStr.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		break MISSING_BLOCK_LABEL_409;
		exception1;
		if (outStr != null)
			try
			{
				outStr.flush();
				outStr.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		throw exception1;
		if (outStr != null)
			try
			{
				outStr.flush();
				outStr.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		throw exception;
		try
		{
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
			break MISSING_BLOCK_LABEL_502;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		if (outStr != null)
			try
			{
				outStr.flush();
				outStr.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		break MISSING_BLOCK_LABEL_527;
		exception1;
		if (outStr != null)
			try
			{
				outStr.flush();
				outStr.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		throw exception1;
		if (outStr != null)
			try
			{
				outStr.flush();
				outStr.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		return success;
	}

	public boolean uploadFile(String localFilePath, String remoteFileName)
	{
		BufferedInputStream inStream;
		boolean success;
		inStream = null;
		success = false;
		inStream = new BufferedInputStream(new FileInputStream(localFilePath));
		success = ftpClient.storeFile(remoteFileName, inStream);
		break MISSING_BLOCK_LABEL_114;
		FileNotFoundException e;
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
		break MISSING_BLOCK_LABEL_132;
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
		break MISSING_BLOCK_LABEL_132;
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

	public boolean uploadFile(File localFile, String remoteFileName)
	{
		BufferedInputStream inStream;
		boolean success;
		inStream = null;
		success = false;
		inStream = new BufferedInputStream(new FileInputStream(localFile));
		success = ftpClient.storeFile(remoteFileName, inStream);
		break MISSING_BLOCK_LABEL_114;
		FileNotFoundException e;
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
		break MISSING_BLOCK_LABEL_132;
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
		break MISSING_BLOCK_LABEL_132;
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

	public void changeDir(String remoteDir)
	{
		try
		{
			ftpClient.changeWorkingDirectory(remoteDir);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void changeDir(String remoteDirs[])
	{
		String dir = "";
		try
		{
			for (int i = 0; i < remoteDirs.length; i++)
			{
				ftpClient.changeWorkingDirectory(remoteDirs[i]);
				dir = (new StringBuilder(String.valueOf(dir))).append(remoteDirs[i]).append("/").toString();
			}

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void toParentDir(String remoteDirs[])
	{
		try
		{
			for (int i = 0; i < remoteDirs.length; i++)
				ftpClient.changeToParentDirectory();

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void toParentDir()
	{
		try
		{
			ftpClient.changeToParentDirectory();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public String[] getListFiels()
	{
		String files[] = null;
		try
		{
			files = ftpClient.listNames();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return files;
	}

	public String[] getListFiels(String dirName)
	{
		String files[] = null;
		try
		{
			ftpClient.changeWorkingDirectory(dirName);
			files = ftpClient.listNames();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return files;
	}

	public FTPClient getFtpClient()
	{
		return ftpClient;
	}

	public FtpModel getFtpModel()
	{
		return ftpModel;
	}

	public void setFtpModel(FtpModel ftpModel)
	{
		this.ftpModel = ftpModel;
	}
}
