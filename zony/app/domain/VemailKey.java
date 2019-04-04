// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   VemailKey.java

package com.zony.app.domain;


public class VemailKey
{

	private Long id;
	private Long serverID;
	private String keyStr;
	private String sendStatus;
	private String keyNote;
	private String postmaster;
	private String serverNote;

	public VemailKey()
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

	public Long getServerID()
	{
		return serverID;
	}

	public void setServerID(Long serverID)
	{
		this.serverID = serverID;
	}

	public String getKeyStr()
	{
		return keyStr;
	}

	public void setKeyStr(String keyStr)
	{
		this.keyStr = keyStr;
	}

	public String getSendStatus()
	{
		return sendStatus;
	}

	public void setSendStatus(String sendStatus)
	{
		this.sendStatus = sendStatus;
	}

	public String getKeyNote()
	{
		return keyNote;
	}

	public void setKeyNote(String keyNote)
	{
		this.keyNote = keyNote;
	}

	public String getPostmaster()
	{
		return postmaster;
	}

	public void setPostmaster(String postmaster)
	{
		this.postmaster = postmaster;
	}

	public String getServerNote()
	{
		return serverNote;
	}

	public void setServerNote(String serverNote)
	{
		this.serverNote = serverNote;
	}
}
