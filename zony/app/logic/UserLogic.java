// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   UserLogic.java

package com.zony.app.logic;

import com.novell.ldap.*;
import com.zony.app.dao.UserDao;
import com.zony.app.domain.LdapPrincipal;
import com.zony.app.domain.User;
import com.zony.common.config.ZonyConfig;
import com.zony.common.util.Globals;
import com.zony.common.util.LdapUtils;
import java.io.PrintStream;
import java.util.Hashtable;
import javax.naming.directory.InitialDirContext;

public class UserLogic
{

	private UserDao userDao;

	public UserLogic()
	{
	}

	public long saveUser(User user)
	{
		return userDao.saveUser(user);
	}

	public User getUserById(long id)
	{
		return userDao.getUserById(id);
	}

	public boolean queryDomainUser()
	{
		Hashtable env = new Hashtable();
		String domainIP = Globals.getProperty("ldap.url");
		String domainPort = Globals.getProperty("ldap.port");
		String domainName = Globals.getProperty("ldap.loginname");
		String loginpwd = Globals.getProperty("ldap.loginpwd");
		String systemuserid = Globals.getProperty("ldap.systemuserid");
		String LDAP_URL = (new StringBuilder("ldap://")).append(domainIP).append(":").append(domainPort).toString();
		String adminName = (new StringBuilder("DEMO\\")).append(systemuserid).toString();
		String adminPassword = loginpwd;
		env.put("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");
		env.put("java.naming.provider.url", LDAP_URL);
		env.put("java.naming.security.authentication", "simple");
		env.put("java.naming.security.principal", adminName);
		env.put("java.naming.security.credentials", adminPassword);
		javax.naming.directory.DirContext dc;
		try
		{
			dc = new InitialDirContext(env);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
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
			System.out.println(nextEntry.getDN());
			return nextEntry.getDN();
		}
		if (searchResults2.hasMore())
		{
			LDAPEntry nextEntry = null;
			nextEntry = searchResults2.next();
			System.out.println(nextEntry.getDN());
			return nextEntry.getDN();
		} else
		{
			return null;
		}
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
			break MISSING_BLOCK_LABEL_142;
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
			break MISSING_BLOCK_LABEL_174;
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
