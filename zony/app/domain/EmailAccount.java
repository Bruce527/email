// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EmailAccount.java

package com.zony.app.domain;


public class EmailAccount
{

	private Long id;
	private String groupCode;
	private String userName;
	private String password;
	private Long sendCount;
	private String isSending;
	private String isReceiving;

	public EmailAccount()
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

	public String getGroupCode()
	{
		return groupCode;
	}

	public void setGroupCode(String groupCode)
	{
		this.groupCode = groupCode;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public Long getSendCount()
	{
		return sendCount;
	}

	public void setSendCount(Long sendCount)
	{
		this.sendCount = sendCount;
	}

	public String getIsSending()
	{
		return isSending;
	}

	public void setIsSending(String isSending)
	{
		this.isSending = isSending;
	}

	public String getIsReceiving()
	{
		return isReceiving;
	}

	public void setIsReceiving(String isReceiving)
	{
		this.isReceiving = isReceiving;
	}
}
