// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EmailReceiveUtil.java

package com.zony.common.util;

import com.zony.app.constants.Constant;
import com.zony.app.domain.VemailKey;
import com.zony.app.logic.PolicyLogLogic;
import com.zony.common.config.ZonyConfig;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.*;
import javax.mail.internet.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

// Referenced classes of package com.zony.common.util:
//			Globals, ExceptionUtils, SysLogUtil, FileOperteUtil

public class EmailReceiveUtil
{

	private static PolicyLogLogic policyLogLogic;
	private static final Logger logger = Logger.getLogger(com/zony/common/util/EmailReceiveUtil);
	private static String ENDSTR = "?=";
	private static String ERRMESSAGEBEGINSTR = "<div style=\"color:#cd0021; font-weight:bold; line-height:18px;\">";
	private static String ERRMESSAGEENDSTR = "</div>";

	public EmailReceiveUtil()
	{
	}

	public static Map receiveEmail(final String userName, final String passWord, List keyList)
		throws Exception
	{
		Map reMap = new HashMap();
		String pop3port = ZonyConfig.getEmailPop3Port();
		String pop3address = ZonyConfig.getEmailPop3Host();
		Properties props = System.getProperties();
		props.put("mail.smtp.host", ZonyConfig.getEmailSmtpHost());
		props.put("mail.smtp.auth", ZonyConfig.getEmailSmtpAuth());
		props.put("mail.pop3.connectiontimeout", Integer.valueOf(60000));
		props.put("mail.pop3.timeout", Integer.valueOf(60000));
		Session session = Session.getDefaultInstance(props, new Authenticator() {

			private final String val$userName;
			private final String val$passWord;

			public PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(userName, passWord);
			}

			
			{
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
				Map resultMap = doReceiveEmail(element, keyList);
				if (resultMap != null && resultMap.size() > 0)
					reMap.putAll(resultMap);
				System.out.println((new StringBuilder()).append(getMailContent(element)).toString());
			}
			catch (Exception e)
			{
				logger.error(ExceptionUtils.getErrorTraceMessage(e));
				e.printStackTrace();
			}
			element.setFlag(javax.mail.Flags.Flag.DELETED, true);
		}

		folder.close(true);
		store.close();
		return reMap;
	}

	private static Map doReceiveEmail(Message message, List keyList)
		throws Exception
	{
		System.out.println("收到一封邮件！");
		Map resultMap = new HashMap();
		String emailFrom = message.getFrom()[0].toString();
		emailFrom = getEmailFrom(emailFrom);
		String messageStr = getMessageStr(message);
		String emailCode = getEmailCode(message);
		SysLogUtil.getDebugLog().debug((new StringBuilder("收到退信,emailFrom:")).append(emailFrom).append(",emailCode:").append(emailCode).toString());
		if (StringUtils.isEmpty(emailCode))
			return null;
		String resultStr = getEmailStatus(messageStr, emailFrom, keyList);
		if (StringUtils.isEmpty(resultStr))
		{
			resultMap.put(emailCode, (new StringBuilder("-5")).append(Constant.STRSEPARATOR).append(getMailContent(message)).toString());
			return resultMap;
		}
		int strSeparatorIndex = resultStr.indexOf(Constant.STRSEPARATOR);
		if (strSeparatorIndex >= 0)
		{
			String errStr = resultStr.substring(strSeparatorIndex + Constant.STRSEPARATOR.length());
			if (StringUtils.isEmpty(errStr))
			{
				resultMap.put(emailCode, (new StringBuilder("-5")).append(Constant.STRSEPARATOR).append(getMailContent(message)).toString());
				return resultMap;
			} else
			{
				resultMap.put(emailCode, resultStr);
				return resultMap;
			}
		} else
		{
			return null;
		}
	}

	public static String getMessageStr(Message msg)
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

