// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TreeNode.java

package com.zony.common.util.tree;

import java.util.ArrayList;
import java.util.List;

public abstract class TreeNode
{

	private String id;
	private String label;
	private String parentID;
	private List nodeList;

	public abstract void setAsRoot();

	public TreeNode()
	{
		nodeList = new ArrayList();
	}

	public TreeNode(String label)
	{
		nodeList = new ArrayList();
		this.label = label;
	}

	public TreeNode(String id, String label)
	{
		nodeList = new ArrayList();
		this.id = id;
		this.label = label;
	}

	public TreeNode(String id, String label, String parentID)
	{
		nodeList = new ArrayList();
		this.id = id;
		this.label = label;
		this.parentID = parentID;
	}

	public void addChild(TreeNode node)
	{
		if (node != null)
			nodeList.add(node);
	}

	public void addChildAll(List nodes)
	{
		if (nodes != null && nodes.size() > 0)
			nodeList.addAll(nodes);
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getLabel()
	{
		return label;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

	public String getParentID()
	{
		return parentID;
	}

	public void setParentID(String parentID)
	{
		this.parentID = parentID;
	}

	public List getNodeList()
	{
		return nodeList;
	}

	public void setNodeList(List nodeList)
	{
		this.nodeList = nodeList;
	}
}
