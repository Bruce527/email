// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FieldUtil.java

package com.zony.common.util;

import java.io.PrintStream;
import java.lang.reflect.Field;
import org.apache.commons.beanutils.PropertyUtils;

public class FieldUtil
{

	public FieldUtil()
	{
	}

	public static String transOuterField(String outerField)
	{
		String innerField = "";
		char charArray[] = outerField.toCharArray();
		for (int i = 0; i < charArray.length; i++)
			if (charArray[i] == '_' && i != charArray.length - 1)
				innerField = (new StringBuilder(String.valueOf(innerField))).append((char)(charArray[++i] - 32)).toString();
			else
				innerField = (new StringBuilder(String.valueOf(innerField))).append(charArray[i]).toString();

		return innerField;
	}

	public static String transInnerField(String innerField)
	{
		String outerField = "";
		char charArray[] = innerField.toCharArray();
		for (int i = 0; i < charArray.length; i++)
			if (i != 0 && charArray[i] >= 'A' && charArray[i] <= 'Z')
				outerField = (new StringBuilder(String.valueOf(outerField))).append("_").append((char)(charArray[i] + 32)).toString();
			else
				outerField = (new StringBuilder(String.valueOf(outerField))).append(charArray[i]).toString();

		return outerField;
	}

	public static String transOuterFieldForXML(String outerField)
	{
		String innerField = "";
		char charArray[] = outerField.toCharArray();
		boolean transflag = false;
		for (int i = 0; i < charArray.length; i++)
		{
			if (charArray[i] == '<')
				transflag = true;
			else
			if (charArray[i] == '>')
				transflag = false;
			if (transflag && charArray[i] == '_' && i != charArray.length - 1)
				innerField = (new StringBuilder(String.valueOf(innerField))).append((char)(charArray[++i] - 32)).toString();
			else
				innerField = (new StringBuilder(String.valueOf(innerField))).append(charArray[i]).toString();
		}

		return innerField;
	}

	public static String transInnerFieldForXML(String innerField)
	{
		String outerField = "";
		char charArray[] = innerField.toCharArray();
		boolean transflag = false;
		char ac[];
		int j = (ac = charArray).length;
		for (int i = 0; i < j; i++)
		{
			char element = ac[i];
			if (element == '<')
				transflag = true;
			else
			if (element == '>')
				transflag = false;
			if (transflag && element > 'A' && element < 'Z')
				outerField = (new StringBuilder(String.valueOf(outerField))).append("_").append((char)(element + 32)).toString();
			else
				outerField = (new StringBuilder(String.valueOf(outerField))).append(element).toString();
		}

		return outerField;
	}

	public static String transStrForExportXML(String str)
	{
		str = str != null ? str : "";
		str = str.replaceAll("&", "&amp;");
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll("\"", "&quot;");
		return str;
	}

	public static String getFieldStrForExportXML(String fieldname, Object fieldvalue)
	{
		String fieldXML = "";
		if (fieldvalue == null)
		{
			return "";
		} else
		{
			String fieldnameStr = transInnerField(fieldname);
			String fieldvalueStr = transStrForExportXML(fieldvalue.toString());
			fieldXML = (new StringBuilder("<")).append(fieldnameStr).append(">").append(fieldvalueStr).append("</").append(fieldnameStr).append(">\n").toString();
			return fieldXML;
		}
	}

	public static String getObjectStrForExportXML(Object exportObj)
		throws Exception
	{
		return getObjectStrForExportXML(exportObj, true);
	}

	public static String getObjectStrForExportXML(Object exportObj, boolean containId)
		throws Exception
	{
		String objXML = "";
		Class objClass = exportObj.getClass();
		Field fields[] = objClass.getDeclaredFields();
		Field afield[];
		int j = (afield = fields).length;
		for (int i = 0; i < j; i++)
		{
			Field field = afield[i];
			String fieldname = field.getName();
			Object fieldvalue = PropertyUtils.getProperty(exportObj, fieldname);
			if (fieldvalue != null && !"".equals(fieldvalue.toString().trim()) && (containId || !"id".equals(fieldname)))
				objXML = (new StringBuilder(String.valueOf(objXML))).append(getFieldStrForExportXML(fieldname, fieldvalue)).toString();
		}

		return objXML;
	}

	public static String getRightWhereStr(String columnname, String idStr)
	{
		int groupCount = 1000;
		String idArray[] = idStr.split(",");
		double count = Math.ceil((double)idArray.length / 1000D);
		String rightIdStr = "";
		for (int i = 0; (double)i < count; i++)
		{
			String tmpStr = "";
			int size = (double)i != count - 1.0D ? 1000 : idArray.length - i * 1000;
			for (int j = 0; j < size; j++)
				tmpStr = (new StringBuilder(String.valueOf(tmpStr))).append(idArray[size * i + j]).append(",").toString();

			tmpStr = tmpStr.substring(0, tmpStr.length() - 1);
			rightIdStr = (new StringBuilder(String.valueOf(rightIdStr))).append(columnname).append(" in (").append(tmpStr).append(") or ").toString();
		}

		rightIdStr = (new StringBuilder("(")).append(rightIdStr.substring(0, rightIdStr.length() - 4)).append(")").toString();
		return rightIdStr;
	}

	public static String appendSpace(String para)
	{
		int length = para.length();
		char value[] = new char[length << 1];
		int i = 0;
		for (int k = 0; i < length; k = ++i << 1)
		{
			value[k] = para.charAt(i);
			value[1 + k] = ' ';
		}

		return new String(value);
	}

	public static void main(String args[])
	{
		String idStr = "1,100";
		String str = getRightWhereStr("id", "1,100");
		System.out.println(str);
	}
}
