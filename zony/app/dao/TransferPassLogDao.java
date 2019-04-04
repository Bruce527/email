// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TransferPassLogDao.java

package com.zony.app.dao;

import com.zony.app.domain.TransferPassLog;
import com.zony.app.model.TransferPassLogModel;
import com.zony.common.dao.CommonSpringDAOImpl;
import java.util.List;
import java.util.Map;

public class TransferPassLogDao extends CommonSpringDAOImpl
{

	public TransferPassLogDao()
	{
	}

	public void saveTransferPassLog(TransferPassLog transferPassLog)
	{
		save(transferPassLog);
	}

	public void saveTransferPassLogList(List list)
	{
		saveList(list);
	}

	public List getTransferPassLogList(int count)
	{
		String hql = (new StringBuilder("from TransferPassLog where transferStatus ='2' and transferCount =")).append(count).toString();
		return getEntityList(hql, new Object[0]);
	}

	public List getTransfailLogList(int count)
	{
		String hql = (new StringBuilder("from TransferPassLog where transferStatus= '2' and transferCount <=")).append(count).toString();
		return getEntityList(hql, new Object[0]);
	}

	public List getTransferPassLogByTransferTimeList(String TransferTime)
	{
		String hql = (new StringBuilder("from TransferPassLog where transferStatus= '2' and TransferTime<'")).append(TransferTime).append("'").toString();
		return getEntityList(hql, new Object[0]);
	}

	public TransferPassLog getTransferPassLog(String cntrno)
	{
		String hql = (new StringBuilder("from TransferPassLog where cntrno='")).append(cntrno).append("'").toString();
		return (TransferPassLog)getEntity(hql, new Object[0]);
	}

	public void updateTransPassLog(TransferPassLog transferPassLog)
	{
		update(transferPassLog);
	}

	public void completeModel(TransferPassLogModel transferpasslogmodel, Map map)
	{
	}

	public volatile void completeModel(Object obj, Map map)
	{
		completeModel((TransferPassLogModel)obj, map);
	}
}
