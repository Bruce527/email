// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FTPImportUtil.java

package com.zony.common.util;

import com.zony.common.config.ZonyConfig;
import java.io.*;
import java.util.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import sun.net.TelnetInputStream;
import sun.net.ftp.FtpClient;

// Referenced classes of package com.zony.common.util:
//			Globals, DateUtil, FileUtil

public class FTPImportUtil
{

	public static Logger logger = Logger.getLogger(com/zony/common/util/FTPImportUtil);
	private String localfilefullname;
	public FtpClient ftpclient;
	public OutputStream os;
	public FileInputStream is;
	public String ip;
	public int port;
	public String username;
	public String password;

	public FTPImportUtil(String strIp, int port, String username, String password)
	{
		localfilefullname = "";
		ftpclient = null;
		os = null;
		is = null;
		ip = strIp;
		this.port = port;
		this.username = username;
		this.password = password;
	}

	public FTPImportUtil()
	{
		localfilefullname = "";
		ftpclient = null;
		os = null;
		is = null;
		ip = ZonyConfig.getFTP_Server();
		port = ZonyConfig.getFTP_Port();
		username = ZonyConfig.getFTP_userUserName();
		password = ZonyConfig.getFTP_userPassword();
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
			throw new Exception((new StringBuilder("���ļ����ļ���[")).append(source.getName()).append("]����򲻴���!").toString());
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

	public long downloadFile(String filename, String newfilename)
		throws Exception
	{
		long result;
		TelnetInputStream is;
		FileOutputStream os;
		result = 0L;
		is = null;
		os = null;
		try
		{
			is = ftpclient.get(filename);
			File outfile = new File(newfilename);
			os = new FileOutputStream(outfile);
			byte bytes[] = new byte[1024];
			int c;
			while ((c = is.read(bytes)) != -1) 
			{
				os.write(bytes, 0, c);
				result += c;
			}
		}
		catch (IOException e)
		{
			throw new Exception((new StringBuilder("�����ļ������쳣���쳣��Ϣ��")).append(e.getMessage()).toString(), e);
		}
		break MISSING_BLOCK_LABEL_176;
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
			throw new Exception((new StringBuilder("�����ļ������쳣���쳣��Ϣ��")).append(e.getMessage()).toString(), e);
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
			throw new Exception((new StringBuilder("�����ļ������쳣���쳣��Ϣ��")).append(e.getMessage()).toString(), e);
		}
		return result;
	}

	public void processdownload(String localPath, String remotePath)
		throws Exception
	{
		FileOutputStream outStream = null;
		ArrayList list = null;
		try
		{
			list = getFileList(remotePath);
			File temp = null;
			for (int i = 0; i < list.size(); i++)
				if (isFile(list.get(i).toString()))
				{
					String templistPath = getFileName(list.get(i).toString());
					String oldPath = (new StringBuilder(String.valueOf(remotePath))).append(templistPath).toString();
					String tragertPath = (new StringBuilder(String.valueOf(localPath))).append(File.separator).append(templistPath).toString();
					(new File((new StringBuilder(String.valueOf(localPath))).append(File.separator).toString())).mkdirs();
					downloadFile(oldPath, tragertPath);
				}

			for (int i = 0; i < list.size(); i++)
				if (isFile(list.get(i).toString()))
				{
					String templistPath = getFileName(list.get(i).toString());
					String oldPath = (new StringBuilder(String.valueOf(remotePath))).append(templistPath).toString();
					deleteFile(oldPath);
				}

		}
		catch (Exception ex)
		{
			throw new Exception((new StringBuilder("processdownload(String localPath, String remotePath)  �����ļ������쳣���쳣��Ϣ")).append(ex.getMessage()).toString(), ex);
		}
	}

	public String getFileName(String line)
	{
		String filename = (String)parseLine(line).get(parseLine(line).size() - 1);
		return filename;
	}

	public ArrayList getNameList(String remotePath)
		throws IOException
	{
		BufferedReader dr = new BufferedReader(new InputStreamReader(ftpclient.nameList(remotePath)));
		ArrayList al = new ArrayList();
		for (String s = ""; (s = dr.readLine()) != null;)
			al.add(s);

		return al;
	}

	public ArrayList getFileList(String remotePath)
		throws Exception
	{
		ArrayList al;
		BufferedReader dr;
		al = new ArrayList();
		dr = null;
		try
		{
			ftpclient.cd(remotePath);
			dr = new BufferedReader(new InputStreamReader(ftpclient.list()));
			for (String s = ""; (s = dr.readLine()) != null;)
			{
				String tempStr = getFileName(s);
				if (!tempStr.equals(".") && !tempStr.equals(".."))
					al.add(s);
			}

		}
		catch (Exception e)
		{
			throw new Exception((new StringBuilder("ArrayList getFileList(String remotePath)  ��ȡ�ļ��б���Ϣ�����쳣���쳣��Ϣ")).append(e.getMessage()).toString(), e);
		}
		break MISSING_BLOCK_LABEL_143;
		Exception exception;
		exception;
		if (dr != null)
			dr.close();
		throw exception;
		if (dr != null)
			dr.close();
		return al;
	}

