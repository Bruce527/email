// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MD5Util.java

package com.zony.common.util;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import sun.misc.BASE64Encoder;

public class MD5Util
{

	public MD5Util()
	{
	}

	public static String EncoderByMd5(String str)
		throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		BASE64Encoder base64en = new BASE64Encoder();
		String newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
		return newstr;
	}

	public static boolean checkpassword(String newpasswd, String oldpasswd)
		throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		return EncoderByMd5(newpasswd).equals(oldpasswd);
	}

	public static void main(String args[])
	{
		try
		{
			System.out.println(EncoderByMd5("PASSWORD"));
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
	}
}
