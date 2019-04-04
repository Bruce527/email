// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ESBWebEntryLocator.java

package com.zony.app.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Remote;
import java.util.HashSet;
import java.util.Iterator;
import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;
import org.apache.axis.AxisFault;
import org.apache.axis.EngineConfiguration;
import org.apache.axis.client.Service;
import org.apache.axis.client.Stub;

// Referenced classes of package com.zony.app.client:
//			ESBWebEntry, ESBWebEntrySoap_BindingStub, ESBWebEntrySoap12Stub, ESBWebEntrySoap_PortType

public class ESBWebEntryLocator extends Service
	implements ESBWebEntry
{

	private String ESBWebEntrySoap_address;
	private String ESBWebEntrySoapWSDDServiceName;
	private String ESBWebEntrySoap12_address;
	private String ESBWebEntrySoap12WSDDServiceName;
	private HashSet ports;

	public ESBWebEntryLocator()
	{
		ESBWebEntrySoap_address = "http://10.165.4.74/esbwebentry/ESBWebEntry.asmx";
		ESBWebEntrySoapWSDDServiceName = "ESBWebEntrySoap";
		ESBWebEntrySoap12_address = "http://10.165.4.74/esbwebentry/ESBWebEntry.asmx";
		ESBWebEntrySoap12WSDDServiceName = "ESBWebEntrySoap12";
		ports = null;
	}

	public ESBWebEntryLocator(EngineConfiguration config)
	{
		super(config);
		ESBWebEntrySoap_address = "http://10.165.4.74/esbwebentry/ESBWebEntry.asmx";
		ESBWebEntrySoapWSDDServiceName = "ESBWebEntrySoap";
		ESBWebEntrySoap12_address = "http://10.165.4.74/esbwebentry/ESBWebEntry.asmx";
		ESBWebEntrySoap12WSDDServiceName = "ESBWebEntrySoap12";
		ports = null;
	}

	public ESBWebEntryLocator(String wsdlLoc, QName sName)
		throws ServiceException
	{
		super(wsdlLoc, sName);
		ESBWebEntrySoap_address = "http://10.165.4.74/esbwebentry/ESBWebEntry.asmx";
		ESBWebEntrySoapWSDDServiceName = "ESBWebEntrySoap";
		ESBWebEntrySoap12_address = "http://10.165.4.74/esbwebentry/ESBWebEntry.asmx";
		ESBWebEntrySoap12WSDDServiceName = "ESBWebEntrySoap12";
		ports = null;
	}

	public String getESBWebEntrySoapAddress()
	{
		return ESBWebEntrySoap_address;
	}

	public String getESBWebEntrySoapWSDDServiceName()
	{
		return ESBWebEntrySoapWSDDServiceName;
	}

	public void setESBWebEntrySoapWSDDServiceName(String name)
	{
		ESBWebEntrySoapWSDDServiceName = name;
	}

	public ESBWebEntrySoap_PortType getESBWebEntrySoap()
		throws ServiceException
	{
		URL endpoint;
		try
		{
			endpoint = new URL(ESBWebEntrySoap_address);
		}
		catch (MalformedURLException e)
		{
			throw new ServiceException(e);
		}
		return getESBWebEntrySoap(endpoint);
	}

	public ESBWebEntrySoap_PortType getESBWebEntrySoap(URL portAddress)
		throws ServiceException
	{
		ESBWebEntrySoap_BindingStub _stub;
		_stub = new ESBWebEntrySoap_BindingStub(portAddress, this);
		_stub.setPortName(getESBWebEntrySoapWSDDServiceName());
		return _stub;
		AxisFault e;
		e;
		return null;
	}

	public void setESBWebEntrySoapEndpointAddress(String address)
	{
		ESBWebEntrySoap_address = address;
	}

	public String getESBWebEntrySoap12Address()
	{
		return ESBWebEntrySoap12_address;
	}

	public String getESBWebEntrySoap12WSDDServiceName()
	{
		return ESBWebEntrySoap12WSDDServiceName;
	}

	public void setESBWebEntrySoap12WSDDServiceName(String name)
	{
		ESBWebEntrySoap12WSDDServiceName = name;
	}

	public ESBWebEntrySoap_PortType getESBWebEntrySoap12()
		throws ServiceException
	{
		URL endpoint;
		try
		{
			endpoint = new URL(ESBWebEntrySoap12_address);
		}
		catch (MalformedURLException e)
		{
			throw new ServiceException(e);
		}
		return getESBWebEntrySoap12(endpoint);
	}

	public ESBWebEntrySoap_PortType getESBWebEntrySoap12(URL portAddress)
		throws ServiceException
	{
		ESBWebEntrySoap12Stub _stub;
		_stub = new ESBWebEntrySoap12Stub(portAddress, this);
		_stub.setPortName(getESBWebEntrySoap12WSDDServiceName());
		return _stub;
		AxisFault e;
		e;
		return null;
	}

	public void setESBWebEntrySoap12EndpointAddress(String address)
	{
		ESBWebEntrySoap12_address = address;
	}

	public Remote getPort(Class serviceEndpointInterface)
		throws ServiceException
	{
		ESBWebEntrySoap_BindingStub _stub;
		if (!com/zony/app/client/ESBWebEntrySoap_PortType.isAssignableFrom(serviceEndpointInterface))
			break MISSING_BLOCK_LABEL_39;
		_stub = new ESBWebEntrySoap_BindingStub(new URL(ESBWebEntrySoap_address), this);
		_stub.setPortName(getESBWebEntrySoapWSDDServiceName());
		return _stub;
		if (!com/zony/app/client/ESBWebEntrySoap_PortType.isAssignableFrom(serviceEndpointInterface))
			break MISSING_BLOCK_LABEL_88;
		_stub = new ESBWebEntrySoap12Stub(new URL(ESBWebEntrySoap12_address), this);
		_stub.setPortName(getESBWebEntrySoap12WSDDServiceName());
		return _stub;
		Throwable t;
		t;
		throw new ServiceException(t);
		throw new ServiceException((new StringBuilder("There is no stub implementation for the interface:  ")).append(serviceEndpointInterface != null ? serviceEndpointInterface.getName() : "null").toString());
	}

	public Remote getPort(QName portName, Class serviceEndpointInterface)
		throws ServiceException
	{
		if (portName == null)
			return getPort(serviceEndpointInterface);
		String inputPortName = portName.getLocalPart();
		if ("ESBWebEntrySoap".equals(inputPortName))
			return getESBWebEntrySoap();
		if ("ESBWebEntrySoap12".equals(inputPortName))
		{
			return getESBWebEntrySoap12();
		} else
		{
			Remote _stub = getPort(serviceEndpointInterface);
			((Stub)_stub).setPortName(portName);
			return _stub;
		}
	}

	public QName getServiceName()
	{
		return new QName("http://eai.metlife.com/", "ESBWebEntry");
	}

	public Iterator getPorts()
	{
		if (ports == null)
		{
			ports = new HashSet();
			ports.add(new QName("http://eai.metlife.com/", "ESBWebEntrySoap"));
			ports.add(new QName("http://eai.metlife.com/", "ESBWebEntrySoap12"));
		}
		return ports.iterator();
	}

	public void setEndpointAddress(String portName, String address)
		throws ServiceException
	{
		if ("ESBWebEntrySoap".equals(portName))
			setESBWebEntrySoapEndpointAddress(address);
		else
		if ("ESBWebEntrySoap12".equals(portName))
			setESBWebEntrySoap12EndpointAddress(address);
		else
			throw new ServiceException((new StringBuilder(" Cannot set Endpoint Address for Unknown Port")).append(portName).toString());
	}

	public void setEndpointAddress(QName portName, String address)
		throws ServiceException
	{
		setEndpointAddress(portName.getLocalPart(), address);
	}
}
