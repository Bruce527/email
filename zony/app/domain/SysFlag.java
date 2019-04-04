// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SysFlag.java

package com.zony.app.domain;


public class SysFlag
{

	private Long id;
	private String flagName;
	private String flagValue;

	public SysFlag()
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

	public String getFlagName()
	{
		return flagName;
	}

	public void setFlagName(String flagName)
	{
		this.flagName = flagName;
	}

	public String getFlagValue()
	{
		return flagValue;
	}

	public void setFlagValue(String flagValue)
	{
		this.flagValue = flagValue;
	}
}