	public List getFileNameList(String remotePath)
		throws Exception
	{
		List al;
		BufferedReader dr;
		al = null;
		dr = null;
		try
		{
			al = new ArrayList();
			ftpclient.cd(remotePath);
			dr = new BufferedReader(new InputStreamReader(ftpclient.list()));
			for (String s = ""; (s = dr.readLine()) != null;)
			{
				String tempStr = getFileName(s);
				if (!tempStr.equals(".") && !tempStr.equals(".."))
					al.add(tempStr);
			}

		}
		catch (Exception e)
		{
			throw new Exception((new StringBuilder("List<String> getFileNameList(String remotePath) ��ȡ�ļ����Ʒ����쳣���쳣��Ϣ��")).append(e.getMessage()).toString(), e);
		}
		break MISSING_BLOCK_LABEL_147;
		Exception exception;
		exception;
		if (dr != null)
			dr.close();
		throw exception;
		if (dr != null)
			dr.close();
		return al;
	}

	public boolean isDir(String line)
	{
		ArrayList list = parseLine(line);
		boolean rp = false;
		for (int i = 0; i < list.size(); i++)
			if (list.get(i).toString().indexOf("<DIR>") != -1)
				rp = true;

		return rp;
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

	public boolean deleteFile(String filename)
	{
		ftpclient.sendServer((new StringBuilder("DELE ")).append(filename).append("\r\n").toString());
		return true;
	}

	public boolean deleteFolder(String filename)
	{
		try
		{
			ftpclient.sendServer((new StringBuilder("RMD ")).append(filename).append("\r\n").toString());
		}
		catch (Exception e)
		{
			logger.error((new StringBuilder("ɾ���ļ��з����쳣���޷�ɾ���ļ��У���")).append(filename).append("��").toString(), e);
		}
		return true;
	}

	public boolean connectServer()
		throws Exception
	{
		if (ftpclient != null && ftpclient.serverIsOpen())
		{
			logger.debug("��ǰ�Ựδ���ڣ�����ʹ�ã�");
			ftpclient.openServer(ip, port);
			ftpclient.login(username, password);
			ftpclient.binary();
			return true;
		}
		logger.debug("�޿��ûỰ���ӣ���ʼ���¹������ӻỰ��");
		try
		{
			ftpclient = new FtpClient();
			ftpclient.openServer(ip, port);
			ftpclient.login(username, password);
			ftpclient.binary();
			logger.debug((new StringBuilder("���¹������ӻỰ������FTP Server is ��")).append(ftpclient.system()).toString());
		}
		catch (Exception e)
		{
			throw new Exception((new StringBuilder("���¹������ӻỰ�쳣�����ӷ����������쳣���쳣��Ϣ��")).append(e.getMessage()).toString(), e);
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
			if (ftpclient != null && ftpclient.serverIsOpen())
			{
				logger.debug("�쳣�����ʼ�رջỰ��");
				ftpclient.closeServer();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean download(String localPath, String remotePath)
		throws Exception
	{
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

	public String getPathSeparator(String path)
	{
		if (path == null || path.equals(""))
			return "";
		else
			return path.substring(path.length() - 1).equals("/") ? "" : "/";
	}

	public static void main(String args[])
		throws Exception
	{
		Globals.setSysRootDir("D:/003-workspace/workspace2014/MetLifeEmail/WebContent");
		FTPImportUtil ftpup = new FTPImportUtil("192.168.202.151", 21, "ftp417", "User2014");
		String returnPath[] = new String[5];
		String localPath = ZonyConfig.getCheckPolicySUCCESSPath();
		String tempimportPath = DateUtil.getNow(ZonyConfig.getCreaPathformat());
		localPath = (new StringBuilder(String.valueOf(localPath))).append(tempimportPath).toString();
		String rootPath = ZonyConfig.getPolicyImportPath();
		try
		{
			if (ftpup.connectServer())
			{
				List folderList = ftpup.getFileNameList(rootPath);
				if (folderList == null || folderList.size() < 1)
					logger.error("��ǰFTP���������ļ��У��˳���������ɨ��FTP��������");
				for (int i = 0; i < folderList.size(); i++)
				{
					if (!ftpup.connectServer())
					{
						logger.error("��ǰFTP���������ļ��У��˳���������ɨ��FTP��������");
						break;
					}
					String folderName = (String)folderList.get(i);
					String folderPath = (new StringBuilder(String.valueOf(rootPath))).append(ftpup.getPathSeparator(rootPath)).append(folderName).toString();
					logger.debug((new StringBuilder("ɨ�赽�ļ��� ,��")).append(i + 1).append("���ļ��У��ļ�������Ϊ����").append(folderName).append("��").toString());
					if (folderName.toLowerCase().startsWith("temp_"))
					{
						logger.error((new StringBuilder("��ǰ�ļ��С�")).append(folderName).append("��Ϊ��ʱ�ļ��У����ԶԴ��ļ��д������������ļ��д���").toString());
						continue;
					}
					List fileNameList = ftpup.getFileNameList((new StringBuilder(String.valueOf(rootPath))).append(ftpup.getPathSeparator(rootPath)).append(folderName).toString());
					if (fileNameList == null || fileNameList.size() < 1)
					{
						logger.error((new StringBuilder("��ǰ�ļ��С�")).append(folderName).append("�������ļ�,ɾ����ǰ�ļ��У�").toString());
						ftpup.deleteFolder(folderPath);
						continue;
					}
					if (fileNameList.size() != 2)
					{
						logger.error((new StringBuilder("��ǰ�ļ��С�")).append(folderName).append("�����ļ�����������ӦΪ����2�� ������ǰ�ļ�����Ϊ : ��").append(fileNameList.size()).append("��").toString());
						continue;
					}
					String pdfName = "";
					String xmlName = "";
					for (Iterator iterator = fileNameList.iterator(); iterator.hasNext();)
					{
						String fileName = (String)iterator.next();
						if (fileName.toLowerCase().endsWith(".xml"))
							xmlName = fileName;
						if (fileName.toLowerCase().endsWith(".pdf"))
							pdfName = fileName;
					}

					if (StringUtils.isEmpty(xmlName) || StringUtils.isEmpty(pdfName))
					{
						logger.error((new StringBuilder("��ǰ�ļ��С�")).append(folderName).append("�����ļ����Ͳ�����Ӧ�ô���XML�����ļ���PDF�����ļ�����ǰ��ȡ���ļ� : �� xml: ").append(xmlName).append(" ; pdf: ").append(pdfName).append("��").toString());
						continue;
					}
					String xmlPath = (new StringBuilder(String.valueOf(folderPath))).append(ftpup.getPathSeparator(folderPath)).append(xmlName).toString();
					String pdfPath = (new StringBuilder(String.valueOf(folderPath))).append(ftpup.getPathSeparator(folderPath)).append(pdfName).toString();
					String localFolderPath = (new StringBuilder(String.valueOf(localPath))).append(ftpup.getPathSeparator(localPath)).append(folderName).toString();
					FileUtil.CreateDir(localFolderPath);
					try
					{
						logger.debug((new StringBuilder("�����ļ�����")).append(xmlPath).append("��,���汾��·��Ϊ��").append(localFolderPath).append(ftpup.getPathSeparator(localFolderPath)).append(xmlName).toString());
						ftpup.downloadFile(xmlPath, (new StringBuilder(String.valueOf(localFolderPath))).append(ftpup.getPathSeparator(localFolderPath)).append(xmlName).toString());
						logger.debug((new StringBuilder("�����ļ�����")).append(xmlPath).append("��,���汾��·��Ϊ��").append(localFolderPath).append(ftpup.getPathSeparator(localFolderPath)).append(pdfName).toString());
						ftpup.downloadFile(pdfPath, (new StringBuilder(String.valueOf(localFolderPath))).append(ftpup.getPathSeparator(localFolderPath)).append(pdfName).toString());
						logger.debug((new StringBuilder("ɾ��������XML�ļ�����")).append(xmlPath).append("��").toString());
						ftpup.deleteFile(xmlPath);
						logger.debug((new StringBuilder("ɾ��������PDF�ļ�����")).append(pdfPath).append("��").toString());
						ftpup.deleteFile(pdfPath);
						logger.debug("���ء�ɾ���������ļ�������");
					}
					catch (Exception e)
					{
						logger.error((new StringBuilder("���ػ�ɾ���ļ������쳣���������ļ��У��ļ��У�")).append(localFolderPath).toString(), e);
						FileUtils.deleteDirectory(new File(localFolderPath));
						continue;
					}
					logger.debug((new StringBuilder("ɾ������������ļ���:��")).append(rootPath).append(ftpup.getPathSeparator(rootPath)).append(folderName).append("��").toString());
					if (!ftpup.deleteFolder((new StringBuilder(String.valueOf(rootPath))).append(ftpup.getPathSeparator(rootPath)).append(folderName).toString()))
					{
						logger.error("����������ļ��з���ʧ�ܣ�������ǰ�ļ��д���������������");
						continue;
					}
					returnPath[0] = localFolderPath;
					returnPath[1] = tempimportPath;
					returnPath[2] = pdfName;
					returnPath[3] = xmlName;
					returnPath[4] = folderName;
					break;
				}

			} else
			{
				logger.error("����ʧ�ܣ��޷���¼�ɹ���");
			}
		}
		catch (Exception e)
		{
			throw new Exception("��ȡ������·���������ļ������ز���������δ֪�쳣��", e);
		}
		String args1[];
		int k = (args1 = returnPath).length;
		for (int j = 0; j < k; j++)
		{
			String string = args1[j];
			System.out.println(string);
		}

	}

}
