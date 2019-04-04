// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FunctionInfoTreeNodeConverter.java

package com.zony.app.model.treenode;

import com.zony.app.model.FunctionInfoModel;
import com.zony.common.util.tree.TreeNode;
import com.zony.common.util.tree.TreeNodeConverter;

// Referenced classes of package com.zony.app.model.treenode:
//			FunctionInfoTreeNode

public class FunctionInfoTreeNodeConverter extends TreeNodeConverter
{

	public FunctionInfoTreeNodeConverter()
	{
	}

	public TreeNode doConvert(FunctionInfoModel functionInfoModel)
	{
		FunctionInfoTreeNode node = new FunctionInfoTreeNode();
		copyProperty(node, functionInfoModel);
		node.setLabel(functionInfoModel.getFunName());
		return node;
	}

	public volatile TreeNode doConvert(Object obj)
	{
		return doConvert((FunctionInfoModel)obj);
	}
}
