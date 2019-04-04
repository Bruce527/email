// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LoginLogic.java

package com.zony.app.logic;

import com.zony.app.constants.Constant;
import com.zony.app.dao.*;
import com.zony.app.domain.*;
import com.zony.common.config.ZonyConfig;
import com.zony.common.util.MD5Util;
import java.io.PrintStream;
import java.util.*;
import javax.naming.AuthenticationException;
import javax.naming.directory.InitialDirContext;
import org.apache.commons.lang.StringUtils;

// Referenced classes of package com.zony.app.logic:
//			OrganizationLogic, LdapPrincipalLogic

public class LoginLogic
{

	private SysDictionaryDao sysDictionaryDao;
	private GroupInfoDao groupInfoDao;
	private ProjectNameDao projectNameDao;
	private PackageNameDao packageNameDao;
	private OrganizationDao organizationDao;
	private UserInfoDao userInfoDao;
	private LogEventDao logEventDao;
	private OrganizationLogic organizationLogic;
	private LdapPrincipalLogic ldapPrincipalLogic;
	private FunctionInfoDao functionInfoDao;

	public LoginLogic()
	{
	}

	public Map doLogin(String userCode, String password)
		throws Exception
	{
		String errorInfo = "";
		UserInfo userInfo = null;
		Map dataMap = new HashMap();
		Map rightsMap = new HashMap();
		boolean isSystemManager = ZonyConfig.getSystemManagerName().equals(userCode);
		if (isSystemManager)
		{
			boolean checkResult = MD5Util.checkpassword(password, ZonyConfig.getSystemManagerPassword());
			if (!checkResult)
			{
				errorInfo = "系统管理员登录失败，请检查登录密码！";
				dataMap.put("errorInfo", errorInfo);
				return dataMap;
			}
			userInfo = new UserInfo();
			userInfo.setUserName("系统管理员");
			userInfo.setOrgCode("");
		} else
		{
			boolean checkFlag = queryDomainUser(userCode, password);
			if (!checkFlag)
			{
				errorInfo = "AD域验证失败，请检查用户名和密码！";
				dataMap.put("errorInfo", errorInfo);
				return dataMap;
			}
			userInfo = userInfoDao.getUserInfoByUserCode(userCode);
			if (userInfo == null)
			{
				errorInfo = "该用户未在本系统注册，请联系系统管理员！";
				dataMap.put("errorInfo", errorInfo);
				return dataMap;
			}
			rightsMap = createRightsMap(userInfo.getId());
		}
		Map dicMap = createDicMap(userInfo, isSystemManager);
		dataMap.put("isSM", Boolean.valueOf(isSystemManager));
		dataMap.put("userInfo", userInfo);
		dataMap.put("dicMap", dicMap);
		dataMap.put("rightsMap", rightsMap);
		return dataMap;
	}

