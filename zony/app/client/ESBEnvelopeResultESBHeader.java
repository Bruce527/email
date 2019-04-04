// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ESBEnvelopeResultESBHeader.java

package com.zony.app.client;

import java.io.Serializable;
import java.util.Date;
import javax.xml.namespace.QName;
import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;
import org.apache.axis.types.Time;

public class ESBEnvelopeResultESBHeader
	implements Serializable
{

	private String ESBHdVer;
	private Date srvDate;
	private Time srvTime;
	private String senderID;
	private String receiverID;
	private String srvOpName;
	private String srvOpVer;
	private String msgID;
	private String corrID;
	private String ESBRspCode;
	private String ESBRspDec;
	private String resField1;
	private String resField2;
	private String resField3;
	private String resField4;
	private String resField5;
	private Object __equalsCalc;
	private boolean __hashCodeCalc;
	private static TypeDesc typeDesc;

	public ESBEnvelopeResultESBHeader()
	{
		__equalsCalc = null;
		__hashCodeCalc = false;
	}

	public ESBEnvelopeResultESBHeader(String ESBHdVer, Date srvDate, Time srvTime, String senderID, String receiverID, String srvOpName, String srvOpVer, 
			String msgID, String corrID, String ESBRspCode, String ESBRspDec, String resField1, String resField2, String resField3, 
			String resField4, String resField5)
	{
		__equalsCalc = null;
		__hashCodeCalc = false;
		this.ESBHdVer = ESBHdVer;
		this.srvDate = srvDate;
		this.srvTime = srvTime;
		this.senderID = senderID;
		this.receiverID = receiverID;
		this.srvOpName = srvOpName;
		this.srvOpVer = srvOpVer;
		this.msgID = msgID;
		this.corrID = corrID;
		this.ESBRspCode = ESBRspCode;
		this.ESBRspDec = ESBRspDec;
		this.resField1 = resField1;
		this.resField2 = resField2;
		this.resField3 = resField3;
		this.resField4 = resField4;
		this.resField5 = resField5;
	}

	public String getESBHdVer()
	{
		return ESBHdVer;
	}

	public void setESBHdVer(String ESBHdVer)
	{
		this.ESBHdVer = ESBHdVer;
	}

	public Date getSrvDate()
	{
		return srvDate;
	}

	public void setSrvDate(Date srvDate)
	{
		this.srvDate = srvDate;
	}

	public Time getSrvTime()
	{
		return srvTime;
	}

	public void setSrvTime(Time srvTime)
	{
		this.srvTime = srvTime;
	}

	public String getSenderID()
	{
		return senderID;
	}

	public void setSenderID(String senderID)
	{
		this.senderID = senderID;
	}

	public String getReceiverID()
	{
		return receiverID;
	}

	public void setReceiverID(String receiverID)
	{
		this.receiverID = receiverID;
	}

	public String getSrvOpName()
	{
		return srvOpName;
	}

	public void setSrvOpName(String srvOpName)
	{
		this.srvOpName = srvOpName;
	}

	public String getSrvOpVer()
	{
		return srvOpVer;
	}

	public void setSrvOpVer(String srvOpVer)
	{
		this.srvOpVer = srvOpVer;
	}

	public String getMsgID()
	{
		return msgID;
	}

	public void setMsgID(String msgID)
	{
		this.msgID = msgID;
	}

	public String getCorrID()
	{
		return corrID;
	}

	public void setCorrID(String corrID)
	{
		this.corrID = corrID;
	}

	public String getESBRspCode()
	{
		return ESBRspCode;
	}

	public void setESBRspCode(String ESBRspCode)
	{
		this.ESBRspCode = ESBRspCode;
	}

	public String getESBRspDec()
	{
		return ESBRspDec;
	}

	public void setESBRspDec(String ESBRspDec)
	{
		this.ESBRspDec = ESBRspDec;
	}

	public String getResField1()
	{
		return resField1;
	}

	public void setResField1(String resField1)
	{
		this.resField1 = resField1;
	}

	public String getResField2()
	{
		return resField2;
	}

	public void setResField2(String resField2)
	{
		this.resField2 = resField2;
	}

	public String getResField3()
	{
		return resField3;
	}

	public void setResField3(String resField3)
	{
		this.resField3 = resField3;
	}

	public String getResField4()
	{
		return resField4;
	}

	public void setResField4(String resField4)
	{
		this.resField4 = resField4;
	}

	public String getResField5()
	{
		return resField5;
	}

	public void setResField5(String resField5)
	{
		this.resField5 = resField5;
	}

	public synchronized boolean equals(Object obj)
	{
		if (!(obj instanceof ESBEnvelopeResultESBHeader))
			return false;
		ESBEnvelopeResultESBHeader other = (ESBEnvelopeResultESBHeader)obj;
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
			boolean _equals = (ESBHdVer == null && other.getESBHdVer() == null || ESBHdVer != null && ESBHdVer.equals(other.getESBHdVer())) && (srvDate == null && other.getSrvDate() == null || srvDate != null && srvDate.equals(other.getSrvDate())) && (srvTime == null && other.getSrvTime() == null || srvTime != null && srvTime.equals(other.getSrvTime())) && (senderID == null && other.getSenderID() == null || senderID != null && senderID.equals(other.getSenderID())) && (receiverID == null && other.getReceiverID() == null || receiverID != null && receiverID.equals(other.getReceiverID())) && (srvOpName == null && other.getSrvOpName() == null || srvOpName != null && srvOpName.equals(other.getSrvOpName())) && (srvOpVer == null && other.getSrvOpVer() == null || srvOpVer != null && srvOpVer.equals(other.getSrvOpVer())) && (msgID == null && other.getMsgID() == null || msgID != null && msgID.equals(other.getMsgID())) && (corrID == null && other.getCorrID() == null || corrID != null && corrID.equals(other.getCorrID())) && (ESBRspCode == null && other.getESBRspCode() == null || ESBRspCode != null && ESBRspCode.equals(other.getESBRspCode())) && (ESBRspDec == null && other.getESBRspDec() == null || ESBRspDec != null && ESBRspDec.equals(other.getESBRspDec())) && (resField1 == null && other.getResField1() == null || resField1 != null && resField1.equals(other.getResField1())) && (resField2 == null && other.getResField2() == null || resField2 != null && resField2.equals(other.getResField2())) && (resField3 == null && other.getResField3() == null || resField3 != null && resField3.equals(other.getResField3())) && (resField4 == null && other.getResField4() == null || resField4 != null && resField4.equals(other.getResField4())) && (resField5 == null && other.getResField5() == null || resField5 != null && resField5.equals(other.getResField5()));
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
		if (getESBHdVer() != null)
			_hashCode += getESBHdVer().hashCode();
		if (getSrvDate() != null)
			_hashCode += getSrvDate().hashCode();
		if (getSrvTime() != null)
			_hashCode += getSrvTime().hashCode();
		if (getSenderID() != null)
			_hashCode += getSenderID().hashCode();
		if (getReceiverID() != null)
			_hashCode += getReceiverID().hashCode();
		if (getSrvOpName() != null)
			_hashCode += getSrvOpName().hashCode();
		if (getSrvOpVer() != null)
			_hashCode += getSrvOpVer().hashCode();
		if (getMsgID() != null)
			_hashCode += getMsgID().hashCode();
		if (getCorrID() != null)
			_hashCode += getCorrID().hashCode();
		if (getESBRspCode() != null)
			_hashCode += getESBRspCode().hashCode();
		if (getESBRspDec() != null)
			_hashCode += getESBRspDec().hashCode();
		if (getResField1() != null)
			_hashCode += getResField1().hashCode();
		if (getResField2() != null)
			_hashCode += getResField2().hashCode();
		if (getResField3() != null)
			_hashCode += getResField3().hashCode();
		if (getResField4() != null)
			_hashCode += getResField4().hashCode();
		if (getResField5() != null)
			_hashCode += getResField5().hashCode();
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
		typeDesc = new TypeDesc(com/zony/app/client/ESBEnvelopeResultESBHeader, true);
		typeDesc.setXmlType(new QName("http://MetLifeEAI.EAISchema", ">>ESBEnvelopeResult>ESBHeader"));
		ElementDesc elemField = new ElementDesc();
		elemField.setFieldName("ESBHdVer");
		elemField.setXmlName(new QName("", "ESBHdVer"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("srvDate");
		elemField.setXmlName(new QName("", "SrvDate"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "date"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("srvTime");
		elemField.setXmlName(new QName("", "SrvTime"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "time"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("senderID");
		elemField.setXmlName(new QName("", "SenderID"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("receiverID");
		elemField.setXmlName(new QName("", "ReceiverID"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("srvOpName");
		elemField.setXmlName(new QName("", "SrvOpName"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("srvOpVer");
		elemField.setXmlName(new QName("", "SrvOpVer"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("msgID");
		elemField.setXmlName(new QName("", "MsgID"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("corrID");
		elemField.setXmlName(new QName("", "CorrID"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("ESBRspCode");
		elemField.setXmlName(new QName("", "ESBRspCode"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("ESBRspDec");
		elemField.setXmlName(new QName("", "ESBRspDec"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("resField1");
		elemField.setXmlName(new QName("", "ResField1"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("resField2");
		elemField.setXmlName(new QName("", "ResField2"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("resField3");
		elemField.setXmlName(new QName("", "ResField3"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("resField4");
		elemField.setXmlName(new QName("", "ResField4"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new ElementDesc();
		elemField.setFieldName("resField5");
		elemField.setXmlName(new QName("", "ResField5"));
		elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
	}
}
