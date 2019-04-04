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
			resultModel.setError("��ɫ���ظ����½�ʧ�ܣ�");
		} else
		{
			resultModel.setResult(1);
			resultModel.setInfo("�½���ɫ�ɹ���");
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
			resultModel.setInfo("��ɫ�������޸�ʧ�ܣ�");
			resultModel.setData("-1");
		} else
		{
			resultModel.setResult(1);
			resultModel.setInfo("�޸Ľ�ɫ�ɹ���");
			resultModel.setData("0");
		}
		return resultModel.getValue();
	}

	public ASObject delRoleInfo(Long id)
	{
		ResultModel resultModel = new ResultModel();
		rightManageLogic.delRoleInfo(id);
		resultModel.setResult(1);
		resultModel.setInfo("ɾ����ɫ�ɹ���");
		return resultModel.getValue();
	}

	public ASObject addUserToRole(String roleId, String userIDs)
		throws Exception
	{
		ResultModel resultModel = new ResultModel();
		rightManageLogic.addUserToRole(new Long(Integer.parseInt(roleId)), userIDs);
		resultModel.setInfo("�����Ա�ɹ���");
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
		resultModel.setInfo("�Ƴ���Ա��Ϣ�ɹ�!");
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
		resultModel.setInfo("��ɫ��Ȩ�ɹ�!");
		resultModel.setResult(1);
		return resultModel.getValue();
	}
}