	private boolean queryDomainUser(String userCode, String password)
	{
		String account = userCode;
		String userpassword = password;
		Hashtable env = new Hashtable();
		env.put("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");
		env.put("java.naming.provider.url", (new StringBuilder("ldap://")).append(ZonyConfig.getLdapurl()).append(":").append(ZonyConfig.getLdapport()).toString());
		env.put("java.naming.security.authentication", "simple");
		env.put("java.naming.security.principal", (new StringBuilder(String.valueOf(ZonyConfig.getDomainName()))).append("\\").append(account).toString());
		env.put("java.naming.security.credentials", userpassword);
		try
		{
			javax.naming.directory.DirContext ctx = new InitialDirContext(env);
			System.out.println("认证成功");
		}
		catch (AuthenticationException e)
		{
			e.printStackTrace();
			System.out.println("认证失败");
			return false;
		}
		catch (Exception e)
		{
			System.out.println("认证出错：");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public Map createRightsMap(Long userID)
	{
		Map funIdCodeMap = new HashMap();
		Map funCodeMap = new HashMap();
		Map funIdMap = new HashMap();
		List functionList = functionInfoDao.getFunByUserID(userID);
		funIdCodeMap.put(Constant.TOPFUNID, "MODULE");
		FunctionInfo funObj;
		Long parentID;
		for (Iterator iterator = functionList.iterator(); iterator.hasNext(); ((List)funIdMap.get(parentID)).add(funObj.getId()))
		{
			funObj = (FunctionInfo)iterator.next();
			funIdCodeMap.put(funObj.getId(), funObj.getFunCode());
			parentID = funObj.getParentID();
			if (funIdMap.get(parentID) == null)
				funIdMap.put(parentID, new ArrayList());
		}

		ArrayList subCodeList;
		Long parentID;
		for (Iterator iterator1 = funIdMap.entrySet().iterator(); iterator1.hasNext(); funCodeMap.put((String)funIdCodeMap.get(parentID), subCodeList))
		{
			java.util.Map.Entry mapEntry = (java.util.Map.Entry)iterator1.next();
			subCodeList = new ArrayList();
			parentID = (Long)mapEntry.getKey();
			ArrayList subIdList = (ArrayList)mapEntry.getValue();
			Long subID;
			for (Iterator iterator2 = subIdList.iterator(); iterator2.hasNext(); subCodeList.add((String)funIdCodeMap.get(subID)))
				subID = (Long)iterator2.next();

		}

		return funCodeMap;
	}

	public Map createDicMap(UserInfo userObj, boolean isSystemManager)
	{
		Map dicMap = new HashMap();
		List dicList = sysDictionaryDao.getDicList();
		SysDictionary dicObj;
		String typecode;
		for (Iterator iterator = dicList.iterator(); iterator.hasNext(); ((List)dicMap.get(typecode)).add(dicObj))
		{
			dicObj = (SysDictionary)iterator.next();
			typecode = dicObj.getTypeCode();
			if (dicMap.get(typecode) == null)
			{
				dicMap.put(typecode, new ArrayList());
				SysDictionary tmpdic = new SysDictionary();
				tmpdic.setTypeCode(dicObj.getTypeCode());
				tmpdic.setItemName("全部");
				((List)dicMap.get(typecode)).add(tmpdic);
			}
		}

		GroupInfo tmpChannelGroup = new GroupInfo();
		tmpChannelGroup.setGroupName("全部");
		if (isSystemManager)
		{
			List allGroupList = groupInfoDao.getAllGroup();
			allGroupList.add(0, tmpChannelGroup);
			dicMap.put("AllGroup", allGroupList);
		} else
		{
			List channelGroupList = groupInfoDao.getChannelGroupByRight(userObj);
			channelGroupList.add(0, tmpChannelGroup);
			dicMap.put("ChannelGroup", channelGroupList);
		}
		String orgXML = getSelfAndSubOrgByCode(userObj.getOrgCode(), isSystemManager);
		dicMap.put("Organization", orgXML);
		List logEvnetList = logEventDao.getAllLogEvent();
		LogEvent tmpLogEvent = new LogEvent();
		tmpLogEvent.setLogEventName("全部");
		logEvnetList.add(0, tmpLogEvent);
		dicMap.put("LogEvent", logEvnetList);
		List projectNameList = projectNameDao.getAllProjectName();
		ProjectName tmpProjectName = new ProjectName();
		tmpProjectName.setProjectName("全部");
		projectNameList.add(0, tmpProjectName);
		dicMap.put("ProjectName", projectNameList);
		List packageNameList = packageNameDao.getAllPackageName();
		PackageName tmpPackageName = new PackageName();
		tmpPackageName.setPackageName("全部");
		packageNameList.add(0, tmpPackageName);
		dicMap.put("PackageName", packageNameList);
		return dicMap;
	}

	public String getSelfAndSubOrgByCode(String orgCode, boolean isSystemManager)
	{
		String treeXML = "";
		List orgList = new ArrayList();
		if (isSystemManager)
		{
			orgList = organizationDao.getAllOrganization();
			treeXML = organizationLogic.generateOrganizationXMLByList(orgList);
		} else
		{
			Organization orgObj = organizationDao.getOrgByCode(orgCode);
			if (orgObj != null)
			{
				orgList = organizationDao.getSelfAndSubOrgByCodePath(orgObj.getOrgCodePath());
				treeXML = organizationLogic.generateOrganizationXMLByList(orgList, orgObj.getId().toString());
			}
		}
		return treeXML;
	}

	public String createUserBaseRight(String userCode, boolean viewOld)
	{
		return createUserBaseRight(userCode, null, viewOld);
	}

	public String createUserBaseRight(String userCode, String alias, boolean viewOld)
	{
		UserInfo userObj = userInfoDao.getUserInfoByUserCode(userCode);
		return createUserBaseRight(userObj, alias, viewOld);
	}

	public String createUserBaseRight(UserInfo userObj, boolean viewOld)
	{
		return createUserBaseRight(userObj, null, viewOld);
	}

	public String createUserBaseRight(String userCode)
	{
		return createUserBaseRight(userCode, null, false);
	}

	public String createUserBaseRight(String userCode, String alias)
	{
		UserInfo userObj = userInfoDao.getUserInfoByUserCode(userCode);
		return createUserBaseRight(userObj, alias, false);
	}

	public String createUserBaseRight(UserInfo userObj)
	{
		return createUserBaseRight(userObj, null, false);
	}

	public String getBaseSqlByOrgCode(String orgCode)
	{
		Organization orgObj = organizationDao.getOrgByCode(orgCode);
		List orgList = organizationDao.getSelfAndSubOrgByCodePath(orgObj.getOrgCodePath());
		return createHQLByOrgList(orgList, "");
	}

	public String createUserBaseRight(UserInfo userObj, String alias, boolean viewOld)
	{
		if (StringUtils.isEmpty(alias))
			alias = "";
		else
			alias = (new StringBuilder(String.valueOf(alias))).append(".").toString();
		Organization orgObj = organizationDao.getOrgByCode(userObj.getOrgCode());
		List orgList = organizationDao.getSelfAndSubOrgByCodePath(orgObj.getOrgCodePath());
		String baseHQL = createHQLByOrgList(orgList, alias);
		if (!userObj.getGroupCode().equals("Y"))
			if (userObj.getGroupCode().equals("Y-A"))
				baseHQL = (new StringBuilder(String.valueOf(baseHQL))).append(" and ").append(alias).append("policyType='").append("1").append("'").toString();
			else
			if (userObj.getGroupCode().equals("Y-B"))
				baseHQL = (new StringBuilder(String.valueOf(baseHQL))).append(" and ").append(alias).append("policyType='").append("2").append("'").toString();
			else
				baseHQL = (new StringBuilder(String.valueOf(baseHQL))).append(" and ").append(alias).append("chn='").append(userObj.getGroupCode()).append("'").toString();
		if (!viewOld)
			baseHQL = (new StringBuilder(String.valueOf(baseHQL))).append(" and ").append(alias).append("newID is null").toString();
		baseHQL = (new StringBuilder(String.valueOf(baseHQL))).append(" and ( 1=1 or ").append(alias).append("checkStatus is null)").toString();
		return baseHQL;
	}

	public String createHQLByOrgList(List orgList, String alias)
	{
		String returnHQL = (new StringBuilder(" ")).append(alias).append("brcd in (").toString();
		for (Iterator iterator = orgList.iterator(); iterator.hasNext();)
		{
			Organization orgObj = (Organization)iterator.next();
			returnHQL = (new StringBuilder(String.valueOf(returnHQL))).append("'").append(orgObj.getOrgCode()).append("',").toString();
		}

		returnHQL = (new StringBuilder(String.valueOf(returnHQL))).append("'NULL')").toString();
		return returnHQL;
	}
}
