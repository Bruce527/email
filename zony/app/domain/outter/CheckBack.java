// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CheckBack.java

package com.zony.app.domain.outter;


public class CheckBack
{

	private Long id;
	private String emailCode;
	private String result;
	private String checkBackTime;
	private String isNew;

	public CheckBack()
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

	public String getEmailCode()
	{
		return emailCode;
	}

	public void setEmailCode(String emailCode)
	{
		this.emailCode = emailCode;
	}

	public String getResult()
	{
		return result;
	}

	public void setResult(String result)
	{
		this.result = result;
	}

	public String getCheckBackTime()
	{
		return checkBackTime;
	}

	public void setCheckBackTime(String checkBackTime)
	{
		this.checkBackTime = checkBackTime;
	}

	public String getIsNew()
	{
		return isNew;
	}

	public void setIsNew(String isNew)
	{
		this.isNew = isNew;
	}
}
