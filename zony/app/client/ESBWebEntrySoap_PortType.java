// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ESBWebEntrySoap_PortType.java

package com.zony.app.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

// Referenced classes of package com.zony.app.client:
//			ESBEnvelope, ESBEnvelopeResult

public interface ESBWebEntrySoap_PortType
	extends Remote
{

	public abstract ESBEnvelopeResult processMessage(ESBEnvelope esbenvelope)
		throws RemoteException;
}
