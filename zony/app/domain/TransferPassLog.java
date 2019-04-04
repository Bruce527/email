// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TransferPassLog.java

package com.zony.app.domain;


public class TransferPassLog
{

	private int id;
	private String cntrno;
	private String transferTime;
	private String transferStatus;
	private int transferCount;

	public TransferPassLog()
	{
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getCntrno()
	{
		return cntrno;
	}

	public void setCntrno(String cntrno)
	{
		this.cntrno = cntrno;
	}

	public String getTransferTime()
	{
		return transferTime;
	}

	public void setTransferTime(String transferTime)
	{
		this.transferTime = transferTime;
	}

	public String getTransferStatus()
	{
		return transferStatus;
	}

	public void setTransferStatus(String transferStatus)
	{
		this.transferStatus = transferStatus;
	}

	public int getTransferCount()
	{
		return transferCount;
	}

	public void setTransferCount(int transferCount)
	{
		this.transferCount = transferCount;
	}
}
