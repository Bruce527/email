// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   QueryTypeUtil.java

package com.zony.common.util;

import com.zony.app.domain.PolicyInfo;
import edu.emory.mathcs.backport.java.util.Arrays;
import java.util.*;

public class QueryTypeUtil
{

	private static Map defineMap;

	public QueryTypeUtil()
	{
	}

	public static List get(String className)
	{
		return (List)defineMap.get(className);
	}

	static 
	{
		defineMap = new HashMap();
		String policyInfoDef[] = {
			"coycd", "brcd", "chn", "letterType", "mtsNoAcknowledgementprojectName", "packageName", "sendStatus", "checkStatus", "signStatus", "policyType"
		};
		defineMap.put(com/zony/app/domain/PolicyInfo.getSimpleName(), Arrays.asList(policyInfoDef));
	}
}
