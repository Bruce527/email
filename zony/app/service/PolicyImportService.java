// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PolicyImportService.java

package com.zony.app.service;

import com.zony.app.domain.PolicyInfo;
import com.zony.app.logic.PolicyImportLogic;

public class PolicyImportService
{

	PolicyImportLogic policyImportLogic;

	public PolicyImportService()
	{
	}

	public Long savePolicyNotice(PolicyInfo policyNotice)
	{
		return Long.valueOf(policyImportLogic.savePolicyInfo(policyNotice));
	}

	public void StartPolicyImportThread()
	{
		PolicyImportLogic.StartPolicyImportThread();
	}
}
