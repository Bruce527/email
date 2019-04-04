// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PolicySendLogic.java

package com.zony.app.logic;

import com.zony.app.dao.*;
import com.zony.app.domain.*;
import com.zony.common.config.ZonyConfig;
import com.zony.common.util.*;
import java.io.*;
import java.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.CellRangeAddress;

// Referenced classes of package com.zony.app.logic:
//			LoginLogic, PolicyLogLogic

public class PolicySendLogic
{

	VpolicyInfoDao vPolicyInfoDao;
	PolicyInfoDao policyInfoDao;
	VpolicyLogDao vpolicyLogDao;
	PolicyLogDao policyLogDao;
	PolicyLogLogic policyLogLogic;
	private LoginLogic loginLogic;

	public PolicySendLogic()
	{
	}

	public List seachPolicyInfoByParams(String sendStatus, PolicyInfo policyInfo, String rolesHql, String beginDate, String endDate)
		throws Throwable
	{
		policyInfo = (PolicyInfo)StringUtil.cleanQueryField(policyInfo);
		String whereStr = "";
		if (!StringUtils.isEmpty(policyInfo.getBrcd()))
		{
			whereStr = loginLogic.getBaseSqlByOrgCode(policyInfo.getBrcd());
			policyInfo.setBrcd(null);
		}
		if (!StringUtils.isEmpty(StringUtil.createWhereStr(policyInfo)))
		{
			if (!StringUtils.isEmpty(whereStr.trim()))
				whereStr = (new StringBuilder(String.valueOf(whereStr))).append(" and ").toString();
			whereStr = (new StringBuilder(String.valueOf(whereStr))).append(StringUtil.createWhereStr(policyInfo)).toString();
		} else
		{
			whereStr = (new StringBuilder(String.valueOf(whereStr))).append(StringUtil.createWhereStr(policyInfo)).toString();
		}
		if (StringUtils.isNotEmpty(whereStr.trim()))
			whereStr = (new StringBuilder(String.valueOf(whereStr))).append(" and ").toString();
		whereStr = (new StringBuilder(String.valueOf(whereStr))).append(rolesHql).append(" and sendStatus in (").append(sendStatus).append(")").toString();
		if (beginDate != null && !StringUtils.isEmpty(beginDate))
			whereStr = (new StringBuilder(String.valueOf(whereStr))).append("and pol_AcknowledgementDate>='").append(beginDate).append("' ").toString();
		if (endDate != null && !StringUtils.isEmpty(endDate))
			whereStr = (new StringBuilder(String.valueOf(whereStr))).append("and pol_AcknowledgementDate<='").append(endDate).append("' ").toString();
		return vPolicyInfoDao.doSearchByWhereStr(whereStr);
	}

	public List seachALLPolicyInfoByParams(String sendStatus, PolicyInfo policyInfo, String rolesHql)
		throws Throwable
	{
		policyInfo = (PolicyInfo)StringUtil.cleanQueryField(policyInfo);
		String whereStr = StringUtil.createWhereStr(policyInfo);
		if (StringUtils.isNotEmpty(whereStr.trim()))
			whereStr = (new StringBuilder(String.valueOf(whereStr))).append(" and ").toString();
		whereStr = (new StringBuilder(String.valueOf(whereStr))).append(rolesHql).append(" and sendStatus in (").append(sendStatus).append(")").toString();
		return vPolicyInfoDao.doSearchALLByWhereStr(whereStr);
	}

	public String getFilePath(Long id)
	{
		String filePath = ((PolicyInfo)policyInfoDao.getById(id)).getFilePath();
		if (StringUtils.isNotEmpty(filePath))
			return filePath;
		else
			return "";
	}

	public PolicyInfo getPolicyInfo(Long id)
	{
		return (PolicyInfo)policyInfoDao.getById(id);
	}

	public VpolicyInfo getVpolicyInfo(Long id)
	{
		return (VpolicyInfo)vPolicyInfoDao.getById(id);
	}

	public PolicyInfo getLastVerPolicyInfo(Long id)
	{
		PolicyInfo policyInfo = (PolicyInfo)policyInfoDao.getById(id);
		if (policyInfo != null && policyInfo.getId().longValue() > 0L)
		{
			if (policyInfo.getNewID() != null && policyInfo.getNewID().longValue() > 0L && !policyInfo.getSendStatus().equals("1"))
				return getLastVerPolicyInfo(policyInfo.getNewID());
			else
				return policyInfo;
		} else
		{
			return null;
		}
	}

