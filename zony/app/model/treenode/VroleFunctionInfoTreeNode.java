// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   VroleFunctionInfoTreeNode.java

package com.zony.app.model.treenode;

import com.zony.common.util.tree.TreeNode;

public class VroleFunctionInfoTreeNode extends TreeNode
{

	private String funName;
	private String funCode;
	private String funNote;
	private String isSetting;

	public VroleFunctionInfoTreeNode()
	{
	}

	public VroleFunctionInfoTreeNode(String id, String label)
	{
		super(id, label);
	}

	public String getIsSetting()
	{
		return isSetting;
	}

	public void setIsSetting(String isSetting)
	{
		this.isSetting = isSetting;
	}

	public String getFunName()
	{
		return funName;
	}

	public void setFunName(String funName)
	{
		this.funName = funName;
	}

	public String getFunCode()
	{
		return funCode;
	}

	public void setFunCode(String funCode)
	{
		this.funCode = funCode;
	}

	public String getFunNote()
	{
		return funNote;
	}

	public void setFunNote(String funNote)
	{
		this.funNote = funNote;
	}

	public void setAsRoot()
	{
	}
}
