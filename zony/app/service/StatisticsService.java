// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   StatisticsService.java

package com.zony.app.service;

import com.zony.app.logic.LoginLogic;
import com.zony.app.logic.StatisticLogic;
import com.zony.app.model.ResultModel;
import flex.messaging.io.amf.ASObject;
import java.util.List;

public class StatisticsService
{

	StatisticLogic statisticLogic;
	LoginLogic loginLogic;

	public StatisticsService()
	{
	}

	public ASObject statisticsByArea(String userCode, String beginDate, String endDate, String policyType)
	{
		ResultModel resultModel = new ResultModel();
		List statisticsMList = statisticLogic.getStatisticByArea(beginDate, endDate, policyType, loginLogic.createUserBaseRight(userCode));
		if (statisticsMList != null && statisticsMList.size() > 0)
		{
			resultModel.setData(statisticsMList);
			resultModel.setResult(1);
		} else
		{
			resultModel.setInfo("无统计结果数据");
			resultModel.setResult(1);
		}
		return resultModel.getValue();
	}

	public ASObject statisticsByProject(String userCode, String beginDate, String endDate, String policyType)
	{
		ResultModel resultModel = new ResultModel();
		List statisticsMList = statisticLogic.getStatisticByProject(beginDate, endDate, policyType, loginLogic.createUserBaseRight(userCode));
		if (statisticsMList != null && statisticsMList.size() > 0)
		{
			resultModel.setData(statisticsMList);
			resultModel.setResult(1);
		} else
		{
			resultModel.setInfo("无统计结果数据");
			resultModel.setResult(1);
		}
		return resultModel.getValue();
	}

	public ASObject statisticsByProduct(String userCode, String beginDate, String endDate, String policyType)
	{
		ResultModel resultModel = new ResultModel();
		List statisticsMList = statisticLogic.getStatisticByProduct(beginDate, endDate, policyType, loginLogic.createUserBaseRight(userCode));
		if (statisticsMList != null && statisticsMList.size() > 0)
		{
			resultModel.setData(statisticsMList);
			resultModel.setResult(1);
		} else
		{
			resultModel.setInfo("无统计结果数据");
			resultModel.setResult(1);
		}
		return resultModel.getValue();
	}

	public ASObject statisticsByReceipt(String userCode, String beginDate, String endDate, String policyType)
	{
		ResultModel resultModel = new ResultModel();
		List statisticsMList = statisticLogic.getStatisticByReceipt(beginDate, endDate, policyType, loginLogic.createUserBaseRight(userCode));
		if (statisticsMList != null && statisticsMList.size() > 0)
		{
			resultModel.setData(statisticsMList);
			resultModel.setResult(1);
		} else
		{
			resultModel.setInfo("无统计结果数据");
			resultModel.setResult(1);
		}
		return resultModel.getValue();
	}
}
