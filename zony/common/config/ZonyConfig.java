// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ZonyConfig.java

package com.zony.common.config;

import com.zony.common.util.Globals;
import edu.emory.mathcs.backport.java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class ZonyConfig
{

	public ZonyConfig()
	{
	}

	public static String getSystemManagerName()
	{
		return Globals.getProperty("systemName");
	}

	public static String getSystemManagerPassword()
	{
		return Globals.getProperty("systemPWD");
	}

	public static String getLdapurl()
	{
		return Globals.getProperty("ldap.url");
	}

	public static String getLdapport()
	{
		return Globals.getProperty("ldap.port");
	}

	public static String getLdaploginPwd()
	{
		return Globals.getProperty("ldap.loginpwd");
	}

	public static String getLdaploginName()
	{
		return Globals.getProperty("ldap.loginname");
	}

	public static String getLdaproot()
	{
		return Globals.getProperty("ldap.root");
	}

	public static String getLdapSearchBase_Org()
	{
		return Globals.getProperty("ldap.baseOrg");
	}

	public static String getLdapSearchBase_Def()
	{
		return Globals.getProperty("ldap.baseDef");
	}

	public static String getLdapSearchBase_Usr()
	{
		return Globals.getProperty("ldap.baseUsr");
	}

	public static String getSystemUserId()
	{
		return Globals.getProperty("ldap.systemuserid");
	}

	public static String getPolicyTMPath()
	{
		return Globals.getProperty("policy.TMPath");
	}

	public static String getPolicyImportPath()
	{
		return Globals.getProperty("policy.importPath");
	}

	public static String getCheckPolicyFAILPath()
	{
		return Globals.getProperty("policy.checkFailPath");
	}

	public static String getCheckPolicySUCCESSPath()
	{
		return Globals.getProperty("policy.successPath");
	}

	public static int getCHECK_POLICYNOTICE_THREAD_COUNT()
	{
		return Integer.parseInt(Globals.getProperty("policy.checkThreadNumber"));
	}

	public static int getCheck_ImporPolicyWatingtime()
	{
		return Integer.parseInt(Globals.getProperty("policy.checkThreadWaitTime"));
	}

	public static String getImportPolicyDefaultXMLName()
	{
		return Globals.getProperty("policy.defaultXmlName");
	}

	public static String getImportPolicyDefaultPDFName()
	{
		return Globals.getProperty("policy.defaultPdfName");
	}

	public static String getTemp_FolderName()
	{
		return Globals.getProperty("policy.tempFolderName");
	}

	public static String getPolicy_CHNFilterCode()
	{
		return Globals.getProperty("policy.policyCHNFilter");
	}

	public static String getFTP_Server()
	{
		return Globals.getProperty("ftp.server");
	}

	public static int getFTP_Port()
	{
		return Integer.parseInt(Globals.getProperty("ftp.port"));
	}

	public static String getFTP_userUserName()
	{
		return Globals.getProperty("ftp.userName");
	}

	public static String getFTP_userPassword()
	{
		return Globals.getProperty("ftp.userPassword");
	}

	public static String getImportPolicyFlag()
	{
		return Globals.getProperty("policy.isNeedImport");
	}

	public static int getUUIDLength()
	{
		return Integer.parseInt(Globals.getProperty("policy.UUIDLength"));
	}

	public static String getCreaPathformat()
	{
		return Globals.getProperty("policy.createPathFormat");
	}

	public static int getDefaultPageSize()
	{
		int pageSize;
		pageSize = Integer.parseInt(Globals.getProperty("defaultPageSize"));
		if (pageSize <= 0)
			pageSize = 2000;
		return pageSize;
		NumberFormatException e;
		e;
		return 2000;
	}

	public static int getMaxLockPeriod()
	{
		int maxLockPeriod;
		maxLockPeriod = Integer.parseInt(Globals.getProperty("maxLockPeriod"));
		if (maxLockPeriod < 0)
			maxLockPeriod = 24;
		return maxLockPeriod;
		NumberFormatException e;
		e;
		return 24;
	}

	public static int getEmailSendCount()
	{
		return 1000;
	}

	public static String getEmailRootAddress()
	{
		return Globals.getProperty("email.root-address");
	}

	public static String getEmailTransportProtocolSMTP()
	{
		return Globals.getProperty("email.smtp-transport-protocol");
	}

	public static String getEmailSmtpHost()
	{
		return Globals.getProperty("email.smtp-host");
	}

	public static String getEmailSmtpPort()
	{
		return Globals.getProperty("email.smtp-port");
	}

	public static String getEmailSmtpAuth()
	{
		return Globals.getProperty("email.smtp-auth");
	}

	public static String getEmailTransportProtocolPOP3()
	{
		return Globals.getProperty("email.pop3-transport-protocol");
	}

	public static String getEmailPop3Host()
	{
		return Globals.getProperty("email.pop3-host");
	}

	public static String getEmailPop3Port()
	{
		return Globals.getProperty("email.pop3-port");
	}

	public static String getEmailPop3Auth()
	{
		return Globals.getProperty("email.pop3-auth");
	}

	public static int getSendThreadNumber()
	{
		return Integer.parseInt(Globals.getProperty("email.sendThreadNumber"));
	}

	public static int getReceiptThreadNumber()
	{
		return Integer.parseInt(Globals.getProperty("email.receiptThreadNumber"));
	}

	public static String getDeclareURL()
	{
		return (new StringBuilder(String.valueOf(Globals.getProperty("webUrl")))).append("/checkbackservlet.do?type=").append("2").append("&mailCode=").toString();
	}

	public static String getReceiptURL()
	{
		return (new StringBuilder(String.valueOf(Globals.getProperty("webUrl")))).append("/checkbackservlet.do?type=").append("4").append("&mailCode=").toString();
	}

	public static String getBFBDURL()
	{
		return (new StringBuilder(String.valueOf(Globals.getProperty("webUrl")))).append("/checkbackservlet.do?type=").append("3").append("&mailCode=").toString();
	}

	public static String getExcelTempPath()
	{
		return Globals.getProperty("tempPath");
	}

	public static String getTempFolder()
	{
		return Globals.getProperty("tempFolder");
	}

	public static String getTemplateFolder()
	{
		return Globals.getProperty("templateFolder");
	}

	public static String getDomainName()
	{
		return Globals.getProperty("ldap.domainName");
	}

	public static List getTMProjectCode()
	{
		String codeStr = Globals.getProperty("policy.TMProjectCode");
		String array[] = codeStr.split(",");
		return Arrays.asList(array);
	}

	public static List getTMChnCode()
	{
		String codeStr = Globals.getProperty("policy.TMChnCode");
		if (codeStr != null)
		{
			String array[] = codeStr.split(",");
			return Arrays.asList(array);
		} else
		{
			return new ArrayList();
		}
	}

	public static void deployTMProjectCode(String value)
	{
		Globals.setProperty("policy.TMProjectCode", value);
	}

	public static void deployTMChnCode(String value)
	{
		Globals.setProperty("policy.TMChnCode", value);
	}

	public static String getTranPassTime()
	{
		return Globals.getProperty("EAI.transferPassTime");
	}

	public static void deployTranPassTime(String value)
	{
		Globals.setProperty("EAI.transferPassTime", value);
	}

	public static String getTranAgainTime()
	{
		return Globals.getProperty("EAI.transferAgainTime");
	}

	public static void deployTranAgainTime(String value)
	{
		Globals.setProperty("EAI.transferAgainTime", value);
	}

	public static String getTranCount()
	{
		return Globals.getProperty("EAI.transferCount");
	}

	public static void deployTranCount(String value)
	{
		Globals.setProperty("EAI.transferCount", value);
	}

	public static String getEAIUrl()
	{
		return Globals.getProperty("EAI.wsdlUrl");
	}

	public static String getEAISrvOpName()
	{
		return Globals.getProperty("EAI.srvOpName");
	}

	public static String getEAISenderID()
	{
		return Globals.getProperty("EAI.senderID");
	}

	public static String getEAIReceiverID()
	{
		return Globals.getProperty("EAI.receiverID");
	}

	public static String getEAILocalSystem()
	{
		return Globals.getProperty("EAI.localSystem");
	}

	public static String getEAIAppRole()
	{
		return Globals.getProperty("EAI.appRole");
	}

	public static String getEAITransferBeginDate()
	{
		return Globals.getProperty("EAI.transferBeginDate");
	}
}
