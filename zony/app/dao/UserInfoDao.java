// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   UserInfoDao.java

package com.zony.app.dao;

import com.zony.app.domain.UserInfo;
import com.zony.app.model.UserInfoModel;
import com.zony.common.dao.CommonSpringDAOImpl;
import com.zony.common.util.PageUtil;
import flex.messaging.util.StringUtils;
import java.util.*;

public class UserInfoDao extends CommonSpringDAOImpl
{

	public UserInfoDao()
	{
	}

	public void completeModel(UserInfoModel userinfomodel, Map map)
	{
	}

	public Long saveUserInfo(UserInfo userInfo)
	{
		return (Long)save(userInfo);
	}

	public UserInfo getUserInfoById(Long id)
	{
		return (UserInfo)getById(id);
	}

	public List getUserInfoByOrgCode(String orgCode)
	{
		String hql = (new StringBuilder("from UserInfo u where u.orgCode = '")).append(orgCode).append("'").toString();
		return getEntityList(hql, null);
	}

	public UserInfo getUserInfoByUserCode(String userCode)
	{
		String hql = "from UserInfo u where u.userCode = ?";
		return (UserInfo)getEntity("from UserInfo u where u.userCode = ?", new Object[] {
			userCode
		});
	}

	public UserInfo getUserInfoByUserName(String userName)
	{
		String hql = "from UserInfo u where u.userName = ?";
		return (UserInfo)getEntity("from UserInfo u where u.userName = ?", new Object[] {
			userName
		});
	}

	public UserInfo checkUserInfoUniqueByUserName(UserInfo user)
	{
		String hql = (new StringBuilder("from UserInfo u where u.userCode ='")).append(user.getUserCode()).append("'").toString();
		if (user.getId() != null)
			hql = (new StringBuilder(String.valueOf(hql))).append("  and u.id<> '").append(user.getId()).append("'").toString();
		return (UserInfo)getEntity(hql, null);
	}

	public List searchUserInfo(Map values, PageUtil page)
	{
		List userInfoList = new ArrayList();
		String hql = "from UserInfo where 1=1";
		String userCode = (String)values.get("userCode");
		String companyCode = (String)values.get("companyCode");
		String channelCode = (String)values.get("channelCode");
		String userName = (String)values.get("userName");
		if (!StringUtils.isEmpty(userCode))
			hql = (new StringBuilder(String.valueOf(hql))).append(" and userCode='").append(userCode).append("'").toString();
		if (!StringUtils.isEmpty(companyCode))
			hql = (new StringBuilder(String.valueOf(hql))).append(" and companyCode='").append(companyCode).append("'").toString();
		if (!StringUtils.isEmpty(channelCode))
			hql = (new StringBuilder(String.valueOf(hql))).append(" and channelCode='").append(channelCode).append("'").toString();
		if (!StringUtils.isEmpty(userName))
			hql = (new StringBuilder(String.valueOf(hql))).append(" and userName like '%").append(userName).append("%'").toString();
		userInfoList = getEntityList(hql, null);
		return userInfoList;
	}

	public List getAllUserInfoModel()
		throws Exception
	{
		String hql = "from UserInfo u order by u.id";
		List list = getModelList("from UserInfo u order by u.id", new Object[0]);
		return list;
	}

	public volatile void completeModel(Object obj, Map map)
	{
		completeModel((UserInfoModel)obj, map);
	}
}
