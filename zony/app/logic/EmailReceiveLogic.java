// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EmailReceiveLogic.java

package com.zony.app.logic;

import com.zony.app.constants.AppGlobals;
import com.zony.app.dao.VemailKeyDao;
import java.util.List;
import org.apache.log4j.Logger;

public class EmailReceiveLogic
{

	private static final Logger logger = Logger.getLogger(com/zony/app/logic/EmailReceiveLogic);
	private VemailKeyDao vemailKeyDao;

	public EmailReceiveLogic()
	{
	}

	public List getVemailKey()
	{
		if (AppGlobals.vemailKeyList == null || AppGlobals.vemailKeyList.size() < 1)
			AppGlobals.vemailKeyList = vemailKeyDao.getEntityList("from VemailKey", null);
		return AppGlobals.vemailKeyList;
	}

}
