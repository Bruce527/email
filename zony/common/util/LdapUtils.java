// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LdapUtils.java

package com.zony.common.util;

import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPException;
import com.zony.common.config.ZonyConfig;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class LdapUtils
{

	public LdapUtils()
	{
	}

	public static LDAPConnection getConnect()
		throws LDAPException, UnsupportedEncodingException
	{
		LDAPConnection connection = null;
		connection = new LDAPConnection();
		String url = ZonyConfig.getLdapurl();
		int port = ZonyConfig.getLdapport() != null ? Integer.parseInt(ZonyConfig.getLdapport()) : 389;
		connection.connect(url, port);
		connection.bind(3, ZonyConfig.getLdaploginName(), ZonyConfig.getLdaploginPwd().getBytes("UTF-8"));
		return connection;
	}

	public static boolean validateUser(String dn, String password)
	{
		try
		{
			System.out.println(dn);
			LDAPConnection connection = null;
			connection = new LDAPConnection();
			String url = ZonyConfig.getLdapurl();
			int port = ZonyConfig.getLdapport() != null ? Integer.parseInt(ZonyConfig.getLdapport()) : 389;
			connection.connect(url, port);
			connection.bind(3, dn, password);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	public static void disConnect(LDAPConnection connect)
		throws LDAPException
	{
		if (connect != null)
			connect.disconnect();
	}
}