	public List getVpolicyLogByPId(Long policyId)
	{
		return vpolicyLogDao.getVpolicyLogInfo(policyId);
	}

	public boolean setSendStatus(List ids, String sendStatus, String sendDate)
	{
		if (ids != null && ids.size() > 0)
		{
			String hql = (new StringBuilder("update PolicyInfo set sendStatus='")).append(sendStatus).append("'").toString();
			if (StringUtils.isNotEmpty(sendDate))
				hql = (new StringBuilder(String.valueOf(hql))).append(",sendDate='").append(sendDate).append("'").toString();
			else
			if ("-1".equals(sendStatus))
				hql = (new StringBuilder(String.valueOf(hql))).append(",sendDate=''").toString();
			if (!"1".equals(sendStatus) && !"2".equals(sendStatus) && !"3".equals(sendStatus) && !"-1".equals(sendStatus))
				hql = (new StringBuilder(String.valueOf(hql))).append(",outdateStatus='3'").toString();
			hql = (new StringBuilder(String.valueOf(hql))).append(" where id in (").toString();
			for (Iterator iterator = ids.iterator(); iterator.hasNext();)
			{
				String id = (String)iterator.next();
				hql = (new StringBuilder(String.valueOf(hql))).append(id).append(",").toString();
			}

			hql = (new StringBuilder(String.valueOf(hql.substring(0, hql.length() - 1)))).append(")").toString();
			policyInfoDao.bulkUpdate(hql, null);
			return true;
		} else
		{
			return false;
		}
	}

	public PolicyInfo setSignStatus(String mailCode, String signStatus, String checkBackDate)
	{
		String hql = (new StringBuilder("from PolicyInfo where emailCode='")).append(mailCode).append("'").toString();
		PolicyInfo policyInfo = (PolicyInfo)policyInfoDao.getEntity(hql, new Object[0]);
		if (policyInfo != null && policyInfo.getId().longValue() > 0L)
		{
			if ((signStatus.equals("2") || signStatus.equals("4")) && policyInfo.getSignStatus() != null && (policyInfo.getSignStatus().equals("2") || policyInfo.getSignStatus().equals("3") || policyInfo.getSignStatus().equals("5") || policyInfo.getSignStatus().equals("3") || policyInfo.getSignStatus().equals("6")))
			{
				return policyInfo;
			} else
			{
				hql = (new StringBuilder("update PolicyInfo set signStatus='")).append(signStatus).append("', pol_AcknowledgementDate='").append(checkBackDate).append("' where id=").append(policyInfo.getId()).toString();
				policyInfoDao.bulkUpdate(hql);
				return policyInfo;
			}
		} else
		{
			return null;
		}
	}

	public PolicyInfo setSendStatusByEmailCode(String emailCode, String sendStatus)
	{
		return setSendStatusAndDateByEmailCode(emailCode, sendStatus, null);
	}

	public PolicyInfo setSendStatusAndDateByEmailCode(String emailCode, String sendStatus, String sendDate)
	{
		String hql = (new StringBuilder("from PolicyInfo where emailCode='")).append(emailCode).append("'").toString();
		PolicyInfo policyInfo = (PolicyInfo)policyInfoDao.getEntity(hql, null);
		if (policyInfo != null && policyInfo.getId().longValue() > 0L)
		{
			policyInfo.setSendStatus(sendStatus);
			if (sendDate != null && !sendDate.isEmpty())
				policyInfo.setSendDate(sendDate);
			if (!"1".equals(sendStatus) && !"2".equals(sendStatus) && !"3".equals(sendStatus) && !"-1".equals(sendStatus))
				policyInfo.setOutdateStatus("3");
			policyInfoDao.update(policyInfo);
			return policyInfo;
		} else
		{
			return null;
		}
	}

