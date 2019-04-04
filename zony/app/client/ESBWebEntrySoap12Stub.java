// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ESBWebEntrySoap12Stub.java

package com.zony.app.client;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import javax.xml.namespace.QName;
import javax.xml.rpc.encoding.SerializerFactory;
import org.apache.axis.AxisFault;
import org.apache.axis.NoEndPointException;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.client.Stub;
import org.apache.axis.constants.Style;
import org.apache.axis.constants.Use;
import org.apache.axis.description.OperationDesc;
import org.apache.axis.description.ParameterDesc;
import org.apache.axis.encoding.DeserializerFactory;
import org.apache.axis.encoding.ser.ArrayDeserializerFactory;
import org.apache.axis.encoding.ser.ArraySerializerFactory;
import org.apache.axis.encoding.ser.BeanDeserializerFactory;
import org.apache.axis.encoding.ser.BeanSerializerFactory;
import org.apache.axis.encoding.ser.EnumDeserializerFactory;
import org.apache.axis.encoding.ser.EnumSerializerFactory;
import org.apache.axis.encoding.ser.SimpleDeserializerFactory;
import org.apache.axis.encoding.ser.SimpleListDeserializerFactory;
import org.apache.axis.encoding.ser.SimpleListSerializerFactory;
import org.apache.axis.encoding.ser.SimpleSerializerFactory;
import org.apache.axis.soap.SOAPConstants;
import org.apache.axis.utils.JavaUtils;

// Referenced classes of package com.zony.app.client:
//			ESBWebEntrySoap_PortType, ESBEnvelope, ESBEnvelopeResult, ProcessMessageResponse, 
//			ESBEnvelopeESBHeader, ESBEnvelopeMsgBody, ESBEnvelopeResultESBHeader, ESBEnvelopeResultMsgBody

