// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Userservice.java

package com.zony.app.service;

import com.zony.app.domain.User;
import com.zony.app.logic.UserLogic;
import com.zony.app.model.ResultModel;
import flex.messaging.io.amf.ASObject;

public class Userservice
{

	private UserLogic userLogic;

	public Userservice()
	{
	}

	public ASObject getUserInfo()
	{
		ResultModel resultModel = new ResultModel();
		User user = new User();
		user.setName("TEST");
		resultModel.setData(user);
		resultModel.setResult(1);
		return resultModel.getValue();
	}
}
