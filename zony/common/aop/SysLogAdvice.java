// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SysLogAdvice.java

package com.zony.common.aop;

import com.zony.app.model.ResultModel;
import com.zony.common.util.ExceptionUtils;
import java.lang.reflect.Method;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;

public class SysLogAdvice
	implements MethodInterceptor
{

	public Logger log;

	public SysLogAdvice()
	{
		log = Logger.getLogger(com/zony/common/aop/SysLogAdvice);
	}

	public Object invoke(MethodInvocation invocation)
		throws Throwable
	{
		Object ret = null;
		try
		{
			ret = invocation.proceed();
		}
		catch (Exception e)
		{
			ResultModel resultModel = new ResultModel();
			Method method = invocation.getMethod();
			String methodName = (new StringBuilder(String.valueOf(method.getDeclaringClass().getSimpleName()))).append(".").append(method.getName()).toString();
			log.error((new StringBuilder("Error: ")).append(methodName).append(",ErrorInfo:").append(ExceptionUtils.getErrorTraceMessage(e)).toString());
			e.printStackTrace();
			resultModel.setResult(2);
			resultModel.setError(e.getMessage());
			return resultModel.getValue();
		}
		return ret;
	}
}
