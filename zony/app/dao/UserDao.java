// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   UserDao.java

package com.zony.app.dao;

import com.zony.app.domain.User;
import com.zony.app.model.UserModel;
import com.zony.common.dao.CommonSpringDAOImpl;
import java.util.Map;

public class UserDao extends CommonSpringDAOImpl
{

	public UserDao()
	{
	}

	public long saveUser(User user)
	{
		return ((Long)save(user)).longValue();
	}

	public User getUserById(long id)
	{
		return (User)getById(Long.valueOf(id));
	}

	public void completeModel(UserModel usermodel, Map map)
	{
	}

	public volatile void completeModel(Object obj, Map map)
	{
		completeModel((UserModel)obj, map);
	}
}
