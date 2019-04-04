// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PolicyInfoQueryLogic.java

package com.zony.app.logic;

import com.zony.app.dao.*;
import com.zony.app.domain.VpolicyInfo;
import com.zony.app.domain.VpolicyLog;
import com.zony.common.config.ZonyConfig;
import com.zony.common.util.*;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.CellRangeAddress;

// Referenced classes of package com.zony.app.logic:
//			LoginLogic

public class PolicyInfoQueryLogic
{

	VpolicyInfoDao vpolicyInfoDao;
	LoginLogic loginLogic;
	VpolicyLogDao vpolicyLogDao;
	PolicyInfoDao policyInfoDao;

	public PolicyInfoQueryLogic()
	{
	}

	public List getPolicyInfoByWhereStr(String querySql, String codes, int pageIndex)
	{
		String codeArr[] = codes.split(",");
		String userWhere = loginLogic.createUserBaseRight(codeArr[0], true);
		querySql = (new StringBuilder(String.valueOf(userWhere))).append(querySql).toString();
		if (codeArr.length > 1 && !StringUtils.isEmpty(codeArr[1]))
		{
			String brcoStr = (new StringBuilder(" and ")).append(loginLogic.getBaseSqlByOrgCode(codeArr[1])).toString();
			querySql = (new StringBuilder(String.valueOf(querySql))).append(brcoStr).toString();
		}
		if (pageIndex != -1)
		{
			PageUtil pageutil = new PageUtil(pageIndex, 20);
			List list = vpolicyInfoDao.doSearchByWhereStr(querySql, pageutil);
			return list;
		} else
		{
			List list = vpolicyInfoDao.doSearchByWhereStr(querySql);
			return list;
		}
	}

