// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ProductCodeDao.java

package com.zony.app.dao;

import com.zony.app.domain.ProductCode;
import com.zony.app.model.ProductCodeModel;
import com.zony.common.dao.CommonSpringDAOImpl;
import java.util.List;
import java.util.Map;

public class ProductCodeDao extends CommonSpringDAOImpl
{

	public ProductCodeDao()
	{
	}

	public void completeModel(ProductCodeModel productcodemodel, Map map)
	{
	}

	public List getProductCodeList()
	{
		String hql = "from ProductCode order by id ";
		return getEntityList(hql, new Object[0]);
	}

	public ProductCode getProductCodeListByCode(String productCode)
	{
		String hql = "from ProductCode where productCode=?";
		return (ProductCode)getEntity(hql, new Object[] {
			productCode
		});
	}

	public void addProductCode(ProductCode productCode)
	{
		save(productCode);
	}

	public List getProductInfoListByCon(String code)
	{
		String hql = (new StringBuilder("from ProductCode where productCode like '%")).append(code).append("%'").toString();
		return getEntityList(hql, new Object[0]);
	}

	public volatile void completeModel(Object obj, Map map)
	{
		completeModel((ProductCodeModel)obj, map);
	}
}
