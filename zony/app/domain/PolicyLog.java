// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PolicyLog.java

package com.zony.app.domain;


public class PolicyLog
{

	private Long id;
	private Long policyID;
	private String logDate;
	private String logEvent;
	private String operator;
	private String errorInfo;
	private String note;

	public PolicyLog()
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

	public Long getPolicyID()
	{
		return policyID;
	}

	public void setPolicyID(Long policyID)
	{
		this.policyID = policyID;
	}

	public String getLogDate()
	{
		return logDate;
	}

	public void setLogDate(String logDate)
	{
		this.logDate = logDate;
	}

	public String getLogEvent()
	{
		return logEvent;
	}

	public void setLogEvent(String logEvent)
	{
		this.logEvent = logEvent;
	}

	public String getErrorInfo()
	{
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo)
	{
		this.errorInfo = errorInfo;
	}

	public String getNote()
	{
		return note;
	}

	public void setNote(String note)
	{
		this.note = note;
	}

	public String getOperator()
	{
		return operator;
	}

	public void setOperator(String operator)
	{
		this.operator = operator;
	}
}
