// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TreeConverter.java

package com.zony.common.util.tree;

import java.util.*;
import org.apache.commons.beanutils.PropertyUtils;

// Referenced classes of package com.zony.common.util.tree:
//			TreeNodeConverter, TreeNode

public class TreeConverter
{

	private Map treeMap;
	private Map fieldPathMap;
	static String pathSeparator = "\\";
	private String rootId;
	private String idFieldName;
	private String parentIdFieldName;
	private String pathFieldName;
	private TreeNodeConverter treeNodeConverter;
	private String curParentId;

	private TreeConverter()
	{
		treeMap = new HashMap();
		fieldPathMap = new HashMap();
		rootId = "0";
		idFieldName = "id";
		parentIdFieldName = "parentID";
	}

	public TreeConverter(TreeNodeConverter treeNodeConverter)
	{
		this();
		this.treeNodeConverter = treeNodeConverter;
		this.treeNodeConverter.treeConverter = this;
		clean();
	}

	public TreeConverter(TreeNodeConverter treeNodeConverter, String rootId)
	{
		this();
		this.treeNodeConverter = treeNodeConverter;
		this.treeNodeConverter.treeConverter = this;
		this.rootId = rootId;
		clean();
	}

	public TreeConverter(TreeNodeConverter treeNodeConverter, String idFieldName, String parentIdFieldName)
	{
		this();
		this.treeNodeConverter = treeNodeConverter;
		this.treeNodeConverter.treeConverter = this;
		this.idFieldName = idFieldName;
		this.parentIdFieldName = parentIdFieldName;
		clean();
	}

	public TreeConverter(TreeNodeConverter treeNodeConverter, String idFieldName, String parentIdFieldName, String rootId)
	{
		this();
		this.treeNodeConverter = treeNodeConverter;
		this.treeNodeConverter.treeConverter = this;
		this.idFieldName = idFieldName;
		this.parentIdFieldName = parentIdFieldName;
		this.rootId = rootId;
		clean();
	}

	void clean()
	{
		treeMap = new HashMap();
		fieldPathMap = new HashMap();
		curParentId = rootId;
	}

	public void setPathSeparator(String pathSeparator)
	{
		pathSeparator = pathSeparator;
	}

	public void setPathFieldName(String pathFieldName)
	{
		this.pathFieldName = pathFieldName;
	}

	public Map getFieldPathMap()
	{
		return fieldPathMap;
	}

	List convertCycleNodes(List entityList)
		throws Exception
	{
		List treeNodes;
		treeNodes = new ArrayList();
		List convertList = getEntityListToConvert(entityList);
		if (convertList != null)
		{
			for (Iterator iterator = convertList.iterator(); iterator.hasNext();)
			{
				Object entity = (Object)iterator.next();
				TreeNode node = doCycleConvert(entity);
				if (node != null)
					treeNodes.add(node);
			}

		}
		return treeNodes;
		Exception e;
		e;
		throw e;
	}

	List getEntityListToConvert(List entityList)
		throws Exception
	{
		List convertList;
		Map treeMap = getTreeMap(entityList);
		convertList = (List)treeMap.get(curParentId);
		return convertList;
		Exception e;
		e;
		throw e;
	}

	Map getTreeMap(List list)
		throws Exception
	{
		if (curParentId.equals(rootId))
			createTreeMap(list);
		return treeMap;
		Exception e;
		e;
		throw e;
	}

	void createTreeMap(List list)
		throws Exception
	{
		try
		{
			if (list != null)
			{
				Object entity;
				List childList;
				for (Iterator iterator = list.iterator(); iterator.hasNext(); childList.add(entity))
				{
					entity = (Object)iterator.next();
					Object parentIdValue = PropertyUtils.getProperty(entity, parentIdFieldName);
					childList = (List)treeMap.get((new StringBuilder()).append(parentIdValue).toString());
					if (childList == null)
					{
						childList = new ArrayList();
						treeMap.put((new StringBuilder()).append(parentIdValue).toString(), childList);
					}
				}

			}
		}
		catch (Exception e)
		{
			throw e;
		}
	}

	TreeNode doCycleConvert(Object entity)
		throws Exception
	{
		TreeNode node;
		Object idValue = PropertyUtils.getProperty(entity, idFieldName);
		if (pathFieldName != null)
			setFieldPathMap(entity, idValue);
		node = treeNodeConverter.doConvert(entity);
		curParentId = (new StringBuilder()).append(idValue).toString();
		List childList = (List)treeMap.get(curParentId);
		if (childList != null && childList.size() > 0)
		{
			List nodeList = convertCycleNodes(childList);
			node.addChildAll(nodeList);
		}
		return node;
		Exception e;
		e;
		throw e;
	}

	private void setFieldPathMap(Object entity, Object idValue)
		throws Exception
	{
		try
		{
			Object parentIdValue = PropertyUtils.getProperty(entity, parentIdFieldName);
			Object pathFieldValue = PropertyUtils.getProperty(entity, pathFieldName);
			if ((new StringBuilder()).append(parentIdValue).toString().equals(rootId))
			{
				fieldPathMap.put((new StringBuilder()).append(idValue).toString(), (new StringBuilder()).append(pathFieldValue).toString());
			} else
			{
				String parentFieldPath = (String)fieldPathMap.get((new StringBuilder()).append(parentIdValue).toString());
				fieldPathMap.put((new StringBuilder()).append(idValue).toString(), (new StringBuilder(String.valueOf(parentFieldPath))).append(pathSeparator).append(pathFieldValue).toString());
			}
		}
		catch (Exception e)
		{
			throw e;
		}
	}

}