	public PolicyInfo setSendStatusByPrpsno(String prpsno, String pol_AcknowledgementDate)
	{
		String hql = (new StringBuilder("from PolicyInfo where prpsno='")).append(prpsno).append("' and outdatestatus != '").append("1").append("' and ( pol_AcknowledgementDate>'").append(pol_AcknowledgementDate).append(" 00:00:00").append("' or pol_AcknowledgementDate ='0' or pol_AcknowledgementDate = null)").toString();
		PolicyInfo policyInfo = (PolicyInfo)policyInfoDao.getEntity(hql, null);
		if (policyInfo != null && policyInfo.getId().longValue() > 0L)
		{
			String updateTime = (new StringBuilder(String.valueOf(pol_AcknowledgementDate.substring(0, 4)))).append("-").append(pol_AcknowledgementDate.substring(4, 6)).append("-").append(pol_AcknowledgementDate.substring(6)).toString();
			hql = (new StringBuilder("update PolicyInfo set signStatus='5',pol_AcknowledgementDate='")).append(updateTime).append(" 00:00:00").append("' where id=").append(policyInfo.getId()).toString();
			policyInfoDao.bulkUpdate(hql);
			return policyInfo;
		} else
		{
			return null;
		}
	}

	public PolicyLog getPolicyLogByPId(String policyId)
	{
		List policyLogList = policyLogDao.getEntityList((new StringBuilder("from PolicyLog where policyID=")).append(Long.parseLong(policyId)).toString(), null);
		Long maxId = Long.valueOf(0L);
		if (policyLogList != null && policyLogList.size() > 0)
		{
			for (Iterator iterator = policyLogList.iterator(); iterator.hasNext();)
			{
				PolicyLog policyLog = (PolicyLog)iterator.next();
				if (policyLog.getId().longValue() > maxId.longValue())
					maxId = policyLog.getId();
			}

			for (Iterator iterator1 = policyLogList.iterator(); iterator1.hasNext();)
			{
				PolicyLog policyLog = (PolicyLog)iterator1.next();
				if (maxId == policyLog.getId())
					return policyLog;
			}

		}
		return null;
	}

	public void setSendStatusYDCS()
	{
		String hql = "from PolicyInfo where sendStatus='4'";
		List policyInfoList = policyInfoDao.getEntityList(hql, null);
		for (Iterator iterator = policyInfoList.iterator(); iterator.hasNext();)
		{
			PolicyInfo policyInfo = (PolicyInfo)iterator.next();
			String sendDateStr = policyInfo.getSendDate();
			if (StringUtils.isNotEmpty(sendDateStr))
			{
				Date sendDate = DateUtil.parseDate(sendDateStr, "yyyy-MM-dd HH:mm:ss");
				Long dateTimes = Long.valueOf(sendDate.getTime());
				dateTimes = Long.valueOf(dateTimes.longValue() + 0xf731400L);
				if (dateTimes.longValue() < (new Date()).getTime())
				{
					hql = (new StringBuilder("update PolicyInfo set sendStatus='-2' where id=")).append(policyInfo.getId()).toString();
					policyInfoDao.bulkUpdate(hql);
					policyLogLogic.saveLogWithError(policyInfo.getId(), "18", "阅读超时，发送邮件失败！", null);
				}
			}
		}

	}

	public static void main(String args[])
	{
		Date sendDate = DateUtil.parseDate("2014-04-01 08:22:00", "yyyy-MM-dd HH:mm:ss");
		Long dateTimes = Long.valueOf(sendDate.getTime());
		dateTimes = Long.valueOf(dateTimes.longValue() + 0xf731400L);
		sendDate = new Date(dateTimes.longValue());
		System.out.println(DateUtil.dateToString(sendDate));
		if (dateTimes.longValue() < (new Date()).getTime())
			System.out.println("old ");
	}

	public List getPolicyInfoSigned(String baseRightSql, String beginDate, String endDate)
	{
		String timeCon = "";
		if (beginDate != null && StringUtils.isNotEmpty(beginDate))
			timeCon = (new StringBuilder(String.valueOf(timeCon))).append(" and logDate >= '").append(beginDate).append("'").toString();
		if (endDate != null && StringUtils.isNotEmpty(endDate))
			timeCon = (new StringBuilder(String.valueOf(timeCon))).append(" and logDate <= '").append(endDate).append("'").toString();
		String hql = (new StringBuilder("from PolicyInfo where ")).append(baseRightSql).append("  and ( signStatus in ('").append("2").append("','").append("5").append("','").append("4").append("','").append("3").append("') and pol_AcknowledgementDate is not null ) and id in (select policyID from PolicyLog where logEvent in(").append("12").append(",").append("14").append(",").append("15").append(",").append("16").append(") ").append(timeCon).append(")").toString();
		return policyInfoDao.getEntityList(hql, null);
	}

