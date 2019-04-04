// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PolicySendConfig.java

package com.zony.app.domain;


public class PolicySendConfig
{

	private Long id;
	private String CHN;
	private String CHNName;
	private String projectName;
	private String orgCode;
	private String orgName;
	private String productCode;
	public String productName;
	private Long isAutoSend;

	public PolicySendConfig()
	{
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getCHN()
	{
		return CHN;
	}

	public void setCHN(String cHN)
	{
		CHN = cHN;
	}

	public String getCHNName()
	{
		return CHNName;
	}

	public void setCHNName(String cHNName)
	{
		CHNName = cHNName;
	}

	public String getProjectName()
	{
		return projectName;
	}

	public void setProjectName(String projectName)
	{
		this.projectName = projectName;
	}

	public String getOrgCode()
	{
		return orgCode;
	}

	public void setOrgCode(String orgCode)
	{
		this.orgCode = orgCode;
	}

	public String getOrgName()
	{
		return orgName;
	}

	public void setOrgName(String orgName)
	{
		this.orgName = orgName;
	}

	public String getProductCode()
	{
		return productCode;
	}

	public void setProductCode(String productCode)
	{
		this.productCode = productCode;
	}

	public String getProductName()
	{
		return productName;
	}

	public void setProductName(String productName)
	{
		this.productName = productName;
	}

	public Long getIsAutoSend()
	{
		return isAutoSend;
	}

	public void setIsAutoSend(Long isAutoSend)
	{
		this.isAutoSend = isAutoSend;
	}
}
