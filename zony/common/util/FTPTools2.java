// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FTPTools2.java

package com.zony.common.util;

import com.zony.common.config.ZonyConfig;
import java.io.*;
import java.util.*;
import sun.net.TelnetInputStream;
import sun.net.TelnetOutputStream;
import sun.net.ftp.FtpClient;

public class FTPTools2
{

	FtpClient ftpClient;
	private String server;
	private int port;
	private String userName;
	private String userPassword;

	public FTPTools2(String server, int port, String userName, String userPassword)
	{
		this.server = server;
		this.port = port;
		this.userName = userName;
		this.userPassword = userPassword;
	}

	public FTPTools2()
	{
		server = ZonyConfig.getFTP_Server();
		port = ZonyConfig.getFTP_Port();
		userName = ZonyConfig.getFTP_userUserName();
		userPassword = ZonyConfig.getFTP_userPassword();
	}

	public boolean open()
	{
		if (ftpClient != null && ftpClient.serverIsOpen())
			return true;
		try
		{
			ftpClient = new FtpClient();
			ftpClient.openServer(server, port);
			ftpClient.login(userName, userPassword);
			ftpClient.binary();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			ftpClient = null;
			return false;
		}
		return true;
	}

	public boolean cd(String dir)
	{
		boolean f = false;
		try
		{
			ftpClient.cd(dir);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return f;
		}
		return true;
	}

	public boolean upload(String localDirectoryAndFileName, String ftpFileName, String ftpDirectory)
		throws Exception
	{
		FileInputStream is;
		TelnetOutputStream os;
		if (!open())
			return false;
		is = null;
		os = null;
		char ch = ' ';
		if (ftpDirectory.length() > 0)
			ch = ftpDirectory.charAt(ftpDirectory.length() - 1);
		for (; ch == '/' || ch == '\\'; ch = ftpDirectory.charAt(ftpDirectory.length() - 1))
			ftpDirectory = ftpDirectory.substring(0, ftpDirectory.length() - 1);

		int slashIndex = ftpDirectory.indexOf('/');
		int backslashIndex = ftpDirectory.indexOf('\\');
		int index = slashIndex;
		String dirall = ftpDirectory;
		if (backslashIndex != -1 && (index == -1 || index > backslashIndex))
			index = backslashIndex;
		String directory = "";
		while (index != -1) 
		{
			if (index > 0)
			{
				String dir = dirall.substring(0, index);
				directory = (new StringBuilder(String.valueOf(directory))).append("/").append(dir).toString();
				ftpClient.sendServer((new StringBuilder("XMKD ")).append(directory).append("\r\n").toString());
				ftpClient.readServerResponse();
			}
			dirall = dirall.substring(index + 1);
			slashIndex = dirall.indexOf('/');
			backslashIndex = dirall.indexOf('\\');
			index = slashIndex;
			if (backslashIndex != -1 && (index == -1 || index > backslashIndex))
				index = backslashIndex;
		}
		ftpClient.sendServer((new StringBuilder("XMKD ")).append(ftpDirectory).append("\r\n").toString());
		ftpClient.readServerResponse();
		os = ftpClient.put((new StringBuilder(String.valueOf(ftpDirectory))).append("/").append(ftpFileName).toString());
		File file_in = new File(localDirectoryAndFileName);
		is = new FileInputStream(file_in);
		byte bytes[] = new byte[1024];
		int i;
		while ((i = is.read(bytes)) != -1) 
			os.write(bytes, 0, i);
		if (is != null)
			is.close();
		if (os != null)
			os.close();
		return true;
		Exception e;
		e;
		e.printStackTrace();
		if (is != null)
			is.close();
		if (os != null)
			os.close();
		return false;
		Exception exception;
		exception;
		if (is != null)
			is.close();
		if (os != null)
			os.close();
		throw exception;
	}

	public long download(String ftpDirectoryAndFileName, String localDirectoryAndFileName)
		throws Exception
	{
		long result = 0L;
		if (!open())
		{
			return result;
		} else
		{
			processdownload(ftpDirectoryAndFileName, localDirectoryAndFileName);
			return result;
		}
	}

