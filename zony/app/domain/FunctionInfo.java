// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FunctionInfo.java

package com.zony.app.domain;


public class FunctionInfo
{

	private Long id;
	private Long parentID;
	private String funName;
	private String funCode;
	private String funNote;

	public FunctionInfo()
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

	public String getFunName()
	{
		return funName;
	}

	public void setFunName(String funName)
	{
		this.funName = funName;
	}

	public String getFunCode()
	{
		return funCode;
	}

	public void setFunCode(String funCode)
	{
		this.funCode = funCode;
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
