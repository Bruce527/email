// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LoginService.java

package com.zony.app.service;

import com.zony.app.logic.LoginLogic;
import com.zony.app.model.ResultModel;
import flex.messaging.io.amf.ASObject;
import flex.messaging.util.StringUtils;
import java.util.Map;

public class LoginService
{

	private LoginLogic loginLogic;

	public LoginService()
	{
	}

	public ASObject doLogin(String userCode, String password)
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		Map dataMap = loginLogic.doLogin(userCode, password);
		String errorInfo = (String)dataMap.get("errorInfo");
		if (StringUtils.isEmpty(errorInfo))
		{
			resultModel.setData(dataMap);
			resultModel.setResult(1);
		} else
		{
			resultModel.setError(errorInfo);
			resultModel.setResult(2);
		}
		return resultModel.getValue();
	}
}
