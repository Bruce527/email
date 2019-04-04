// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   JexcelXLSXFiles.java

package com.zony.common.util;

import java.io.*;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

// Referenced classes of package com.zony.common.util:
//			CEVUtil, DateUtil

public class JexcelXLSXFiles
{

	public JexcelXLSXFiles()
	{
	}

	public boolean validateExcel(String filePath)
	{
		if (!CEVUtil.fileExist(filePath))
			return false;
		return CEVUtil.isExcel(filePath);
	}

	public static boolean isExcel2003(String filePath)
	{
		boolean isExcel2003 = true;
		if (CEVUtil.isExcel2007(filePath))
			isExcel2003 = false;
		return isExcel2003;
	}

	public static void mergeCells(String filename)
		throws FileNotFoundException, IOException
	{
		InputStream fs = new FileInputStream(filename);
		XSSFWorkbook wb = new XSSFWorkbook(fs);
		XSSFSheet sheet = wb.getSheetAt(0);
		String c = "";
		String bottom = "";
		String right = "";
		int row = sheet.getLastRowNum();
		int startColumnIndex = 0;
		int startRowIndex = 0;
		int columnIndextmp = 0;
		int rowIndextmp = 0;
		int endColumnIndex = 0;
		int endRowIndex = 0;
		boolean isColumnFirst = false;
		boolean isRowFirst = false;
		for (int iRow = 0; iRow < row + 1; iRow++)
		{
			for (int j = 0; j < sheet.getRow(iRow).getLastCellNum(); j++)
			{
				for (int i = 0; i < row + 1; i++)
				{
					XSSFRow hssfRow = sheet.getRow(i);
					if (hssfRow.getCell(j) != null && hssfRow.getCell(j).getCellType() == 1)
						c = hssfRow.getCell(j).getStringCellValue();
					else
						c = "";
					if (i != row && sheet.getRow(i + 1).getCell(j) != null && sheet.getRow(i + 1).getCell(j).getCellType() == 1)
						bottom = sheet.getRow(i + 1).getCell(j).getStringCellValue();
					else
						bottom = "";
					if (hssfRow.getCell(j + 1) != null && hssfRow.getCell(j + 1).getCellType() == 1)
						right = hssfRow.getCell(j + 1).getStringCellValue();
					else
						right = "";
					if (c != null && !c.equals("") && c.equals(bottom) && !isNumeric(c))
					{
						if (!isRowFirst)
						{
							isRowFirst = true;
							startRowIndex = i;
							columnIndextmp = j;
						}
					} else
					if (c != null && !c.equals("") && !c.equals(bottom) && !isNumeric(c) && isRowFirst && j == columnIndextmp)
						endRowIndex = i;
					if (c != null && !c.equals("") && c.equals(right) && !isNumeric(c))
					{
						if (!isColumnFirst)
						{
							isColumnFirst = true;
							startColumnIndex = j;
							rowIndextmp = i;
						}
					} else
					if (c != null && !c.equals("") && !c.equals(right) && !isNumeric(c) && isColumnFirst && i == rowIndextmp)
						endColumnIndex = j;
					if (startRowIndex > 0 && endRowIndex > 0)
					{
						CellRangeAddress region = new CellRangeAddress(startRowIndex, endRowIndex, columnIndextmp, columnIndextmp);
						sheet.addMergedRegion(region);
						isRowFirst = false;
						startRowIndex = 0;
						endRowIndex = 0;
						columnIndextmp = 0;
					}
					if (startColumnIndex > 0 && endColumnIndex > 0)
					{
						CellRangeAddress region = new CellRangeAddress(rowIndextmp, rowIndextmp, startColumnIndex, endColumnIndex);
						sheet.addMergedRegion(region);
						isColumnFirst = false;
						startColumnIndex = 0;
						endColumnIndex = 0;
						rowIndextmp = 0;
					}
				}

			}

		}

		FileOutputStream fileOut = new FileOutputStream(filename);
		wb.write(fileOut);
		fileOut.close();
	}

	public static boolean isNumeric(String s)
	{
		if (s != null && s != "")
			return s.matches("^[0-9]*$");
		else
			return false;
	}

	public static XSSFCellStyle getHeader(XSSFWorkbook workbook)
	{
		XSSFCellStyle hssfcellstyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setColor((short)8);
		font.setBoldweight((short)700);
		font.setUnderline((byte)1);
		font.setFontName("微软雅黑");
		font.setFontHeightInPoints((short)12);
		hssfcellstyle.setFont(font);
		hssfcellstyle.setFillForegroundColor((short)8);
		hssfcellstyle.setAlignment((short)2);
		hssfcellstyle.setVerticalAlignment((short)1);
		return hssfcellstyle;
	}

