// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RightManageLogic.java

package com.zony.app.logic;

import com.zony.app.dao.*;
import com.zony.app.domain.*;
import com.zony.app.model.VroleFunModel;
import com.zony.app.model.treenode.VroleFunctionInfoTreeNode;
import com.zony.app.model.treenode.VroleFunctionInfoTreeNodeConverter;
import com.zony.common.util.tree.TreeConverter;
import com.zony.common.util.tree.TreeUtils;
import java.util.*;

public class RightManageLogic
{

	public static final String NOT_SET_RIGHT = "0";
	public static final String SET_RIGHT = "1";
	private RoleInfoDao roleInfoDao;
	private RoleRelationDao roleRelationDao;
	private RoleRightDao roleRightDao;
	private FunctionInfoDao functionInfoDao;
	private VroleFunDao vroleFunDao;
	private VroleUserDao vroleUserDao;
	private VuserInfoDao vuserInfoDao;

	public RightManageLogic()
	{
	}

	public Long addRoleInfo(RoleInfo roleInfo)
	{
		if (roleInfoDao.checkRoleInfoByRoleName(roleInfo) != null)
			return Long.valueOf(0L);
		if (roleInfo.getRoleName() != null)
			roleInfo.setRoleName(roleInfo.getRoleName().trim());
		if (roleInfo.getNote() != null)
			roleInfo.setNote(roleInfo.getNote().trim());
		return roleInfoDao.saveRoleInfo(roleInfo);
	}

	public Long updRoleInfo(RoleInfo roleInfo)
	{
		if (roleInfoDao.checkRoleInfoByRoleName(roleInfo) != null)
			return Long.valueOf(0L);
		if (roleInfo.getRoleName() != null)
			roleInfo.setRoleName(roleInfo.getRoleName().trim());
		if (roleInfo.getNote() != null)
			roleInfo.setNote(roleInfo.getNote().trim());
		roleInfoDao.update(roleInfo);
		return new Long(1L);
	}

	public void delRoleInfo(Long roleId)
	{
		roleRelationDao.delRoleRelationByRoleId(roleId);
		roleRightDao.delRoleRightByRoleId(roleId);
		roleInfoDao.deleteById(roleId);
	}

	public List getAllRoleInfoModel()
		throws Exception
	{
		return roleInfoDao.getAllRoleInfoModel();
	}

	public Long addRoleRelation(Long roleId, Long userId)
	{
		RoleRelation roleRelation = new RoleRelation();
		roleRelation.setRoleID(roleId);
		roleRelation.setUserID(userId);
		return roleRelationDao.saveRoleRelation(roleRelation);
	}

	public int addUserToRole(Long roleID, String userStrs)
	{
		String userIDtemp[] = userStrs.split(",");
		List userIDs = new ArrayList();
		String as[];
		int l = (as = userIDtemp).length;
		for (int k = 0; k < l; k++)
		{
			String element = as[k];
			userIDs.add(new Long(Integer.parseInt(element)));
		}

		List relationList = roleRelationDao.getRoleRelationByRoleID(roleID);
		int isExiccount = 0;
		for (int i = 0; i < relationList.size(); i++)
		{
			for (int j = 0; j < userIDs.size(); j++)
			{
				String reslUserId = ((RoleRelation)relationList.get(i)).getUserID().toString();
				String userID = ((Long)userIDs.get(j)).toString();
				if (reslUserId.equals(userID))
				{
					isExiccount++;
					userIDs.remove(j);
				}
			}

		}

		if (userIDs.size() > 0)
		{
			for (int i = 0; i < userIDs.size(); i++)
			{
				RoleRelation roleRelation = new RoleRelation();
				roleRelation.setRoleID(roleID);
				roleRelation.setUserID((Long)userIDs.get(i));
				roleRelationDao.save(roleRelation);
			}

		}
		return isExiccount;
	}

	public void removeUserFromRole(Long roleID, String userIDStrs)
	{
		String userIDTemp[] = userIDStrs.split(",");
		List userIDs = new ArrayList();
		String as[];
		int j = (as = userIDTemp).length;
		for (int i = 0; i < j; i++)
		{
			String element = as[i];
			userIDs.add(new Long(Integer.parseInt(element)));
		}

		roleRelationDao.removeUserFromRole(roleID, userIDs);
	}

