// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PolicyCheckService.java

package com.zony.app.service;

import com.zony.app.domain.PolicyInfo;
import com.zony.app.logic.PolicyCheckLogic;
import com.zony.app.logic.PolicyInfoQueryLogic;
import com.zony.app.model.ResultModel;
import com.zony.common.util.FlexInvokeUtil;
import flex.messaging.io.ArrayCollection;
import flex.messaging.io.amf.ASObject;
import java.io.PrintStream;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class PolicyCheckService
{

	private PolicyCheckLogic policyCheckLogic;
	private PolicyInfoQueryLogic policyInfoQueryLogic;

	public PolicyCheckService()
	{
	}

	public ASObject doSearch(ASObject policyASObj, String queryStr, String userCode)
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		PolicyInfo policyInfo = (PolicyInfo)FlexInvokeUtil.getFormAsObj(com/zony/app/domain/PolicyInfo, policyASObj);
		List policyList = policyCheckLogic.doSearch(policyInfo, queryStr, userCode);
		resultModel.setData(policyList);
		resultModel.setResult(1);
		return resultModel.getValue();
	}

	public ASObject doForceUnLock(ArrayCollection selectedIdListASObj, ArrayCollection allIdListASObj)
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		List selectedIdList = FlexInvokeUtil.getFormAsLongList(selectedIdListASObj);
		List allIdList = FlexInvokeUtil.getFormAsLongList(allIdListASObj);
		List policyList = policyCheckLogic.doForceUnLock(selectedIdList, allIdList);
		resultModel.setData(policyList);
		resultModel.setInfo("操作完成！");
		resultModel.setResult(1);
		return resultModel.getValue();
	}

	public ASObject startCheck(ArrayCollection allIdListASObj, ArrayCollection selectedIdListASObj, String userCode)
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		List allIdList = FlexInvokeUtil.getFormAsLongList(allIdListASObj);
		List selectedIdList = FlexInvokeUtil.getFormAsLongList(selectedIdListASObj);
		List policyList = policyCheckLogic.startCheck(allIdList, selectedIdList, userCode);
		resultModel.setData(policyList);
		resultModel.setInfo("操作完成！");
		resultModel.setResult(1);
		return resultModel.getValue();
	}

	public ASObject viewLocked(String userCode)
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		List policyList = policyCheckLogic.viewLocked(userCode);
		resultModel.setData(policyList);
		resultModel.setResult(1);
		return resultModel.getValue();
	}

	public ASObject doNormalUnLock(ArrayCollection selectedIdListASObj, String userCode)
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		List selectedIdList = FlexInvokeUtil.getFormAsLongList(selectedIdListASObj);
		List policyList = policyCheckLogic.doNormalUnLock(selectedIdList, userCode);
		resultModel.setData(policyList);
		resultModel.setInfo("操作完成！");
		resultModel.setResult(1);
		return resultModel.getValue();
	}

	public ASObject cancelCheck(String userCode)
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		policyCheckLogic.cancelCheck(userCode);
		resultModel.setInfo("操作完成！");
		resultModel.setResult(1);
		return resultModel.getValue();
	}

	public ASObject setCheckStatusBatch(String userCode, String checkStatus, ArrayCollection selectedIdListASObj)
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		List selectedIdList = FlexInvokeUtil.getFormAsLongList(selectedIdListASObj);
		List policyList = policyCheckLogic.setCheckStatusBatch(userCode, checkStatus, selectedIdList);
		resultModel.setData(policyList);
		resultModel.setInfo("操作完成！");
		resultModel.setResult(1);
		return resultModel.getValue();
	}

	public ASObject doFinishCheck(String userCode, String checkStatus, String note)
	{
		ResultModel resultModel = new ResultModel();
		policyCheckLogic.doFinishCheck(userCode, checkStatus, note);
		resultModel.setInfo("操作完成！");
		resultModel.setResult(1);
		return resultModel.getValue();
	}

	public ASObject doFinishCheckByChoose(String userCode, ArrayCollection idListASObj)
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		List idList = FlexInvokeUtil.getFormAsLongList(idListASObj);
		policyCheckLogic.doFinishCheckByChoose(userCode, idList);
		resultModel.setInfo("操作完成！");
		resultModel.setResult(1);
		return resultModel.getValue();
	}

	public ASObject doCheckOne(String userCode, Long policyID, boolean checkFlag, String note)
	{
		ResultModel resultModel = new ResultModel();
		com.zony.app.domain.VpolicyInfo vpolicyInfo = policyCheckLogic.doCheckOne(userCode, policyID, checkFlag, note);
		resultModel.setData(vpolicyInfo);
		resultModel.setResult(1);
		return resultModel.getValue();
	}

	public ASObject checkStatusOutXLS(ArrayCollection idListASObj)
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		List idList = FlexInvokeUtil.getFormAsLongList(idListASObj);
		List vpolicyList = policyCheckLogic.doSearchCheckStatus(idList);
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
