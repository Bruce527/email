// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ESBEnvelopeMsgBody.java

package com.zony.app.client;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import javax.xml.namespace.QName;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.*;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;
import org.apache.axis.message.MessageElement;

public class ESBEnvelopeMsgBody
	implements Serializable, AnyContentType
{

	private MessageElement _any[];
	private Object __equalsCalc;
	private boolean __hashCodeCalc;
	private static TypeDesc typeDesc;

	public ESBEnvelopeMsgBody()
	{
		__equalsCalc = null;
		__hashCodeCalc = false;
	}

	public ESBEnvelopeMsgBody(MessageElement _any[])
	{
		__equalsCalc = null;
		__hashCodeCalc = false;
		this._any = _any;
	}

	public MessageElement[] get_any()
	{
		return _any;
	}

	public void set_any(MessageElement _any[])
	{
		this._any = _any;
	}

	public synchronized boolean equals(Object obj)
	{
		if (!(obj instanceof ESBEnvelopeMsgBody))
			return false;
		ESBEnvelopeMsgBody other = (ESBEnvelopeMsgBody)obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (__equalsCalc != null)
		{
			return __equalsCalc == obj;
		} else
		{
			__equalsCalc = obj;
			boolean _equals = _any == null && other.get_any() == null || _any != null && Arrays.equals(_any, other.get_any());
			__equalsCalc = null;
			return _equals;
		}
	}

	public synchronized int hashCode()
	{
		if (__hashCodeCalc)
			return 0;
		__hashCodeCalc = true;
		int _hashCode = 1;
		if (get_any() != null)
		{
			for (int i = 0; i < Array.getLength(get_any()); i++)
			{
				Object obj = Array.get(get_any(), i);
				if (obj != null && !obj.getClass().isArray())
					_hashCode += obj.hashCode();
			}

		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	public static TypeDesc getTypeDesc()
	{
		return typeDesc;
	}

	public static Serializer getSerializer(String mechType, Class _javaType, QName _xmlType)
	{
		return new BeanSerializer(_javaType, _xmlType, typeDesc);
	}

	public static Deserializer getDeserializer(String mechType, Class _javaType, QName _xmlType)
	{
		return new BeanDeserializer(_javaType, _xmlType, typeDesc);
	}

	public String toString()
	{
		return (new StringBuilder("ESBEnvelopeMsgBody [_any=")).append(Arrays.toString(_any)).append(", __equalsCalc=").append(__equalsCalc).append(", __hashCodeCalc=").append(__hashCodeCalc).append("]").toString();
	}

	static 
	{
		typeDesc = new TypeDesc(com/zony/app/client/ESBEnvelopeMsgBody, true);
		typeDesc.setXmlType(new QName("http://MetLifeEAI.EAISchema", ">>ESBEnvelope>MsgBody"));
	}
}
