// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ESBWebEntry.java

package com.zony.app.client;

import java.net.URL;
import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceException;

// Referenced classes of package com.zony.app.client:
//			ESBWebEntrySoap_PortType

public interface ESBWebEntry
	extends Service
{

	public abstract String getESBWebEntrySoapAddress();

	public abstract ESBWebEntrySoap_PortType getESBWebEntrySoap()
		throws ServiceException;

	public abstract ESBWebEntrySoap_PortType getESBWebEntrySoap(URL url)
		throws ServiceException;

	public abstract String getESBWebEntrySoap12Address();

	public abstract ESBWebEntrySoap_PortType getESBWebEntrySoap12()
		throws ServiceException;

	public abstract ESBWebEntrySoap_PortType getESBWebEntrySoap12(URL url)
		throws ServiceException;
}
