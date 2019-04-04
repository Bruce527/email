// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   VroleFun.java

package com.zony.app.domain;


public class VroleFun
{

	private Long id;
	private Long roleID;
	private Long funID;
	private String roleName;
	private String roleNote;
	private Long parentID;
	private String funName;
	private String funCode;
	private String funNote;

	public VroleFun()
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

	public String getFunName()
	{
		return funName;
	}

	public void setFunName(String funName)
	{
		this.funName = funName;
	}

	public String getRoleName()
	{
		return roleName;
	}

	public void setRoleName(String roleName)
	{
		this.roleName = roleName;
	}

	public Long getRoleID()
	{
		return roleID;
	}

	public void setRoleID(Long roleID)
	{
		this.roleID = roleID;
	}

	public Long getFunID()
	{
		return funID;
	}

	public void setFunID(Long funID)
	{
		this.funID = funID;
	}

	public Long getParentID()
	{
		return parentID;
	}

	public void setParentID(Long parentID)
	{
		this.parentID = parentID;
	}

	public String getFunCode()
	{
		return funCode;
	}

	public void setFunCode(String funCode)
	{
		this.funCode = funCode;
	}

	public String getRoleNote()
	{
		return roleNote;
	}

	public void setRoleNote(String roleNote)
	{
		this.roleNote = roleNote;
	}

	public String getFunNote()
	{
		return funNote;
	}

	public void setFunNote(String funNote)
	{
		this.funNote = funNote;
	}
}
