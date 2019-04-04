// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ImportExceptionDao.java

package com.zony.app.dao;

import com.zony.app.domain.ImportException;
import com.zony.app.model.ImportExceptionModel;
import com.zony.common.dao.CommonSpringDAOImpl;
import com.zony.common.util.DateUtil;
import java.util.Map;

public class ImportExceptionDao extends CommonSpringDAOImpl
{

	public ImportExceptionDao()
	{
	}

	public void completeModel(ImportExceptionModel importexceptionmodel, Map map)
	{
	}

	public long savePolicyInfo(ImportException importException)
	{
		return ((Long)save(importException)).longValue();
	}

	public ImportException getImportExceptionInfoById(long id)
	{
		return (ImportException)getById(Long.valueOf(id));
	}

	public int updateImportExceptionInfoByID(long id, String note)
	{
		String deleteDate = DateUtil.getNow();
		String hql = (new StringBuilder(" update ImportException set DeleteDate=")).append(deleteDate).append(", Note =").append(note).append(" where ID=").append(id).toString();
		return bulkUpdate(hql);
	}

	public volatile void completeModel(Object obj, Map map)
	{
		completeModel((ImportExceptionModel)obj, map);
	}
}
