// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TreeNodeConverter.java

package com.zony.common.util.tree;

import java.util.Map;
import org.apache.commons.beanutils.BeanUtils;

// Referenced classes of package com.zony.common.util.tree:
//			TreeConverter, TreeNode

public abstract class TreeNodeConverter
{

	TreeConverter treeConverter;

	public TreeNodeConverter()
	{
	}

	public abstract TreeNode doConvert(Object obj);

	public String transToStr(Object value)
	{
		if (value == null)
			return "";
		else
			return value.toString();
	}

	public void copyProperty(TreeNode node, Object entity)
	{
		try
		{
			BeanUtils.copyProperties(node, entity);
		}
		catch (Exception exception) { }
	}

	public Map getFieldPathMap()
	{
		return treeConverter.getFieldPathMap();
	}

	public TreeConverter getTreeConverter()
	{
		return treeConverter;
	}

	public void setTreeConverter(TreeConverter treeConverter)
	{
		this.treeConverter = treeConverter;
	}
}