	public static XSSFCellStyle getDateHeader(XSSFWorkbook workbook)
	{
		XSSFCellStyle hssfcellstyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setColor((short)8);
		font.setFontName("微软雅黑");
		font.setFontHeightInPoints((short)10);
		hssfcellstyle.setFont(font);
		hssfcellstyle.setFillForegroundColor((short)8);
		hssfcellstyle.setAlignment((short)3);
		hssfcellstyle.setVerticalAlignment((short)1);
		return hssfcellstyle;
	}

	public static XSSFCellStyle getDateCell(String alignment, String patterm, XSSFWorkbook workbook)
	{
		XSSFCellStyle hssfcellstyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontName("微软雅黑");
		font.setColor((short)8);
		font.setFontHeightInPoints((short)10);
		hssfcellstyle.setFont(font);
		if (alignment.toLowerCase().equals("left"))
			hssfcellstyle.setAlignment((short)1);
		else
		if (alignment.toLowerCase().equals("right"))
			hssfcellstyle.setAlignment((short)3);
		else
		if (alignment.toLowerCase().equals("center"))
			hssfcellstyle.setAlignment((short)2);
		else
			hssfcellstyle.setAlignment((short)1);
		XSSFDataFormat format = workbook.createDataFormat();
		hssfcellstyle.setDataFormat(format.getFormat(patterm));
		hssfcellstyle.setVerticalAlignment((short)1);
		hssfcellstyle = setBorder(hssfcellstyle);
		return hssfcellstyle;
	}

	public static XSSFCellStyle getTitle(XSSFWorkbook workbook)
	{
		XSSFCellStyle hssfcellstyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setColor((short)8);
		font.setBoldweight((short)700);
		font.setFontName("微软雅黑");
		font.setFontHeightInPoints((short)10);
		hssfcellstyle.setFont(font);
		hssfcellstyle = setBorder(hssfcellstyle);
		hssfcellstyle.setAlignment((short)2);
		hssfcellstyle.setVerticalAlignment((short)1);
		hssfcellstyle.setFillForegroundColor((short)22);
		hssfcellstyle.setFillPattern((short)1);
		return hssfcellstyle;
	}

	private static XSSFCellStyle setBorder(XSSFCellStyle hssfcellstyle)
	{
		hssfcellstyle.setBorderBottom((short)1);
		hssfcellstyle.setBorderLeft((short)1);
		hssfcellstyle.setBorderRight((short)1);
		hssfcellstyle.setBorderTop((short)1);
		hssfcellstyle.setLeftBorderColor((short)8);
		hssfcellstyle.setRightBorderColor((short)8);
		hssfcellstyle.setBottomBorderColor((short)8);
		hssfcellstyle.setTopBorderColor((short)8);
		return hssfcellstyle;
	}

	public static XSSFCellStyle getTitleUnBold(XSSFWorkbook workbook)
	{
		XSSFCellStyle hssfcellstyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontName("微软雅黑");
		font.setColor((short)8);
		font.setFontHeightInPoints((short)10);
		hssfcellstyle.setFont(font);
		hssfcellstyle.setAlignment((short)2);
		hssfcellstyle.setVerticalAlignment((short)1);
		hssfcellstyle = setBorder(hssfcellstyle);
		hssfcellstyle.setFillForegroundColor((short)22);
		hssfcellstyle.setFillPattern((short)1);
		return hssfcellstyle;
	}

	public static XSSFCellStyle getTitleUnBold(String alignment, XSSFWorkbook workbook)
	{
		XSSFCellStyle hssfcellstyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontName("微软雅黑");
		font.setColor((short)8);
		font.setFontHeightInPoints((short)10);
		hssfcellstyle.setFont(font);
		if (alignment.toLowerCase().equals("left"))
			hssfcellstyle.setAlignment((short)1);
		else
		if (alignment.toLowerCase().equals("right"))
			hssfcellstyle.setAlignment((short)3);
		else
		if (alignment.toLowerCase().equals("center"))
			hssfcellstyle.setAlignment((short)2);
		else
			hssfcellstyle.setAlignment((short)1);
		hssfcellstyle.setVerticalAlignment((short)1);
		hssfcellstyle = setBorder(hssfcellstyle);
		hssfcellstyle.setFillForegroundColor((short)22);
		hssfcellstyle.setFillPattern((short)1);
		return hssfcellstyle;
	}