	public String writeVPolicyInfoToXLS(List pinfoList)
		throws FileNotFoundException, IOException
	{
		if (pinfoList == null || pinfoList.size() < 1)
			return null;
		String excelOutPath = (new StringBuilder(String.valueOf(ZonyConfig.getExcelTempPath()))).append("/LA_").append(DateUtil.getNow("yyyy-MM-dd-HH-mm-ss")).append(".xls").toString();
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("发送结果报表");
		CellRangeAddress region = new CellRangeAddress(0, 0, 1, 13);
		sheet.addMergedRegion(region);
		HSSFRow row = sheet.createRow(0);
		row.setHeight((short)550);
		HSSFCell cell = row.createCell(1);
		cell.setCellValue("《邮件管理系统 》查询发送结果报表");
		cell.setCellStyle(JexcelFiles.getHeader(wb));
		region = new CellRangeAddress(1, 1, 1, 13);
		sheet.addMergedRegion(region);
		row = sheet.createRow(1);
		row.setHeight((short)350);
		cell = row.createCell(1);
		cell.setCellValue((new StringBuilder("查询结果导出时间：")).append(DateUtil.getNow()).toString());
		cell.setCellStyle(JexcelFiles.getDateHeader(wb));
		HSSFRow row3 = sheet.createRow(2);
		row3.setHeight((short)350);
		cell = row3.createCell(1);
		cell.setCellValue("保单号");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		cell = row3.createCell(2);
		cell.setCellValue("投保单号");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		cell = row3.createCell(3);
		cell.setCellValue("分公司");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		cell = row3.createCell(4);
		cell.setCellValue("渠道");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		cell = row3.createCell(5);
		cell.setCellValue("保单类型");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		cell = row3.createCell(6);
		cell.setCellValue("项目名称");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		cell = row3.createCell(7);
		cell.setCellValue("产品代码");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		cell = row3.createCell(8);
		cell.setCellValue("险种组装名称");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		cell = row3.createCell(9);
		cell.setCellValue("投保人姓名");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		cell = row3.createCell(10);
		cell.setCellValue("发送邮箱");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		cell = row3.createCell(11);
		cell.setCellValue("发送状态");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		cell = row3.createCell(12);
		cell.setCellValue("发送日期");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		cell = row3.createCell(13);
		cell.setCellValue("备注说明");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		HSSFCellStyle hssfcellstyle = JexcelFiles.getNormolCell(wb);
		HSSFCellStyle leftcellstyle = JexcelFiles.getNormolCell("left", wb);
		int rowNum = 3;
		for (Iterator iterator = pinfoList.iterator(); iterator.hasNext();)
		{
			VpolicyInfo vpolicy = (VpolicyInfo)iterator.next();
			row = sheet.createRow(rowNum);
			row.setHeight((short)350);
			cell = row.createCell(1);
			cell.setCellValue(vpolicy.getCntrno());
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(2);
			cell.setCellValue(vpolicy.getPrpsno());
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(3);
			if (vpolicy.getOrgName() != null && StringUtils.isNotEmpty(vpolicy.getOrgName()))
				cell.setCellValue(vpolicy.getOrgName());
			else
				cell.setCellValue("");
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(4);
			if (vpolicy.getGroupName() != null && StringUtils.isNotEmpty(vpolicy.getGroupName()))
				cell.setCellValue(vpolicy.getGroupName());
			else
				cell.setCellValue("");
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(5);
			if (vpolicy.getPolicyTypeName() != null && StringUtils.isNotEmpty(vpolicy.getPolicyTypeName()))
				cell.setCellValue(vpolicy.getPolicyTypeName());
			else
				cell.setCellValue("");
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(6);
			if (vpolicy.getProjectName() != null && StringUtils.isNotEmpty(vpolicy.getProjectName()))
				cell.setCellValue(vpolicy.getProjectName());
			else
				cell.setCellValue("");
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(7);
			if (vpolicy.getProductCode() != null && StringUtils.isNotEmpty(vpolicy.getProductCode()))
				cell.setCellValue(vpolicy.getProductCode());
			else
				cell.setCellValue("");
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(8);
			if (vpolicy.getPackageName() != null && StringUtils.isNotEmpty(vpolicy.getPackageName()))
				cell.setCellValue(vpolicy.getPackageName());
			else
				cell.setCellValue("");
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(9);
			if (vpolicy.getOwnerName() != null && StringUtils.isNotEmpty(vpolicy.getOwnerName()))
				cell.setCellValue(vpolicy.getOwnerName());
			else
				cell.setCellValue("");
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(10);
			if (vpolicy.getOwnerMail() != null && StringUtils.isNotEmpty(vpolicy.getOwnerMail()))
				cell.setCellValue(vpolicy.getOwnerMail());
			else
				cell.setCellValue("");
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(11);
			if (vpolicy.getSendStatus() != null && StringUtils.isNotEmpty(vpolicy.getSendStatus()))
				cell.setCellValue(vpolicy.getSendStatusName());
			else
				cell.setCellValue("");
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(12);
			if (vpolicy.getSendDate() != null && StringUtils.isNotEmpty(vpolicy.getSendDate()))
				cell.setCellValue(vpolicy.getSendDate());
			else
				cell.setCellValue("");
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(13);
			if (vpolicy.getNote() != null && StringUtils.isNotEmpty(vpolicy.getNote()))
				cell.setCellValue(vpolicy.getNote());
			else
				cell.setCellValue("");
			cell.setCellStyle(leftcellstyle);
			rowNum++;
		}

		sheet.setColumnWidth(1, 3500);
		sheet.setColumnWidth(2, 4000);
		sheet.setColumnWidth(3, 3000);
		sheet.setColumnWidth(4, 4000);
		sheet.setColumnWidth(5, 4000);
		sheet.setColumnWidth(6, 4000);
		sheet.setColumnWidth(7, 4000);
		sheet.setColumnWidth(8, 4000);
		sheet.setColumnWidth(9, 4000);
		sheet.setColumnWidth(10, 4000);
		sheet.setColumnWidth(11, 4000);
		sheet.setColumnWidth(12, 4000);
		sheet.setColumnWidth(13, 20000);
		FileOutputStream fileOut = new FileOutputStream(excelOutPath);
		wb.write(fileOut);
		fileOut.close();
		return excelOutPath;
	}

