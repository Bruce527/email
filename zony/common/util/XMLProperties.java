// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   XMLProperties.java

package com.zony.common.util;

import java.io.*;
import java.util.*;
import org.jdom.*;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

// Referenced classes of package com.zony.common.util:
//			Globals

public class XMLProperties
{

	private final File file;
	public Document doc;
	private final Map propertyCache = new HashMap();

	public XMLProperties(String file)
	{
		this.file = new File(file);
		try
		{
			SAXBuilder builder = new SAXBuilder();
			doc = builder.build(new File(file));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public String getProperty(String name)
	{
		if (propertyCache.containsKey(name))
			return (String)propertyCache.get(name);
		String propName[] = parsePropertyName(name);
		Element element = doc.getRootElement();
		String as[];
		int j = (as = propName).length;
		for (int i = 0; i < j; i++)
		{
			String element2 = as[i];
			element = element.getChild(element2);
			if (element == null)
				return null;
		}

		String value = element.getText();
		if ("".equals(value))
		{
			return null;
		} else
		{
			value = value.trim();
			propertyCache.put(name, value);
			return value;
		}
	}

	public String[] getChildrenProperties(String parent)
	{
		String propName[] = parsePropertyName(parent);
		Element element = doc.getRootElement();
		String as[];
		int k = (as = propName).length;
		for (int j = 0; j < k; j++)
		{
			String element2 = as[j];
			element = element.getChild(element2);
			if (element == null)
				return new String[0];
		}

		List children = element.getChildren();
		int childCount = children.size();
		String childrenNames[] = new String[childCount];
		for (int i = 0; i < childCount; i++)
			childrenNames[i] = ((Element)children.get(i)).getName();

		return childrenNames;
	}

	public void setProperty(String name, String value)
	{
		propertyCache.put(name, value);
		String propName[] = parsePropertyName(name);
		Element element = doc.getRootElement();
		String as[];
		int j = (as = propName).length;
		for (int i = 0; i < j; i++)
		{
			String element2 = as[i];
			if (element.getChild(element2) == null)
				element.addContent(new Element(element2));
			element = element.getChild(element2);
		}

		element.setText(value);
		saveProperties();
	}

	public void deleteProperty(String name)
	{
		String propName[] = parsePropertyName(name);
		Element element = doc.getRootElement();
		for (int i = 0; i < propName.length - 1; i++)
		{
			element = element.getChild(propName[i]);
			if (element == null)
				return;
		}

		element.removeChild(propName[propName.length - 1]);
		saveProperties();
	}

	private synchronized void saveProperties()
	{
		OutputStream out;
		boolean error;
		File tempFile;
		out = null;
		error = false;
		tempFile = null;
		try
		{
			tempFile = new File(file.getParentFile(), (new StringBuilder(String.valueOf(file.getName()))).append(".tmp").toString());
			Format format = Format.getPrettyFormat();
			format.setIndent("    ");
			XMLOutputter outputter = new XMLOutputter();
			out = new BufferedOutputStream(new FileOutputStream(tempFile));
			outputter.output(doc, out);
			break MISSING_BLOCK_LABEL_146;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			error = true;
		}
		try
		{
			out.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			error = true;
		}
		break MISSING_BLOCK_LABEL_162;
		Exception exception;
		exception;
		try
		{
			out.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			error = true;
		}
		throw exception;
		try
		{
			out.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			error = true;
		}
		if (!error)
		{
			file.delete();
			tempFile.renameTo(file);
		}
		return;
	}

	private String[] parsePropertyName(String name)
	{
		int size = 1;
		for (int i = 0; i < name.length(); i++)
			if (name.charAt(i) == '.')
				size++;

		String propName[] = new String[size];
		StringTokenizer tokenizer = new StringTokenizer(name, ".");
		for (int i = 0; tokenizer.hasMoreTokens(); i++)
			propName[i] = tokenizer.nextToken();

		return propName;
	}

	public String getattribute(String key)
	{
		Element element = doc.getRootElement();
		List list = element.getChildren();
		String returnvalue = "";
		Element ele = (Element)list.get(0);
		if (ele.getAttributeValue("id").equals("dataSource"))
		{
			list = ele.getContent();
			for (int i = 1; i < list.size(); i += 2)
			{
				Element elementchild = (Element)list.get(i);
				if (!elementchild.getAttributeValue("name").equals(key))
					continue;
				returnvalue = elementchild.getAttributeValue("value");
				break;
			}

		}
		return returnvalue;
	}

	public void setattribute(String key, String value)
	{
		Element element = doc.getRootElement();
		List list = element.getChildren();
		Element ele = (Element)list.get(0);
		if (ele.getAttributeValue("id").equals("dataSource"))
		{
			list = ele.getContent();
			for (int i = 1; i < list.size(); i += 2)
			{
				Element elementchild = (Element)list.get(i);
				if (!elementchild.getAttributeValue("name").equals(key))
					continue;
				elementchild.getAttribute("value").setValue(value);
				break;
			}

		}
		XMLOutputter outputter = new XMLOutputter();
		try
		{
			outputter.output(doc, new FileOutputStream((new StringBuilder(String.valueOf(Globals.getsysRootDir()))).append("\\WEB-INF\\classes\\applicationContext.xml").toString()));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
