// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   POP3MailReceiverTest.java

package com.zony.test.junit;

import java.io.*;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;

public class POP3MailReceiverTest
{

	public POP3MailReceiverTest()
	{
		Store store;
		Folder folder;
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", "smtp.x263.net");
		props.setProperty("mail.smtp.auth", "true");
		Session session = Session.getDefaultInstance(props);
		URLName urlname = new URLName("pop3", "smtp.x263.net", 110, null, "Telemarketing-test@metlife.com.cn", "Hello01");
		store = session.getStore(urlname);
		store.connect();
		folder = store.getDefaultFolder();
		if (folder == null)
		{
			System.out.println("服务器不可用");
			return;
		}
		try
		{
			Folder popFolder = folder.getFolder("INBOX");
			popFolder.open(2);
			Message messages[] = popFolder.getMessages();
			int msgCount = popFolder.getMessageCount();
			System.out.println((new StringBuilder("共有邮件: ")).append(msgCount).append("封").toString());
			for (int i = 0; i < msgCount; i++)
			{
				System.out.println((new StringBuilder("第")).append(i).append("邮件开始").toString());
				mailReceiver(messages[i]);
				System.out.println((new StringBuilder("第")).append(i).append("邮件结束").toString());
				messages[i].writeTo(new FileOutputStream((new StringBuilder("D:/pop3MailReceiver")).append(i).append(".eml").toString()));
			}

			popFolder.close(true);
			store.close();
		}
		catch (NoSuchProviderException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return;
	}

	private void mailReceiver(Message msg)
		throws Exception
	{
		javax.mail.Address froms[] = msg.getFrom();
		if (froms != null)
		{
			InternetAddress addr = (InternetAddress)froms[0];
			System.out.println((new StringBuilder("发件人地址:")).append(addr.getAddress()).toString());
			System.out.println((new StringBuilder("发件人显示名:")).append(addr.getPersonal()).toString());
			System.out.println((new StringBuilder("内容:")).append(msg.getContent()).toString());
		}
		System.out.println((new StringBuilder("邮件主题:")).append(msg.getSubject()).toString());
		Object o = msg.getContent();
		if (o instanceof Multipart)
		{
			Multipart multipart = (Multipart)o;
			reMultipart(multipart);
		} else
		if (o instanceof Part)
		{
			Part part = (Part)o;
			rePart(part);
		} else
		{
			System.out.println((new StringBuilder("类型")).append(msg.getContentType()).toString());
			System.out.println((new StringBuilder("内容")).append(msg.getContent()).toString());
		}
	}

	public String getMessageStr(Message msg)
		throws Exception
	{
		Object o = msg.getContent();
		if (o instanceof Multipart)
		{
			Multipart multipart = (Multipart)o;
			return reMultipart(multipart);
		}
		if (o instanceof Part)
		{
			Part part = (Part)o;
			return rePart(part);
		} else
		{
			return msg.getContent().toString();
		}
	}

	private String reMultipart(Multipart multipart)
		throws Exception
	{
		int j = 0;
		int n = multipart.getCount();
		if (j < n)
		{
			Part part = multipart.getBodyPart(j);
			if (part.getContent() instanceof Multipart)
			{
				Multipart p = (Multipart)part.getContent();
				return reMultipart(p);
			} else
			{
				return rePart(part);
			}
		} else
		{
			return null;
		}
	}

	private String rePart(Part part)
		throws MessagingException, UnsupportedEncodingException, IOException, FileNotFoundException
	{
		if (part.getDisposition() == null)
		{
			if (part.getContentType().startsWith("text/plain"))
				return part.getContent().toString();
			else
				return part.getContent().toString();
		} else
		{
			return null;
		}
	}

	private void reMultipart11(Multipart multipart)
		throws Exception
	{
		int j = 0;
		for (int n = multipart.getCount(); j < n; j++)
		{
			Part part = multipart.getBodyPart(j);
			if (part.getContent() instanceof Multipart)
			{
				Multipart p = (Multipart)part.getContent();
				reMultipart(p);
			} else
			{
				rePart(part);
			}
		}

	}

	public static void main(String args[])
	{
		new POP3MailReceiverTest();
	}
}