	private static String reMultipart(Multipart multipart)
		throws Exception
	{
		int i = 0;
		if (i < multipart.getCount())
		{
			Part part = multipart.getBodyPart(i);
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

	private static String rePart(Part part)
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

	private static String getEmailCode(Message msg)
	{
		String emailCode;
		String emlPath;
		emailCode = "";
		emlPath = (new StringBuilder(String.valueOf(ZonyConfig.getTempFolder()))).append(UUID.randomUUID().toString()).append(".eml").toString();
		try
		{
			mailReceiver(msg, emlPath);
			File tempFile = new File(emlPath);
			if (tempFile.exists())
			{
				SysLogUtil.getDebugLog().debug((new StringBuilder("emlPath exists:")).append(emlPath).toString());
				emailCode = readEmlFile(emlPath);
			} else
			{
				SysLogUtil.getDebugLog().debug("emlPath not exists");
				emailCode = readEmlFile(msg);
			}
			break MISSING_BLOCK_LABEL_150;
		}
		catch (Exception e)
		{
			logger.error((new StringBuilder("解析邮件唯一编号异常，异常信息：")).append(ExceptionUtils.getErrorTraceMessage(e)).toString());
		}
		FileOperteUtil.deleteFile(emlPath);
		break MISSING_BLOCK_LABEL_155;
		Exception exception;
		exception;
		FileOperteUtil.deleteFile(emlPath);
		throw exception;
		FileOperteUtil.deleteFile(emlPath);
		SysLogUtil.getDebugLog().debug((new StringBuilder("emailCode:")).append(emailCode).toString());
		return emailCode;
	}

	public static String readEmlFile(Message msg)
		throws MessagingException
	{
		String subJect = msg.getSubject();
		if (!StringUtils.isEmpty(subJect) && subJect.indexOf("大都会人寿电子保单　保单邮件编号") >= 0)
			return subJect.substring(subJect.indexOf("大都会人寿电子保单　保单邮件编号") + "大都会人寿电子保单　保单邮件编号".length());
		else
			return subJect;
	}

	public static String readEmlFile(String emlFilePath)
		throws MessagingException, IOException
	{
		Properties props = System.getProperties();
		Session mailSession = Session.getInstance(props, null);
		InputStream fis = new FileInputStream(emlFilePath);
		MimeMessage msg = new MimeMessage(mailSession, fis);
		System.out.println(msg.getEncoding());
		String subJect = msg.getSubject();
		System.out.println(subJect);
		System.out.println(msg.getContentType());
		fis.close();
		if (!StringUtils.isEmpty(subJect) && subJect.indexOf("大都会人寿电子保单　保单邮件编号") >= 0)
			return subJect.substring(subJect.indexOf("大都会人寿电子保单　保单邮件编号") + "大都会人寿电子保单　保单邮件编号".length());
		else
			return subJect;
	}

	private static void mailReceiver(Message msg, String emlFilePath)
		throws Exception
	{
		Address froms[] = msg.getFrom();
		if (froms != null)
		{
			InternetAddress addr = (InternetAddress)froms[0];
			System.out.println((new StringBuilder("发件人地址:")).append(addr.getAddress()).toString());
			System.out.println((new StringBuilder("发件人显示名:")).append(addr.getPersonal()).toString());
		}
		System.out.println((new StringBuilder("邮件主题:")).append(msg.getSubject()).toString());
		Object o = msg.getContent();
		if (o instanceof Multipart)
		{
			Multipart multipart = (Multipart)o;
			for (int i = 0; i < multipart.getCount(); i++)
			{
				Part part = multipart.getBodyPart(i);
				if (part.getContent() instanceof Part)
				{
					getSystemReAttach(part, emlFilePath);
					break;
				}
				if (!(part.getContent() instanceof InputStream))
					continue;
				getSystemReAttachInputStream(part, emlFilePath);
				break;
			}

		} else
		if (o instanceof Part)
		{
			Part part = (Part)o;
			rePart(part, emlFilePath);
		} else
		{
			SysLogUtil.getDebugLog().debug((new StringBuilder("Class")).append(o.getClass()).append(",类型").append(msg.getContentType()).append(",内容").append(msg.getContent()).toString());
		}
	}

	private static void rePart(Part part, String emlFilePath)
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
				getSystemReAttach(part, emlFilePath);
		}
	}

	private static InputStream getSystemReAttachInputStream(Part part, String emlFilePath)
		throws Exception
	{
		SysLogUtil.getDebugLog().debug("getSystemReAttachInputStream");
		InputStream in = (InputStream)part.getContent();
		FileOutputStream out = new FileOutputStream(emlFilePath);
		int data;
		while ((data = in.read()) != -1) 
			out.write(data);
		in.close();
		out.close();
		return in;
	}

