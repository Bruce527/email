// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   VroleFunctionInfoTreeNodeConverter.java

package com.zony.app.model.treenode;

import com.zony.app.model.VroleFunModel;
import com.zony.common.util.tree.TreeNode;
import com.zony.common.util.tree.TreeNodeConverter;

// Referenced classes of package com.zony.app.model.treenode:
//			VroleFunctionInfoTreeNode

public class VroleFunctionInfoTreeNodeConverter extends TreeNodeConverter
{

	public VroleFunctionInfoTreeNodeConverter()
	{
	}

	public TreeNode doConvert(VroleFunModel functionInfoModel)
	{
		VroleFunctionInfoTreeNode node = new VroleFunctionInfoTreeNode();
		copyProperty(node, functionInfoModel);
		node.setLabel(functionInfoModel.getFunName());
		return node;
	}

	public volatile TreeNode doConvert(Object obj)
	{
		return doConvert((VroleFunModel)obj);
	}
}