	public static XSSFCellStyle getNormolCell(XSSFWorkbook workbook)
	{
		XSSFCellStyle hssfcellstyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontName("微软雅黑");
		font.setColor((short)8);
		font.setFontHeightInPoints((short)10);
		hssfcellstyle.setFont(font);
		hssfcellstyle.setAlignment((short)2);
		hssfcellstyle.setVerticalAlignment((short)1);
		hssfcellstyle = setBorder(hssfcellstyle);
		return hssfcellstyle;
	}

	public static XSSFCellStyle getNormolCell(String alignment, XSSFWorkbook workbook)
	{
		XSSFCellStyle hssfcellstyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontName("微软雅黑");
		font.setColor((short)8);
		font.setFontHeightInPoints((short)10);
		hssfcellstyle.setFont(font);
		if (alignment.toLowerCase().equals("left"))
			hssfcellstyle.setAlignment((short)1);
		else
		if (alignment.toLowerCase().equals("right"))
			hssfcellstyle.setAlignment((short)3);
		else
		if (alignment.toLowerCase().equals("center"))
			hssfcellstyle.setAlignment((short)2);
		else
			hssfcellstyle.setAlignment((short)1);
		hssfcellstyle.setVerticalAlignment((short)1);
		hssfcellstyle = setBorder(hssfcellstyle);
		return hssfcellstyle;
	}

	public static XSSFCellStyle getNotesCell(String alignment, XSSFWorkbook workbook)
	{
		XSSFCellStyle hssfcellstyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontName("微软雅黑");
		font.setColor((short)8);
		font.setFontHeightInPoints((short)10);
		hssfcellstyle.setFont(font);
		if (alignment.toLowerCase().equals("left"))
			hssfcellstyle.setAlignment((short)1);
		else
		if (alignment.toLowerCase().equals("right"))
			hssfcellstyle.setAlignment((short)3);
		else
		if (alignment.toLowerCase().equals("center"))
			hssfcellstyle.setAlignment((short)2);
		else
			hssfcellstyle.setAlignment((short)1);
		hssfcellstyle.setVerticalAlignment((short)1);
		hssfcellstyle = setBorder(hssfcellstyle);
		hssfcellstyle.setWrapText(true);
		return hssfcellstyle;
	}

	public static XSSFCellStyle getNumberCell(XSSFWorkbook workbook)
	{
		XSSFCellStyle hssfcellstyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		XSSFDataFormat format = workbook.createDataFormat();
		font.setFontName("微软雅黑");
		font.setColor((short)8);
		font.setFontHeightInPoints((short)10);
		hssfcellstyle.setFont(font);
		hssfcellstyle.setDataFormat(format.getFormat("#"));
		hssfcellstyle.setAlignment((short)2);
		hssfcellstyle.setVerticalAlignment((short)1);
		hssfcellstyle = setBorder(hssfcellstyle);
		hssfcellstyle.setWrapText(true);
		return hssfcellstyle;
	}

	public static void main(String args[])
		throws IOException
	{
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet("保单日志查询结果报表");
		CellRangeAddress region = new CellRangeAddress(0, 0, 1, 2);
		sheet.addMergedRegion(region);
		XSSFRow rowF = sheet.createRow(0);
		rowF.setHeight((short)550);
		XSSFCell cell = rowF.createCell(1);
		cell.setCellValue("《邮件管理系统 》保单日志查询结果报表");
		cell.setCellStyle(getHeader(wb));
		region = new CellRangeAddress(1, 1, 1, 2);
		sheet.addMergedRegion(region);
		rowF = sheet.createRow(1);
		rowF.setHeight((short)350);
		cell = rowF.createCell(1);
		cell.setCellValue((new StringBuilder("查询结果导出时间：")).append(DateUtil.getNow()).toString());
		cell.setCellStyle(getDateHeader(wb));
		XSSFRow row2 = sheet.createRow(2);
		row2.setHeight((short)350);
		cell = row2.createCell(1);
		cell.setCellValue("保单号");
		cell.setCellStyle(getTitle(wb));
		int rowNum = 3;
		XSSFCellStyle hssfcellstyle = getNormolCell(wb);
		XSSFRow row = sheet.createRow(rowNum);
		row.setHeight((short)350);
		cell = row.createCell(1);
		cell.setCellValue("TEST0000000000001");
		cell.setCellStyle(hssfcellstyle);
		rowNum++;
		sheet.setColumnWidth(1, 5500);
		FileOutputStream fileOut = new FileOutputStream("D:/000000000000000.xlsx");
		wb.write(fileOut);
		fileOut.close();
	}
}
