// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EmailTaskBakDao.java

package com.zony.app.dao;

import com.zony.app.model.EmailTaskBakModel;
import com.zony.common.dao.CommonSpringDAOImpl;
import java.util.Map;

public class EmailTaskBakDao extends CommonSpringDAOImpl
{

	public EmailTaskBakDao()
	{
	}

	public void completeModel(EmailTaskBakModel emailtaskbakmodel, Map map)
	{
	}

	public volatile void completeModel(Object obj, Map map)
	{
		completeModel((EmailTaskBakModel)obj, map);
	}
}
