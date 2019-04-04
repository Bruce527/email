// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TreeUtils.java

package com.zony.common.util.tree;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import java.util.*;

// Referenced classes of package com.zony.common.util.tree:
//			TreeNode, TreeConverter, TreeNodeConverter

public class TreeUtils
{

	public TreeUtils()
	{
	}

	public static String convertSameClassTree(List entityList, TreeConverter converter, TreeNode rootNode)
	{
		if (rootNode != null)
		{
			List nodes = convertSameClassTreeNodes(entityList, converter);
			rootNode.setAsRoot();
			rootNode.addChildAll(nodes);
			return toXml(rootNode);
		} else
		{
			List nodes = convertSameClassTreeNodes(entityList, converter);
			String xml = toXml(nodes);
			xml = xml.replace("<list>", "").replace("</list>", "");
			return xml;
		}
	}

	public static String convertSameClassTreeNoRoot(List entityList, TreeConverter converter)
	{
		return convertSameClassTree(entityList, converter, null);
	}

	private static List convertSameClassTreeNodes(List entityList, TreeConverter converter)
	{
		List treeNodes = null;
		try
		{
			treeNodes = converter.convertCycleNodes(entityList);
			converter.clean();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return treeNodes;
	}

	public static String convertMultiClassTree(List entityList, TreeNodeConverter converter, TreeNode rootNode)
	{
		if (rootNode != null)
		{
			List nodes = convertMultiClassTreeNodes(entityList, converter);
			rootNode.setAsRoot();
			rootNode.addChildAll(nodes);
			return toXml(rootNode);
		} else
		{
			List nodes = convertMultiClassTreeNodes(entityList, converter);
			String xml = toXml(nodes);
			xml = xml.replace("<list>", "").replace("</list>", "");
			return xml;
		}
	}

	public static String convertMultiClassTreeNoRoot(List entityList, TreeNodeConverter converter)
	{
		return convertMultiClassTree(entityList, converter, null);
	}

	private static List convertMultiClassTreeNodes(List entityList, TreeNodeConverter converter)
	{
		List treeNodes = new ArrayList();
		for (Iterator iterator = entityList.iterator(); iterator.hasNext();)
		{
			Object entity = (Object)iterator.next();
			TreeNode node = converter.doConvert(entity);
			if (node != null)
				treeNodes.add(node);
		}

		return treeNodes;
	}

	private static String toXml(Object o)
	{
		XStream stream = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
		stream.autodetectAnnotations(true);
		return stream.toXML(o);
	}
}
