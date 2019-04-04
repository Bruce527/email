// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CompressUtil.java

package com.zony.common.util;

import java.io.File;
import java.util.*;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import org.apache.commons.lang.StringUtils;

public class CompressUtil
{

	public CompressUtil()
	{
	}

	public static File[] unzip(String zip, String dest, String passwd)
		throws ZipException
	{
		File zipFile = new File(zip);
		return unzip(zipFile, dest, passwd);
	}

	public static File[] unzip(String zip, String passwd)
		throws ZipException
	{
		File zipFile = new File(zip);
		File parentDir = zipFile.getParentFile();
		return unzip(zipFile, parentDir.getAbsolutePath(), passwd);
	}

	public static File[] unzip(File zipFile, String dest, String passwd)
		throws ZipException
	{
		ZipFile zFile = new ZipFile(zipFile);
		zFile.setFileNameCharset("GBK");
		if (!zFile.isValidZipFile())
			throw new ZipException("压缩文件不合法,可能被损坏.");
		File destDir = new File(dest);
		if (destDir.isDirectory() && !destDir.exists())
			destDir.mkdir();
		if (zFile.isEncrypted())
			zFile.setPassword(passwd.toCharArray());
		zFile.extractAll(dest);
		List headerList = zFile.getFileHeaders();
		List extractedFileList = new ArrayList();
		for (Iterator iterator = headerList.iterator(); iterator.hasNext();)
		{
			FileHeader fileHeader = (FileHeader)iterator.next();
			if (!fileHeader.isDirectory())
				extractedFileList.add(new File(destDir, fileHeader.getFileName()));
		}

		File extractedFiles[] = new File[extractedFileList.size()];
		extractedFileList.toArray(extractedFiles);
		return extractedFiles;
	}

	public static String zip(String src)
	{
		return zip(src, null);
	}

	public static String zip(String src, String passwd)
	{
		return zip(src, null, passwd);
	}

	public static String zip(String src, String dest, String passwd)
	{
		return zip(src, dest, true, passwd);
	}

	public static String zip(String src, String dest, boolean isCreateDir, String passwd)
	{
		File srcFile;
		ZipParameters parameters;
		srcFile = new File(src);
		dest = buildDestinationZipFilePath(srcFile, dest);
		parameters = new ZipParameters();
		parameters.setCompressionMethod(8);
		parameters.setCompressionLevel(5);
		if (!StringUtils.isEmpty(passwd))
		{
			parameters.setEncryptFiles(true);
			parameters.setEncryptionMethod(0);
			parameters.setPassword(passwd.toCharArray());
		}
		ZipFile zipFile;
		zipFile = new ZipFile(dest);
		if (!srcFile.isDirectory())
			break MISSING_BLOCK_LABEL_136;
		if (isCreateDir)
			break MISSING_BLOCK_LABEL_124;
		File subFiles[] = srcFile.listFiles();
		ArrayList temp = new ArrayList();
		Collections.addAll(temp, subFiles);
		zipFile.addFiles(temp, parameters);
		return dest;
		zipFile.addFolder(srcFile, parameters);
		break MISSING_BLOCK_LABEL_145;
		zipFile.addFile(srcFile, parameters);
		return dest;
		ZipException e;
		e;
		e.printStackTrace();
		return null;
	}

	private static String buildDestinationZipFilePath(File srcFile, String destParam)
	{
		if (StringUtils.isEmpty(destParam))
		{
			if (srcFile.isDirectory())
			{
				destParam = (new StringBuilder(String.valueOf(srcFile.getParent()))).append(File.separator).append(srcFile.getName()).append(".zip").toString();
			} else
			{
				String fileName = srcFile.getName().substring(0, srcFile.getName().lastIndexOf("."));
				destParam = (new StringBuilder(String.valueOf(srcFile.getParent()))).append(File.separator).append(fileName).append(".zip").toString();
			}
		} else
		{
			createDestDirectoryIfNecessary(destParam);
			if (destParam.endsWith(File.separator))
			{
				String fileName = "";
				if (srcFile.isDirectory())
					fileName = srcFile.getName();
				else
					fileName = srcFile.getName().substring(0, srcFile.getName().lastIndexOf("."));
				destParam = (new StringBuilder(String.valueOf(destParam))).append(fileName).append(".zip").toString();
			}
		}
		return destParam;
	}

	private static void createDestDirectoryIfNecessary(String destParam)
	{
		File destDir = null;
		if (destParam.endsWith(File.separator))
			destDir = new File(destParam);
		else
			destDir = new File(destParam.substring(0, destParam.lastIndexOf(File.separator)));
		if (!destDir.exists())
			destDir.mkdirs();
	}

	public static void main(String args[])
	{
		zip("C:\\temp\\1.pdf", "C:\\temp\\2.zip", "123");
	}
}
