// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   OrganizationTreeNodeConverter.java

package com.zony.app.model.treenode;

import com.zony.app.domain.Organization;
import com.zony.common.util.tree.TreeNode;
import com.zony.common.util.tree.TreeNodeConverter;

// Referenced classes of package com.zony.app.model.treenode:
//			OrganizationTreeNode

public class OrganizationTreeNodeConverter extends TreeNodeConverter
{

	public OrganizationTreeNodeConverter()
	{
	}

	public TreeNode doConvert(Organization organization)
	{
		OrganizationTreeNode node = new OrganizationTreeNode();
		copyProperty(node, organization);
		node.setLabel(organization.getOrgName());
		return node;
	}

	public volatile TreeNode doConvert(Object obj)
	{
		return doConvert((Organization)obj);
	}
}
