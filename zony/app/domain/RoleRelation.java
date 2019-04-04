// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RoleRelation.java

package com.zony.app.domain;


public class RoleRelation
{

	private Long id;
	private Long roleID;
	private Long userID;

	public RoleRelation()
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

	public Long getRoleID()
	{
		return roleID;
	}

	public void setRoleID(Long roleID)
	{
		this.roleID = roleID;
	}

	public Long getUserID()
	{
		return userID;
	}

	public void setUserID(Long userID)
	{
		this.userID = userID;
	}
}
