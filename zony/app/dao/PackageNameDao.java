// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PackageNameDao.java

package com.zony.app.dao;

import com.zony.app.domain.PackageName;
import com.zony.app.model.PackageNameModel;
import com.zony.common.dao.CommonSpringDAOImpl;
import java.util.List;
import java.util.Map;

public class PackageNameDao extends CommonSpringDAOImpl
{

	public PackageNameDao()
	{
	}

	public void completeModel(PackageNameModel packagenamemodel, Map map)
	{
	}

	public List getAllPackageName()
	{
		String hql = "From PackageName order by packageName";
		List packageNameList = getEntityList("From PackageName order by packageName", new Object[0]);
		return packageNameList;
	}

	public long savePackageName(PackageName packageName)
	{
		return ((Long)save(packageName)).longValue();
	}

	public PackageName getPackageCountByName(String packageName)
	{
		String hql = " From PackageName where packageName =?";
		return (PackageName)getEntity(" From PackageName where packageName =?", new Object[] {
			packageName
		});
	}

	public volatile void completeModel(Object obj, Map map)
	{
		completeModel((PackageNameModel)obj, map);
	}
}
