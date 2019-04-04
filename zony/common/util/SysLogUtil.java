// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SysLogUtil.java

package com.zony.common.util;

import org.apache.log4j.Logger;

public class SysLogUtil
{

	private static final String DUBUG_FILE = "debuglog";
	private static final String ERROR_FILE = "errorlog";

	public SysLogUtil()
	{
	}

	public static Logger getErrorLog()
	{
		return Logger.getLogger("errorlog");
	}

	public static Logger getDebugLog()
	{
		return Logger.getLogger("debuglog");
	}
}
