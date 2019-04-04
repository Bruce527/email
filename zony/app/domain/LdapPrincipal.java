// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LdapPrincipal.java

package com.zony.app.domain;

import java.util.List;

public class LdapPrincipal
{

	private String uid;
	private String name;
	private String path;
	private String member[];
	private String memberOf[];
	private boolean isAdmin;
	private String type;
	private String parentPath;
	private List groups;

	public LdapPrincipal()
	{
		uid = "";
		name = "";
		path = "";
		isAdmin = false;
	}

	public String getParentPath()
	{
		return parentPath;
	}

	public void setParentPath(String parentPath)
	{
		this.parentPath = parentPath;
	}

	public String getUid()
	{
		return uid;
	}

	public String getName()
	{
		return name;
	}

	public String getPath()
	{
		return path;
	}

	public String getType()
	{
		return type;
	}

	public List getGroups()
	{
		return groups;
	}

	public void setUid(String uid)
	{
		this.uid = uid;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setPath(String path)
	{
		this.path = path;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public void setGroups(List groups)
	{
		this.groups = groups;
	}

	public String[] getMember()
	{
		return member;
	}

	public String[] getMemberOf()
	{
		return memberOf;
	}

	public void setMember(String member[])
	{
		this.member = member;
	}

	public void setMemberOf(String memberOf[])
	{
		this.memberOf = memberOf;
	}

	public boolean getIsAdmin()
	{
		return isAdmin;
	}

	public void setIsAdmin(boolean isAdmin)
	{
		this.isAdmin = isAdmin;
	}
}