	public List getRoleRelationByRoleID(Long roleID)
	{
		return roleRelationDao.getRoleRelationByRoleID(roleID);
	}

	public List getAllRoleRelationModel()
		throws Exception
	{
		return roleRelationDao.getAllRoleRelationModel();
	}

	public Long addRoleRight(RoleRight roleRight)
	{
		return roleRightDao.saveRoleRight(roleRight);
	}

	public List originalRoleRightByRoleId(Long roleId)
		throws Exception
	{
		List functionList = functionInfoDao.getFunctionInfoModel();
		List vroleFunlOriginalList = vroleFunDao.getVroleFunByRoleId(roleId);
		List vroleFunlNewlList = new ArrayList();
		RoleInfo roleInfo = (RoleInfo)roleInfoDao.getById(roleId);
		VroleFunModel vroleFunModel = null;
		for (int i = 0; i < functionList.size(); i++)
		{
			vroleFunModel = new VroleFunModel();
			FunctionInfo functionInfo = (FunctionInfo)functionList.get(i);
			vroleFunModel.setId(functionInfo.getId());
			vroleFunModel.setRoleID(roleId);
			vroleFunModel.setRoleName(roleInfo.getRoleName());
			vroleFunModel.setFunName(functionInfo.getFunName());
			vroleFunModel.setParentID(functionInfo.getParentID());
			vroleFunModel.setFunID(functionInfo.getId());
			vroleFunModel.setIsSetting("0");
			for (int j = 0; j < vroleFunlOriginalList.size(); j++)
			{
				VroleFun originaVroleFun = (VroleFun)vroleFunlOriginalList.get(j);
				if (functionInfo.getId().equals(originaVroleFun.getFunID()))
					vroleFunModel.setIsSetting("1");
			}

			vroleFunlNewlList.add(vroleFunModel);
		}

		return vroleFunlNewlList;
	}

	public String generateAllFunctionInfoXMLByRoleID(Long roleId)
		throws Exception
	{
		List modelList = originalRoleRightByRoleId(roleId);
		return generateFunctionInfoXMLByList(modelList);
	}

	public String generateFunctionInfoXMLByList(List modelList)
	{
		String functionInfoXML = "";
		String rootId = "0";
		TreeConverter treeConverter = new TreeConverter(new VroleFunctionInfoTreeNodeConverter(), "0");
		VroleFunctionInfoTreeNode rootNode = new VroleFunctionInfoTreeNode("0", "系统功能");
		int isSetingCount = 0;
		for (Iterator iterator = modelList.iterator(); iterator.hasNext();)
		{
			VroleFunModel role = (VroleFunModel)iterator.next();
			if (role.getIsSetting().equals("1"))
				isSetingCount++;
		}

		if (isSetingCount > 0)
			rootNode.setIsSetting("1");
		else
			rootNode.setIsSetting("0");
		functionInfoXML = TreeUtils.convertSameClassTree(modelList, treeConverter, rootNode);
		return functionInfoXML;
	}

	public List getRoleUserByRoleId(Long roleId)
		throws Exception
	{
		return vroleUserDao.getRoleUserByRoleId(roleId);
	}

	public List getUserInfoByOrgCodeAndRoleId(String orgCode, String roleId)
	{
		return vuserInfoDao.getUserInfoByOrgCodeAndRoleId(orgCode, roleId);
	}

	public void authorization(Long roleId, String functionIDS)
		throws Exception
	{
		String userIDtemp[] = functionIDS.split(",");
		List functionIDList = new ArrayList();
		String as[];
		int j = (as = userIDtemp).length;
		for (int i = 0; i < j; i++)
		{
			String element = as[i];
			functionIDList.add(new Long(Integer.parseInt(element)));
		}

		roleRightDao.delRoleRightByRoleId(roleId);
		RoleRight roleRight;
		for (Iterator iterator = functionIDList.iterator(); iterator.hasNext(); roleRightDao.save(roleRight))
		{
			Long funID = (Long)iterator.next();
			roleRight = new RoleRight();
			roleRight.setFunID(funID);
			roleRight.setRoleID(roleId);
		}

	}

	public List getAllVroleFunModel()
		throws Exception
	{
		return vroleFunDao.getAllVroleFunModel();
	}

	public List getVroleFunModelByRoleId(Long roleId)
		throws Exception
	{
		return vroleFunDao.getVroleFunModelByRoleId(roleId);
	}
}
