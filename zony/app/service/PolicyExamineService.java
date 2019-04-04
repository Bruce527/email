// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PolicyExamineService.java

package com.zony.app.service;

import com.zony.app.domain.VpolicyInfo;
import com.zony.app.logic.*;
import com.zony.app.model.ResultModel;
import com.zony.common.util.FlexInvokeUtil;
import flex.messaging.io.amf.ASObject;
import java.io.PrintStream;
import java.util.*;
import org.apache.commons.lang.StringUtils;

public class PolicyExamineService
{

	private PolicyLogLogic policyLogLogic;
	private PolicyInfoQueryLogic policyInfoQueryLogic;
	private LoginLogic loginLogic;

	public PolicyExamineService()
	{
	}

	public ASObject searchPolicyInfo(String userCode, ASObject asObj, String startDate, String endDate, int pageIndex)
	{
		ResultModel resultModel = new ResultModel();
		VpolicyInfo vpolicyInfo;
		try
		{
			vpolicyInfo = (VpolicyInfo)FlexInvokeUtil.getFormAsObj(com/zony/app/domain/VpolicyInfo, asObj);
		}
		catch (Exception e)
		{
			resultModel.setError("查询发生异常,请重试或联系管理员");
			resultModel.setResult(2);
			return resultModel.getValue();
		}
		List list = policyInfoQueryLogic.getPolicyInfos(pageIndex, vpolicyInfo, startDate, endDate, loginLogic.createUserBaseRight(userCode));
		int totalCount = policyInfoQueryLogic.getPolicyInfoForOutXLS(vpolicyInfo, startDate, endDate, loginLogic.createUserBaseRight(userCode)).size();
		if (list == null || list.size() == 0)
			list = new ArrayList();
		Map dataMap = new HashMap();
		dataMap.put("list", list);
		dataMap.put("totalCout", Integer.valueOf(totalCount));
		resultModel.setData(dataMap);
		resultModel.setResult(1);
		return resultModel.getValue();
	}

	public ASObject searchPolicyInfoOutXLS(String userCode, ASObject asObj, String startDate, String endDate)
	{
		ResultModel resultModel = new ResultModel();
		VpolicyInfo vpolicyInfo;
		try
		{
			vpolicyInfo = (VpolicyInfo)FlexInvokeUtil.getFormAsObj(com/zony/app/domain/VpolicyInfo, asObj);
		}
		catch (Exception e)
		{
			resultModel.setError("查询结果导出发生异常,请重试或联系管理员");
			resultModel.setResult(2);
			return resultModel.getValue();
		}
		List vpolicyList = policyInfoQueryLogic.getPolicyInfoForOutXLS(vpolicyInfo, startDate, endDate, loginLogic.createUserBaseRight(userCode));
		String xlsPath = "";
		try
		{
			xlsPath = policyInfoQueryLogic.writePolicyinfoToXLS(vpolicyList);
		}
		catch (Exception e)
		{
			resultModel.setError("查询结果导出发生异常,请重试或联系管理员");
			resultModel.setResult(2);
			return resultModel.getValue();
		}
		if (StringUtils.isEmpty(xlsPath))
		{
			resultModel.setData("nodata");
			System.out.println("nodata");
			resultModel.setResult(1);
			return resultModel.getValue();
		} else
		{
			resultModel.setData(xlsPath);
			System.out.println(xlsPath);
			resultModel.setResult(1);
			return resultModel.getValue();
		}
	}
}
