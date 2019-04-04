// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AppGlobals.java

package com.zony.app.constants;

import java.util.*;

public class AppGlobals
{

	private static List emailTaskIdList = new ArrayList();
	public static List vemailKeyList = new ArrayList();
	public static Map tempReceiptMap = new HashMap();

	public AppGlobals()
	{
	}

	public static List getTempReceipt(String key)
	{
		Map map = tempReceiptMap;
		JVM INSTR monitorenter ;
		List reList;
		reList = (List)tempReceiptMap.get(key);
		tempReceiptMap.put(key, null);
		return reList;
		map;
		JVM INSTR monitorexit ;
		throw ;
	}

	public static void setTempReceipt(String key, List value)
	{
		synchronized (tempReceiptMap)
		{
			tempReceiptMap.put(key, value);
		}
	}

	public static Long getEmailTask()
	{
		List list = emailTaskIdList;
		JVM INSTR monitorenter ;
		Long id;
		if (emailTaskIdList.size() <= 0)
			break MISSING_BLOCK_LABEL_44;
		id = (Long)emailTaskIdList.get(0);
		emailTaskIdList.remove(0);
		return id;
		Long.valueOf(0L);
		list;
		JVM INSTR monitorexit ;
		return;
		list;
		JVM INSTR monitorexit ;
		throw ;
	}

	public static void addEmailTask(List ids)
	{
		synchronized (emailTaskIdList)
		{
			Long id;
			for (Iterator iterator = ids.iterator(); iterator.hasNext(); addEmailTask(id))
				id = (Long)iterator.next();

		}
	}

	public static void addEmailTask(Long id)
	{
		synchronized (emailTaskIdList)
		{
			if (!emailTaskIdList.contains(id))
				emailTaskIdList.add(id);
		}
	}

}
