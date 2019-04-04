// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   OrganizationTreeNode.java

package com.zony.app.model.treenode;

import com.zony.common.util.tree.TreeNode;

public class OrganizationTreeNode extends TreeNode
{

	private String orgName;
	private String orgCode;
	private String orgCodePath;
	private String secondOrgCode;

	public OrganizationTreeNode()
	{
	}

	public OrganizationTreeNode(String id, String label)
	{
		super(id, label);
	}

	public String getOrgName()
	{
		return orgName;
	}

	public void setOrgName(String orgName)
	{
		this.orgName = orgName;
	}

	public String getOrgCode()
	{
		return orgCode;
	}

	public void setOrgCode(String orgCode)
	{
		this.orgCode = orgCode;
	}

	public String getOrgCodePath()
	{
		return orgCodePath;
	}

	public void setOrgCodePath(String orgCodePath)
	{
		this.orgCodePath = orgCodePath;
	}

	public String getSecondOrgCode()
	{
		return secondOrgCode;
	}

	public void setSecondOrgCode(String secondOrgCode)
	{
		this.secondOrgCode = secondOrgCode;
	}

	public void setAsRoot()
	{
	}
}
