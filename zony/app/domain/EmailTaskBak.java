// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EmailTaskBak.java

package com.zony.app.domain;


public class EmailTaskBak
{

	private Long id;
	private String policyID;
	private String taskType;
	private String sendDate;
	private String sendAccount;
	private String taskStatus;

	public EmailTaskBak()
	{
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getPolicyID()
	{
		return policyID;
	}

	public void setPolicyID(String policyID)
	{
		this.policyID = policyID;
	}

	public String getTaskType()
	{
		return taskType;
	}

	public void setTaskType(String taskType)
	{
		this.taskType = taskType;
	}

	public String getSendDate()
	{
		return sendDate;
	}

	public void setSendDate(String sendDate)
	{
		this.sendDate = sendDate;
	}

	public String getTaskStatus()
	{
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus)
	{
		this.taskStatus = taskStatus;
	}

	public String getSendAccount()
	{
		return sendAccount;
	}

	public void setSendAccount(String sendAccount)
	{
		this.sendAccount = sendAccount;
	}
}
