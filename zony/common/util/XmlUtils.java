// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   XmlUtils.java

package com.zony.common.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import java.io.*;
import java.lang.reflect.Field;
import java.util.*;
import org.apache.commons.beanutils.BeanUtils;
import org.jdom.*;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

// Referenced classes of package com.zony.common.util:
//			FieldUtil

public class XmlUtils
{

	public XmlUtils()
	{
	}

	public static String toXml(Object object, Class clazz, boolean isAonn)
	{
		XStream xstream = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
		xstream.setMode(1001);
		if (isAonn)
			xstream.processAnnotations(clazz);
		String result = xstream.toXML(object);
		return result;
	}

	public static Document getDocument(InputStream in)
	{
		Document doc = null;
		try
		{
			SAXBuilder sb = new SAXBuilder();
			doc = sb.build(in);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return doc;
	}

	public static Document getDocumentReader(InputStreamReader in)
	{
		Document doc = null;
		try
		{
			SAXBuilder sb = new SAXBuilder();
			doc = sb.build(in);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return doc;
	}

	public static String getSingleXMLValue(Document doc, String singlePath)
	{
		String value = "";
		try
		{
			Element singleNode = (Element)XPath.selectSingleNode(doc.getRootElement(), singlePath);
			value = singleNode.getText();
			System.out.println((new StringBuilder("Text:¡¾")).append(singleNode.getText()).append("¡¿").toString());
			System.out.println((new StringBuilder("TextNormalize:¡¾")).append(singleNode.getTextNormalize()).append("¡¿").toString());
			System.out.println((new StringBuilder("TextTrim:¡¾")).append(singleNode.getTextTrim()).append("¡¿").toString());
		}
		catch (Exception exception) { }
		return value;
	}

	public static Object fromXml(String xml)
	{
		XStream xstream = new XStream(new DomDriver());
		Object result = xstream.fromXML(xml);
		return result;
	}

	public static Object fromXml(String xml, Map mapAlias)
	{
		XStream xstream = new XStream(new DomDriver());
		Set keys = mapAlias.keySet();
		String key;
		for (Iterator iterator = keys.iterator(); iterator.hasNext(); xstream.alias(key, (Class)mapAlias.get(key)))
			key = (String)iterator.next();

		Object result = xstream.fromXML(xml);
		return result;
	}

	public static List getListXMLValueWithXStream(Document doc, String path, Class objClass)
		throws Exception
	{
		String listName = "list";
		String objName = "object";
		Map fieldMap = getFieldMap(objClass);
		String newxml = "<list>";
		List objEltList = XPath.selectNodes(doc.getRootElement(), path);
		for (Iterator iterator = objEltList.iterator(); iterator.hasNext();)
		{
			Element objElt = (Element)iterator.next();
			newxml = (new StringBuilder(String.valueOf(newxml))).append("<object>").toString();
			List fieldEltList = objElt.getChildren();
			for (Iterator iterator1 = fieldEltList.iterator(); iterator1.hasNext();)
			{
				Element fieldElt = (Element)iterator1.next();
				String fieldname = fieldElt.getName();
				String fieldvalue = fieldElt.getText().trim();
				if (!"".equals(fieldvalue))
				{
					String innerfieldname = (String)fieldMap.get(fieldname);
					if (innerfieldname != null && !"".equals(innerfieldname))
						newxml = (new StringBuilder(String.valueOf(newxml))).append("<").append(innerfieldname).append(">").append(fieldvalue).append("</").append(innerfieldname).append(">").toString();
				}
			}

			newxml = (new StringBuilder(String.valueOf(newxml))).append("</object>").toString();
		}

		newxml = (new StringBuilder(String.valueOf(newxml))).append("</list>").toString();
		Map map = new HashMap();
		map.put("object", objClass);
		map.put("list", java/util/ArrayList);
		List list = (List)fromXml(newxml, map);
		return list;
	}

	public static List getListXMLValue(Document doc, String listPath, Class objClass)
	{
		List objList = new ArrayList();
		try
		{
			List objEltList = XPath.selectNodes(doc.getRootElement(), listPath);
			if (objEltList != null)
			{
				for (Iterator iterator = objEltList.iterator(); iterator.hasNext();)
				{
					Element objElt = (Element)iterator.next();
					Object obj = objClass.newInstance();
					List fieldEltList = objElt.getChildren();
					boolean checkflag = setFieldValue(obj, fieldEltList, true);
					if (checkflag)
						objList.add(obj);
				}

			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return objList;
	}

	private static boolean setFieldValue(Object obj, List fieldEltList, boolean transXMLField)
	{
		boolean checkflag = false;
		try
		{
			if (fieldEltList != null)
			{
				for (Iterator iterator = fieldEltList.iterator(); iterator.hasNext();)
				{
					Element fieldElt = (Element)iterator.next();
					String fieldname = fieldElt.getName();
					String fieldvalue = fieldElt.getText().trim();
					if (!"".equals(fieldvalue))
					{
						if (transXMLField)
							fieldname = FieldUtil.transOuterField(fieldElt.getName());
						try
						{
							BeanUtils.setProperty(obj, fieldname, fieldvalue);
							checkflag = true;
						}
						catch (Exception exception) { }
					}
				}

			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return checkflag;
	}

	private static Map getFieldMap(Class objClass)
	{
		Map map = new HashMap();
		Field fields[] = objClass.getDeclaredFields();
		Field afield[];
		int j = (afield = fields).length;
		for (int i = 0; i < j; i++)
		{
			Field field = afield[i];
			String fieldname = field.getName();
			String outterFieldName = fieldname;
			map.put(outterFieldName, fieldname);
		}

		return map;
	}

	public static Map getCycleNode(Document doc, String firstStr, String secondStr1, String secondStr2)
	{
		Map map = new HashMap();
		Element root = doc.getRootElement();
		List list = root.getChildren(firstStr);
		for (int i = 0; i < list.size(); i++)
		{
			Element item = (Element)list.get(i);
			String secondStrRe = item.getChildText(secondStr1);
			if (secondStrRe != null)
			{
				String secondStr2Re = item.getChildText(secondStr2);
				map.put(secondStrRe, secondStr2Re);
			}
		}

		return map;
	}

	public static void main(String args[])
		throws FileNotFoundException, JDOMException, IOException
	{
		SAXBuilder sb = new SAXBuilder();
		Document doc = sb.build(new FileInputStream(new File("D://00037087_HPOLSCDT_20160701140319.xml")));
		getCycleNode(doc, "ComponentDetails", "ProductCode", "CoverageName");
	}
}
