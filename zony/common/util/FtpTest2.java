// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FtpTest2.java

package com.zony.common.util;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import sun.net.TelnetInputStream;
import sun.net.ftp.FtpClient;

public class FtpTest2
{

	private String ip;
	private String username;
	private String password;
	private int port;
	private String localfilefullname;
	FtpClient ftpclient;
	OutputStream os;
	FileInputStream is;

	public FtpTest2()
	{
		ip = "192.168.58.132";
		username = "administrator";
		password = "password";
		port = 21;
		localfilefullname = "";
		ftpclient = null;
		os = null;
		is = null;
	}

	private void createdir(String dir, FtpClient ftpclient)
		throws Exception
	{
		ftpclient.ascii();
		StringTokenizer s = new StringTokenizer(dir, "/");
		s.countTokens();
		String pathname = "";
		for (; s.hasMoreElements(); ftpclient.readServerResponse())
		{
			pathname = (new StringBuilder(String.valueOf(pathname))).append("/").append((String)s.nextElement()).toString();
			Object obj;
			try
			{
				ftpclient.sendServer((new StringBuilder("mkd ")).append(pathname).append("\r\n").toString());
			}
			catch (Exception e)
			{
				obj = null;
			}
		}

		ftpclient.binary();
	}

	private boolean isdirexist(String dir, FtpClient ftpclient)
		throws Exception
	{
		try
		{
			ftpclient.cd(dir);
		}
		catch (Exception e)
		{
			return false;
		}
		return true;
	}

	public boolean upload(String prefix, String localfilefullname)
		throws Exception
	{
		this.localfilefullname = localfilefullname;
		String savefilename = localfilefullname;
		ftpclient = new FtpClient();
		ftpclient.openServer(ip, port);
		ftpclient.login(username, password);
		File file_in = new File(savefilename);
		processfile(prefix, file_in, ftpclient);
		if (is != null)
			is.close();
		if (os != null)
			os.close();
		if (ftpclient != null)
			ftpclient.closeServer();
		if (is != null)
			is.close();
		if (os != null)
			os.close();
		if (ftpclient != null)
			ftpclient.closeServer();
		return true;
		Exception e;
		e;
		e.printStackTrace();
		System.err.println((new StringBuilder("exception e in ftp upload(): ")).append(e.toString()).toString());
		if (is != null)
			is.close();
		if (os != null)
			os.close();
		if (ftpclient != null)
			ftpclient.closeServer();
		return false;
		Exception exception;
		exception;
		if (is != null)
			is.close();
		if (os != null)
			os.close();
		if (ftpclient != null)
			ftpclient.closeServer();
		throw exception;
	}

	private void processfile(String prefix, File source, FtpClient ftpclient)
		throws Exception
	{
		if (source.exists())
		{
			if (source.isDirectory())
			{
				String path = (new StringBuilder(String.valueOf(prefix))).append(source.getPath().substring(localfilefullname.length()).replace("\\", "/")).toString();
				if (!isdirexist(path, ftpclient))
					createdir(path, ftpclient);
				File sourcefile[] = source.listFiles();
				for (int i = 0; i < sourcefile.length; i++)
					if (sourcefile[i].exists())
						if (sourcefile[i].isDirectory())
						{
							processfile(prefix, sourcefile[i], ftpclient);
						} else
						{
							ftpclient.cd(cheangpath(prefix, sourcefile[i].getPath()));
							ftpclient.binary();
							os = ftpclient.put(sourcefile[i].getName());
							byte bytes[] = new byte[1024];
							is = new FileInputStream(sourcefile[i]);
							int c;
							while ((c = is.read(bytes)) != -1) 
								os.write(bytes, 0, c);
							is.close();
							os.close();
						}

			} else
			{
				ftpclient.cd(cheangpath(prefix, source.getPath()));
				ftpclient.binary();
				os = ftpclient.put(source.getName());
				byte bytes[] = new byte[1024];
				is = new FileInputStream(source);
				int c;
				while ((c = is.read(bytes)) != -1) 
					os.write(bytes, 0, c);
				is.close();
				os.close();
			}
		} else
		{
			throw new Exception((new StringBuilder("此文件或文件夹[")).append(source.getName()).append("]有误或不存在!").toString());
		}
	}

