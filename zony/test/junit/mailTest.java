// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   mailTest.java

package com.zony.test.junit;

import com.zony.common.config.ZonyConfig;
import java.io.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class mailTest
{

	public mailTest()
	{
	}

	public Map receiveEmail(final String userName, final String passWord, List keyList)
		throws Exception
	{
		Map reMap = new HashMap();
		String pop3port = ZonyConfig.getEmailPop3Port();
		String pop3address = ZonyConfig.getEmailPop3Host();
		Properties props = System.getProperties();
		props.put("mail.smtp.host", ZonyConfig.getEmailSmtpHost());
		props.put("mail.smtp.auth", ZonyConfig.getEmailSmtpAuth());
		Session session = Session.getInstance(props, new Authenticator() {

			final mailTest this$0;
			private final String val$userName;
			private final String val$passWord;

			public PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(userName, passWord);
			}

			
			{
				this$0 = mailTest.this;
				userName = s;
				passWord = s1;
				super();
			}
		});
		URLName urln = new URLName(ZonyConfig.getEmailTransportProtocolPOP3(), pop3address, Integer.valueOf(pop3port).intValue(), null, userName, passWord);
		Store store = session.getStore(urln);
		store.connect();
		Folder folder = store.getFolder("INBOX");
		folder.open(2);
		Message message[] = folder.getMessages();
		Message amessage[];
		int j = (amessage = message).length;
		for (int i = 0; i < j; i++)
		{
			Message element = amessage[i];
			try
			{
				InputStream fis = mailReceiver(element);
				reMap.put("test", readEmlFile(fis));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			element.setFlag(javax.mail.Flags.Flag.USER, true);
		}

		folder.close(true);
		store.close();
		return reMap;
	}

	public static String readEmlFile(InputStream fis)
		throws MessagingException, FileNotFoundException
	{
		Object emlObj = fis;
		Properties props = System.getProperties();
		props.put("mail.smtp.host", "");
		props.put("mail.smtp.auth", "");
		Session mailSession = Session.getInstance(props, null);
		InputStream fiss = new FileInputStream("D:/12312222222.eml");
		MimeMessage msg = new MimeMessage(mailSession, fiss);
		System.out.println(msg.getEncoding());
		String subJect = msg.getSubject();
		System.out.println(subJect);
		System.out.println(msg.getContentType());
		return subJect.substring(subJect.indexOf("大都会人寿电子保单　保单邮件编号") + "大都会人寿电子保单　保单邮件编号".length());
	}

	private InputStream mailReceiver(Message msg)
		throws Exception
	{
		javax.mail.Address froms[] = msg.getFrom();
		if (froms != null)
		{
			InternetAddress addr = (InternetAddress)froms[0];
			System.out.println((new StringBuilder("发件人地址:")).append(addr.getAddress()).toString());
			System.out.println((new StringBuilder("发件人显示名:")).append(addr.getPersonal()).toString());
		}
		System.out.println((new StringBuilder("邮件主题:")).append(msg.getSubject()).toString());
		Object o = msg.getContent();
		InputStream in = null;
		if (o instanceof Multipart)
		{
			Multipart multipart = (Multipart)o;
			for (int i = 0; i < multipart.getCount(); i++)
			{
				Part part = multipart.getBodyPart(i);
				if (!(part.getContent() instanceof Part))
					continue;
				in = getSystemReAttach(part);
				break;
			}

		} else
		if (o instanceof Part)
		{
			Part part = (Part)o;
			in = rePart(part);
		} else
		{
			System.out.println((new StringBuilder("类型")).append(msg.getContentType()).toString());
			System.out.println((new StringBuilder("内容")).append(msg.getContent()).toString());
		}
		return in;
	}

	private InputStream rePart(Part part)
		throws Exception
	{
		if (part.getDisposition() != null)
		{
			String strFileNmae = MimeUtility.decodeText(part.getFileName());
			System.out.println((new StringBuilder("发现附件: ")).append(MimeUtility.decodeText(part.getFileName())).toString());
			System.out.println((new StringBuilder("内容类型: ")).append(MimeUtility.decodeText(part.getContentType())).toString());
			System.out.println((new StringBuilder("附件内容:")).append(part.getContent()).toString());
			InputStream in = part.getInputStream();
			FileOutputStream out = new FileOutputStream(strFileNmae);
			int data;
			while ((data = in.read()) != -1) 
				out.write(data);
			in.close();
			out.close();
		} else
		{
			if (part.getContentType().startsWith("text/plain"))
				System.out.println((new StringBuilder("文本内容：")).append(part.getContent()).toString());
			if (part.getContentType().indexOf("message/rfc822") != -1)
				return getSystemReAttach(part);
			System.out.println((new StringBuilder("文本内容：")).append(part.getContent()).toString());
		}
		return null;
	}

	private InputStream getSystemReAttach(Part part)
		throws Exception
	{
		if (part.getContentType().indexOf("message/rfc822") != -1)
		{
			InputStream in = part.getInputStream();
			System.out.println((new StringBuilder("系统退信附件：")).append(part.getContent()).toString());
			FileOutputStream out = new FileOutputStream("D:/12312222222.eml");
			int data;
			while ((data = in.read()) != -1) 
				out.write(data);
			in.close();
			out.close();
			return in;
		} else
		{
			return null;
		}
	}

	private InputStream reMultipart(Multipart multipart)
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
				InputStream in = rePart(part);
				return in;
			}
		} else
		{
			return null;
		}
	}
}
