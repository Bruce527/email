// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RightManageService.java

package com.zony.app.service;

import com.zony.app.domain.RoleInfo;
import com.zony.app.logic.FunctionInfoLogic;
import com.zony.app.logic.RightManageLogic;
import com.zony.app.model.ResultModel;
import com.zony.common.util.FlexInvokeUtil;
import flex.messaging.io.amf.ASObject;

public class RightManageService
{

	private RightManageLogic rightManageLogic;
	private FunctionInfoLogic FunctionInfoLogic;

	public RightManageService()
	{
	}

	public ASObject addRoleInfo(ASObject asObj)
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		RoleInfo roleInfo = (RoleInfo)FlexInvokeUtil.getFormAsObj(com/zony/app/domain/RoleInfo, asObj);
		Long id = rightManageLogic.addRoleInfo(roleInfo);
		if (id.longValue() == 0L)
		{
			resultModel.setResult(2);
			resultModel.setError("角色名重复，新建失败！");
		} else
		{
			resultModel.setResult(1);
			resultModel.setInfo("新建角色成功！");
		}
		return resultModel.getValue();
	}

	public ASObject updRoleInfo(ASObject asObj)
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		RoleInfo roleInfo = (RoleInfo)FlexInvokeUtil.getFormAsObj(com/zony/app/domain/RoleInfo, asObj);
		Long id = rightManageLogic.updRoleInfo(roleInfo);
		if (id.longValue() == 0L)
		{
			resultModel.setResult(1);
			resultModel.setInfo("角色重名，修改失败！");
			resultModel.setData("-1");
		} else
		{
			resultModel.setResult(1);
			resultModel.setInfo("修改角色成功！");
			resultModel.setData("0");
		}
		return resultModel.getValue();
	}

	public ASObject delRoleInfo(Long id)
	{
		ResultModel resultModel = new ResultModel();
		rightManageLogic.delRoleInfo(id);
		resultModel.setResult(1);
		resultModel.setInfo("删除角色成功！");
		return resultModel.getValue();
	}

	public ASObject addUserToRole(String roleId, String userIDs)
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		rightManageLogic.addUserToRole(new Long(Integer.parseInt(roleId)), userIDs);
		resultModel.setInfo("添加人员成功！");
		resultModel.setResult(1);
		return resultModel.getValue();
	}

	public ASObject generateFunctionInfoXML(Long roleId)
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		String XML = rightManageLogic.generateAllFunctionInfoXMLByRoleID(roleId);
		resultModel.setData(XML);
		resultModel.setResult(1);
		return resultModel.getValue();
	}

	public ASObject removeUserFromRole(Long roleId, String userIDs)
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		rightManageLogic.removeUserFromRole(roleId, userIDs);
		resultModel.setInfo("移出人员信息成功!");
		resultModel.setResult(1);
		return resultModel.getValue();
	}

	public ASObject addRoleRelation(Long roleId, Long userId)
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		rightManageLogic.addRoleRelation(roleId, userId);
		resultModel.setResult(1);
		return resultModel.getValue();
	}

	public ASObject getRoleUserByRoleId(Long roleId)
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		java.util.List list = rightManageLogic.getRoleUserByRoleId(roleId);
		resultModel.setData(list);
		resultModel.setResult(1);
		return resultModel.getValue();
	}

	public ASObject getUserInfoByOrgCodeAndRoleId(String orgCode, String roleId)
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		java.util.List list = rightManageLogic.getUserInfoByOrgCodeAndRoleId(orgCode, roleId);
		resultModel.setData(list);
		resultModel.setResult(1);
		return resultModel.getValue();
	}

	public ASObject showOglRoleRightByRoleId(Long userID)
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		java.util.List list = rightManageLogic.originalRoleRightByRoleId(userID);
		resultModel.setData(list);
		resultModel.setResult(1);
		return resultModel.getValue();
	}

	public ASObject getAllRoleInfoModel()
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		java.util.List roleInfoList = rightManageLogic.getAllRoleInfoModel();
		resultModel.setData(roleInfoList);
		resultModel.setResult(1);
		return resultModel.getValue();
	}

	public ASObject authorization(Long roleId, String functionIDs)
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		rightManageLogic.authorization(roleId, functionIDs);
		resultModel.setInfo("角色授权成功!");
		resultModel.setResult(1);
		return resultModel.getValue();
	}
}
