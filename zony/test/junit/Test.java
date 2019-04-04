// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Test.java

package com.zony.test.junit;

import com.zony.app.logic.PolicyImportLogic;
import de.idyl.winzipaes.AesZipFileEncrypter;
import de.idyl.winzipaes.impl.AESEncrypterBC;
import java.io.*;
import java.util.*;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import org.apache.commons.io.FileUtils;

// Referenced classes of package com.zony.test.junit:
//			mailTest

public class Test
{

	public Test()
	{
	}

	public void before()
	{
		System.setProperty("sysRootDir", "WebContent");
	}

	public void test2()
	{
		PolicyImportLogic.getPolicyImportPath();
	}

	public void test1()
	{
		try
		{
			List keyList = new ArrayList();
			Map map = (new mailTest()).receiveEmail("Telemarketing-test@metlife.com.cn", "Hello01", keyList);
			String key;
			for (Iterator mepset = map.keySet().iterator(); mepset.hasNext(); System.out.println((new StringBuilder("Value+++++++++++++++++++++++++")).append((String)map.get(key)).toString()))
			{
				key = (String)mepset.next();
				System.out.println((new StringBuilder("Key+++++++++++++++++++++++++")).append(key).toString());
			}

		}
		catch (MessagingException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void testReadEmlFile()
	{
		try
		{
			System.out.println(readEmlFile(new FileInputStream("c:/保单邮件编号：92801879-96b8-4b78-be74-0ff33e303d97f6098ccd-7c9f-4b38-9dc4-9eb7576b93d81e3dcfbd-2384-49ba-9919-51cd138e81edca538599-5733-4c19-a.eml")));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (MessagingException e)
		{
			e.printStackTrace();
		}
	}

	public static String readEmlFile(InputStream fis)
		throws MessagingException
	{
		Object emlObj = fis;
		java.util.Properties props = System.getProperties();
		Session mailSession = Session.getInstance(props, null);
		MimeMessage msg = new MimeMessage(mailSession, fis);
		System.out.println(msg.getEncoding());
		String subJect = msg.getSubject();
		System.out.println(subJect);
		System.out.println(msg.getContentType());
		return subJect.substring(subJect.indexOf("大都会人寿电子保单　保单邮件编号") + "大都会人寿电子保单　保单邮件编号".length());
	}

	public String zipAESFile(String filePath, String pwd)
	{
		String zipName = (new StringBuilder(String.valueOf(filePath.substring(0, filePath.lastIndexOf("."))))).append(".zip").toString();
		String outZipFilePath = zipName;
		AESEncrypterBC aesBC = new AESEncrypterBC();
		try
		{
			AesZipFileEncrypter.zipFileAndEncrypt(new File(filePath), new File(outZipFilePath), pwd, aesBC);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return "";
		}
		return outZipFilePath;
	}

	public void zipAESFile(String filePath, String pwd, String outZipFolderPath)
	{
		try
		{
			String zipName = (new File(filePath)).getName();
			zipName = (new StringBuilder(String.valueOf(zipName.substring(0, zipName.lastIndexOf("."))))).append(".zip").toString();
			String outZipFilePath = (new StringBuilder(String.valueOf(outZipFolderPath))).append("\\").append(zipName).toString();
			AESEncrypterBC aesBC = new AESEncrypterBC();
			AesZipFileEncrypter.zipFileAndEncrypt(new File(filePath), new File(outZipFilePath), pwd, aesBC);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String args[])
		throws IOException
	{
		File tempZipFile = new File("c:\\新建文本文档.txt");
		File zipFile = new File("G:\\test1\\aaa.txt");
		FileUtils.moveFile(tempZipFile, zipFile);
	}
}
