// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ReadXLS2Model.java

package com.zony.common.util;

import com.zony.app.model.ReceiptModel;
import java.io.*;
import java.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

// Referenced classes of package com.zony.common.util:
//			DateUtil

public class ReadXLS2Model
{

	public ReadXLS2Model()
	{
	}

	public static List readXls(String filePath)
		throws IOException
	{
		InputStream is = new FileInputStream(filePath);
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		ReceiptModel receiptModel = null;
		List list = new ArrayList();
		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++)
		{
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			if (hssfSheet != null)
			{
				for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++)
				{
					HSSFRow hssfRow = hssfSheet.getRow(rowNum);
					if (hssfRow != null)
					{
						receiptModel = new ReceiptModel();
						HSSFCell prpsnoHC = hssfRow.getCell(0);
						if (prpsnoHC != null)
						{
							receiptModel.setPrpsno(getValue(prpsnoHC));
							HSSFCell dateHC = hssfRow.getCell(1);
							if (dateHC != null)
							{
								receiptModel.setPol_AcknowledgementDate(getValue(dateHC));
								if (!StringUtils.isEmpty(receiptModel.getPrpsno()) && !StringUtils.isEmpty(receiptModel.getPol_AcknowledgementDate()) && isDate(receiptModel.getPol_AcknowledgementDate()))
									list.add(receiptModel);
							}
						}
					}
				}

			}
		}

		return list;
	}

	public static List readXlsx(String filePath)
		throws IOException
	{
		InputStream is = new FileInputStream(filePath);
		XSSFWorkbook hssfWorkbook = new XSSFWorkbook(is);
		ReceiptModel receiptModel = null;
		List list = new ArrayList();
		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++)
		{
			XSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			if (hssfSheet != null)
			{
				for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++)
				{
					XSSFRow hssfRow = hssfSheet.getRow(rowNum);
					if (hssfRow != null)
					{
						receiptModel = new ReceiptModel();
						XSSFCell prpsnoHC = hssfRow.getCell(0);
						if (prpsnoHC != null)
						{
							receiptModel.setPrpsno(getValue(prpsnoHC));
							XSSFCell dateHC = hssfRow.getCell(1);
							if (dateHC != null)
							{
								receiptModel.setPol_AcknowledgementDate(getValue(dateHC));
								if (!StringUtils.isEmpty(receiptModel.getPrpsno()) && !StringUtils.isEmpty(receiptModel.getPol_AcknowledgementDate()) && isDate(receiptModel.getPol_AcknowledgementDate()))
									list.add(receiptModel);
							}
						}
					}
				}

			}
		}

		return list;
	}

	private static String getValue(XSSFCell xssfCell)
	{
		if (xssfCell.getCellType() == 1)
			return String.valueOf(xssfCell.getStringCellValue());
		if (xssfCell.getCellType() == 0)
		{
			double num = (new Double(xssfCell.getNumericCellValue())).doubleValue();
			String dateStr = String.valueOf(num);
			dateStr = dateStr.replace(".", "").replace("E7", "");
			return dateStr;
		}
		if (xssfCell.getCellType() == 2)
			return xssfCell.getRichStringCellValue().getString();
		if (xssfCell.getCellType() == 4)
			return String.valueOf(xssfCell.getBooleanCellValue());
		else
			return "";
	}

	private static String getValue(HSSFCell hssfCell)
	{
		if (hssfCell.getCellType() == 1)
			return String.valueOf(hssfCell.getStringCellValue());
		if (hssfCell.getCellType() == 0)
		{
			double num = (new Double(hssfCell.getNumericCellValue())).doubleValue();
			String dateStr = String.valueOf(num);
			dateStr = dateStr.replace(".", "").replace("E7", "");
			if (dateStr.length() == 7)
				dateStr = (new StringBuilder(String.valueOf(dateStr))).append("0").toString();
			return dateStr;
		}
		if (hssfCell.getCellType() == 2)
			return hssfCell.getRichStringCellValue().getString();
		if (hssfCell.getCellType() == 4)
			return String.valueOf(hssfCell.getBooleanCellValue());
		else
			return "";
	}

	public static boolean isDate(String date)
	{
label0:
		{
label1:
			{
				try
				{
					Date parseDate = DateUtil.parseDate(date, "yyyyMMdd");
					if (parseDate == null)
						break label0;
					String tempDate = DateUtil.formatDate(parseDate, "yyyyMMdd");
					if (!date.equals(tempDate))
						break label1;
					System.out.println(parseDate.toString());
				}
				catch (Exception e)
				{
					return false;
				}
				return true;
			}
			return false;
		}
		return false;
	}

	public static void main(String args1[])
	{
	}
}
