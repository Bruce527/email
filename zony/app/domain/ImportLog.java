// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ImportLog.java

package com.zony.app.domain;


public class ImportLog
{

	private Long id;
	private String folderName;
	private String errorInfo;
	private String logDate;

	public ImportLog()
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

	public String getFolderName()
	{
		return folderName;
	}

	public void setFolderName(String folderName)
	{
		this.folderName = folderName;
	}

	public String getErrorInfo()
	{
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo)
	{
		this.errorInfo = errorInfo;
	}

	public String getLogDate()
	{
		return logDate;
	}

	public void setLogDate(String logDate)
	{
		this.logDate = logDate;
	}
}
