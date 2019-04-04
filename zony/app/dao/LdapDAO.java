// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LdapDAO.java

package com.zony.app.dao;

import com.novell.ldap.*;
import com.zony.app.domain.LdapPrincipal;
import com.zony.common.config.ZonyConfig;
import com.zony.common.util.LdapUtils;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class LdapDAO
{

	public static final String TYPE_USER = "user";
	public static final String TYPE_GROUP = "group";
	public static final String TYPE_OU = "organizationalUnit";

	public LdapDAO()
	{
	}

	public List getGroupOriginalList()
	{
		String searchBase = ZonyConfig.getLdapSearchBase_Org();
		return getPrincipals(searchBase, "group");
	}

	public List getOUOriginalList()
	{
		String searchBase = ZonyConfig.getLdapSearchBase_Org();
		return getPrincipals(searchBase, "organizationalUnit");
	}

	public List getOUOriginalList(String baseDN)
	{
		return getPrincipals(baseDN, "organizationalUnit");
	}

	public List getGroupDefinedList()
	{
		String searchBase = ZonyConfig.getLdapSearchBase_Def();
		return getPrincipals(searchBase, "group");
	}

	public List getUserOriginalList()
	{
		String searchBase = ZonyConfig.getLdapSearchBase_Org();
		return getPrincipals(searchBase, "user");
	}

	public List getUserDefinedList()
	{
		String searchBase = ZonyConfig.getLdapSearchBase_Def();
		return getPrincipals(searchBase, "user");
	}

	public List getUserSystemList()
	{
		String searchBase = ZonyConfig.getLdapSearchBase_Usr();
		List list = getPrincipals(searchBase, "user");
		List result = new ArrayList();
		for (int i = 0; i < list.size(); i++)
		{
			LdapPrincipal principal = (LdapPrincipal)list.get(i);
			if (ZonyConfig.getSystemUserId().equals(principal.getUid()))
				result.add(principal);
		}

		return result;
	}

	private List getPrincipals(String searchBase, String type)
	{
		List principalList;
		LDAPConnection connect;
		principalList = new ArrayList();
		connect = null;
		try
		{
			connect = LdapUtils.getConnect();
			String filter = (new StringBuilder("objectclass=")).append(type).toString();
			LdapPrincipal principal;
			for (LDAPSearchResults searchResults = connect.search(searchBase, 2, filter, null, false); searchResults.hasMore(); principalList.add(principal))
			{
				principal = new LdapPrincipal();
				LDAPEntry nextEntry = null;
				nextEntry = searchResults.next();
				String cn = nextEntry.getAttribute("cn") != null ? nextEntry.getAttribute("cn").getStringValue() : "";
				String dn = nextEntry.getDN();
				if (type.toLowerCase().equals("user".toLowerCase()))
				{
					String uid = nextEntry.getAttribute("sAMAccountName") != null ? nextEntry.getAttribute("sAMAccountName").getStringValue() : "";
					String displayName = nextEntry.getAttribute("displayName") != null ? nextEntry.getAttribute("displayName").getStringValue() : "";
					principal.setUid(uid);
					principal.setName(displayName);
				} else
				if (type.toLowerCase().equals("group".toLowerCase()))
				{
					principal.setName(cn);
				} else
				{
					String name = nextEntry.getAttribute("name") != null ? nextEntry.getAttribute("name").getStringValue() : "";
					principal.setName(name);
				}
				String member[] = nextEntry.getAttribute("member") != null ? nextEntry.getAttribute("member").getStringValueArray() : null;
				String memberOf[] = nextEntry.getAttribute("memberOf") != null ? nextEntry.getAttribute("memberOf").getStringValueArray() : null;
				String path = nextEntry.getAttribute("distinguishedName") != null ? nextEntry.getAttribute("distinguishedName").getStringValue() : "";
				principal.setMember(member);
				principal.setMemberOf(memberOf);
				principal.setPath(dn);
				principal.setType(type);
			}

			break MISSING_BLOCK_LABEL_419;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		try
		{
			LdapUtils.disConnect(connect);
		}
		catch (LDAPException e)
		{
			e.printStackTrace();
		}
		break MISSING_BLOCK_LABEL_434;
		Exception exception;
		exception;
		try
		{
			LdapUtils.disConnect(connect);
		}
		catch (LDAPException e)
		{
			e.printStackTrace();
		}
		throw exception;
		try
		{
			LdapUtils.disConnect(connect);
		}
		catch (LDAPException e)
		{
			e.printStackTrace();
		}
		return principalList;
	}

	public LdapPrincipal getGroupOriginal(String dn)
		throws Exception
	{
		return getPrincipal(dn);
	}

	public LdapPrincipal getGroupDefined(String dn)
		throws Exception
	{
		return getPrincipal(dn);
	}

	public LdapPrincipal getUserOriginal(String dn)
		throws Exception
	{
		return getPrincipal(dn);
	}

	public LdapPrincipal getUserDefined(String dn)
		throws Exception
	{
		return getPrincipal(dn);
	}

	private LdapPrincipal getPrincipal(LDAPSearchResults searchResults, String dn)
		throws LDAPException
	{
		LdapPrincipal principal = null;
		while (searchResults.hasMore()) 
		{
			principal = new LdapPrincipal();
			LDAPEntry nextEntry = null;
			nextEntry = searchResults.next();
			if (dn == null || nextEntry.getDN().toLowerCase().equals(dn.toLowerCase()))
			{
				String cn = nextEntry.getAttribute("cn") != null ? nextEntry.getAttribute("cn").getStringValue() : "";
				String objClasses[] = nextEntry.getAttribute("objectClass").getStringValueArray();
				String objectClass = "";
				String as[];
				int j = (as = objClasses).length;
				for (int i = 0; i < j; i++)
				{
					String objectType = as[i];
					if (objectType.equals("group"))
						objectClass = "group";
					if (objectType.equals("user"))
						objectClass = "user";
					else
						objectClass = "organizationalUnit";
				}

				if (objectClass.toLowerCase().equals("user".toLowerCase()))
				{
					String uid = nextEntry.getAttribute("sAMAccountName") != null ? nextEntry.getAttribute("sAMAccountName").getStringValue() : "";
					principal.setUid(uid);
					String displayName = nextEntry.getAttribute("displayName") != null ? nextEntry.getAttribute("displayName").getStringValue() : "";
					principal.setName(displayName);
					principal.setType("user");
				} else
				if (objectClass.toLowerCase().equals("group".toLowerCase()))
				{
					principal.setName(cn);
				} else
				{
					String name = nextEntry.getAttribute("name") != null ? nextEntry.getAttribute("name").getStringValue() : "";
					principal.setName(name);
				}
				String member[] = nextEntry.getAttribute("member") != null ? nextEntry.getAttribute("member").getStringValueArray() : null;
				String memberOf[] = nextEntry.getAttribute("memberOf") != null ? nextEntry.getAttribute("memberOf").getStringValueArray() : null;
				String ou = nextEntry.getAttribute("distinguishedName") != null ? nextEntry.getAttribute("distinguishedName").getStringValue() : "";
				if (memberOf != null)
				{
					String as1[];
					int l = (as1 = memberOf).length;
					for (int k = 0; k < l; k++)
					{
						String groupName = as1[k];
						groupName = groupName.substring(3, groupName.indexOf(","));
					}

				}
				principal.setMember(member);
				principal.setMemberOf(memberOf);
				principal.setPath(nextEntry.getDN());
				principal.setParentPath(ou);
				principal.setType(objectClass);
			}
		}
		return principal;
	}

	private LdapPrincipal getPrincipal(String dn)
		throws Exception
	{
		LDAPConnection connect;
		String searchBase_Org;
		String searchBase_Def;
		String searchBase_Usr;
		LdapPrincipal principal = new LdapPrincipal();
		connect = null;
		searchBase_Org = ZonyConfig.getLdapSearchBase_Org();
		searchBase_Def = ZonyConfig.getLdapSearchBase_Def();
		searchBase_Usr = ZonyConfig.getLdapSearchBase_Usr();
		LDAPSearchResults searchResults2;
		LDAPSearchResults searchResults3;
		LdapPrincipal ldapprincipal;
		connect = LdapUtils.getConnect();
		String filter = (new StringBuilder("distinguishedName=")).append(dn).toString();
		LDAPSearchResults searchResults = connect.search(searchBase_Org, 2, filter, null, false);
		searchResults2 = connect.search(searchBase_Def, 2, filter, null, false);
		searchResults3 = connect.search(searchBase_Usr, 2, filter, null, false);
		if (searchResults.getCount() + searchResults2.getCount() > 1)
			throw new Exception();
		LdapPrincipal principal = getPrincipal(searchResults, dn);
		if (principal == null)
			break MISSING_BLOCK_LABEL_141;
		ldapprincipal = principal;
		try
		{
			LdapUtils.disConnect(connect);
		}
		catch (LDAPException e)
		{
			e.printStackTrace();
		}
		return ldapprincipal;
		LdapPrincipal principal = getPrincipal(searchResults2, dn);
		if (principal == null)
			break MISSING_BLOCK_LABEL_173;
		ldapprincipal = principal;
		try
		{
			LdapUtils.disConnect(connect);
		}
		catch (LDAPException e)
		{
			e.printStackTrace();
		}
		return ldapprincipal;
		LdapPrincipal principal;
label0:
		{
			LdapPrincipal ldapprincipal1;
			try
			{
				principal = getPrincipal(searchResults3, dn);
				if (principal == null)
					break label0;
				ldapprincipal1 = principal;
			}
			catch (Exception ex)
			{
				throw new Exception(ex);
			}
			finally
			{
				try
				{
					LdapUtils.disConnect(connect);
				}
				catch (LDAPException e)
				{
					e.printStackTrace();
				}
				throw exception;
			}
			try
			{
				LdapUtils.disConnect(connect);
			}
			catch (LDAPException e)
			{
				e.printStackTrace();
			}
			return ldapprincipal1;
		}
		try
		{
			LdapUtils.disConnect(connect);
		}
		catch (LDAPException e)
		{
			e.printStackTrace();
		}
		return principal;
	}

	private String getTree(String xml, String dn)
		throws UnsupportedEncodingException, LDAPException
	{
		LDAPConnection connect = LdapUtils.getConnect();
		for (LDAPSearchResults searchResults = connect.search(dn, 1, "objectClass=organizationalUnit", null, false); searchResults.hasMore();)
		{
			LDAPEntry nextEntry = null;
			nextEntry = searchResults.next();
			String subName = nextEntry.getAttribute("name").getStringValue();
			String subDN = nextEntry.getDN();
			xml = (new StringBuilder(String.valueOf(xml))).append("<folder name='").append(subName).append("' dn='").append(subDN).append("'>\r\n").toString();
			xml = (new StringBuilder(String.valueOf(xml))).append(getTree("", subDN)).toString();
			xml = (new StringBuilder(String.valueOf(xml))).append("</folder>\r\n").toString();
		}

		return xml;
	}

	public String getOUTree()
		throws UnsupportedEncodingException, LDAPException
	{
		String xml = (new StringBuilder("<folder name='上海仲尼软件有限公司' dn='")).append(ZonyConfig.getLdapSearchBase_Org()).append("'>\r\n").toString();
		xml = (new StringBuilder(String.valueOf(xml))).append(getTree("", ZonyConfig.getLdapSearchBase_Org())).toString();
		xml = (new StringBuilder(String.valueOf(xml))).append("</folder>").toString();
		System.out.println(xml);
		return xml;
	}

	public String getOUTree(String baseDN)
		throws UnsupportedEncodingException, LDAPException
	{
		String xml = (new StringBuilder("<folder name='上海仲尼软件有限公司' dn='")).append(ZonyConfig.getLdapSearchBase_Org()).append("'>\r\n").toString();
		xml = (new StringBuilder(String.valueOf(xml))).append(getTree("", baseDN)).toString();
		xml = (new StringBuilder(String.valueOf(xml))).append("</folder>").toString();
		return xml;
	}

	public List getOUOfPrincipal(LdapPrincipal principal)
	{
		List ou = new ArrayList();
		String memberOf[] = principal.getMemberOf();
		String as[];
		int l = (as = memberOf).length;
		for (int k = 0; k < l; k++)
		{
			String ouPath = as[k];
			ou.addAll(getOUOriginalList(ouPath.substring(ouPath.indexOf("OU"))));
		}

		String path = principal.getParentPath();
		int index = principal.getParentPath().indexOf("OU");
		if (-1 != index)
			path = principal.getParentPath().substring(principal.getParentPath().indexOf("OU"));
		ou.addAll(getOUOriginalList(path));
		for (int i = 0; i < ou.size() - 1; i++)
		{
			for (int j = ou.size() - 1; j > i; j--)
				if (((LdapPrincipal)ou.get(j)).getPath().equals(((LdapPrincipal)ou.get(i)).getPath()))
					ou.remove(j);

		}

		LdapPrincipal ldapPrincipal;
		for (Iterator iterator = ou.iterator(); iterator.hasNext(); System.out.println(ldapPrincipal.getPath()))
			ldapPrincipal = (LdapPrincipal)iterator.next();

		return ou;
	}

	public List getOUOfUser(String userName)
	{
		List list = searchUser(userName);
		List ou = new ArrayList();
		LdapPrincipal principal = null;
		if (list != null && list.size() != 0)
			principal = (LdapPrincipal)list.get(0);
		else
			return null;
		String memberOf[] = principal.getMemberOf();
		String as[];
		int l = (as = memberOf).length;
		for (int k = 0; k < l; k++)
		{
			String ouPath = as[k];
			ou.addAll(getOUOriginalList(ouPath.substring(ouPath.indexOf("OU"))));
		}

		String path = principal.getParentPath();
		int index = principal.getParentPath().indexOf("OU");
		if (-1 != index)
			path = principal.getParentPath().substring(principal.getParentPath().indexOf("OU"));
		ou.addAll(getOUOriginalList(path));
		for (int i = 0; i < ou.size() - 1; i++)
		{
			for (int j = ou.size() - 1; j > i; j--)
				if (((LdapPrincipal)ou.get(j)).getPath().equals(((LdapPrincipal)ou.get(i)).getPath()))
					ou.remove(j);

		}

		return ou;
	}

	public void changeUserInGroup(String groupPath, List uidList)
		throws Exception
	{
		LDAPConnection connect = LdapUtils.getConnect();
		LdapPrincipal group = getPrincipal(groupPath);
		List modList = new ArrayList();
		List dnList = new ArrayList();
		for (int i = 0; i < uidList.size(); i++)
		{
			String userDn = getDnByUserId((String)uidList.get(i));
			dnList.add(userDn);
		}

		String userArray[] = new String[dnList.size()];
		for (int j = 0; j < dnList.size(); j++)
			userArray[j] = (String)dnList.get(j);

		LDAPAttribute attribute = new LDAPAttribute("member", userArray);
		modList.add(new LDAPModification(2, attribute));
		LDAPModification mods[] = new LDAPModification[modList.size()];
		mods = (LDAPModification[])modList.toArray(mods);
		connect.modify(group.getPath(), mods);
	}

	public void changeGroupInGroup(String groupPath, List groupPathList, boolean isClear)
		throws Exception
	{
		LDAPConnection connect = LdapUtils.getConnect();
		LdapPrincipal group = getPrincipal(groupPath);
		String mainGroupMember[] = group.getMember();
		List modList = new ArrayList();
		String groupArray[] = null;
		if (isClear)
			groupArray = new String[groupPathList.size()];
		else
		if (mainGroupMember != null)
			groupArray = new String[groupPathList.size() + mainGroupMember.length];
		else
			groupArray = new String[groupPathList.size()];
		for (int j = 0; j < groupPathList.size(); j++)
			groupArray[j] = (String)groupPathList.get(j);

		if (!isClear && mainGroupMember != null)
		{
			for (int k = 0; k < mainGroupMember.length; k++)
				groupArray[k + groupPathList.size()] = mainGroupMember[k];

		}
		LDAPAttribute attribute = new LDAPAttribute("member", groupArray);
		modList.add(new LDAPModification(2, attribute));
		LDAPModification mods[] = new LDAPModification[modList.size()];
		mods = (LDAPModification[])modList.toArray(mods);
		connect.modify(group.getPath(), mods);
	}

	public boolean addGroup(String groupName)
		throws LDAPException, UnsupportedEncodingException
	{
		boolean result = false;
		LDAPAttributeSet attributeSet = new LDAPAttributeSet();
		LDAPConnection connect = LdapUtils.getConnect();
		attributeSet.add(new LDAPAttribute("objectclass", new String("group")));
		attributeSet.add(new LDAPAttribute("cn", new String[] {
			groupName
		}));
		attributeSet.add(new LDAPAttribute("sAMAccountName", new String(groupName)));
		String dn = (new StringBuilder("cn=")).append(groupName).append(",").append(ZonyConfig.getLdapSearchBase_Def()).toString();
		LDAPEntry newEntry = new LDAPEntry(dn, attributeSet);
		connect.add(newEntry);
		result = true;
		LdapUtils.disConnect(connect);
		return result;
	}

	public boolean addUser(String userId, String password, String userName)
		throws LDAPException, UnsupportedEncodingException
	{
		boolean result = false;
		LDAPAttributeSet attributeSet = new LDAPAttributeSet();
		LDAPConnection connect = LdapUtils.getConnect();
		attributeSet.add(new LDAPAttribute("objectclass", new String("user")));
		attributeSet.add(new LDAPAttribute("sAMAccountName", new String(userId)));
		attributeSet.add(new LDAPAttribute("userPassword", new String(password)));
		attributeSet.add(new LDAPAttribute("displayName", new String[] {
			userName
		}));
		String path = ZonyConfig.getLdapSearchBase_Def();
		String dn = (new StringBuilder("cn=")).append(userId).append(",").append(path).toString();
		LDAPEntry newEntry = new LDAPEntry(dn, attributeSet);
		connect.add(newEntry);
		result = true;
		LdapUtils.disConnect(connect);
		return result;
	}

	public boolean delGroup(String groupName)
		throws LDAPException, UnsupportedEncodingException
	{
		boolean result = false;
		LDAPConnection connect = LdapUtils.getConnect();
		LDAPSearchResults searchResults = connect.search(ZonyConfig.getLdapSearchBase_Def(), 2, (new StringBuilder("cn=")).append(groupName).toString(), null, false);
		LdapPrincipal group = new LdapPrincipal();
		if (searchResults.hasMore())
		{
			LDAPEntry nextEntry = null;
			nextEntry = searchResults.next();
			group.setPath(nextEntry.getDN());
		}
		connect.delete(group.getPath());
		result = true;
		LdapUtils.disConnect(connect);
		return result;
	}

	public List getGroupByUid(String userDn, boolean isRecursive)
	{
		String searchBase;
		String searchBase2;
		List groupList;
		LDAPConnection connect;
		searchBase = ZonyConfig.getLdapSearchBase_Org();
		searchBase2 = ZonyConfig.getLdapSearchBase_Usr();
		groupList = new ArrayList();
		connect = null;
		try
		{
			connect = LdapUtils.getConnect();
			String filter = (new StringBuilder("distinguishedName=")).append(userDn).toString();
			LDAPSearchResults searchResults = connect.search(searchBase, 2, filter, null, false);
			LDAPSearchResults searchResults2 = connect.search(searchBase2, 2, filter, null, false);
			if (searchResults.hasMore())
			{
				LDAPEntry nextEntry = null;
				nextEntry = searchResults.next();
				String parents[] = nextEntry.getAttribute("memberOf") != null ? nextEntry.getAttribute("memberOf").getStringValueArray() : null;
				String as[];
				int k = (as = parents).length;
				for (int i = 0; i < k; i++)
				{
					String parent = as[i];
					String groupdn = parent;
					if (isRecursive)
						getGroupByPath(groupdn, groupList, isRecursive);
				}

			}
			if (searchResults2.hasMore())
			{
				LDAPEntry nextEntry = null;
				nextEntry = searchResults2.next();
				String parents[] = nextEntry.getAttribute("memberOf") != null ? nextEntry.getAttribute("memberOf").getStringValueArray() : null;
				String as1[];
				int l = (as1 = parents).length;
				for (int j = 0; j < l; j++)
				{
					String parent = as1[j];
					String groupdn = parent;
					if (isRecursive)
						getGroupByPath(groupdn, groupList, isRecursive);
				}

			}
			break MISSING_BLOCK_LABEL_303;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		try
		{
			LdapUtils.disConnect(connect);
		}
		catch (LDAPException e)
		{
			e.printStackTrace();
		}
		break MISSING_BLOCK_LABEL_318;
		Exception exception;
		exception;
		try
		{
			LdapUtils.disConnect(connect);
		}
		catch (LDAPException e)
		{
			e.printStackTrace();
		}
		throw exception;
		try
		{
			LdapUtils.disConnect(connect);
		}
		catch (LDAPException e)
		{
			e.printStackTrace();
		}
		return groupList;
	}

	private void getGroupByPath(String dn, List groupList, boolean isRecursive)
	{
		String searchBase;
		LdapPrincipal principal;
		LDAPConnection connect;
		searchBase = ZonyConfig.getLdaproot();
		principal = new LdapPrincipal();
		connect = null;
		try
		{
			connect = LdapUtils.getConnect();
			String filter = (new StringBuilder("distinguishedName=")).append(dn).toString();
			for (LDAPSearchResults searchResults = connect.search(searchBase, 2, filter, null, false); searchResults.hasMore();)
			{
				LDAPEntry nextEntry = null;
				nextEntry = searchResults.next();
				if (nextEntry.getDN().toLowerCase().equals(dn.toLowerCase()))
				{
					String cn = nextEntry.getAttribute("cn") != null ? nextEntry.getAttribute("cn").getStringValue() : "";
					principal.setName(cn);
					principal.setPath(dn);
					principal.setType("group");
					groupList.add(principal);
					String parents[] = nextEntry.getAttribute("memberOf") != null ? nextEntry.getAttribute("memberOf").getStringValueArray() : null;
					if (parents != null)
					{
						String as[];
						int j = (as = parents).length;
						for (int i = 0; i < j; i++)
						{
							String parent = as[i];
							String groupdn = parent;
							if (isRecursive)
								getGroupByPath(groupdn, groupList, isRecursive);
						}

					}
					break;
				}
			}

			break MISSING_BLOCK_LABEL_281;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		try
		{
			LdapUtils.disConnect(connect);
		}
		catch (LDAPException e)
		{
			e.printStackTrace();
		}
		break MISSING_BLOCK_LABEL_296;
		Exception exception;
		exception;
		try
		{
			LdapUtils.disConnect(connect);
		}
		catch (LDAPException e)
		{
			e.printStackTrace();
		}
		throw exception;
		try
		{
			LdapUtils.disConnect(connect);
		}
		catch (LDAPException e)
		{
			e.printStackTrace();
		}
	}

	public List getUsersByGpath(String groupPath, boolean isRecursive, List userList)
	{
		String searchBase;
		LDAPConnection connect;
		searchBase = ZonyConfig.getLdapSearchBase_Def();
		if (userList == null)
			userList = new ArrayList();
		connect = null;
		try
		{
			connect = LdapUtils.getConnect();
			String filter = (new StringBuilder("distinguishedName=")).append(groupPath).toString();
			for (LDAPSearchResults searchResults = connect.search(searchBase, 2, filter, null, false); searchResults.hasMore();)
			{
				LDAPEntry nextEntry = null;
				nextEntry = searchResults.next();
				String childs[] = nextEntry.getAttribute("member") != null ? nextEntry.getAttribute("member").getStringValueArray() : null;
				String as[];
				int j = (as = childs).length;
				for (int i = 0; i < j; i++)
				{
					String child = as[i];
					String principalDN = child;
					LdapPrincipal principal = getPrincipal(principalDN);
					if ("group".equals(principal.getType()) && isRecursive)
						getUsersByGpath(principalDN, isRecursive, userList);
					else
					if ("user".equals(principal.getType()))
						userList.add(principal);
				}

			}

			break MISSING_BLOCK_LABEL_246;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		try
		{
			LdapUtils.disConnect(connect);
		}
		catch (LDAPException e)
		{
			e.printStackTrace();
		}
		break MISSING_BLOCK_LABEL_261;
		Exception exception;
		exception;
		try
		{
			LdapUtils.disConnect(connect);
		}
		catch (LDAPException e)
		{
			e.printStackTrace();
		}
		throw exception;
		try
		{
			LdapUtils.disConnect(connect);
		}
		catch (LDAPException e)
		{
			e.printStackTrace();
		}
		return userList;
	}

	public boolean judgeUserInGroup(String userId, String groupPath, boolean isRecursive)
		throws Exception
	{
		String userDn = getDnByUserId(userId);
		List groups = getGroupByUid(userDn, isRecursive);
		for (Iterator iterator = groups.iterator(); iterator.hasNext();)
		{
			LdapPrincipal group = (LdapPrincipal)iterator.next();
			if (groupPath.toLowerCase().equals(group.getPath().toLowerCase()))
				return true;
		}

		return false;
	}

	public List searchUser(String userName)
	{
		List principalList;
		LDAPConnection connect;
		String searchBase1;
		String searchBase2;
		principalList = new ArrayList();
		connect = null;
		searchBase1 = ZonyConfig.getLdapSearchBase_Org();
		searchBase2 = ZonyConfig.getLdapSearchBase_Usr();
		try
		{
			connect = LdapUtils.getConnect();
			String filter = (new StringBuilder("displayName=")).append(userName).append("*").toString();
			LDAPSearchResults searchResults = connect.search(searchBase1, 2, filter, null, false);
			LDAPSearchResults searchResults2 = connect.search(searchBase2, 2, filter, null, false);
			LdapPrincipal principal;
			for (; searchResults.hasMore(); principalList.add(principal))
			{
				principal = new LdapPrincipal();
				LDAPEntry nextEntry = null;
				nextEntry = searchResults.next();
				String dn = nextEntry.getDN();
				LDAPAttributeSet attributeSet = nextEntry.getAttributeSet();
				LDAPAttribute attribute;
				for (Iterator iterator = attributeSet.iterator(); iterator.hasNext(); System.out.println(attribute))
					attribute = (LDAPAttribute)iterator.next();

				String displayName = nextEntry.getAttribute("displayName") != null ? nextEntry.getAttribute("displayName").getStringValue() : "";
				String uid = nextEntry.getAttribute("sAMAccountName") != null ? nextEntry.getAttribute("sAMAccountName").getStringValue() : "";
				String ou = nextEntry.getAttribute("distinguishedName") != null ? nextEntry.getAttribute("distinguishedName").getStringValue() : "";
				String memberOf[] = nextEntry.getAttribute("memberOf") != null ? nextEntry.getAttribute("memberOf").getStringValueArray() : null;
				principal.setUid(uid);
				principal.setName(displayName);
				principal.setPath(dn);
				principal.setType("user");
				principal.setParentPath(ou);
				principal.setMemberOf(memberOf);
			}

			LdapPrincipal principal;
			for (; searchResults2.hasMore(); principalList.add(principal))
			{
				principal = new LdapPrincipal();
				LDAPEntry nextEntry = null;
				nextEntry = searchResults2.next();
				LDAPAttributeSet attributeSet = nextEntry.getAttributeSet();
				LDAPAttribute attribute;
				for (Iterator iterator = attributeSet.iterator(); iterator.hasNext(); System.out.println(attribute))
					attribute = (LDAPAttribute)iterator.next();

				String cn = nextEntry.getAttribute("cn") != null ? nextEntry.getAttribute("cn").getStringValue() : "";
				String dn = nextEntry.getDN();
				String displayName = nextEntry.getAttribute("displayName") != null ? nextEntry.getAttribute("displayName").getStringValue() : "";
				String uid = nextEntry.getAttribute("sAMAccountName") != null ? nextEntry.getAttribute("sAMAccountName").getStringValue() : "";
				String ou = nextEntry.getAttribute("distinguishedName") != null ? nextEntry.getAttribute("distinguishedName").getStringValue() : "";
				String memberOf[] = nextEntry.getAttribute("memberOf") != null ? nextEntry.getAttribute("memberOf").getStringValueArray() : null;
				principal.setUid(uid);
				principal.setName(displayName);
				principal.setPath(dn);
				principal.setType("user");
				principal.setParentPath(ou);
				principal.setMemberOf(memberOf);
			}

			break MISSING_BLOCK_LABEL_632;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		try
		{
			LdapUtils.disConnect(connect);
		}
		catch (LDAPException e)
		{
			e.printStackTrace();
		}
		break MISSING_BLOCK_LABEL_646;
		Exception exception;
		exception;
		try
		{
			LdapUtils.disConnect(connect);
		}
		catch (LDAPException e)
		{
			e.printStackTrace();
		}
		throw exception;
		try
		{
			LdapUtils.disConnect(connect);
		}
		catch (LDAPException e)
		{
			e.printStackTrace();
		}
		return principalList;
	}

	public String getDnByGroupName(String groupName)
		throws Exception
	{
		LDAPConnection connect = null;
		String searchBase = ZonyConfig.getLdapSearchBase_Def();
		connect = LdapUtils.getConnect();
		String filter = (new StringBuilder("name=")).append(groupName).toString();
		LDAPSearchResults searchResults = connect.search(searchBase, 2, filter, null, false);
		if (searchResults.getCount() > 1)
			throw new Exception();
		if (searchResults.hasMore())
		{
			LDAPEntry nextEntry = null;
			nextEntry = searchResults.next();
			return nextEntry.getDN();
		} else
		{
			return null;
		}
	}

	public String getDnByUserId(String userId)
		throws Exception
	{
		LDAPConnection connect = null;
		String searchBase_org = ZonyConfig.getLdapSearchBase_Org();
		String searchBase_usr = ZonyConfig.getLdapSearchBase_Usr();
		connect = LdapUtils.getConnect();
		String filter = (new StringBuilder("sAMAccountName=")).append(userId).toString();
		LDAPSearchResults searchResults = connect.search(searchBase_org, 2, filter, null, false);
		LDAPSearchResults searchResults2 = connect.search(searchBase_usr, 2, filter, null, false);
		if (searchResults.getCount() + searchResults2.getCount() > 1)
			throw new Exception();
		if (searchResults.hasMore())
		{
			LDAPEntry nextEntry = null;
			nextEntry = searchResults.next();
			return nextEntry.getDN();
		}
		if (searchResults2.hasMore())
		{
			LDAPEntry nextEntry = null;
			nextEntry = searchResults2.next();
			return nextEntry.getDN();
		} else
		{
			return null;
		}
	}

	public LdapPrincipal logon(String userId, String password)
		throws Exception
	{
		LdapPrincipal user = new LdapPrincipal();
		String userDn = getDnByUserId(userId);
		LdapPrincipal ldapUser = getPrincipal(userDn);
		boolean correct = LdapUtils.validateUser(ldapUser.getPath(), password);
		if (!correct)
			user = null;
		return user;
	}
}
