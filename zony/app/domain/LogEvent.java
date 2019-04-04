// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LogEvent.java

package com.zony.app.domain;


public class LogEvent
{

	private Long id;
	private String logType;
	private String logEventCode;
	private String logEventName;
	private Integer sortID;

	public LogEvent()
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

	public String getLogType()
	{
		return logType;
	}

	public void setLogType(String logType)
	{
		this.logType = logType;
	}

	public String getLogEventCode()
	{
		return logEventCode;
	}

	public void setLogEventCode(String logEventCode)
	{
		this.logEventCode = logEventCode;
	}

	public String getLogEventName()
	{
		return logEventName;
	}

	public void setLogEventName(String logEventName)
	{
		this.logEventName = logEventName;
	}

	public Integer getSortID()
	{
		return sortID;
	}

	public void setSortID(Integer sortID)
	{
		this.sortID = sortID;
	}
}
