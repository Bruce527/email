// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GroupInfo.java

package com.zony.app.domain;


public class GroupInfo
{

	private Long id;
	private String groupName;
	private String groupCode;
	private String isChannel;
	private String sortID;

	public GroupInfo()
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

	public String getGroupName()
	{
		return groupName;
	}

	public void setGroupName(String groupName)
	{
		this.groupName = groupName;
	}

	public String getGroupCode()
	{
		return groupCode;
	}

	public void setGroupCode(String groupCode)
	{
		this.groupCode = groupCode;
	}

	public String getIsChannel()
	{
		return isChannel;
	}

	public void setIsChannel(String isChannel)
	{
		this.isChannel = isChannel;
	}

	public String getSortID()
	{
		return sortID;
	}

	public void setSortID(String sortID)
	{
		this.sortID = sortID;
	}
}
