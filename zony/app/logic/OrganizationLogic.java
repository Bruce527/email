// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   OrganizationLogic.java

package com.zony.app.logic;

import com.zony.app.dao.OrganizationDao;
import com.zony.app.domain.Organization;
import com.zony.app.model.treenode.OrganizationTreeNode;
import com.zony.app.model.treenode.OrganizationTreeNodeConverter;
import com.zony.common.util.tree.TreeConverter;
import com.zony.common.util.tree.TreeUtils;
import java.util.List;

public class OrganizationLogic
{

	private OrganizationDao organizationDao;
	public static final String ORGANIZATION_ROOTID = "1";
	public static final String FUNCTIONINFO_ROOTID = "0";

	public OrganizationLogic()
	{
	}

	public List getAllOrganization()
		throws Exception
	{
		return organizationDao.getAllOrganization();
	}

	public String generateOrganizationXMLByList(List orgList)
	{
		return generateOrganizationXMLByList(orgList, "1");
	}

	public String generateOrganizationXMLByList(List orgList, String rootId)
	{
		OrganizationTreeNode rootNode = new OrganizationTreeNode(rootId, ((Organization)orgList.get(0)).getOrgName());
		rootNode.setOrgCode(((Organization)orgList.get(0)).getOrgCode());
		TreeConverter treeConverter = new TreeConverter(new OrganizationTreeNodeConverter(), rootId);
		String organizationXML = TreeUtils.convertSameClassTree(orgList, treeConverter, rootNode);
		return organizationXML;
	}
}
