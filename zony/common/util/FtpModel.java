// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FtpModel.java

package com.zony.common.util;


public class FtpModel
{

	private String ftpId;
	private String username;
	private String password;
	private String url;
	private int port;
	private String remoteDir;

	public String getFtpId()
	{
		return ftpId;
	}

	public void setFtpId(String ftpId)
	{
		this.ftpId = ftpId;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public int getPort()
	{
		return port;
	}

	public void setPort(int port)
	{
		this.port = port;
	}

	public String getRemoteDir()
	{
		return remoteDir;
	}

	public void setRemoteDir(String remoteDir)
	{
		this.remoteDir = remoteDir;
	}

	public FtpModel()
	{
	}

	public FtpModel(String username, String password, String url, int port, String remoteDir)
	{
		this.username = username;
		this.password = password;
		this.url = url;
		this.port = port;
		this.remoteDir = remoteDir;
	}
}
