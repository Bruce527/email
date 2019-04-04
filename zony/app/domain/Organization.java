// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Organization.java

package com.zony.app.domain;


public class Organization
{

	private Long id;
	private Long parentID;
	private String orgName;
	private String orgCode;
	private String orgCodePath;
	private String secondOrgCode;
	private String sortID;

	public Organization()
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

	public Long getParentID()
	{
		return parentID;
	}

	public void setParentID(Long parentID)
	{
		this.parentID = parentID;
	}

	public String getOrgName()
	{
		return orgName;
	}

	public void setOrgName(String orgName)
	{
		this.orgName = orgName;
	}

	public String getOrgCode()
	{
		return orgCode;
	}

	public void setOrgCode(String orgCode)
	{
		this.orgCode = orgCode;
	}

	public String getOrgCodePath()
	{
		return orgCodePath;
	}

	public void setOrgCodePath(String orgCodePath)
	{
		this.orgCodePath = orgCodePath;
	}

	public String getSecondOrgCode()
	{
		return secondOrgCode;
	}

	public void setSecondOrgCode(String secondOrgCode)
	{
		this.secondOrgCode = secondOrgCode;
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
