// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TemplateConfigUtils.java

package com.zony.common.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class TemplateConfigUtils
{

	private static final ResourceBundle ERR_BUNDLE = ResourceBundle.getBundle("tamplateConfig");
	private static MessageFormat format;

	public TemplateConfigUtils()
	{
	}

	public static String getTemplatePath(String key)
	{
		return ERR_BUNDLE.getString(key);
	}

	static 
	{
		format = new MessageFormat("");
		format.setLocale(Locale.getDefault());
	}
}
