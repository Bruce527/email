// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EmailReceiptLogic.java

package com.zony.app.logic;

import com.zony.common.util.Globals;
import java.util.List;
import org.apache.log4j.Logger;

// Referenced classes of package com.zony.app.logic:
//			EmailTaskLogic

public class EmailReceiptLogic
{

	private final Logger logger = Logger.getLogger(com/zony/app/logic/EmailReceiptLogic);
	private EmailTaskLogic emailTaskLogic;
	private static EmailReceiptLogic emailReceiptManager;

	public EmailReceiptLogic()
	{
		emailTaskLogic = null;
	}

	public void setEmailTaskLogic(EmailTaskLogic emailTaskLogic)
	{
		this.emailTaskLogic = emailTaskLogic;
	}

	public static synchronized EmailReceiptLogic getInstance()
	{
		if (emailReceiptManager == null)
			emailReceiptManager = new EmailReceiptLogic();
		return emailReceiptManager;
	}

	private List getNoReceiptEmail()
	{
		int hour = Integer.parseInt(Globals.getProperty("receipthour"));
		return emailTaskLogic.getNoReceiptEmailTask(hour);
	}

	private void updateNoReceiptEmail(List listEmail)
	{
		emailTaskLogic.updateNoReceiptEmailTask(listEmail);
	}

	public synchronized void receiptEmail()
	{
		List list = getNoReceiptEmail();
		logger.info((new StringBuilder("需要改为回执的Email数量：")).append(list.size()).toString());
		if (list != null && list.size() != 0)
			updateNoReceiptEmail(list);
	}
}