	public String writePolicyInfoToXLS(List policyInfoList)
		throws Exception
	{
		if (policyInfoList == null || policyInfoList.size() < 1)
			return null;
		String excelOutPath = (new StringBuilder(String.valueOf(ZonyConfig.getExcelTempPath()))).append("/LA_").append(DateUtil.getNow("yyyy-MM-dd-HH-mm-ss")).append(".xls").toString();
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("保单信息查询结果报表");
		CellRangeAddress region = new CellRangeAddress(0, 0, 1, 17);
		sheet.addMergedRegion(region);
		HSSFRow row = sheet.createRow(0);
		row.setHeight((short)550);
		HSSFCell cell = row.createCell(1);
		cell.setCellValue("《邮件管理系统 》保单信息查询结果报表");
		cell.setCellStyle(JexcelFiles.getHeader(wb));
		region = new CellRangeAddress(1, 1, 1, 17);
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
		cell.setCellValue("险种组装名称");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		cell = row3.createCell(8);
		cell.setCellValue("产品代码");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		cell = row3.createCell(9);
		cell.setCellValue("过时状态");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		cell = row3.createCell(10);
		cell.setCellValue("抽检状态");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		cell = row3.createCell(11);
		cell.setCellValue("发送状态");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		cell = row3.createCell(12);
		cell.setCellValue("回签状态");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		cell = row3.createCell(13);
		cell.setCellValue("发送时间");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		cell = row3.createCell(14);
		cell.setCellValue("回签时间");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		cell = row3.createCell(15);
		cell.setCellValue("锁定时间");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		cell = row3.createCell(16);
		cell.setCellValue("抽检锁定人");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		cell = row3.createCell(17);
		cell.setCellValue("备注说明");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		HSSFCellStyle hssfcellstyle = JexcelFiles.getNormolCell(wb);
		HSSFCellStyle leftcellstyle = JexcelFiles.getNormolCell("left", wb);
		int rowNum = 3;
		for (Iterator iterator = policyInfoList.iterator(); iterator.hasNext();)
		{
			VpolicyInfo vpolicyInfo = (VpolicyInfo)iterator.next();
			row = sheet.createRow(rowNum);
			row.setHeight((short)350);
			cell = row.createCell(1);
			cell.setCellValue(vpolicyInfo.getCntrno());
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(2);
			cell.setCellValue(vpolicyInfo.getPrpsno());
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(3);
			if (vpolicyInfo.getOrgName() != null && StringUtils.isNotEmpty(vpolicyInfo.getOrgName()))
				cell.setCellValue(vpolicyInfo.getOrgName());
			else
				cell.setCellValue("");
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(4);
			if (vpolicyInfo.getGroupName() != null && StringUtils.isNotEmpty(vpolicyInfo.getGroupName()))
				cell.setCellValue(vpolicyInfo.getGroupName());
			else
				cell.setCellValue("");
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(5);
			if (vpolicyInfo.getPolicyTypeName() != null && StringUtils.isNotEmpty(vpolicyInfo.getPolicyTypeName()))
				cell.setCellValue(vpolicyInfo.getPolicyTypeName());
			else
				cell.setCellValue("");
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(6);
			if (vpolicyInfo.getProjectName() != null && StringUtils.isNotEmpty(vpolicyInfo.getProjectName()))
				cell.setCellValue(vpolicyInfo.getProjectName());
			else
				cell.setCellValue("");
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(7);
			if (vpolicyInfo.getPackageName() != null && StringUtils.isNotEmpty(vpolicyInfo.getPackageName()))
				cell.setCellValue(vpolicyInfo.getPackageName());
			else
				cell.setCellValue("");
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(8);
			if (vpolicyInfo.getProductCode() != null && StringUtils.isNotEmpty(vpolicyInfo.getProductCode()))
				cell.setCellValue(vpolicyInfo.getProductCode());
			else
				cell.setCellValue("");
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(9);
			if (vpolicyInfo.getOutdateStatusName() != null && StringUtils.isNotEmpty(vpolicyInfo.getOutdateStatusName()))
				cell.setCellValue(vpolicyInfo.getOutdateStatusName());
			else
				cell.setCellValue("");
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(10);
			if (vpolicyInfo.getCheckStatusName() != null && StringUtils.isNotEmpty(vpolicyInfo.getCheckStatusName()))
				cell.setCellValue(vpolicyInfo.getCheckStatusName());
			else
				cell.setCellValue("");
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(11);
			if (vpolicyInfo.getSendStatusName() != null && StringUtils.isNotEmpty(vpolicyInfo.getSendStatusName()))
				cell.setCellValue(vpolicyInfo.getSendStatusName());
			else
				cell.setCellValue("");
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(12);
			if (vpolicyInfo.getSignStatus() != null && StringUtils.isNotEmpty(vpolicyInfo.getSignStatusName()))
				cell.setCellValue(vpolicyInfo.getSignStatusName());
			else
				cell.setCellValue("");
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(13);
			if (vpolicyInfo.getSendDate() != null && StringUtils.isNotEmpty(vpolicyInfo.getSendDate()))
				cell.setCellValue(vpolicyInfo.getSendDate());
			else
				cell.setCellValue("");
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(14);
			if (vpolicyInfo.getPol_AcknowledgementDate() != null && StringUtils.isNotEmpty(vpolicyInfo.getPol_AcknowledgementDate()))
				cell.setCellValue(vpolicyInfo.getPol_AcknowledgementDate());
			else
				cell.setCellValue("");
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(15);
			if (vpolicyInfo.getLockDate() != null && StringUtils.isNotEmpty(vpolicyInfo.getLockDate()))
				cell.setCellValue(vpolicyInfo.getLockDate());
			else
				cell.setCellValue("");
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(16);
			if (vpolicyInfo.getLocker() != null && StringUtils.isNotEmpty(vpolicyInfo.getLocker()))
				cell.setCellValue(vpolicyInfo.getLocker());
			else
				cell.setCellValue("");
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(17);
			if (vpolicyInfo.getNote() != null && StringUtils.isNotEmpty(vpolicyInfo.getNote()))
				cell.setCellValue(vpolicyInfo.getNote());
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
		sheet.setColumnWidth(7, 8000);
		sheet.setColumnWidth(8, 5000);
		sheet.setColumnWidth(9, 5000);
		sheet.setColumnWidth(10, 5000);
		sheet.setColumnWidth(11, 5000);
		sheet.setColumnWidth(12, 5000);
		sheet.setColumnWidth(13, 8000);
		sheet.setColumnWidth(14, 8000);
		sheet.setColumnWidth(15, 5000);
		sheet.setColumnWidth(16, 5000);
		sheet.setColumnWidth(17, 20000);
		FileOutputStream fileOut = new FileOutputStream(excelOutPath);
		wb.write(fileOut);
		fileOut.close();
		return excelOutPath;
	}

	public List getPolicyInfos(int pageIndex, VpolicyInfo vpolicyInfo, String startDate, String endDate, String rolesHQL)
	{
		String hql = (new StringBuilder("from VpolicyInfo where ")).append(rolesHQL).toString();
		VpolicyInfo policySearch = (VpolicyInfo)StringUtil.cleanQueryField(vpolicyInfo);
		String whereStr = "";
		if (!StringUtils.isEmpty(policySearch.getBrcd()))
		{
			whereStr = (new StringBuilder(String.valueOf(whereStr))).append(" and ").append(loginLogic.getBaseSqlByOrgCode(policySearch.getBrcd())).toString();
			policySearch.setBrcd(null);
		}
		String policySearchStr = StringUtil.createWhereStr(policySearch);
		if (StringUtils.isNotEmpty(policySearchStr))
			whereStr = (new StringBuilder(String.valueOf(whereStr))).append(" and ").append(policySearchStr).toString();
		if (StringUtils.isNotEmpty(startDate))
			whereStr = (new StringBuilder(String.valueOf(whereStr))).append(" and checkDate>='").append(startDate).append("'").toString();
		if (StringUtils.isNotEmpty(endDate))
			whereStr = (new StringBuilder(String.valueOf(whereStr))).append(" and checkDate<='").append(endDate).append("'").toString();
		if (StringUtils.isNotEmpty(whereStr.trim()))
			hql = (new StringBuilder(String.valueOf(hql))).append(whereStr).toString();
		PageUtil pageutil = new PageUtil(pageIndex, 20);
		List vpList = vpolicyInfoDao.getByPage(hql, null, pageutil);
		return vpList;
	}

	public List getPolicyLogInfo(int pageIndex, VpolicyLog vpolicylog, String startDate, String endDate, String rolesHQL)
	{
		String hql = "from VpolicyLog where ";
		VpolicyLog policyLogSearch = (VpolicyLog)StringUtil.cleanQueryField(vpolicylog);
		String whereStr = "";
		if (!StringUtils.isEmpty(policyLogSearch.getBrcd()))
		{
			whereStr = loginLogic.getBaseSqlByOrgCode(policyLogSearch.getBrcd());
			policyLogSearch.setBrcd(null);
		}
		if (!StringUtils.isEmpty(StringUtil.createWhereStr(policyLogSearch)))
		{
			if (!StringUtils.isEmpty(whereStr.trim()))
				whereStr = (new StringBuilder(String.valueOf(whereStr))).append(" and ").toString();
			whereStr = (new StringBuilder(String.valueOf(whereStr))).append(StringUtil.createWhereStr(policyLogSearch)).toString();
		} else
		{
			whereStr = (new StringBuilder(String.valueOf(whereStr))).append(StringUtil.createWhereStr(policyLogSearch)).toString();
		}
		if (StringUtils.isNotEmpty(whereStr.trim()))
			whereStr = (new StringBuilder(String.valueOf(whereStr))).append(" and ").toString();
		if (StringUtils.isNotEmpty(startDate))
			whereStr = (new StringBuilder(String.valueOf(whereStr))).append(" logDate>='").append(startDate).append("' and ").toString();
		if (StringUtils.isNotEmpty(endDate))
			whereStr = (new StringBuilder(String.valueOf(whereStr))).append(" logDate<='").append(endDate).append("' and ").toString();
		hql = (new StringBuilder(String.valueOf(hql))).append(whereStr).append(rolesHQL).toString();
		PageUtil pageutil = new PageUtil(pageIndex, 20);
		List vplist = vpolicyLogDao.getByPage(hql, null, pageutil);
		return vplist;
	}

	public List getPolicyLogInfoForOutXLS(VpolicyLog vpolicyLog, String startDate, String endDate, String rolesHQL)
	{
		String hql = "from VpolicyLog where ";
		VpolicyLog policyLogSearch = (VpolicyLog)StringUtil.cleanQueryField(vpolicyLog);
		String whereStr = "";
		if (!StringUtils.isEmpty(policyLogSearch.getBrcd()))
		{
			whereStr = loginLogic.getBaseSqlByOrgCode(policyLogSearch.getBrcd());
			policyLogSearch.setBrcd(null);
		}
		if (!StringUtils.isEmpty(StringUtil.createWhereStr(policyLogSearch)))
		{
			if (!StringUtils.isEmpty(whereStr.trim()))
				whereStr = (new StringBuilder(String.valueOf(whereStr))).append(" and ").toString();
			whereStr = (new StringBuilder(String.valueOf(whereStr))).append(StringUtil.createWhereStr(policyLogSearch)).toString();
		} else
		{
			whereStr = (new StringBuilder(String.valueOf(whereStr))).append(StringUtil.createWhereStr(policyLogSearch)).toString();
		}
		if (StringUtils.isNotEmpty(whereStr.trim()))
			whereStr = (new StringBuilder(String.valueOf(whereStr))).append(" and ").toString();
		if (StringUtils.isNotEmpty(startDate))
			whereStr = (new StringBuilder(String.valueOf(whereStr))).append(" logDate>='").append(startDate).append("' and ").toString();
		if (StringUtils.isNotEmpty(endDate))
			whereStr = (new StringBuilder(String.valueOf(whereStr))).append(" logDate<='").append(endDate).append("' and ").toString();
		hql = (new StringBuilder(String.valueOf(hql))).append(whereStr).append(rolesHQL).toString();
		return vpolicyLogDao.getEntityList(hql, null);
	}

	public List getPolicyInfoForOutXLS(VpolicyInfo vpolicyinfo, String startDate, String endDate, String rolesHQL)
	{
		String hql = "from VpolicyInfo where ";
		VpolicyInfo policySearch = (VpolicyInfo)StringUtil.cleanQueryField(vpolicyinfo);
		String whereStr = "";
		if (!StringUtils.isEmpty(policySearch.getBrcd()))
		{
			whereStr = loginLogic.getBaseSqlByOrgCode(policySearch.getBrcd());
			policySearch.setBrcd(null);
		}
		if (!StringUtils.isEmpty(StringUtil.createWhereStr(policySearch)))
		{
			if (!StringUtils.isEmpty(whereStr.trim()))
				whereStr = (new StringBuilder(String.valueOf(whereStr))).append(" and ").toString();
			whereStr = (new StringBuilder(String.valueOf(whereStr))).append(StringUtil.createWhereStr(policySearch)).toString();
		} else
		{
			whereStr = (new StringBuilder(String.valueOf(whereStr))).append(StringUtil.createWhereStr(policySearch)).toString();
		}
		if (StringUtils.isNotEmpty(whereStr.trim()))
			whereStr = (new StringBuilder(String.valueOf(whereStr))).append(" and ").toString();
		if (StringUtils.isNotEmpty(startDate))
			whereStr = (new StringBuilder(String.valueOf(whereStr))).append(" checkDate>='").append(startDate).append("' and ").toString();
		if (StringUtils.isNotEmpty(endDate))
			whereStr = (new StringBuilder(String.valueOf(whereStr))).append(" checkDate<='").append(endDate).append("' and ").toString();
		hql = (new StringBuilder(String.valueOf(hql))).append(whereStr).append(rolesHQL).toString();
		return vpolicyInfoDao.getEntityList(hql, null);
	}

	public String writePolicyLogToXLS(List policyLogList)
		throws Exception
	{
		if (policyLogList == null || policyLogList.size() < 1)
			return null;
		String excelOutPath = (new StringBuilder(String.valueOf(ZonyConfig.getExcelTempPath()))).append("/LA_").append(DateUtil.getNow("yyyy-MM-dd-HH-mm-ss")).append(".xls").toString();
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("保单日志查询结果报表");
		CellRangeAddress region = new CellRangeAddress(0, 0, 1, 12);
		sheet.addMergedRegion(region);
		HSSFRow rowF = sheet.createRow(0);
		rowF.setHeight((short)550);
		HSSFCell cell = rowF.createCell(1);
		cell.setCellValue("《邮件管理系统 》保单日志查询结果报表");
		cell.setCellStyle(JexcelFiles.getHeader(wb));
		region = new CellRangeAddress(1, 1, 1, 12);
		sheet.addMergedRegion(region);
		rowF = sheet.createRow(1);
		rowF.setHeight((short)350);
		cell = rowF.createCell(1);
		cell.setCellValue((new StringBuilder("查询结果导出时间：")).append(DateUtil.getNow()).toString());
		cell.setCellStyle(JexcelFiles.getDateHeader(wb));
		HSSFRow row2 = sheet.createRow(2);
		row2.setHeight((short)350);
		cell = row2.createCell(1);
		cell.setCellValue("保单号");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		cell = row2.createCell(2);
		cell.setCellValue("投保单号");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		cell = row2.createCell(3);
		cell.setCellValue("分公司");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		cell = row2.createCell(4);
		cell.setCellValue("渠道");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		cell = row2.createCell(5);
		cell.setCellValue("保单类型");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		cell = row2.createCell(6);
		cell.setCellValue("项目名称");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		cell = row2.createCell(7);
		cell.setCellValue("产品代码");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		cell = row2.createCell(8);
		cell.setCellValue("险种组装名称");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		cell = row2.createCell(9);
		cell.setCellValue("记录类型");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		cell = row2.createCell(10);
		cell.setCellValue("记录事件");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		cell = row2.createCell(11);
		cell.setCellValue("记录时间");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		cell = row2.createCell(12);
		cell.setCellValue("操作人");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		int rowNum = 3;
		HSSFCellStyle hssfcellstyle = JexcelFiles.getNormolCell(wb);
		for (Iterator iterator = policyLogList.iterator(); iterator.hasNext();)
		{
			VpolicyLog vpolicyLog = (VpolicyLog)iterator.next();
			HSSFRow row = sheet.createRow(rowNum);
			row.setHeight((short)350);
			cell = row.createCell(1);
			cell.setCellValue(vpolicyLog.getCntrno());
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(2);
			cell.setCellValue(vpolicyLog.getPrpsno());
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(3);
			if (vpolicyLog.getOrgName() != null && StringUtils.isNotEmpty(vpolicyLog.getOrgName()))
				cell.setCellValue(vpolicyLog.getOrgName());
			else
				cell.setCellValue("");
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(4);
			if (vpolicyLog.getGroupName() != null && StringUtils.isNotEmpty(vpolicyLog.getGroupName()))
				cell.setCellValue(vpolicyLog.getGroupName());
			else
				cell.setCellValue("");
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(5);
			if (vpolicyLog.getPolicyTypeName() != null && StringUtils.isNotEmpty(vpolicyLog.getPolicyTypeName()))
				cell.setCellValue(vpolicyLog.getPolicyTypeName());
			else
				cell.setCellValue("");
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(6);
			if (vpolicyLog.getProjectName() != null && StringUtils.isNotEmpty(vpolicyLog.getProjectName()))
				cell.setCellValue(vpolicyLog.getProjectName());
			else
				cell.setCellValue("");
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(7);
			if (vpolicyLog.getProductCode() != null && StringUtils.isNotEmpty(vpolicyLog.getProductCode()))
				cell.setCellValue(vpolicyLog.getProductCode());
			else
				cell.setCellValue("");
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(8);
			if (vpolicyLog.getPackageName() != null && StringUtils.isNotEmpty(vpolicyLog.getPackageName()))
				cell.setCellValue(vpolicyLog.getPackageName());
			else
				cell.setCellValue("");
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(9);
			if (vpolicyLog.getOwnerName() != null && StringUtils.isNotEmpty(vpolicyLog.getOwnerName()))
				cell.setCellValue(vpolicyLog.getLogTypeName());
			else
				cell.setCellValue("");
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(10);
			if (vpolicyLog.getOwnerMail() != null && StringUtils.isNotEmpty(vpolicyLog.getOwnerMail()))
				cell.setCellValue(vpolicyLog.getLogEventName());
			else
				cell.setCellValue("");
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(11);
			if (vpolicyLog.getLogDate() != null && StringUtils.isNotEmpty(vpolicyLog.getLogDate()))
				cell.setCellValue(vpolicyLog.getLogDate());
			else
				cell.setCellValue("");
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(12);
			if (vpolicyLog.getOperator() != null && StringUtils.isNotEmpty(vpolicyLog.getOperator()))
				cell.setCellValue(vpolicyLog.getOperator());
			else
				cell.setCellValue("");
			cell.setCellStyle(hssfcellstyle);
			rowNum++;
		}

		sheet.setColumnWidth(1, 3500);
		sheet.setColumnWidth(2, 4000);
		sheet.setColumnWidth(3, 3000);
		sheet.setColumnWidth(4, 4000);
		sheet.setColumnWidth(5, 4000);
		sheet.setColumnWidth(6, 4000);
		sheet.setColumnWidth(7, 4000);
		sheet.setColumnWidth(8, 7000);
		sheet.setColumnWidth(9, 4000);
		sheet.setColumnWidth(10, 4000);
		sheet.setColumnWidth(11, 6000);
		sheet.setColumnWidth(12, 4000);
		FileOutputStream fileOut = new FileOutputStream(excelOutPath);
		wb.write(fileOut);
		fileOut.close();
		return excelOutPath;
	}

	public String writePolicyinfoToXLS(List vpolicyList)
		throws Exception
	{
		if (vpolicyList == null || vpolicyList.size() < 1)
			return null;
		String excelOutPath = (new StringBuilder(String.valueOf(ZonyConfig.getExcelTempPath()))).append("/LA_").append(DateUtil.getNow("yyyy-MM-dd-HH-mm-ss")).append(".xls").toString();
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("保单抽检记录查询结果报表");
		CellRangeAddress region = new CellRangeAddress(0, 0, 1, 12);
		sheet.addMergedRegion(region);
		HSSFRow rowF = sheet.createRow(0);
		rowF.setHeight((short)550);
		HSSFCell cell = rowF.createCell(1);
		cell.setCellValue("《邮件管理系统 》保单抽检记录查询结果报表");
		cell.setCellStyle(JexcelFiles.getHeader(wb));
		region = new CellRangeAddress(1, 1, 1, 11);
		sheet.addMergedRegion(region);
		rowF = sheet.createRow(1);
		rowF.setHeight((short)350);
		cell = rowF.createCell(1);
		cell.setCellValue((new StringBuilder("查询结果导出时间：")).append(DateUtil.getNow()).toString());
		cell.setCellStyle(JexcelFiles.getDateHeader(wb));
		HSSFRow row2 = sheet.createRow(2);
		row2.setHeight((short)350);
		cell = row2.createCell(1);
		cell.setCellValue("保单号");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		cell = row2.createCell(2);
		cell.setCellValue("投保单号");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		cell = row2.createCell(3);
		cell.setCellValue("分公司");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		cell = row2.createCell(4);
		cell.setCellValue("渠道");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		cell = row2.createCell(5);
		cell.setCellValue("保单类型");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		cell = row2.createCell(6);
		cell.setCellValue("项目名称");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		cell = row2.createCell(7);
		cell.setCellValue("产品代码");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		cell = row2.createCell(8);
		cell.setCellValue("抽检状态");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		cell = row2.createCell(9);
		cell.setCellValue("险种组装名称");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		cell = row2.createCell(10);
		cell.setCellValue("抽检人");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		cell = row2.createCell(11);
		cell.setCellValue("抽检时间");
		cell.setCellStyle(JexcelFiles.getTitle(wb));
		int rowNum = 3;
		HSSFCellStyle hssfcellstyle = JexcelFiles.getNormolCell(wb);
		for (Iterator iterator = vpolicyList.iterator(); iterator.hasNext();)
		{
			VpolicyInfo vpolicyInfo = (VpolicyInfo)iterator.next();
			HSSFRow row = sheet.createRow(rowNum);
			row.setHeight((short)350);
			cell = row.createCell(1);
			cell.setCellValue(vpolicyInfo.getCntrno());
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(2);
			cell.setCellValue(vpolicyInfo.getPrpsno());
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(3);
			if (vpolicyInfo != null && StringUtils.isNotEmpty(vpolicyInfo.getOrgName()))
				cell.setCellValue(vpolicyInfo.getOrgName());
			else
				cell.setCellValue("");
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(4);
			if (vpolicyInfo.getGroupName() != null && StringUtils.isNotEmpty(vpolicyInfo.getGroupName()))
				cell.setCellValue(vpolicyInfo.getGroupName());
			else
				cell.setCellValue("");
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(5);
			if (vpolicyInfo.getPolicyTypeName() != null && StringUtils.isNotEmpty(vpolicyInfo.getPolicyTypeName()))
				cell.setCellValue(vpolicyInfo.getPolicyTypeName());
			else
				cell.setCellValue("");
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(6);
			if (vpolicyInfo.getProjectName() != null && StringUtils.isNotEmpty(vpolicyInfo.getProjectName()))
				cell.setCellValue(vpolicyInfo.getProjectName());
			else
				cell.setCellValue("");
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(7);
			if (vpolicyInfo.getProductCode() != null && StringUtils.isNotEmpty(vpolicyInfo.getProductCode()))
				cell.setCellValue(vpolicyInfo.getProductCode());
			else
				cell.setCellValue("");
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(8);
			if (vpolicyInfo.getCheckStatus() != null && StringUtils.isNotEmpty(vpolicyInfo.getCheckStatus()))
				cell.setCellValue(vpolicyInfo.getCheckStatusName());
			else
				cell.setCellValue("");
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(9);
			if (vpolicyInfo.getPackageName() != null && StringUtils.isNotEmpty(vpolicyInfo.getPackageName()))
				cell.setCellValue(vpolicyInfo.getPackageName());
			else
				cell.setCellValue("");
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(10);
			if (vpolicyInfo.getChecker() != null && StringUtils.isNotEmpty(vpolicyInfo.getChecker()))
				cell.setCellValue(vpolicyInfo.getChecker());
			else
				cell.setCellValue("");
			cell.setCellStyle(hssfcellstyle);
			cell = row.createCell(11);
			if (vpolicyInfo.getCheckDate() != null && StringUtils.isNotEmpty(vpolicyInfo.getCheckDate()))
				cell.setCellValue(vpolicyInfo.getCheckDate());
			else
				cell.setCellValue("");
			cell.setCellStyle(hssfcellstyle);
			rowNum++;
		}

		sheet.setColumnWidth(1, 3500);
		sheet.setColumnWidth(2, 4000);
		sheet.setColumnWidth(3, 3000);
		sheet.setColumnWidth(4, 4000);
		sheet.setColumnWidth(5, 4000);
		sheet.setColumnWidth(6, 4000);
		sheet.setColumnWidth(7, 4000);
		sheet.setColumnWidth(8, 7000);
		sheet.setColumnWidth(9, 4000);
		sheet.setColumnWidth(10, 4000);
		sheet.setColumnWidth(11, 6000);
		FileOutputStream fileOut = new FileOutputStream(excelOutPath);
		wb.write(fileOut);
		fileOut.close();
		return excelOutPath;
	}
}
