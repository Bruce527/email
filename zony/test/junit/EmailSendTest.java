// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EmailSendTest.java

package com.zony.test.junit;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import org.springframework.mail.javamail.MimeMessageHelper;

public class EmailSendTest
{

	public EmailSendTest()
	{
	}

	public static void main(String args[])
		throws Exception
	{
		(new EmailSendTest()).doSendEmail();
	}

	public void doSendEmail()
		throws Exception
	{
		try
		{
			Properties props = new Properties();
			props.setProperty("mail.transport.protocol", "smtp");
			props.put("mail.smtp.host", "smtp.x263.net");
			props.put("mail.smtp.port", "25");
			props.put("mail.smtp.auth", "true");
			Session session = Session.getDefaultInstance(props, new Authenticator() {

				final EmailSendTest this$0;

				public PasswordAuthentication getPasswordAuthentication()
				{
					return new PasswordAuthentication("Telemarketing-test@metlife.com.cn", "Hello001");
				}

			
			{
				this$0 = EmailSendTest.this;
				super();
			}
			});
			MimeMessage msg = new MimeMessage(session);
			MimeMessageHelper helper = new MimeMessageHelper(msg, true, "utf-8");
			helper.setFrom(new InternetAddress("Telemarketing-test@metlife.com.cn"));
			helper.setTo("82872617@qq.com");
			helper.setSubject("hello");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(createContent("gubin test"));
			msg.setContent(multipart);
			Transport.send(msg);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception("系统错误，发送邮件失败");
		}
	}

	private MimeBodyPart createContent(String string)
		throws MessagingException
	{
		MimeBodyPart contentPart = new MimeBodyPart();
		MimeMultipart contentmultipart = new MimeMultipart("related");
		MimeBodyPart htmlBodyPart = new MimeBodyPart();
		htmlBodyPart.setContent(string, "text/html;charset=utf-8");
		contentmultipart.addBodyPart(htmlBodyPart);
		contentPart.setContent(contentmultipart);
		return contentPart;
	}
}
