// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ReflectionUtils.java

package com.zony.common.util;

import java.lang.reflect.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;

public class ReflectionUtils
{

	private static Logger logger = Logger.getLogger(com/zony/common/util/ReflectionUtils);

	public ReflectionUtils()
	{
	}

	public static Object invokeGetterMethod(Object obj, String propertyName)
	{
		String getterMethodName = (new StringBuilder("get")).append(StringUtils.capitalize(propertyName)).toString();
		return invokeMethod(obj, getterMethodName, new Class[0], new Object[0]);
	}

	public static void invokeSetterMethod(Object obj, String propertyName, Object value)
	{
		invokeSetterMethod(obj, propertyName, value, null);
	}

	public static void invokeSetterMethod(Object obj, String propertyName, Object value, Class propertyType)
	{
		Class type = propertyType == null ? value.getClass() : propertyType;
		String setterMethodName = (new StringBuilder("set")).append(StringUtils.capitalize(propertyName)).toString();
		invokeMethod(obj, setterMethodName, new Class[] {
			type
		}, new Object[] {
			value
		});
	}

	public static Object getFieldValue(Object obj, String fieldName)
	{
		Field field = getAccessibleField(obj, fieldName);
		if (field == null)
			throw new IllegalArgumentException((new StringBuilder("Could not find field [")).append(fieldName).append("] on target [").append(obj).append("]").toString());
		Object result = null;
		try
		{
			result = field.get(obj);
		}
		catch (IllegalAccessException e)
		{
			logger.error("不可能抛出的异常", e);
		}
		return result;
	}

	public static void setFieldValue(Object obj, String fieldName, Object value)
	{
		Field field = getAccessibleField(obj, fieldName);
		if (field == null)
			throw new IllegalArgumentException((new StringBuilder("Could not find field [")).append(fieldName).append("] on target [").append(obj).append("]").toString());
		try
		{
			field.set(obj, value);
		}
		catch (IllegalAccessException e)
		{
			logger.error("不可能抛出的异常:{}", e);
		}
	}

	public static Field getAccessibleField(Object obj, String fieldName)
	{
		Class superClass;
		Assert.notNull(obj, "object不能为空");
		Assert.hasText(fieldName, "fieldName");
		superClass = obj.getClass();
		  goto _L1
_L3:
		Field field;
		field = superClass.getDeclaredField(fieldName);
		field.setAccessible(true);
		return field;
		NoSuchFieldException nosuchfieldexception;
		nosuchfieldexception;
		superClass = superClass.getSuperclass();
_L1:
		if (superClass != java/lang/Object) goto _L3; else goto _L2
_L2:
		return null;
	}

	public static Object invokeMethod(Object obj, String methodName, Class parameterTypes[], Object args[])
	{
		Method method;
		method = getAccessibleMethod(obj, methodName, parameterTypes);
		if (method == null)
			throw new IllegalArgumentException((new StringBuilder("Could not find method [")).append(methodName).append("] on target [").append(obj).append("]").toString());
		return method.invoke(obj, args);
		Exception e;
		e;
		throw convertReflectionExceptionToUnchecked(e);
	}

	public static transient Method getAccessibleMethod(Object obj, String methodName, Class parameterTypes[])
	{
		Class superClass;
		Assert.notNull(obj, "object不能为空");
		superClass = obj.getClass();
		  goto _L1
_L3:
		Method method;
		method = superClass.getDeclaredMethod(methodName, parameterTypes);
		method.setAccessible(true);
		return method;
		NoSuchMethodException nosuchmethodexception;
		nosuchmethodexception;
		superClass = superClass.getSuperclass();
_L1:
		if (superClass != java/lang/Object) goto _L3; else goto _L2
_L2:
		return null;
	}

	public static Class getSuperClassGenricType(Class clazz)
	{
		return getSuperClassGenricType(clazz, 0);
	}

	public static Class getSuperClassGenricType(Class clazz, int index)
	{
		Type genType = clazz.getGenericSuperclass();
		if (!(genType instanceof ParameterizedType))
		{
			logger.warn((new StringBuilder(String.valueOf(clazz.getSimpleName()))).append("'s superclass not ParameterizedType").toString());
			return java/lang/Object;
		}
		Type params[] = ((ParameterizedType)genType).getActualTypeArguments();
		if (index >= params.length || index < 0)
		{
			logger.warn((new StringBuilder("Index: ")).append(index).append(", Size of ").append(clazz.getSimpleName()).append("'s Parameterized Type: ").append(params.length).toString());
			return java/lang/Object;
		}
		if (!(params[index] instanceof Class))
		{
			logger.warn((new StringBuilder(String.valueOf(clazz.getSimpleName()))).append(" not set the actual class on superclass generic parameter").toString());
			return java/lang/Object;
		} else
		{
			return (Class)params[index];
		}
	}

	public static RuntimeException convertReflectionExceptionToUnchecked(Exception e)
	{
		if ((e instanceof IllegalAccessException) || (e instanceof IllegalArgumentException) || (e instanceof NoSuchMethodException))
			return new IllegalArgumentException("Reflection Exception.", e);
		if (e instanceof InvocationTargetException)
			return new RuntimeException("Reflection Exception.", ((InvocationTargetException)e).getTargetException());
		if (e instanceof RuntimeException)
			return (RuntimeException)e;
		else
			return new RuntimeException("Unexpected Checked Exception.", e);
	}

}