public class ESBWebEntrySoap12Stub extends Stub
	implements ESBWebEntrySoap_PortType
{

	private Vector cachedSerClasses;
	private Vector cachedSerQNames;
	private Vector cachedSerFactories;
	private Vector cachedDeserFactories;
	static OperationDesc _operations[] = new OperationDesc[1];

	private static void _initOperationDesc1()
	{
		OperationDesc oper = new OperationDesc();
		oper.setName("ProcessMessage");
		ParameterDesc param = new ParameterDesc(new QName("http://MetLifeEAI.EAISchema", "ESBEnvelope"), (byte)1, new QName("http://MetLifeEAI.EAISchema", ">ESBEnvelope"), com/zony/app/client/ESBEnvelope, false, false);
		param.setOmittable(true);
		oper.addParameter(param);
		oper.setReturnType(new QName("http://MetLifeEAI.EAISchema", ">ESBEnvelopeResult"));
		oper.setReturnClass(com/zony/app/client/ESBEnvelopeResult);
		oper.setReturnQName(new QName("http://MetLifeEAI.EAISchema", "ESBEnvelopeResult"));
		oper.setStyle(Style.WRAPPED);
		oper.setUse(Use.LITERAL);
		_operations[0] = oper;
	}

	public ESBWebEntrySoap12Stub()
		throws AxisFault
	{
		this(null);
	}

	public ESBWebEntrySoap12Stub(URL endpointURL, javax.xml.rpc.Service service)
		throws AxisFault
	{
		this(service);
		super.cachedEndpoint = endpointURL;
	}

	public ESBWebEntrySoap12Stub(javax.xml.rpc.Service service)
		throws AxisFault
	{
		cachedSerClasses = new Vector();
		cachedSerQNames = new Vector();
		cachedSerFactories = new Vector();
		cachedDeserFactories = new Vector();
		if (service == null)
			super.service = new Service();
		else
			super.service = service;
		((Service)super.service).setTypeMappingVersion("1.2");
		Class beansf = org/apache/axis/encoding/ser/BeanSerializerFactory;
		Class beandf = org/apache/axis/encoding/ser/BeanDeserializerFactory;
		Class enumsf = org/apache/axis/encoding/ser/EnumSerializerFactory;
		Class enumdf = org/apache/axis/encoding/ser/EnumDeserializerFactory;
		Class arraysf = org/apache/axis/encoding/ser/ArraySerializerFactory;
		Class arraydf = org/apache/axis/encoding/ser/ArrayDeserializerFactory;
		Class simplesf = org/apache/axis/encoding/ser/SimpleSerializerFactory;
		Class simpledf = org/apache/axis/encoding/ser/SimpleDeserializerFactory;
		Class simplelistsf = org/apache/axis/encoding/ser/SimpleListSerializerFactory;
		Class simplelistdf = org/apache/axis/encoding/ser/SimpleListDeserializerFactory;
		QName qName = new QName("http://eai.metlife.com/", ">ProcessMessageResponse");
		cachedSerQNames.add(qName);
		Class cls = com/zony/app/client/ProcessMessageResponse;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);
		qName = new QName("http://MetLifeEAI.EAISchema", ">>ESBEnvelope>ESBHeader");
		cachedSerQNames.add(qName);
		cls = com/zony/app/client/ESBEnvelopeESBHeader;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);
		qName = new QName("http://MetLifeEAI.EAISchema", ">>ESBEnvelope>MsgBody");
		cachedSerQNames.add(qName);
		cls = com/zony/app/client/ESBEnvelopeMsgBody;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);
		qName = new QName("http://MetLifeEAI.EAISchema", ">>ESBEnvelopeResult>ESBHeader");
		cachedSerQNames.add(qName);
		cls = com/zony/app/client/ESBEnvelopeResultESBHeader;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);
		qName = new QName("http://MetLifeEAI.EAISchema", ">>ESBEnvelopeResult>MsgBody");
		cachedSerQNames.add(qName);
		cls = com/zony/app/client/ESBEnvelopeResultMsgBody;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);
		qName = new QName("http://MetLifeEAI.EAISchema", ">ESBEnvelope");
		cachedSerQNames.add(qName);
		cls = com/zony/app/client/ESBEnvelope;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);
		qName = new QName("http://MetLifeEAI.EAISchema", ">ESBEnvelopeResult");
		cachedSerQNames.add(qName);
		cls = com/zony/app/client/ESBEnvelopeResult;
		cachedSerClasses.add(cls);
		cachedSerFactories.add(beansf);
		cachedDeserFactories.add(beandf);
	}

	protected Call createCall()
		throws RemoteException
	{
		Call _call;
		_call = super._createCall();
		if (super.maintainSessionSet)
			_call.setMaintainSession(super.maintainSession);
		if (super.cachedUsername != null)
			_call.setUsername(super.cachedUsername);
		if (super.cachedPassword != null)
			_call.setPassword(super.cachedPassword);
		if (super.cachedEndpoint != null)
			_call.setTargetEndpointAddress(super.cachedEndpoint);
		if (super.cachedTimeout != null)
			_call.setTimeout(super.cachedTimeout);
		if (super.cachedPortName != null)
			_call.setPortName(super.cachedPortName);
		String key;
		for (Enumeration keys = super.cachedProperties.keys(); keys.hasMoreElements(); _call.setProperty(key, super.cachedProperties.get(key)))
			key = (String)keys.nextElement();

		synchronized (this)
		{
			if (firstCall())
			{
				_call.setEncodingStyle(null);
				for (int i = 0; i < cachedSerFactories.size(); i++)
				{
					Class cls = (Class)cachedSerClasses.get(i);
					QName qName = (QName)cachedSerQNames.get(i);
					Object x = cachedSerFactories.get(i);
					if (x instanceof Class)
					{
						Class sf = (Class)cachedSerFactories.get(i);
						Class df = (Class)cachedDeserFactories.get(i);
						_call.registerTypeMapping(cls, qName, sf, df, false);
					} else
					if (x instanceof SerializerFactory)
					{
						org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)cachedSerFactories.get(i);
						DeserializerFactory df = (DeserializerFactory)cachedDeserFactories.get(i);
						_call.registerTypeMapping(cls, qName, sf, df, false);
					}
				}

			}
		}
		return _call;
		Throwable _t;
		_t;
		throw new AxisFault("Failure trying to get the Call object", _t);
	}

	public ESBEnvelopeResult processMessage(ESBEnvelope ESBEnvelope)
		throws RemoteException
	{
		Call _call;
		if (super.cachedEndpoint == null)
			throw new NoEndPointException();
		_call = createCall();
		_call.setOperation(_operations[0]);
		_call.setUseSOAPAction(true);
		_call.setSOAPActionURI("http://eai.metlife.com/ESBWebEntry/ProcessMessage");
		_call.setEncodingStyle(null);
		_call.setProperty("sendXsiTypes", Boolean.FALSE);
		_call.setProperty("sendMultiRefs", Boolean.FALSE);
		_call.setSOAPVersion(SOAPConstants.SOAP12_CONSTANTS);
		_call.setOperationName(new QName("http://eai.metlife.com/", "ProcessMessage"));
		setRequestHeaders(_call);
		setAttachments(_call);
		Object _resp;
		_resp = _call.invoke(new Object[] {
			ESBEnvelope
		});
		if (_resp instanceof RemoteException)
			throw (RemoteException)_resp;
		extractAttachments(_call);
		return (ESBEnvelopeResult)_resp;
		Exception _exception;
		_exception;
		return (ESBEnvelopeResult)JavaUtils.convert(_resp, com/zony/app/client/ESBEnvelopeResult);
		AxisFault axisFaultException;
		axisFaultException;
		throw axisFaultException;
	}

	static 
	{
		_initOperationDesc1();
	}
}
