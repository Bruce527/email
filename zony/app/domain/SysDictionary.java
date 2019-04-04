// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SysDictionary.java

package com.zony.app.domain;


public class SysDictionary
{

	private Long id;
	private String typeCode;
	private String itemCode;
	private String itemName;
	private Long sortID;

	public SysDictionary()
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

	public String getTypeCode()
	{
		return typeCode;
	}

	public void setTypeCode(String typeCode)
	{
		this.typeCode = typeCode;
	}

	public String getItemCode()
	{
		return itemCode;
	}

	public void setItemCode(String itemCode)
	{
		this.itemCode = itemCode;
	}

	public String getItemName()
	{
		return itemName;
	}

	public void setItemName(String itemName)
	{
		this.itemName = itemName;
	}

	public Long getSortID()
	{
		return sortID;
	}

	public void setSortID(Long sortID)
	{
		this.sortID = sortID;
	}
}
