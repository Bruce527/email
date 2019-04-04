// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EmailAccountDao.java

package com.zony.app.dao;

import com.zony.app.domain.EmailAccount;
import com.zony.app.model.EmailAccountModel;
import com.zony.common.config.ZonyConfig;
import com.zony.common.dao.CommonSpringDAOImpl;
import java.util.List;
import java.util.Map;

public class EmailAccountDao extends CommonSpringDAOImpl
{

	public EmailAccountDao()
	{
	}

	public void completeModel(EmailAccountModel emailaccountmodel, Map map)
	{
	}

	public void initEmailAccount()
	{
		String hql = "update EmailAccount set sendCount=0,isSending='0',isReceiving='0'";
		bulkUpdate("update EmailAccount set sendCount=0,isSending='0',isReceiving='0'");
	}

	public EmailAccount getEmailAccount(String groupCode, String type)
	{
		List list = null;
		String hql = "";
		if (type.equals("SEND"))
		{
			hql = (new StringBuilder("from EmailAccount where groupCode='")).append(groupCode).append("' and sendCount<").append(ZonyConfig.getEmailSendCount()).toString();
			list = getEntityList(hql, new Object[0]);
			if (list != null && list.size() > 0)
				return (EmailAccount)list.get(0);
			hql = "from EmailAccount ";
			list = getEntityList(hql, new Object[0]);
			if (list != null && list.size() > 0)
				return (EmailAccount)list.get(0);
			else
				return null;
		}
		if (type.equals("RECEIPT"))
		{
			hql = "from EmailAccount where isReceiving='0'";
			list = getEntityList(hql, new Object[0]);
			if (list != null && list.size() > 0)
			{
				EmailAccount emailAccount = (EmailAccount)list.get(0);
				bulkUpdate((new StringBuilder("update EmailAccount set isReceiving='1' where id=")).append(emailAccount.getId()).toString(), null);
				return emailAccount;
			} else
			{
				return null;
			}
		} else
		{
			return null;
		}
	}

	public volatile void completeModel(Object obj, Map map)
	{
		completeModel((EmailAccountModel)obj, map);
	}
}