	private static InputStream getSystemReAttach(Part part, String emlFilePath)
		throws Exception
	{
		SysLogUtil.getDebugLog().debug("getSystemReAttach");
		if (part.getContentType().indexOf("message/rfc822") != -1)
		{
			InputStream in = part.getInputStream();
			System.out.println((new StringBuilder("系统退信附件：")).append(part.getContent()).toString());
			FileOutputStream out = new FileOutputStream(emlFilePath);
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

	private static String getEmailCode_Bak(String massageStr)
	{
		String emailCode = "";
		try
		{
			BASE64Decoder base64 = new BASE64Decoder();
			BASE64Encoder base64en = new BASE64Encoder();
			String en = base64en.encode("大都会人寿电子保单　保单邮件编号".getBytes());
			int beginIndex = massageStr.indexOf(en);
			if (beginIndex >= 0)
			{
				massageStr = massageStr.substring(beginIndex);
				int endIndex = massageStr.indexOf(ENDSTR);
				massageStr = massageStr.substring(en.length(), endIndex);
				if (StringUtils.isNotEmpty(massageStr))
					emailCode = new String(base64.decodeBuffer(massageStr));
			}
		}
		catch (Exception e)
		{
			logger.error((new StringBuilder("解析邮件唯一编号异常，异常信息：")).append(ExceptionUtils.getErrorTraceMessage(e)).append("\n 邮件内容：").append(massageStr).toString());
			System.out.println(massageStr);
		}
		return emailCode;
	}

	private static String getEmailFrom(String fromStr)
	{
		Pattern p = Pattern.compile("<(.*?)>");
		Matcher m = p.matcher(fromStr);
		if (m.find())
		{
			String tmpStr = m.group();
			tmpStr = tmpStr.substring(1);
			tmpStr = tmpStr.substring(0, tmpStr.length() - 1);
			return tmpStr;
		} else
		{
			return fromStr;
		}
	}

	private static String getEmailStatus(String messageStr, String emailFrom, List keyList)
	{
		String sendStatus = "";
		for (Iterator iterator = keyList.iterator(); iterator.hasNext();)
		{
			VemailKey vemailKey = (VemailKey)iterator.next();
			if (vemailKey.getPostmaster().toUpperCase().equals(emailFrom.toUpperCase()))
			{
				for (Iterator iterator1 = keyList.iterator(); iterator1.hasNext();)
				{
					VemailKey key = (VemailKey)iterator1.next();
					if (key.getPostmaster().toUpperCase().equals(emailFrom.toUpperCase()) && messageStr.indexOf(key.getKeyStr()) != -1)
						sendStatus = key.getSendStatus();
				}

				if (StringUtils.isEmpty(sendStatus))
					sendStatus = "-5";
				String errorMessage = "";
				int beginIndex = messageStr.indexOf(ERRMESSAGEBEGINSTR);
				if (beginIndex >= 0)
				{
					errorMessage = messageStr.substring(beginIndex);
					int endIndex = errorMessage.indexOf(ERRMESSAGEENDSTR);
					if (endIndex >= 0)
						errorMessage = errorMessage.substring(ERRMESSAGEBEGINSTR.length(), endIndex);
				}
				return (new StringBuilder(String.valueOf(sendStatus))).append(Constant.STRSEPARATOR).append(errorMessage).toString();
			}
		}

		return null;
	}

	private static StringBuffer getMailContent(Part part)
		throws Exception
	{
		StringBuffer bodytext = new StringBuffer();
		String contenttype = part.getContentType();
		int nameindex = contenttype.indexOf("name");
		boolean conname = false;
		if (nameindex != -1)
			conname = true;
		if (part.isMimeType("text/plain") && !conname)
			bodytext.append((String)part.getContent());
		else
		if (part.isMimeType("text/html") && !conname)
			bodytext.append((String)part.getContent());
		else
		if (part.isMimeType("multipart/*"))
		{
			Multipart multipart = (Multipart)part.getContent();
			int counts = multipart.getCount();
			for (int i = 0; i < counts; i++)
				getMailContent(((Part) (multipart.getBodyPart(i))));

		} else
		if (part.isMimeType("message/rfc822"))
			getMailContent((Part)part.getContent());
		if (bodytext.length() > 8000)
			bodytext = new StringBuffer(bodytext.toString().substring(0, 8000));
		return bodytext;
	}

	public static void main(String args1[])
	{
	}

	static 
	{
		policyLogLogic = (PolicyLogLogic)Globals.appContext.getBean("policyLogLogic");
	}
}
