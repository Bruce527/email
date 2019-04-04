// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   JexcelFiles.java

package com.zony.common.util;

import java.io.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

// Referenced classes of package com.zony.common.util:
//			CEVUtil

public class JexcelFiles
{

	public JexcelFiles()
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
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filename));
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		HSSFSheet sheet = wb.getSheetAt(0);
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
					HSSFRow hssfRow = sheet.getRow(i);
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

	public static HSSFCellStyle getHeader(HSSFWorkbook workbook)
	{
		HSSFCellStyle hssfcellstyle = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setColor((short)8);
		font.setBoldweight((short)700);
		font.setUnderline((byte)1);
		font.setFontName("Î¢ÈíÑÅºÚ");
		font.setFontHeightInPoints((short)12);
		hssfcellstyle.setFont(font);
		hssfcellstyle.setFillForegroundColor((short)8);
		hssfcellstyle.setAlignment((short)2);
		hssfcellstyle.setVerticalAlignment((short)1);
		return hssfcellstyle;
	}

	public static HSSFCellStyle getDateHeader(HSSFWorkbook workbook)
	{
		HSSFCellStyle hssfcellstyle = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setColor((short)8);
		font.setFontName("Î¢ÈíÑÅºÚ");
		font.setFontHeightInPoints((short)10);
		hssfcellstyle.setFont(font);
		hssfcellstyle.setFillForegroundColor((short)8);
		hssfcellstyle.setAlignment((short)3);
		hssfcellstyle.setVerticalAlignment((short)1);
		return hssfcellstyle;
	}

	public static HSSFCellStyle getDateCell(String alignment, String patterm, HSSFWorkbook workbook)
	{
		HSSFCellStyle hssfcellstyle = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setFontName("Î¢ÈíÑÅºÚ");
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
		HSSFDataFormat format = workbook.createDataFormat();
		hssfcellstyle.setDataFormat(format.getFormat(patterm));
		hssfcellstyle.setVerticalAlignment((short)1);
		hssfcellstyle = setBorder(hssfcellstyle);
		return hssfcellstyle;
	}

	public static HSSFCellStyle getTitle(HSSFWorkbook workbook)
	{
		HSSFCellStyle hssfcellstyle = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setColor((short)8);
		font.setBoldweight((short)700);
		font.setFontName("Î¢ÈíÑÅºÚ");
		font.setFontHeightInPoints((short)10);
		hssfcellstyle.setFont(font);
		hssfcellstyle = setBorder(hssfcellstyle);
		hssfcellstyle.setAlignment((short)2);
		hssfcellstyle.setVerticalAlignment((short)1);
		hssfcellstyle.setFillForegroundColor((short)22);
		hssfcellstyle.setFillPattern((short)1);
		return hssfcellstyle;
	}

	private static HSSFCellStyle setBorder(HSSFCellStyle hssfcellstyle)
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

	public static HSSFCellStyle getTitleUnBold(HSSFWorkbook workbook)
	{
		HSSFCellStyle hssfcellstyle = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setFontName("Î¢ÈíÑÅºÚ");
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

	public static HSSFCellStyle getTitleUnBold(String alignment, HSSFWorkbook workbook)
	{
		HSSFCellStyle hssfcellstyle = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setFontName("Î¢ÈíÑÅºÚ");
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

	public static HSSFCellStyle getNormolCell(HSSFWorkbook workbook)
	{
		HSSFCellStyle hssfcellstyle = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setFontName("Î¢ÈíÑÅºÚ");
		font.setColor((short)8);
		font.setFontHeightInPoints((short)10);
		hssfcellstyle.setFont(font);
		hssfcellstyle.setAlignment((short)2);
		hssfcellstyle.setVerticalAlignment((short)1);
		hssfcellstyle = setBorder(hssfcellstyle);
		return hssfcellstyle;
	}

	public static HSSFCellStyle getNormolCell(String alignment, HSSFWorkbook workbook)
	{
		HSSFCellStyle hssfcellstyle = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setFontName("Î¢ÈíÑÅºÚ");
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

	public static HSSFCellStyle getNotesCell(String alignment, HSSFWorkbook workbook)
	{
		HSSFCellStyle hssfcellstyle = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setFontName("Î¢ÈíÑÅºÚ");
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

	public static HSSFCellStyle getNumberCell(HSSFWorkbook workbook)
	{
		HSSFCellStyle hssfcellstyle = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		HSSFDataFormat format = workbook.createDataFormat();
		font.setFontName("Î¢ÈíÑÅºÚ");
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
}
