// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ExceptionUtils.java

package com.zony.common.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ExceptionUtils
{

	public ExceptionUtils()
	{
	}

	public static String getErrorTraceMessage(Exception ex)
	{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PrintStream pout = new PrintStream(out);
		ex.printStackTrace(pout);
		String ret = new String(out.toByteArray());
		pout.close();
		try
		{
			out.close();
		}
		catch (Exception e)
		{
			System.out.println("ϵͳ�����෽������getErrorTraceMessage���� ��ȡ������ϸ��Ϣ");
		}
		return ret;
	}

	public static String getErrorTraceMessage(Throwable ex)
	{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PrintStream pout = new PrintStream(out);
		ex.printStackTrace(pout);
		String ret = new String(out.toByteArray());
		pout.close();
		try
		{
			out.close();
		}
		catch (Exception e)
		{
			System.out.println("ϵͳ�����෽������getErrorTraceMessage���� ��ȡ������ϸ��Ϣ");
		}
		return ret;
	}
}
