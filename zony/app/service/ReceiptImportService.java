// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ReceiptImportService.java

package com.zony.app.service;

import com.zony.app.constants.AppGlobals;
import com.zony.app.domain.PolicyInfo;
import com.zony.app.logic.PolicyLogLogic;
import com.zony.app.logic.PolicySendLogic;
import com.zony.app.model.ReceiptModel;
import com.zony.app.model.ResultModel;
import com.zony.common.util.FlexInvokeUtil;
import flex.messaging.io.ArrayCollection;
import flex.messaging.io.amf.ASObject;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class ReceiptImportService
{

	PolicyLogLogic policyLogLogic;
	PolicySendLogic policySendLogic;

	public ReceiptImportService()
	{
	}

	public ASObject showReceiptInfo(String userCode)
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		List reList = AppGlobals.getTempReceipt(userCode);
		if (reList != null && reList.size() > 0)
		{
			resultModel.setData(reList);
			resultModel.setResult(1);
		} else
		{
			resultModel.setError("�޷��ɹ������ļ������ʵ�����ļ���ʽ���������ݺ����ԣ�");
			resultModel.setResult(2);
		}
		return resultModel.getValue();
	}

	public ASObject importReceiptInfo(String userCode, ArrayCollection acReModel)
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		String errPrpsno = "";
		List reList = FlexInvokeUtil.getFormAsList(com/zony/app/model/ReceiptModel, acReModel);
		if (reList != null && reList.size() > 0)
		{
			for (Iterator iterator = reList.iterator(); iterator.hasNext();)
			{
				ReceiptModel receiptModel = (ReceiptModel)iterator.next();
				PolicyInfo policyInfo = policySendLogic.setSendStatusByPrpsno(receiptModel.getPrpsno(), receiptModel.getPol_AcknowledgementDate());
				if (policyInfo != null && policyInfo.getId().longValue() > 0L)
					policyLogLogic.saveLog(policyInfo.getId(), "16", userCode, (new StringBuilder("������³ɹ���ԭ��ǩ״̬Ϊ��")).append(policyInfo.getSignStatus()).append(",��ǩ���ڣ�").append(policyInfo.getPol_AcknowledgementDate()).append("�����º��ǩ״̬Ϊ��").append("5").append("����ǩ����Ϊ��").append(receiptModel.getPol_AcknowledgementDate()).toString());
				else
					errPrpsno = (new StringBuilder(String.valueOf(errPrpsno))).append(receiptModel.getPrpsno()).append("��").toString();
			}

		}
		if (StringUtils.isNotEmpty(errPrpsno))
			resultModel.setInfo((new StringBuilder("����ɹ�������Ͷ�����Ų����ڻ��������ڵ�ǰ�������ڣ�δ�ɹ����»�ǩ״̬������Ͷ�����ţ�")).append(errPrpsno).toString());
		else
			resultModel.setInfo("��ǩ���ݵ���ɹ���");
		resultModel.setResult(1);
		return resultModel.getValue();
	}
}
