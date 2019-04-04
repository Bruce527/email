// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SendMailUtil.java

package com.zony.common.util;

import com.zony.app.dao.PolicyInfoDao;
import com.zony.app.domain.EmailAccount;
import com.zony.app.domain.PolicyInfo;
import com.zony.common.config.ZonyConfig;
import freemarker.template.Configuration;
import java.awt.Color;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

// Referenced classes of package com.zony.common.util:
//			Globals, CreateImage, FileOperteUtil, CompressUtil

public class SendMailUtil
{

	private static Logger logger = Logger.getLogger(com/zony/common/util/SendMailUtil);
	private PolicyInfoDao policyInfoDao;

	public SendMailUtil()
	{
		policyInfoDao = (PolicyInfoDao)Globals.appContext.getBean("policyInfoDao");
	}

	public void doSendEmail(Long taskId, PolicyInfo policyInfo, final EmailAccount emailAccount)
		throws Exception
	{
		String newImgPath = "";
		String zipPath = "";
		try
		{
			String ownName = policyInfo.getOwnerName();
			String policyId = policyInfo.getCntrno();
			String ownerIDNumber = policyInfo.getOwnerIDNumber();
			if (emailAccount != null && emailAccount.getId().longValue() > 0L)
			{
				Properties props = new Properties();
				SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String timeStr = dateformat.format(new Date());
				props.setProperty("mail.transport.protocol", ZonyConfig.getEmailTransportProtocolSMTP());
				props.put("mail.smtp.host", ZonyConfig.getEmailSmtpHost());
				props.put("mail.smtp.port", ZonyConfig.getEmailSmtpPort());
				props.put("mail.smtp.auth", ZonyConfig.getEmailSmtpAuth());
				props.put("mail.smtp.connectiontimeout", Integer.valueOf(60000));
				props.put("mail.smtp.timeout", Integer.valueOf(60000));
				Session session = Session.getDefaultInstance(props, new Authenticator() {

					final SendMailUtil this$0;
					private final EmailAccount val$emailAccount;

					public PasswordAuthentication getPasswordAuthentication()
					{
						return new PasswordAuthentication(emailAccount.getUserName(), emailAccount.getPassword());
					}

			
			{
				this$0 = SendMailUtil.this;
				emailAccount = emailaccount;
				super();
			}
				});
				MimeMessage msg = new MimeMessage(session);
				MimeMessageHelper helper = new MimeMessageHelper(msg, true, "utf-8");
				helper.setFrom(new InternetAddress(emailAccount.getUserName()));
				String emailCode = (new StringBuilder(String.valueOf(policyInfo.getCntrno()))).append(" ").append(timeStr).toString();
				policyInfo.setEmailCode(emailCode);
				policyInfoDao.update(policyInfo);
				helper.setTo(policyInfo.getOwnerMail());
				helper.setSubject((new StringBuilder("大都会人寿电子保单　保单邮件编号")).append(policyInfo.getEmailCode()).toString());
				Map map = new HashMap();
				map.put("user", "<img src='cid:user_jpg'/>");
				map.put("identifier", policyId);
				String htmlText = "";
				newImgPath = (new StringBuilder(String.valueOf(ZonyConfig.getTempFolder()))).append("userName{").append(UUID.randomUUID().toString()).append("}.jpg").toString();
				if ("01".equals(policyInfo.getChn()))
					htmlText = getMailText("chn01.ftl", map);
				else
				if ("03".equals(policyInfo.getChn()))
				{
					map.put("declare", (new StringBuilder(String.valueOf(ZonyConfig.getReceiptURL()))).append(policyInfo.getEmailCode()).toString());
					htmlText = getMailText("chn03.ftl", map);
				} else
				if ("交行信用卡-武汉".equals(policyInfo.getProjectName()) || "交行信用卡-花桥".equals(policyInfo.getProjectName()))
				{
					map.put("declare", (new StringBuilder(String.valueOf(ZonyConfig.getReceiptURL()))).append(policyInfo.getEmailCode()).append("&ProjectName=BCM").toString());
					htmlText = getMailText("General_BCM.ftl", map);
				} else
				if (policyInfo.getTrancode() != null && !policyInfo.getTrancode().equals("TA78"))
				{
					if (policyInfo.getPrintPolicyIndicator() != null && policyInfo.getPrintPolicyIndicator().equals("Y"))
					{
						map.put("declare", (new StringBuilder(String.valueOf(ZonyConfig.getDeclareURL()))).append(policyInfo.getEmailCode()).toString());
						htmlText = getMailText("General_Receipt.ftl", map);
					} else
					if (policyInfo.getPrintPolicyIndicator() != null && policyInfo.getPrintPolicyIndicator().equals("N"))
					{
						map.put("declare", (new StringBuilder(String.valueOf(ZonyConfig.getReceiptURL()))).append(policyInfo.getEmailCode()).toString());
						htmlText = getMailText("General.ftl", map);
					}
				} else
				if (policyInfo.getPol_AcknowledgementDate() != null && !policyInfo.getPol_AcknowledgementDate().equals("0"))
				{
					map.put("declare", (new StringBuilder(String.valueOf(ZonyConfig.getReceiptURL()))).append(policyInfo.getEmailCode()).toString());
					htmlText = getMailText("NO_Template.ftl", map);
				} else
				if (policyInfo.getPrintPolicyIndicator() != null && policyInfo.getPrintPolicyIndicator().equals("Y"))
				{
					map.put("declare", (new StringBuilder(String.valueOf(ZonyConfig.getDeclareURL()))).append(policyInfo.getEmailCode()).toString());
					htmlText = getMailText("General_Receipt.ftl", map);
				} else
				if (policyInfo.getPrintPolicyIndicator() != null && policyInfo.getPrintPolicyIndicator().equals("N"))
				{
					map.put("declare", (new StringBuilder(String.valueOf(ZonyConfig.getReceiptURL()))).append(policyInfo.getEmailCode()).toString());
					htmlText = getMailText("General.ftl", map);
				}
				int nameWidth = 680;
				ownName = (new StringBuilder("尊敬的")).append(ownName).append("客户 :").toString();
				CreateImage.createImageByStr(nameWidth, 14, ownName, newImgPath, 14, Color.WHITE, Color.black);
				String filePath = (new StringBuilder(String.valueOf(ZonyConfig.getCheckPolicySUCCESSPath()))).append("/").append(policyInfo.getFilePath()).toString();
				zipPath = zipAESFile(filePath, ownerIDNumber.substring(ownerIDNumber.length() - 4));
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(createContent(newImgPath, htmlText));
				multipart.addBodyPart(createAttachment(zipPath));
				msg.setContent(multipart);
				Transport.send(msg);
				FileOperteUtil.deleteFile(newImgPath);
				FileOperteUtil.deleteFile(zipPath);
			} else
			{
				throw new Exception("没有可用的邮件发送账户");
			}
		}
		catch (SendFailedException e1)
		{
			if (e1.getMessage().equals("Invalid Addresses"))
			{
				FileOperteUtil.deleteFile(newImgPath);
				FileOperteUtil.deleteFile(zipPath);
				policyInfo.setSendStatus("-4");
			} else
			{
				e1.printStackTrace();
				throw new Exception((new StringBuilder("发送邮件失败：")).append(e1.toString()).toString(), e1);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception((new StringBuilder("发送邮件失败：")).append(e.toString()).toString(), e);
		}
	}

	private MimeBodyPart createContent(String imgPath, String string)
		throws MessagingException
	{
		MimeBodyPart contentPart = new MimeBodyPart();
		MimeMultipart contentmultipart = new MimeMultipart("related");
		MimeBodyPart htmlBodyPart = new MimeBodyPart();
		htmlBodyPart.setContent(string, "text/html;charset=utf-8");
		contentmultipart.addBodyPart(htmlBodyPart);
		File img = new File(imgPath);
		MimeBodyPart jpgBody = new MimeBodyPart();
		FileDataSource fds = new FileDataSource(img);
		jpgBody.setDataHandler(new DataHandler(fds));
		jpgBody.setContentID("user_jpg");
		contentmultipart.addBodyPart(jpgBody);
		contentPart.setContent(contentmultipart);
		return contentPart;
	}

	private String getMailText(String mailTemplateName, Map dataMap)
	{
		String htmlText = null;
		try
		{
			Configuration cfg = new Configuration();
			try
			{
				cfg.setDirectoryForTemplateLoading(new File(ZonyConfig.getTemplateFolder()));
				cfg.setEncoding(Locale.SIMPLIFIED_CHINESE, "utf-8");
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
			freeMarkerConfigurer.setConfiguration(cfg);
			freemarker.template.Template tpl = freeMarkerConfigurer.getConfiguration().getTemplate(mailTemplateName);
			htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, dataMap);
			System.out.println((new StringBuilder("htmlText:")).append(htmlText).toString());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return htmlText;
	}

	private MimeBodyPart createAttachment(String filePath)
		throws Exception
	{
		MimeBodyPart attchBodyPart = new MimeBodyPart();
		FileDataSource fds = new FileDataSource(filePath);
		attchBodyPart.setDataHandler(new DataHandler(fds));
		String newName = MimeUtility.encodeWord(fds.getName());
		attchBodyPart.setFileName(newName);
		return attchBodyPart;
	}

	public String zipAESFile(String filePath, String pwd)
	{
		String zipName = (new File(filePath)).getName();
		zipName = (new StringBuilder(String.valueOf(zipName.substring(0, zipName.lastIndexOf("."))))).append(".zip").toString();
		String outZipFilePath = (new StringBuilder(String.valueOf(ZonyConfig.getTempFolder()))).append(zipName).toString();
		try
		{
			CompressUtil.zip(filePath, outZipFilePath, pwd);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("生成zip压缩包并加密发送错误，错误信息：", e);
			return "";
		}
		return outZipFilePath;
	}

	public static void main(String args[])
	{
		String mailStr = "echo.yang@metlife.com";
		System.out.println(isEmail(mailStr));
	}

	public static boolean isEmail(String email)
	{
		if (email == null)
			return false;
		return email.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+");
	}

}
