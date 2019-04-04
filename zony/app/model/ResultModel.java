// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ResultModel.java

package com.zony.app.model;

import flex.messaging.io.amf.ASObject;

public class ResultModel
{

	private final ASObject aso = new ASObject();
	public static final int RESULT_SUCCESS = 1;
	public static final int RESULT_FAIL = 2;

	public ResultModel()
	{
		aso.put("data", "");
	}

	public void setResult(int result)
	{
		aso.put("result", Integer.valueOf(result));
	}

	public void setData(Object data)
	{
		aso.put("data", data);
	}

	public void setError(String errorInfo)
	{
		aso.put("errorInfo", errorInfo);
	}

	public void setInfo(String info)
	{
		aso.put("info", info);
	}

	public ASObject getValue()
	{
		return aso;
	}
}
