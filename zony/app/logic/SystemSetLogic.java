// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SystemSetLogic.java

package com.zony.app.logic;

import com.zony.app.dao.*;
import com.zony.app.domain.*;
import com.zony.app.model.treenode.OrganizationTreeNodeConverter;
import com.zony.app.thread.PolicyImportThread;
import com.zony.common.config.ZonyConfig;
import com.zony.common.util.PageUtil;
import com.zony.common.util.tree.TreeConverter;
import java.util.*;
import org.springframework.util.StringUtils;

// Referenced classes of package com.zony.app.logic:
//			OrganizationLogic

public class SystemSetLogic
{

	public static final String CLOSEING = "0";
	public static final String OPENING = "1";
	private UserInfoDao userInfoDao;
	private VuserInfoDao vuserInfoDao;
	private OrganizationDao organizationDao;
	private SysFlagDao sysFlagDao;
	private OrganizationLogic organizationLogic;
	private RoleRelationDao roleRelationDao;
	private GroupInfoDao groupInfoDao;
	private ProjectNameDao projectNameDao;

	public SystemSetLogic()
	{
	}

	public Long addUserInfo(UserInfo userInfo)
	{
		UserInfo user = userInfoDao.checkUserInfoUniqueByUserName(userInfo);
		if (user == null)
			return userInfoDao.saveUserInfo(userInfo);
		else
			return Long.valueOf(0L);
	}

	public UserInfo getUserInfoById(Long id)
	{
		return userInfoDao.getUserInfoById(id);
	}

	public List getUserInfoByOrgCode(String orgCode)
	{
		return vuserInfoDao.getUserInfoByOrgCode(orgCode);
	}

	public UserInfo getUserInfoByUserCode(String userCode)
	{
		return userInfoDao.getUserInfoByUserCode(userCode);
	}

	public List searchUserInfo(Map values, PageUtil page)
	{
		return userInfoDao.searchUserInfo(values, page);
	}

	public Long updUserInfo(UserInfo userInfo)
	{
		UserInfo user = userInfoDao.checkUserInfoUniqueByUserName(userInfo);
		if (user == null)
		{
			userInfoDao.update(userInfo);
			return new Long(1L);
		} else
		{
			return Long.valueOf(0L);
		}
	}

	public void delUserInfo(Long id)
	{
		roleRelationDao.delRoleRelationByUserID(id);
		userInfoDao.deleteById(id);
	}

	public List getAllOrganization()
		throws Exception
	{
		return organizationDao.getAllOrganization();
	}

	public String generateOrganizationXMLByList(List modelList)
	{
		String organizationXML = "";
		String rootId = "1";
		TreeConverter treeConverter = new TreeConverter(new OrganizationTreeNodeConverter(), "1");
		organizationXML = organizationLogic.generateOrganizationXMLByList(modelList, "1");
		return organizationXML;
	}

	public Long addSysFlag(SysFlag sysFlag)
	{
		return sysFlagDao.saveSysFlag(sysFlag);
	}

	public SysFlag getCurrentImportSwitch()
	{
		SysFlag sysFlag = sysFlagDao.getSysFlagByFlagName("PolicyImportSwitch");
		return sysFlag;
	}

	public SysFlag getCurrentSendSwitch()
	{
		SysFlag sysFlag = sysFlagDao.getSysFlagByFlagName("PolicySendSwitch");
		return sysFlag;
	}

	public void switchPolicyImport(String switchValue)
	{
		if (!StringUtils.isEmpty(switchValue))
		{
			PolicyImportThread.CHECK_POLICYNOTICE_FLAG = switchValue;
			sysFlagDao.updateFlagValue("PolicyImportSwitch", switchValue);
		}
	}

	public void switchPolicySend(String switchValue)
	{
		if (!StringUtils.isEmpty(switchValue))
		{
			PolicyImportThread.SEND_POLICYNOTICE_FLAG = switchValue;
			sysFlagDao.updateFlagValue("PolicySendSwitch", switchValue);
		}
	}

	public void updSysFlag(SysFlag sysFlag)
	{
		sysFlagDao.update(sysFlag);
	}

	public SysFlag getSysFlagByFlagName(String flagName)
	{
		return sysFlagDao.getSysFlagByFlagName(flagName);
	}

	public void deployTMProjectCode(List list)
	{
		String array = "";
		String value = "";
		for (int i = 0; i < list.size(); i++)
		{
			array = (new StringBuilder(String.valueOf(array))).append((String)list.get(i)).toString();
			array = (new StringBuilder(String.valueOf(array))).append(",").toString();
		}

		int num = array.length() - 1;
		if (array != null && !"".equals(array))
			value = array.substring(0, num);
		ZonyConfig.deployTMProjectCode(value);
	}

	public List getTMProjectCode()
	{
		List list = new ArrayList();
		List list2 = ZonyConfig.getTMProjectCode();
		for (int i = 0; i < list2.size(); i++)
		{
			ProjectName projectName = new ProjectName();
			projectName.setProjectName((String)list2.get(i));
			list.add(projectName);
		}

		return list;
	}

	public void deployTMChnCode(List list)
	{
		String array = "";
		String value = "";
		for (int i = 0; i < list.size(); i++)
		{
			array = (new StringBuilder(String.valueOf(array))).append((String)list.get(i)).toString();
			array = (new StringBuilder(String.valueOf(array))).append(",").toString();
		}

		int num = array.length() - 1;
		if (array != null && "" != array)
			value = array.substring(0, num);
		ZonyConfig.deployTMChnCode(value);
	}

	public List getTMChnCode()
	{
		List list = new ArrayList();
		List list2 = ZonyConfig.getTMChnCode();
		for (int i = 0; i < list2.size(); i++)
		{
			GroupInfo groupInfo = new GroupInfo();
			groupInfo.setGroupCode((String)list2.get(i));
			list.add(groupInfo);
		}

		return list;
	}

	public List getChannelGroup()
		throws Exception
	{
		List list = groupInfoDao.getChannelGroup();
		return list;
	}

	public List getProjectName()
		throws Exception
	{
		List list = projectNameDao.getAllProjectName();
		return list;
	}

	public List getProjectNameListByCon(String projectName)
		throws Exception
	{
		List list = projectNameDao.getProjectNameListByCon(projectName);
		return list;
	}

	public String getTranPassTime()
	{
		return ZonyConfig.getTranPassTime();
	}

	public void deployTranPassTime(String transferPassTime)
	{
		double num = Double.parseDouble(transferPassTime);
		int passTime = (int)num;
		ZonyConfig.deployTranPassTime(String.valueOf(passTime));
	}

	public String getTranAgainTime()
	{
		return ZonyConfig.getTranAgainTime();
	}

	public void deployTranAgainTime(String transferagainTime)
	{
		double num = Double.parseDouble(transferagainTime);
		int againTime = (int)num;
		ZonyConfig.deployTranAgainTime(String.valueOf(againTime));
	}

	public String getTranCount()
	{
		return ZonyConfig.getTranCount();
	}

	public void deployTranCount(String transferCount)
	{
		double num = Double.parseDouble(transferCount);
		int count = (int)num;
		ZonyConfig.deployTranCount(String.valueOf(count));
	}
}
