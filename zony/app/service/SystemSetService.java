// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SystemSetService.java

package com.zony.app.service;

import com.zony.app.domain.*;
import com.zony.app.logic.PolicySendConfigLogic;
import com.zony.app.logic.SystemSetLogic;
import com.zony.app.model.ResultModel;
import com.zony.app.thread.PolicyImportThread;
import com.zony.common.util.FlexInvokeUtil;
import flex.messaging.io.ArrayCollection;
import flex.messaging.io.amf.ASObject;
import java.util.*;

public class SystemSetService
{

	private SystemSetLogic systemSetLogic;
	PolicySendConfigLogic policySendConfigLogic;

	public SystemSetService()
	{
	}

	public ASObject addUserInfo(ASObject asObj)
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		UserInfo userInfo = (UserInfo)FlexInvokeUtil.getFormAsObj(com/zony/app/domain/UserInfo, asObj);
		Long id = systemSetLogic.addUserInfo(userInfo);
		if (id == null)
		{
			resultModel.setResult(2);
			resultModel.setError("人员信息录入失败！");
		} else
		if (id.longValue() == 0L)
		{
			resultModel.setResult(2);
			resultModel.setError("人员域账户名重复，录入失败！");
		} else
		{
			resultModel.setResult(1);
			resultModel.setInfo("人员信息录入成功！");
		}
		return resultModel.getValue();
	}

	public ASObject updUserInfo(ASObject asObj)
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		UserInfo userInfo = (UserInfo)FlexInvokeUtil.getFormAsObj(com/zony/app/domain/UserInfo, asObj);
		Long id = systemSetLogic.updUserInfo(userInfo);
		if (id.longValue() == 0L)
		{
			resultModel.setResult(2);
			resultModel.setError("人员姓名重复，修改失败！");
		} else
		{
			resultModel.setResult(1);
			resultModel.setInfo("人员信息修改成功！");
		}
		return resultModel.getValue();
	}

	public ASObject delUserInfo(Long id)
	{
		ResultModel resultModel = new ResultModel();
		systemSetLogic.delUserInfo(id);
		resultModel.setResult(1);
		resultModel.setInfo("人员信息删除成功！");
		return resultModel.getValue();
	}

	public ASObject getUserInfoByOrgCode(String orgCode)
	{
		ResultModel resultModel = new ResultModel();
		List list = systemSetLogic.getUserInfoByOrgCode(orgCode);
		resultModel.setData(list);
		resultModel.setResult(1);
		return resultModel.getValue();
	}

	public ASObject createOrganizationTree()
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		List modelList = systemSetLogic.getAllOrganization();
		String organizationXML = systemSetLogic.generateOrganizationXMLByList(modelList);
		resultModel.setData(organizationXML);
		resultModel.setResult(1);
		return resultModel.getValue();
	}

	public ASObject getCurrentSwitch()
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		String importSwitch = PolicyImportThread.CHECK_POLICYNOTICE_FLAG;
		String sendSwitch = PolicyImportThread.SEND_POLICYNOTICE_FLAG;
		resultModel.setData(new String[] {
			importSwitch, sendSwitch
		});
		resultModel.setResult(1);
		return resultModel.getValue();
	}

	public ASObject switchPolicyImport(String switchValue)
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		systemSetLogic.switchPolicyImport(switchValue);
		String importSwitch = PolicyImportThread.CHECK_POLICYNOTICE_FLAG;
		String sendSwitch = PolicyImportThread.SEND_POLICYNOTICE_FLAG;
		resultModel.setData(new String[] {
			importSwitch, sendSwitch
		});
		resultModel.setResult(1);
		resultModel.setInfo("保单导入开关切换成功！");
		return resultModel.getValue();
	}

	public ASObject switchPolicySend(String switchValue)
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		systemSetLogic.switchPolicySend(switchValue);
		String importSwitch = PolicyImportThread.CHECK_POLICYNOTICE_FLAG;
		String sendSwitch = PolicyImportThread.SEND_POLICYNOTICE_FLAG;
		resultModel.setData(new String[] {
			importSwitch, sendSwitch
		});
		resultModel.setResult(1);
		resultModel.setInfo("保单自动发送开关切换成功！");
		return resultModel.getValue();
	}

	public ASObject getPolicySendConfigListCon(ASObject asObj)
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		PolicySendConfig policySendConfig = (PolicySendConfig)FlexInvokeUtil.getFormAsObj(com/zony/app/domain/PolicySendConfig, asObj);
		List configList = policySendConfigLogic.getListBySameConfigCon(policySendConfig);
		resultModel.setData(configList);
		resultModel.setResult(1);
		return resultModel.getValue();
	}

	public ASObject addPolicySendConfig(ASObject asObj)
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		PolicySendConfig policySendConfig = (PolicySendConfig)FlexInvokeUtil.getFormAsObj(com/zony/app/domain/PolicySendConfig, asObj);
		List configList = policySendConfigLogic.getSameConfigCon(policySendConfig);
		if (configList == null || configList.size() == 0)
		{
			policySendConfigLogic.addPolicySendConfig(policySendConfig);
			resultModel.setInfo("新增配置成功！");
			resultModel.setResult(1);
		} else
		{
			resultModel.setError("新增配置失败，已存在相同配置。");
			resultModel.setResult(2);
		}
		return resultModel.getValue();
	}

	public ASObject updateSendConfig(ASObject asObj)
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		PolicySendConfig policySendConfig = (PolicySendConfig)FlexInvokeUtil.getFormAsObj(com/zony/app/domain/PolicySendConfig, asObj);
		List configList = policySendConfigLogic.getSameConfigCon(policySendConfig);
		if (configList == null || configList.size() == 0)
		{
			policySendConfigLogic.updatePolicySendConfig(policySendConfig);
			resultModel.setInfo("更新配置成功！");
			resultModel.setResult(1);
		} else
		{
			resultModel.setError("更新配置失败，已存在相同配置。");
			resultModel.setResult(2);
		}
		return resultModel.getValue();
	}

	public ASObject delPolicySendConfig(ArrayCollection asObj)
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		List idList = FlexInvokeUtil.getFormAsLongList(asObj);
		policySendConfigLogic.delPolicySendConfig(idList);
		resultModel.setInfo("删除配置成功！");
		resultModel.setResult(1);
		return resultModel.getValue();
	}

	public ASObject getConfigMap()
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		Map configMap = policySendConfigLogic.getConfigMap();
		resultModel.setData(configMap);
		resultModel.setResult(1);
		return resultModel.getValue();
	}

	public ASObject deployTMProjectCode(ArrayCollection asList)
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		List list = FlexInvokeUtil.getFormAsStringList(asList);
		systemSetLogic.deployTMProjectCode(list);
		resultModel.setResult(1);
		resultModel.setInfo("设置配置成功！");
		return resultModel.getValue();
	}

	public ASObject getTMProjectCode()
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		List list = systemSetLogic.getTMProjectCode();
		resultModel.setData(list);
		resultModel.setResult(1);
		return resultModel.getValue();
	}

	public ASObject deployTMChnCode(ArrayCollection asList)
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		List list = FlexInvokeUtil.getFormAsStringList(asList);
		systemSetLogic.deployTMChnCode(list);
		resultModel.setResult(1);
		resultModel.setInfo("设置配置成功！");
		return resultModel.getValue();
	}

	public ASObject getTMChnCode()
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		List list = systemSetLogic.getTMChnCode();
		resultModel.setData(list);
		resultModel.setResult(1);
		return resultModel.getValue();
	}

	public ASObject getChannelGroup()
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		List list = systemSetLogic.getChannelGroup();
		resultModel.setData(list);
		resultModel.setResult(1);
		return resultModel.getValue();
	}

	public ASObject getProjectName()
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		List list = systemSetLogic.getProjectName();
		resultModel.setData(list);
		resultModel.setResult(1);
		return resultModel.getValue();
	}

	public ASObject getProjectNameListByCon(ASObject asObj)
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		ProjectName projectName = (ProjectName)FlexInvokeUtil.getFormAsObj(com/zony/app/domain/ProjectName, asObj);
		List list = systemSetLogic.getProjectNameListByCon(projectName.getProjectName());
		resultModel.setData(list);
		resultModel.setResult(1);
		return resultModel.getValue();
	}

	public ASObject deployTranPassTime(String tranPassTime)
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		systemSetLogic.deployTranPassTime(tranPassTime);
		resultModel.setInfo("设置成功");
		resultModel.setResult(1);
		return resultModel.getValue();
	}

	public ASObject deployTranAgainTime(String tranAgainTime)
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		systemSetLogic.deployTranAgainTime(tranAgainTime);
		resultModel.setInfo("设置成功");
		resultModel.setResult(1);
		return resultModel.getValue();
	}

	public ASObject deployTranCount(String tranCount)
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		systemSetLogic.deployTranCount(tranCount);
		resultModel.setInfo("设置成功");
		resultModel.setResult(1);
		return resultModel.getValue();
	}

	public ASObject getTransfer()
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		Map info = new HashMap();
		String tranferPassTime = systemSetLogic.getTranPassTime();
		info.put("transferPassTime", tranferPassTime);
		String transferAgainTime = systemSetLogic.getTranAgainTime();
		info.put("transferAgainTime", transferAgainTime);
		String transferCount = systemSetLogic.getTranCount();
		info.put("transferCount", transferCount);
		resultModel.setData(info);
		resultModel.setResult(1);
		return resultModel.getValue();
	}
}
