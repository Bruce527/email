// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ProcessMessageResponse.java

package com.zony.app.client;

import java.io.Serializable;
import javax.xml.namespace.QName;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

// Referenced classes of package com.zony.app.client:
//			ESBEnvelopeResult

public class ProcessMessageResponse
	implements Serializable
{

	private ESBEnvelopeResult ESBEnvelopeResult;
	private Object __equalsCalc;
	private boolean __hashCodeCalc;
	private static TypeDesc typeDesc;

	public ProcessMessageResponse()
	{
		__equalsCalc = null;
		__hashCodeCalc = false;
	}

	public ProcessMessageResponse(ESBEnvelopeResult ESBEnvelopeResult)
	{
		__equalsCalc = null;
		__hashCodeCalc = false;
		this.ESBEnvelopeResult = ESBEnvelopeResult;
	}

	public ESBEnvelopeResult getESBEnvelopeResult()
	{
		return ESBEnvelopeResult;
	}

	public void setESBEnvelopeResult(ESBEnvelopeResult ESBEnvelopeResult)
	{
		this.ESBEnvelopeResult = ESBEnvelopeResult;
	}

	public synchronized boolean equals(Object obj)
	{
		if (!(obj instanceof ProcessMessageResponse))
			return false;
		ProcessMessageResponse other = (ProcessMessageResponse)obj;
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
			boolean _equals = ESBEnvelopeResult == null && other.getESBEnvelopeResult() == null || ESBEnvelopeResult != null && ESBEnvelopeResult.equals(other.getESBEnvelopeResult());
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
		if (getESBEnvelopeResult() != null)
			_hashCode += getESBEnvelopeResult().hashCode();
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

	static 
	{
		typeDesc = new TypeDesc(com/zony/app/client/ProcessMessageResponse, true);
		typeDesc.setXmlType(new QName("http://eai.metlife.com/", ">ProcessMessageResponse"));
		ElementDesc elemField = new ElementDesc();
		elemField.setFieldName("ESBEnvelopeResult");
		elemField.setXmlName(new QName("http://MetLifeEAI.EAISchema", "ESBEnvelopeResult"));
		elemField.setXmlType(new QName("http://MetLifeEAI.EAISchema", ">ESBEnvelopeResult"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
	}
}
