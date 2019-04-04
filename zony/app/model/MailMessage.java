// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MailMessage.java

package com.zony.app.model;

import java.util.Date;

public class MailMessage
{

	private String sendFrom;
	private String sendTo;
	private String subject;
	private String context;
	private String templateName;
	private Date sendDate;
	private String attachmentFilePath[];
	private String lineFilePath[];

	public MailMessage()
	{
	}

	public String[] getAttachmentFilePath()
	{
		return attachmentFilePath;
	}

	public void setAttachmentFilePath(String attachmentFilePath[])
	{
		this.attachmentFilePath = attachmentFilePath;
	}

	public String[] getLineFilePath()
	{
		return lineFilePath;
	}

	public void setLineFilePath(String lineFilePath[])
	{
		this.lineFilePath = lineFilePath;
	}

	public String getSendFrom()
	{
		return sendFrom;
	}

	public void setSendFrom(String sendFrom)
	{
		this.sendFrom = sendFrom;
	}

	public String getSendTo()
	{
		return sendTo;
	}

	public void setSendTo(String sendTo)
	{
		this.sendTo = sendTo;
	}

	public String getSubject()
	{
		return subject;
	}

	public void setSubject(String subject)
	{
		this.subject = subject;
	}

	public String getContext()
	{
		return context;
	}

	public void setContext(String context)
	{
		this.context = context;
	}

	public String getTemplateName()
	{
		return templateName;
	}

	public void setTemplateName(String templateName)
	{
		this.templateName = templateName;
	}

	public Date getSendDate()
	{
		return sendDate;
	}

	public void setSendDate(Date sendDate)
	{
		this.sendDate = sendDate;
	}
}
