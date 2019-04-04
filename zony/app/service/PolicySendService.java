// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PolicySendService.java

package com.zony.app.service;

import com.zony.app.domain.PolicyInfo;
import com.zony.app.domain.PolicyLog;
import com.zony.app.logic.*;
import com.zony.app.model.ResultModel;
import com.zony.common.util.FlexInvokeUtil;
import flex.messaging.io.ArrayCollection;
import flex.messaging.io.amf.ASObject;
import java.io.PrintStream;
import java.util.*;
import org.apache.commons.lang.StringUtils;

public class PolicySendService
{

	PolicyLogLogic policyLogLogic;
	PolicySendLogic policySendLogic;
	EmailTaskLogic emailTaskLogic;
	LoginLogic loginLogic;

	public PolicySendService()
	{
	}

	public ASObject seachPolicyInfoByParams(String sendType, String userCode, ASObject asobject, String beginDate, String endDate)
		throws Throwable
	{
		ResultModel resultModel = new ResultModel();
		PolicyInfo policyInfo = (PolicyInfo)FlexInvokeUtil.getFormAsObj(com/zony/app/domain/PolicyInfo, asobject);
		List policyInfoList = null;
		String sendStatus = "";
		if (StringUtils.isNotEmpty(sendType) && sendType.equals("DFS"))
		{
			sendStatus = "'2'";
			policyInfoList = policySendLogic.seachPolicyInfoByParams(sendStatus, policyInfo, loginLogic.createUserBaseRight(userCode, true), null, null);
		}
		if (StringUtils.isNotEmpty(sendType) && sendType.equals("YFS"))
		{
			sendStatus = "'3','4'";
			policyInfoList = policySendLogic.seachPolicyInfoByParams(sendStatus, policyInfo, loginLogic.createUserBaseRight(userCode, true), null, null);
		}
		if (StringUtils.isNotEmpty(sendType) && sendType.equals("FSJG"))
		{
			sendStatus = "'5','-1','-2','-3','-4','-5','6'";
			policyInfoList = policySendLogic.seachPolicyInfoByParams(sendStatus, policyInfo, loginLogic.createUserBaseRight(userCode, true), beginDate, endDate);
		}
		resultModel.setData(policyInfoList);
		resultModel.setResult(1);
		return resultModel.getValue();
	}

	public ASObject seachPolicyInfoOutXLS(String userCode, ASObject asobject)
		throws Throwable
	{
		ResultModel resultModel = new ResultModel();
		PolicyInfo policyInfo = (PolicyInfo)FlexInvokeUtil.getFormAsObj(com/zony/app/domain/PolicyInfo, asobject);
		List policyInfoList = null;
		String sendStatus = "";
		sendStatus = "'5','-1','-2','-3','-4','-5','6'";
		policyInfoList = policySendLogic.seachALLPolicyInfoByParams(sendStatus, policyInfo, loginLogic.createUserBaseRight(userCode, true));
		String xlsPath = policySendLogic.writeVPolicyInfoToXLS(policyInfoList);
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

	public ASObject seachLAOutXLS(String userCode, String beginDate, String endDate)
		throws Throwable
	{
		ResultModel resultModel = new ResultModel();
		List pList = policySendLogic.getPolicyInfoSigned(loginLogic.createUserBaseRight(userCode, true), beginDate, endDate);
		if (pList == null || pList.size() < 1)
		{
			resultModel.setData("nodata");
			System.out.println("nodata");
			resultModel.setResult(1);
			return resultModel.getValue();
		} else
		{
			String xlsPath = policySendLogic.writeLAToXLS(pList);
			resultModel.setData(xlsPath);
			System.out.println(xlsPath);
			resultModel.setResult(1);
			return resultModel.getValue();
		}
	}

	public ASObject getErrorInfoByPId(String policyId)
	{
		ResultModel resultModel = new ResultModel();
		PolicyLog policyLog = policySendLogic.getPolicyLogByPId(policyId);
		if (policyLog != null && policyLog.getId().longValue() > 0L)
		{
			resultModel.setData(policyLog);
			resultModel.setResult(1);
		} else
		{
			resultModel.setError("未查询到保单错误日志信息");
			resultModel.setResult(2);
		}
		return resultModel.getValue();
	}

	public ASObject addSeedMailTask(String userCode, String taskType, String dateStr, ArrayCollection arrc, ArrayCollection arrc2)
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		List idList = FlexInvokeUtil.getFormAsStringList(arrc);
		List cntrnoList = FlexInvokeUtil.getFormAsStringList(arrc2);
		List newIDList = emailTaskLogic.checkAddSeedMailTaskForTM(idList);
		String info = "";
		if (newIDList.size() < idList.size())
			if (StringUtils.isNotEmpty(taskType) && taskType.equals("now"))
				info = "所选择发送的网销项目保单请使用LA功能重新发送。其他保单将继续做邮件发送。添加邮件立即发送任务成功！";
			else
				info = "所选择发送的网销项目保单请使用LA功能重新发送。其他保单将继续做邮件发送。";
		Map result = null;
		String key = null;
		if (newIDList.size() > 0)
		{
			result = emailTaskLogic.addSeedMailTask(userCode, taskType, dateStr, newIDList, cntrnoList, Long.valueOf(1L));
			key = (String)result.keySet().iterator().next();
		}
		if (StringUtils.isNotEmpty(info))
		{
			resultModel.setInfo(info);
			resultModel.setResult(1);
		} else
		if (result != null)
		{
			resultModel.setInfo((String)result.get(key));
			resultModel.setResult(Integer.parseInt(key));
		} else
		{
			resultModel.setInfo("没有保单需要发送。");
			resultModel.setResult(1);
		}
		return resultModel.getValue();
	}

	public ASObject cancelSendMail(String userCode, String type, ArrayCollection arrc)
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		List idList = FlexInvokeUtil.getFormAsStringList(arrc);
		Map result = emailTaskLogic.cancelSendMail(userCode, type, idList);
		String key = (String)result.keySet().iterator().next();
		resultModel.setInfo((String)result.get(key));
		resultModel.setResult(Integer.parseInt(key));
		return resultModel.getValue();
	}

	public ASObject getVPolicyLogInfo(Long policyId)
	{
		ResultModel resultModel = new ResultModel();
		List vpolicyLogList = policySendLogic.getVpolicyLogByPId(policyId);
		resultModel.setData(vpolicyLogList);
		resultModel.setResult(1);
		return resultModel.getValue();
	}

	public ASObject updateOutDataStatus(String userCode, ArrayCollection ids)
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		List idsList = FlexInvokeUtil.getFormAsLongList(ids);
		policySendLogic.updateOutDateStatus(idsList);
		policyLogLogic.saveLog(idsList, "17", userCode, "已过时保单执行发生，系统默认设置为发送失败-已取消");
		resultModel.setResult(1);
		return resultModel.getValue();
	}

	public ASObject saveNote(String userCode, Long policyId, String note)
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		policySendLogic.saveNote(policyId, note);
		policyLogLogic.saveLogWithNote(policyId, "29", userCode, note);
		resultModel.setResult(1);
		return resultModel.getValue();
	}
}
