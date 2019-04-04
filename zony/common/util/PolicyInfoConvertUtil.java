// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PolicyInfoConvertUtil.java

package com.zony.common.util;

import com.zony.app.domain.EmailTask;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PolicyInfoConvertUtil
{

	private static List Email_LIST;
	public static ConcurrentLinkedQueue queue;

	public PolicyInfoConvertUtil()
	{
	}

	public static synchronized void buildQueue()
	{
		Email_LIST = new ArrayList();
	}

	public static synchronized EmailTask getNextConvertItem(List urgencyList)
		throws Exception
	{
		if (queue != null && queue.size() > 0)
		{
			EmailTask item = (EmailTask)queue.poll();
			return item;
		} else
		{
			return null;
		}
	}

	public static void getDocToConvertPDF()
		throws Exception
	{
	}

	static 
	{
		buildQueue();
	}
}
