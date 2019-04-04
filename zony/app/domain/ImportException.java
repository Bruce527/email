// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ImportException.java

package com.zony.app.domain;


public class ImportException
{

	private Long id;
	private String policyNO;
	private String importDate;
	private String errorInfo;
	private String deleteDate;
	private String note;

	public ImportException()
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

	public String getPolicyNO()
	{
		return policyNO;
	}

	public void setPolicyNO(String policyNO)
	{
		this.policyNO = policyNO;
	}

	public String getImportDate()
	{
		return importDate;
	}

	public void setImportDate(String importDate)
	{
		this.importDate = importDate;
	}

	public String getErrorInfo()
	{
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo)
	{
		this.errorInfo = errorInfo;
	}

	public String getDeleteDate()
	{
		return deleteDate;
	}

	public void setDeleteDate(String deleteDate)
	{
		this.deleteDate = deleteDate;
	}

	public String getNote()
	{
		return note;
	}

	public void setNote(String note)
	{
		this.note = note;
	}
}
