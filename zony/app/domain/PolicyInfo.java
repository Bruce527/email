// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PolicyInfo.java

package com.zony.app.domain;


public class PolicyInfo
{

	private Long id;
	private String letterctl;
	private String cntrno;
	private String old_cntrno;
	private String prpsno;
	private String coycd;
	private String brcd;
	private String chn;
	private String ownerName;
	private String ownerIDNumber;
	private String ownerSex;
	private String trancode;
	private String letterType;
	private String mtsNoAcknowledgement;
	private String mtsProjectBankCode;
	private String ownerMail;
	private String projectName;
	private String productCode;
	private String sendStatus;
	private String checkStatus;
	private String emailCode;
	private String locker;
	private String lockDate;
	private Long newID;
	private String packageName;
	private String filePath;
	private String printPolicyIndicator;
	private String pol_AcknowledgementDate;
	private String sender;
	private String sendDate;
	private String checker;
	private String checkDate;
	private String readDate;
	private String signStatus;
	private String policyType;
	private String importDate;
	private String outdateStatus;
	private String note;
	private Long priority;

	public PolicyInfo()
	{
	}

	public String getOutdateStatus()
	{
		return outdateStatus;
	}

	public void setOutdateStatus(String outdateStatus)
	{
		this.outdateStatus = outdateStatus;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getCntrno()
	{
		return cntrno;
	}

	public void setCntrno(String cntrno)
	{
		this.cntrno = cntrno;
	}

	public String getPrpsno()
	{
		return prpsno;
	}

	public void setPrpsno(String prpsno)
	{
		this.prpsno = prpsno;
	}

	public String getCoycd()
	{
		return coycd;
	}

	public void setCoycd(String coycd)
	{
		this.coycd = coycd;
	}

	public String getBrcd()
	{
		return brcd;
	}

	public void setBrcd(String brcd)
	{
		this.brcd = brcd;
	}

	public String getChn()
	{
		return chn;
	}

	public void setChn(String chn)
	{
		this.chn = chn;
	}

	public String getOwnerName()
	{
		return ownerName;
	}

	public void setOwnerName(String ownerName)
	{
		this.ownerName = ownerName;
	}

	public String getOwnerSex()
	{
		return ownerSex;
	}

	public void setOwnerSex(String ownerSex)
	{
		this.ownerSex = ownerSex;
	}

	public String getTrancode()
	{
		return trancode;
	}

	public void setTrancode(String trancode)
	{
		this.trancode = trancode;
	}

	public String getLetterType()
	{
		return letterType;
	}

	public void setLetterType(String letterType)
	{
		this.letterType = letterType;
	}

	public String getMtsNoAcknowledgement()
	{
		return mtsNoAcknowledgement;
	}

	public void setMtsNoAcknowledgement(String mtsNoAcknowledgement)
	{
		this.mtsNoAcknowledgement = mtsNoAcknowledgement;
	}

	public String getMtsProjectBankCode()
	{
		return mtsProjectBankCode;
	}

	public void setMtsProjectBankCode(String mtsProjectBankCode)
	{
		this.mtsProjectBankCode = mtsProjectBankCode;
	}

	public String getOwnerIDNumber()
	{
		return ownerIDNumber;
	}

	public void setOwnerIDNumber(String ownerIDNumber)
	{
		this.ownerIDNumber = ownerIDNumber;
	}

	public String getOwnerMail()
	{
		return ownerMail;
	}

	public void setOwnerMail(String ownerMail)
	{
		this.ownerMail = ownerMail;
	}

	public String getProjectName()
	{
		return projectName;
	}

	public void setProjectName(String projectName)
	{
		this.projectName = projectName;
	}

	public String getProductCode()
	{
		return productCode;
	}

	public void setProductCode(String productCode)
	{
		this.productCode = productCode;
	}

	public String getSendStatus()
	{
		return sendStatus;
	}

	public void setSendStatus(String sendStatus)
	{
		this.sendStatus = sendStatus;
	}

	public String getSignStatus()
	{
		return signStatus;
	}

	public void setSignStatus(String signStatus)
	{
		this.signStatus = signStatus;
	}

	public String getCheckStatus()
	{
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus)
	{
		this.checkStatus = checkStatus;
	}

	public String getEmailCode()
	{
		return emailCode;
	}

	public void setEmailCode(String emailCode)
	{
		this.emailCode = emailCode;
	}

	public String getPolicyType()
	{
		return policyType;
	}

	public void setPolicyType(String policyType)
	{
		this.policyType = policyType;
	}

	public String getLocker()
	{
		return locker;
	}

	public void setLocker(String locker)
	{
		this.locker = locker;
	}

	public String getLockDate()
	{
		return lockDate;
	}

	public void setLockDate(String lockDate)
	{
		this.lockDate = lockDate;
	}

	public Long getNewID()
	{
		return newID;
	}

	public void setNewID(Long newID)
	{
		this.newID = newID;
	}

	public String getPackageName()
	{
		return packageName;
	}

	public void setPackageName(String packageName)
	{
		this.packageName = packageName;
	}

	public String getFilePath()
	{
		return filePath;
	}

	public void setFilePath(String filePath)
	{
		this.filePath = filePath;
	}

	public String getPol_AcknowledgementDate()
	{
		return pol_AcknowledgementDate;
	}

	public void setPol_AcknowledgementDate(String pol_AcknowledgementDate)
	{
		this.pol_AcknowledgementDate = pol_AcknowledgementDate;
	}

	public String getSender()
	{
		return sender;
	}

	public void setSender(String sender)
	{
		this.sender = sender;
	}

	public String getChecker()
	{
		return checker;
	}

	public void setChecker(String checker)
	{
		this.checker = checker;
	}

	public String getReadDate()
	{
		return readDate;
	}

	public void setReadDate(String readDate)
	{
		this.readDate = readDate;
	}

	public String getSendDate()
	{
		return sendDate;
	}

	public void setSendDate(String sendDate)
	{
		this.sendDate = sendDate;
	}

	public String getCheckDate()
	{
		return checkDate;
	}

	public void setCheckDate(String checkDate)
	{
		this.checkDate = checkDate;
	}

	public String getOld_cntrno()
	{
		return old_cntrno;
	}

	public void setOld_cntrno(String old_cntrno)
	{
		this.old_cntrno = old_cntrno;
	}

	public String getImportDate()
	{
		return importDate;
	}

	public void setImportDate(String importDate)
	{
		this.importDate = importDate;
	}

	public String getPrintPolicyIndicator()
	{
		return printPolicyIndicator;
	}

	public void setPrintPolicyIndicator(String printPolicyIndicator)
	{
		this.printPolicyIndicator = printPolicyIndicator;
	}

	public String getLetterctl()
	{
		return letterctl;
	}

	public void setLetterctl(String letterctl)
	{
		this.letterctl = letterctl;
	}

	public String getNote()
	{
		return note;
	}

	public void setNote(String note)
	{
		this.note = note;
	}

	public Long getPriority()
	{
		return priority;
	}

	public void setPriority(Long priority)
	{
		this.priority = priority;
	}
}
