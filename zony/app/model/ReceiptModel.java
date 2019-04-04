// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ReceiptModel.java

package com.zony.app.model;


public class ReceiptModel
{

	private String prpsno;
	private String pol_AcknowledgementDate;

	public ReceiptModel()
	{
	}

	public String getPrpsno()
	{
		return prpsno;
	}

	public void setPrpsno(String prpsno)
	{
		this.prpsno = prpsno;
	}

	public String getPol_AcknowledgementDate()
	{
		return pol_AcknowledgementDate;
	}

	public void setPol_AcknowledgementDate(String pol_AcknowledgementDate)
	{
		this.pol_AcknowledgementDate = pol_AcknowledgementDate;
	}
}