	private String cheangpath(String prefix, String path)
		throws Exception
	{
		path = path.substring(localfilefullname.length()).replace("\\", "/");
		if ("".equals(path))
			path = "/";
		else
			path = path.substring(0, path.lastIndexOf("/") + 1);
		path = (new StringBuilder(String.valueOf(prefix))).append(path).toString();
		return path;
	}

	public Long getSize(String strName)
	{
		Long TotalSize = Long.valueOf(0L);
		File f = new File(strName);
		if (f.isFile())
			return Long.valueOf(f.length());
		if (f.isDirectory())
		{
			File contents[] = f.listFiles();
			for (int i = 0; i < contents.length; i++)
				if (contents[i].isFile())
					TotalSize = Long.valueOf(TotalSize.longValue() + contents[i].length());
				else
				if (contents[i].isDirectory())
					TotalSize = Long.valueOf(TotalSize.longValue() + getSize(contents[i].getPath()).longValue());

		}
		return TotalSize;
	}

	public void processdownload(String localPath, String remotePath)
	{
		FileOutputStream outStream;
		outStream = null;
		ArrayList list = null;
		try
		{
			ArrayList list = getFileList(remotePath);
			ftpclient.binary();
			File temp = null;
			for (int i = 0; i < list.size(); i++)
				if (isFile(list.get(i).toString()))
				{
					ftpclient.cd(remotePath);
					ArrayList listfileName = getNameList(remotePath);
					for (int j = 0; j < listfileName.size(); j++)
					{
						temp = new File((new StringBuilder(String.valueOf(localPath))).append(File.separator).append(listfileName.get(j).toString()).toString());
						outStream = new FileOutputStream(temp);
						TelnetInputStream is = ftpclient.get(listfileName.get(j).toString());
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

			break MISSING_BLOCK_LABEL_447;
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
		break MISSING_BLOCK_LABEL_461;
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

	public String getFileName(String line)
	{
		String filename = (String)parseLine(line).get(8);
		return filename;
	}

	public ArrayList getNameList(String remotePath)
		throws IOException
	{
		BufferedReader dr = new BufferedReader(new InputStreamReader(ftpclient.nameList(remotePath)));
		ArrayList al = new ArrayList();
		for (String s = ""; (s = dr.readLine()) != null;)
		{
			System.out.println((new StringBuilder("filename:")).append(s).toString());
			al.add(s);
		}

		return al;
	}

	public ArrayList getFileList(String remotePath)
		throws IOException
	{
		ftpclient.cd(remotePath);
		BufferedReader dr = new BufferedReader(new InputStreamReader(ftpclient.list()));
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

	public boolean isDir(String line)
	{
		return ((String)parseLine(line).get(0)).indexOf("d") != -1;
	}

	public boolean isFile(String line)
	{
		return !isDir(line);
	}

	private ArrayList parseLine(String line)
	{
		ArrayList s1 = new ArrayList();
		for (StringTokenizer st = new StringTokenizer(line, " "); st.hasMoreTokens(); s1.add(st.nextToken()));
		return s1;
	}

	public boolean download(String localPath, String remotePath)
		throws Exception
	{
		localfilefullname = localfilefullname;
		String savefilename = localfilefullname;
		ftpclient = new FtpClient();
		ftpclient.openServer(ip, port);
		ftpclient.login(username, password);
		ftpclient.cd(remotePath);
		processdownload(localPath, remotePath);
		if (is != null)
			is.close();
		if (os != null)
			os.close();
		if (ftpclient != null)
			ftpclient.closeServer();
		if (is != null)
			is.close();
		if (os != null)
			os.close();
		if (ftpclient != null)
			ftpclient.closeServer();
		return true;
		Exception e;
		e;
		e.printStackTrace();
		System.err.println((new StringBuilder("exception e in ftp upload(): ")).append(e.toString()).toString());
		if (is != null)
			is.close();
		if (os != null)
			os.close();
		if (ftpclient != null)
			ftpclient.closeServer();
		return false;
		Exception exception;
		exception;
		if (is != null)
			is.close();
		if (os != null)
			os.close();
		if (ftpclient != null)
			ftpclient.closeServer();
		throw exception;
	}

	public static void main(String args[])
		throws Exception
	{
		FtpTest2 ftpTest = new FtpTest2();
		String path = "E:/test2/test";
		ftpTest.download("e:\\test", "/ftpTest");
	}
}