	public List getFileNameList(String ftpDirectory)
	{
		List list = new ArrayList();
		if (!open())
			return list;
		try
		{
			DataInputStream dis = new DataInputStream(ftpClient.nameList(ftpDirectory));
			for (String filename = ""; (filename = dis.readLine()) != null;)
				list.add(filename);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

	public boolean deleteFile(String ftpDirAndFileName)
	{
		if (!open())
		{
			return false;
		} else
		{
			ftpClient.sendServer((new StringBuilder("DELE ")).append(ftpDirAndFileName).append("\r\n").toString());
			return true;
		}
	}

	public boolean deleteDirectory(String ftpDirectory)
	{
		if (!open())
		{
			return false;
		} else
		{
			ftpClient.sendServer((new StringBuilder("XRMD ")).append(ftpDirectory).append("\r\n").toString());
			return true;
		}
	}

	public void close()
	{
		try
		{
			if (ftpClient != null && ftpClient.serverIsOpen())
				ftpClient.closeServer();
		}
		catch (Exception exception) { }
	}

	public boolean isDir(String line)
	{
		return ((String)parseLine(line).get(0)).indexOf("d") != -1;
	}

	public boolean isFile(String line)
	{
		return !isDir(line);
	}

	public ArrayList getFileList(String remotePath)
		throws IOException
	{
		ftpClient.cd(remotePath);
		BufferedReader dr = new BufferedReader(new InputStreamReader(ftpClient.list()));
		ArrayList al = new ArrayList();
		for (String s = ""; (s = dr.readLine()) != null;)
		{
			System.out.println((new StringBuilder("readLine:")).append(s).toString());
			if (!((String)parseLine(s).get(8)).equals(".") && !((String)parseLine(s).get(8)).equals(".."))
			{
				al.add(s);
				System.out.println((new StringBuilder("s:")).append(s).toString());
			}
		}

		return al;
	}

	public ArrayList getNameList(String remotePath)
		throws IOException
	{
		open();
		BufferedReader dr = new BufferedReader(new InputStreamReader(ftpClient.nameList(remotePath)));
		ArrayList al = new ArrayList();
		for (String s = ""; (s = dr.readLine()) != null;)
		{
			System.out.println((new StringBuilder("filename:")).append(s).toString());
			al.add(s);
		}

		return al;
	}

	public void processdownload(String localPath, String remotePath)
	{
		FileOutputStream outStream;
		outStream = null;
		ArrayList list = null;
		try
		{
			ArrayList list = getFileList(remotePath);
			File temp = null;
			for (int i = 0; i < list.size(); i++)
				if (isFile(list.get(i).toString()))
				{
					ftpClient.cd(remotePath);
					ArrayList listfileName = getNameList(remotePath);
					for (int j = 0; j < listfileName.size(); j++)
					{
						temp = new File((new StringBuilder(String.valueOf(localPath))).append(File.separator).append(listfileName.get(j).toString()).toString());
						outStream = new FileOutputStream(temp);
						TelnetInputStream is = ftpClient.get(listfileName.get(j).toString());
						byte bytes[] = new byte[1024];
						int c;
						while ((c = is.read(bytes)) != -1) 
							outStream.write(bytes, 0, c);
						is.close();
						outStream.close();
						System.out.println((new StringBuilder("成功下载文件：")).append(remotePath).append(File.separator).append(listfileName.get(j).toString()).toString());
					}

				} else
				if (isDir(list.get(i).toString()))
				{
					temp = new File((new StringBuilder(String.valueOf(localPath))).append(File.separator).append(getFileName(list.get(i).toString())).toString());
					temp.mkdirs();
					String newRemote = (new StringBuilder(String.valueOf(remotePath))).append(File.separator).append(getFileName(list.get(i).toString())).toString();
					processdownload((new StringBuilder(String.valueOf(localPath))).append(File.separator).append(getFileName(list.get(i).toString())).toString(), newRemote);
				}

			break MISSING_BLOCK_LABEL_440;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		try
		{
			outStream.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		break MISSING_BLOCK_LABEL_454;
		Exception exception;
		exception;
		try
		{
			outStream.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		throw exception;
		try
		{
			outStream.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	private ArrayList parseLine(String line)
	{
		ArrayList s1 = new ArrayList();
		for (StringTokenizer st = new StringTokenizer(line, " "); st.hasMoreTokens(); s1.add(st.nextToken()));
		return s1;
	}

	public String getFileName(String line)
	{
		String filename = (String)parseLine(line).get(8);
		return filename;
	}

	public static void main(String args[])
	{
		FTPTools2 ftp = new FTPTools2();
		String ftpRootPath = "//files//";
		String localPath = "E:\\testGetftp\\";
		try
		{
			ftp.download(localPath, ftpRootPath);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