	public String writeLAToXLS(List pinfoList)
		throws FileNotFoundException, IOException
	{
		if (pinfoList == null || pinfoList.size() < 1)
			return null;
		String excelOutPath = (new StringBuilder(String.valueOf(ZonyConfig.getExcelTempPath()))).append("/LA_").append(DateUtil.getNow("yyyy-MM-dd-HH-mm-ss")).append(".xls").toString();
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("LA系统回执报表");
		HSSFRow row3 = sheet.createRow(0);
		row3.setHeight((short)350);
		HSSFCell cell = row3.createCell(0);
		cell.setCellValue("投保单号");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		cell = row3.createCell(1);
		cell.setCellValue("回执签收日期");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		HSSFCellStyle hssfcellstyle = JexcelFiles.getNormolCell(wb);
		int rowNum = 1;
		for (Iterator iterator = pinfoList.iterator(); iterator.hasNext();)
		{
			PolicyInfo policyInfo = (PolicyInfo)iterator.next();
			row3 = sheet.createRow(rowNum);
			row3.setHeight((short)350);
			cell = row3.createCell(0);
			cell.setCellValue(policyInfo.getPrpsno());
			cell.setCellStyle(hssfcellstyle);
			cell = row3.createCell(1);
			if (policyInfo.getPol_AcknowledgementDate() != null && StringUtils.isNotEmpty(policyInfo.getPol_AcknowledgementDate()))
				cell.setCellValue(DateUtil.getSampleDate(policyInfo.getPol_AcknowledgementDate()));
			cell.setCellStyle(hssfcellstyle);
			rowNum++;
		}

		sheet.setColumnWidth(0, 6000);
		sheet.setColumnWidth(1, 6000);
		FileOutputStream fileOut = new FileOutputStream(excelOutPath);
		wb.write(fileOut);
		fileOut.close();
		return excelOutPath;
	}

	public void updateOutDateStatus(List ids)
	{
		String idsStr = "";
		for (Iterator iterator = ids.iterator(); iterator.hasNext();)
		{
			Long id = (Long)iterator.next();
			idsStr = (new StringBuilder(String.valueOf(idsStr))).append(id).append(",").toString();
		}

		idsStr = idsStr.substring(0, idsStr.length() - 1);
		String hql = (new StringBuilder("update PolicyInfo set sendStatus='-1' where id in(")).append(idsStr).append(")").toString();
		policyInfoDao.bulkUpdate(hql);
	}

	public void saveNote(Long policyId, String note)
	{
		String hql = (new StringBuilder("update PolicyInfo set note = ? where id=")).append(policyId).toString();
		policyInfoDao.bulkUpdate(hql, note);
	}
}
