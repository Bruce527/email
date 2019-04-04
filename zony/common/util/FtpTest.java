// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FtpTest.java

package com.zony.common.util;

import java.io.*;
import java.util.*;
import sun.net.TelnetInputStream;
import sun.net.ftp.FtpClient;

public class FtpTest
{

	private String ip;
	private String username;
	private String password;
	private int port;
	private String path;
	FtpClient ftpClient;
	OutputStream os;
	FileInputStream is;

	public FtpTest(String serverIP, String username, String password)
	{
		ip = "";
		this.username = "";
		this.password = "";
		port = -1;
		path = "";
		ftpClient = null;
		os = null;
		is = null;
		ip = serverIP;
		this.username = username;
		this.password = password;
	}

	public FtpTest(String serverIP, int port, String username, String password)
	{
		ip = "";
		this.username = "";
		this.password = "";
		this.port = -1;
		path = "";
		ftpClient = null;
		os = null;
		is = null;
		ip = serverIP;
		this.username = username;
		this.password = password;
		this.port = port;
	}

	public boolean connectServer()
	{
		ftpClient = new FtpClient();
		try
		{
			if (port != -1)
				ftpClient.openServer(ip, port);
			else
				ftpClient.openServer(ip);
			ftpClient.login(username, password);
			if (path.length() != 0)
				ftpClient.cd(path);
			ftpClient.binary();
			System.out.println((new StringBuilder("已登录到\"")).append(ftpClient.pwd()).append("\"目录").toString());
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean closeServer()
	{
		try
		{
			if (is != null)
				is.close();
			if (os != null)
				os.close();
			if (ftpClient != null)
				ftpClient.closeServer();
			System.out.println("已从服务器断开");
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private boolean isDirExist(String dir)
	{
		String pwd = "";
		try
		{
			pwd = ftpClient.pwd();
			ftpClient.cd(dir);
			ftpClient.cd(pwd);
		}
		catch (Exception e)
		{
			return false;
		}
		return true;
	}

	private boolean createDir(String dir)
	{
		StringTokenizer s;
		String pathName;
		ftpClient.ascii();
		s = new StringTokenizer(dir, "/");
		s.countTokens();
		pathName = ftpClient.pwd();
		  goto _L1
_L3:
		pathName = (new StringBuilder(String.valueOf(pathName))).append("/").append((String)s.nextElement()).toString();
		Exception e;
		try
		{
			ftpClient.sendServer((new StringBuilder("MKD ")).append(pathName).append("\r\n").toString());
			continue; /* Loop/switch isn't completed */
		}
		catch (Exception e)
		{
			e = null;
		}
		return false;
		ftpClient.readServerResponse();
_L1:
		if (s.hasMoreElements()) goto _L3; else goto _L2
_L2:
		ftpClient.binary();
		return true;
		IOException e1;
		e1;
		e1.printStackTrace();
		return false;
	}

	public long downloadFile(String filename, String newfilename)
	{
		long result;
		TelnetInputStream is;
		FileOutputStream os;
		result = 0L;
		is = null;
		os = null;
		try
		{
			is = ftpClient.get(filename);
			File outfile = new File(newfilename);
			os = new FileOutputStream(outfile);
			byte bytes[] = new byte[0x19000];
			int c;
			while ((c = is.read(bytes)) != -1) 
			{
				os.write(bytes, 0, c);
				result += c;
			}
			break MISSING_BLOCK_LABEL_156;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		try
		{
			if (is != null)
				is.close();
			if (os != null)
				os.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		break MISSING_BLOCK_LABEL_186;
		Exception exception;
		exception;
		try
		{
			if (is != null)
				is.close();
			if (os != null)
				os.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		throw exception;
		try
		{
			if (is != null)
				is.close();
			if (os != null)
				os.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	public List getFileList(String path)
	{
		List list = new ArrayList();
		try
		{
			DataInputStream dis = new DataInputStream(ftpClient.nameList((new StringBuilder(String.valueOf(this.path))).append(path).toString()));
			for (String filename = ""; (filename = dis.readLine()) != null;)
				list.add(filename);

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return list;
	}

	public static void main(String args[])
	{
		FtpTest ftpTest = new FtpTest("192.168.52.132", 21, "administrator", "password");
		ftpTest.connectServer();
		List list = ftpTest.getFileList("e:/ftpTest/");
		for (int i = 0; i < list.size(); i++)
		{
			String filename = list.get(i).toString();
			if ((new File(filename)).isFile() || (new File(filename)).isDirectory())
			{
				System.out.println(filename);
				ftpTest.downloadFile(filename, (new StringBuilder("e:/test/")).append(filename).toString());
			}
		}

	}
}
