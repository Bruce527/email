// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   QueryService.java

package com.zony.app.service;

import com.zony.app.domain.VpolicyLog;
import com.zony.app.logic.LoginLogic;
import com.zony.app.logic.PolicyInfoQueryLogic;
import com.zony.app.model.ResultModel;
import com.zony.common.util.FlexInvokeUtil;
import flex.messaging.io.amf.ASObject;
import java.io.PrintStream;
import java.util.*;
import org.apache.commons.lang.StringUtils;

public class QueryService
{

	private PolicyInfoQueryLogic policyInfoQueryLogic;
	LoginLogic loginLogic;

	public QueryService()
	{
	}

	public ASObject queryPolicyInfo(String policyQueryStr, String userCode, int pageIndex)
	{
		ResultModel resultModel = new ResultModel();
		List list = policyInfoQueryLogic.getPolicyInfoByWhereStr(policyQueryStr, userCode, pageIndex);
		int totalCount = policyInfoQueryLogic.getPolicyInfoByWhereStr(policyQueryStr, userCode, -1).size();
		if (list == null || list.size() == 0)
			list = new ArrayList();
		Map dataMap = new HashMap();
		dataMap.put("list", list);
		dataMap.put("totalCout", Integer.valueOf(totalCount));
		resultModel.setData(dataMap);
		resultModel.setResult(1);
		return resultModel.getValue();
	}

	public ASObject searchPolicyInfoOutXLS(String policyQueryStr, String userCode, int pageIndex)
	{
		ResultModel resultModel = new ResultModel();
		List policyInfoList = policyInfoQueryLogic.getPolicyInfoByWhereStr(policyQueryStr, userCode, pageIndex);
		String xlsPath = "";
		try
		{
			xlsPath = policyInfoQueryLogic.writePolicyInfoToXLS(policyInfoList);
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

	public ASObject searchPolicyLog(String userCode, ASObject asObj, String startDate, String endDate, int pageIndex)
	{
		ResultModel resultModel = new ResultModel();
		VpolicyLog vpolicyLog;
		try
		{
			vpolicyLog = (VpolicyLog)FlexInvokeUtil.getFormAsObj(com/zony/app/domain/VpolicyLog, asObj);
		}
		catch (Exception e)
		{
			resultModel.setError("查询发生异常,请重试或联系管理员");
			resultModel.setResult(2);
			return resultModel.getValue();
		}
		List list = policyInfoQueryLogic.getPolicyLogInfo(pageIndex, vpolicyLog, startDate, endDate, loginLogic.createUserBaseRight(userCode));
		int totalCount = policyInfoQueryLogic.getPolicyLogInfoForOutXLS(vpolicyLog, startDate, endDate, loginLogic.createUserBaseRight(userCode)).size();
		if (list == null || list.size() == 0)
			list = new ArrayList();
		Map dataMap = new HashMap();
		dataMap.put("list", list);
		dataMap.put("totalCout", Integer.valueOf(totalCount));
		resultModel.setData(dataMap);
		resultModel.setResult(1);
		return resultModel.getValue();
	}

	public ASObject searchPolicyLogOutXLS(String userCode, ASObject asObj, String startDate, String endDate)
	{
		ResultModel resultModel = new ResultModel();
		VpolicyLog vpolicyLog;
		try
		{
			vpolicyLog = (VpolicyLog)FlexInvokeUtil.getFormAsObj(com/zony/app/domain/VpolicyLog, asObj);
		}
		catch (Exception e)
		{
			resultModel.setError("查询结果导出发生异常,请重试或联系管理员");
			resultModel.setResult(2);
			return resultModel.getValue();
		}
		List policyLogList = policyInfoQueryLogic.getPolicyLogInfoForOutXLS(vpolicyLog, startDate, endDate, loginLogic.createUserBaseRight(userCode));
		String xlsPath = "";
		try
		{
			xlsPath = policyInfoQueryLogic.writePolicyLogToXLS(policyLogList);
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
