// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PolicyImportLogic.java

package com.zony.app.logic;

import com.zony.app.constants.Constant;
import com.zony.app.dao.*;
import com.zony.app.domain.*;
import com.zony.app.thread.PolicyImportThread;
import com.zony.common.config.ZonyConfig;
import com.zony.common.util.*;
import java.io.*;
import java.util.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

// Referenced classes of package com.zony.app.logic:
//			PolicyLogLogic, PolicySendConfigLogic, EmailTaskLogic

public class PolicyImportLogic
{

	private PolicyInfoDao policyInfoDao;
	private ProjectNameDao projectNameDao;
	private PackageNameDao packageNameDao;
	private PolicyLogLogic policyLogLogic;
	EmailTaskLogic emailTaskLogic;
	PolicySendConfigLogic policySendConfigLogic;
	ProductCodeDao productCodeDao;
	public static Logger logger = Logger.getLogger(com/zony/app/logic/PolicyImportLogic);
	public static PolicyImportLogic policyImpoprLogic = new PolicyImportLogic();
	public static Object lockerObj = new Object();

	public PolicyImportLogic()
	{
	}

	public long savePolicyInfo(PolicyInfo policyInfo)
	{
		return policyInfoDao.savePolicyInfo(policyInfo);
	}

	public PolicyInfo getPolicyNoticeById(long id)
	{
		return policyInfoDao.getPolicyInfoById(id);
	}

	public static synchronized String[] getFTPFiles(FTPCommon ftpCommon)
		throws Exception
	{
		String rtnPath[];
		String successPath;
		String temp_prefix;
		String moveToPath;
		rtnPath = new String[5];
		successPath = ZonyConfig.getCheckPolicySUCCESSPath();
		temp_prefix = ZonyConfig.getTemp_FolderName().split(",")[0];
		moveToPath = "";
		List folderList;
		if (!ftpCommon.ftpLogin())
			break MISSING_BLOCK_LABEL_837;
		logger.debug("登录方法ftpCommon.ftpLogin()返回成功，连接上ftp服务器，开始扫描文件夹......");
		folderList = ftpCommon.getFolderList(ftpCommon.getFtpModel().getRemoteDir());
		logger.debug((new StringBuilder("结束扫描文件夹，扫描到【")).append(folderList == null ? 0 : folderList.size()).append("个文件夹！").toString());
		if (folderList == null || folderList.size() < 1)
			return null;
		Iterator iterator = folderList.iterator();
		while (iterator.hasNext()) 
		{
			String folderName = (String)iterator.next();
			String folderFullPath = folderName;
			folderName = folderName.substring(folderName.lastIndexOf("/") + 1);
			if (folderName.toLowerCase().startsWith(temp_prefix.toLowerCase()))
			{
				logger.error((new StringBuilder("放弃对文件夹 【 ")).append(folderFullPath).append("】的扫描处理，文件不完整，以").append(temp_prefix.toLowerCase()).append("开始的文件。放弃处理").toString());
				continue;
			}
			ftpCommon.getFtpClient().changeWorkingDirectory(folderFullPath);
			FTPFile files[] = ftpCommon.getFtpClient().listFiles();
			List fileList = new ArrayList();
			for (int i = 0; i < files.length; i++)
				if (files[i].isFile())
				{
					logger.error((new StringBuilder("当前扫描到第 【")).append(i).append("】 个文件：").append(files[i].getName()).toString());
					fileList.add((new StringBuilder(String.valueOf(folderFullPath))).append("/").append(files[i].getName()).toString());
				}

			if (fileList.size() != 2)
			{
				logger.error((new StringBuilder("放弃对文件夹 【 ")).append(folderFullPath).append("】的扫描处理，文件个数不符，应为 2 个，当前").append(fileList.size()).toString());
				continue;
			}
			String pdfName = "";
			String pdfPath = "";
			String xmlName = "";
			String xmlPath = "";
			for (Iterator iterator1 = fileList.iterator(); iterator1.hasNext();)
			{
				String dataFile = (String)iterator1.next();
				if (dataFile.toLowerCase().endsWith(".pdf"))
				{
					pdfPath = dataFile;
					pdfName = dataFile.substring(dataFile.lastIndexOf("/") + 1);
				}
				if (dataFile.toLowerCase().endsWith(".xml"))
				{
					xmlPath = dataFile;
					xmlName = dataFile.substring(dataFile.lastIndexOf("/") + 1);
				}
			}

			if (StringUtils.isEmpty(xmlName) || StringUtils.isEmpty(pdfName))
			{
				logger.error((new StringBuilder("当前文件夹【")).append(folderName).append("】下子文件中没有xml 或 pdf文件 ; 跳过文件，继续处理！").toString());
				continue;
			}
			String tempimportPath = DateUtil.getNow(ZonyConfig.getCreaPathformat());
			FileUtil.CreateDir((new StringBuilder(String.valueOf(successPath))).append(tempimportPath).toString());
			moveToPath = (new StringBuilder(String.valueOf(successPath))).append(tempimportPath).append(folderName).toString();
			File moveToFile = new File(moveToPath);
			FileUtils.deleteDirectory(moveToFile);
			FileUtil.CreateDir(moveToPath);
			if (!ftpCommon.downloadFile((new StringBuilder(String.valueOf(moveToPath))).append("/").append(pdfName).toString(), pdfPath))
				throw new Exception((new StringBuilder("下载文件到本地失败，文件路径：")).append(pdfPath).toString());
			if (!ftpCommon.downloadFile((new StringBuilder(String.valueOf(moveToPath))).append("/").append(xmlName).toString(), xmlPath))
				throw new Exception((new StringBuilder("下载文件到本地失败，文件路径：")).append(xmlPath).toString());
			if (!ftpCommon.deleteDirectory(folderFullPath))
				throw new Exception((new StringBuilder("移除文件夹异常，文件夹名称：")).append(folderFullPath).toString());
			rtnPath[0] = moveToPath;
			rtnPath[1] = tempimportPath;
			rtnPath[2] = pdfName;
			rtnPath[3] = xmlName;
			rtnPath[4] = folderName;
			break;
		}
		return rtnPath;
		try
		{
			throw new Exception("连接登录到FTP服务器失败，getFTPFiles()中代码行：ftpcommon.ftpLogin()。");
		}
		catch (Exception e)
		{
			try
			{
				FileUtils.deleteDirectory(new File(moveToPath));
			}
			catch (IOException e1)
			{
				throw new Exception((new StringBuilder("清理本地文件夹失败,文件夹：")).append(moveToPath).toString(), e1);
			}
			throw new Exception("扫描FTP发送异常；异常信息如下：", e);
		}
	}

	public static Object[] getPolicyImportPath()
	{
		List childList;
		int i;
		Globals.loadProperties();
		Document doc = Globals.properties.doc;
		Element rootElt = doc.getRootElement();
		Element pathElt = (Element)XPath.selectSingleNode(rootElt, "/zonysoft/policy/importPath");
		childList = pathElt.getChildren();
		i = 0;
		  goto _L1
_L6:
		int j;
		int k;
		File afile[];
		String eltPath = ((Element)childList.get(i)).getValue();
		File eltFile = new File(eltPath);
		File rootFiles[] = eltFile.listFiles();
		if (rootFiles == null || rootFiles.length <= 0)
			continue; /* Loop/switch isn't completed */
		System.out.println((new StringBuilder("当前检测到文件数量：")).append(rootFiles.length).toString());
		logger.debug((new StringBuilder("当前检测到文件数量：")).append(rootFiles.length).toString());
		k = (afile = rootFiles).length;
		j = 0;
		  goto _L2
_L4:
		Object objArray[];
		File file = afile[j];
		objArray = doImportCheck(file);
		if (!((Boolean)objArray[0]).booleanValue())
			continue; /* Loop/switch isn't completed */
		objArray[3] = Integer.valueOf(i + 1);
		return objArray;
		j++;
_L2:
		if (j < k) goto _L4; else goto _L3
_L3:
		i++;
_L1:
		if (i < childList.size()) goto _L6; else goto _L5
_L5:
		break MISSING_BLOCK_LABEL_226;
		Exception e;
		e;
		logger.error(e.toString(), e);
		return (new Object[] {
			Boolean.valueOf(false), 0, 0, Integer.valueOf(0), 0
		});
	}

	public static Object[] doImportCheck(File file)
	{
		Boolean checkFlag = Boolean.valueOf(true);
		if (!file.isDirectory())
			checkFlag = Boolean.valueOf(false);
		if (file.getName().startsWith("temp_") || file.getName().startsWith("TEMP_"))
		{
			logger.debug((new StringBuilder("当前文件【")).append(file.getName()).append("】以temp_ ; 跳过文件，继续处理！").toString());
			checkFlag = Boolean.valueOf(false);
		}
		File rootDataFiles[] = file.listFiles();
		if (rootDataFiles == null || rootDataFiles.length != 2)
		{
			System.out.println((new StringBuilder("当前文件夹【")).append(file.getName()).append("】下没有文件或是子文件个数不为2 ; 跳过文件，继续处理！").toString());
			SysLogUtil.getDebugLog().debug((new StringBuilder("当前文件夹【")).append(file.getName()).append("】下没有文件或是子文件个数不为2 ; 跳过文件，继续处理！").toString());
			checkFlag = Boolean.valueOf(false);
		}
		boolean isHaveXml = false;
		boolean isHavePdf = false;
		String pdfName = "";
		String xmlName = "";
		File afile[];
		int j = (afile = rootDataFiles).length;
		for (int i = 0; i < j; i++)
		{
			File dataFile = afile[i];
			if (dataFile.getName().toLowerCase().endsWith(".pdf") && dataFile.length() > 0L)
			{
				isHavePdf = true;
				pdfName = dataFile.getName();
			}
			if (dataFile.getName().toLowerCase().endsWith(".xml") && dataFile.length() > 0L)
			{
				isHaveXml = true;
				xmlName = dataFile.getName();
			}
		}

		if (!isHaveXml || !isHavePdf)
		{
			System.out.println((new StringBuilder("当前文件夹【")).append(file.getName()).append("】下子文件中没有xml 或 pdf文件或者对应文件字节数为0 ; 跳过文件，继续处理！").toString());
			checkFlag = Boolean.valueOf(false);
		}
		return (new Object[] {
			checkFlag, pdfName, xmlName, Integer.valueOf(0), file
		});
	}

	public static synchronized String[] getLocalFolePathName()
		throws IOException
	{
		String rtnPath[] = new String[6];
		String successPath = ZonyConfig.getCheckPolicySUCCESSPath();
		Object objArray[] = getPolicyImportPath();
		if (!((Boolean)objArray[0]).booleanValue())
		{
			return null;
		} else
		{
			File file = (File)objArray[4];
			String pdfName = (String)objArray[1];
			String xmlName = (String)objArray[2];
			String tempimportPath = DateUtil.getNow(ZonyConfig.getCreaPathformat());
			FileUtil.CreateDir((new StringBuilder(String.valueOf(successPath))).append(tempimportPath).toString());
			String moveToPath = (new StringBuilder(String.valueOf(successPath))).append(tempimportPath).append(file.getName()).toString();
			File moveToFile = new File(moveToPath);
			FileUtils.deleteDirectory(moveToFile);
			FileUtil.CreateDir(moveToPath);
			FileUtils.moveFile(new File((new StringBuilder(String.valueOf(file.getPath()))).append("/").append(xmlName).toString()), new File((new StringBuilder(String.valueOf(moveToPath))).append("/").append(xmlName).toString()));
			FileUtils.moveFile(new File((new StringBuilder(String.valueOf(file.getPath()))).append("/").append(pdfName).toString()), new File((new StringBuilder(String.valueOf(moveToPath))).append("/").append(pdfName).toString()));
			file.delete();
			rtnPath[0] = moveToPath;
			rtnPath[1] = tempimportPath;
			rtnPath[2] = pdfName;
			rtnPath[3] = xmlName;
			rtnPath[4] = file.getName();
			rtnPath[5] = objArray[3].toString();
			return rtnPath;
		}
	}

	public static void main(String args[])
		throws IOException
	{
		Globals.setSysRootDir("D:\\TFS\\EmailManagement\\Email Management\\Trunk\\Email-Prd\\MetLifeEmail\\WebContent");
		String rtnPath[] = getLocalFolePathName();
		String args1[];
		int j = (args1 = rtnPath).length;
		for (int i = 0; i < j; i++)
		{
			String string = args1[i];
			System.out.println((new StringBuilder("=====")).append(string).toString());
		}

	}

	public static synchronized String[] getMSFTPFolderPathName(FTPImportUtil ftpup)
		throws Exception
	{
		String returnPath[] = new String[5];
		String localPath = ZonyConfig.getCheckPolicySUCCESSPath();
		String tempimportPath = DateUtil.getNow(ZonyConfig.getCreaPathformat());
		String temp_prefix = "";
		if (ZonyConfig.getTemp_FolderName() != null)
			temp_prefix = ZonyConfig.getTemp_FolderName().split(",")[0];
		localPath = (new StringBuilder(String.valueOf(localPath))).append(tempimportPath).toString();
		String rootPath = ZonyConfig.getPolicyImportPath();
		try
		{
			if (ftpup.connectServer())
			{
				List folderList = ftpup.getFileNameList(rootPath);
				if (folderList == null || folderList.size() < 1)
					logger.error("当前FTP服务器无文件夹，退出本次连接扫描FTP服务器！");
				for (int i = 0; i < folderList.size(); i++)
				{
					if (!ftpup.connectServer())
					{
						logger.error("当前FTP服务器无文件夹，退出本次连接扫描FTP服务器！");
						break;
					}
					String folderName = (String)folderList.get(i);
					String folderPath = (new StringBuilder(String.valueOf(rootPath))).append(ftpup.getPathSeparator(rootPath)).append(folderName).toString();
					logger.debug((new StringBuilder("扫描到文件夹 ,第")).append(i + 1).append("个文件夹，文件夹名称为：【").append(folderName).append("】").toString());
					if (folderName.toLowerCase().startsWith(temp_prefix.toLowerCase()))
					{
						logger.error((new StringBuilder("当前文件夹【")).append(folderName).append("】为临时文件夹，忽略对此文件夹处理，继续后续文件夹处理！").toString());
						continue;
					}
					List fileNameList = ftpup.getFileNameList((new StringBuilder(String.valueOf(rootPath))).append(ftpup.getPathSeparator(rootPath)).append(folderName).toString());
					if (fileNameList == null || fileNameList.size() < 1)
					{
						logger.error((new StringBuilder("当前文件夹【")).append(folderName).append("】下无文件,忽略对此文件夹处理！").toString());
						continue;
					}
					if (fileNameList.size() != 2)
					{
						logger.error((new StringBuilder("当前文件夹【")).append(folderName).append("】下文件个数不符，应为：【2】 个，当前文件个数为 : 【").append(fileNameList.size()).append("】").toString());
						continue;
					}
					String pdfName = "";
					String xmlName = "";
					for (Iterator iterator = fileNameList.iterator(); iterator.hasNext();)
					{
						String fileName = (String)iterator.next();
						if (fileName.toLowerCase().endsWith(".xml"))
							xmlName = fileName;
						if (fileName.toLowerCase().endsWith(".pdf"))
							pdfName = fileName;
					}

					if (StringUtils.isEmpty(xmlName) || StringUtils.isEmpty(pdfName))
					{
						logger.error((new StringBuilder("当前文件夹【")).append(folderName).append("】下文件类型不符，应该存在XML数据文件和PDF保单文件，当前获取到文件 : 【 xml: ").append(xmlName).append(" ; pdf: ").append(pdfName).append("】").toString());
						continue;
					}
					String xmlPath = (new StringBuilder(String.valueOf(folderPath))).append(ftpup.getPathSeparator(folderPath)).append(xmlName).toString();
					String pdfPath = (new StringBuilder(String.valueOf(folderPath))).append(ftpup.getPathSeparator(folderPath)).append(pdfName).toString();
					String localFolderPath = (new StringBuilder(String.valueOf(localPath))).append(ftpup.getPathSeparator(localPath)).append(folderName).toString();
					FileUtil.CreateDir(localFolderPath);
					try
					{
						logger.debug((new StringBuilder("下载文件：【")).append(xmlPath).append("】,保存本地路径为：").append(localFolderPath).append(ftpup.getPathSeparator(localFolderPath)).append(xmlName).toString());
						ftpup.downloadFile(xmlPath, (new StringBuilder(String.valueOf(localFolderPath))).append(ftpup.getPathSeparator(localFolderPath)).append(xmlName).toString());
						logger.debug((new StringBuilder("下载文件：【")).append(pdfPath).append("】,保存本地路径为：").append(localFolderPath).append(ftpup.getPathSeparator(localFolderPath)).append(pdfName).toString());
						ftpup.downloadFile(pdfPath, (new StringBuilder(String.valueOf(localFolderPath))).append(ftpup.getPathSeparator(localFolderPath)).append(pdfName).toString());
						logger.debug((new StringBuilder("删除服务器XML文件：【")).append(xmlPath).append("】").toString());
						ftpup.deleteFile(xmlPath);
						logger.debug((new StringBuilder("删除服务器PDF文件：【")).append(pdfPath).append("】").toString());
						ftpup.deleteFile(pdfPath);
						logger.debug("下载、删除服务器文件结束！");
					}
					catch (Exception e)
					{
						logger.error((new StringBuilder("下载或删除文件发生异常，清理本地文件夹；文件夹：")).append(localFolderPath).toString(), e);
						FileUtils.deleteDirectory(new File(localFolderPath));
						continue;
					}
					logger.debug((new StringBuilder("删除清理服务器文件夹:【")).append(rootPath).append(ftpup.getPathSeparator(rootPath)).append(folderName).append("】").toString());
					if (!ftpup.deleteFolder((new StringBuilder(String.valueOf(rootPath))).append(ftpup.getPathSeparator(rootPath)).append(folderName).toString()))
					{
						logger.error("清理服务器文件夹发生失败，跳过当前文件夹处理，继续处理任务！");
						continue;
					}
					returnPath[0] = localFolderPath;
					returnPath[1] = tempimportPath;
					returnPath[2] = pdfName;
					returnPath[3] = xmlName;
					returnPath[4] = folderName;
					break;
				}

			} else
			{
				logger.error("连接失败！无法登录成功！");
				throw new Exception("连接失败！无法登录成功！");
			}
		}
		catch (Exception e)
		{
			throw new Exception("获取服务器路径及下载文件到本地操作，其他未知异常；", e);
		}
		return returnPath;
	}

	public static synchronized String[] getImportFolePathName(FTPImportUtil ftpUtil)
		throws Exception
	{
		String rtnPath[];
		List dirs;
		String path[];
		dirs = ftpUtil.getFileNameList(ZonyConfig.getPolicyImportPath());
		path = new String[2];
		rtnPath = new String[5];
		if (dirs == null || dirs.size() <= 0) goto _L2; else goto _L1
_L1:
		Iterator iterator = dirs.iterator();
		  goto _L3
_L5:
		String currPolicyNoticPath = (String)iterator.next();
		String tempPrefix[] = ZonyConfig.getTemp_FolderName().split(",");
		if (tempPrefix != null && tempPrefix.length > 0 && currPolicyNoticPath.toLowerCase().startsWith(tempPrefix[0].toLowerCase()))
			continue; /* Loop/switch isn't completed */
		String temStr;
		path = policyImportDataStep1(currPolicyNoticPath, ftpUtil);
		if (path == null || path[0] == null)
			continue; /* Loop/switch isn't completed */
		temStr = cutCopyPolicayData(ZonyConfig.getCheckPolicySUCCESSPath(), currPolicyNoticPath, (new StringBuilder(String.valueOf(ZonyConfig.getPolicyImportPath()))).append(currPolicyNoticPath).append("/").toString(), ftpUtil);
		if (temStr == null || temStr.equals(""))
			break; /* Loop/switch isn't completed */
		try
		{
			String temparr[] = temStr.split(",");
			rtnPath[0] = temparr[0];
			rtnPath[1] = temparr[1];
			rtnPath[2] = path[0];
			rtnPath[3] = path[1];
			rtnPath[4] = currPolicyNoticPath;
			break; /* Loop/switch isn't completed */
		}
		catch (Exception e)
		{
			logger.error((new StringBuilder("执行获取保单文件夹路径时出错,失败时间：")).append(DateUtil.getNow()).append(" 异常信息：").append(ExceptionUtils.getErrorTraceMessage(e)).toString());
		}
_L3:
		if (iterator.hasNext()) goto _L5; else goto _L4
_L2:
		return null;
		Exception e;
		e;
		throw e;
_L4:
		return rtnPath;
	}

	public int checkPolicyExist(PolicyInfo policyInfo)
	{
		long newID = policyInfo.getId().longValue();
		List tempPolicyInfoList = policyInfoDao.getPolicyInfoByCntrNO(policyInfo.getCntrno(), newID, policyInfo.getLetterctl(), policyInfo.getTrancode());
		if (tempPolicyInfoList != null && tempPolicyInfoList.size() > 0)
		{
			PolicyInfo tempPolicyInfo;
			for (Iterator iterator = tempPolicyInfoList.iterator(); iterator.hasNext(); policyLogLogic.saveLog(tempPolicyInfo.getId(), "24", null))
			{
				tempPolicyInfo = (PolicyInfo)iterator.next();
				policyInfoDao.updatePolicyInfoByNewID(tempPolicyInfo.getId().longValue(), newID, "1");
			}

			policyLogLogic.saveLog(Long.valueOf(newID), "25", null);
			return 1;
		} else
		{
			policyLogLogic.saveLog(Long.valueOf(newID), "26", null);
			return 0;
		}
	}

	public static String[] policyImportDataStep1(String dirname, FTPImportUtil ftpUtils)
		throws Exception
	{
		String path = (new StringBuilder(String.valueOf(ZonyConfig.getPolicyImportPath()))).append(dirname).append("/").toString();
		String errorinfo = "";
		List policyArr = ftpUtils.getFileNameList(path);
		boolean isHavePdffile = false;
		boolean isHaveXMLFile = false;
		String pdfArr[] = new String[2];
		if (policyArr != null && policyArr.size() > 0)
		{
			for (Iterator iterator = policyArr.iterator(); iterator.hasNext();)
			{
				String fileName = (String)iterator.next();
				String tempFileName = fileName.substring(fileName.lastIndexOf("."));
				if (tempFileName.equalsIgnoreCase(".pdf"))
				{
					isHavePdffile = true;
					pdfArr[0] = fileName;
				}
				if (tempFileName.equalsIgnoreCase(".xml"))
				{
					isHaveXMLFile = true;
					pdfArr[1] = fileName;
				}
			}

		} else
		{
			return null;
		}
		if (!isHavePdffile || !isHaveXMLFile)
		{
			if (!isHavePdffile)
			{
				errorinfo = (new StringBuilder("您选择的保单中缺少policy.pdf文件，不能执行导入，错误路径:【")).append(pdfArr[0]).append("】").toString();
				logger.error(errorinfo);
			}
			if (!isHaveXMLFile)
			{
				errorinfo = (new StringBuilder("您选择的保单中缺少XML文件，不能执行导入，错误路径:【")).append(pdfArr[1]).append("】").toString();
				logger.error(errorinfo);
			}
			cutCopyPolicayData(ZonyConfig.getCheckPolicyFAILPath(), dirname, path, ftpUtils);
			return null;
		} else
		{
			return pdfArr;
		}
	}

	public static String readXMLDataforPolicy(Document doc, String xmlTag)
	{
		String xmlInfo = null;
		try
		{
			xmlInfo = XmlUtils.getSingleXMLValue(doc, xmlTag);
			if (!StringUtils.isEmpty(xmlInfo))
				xmlInfo = xmlInfo.trim();
		}
		catch (Exception e)
		{
			logger.error((new StringBuilder("执行保单XML数据预处理,防止空异常和去空格操作时出错,失败时间：")).append(DateUtil.getNow()).append(" 异常信息：").append(ExceptionUtils.getErrorTraceMessage(e)).toString());
		}
		return xmlInfo;
	}

	public static boolean checkPolicyImportData(PolicyInfo policy, String policyRootPath[], FtpUtils ftpUtils)
	{
		boolean isneedCut = true;
		if (StringUtils.isEmpty(policy.getBrcd()))
			isneedCut = false;
		if (StringUtils.isEmpty(policy.getChn()))
			isneedCut = false;
		if (StringUtils.isEmpty(policy.getCntrno()))
			isneedCut = false;
		if (StringUtils.isEmpty(policy.getMtsNoAcknowledgement()))
			isneedCut = false;
		if (StringUtils.isEmpty(policy.getOwnerMail()))
			isneedCut = false;
		if (!isneedCut)
		{
			String dirName = policyRootPath[4];
			cutCopyPolicayDataByLoaclFile(ZonyConfig.getCheckPolicyFAILPath(), dirName, (new StringBuilder(String.valueOf(ZonyConfig.getCheckPolicySUCCESSPath()))).append(policyRootPath[1]).append(dirName).append(File.separator).toString());
		}
		return isneedCut;
	}

	public long importPolicyDataRead(String policyRootPath[])
		throws Exception
	{
		Object obj = lockerObj;
		JVM INSTR monitorenter ;
		String projectName;
		String CHN;
		String orgCode;
		int retFlag;
		boolean isTMFlag;
		Document doc;
		PolicyInfo policy;
		String filePath;
		Map productCodeMap;
		projectName = "";
		String productCode = null;
		CHN = null;
		orgCode = null;
		retFlag = -1;
		isTMFlag = false;
		doc = getDocument((new StringBuilder(String.valueOf(policyRootPath[0]))).append(File.separator).append(policyRootPath[3]).toString());
		policy = new PolicyInfo();
		filePath = (new StringBuilder(String.valueOf(policyRootPath[1]))).append(policyRootPath[4]).append(File.separator).append(policyRootPath[2]).toString();
		productCodeMap = null;
		policy.setFilePath(filePath);
		policy.setCoycd(readXMLDataforPolicy(doc, "/Dataset/COYCD"));
		orgCode = readXMLDataforPolicy(doc, "/Dataset/BRCD");
		policy.setBrcd(orgCode);
		CHN = readXMLDataforPolicy(doc, "/Dataset/CHN");
		policy.setChn(CHN);
		policy.setCheckStatus("1");
		policy.setTrancode(readXMLDataforPolicy(doc, "/Dataset/Trancode"));
		if (policy.getTrancode().equals("TA78"))
			policy.setPolicyType("2");
		else
			policy.setPolicyType("1");
		policy.setLetterType(readXMLDataforPolicy(doc, "/Dataset/LetterType"));
		policy.setMtsNoAcknowledgement(readXMLDataforPolicy(doc, "/Dataset/MTSNoAcknowledgement"));
		policy.setMtsProjectBankCode(readXMLDataforPolicy(doc, "/Dataset/MTSProjectBankCode"));
		policy.setPrpsno(readXMLDataforPolicy(doc, "/Dataset/PRPSNO"));
		policy.setCntrno(readXMLDataforPolicy(doc, "/Dataset/CNTRNO"));
		policy.setOwnerName(readXMLDataforPolicy(doc, "/Dataset/OwnerName"));
		policy.setOwnerSex(readXMLDataforPolicy(doc, "/Dataset/OwnerSex"));
		policy.setOwnerMail(readXMLDataforPolicy(doc, "/Dataset/OwnerMail"));
		if (policy.getOwnerMail() != null)
			policy.setOwnerMail(policy.getOwnerMail().trim());
		policy.setOwnerIDNumber(readXMLDataforPolicy(doc, "/Dataset/OwnerIDNumber"));
		policy.setPrintPolicyIndicator(readXMLDataforPolicy(doc, "/Dataset/PrintPolicyIndicator"));
		policy.setLetterctl(readXMLDataforPolicy(doc, "/Dataset/LETTERCTL"));
		FtpUtils ftpUtil = new FtpUtils();
		if (checkPolicyImportData(policy, policyRootPath, ftpUtil))
			break MISSING_BLOCK_LABEL_403;
		return -1L;
		boolean zipFLag;
		String outZipFilePath;
		String zipName;
		policy.setEmailCode(StringUtil.getUUID(ZonyConfig.getUUIDLength()));
		String productCode = readXMLDataforPolicy(doc, "/Dataset/ComponentDetails/ProductCode");
		policy.setProductCode(productCode);
		productCodeMap = XmlUtils.getCycleNode(doc, "ComponentDetails", "ProductCode", "CoverageName");
		checkAddProductCode(productCodeMap);
		String packageName1 = readXMLDataforPolicy(doc, "/Dataset/PackageName1");
		String packageName2 = readXMLDataforPolicy(doc, "/Dataset/PackageName2");
		if (!StringUtils.isEmpty(packageName1))
		{
			policy.setPackageName(packageName1);
			if (!StringUtils.isEmpty(packageName2))
				policy.setPackageName((new StringBuilder(String.valueOf(packageName1))).append(packageName2).toString());
		}
		projectName = readXMLDataforPolicy(doc, "/Dataset/ProjectName");
		saveProjectNameByName(projectName);
		policy.setProjectName(projectName);
		if (policy.getPackageName() != null)
			savePackageNameByName(policy.getPackageName());
		policy.setSendStatus("1");
		String pol_Acknowledgement = readXMLDataforPolicy(doc, "/Dataset/Pol_Acknowledgement_Date");
		logger.debug((new StringBuilder("+++++++++++++++++pol_Acknowledgement : ")).append(pol_Acknowledgement).toString());
		if (!StringUtils.isEmpty(pol_Acknowledgement) && !pol_Acknowledgement.equals("0"))
		{
			pol_Acknowledgement = (new StringBuilder(String.valueOf(pol_Acknowledgement.substring(0, 4)))).append("-").append(pol_Acknowledgement.substring(4, 6)).append("-").append(pol_Acknowledgement.substring(6)).append(" 00:00:00").toString();
			policy.setPol_AcknowledgementDate(pol_Acknowledgement);
			if (policy.getTrancode().equals(Constant.TOPFUNID) && policy.getMtsNoAcknowledgement().equals("Y"))
				policy.setMtsNoAcknowledgement("N");
			if (policy.getTrancode().equals("TA78"))
				policy.setSignStatus("3");
			else
				policy.setSignStatus("6");
		} else
		{
			policy.setPol_AcknowledgementDate(pol_Acknowledgement);
			policy.setSignStatus("1");
		}
		logger.debug((new StringBuilder("+++++++++++++++++++pol_Acknowledgement :")).append(policy.getPol_AcknowledgementDate()).toString());
		policy.setImportDate(DateUtil.getNow());
		policy.setOutdateStatus("2");
		zipFLag = false;
		outZipFilePath = "";
		zipName = "";
		List tmCodeList = ZonyConfig.getTMProjectCode();
		List tmChnList = ZonyConfig.getTMChnCode();
		try
		{
			if (tmCodeList.contains(projectName) || tmChnList.contains(policy.getChn()))
			{
				isTMFlag = true;
				policy.setSendStatus("6");
				policy.setCheckStatus(null);
				policy.setSignStatus(null);
				String srcFilePath = (new StringBuilder(String.valueOf(ZonyConfig.getCheckPolicySUCCESSPath()))).append(policyRootPath[1]).append(policyRootPath[4]).append("\\").append(policyRootPath[2]).toString();
				String ownerIDNumber = policy.getOwnerIDNumber();
				String pwd = ownerIDNumber.substring(ownerIDNumber.length() - 4);
				outZipFilePath = zipAESFile(srcFilePath, pwd, ZonyConfig.getPolicyTMPath());
				zipName = (new File(srcFilePath)).getName();
				zipName = (new StringBuilder(String.valueOf(ZonyConfig.getPolicyTMPath()))).append("\\").append(policy.getPrpsno()).append("_").append(zipName.substring(0, zipName.lastIndexOf("."))).append(".zip").toString();
				zipFLag = true;
			}
			break MISSING_BLOCK_LABEL_1098;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error((new StringBuilder("执行处理天猫保单时出错,失败时间：")).append(DateUtil.getNow()).append(" 异常信息：").append(ExceptionUtils.getErrorTraceMessage(e)).toString());
		}
		obj;
		JVM INSTR monitorexit ;
		return -1L;
		try
		{
			policy.setPriority(Long.valueOf(Long.parseLong(policyRootPath[5])));
			long newPolicyId = policyInfoDao.savePolicyInfo(policy);
			policyLogLogic.saveLog(Long.valueOf(newPolicyId), "1", null);
			if (zipFLag)
			{
				File tempZipFile = new File(outZipFilePath);
				File zipFile = new File(zipName);
				if (tempZipFile.exists())
				{
					if (zipFile.exists())
						zipFile.delete();
					FileUtils.moveFile(tempZipFile, zipFile);
					policyLogLogic.saveLogWithNote(Long.valueOf(newPolicyId), "28", null, (new StringBuilder("zip文件路径：")).append(zipFile.getAbsolutePath()).toString());
				}
			}
			retFlag = checkPolicyExist(policy);
			break MISSING_BLOCK_LABEL_1295;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error((new StringBuilder("执行保存导入保单数据到系统中时出错,失败时间：")).append(DateUtil.getNow()).append(" 异常信息：").append(ExceptionUtils.getErrorTraceMessage(e)).toString());
		}
		obj;
		JVM INSTR monitorexit ;
		return -1L;
		boolean isAutoSend = policySendConfigLogic.getConfigByHql(CHN, projectName, productCodeMap, orgCode);
		try
		{
			if (!isTMFlag && "1".equals(policy.getCheckStatus()) && "1".equals(PolicyImportThread.SEND_POLICYNOTICE_FLAG) && isAutoSend)
			{
				List idList = new ArrayList();
				List cntronoList = new ArrayList();
				idList.add((new StringBuilder()).append(policy.getId()).toString());
				cntronoList.add(policy.getCntrno());
				emailTaskLogic.addSeedMailTask("", "now", "", idList, cntronoList, policy.getPriority());
				logger.debug((new StringBuilder("保单邮件自动发送已开启,添加一个自动发送任务：id:")).append(policy.getId()).append(",cntrno:").append(policy.getCntrno()).toString());
			}
			break MISSING_BLOCK_LABEL_1524;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error((new StringBuilder("执行添加保单邮件自动发送任务时出错,失败时间：")).append(DateUtil.getNow()).append(" 异常信息：").append(ExceptionUtils.getErrorTraceMessage(e)).toString());
		}
		obj;
		JVM INSTR monitorexit ;
		return -1L;
		(long)retFlag;
		obj;
		JVM INSTR monitorexit ;
		return;
		obj;
		JVM INSTR monitorexit ;
		throw ;
	}

	public String zipAESFile(String srcfilePath, String pwd, String outZipFolderPath)
	{
		String outZipFilePath = "";
		try
		{
			String zipName = (new StringBuilder("TEMP_")).append(UUID.randomUUID().toString()).append(".zip").toString();
			outZipFilePath = (new StringBuilder(String.valueOf(outZipFolderPath))).append("\\").append(zipName).toString();
			CompressUtil.zip(srcfilePath, outZipFilePath, pwd);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error((new StringBuilder("生成zip压缩包并加密发送错误，错误信息：")).append(ExceptionUtils.getErrorTraceMessage(e)).toString());
		}
		return outZipFilePath;
	}

	public Long savePackageNameByName(String packageName)
	{
		packageName = packageName.trim();
		Long result = new Long(-1L);
		if (StringUtils.isEmpty(packageName))
			return result;
		PackageName name = packageNameDao.getPackageCountByName(packageName);
		if (name == null)
		{
			PackageName Package = new PackageName();
			Package.setPackageName(packageName);
			result = Long.valueOf(packageNameDao.savePackageName(Package));
		}
		return result;
	}

	public Long saveProjectNameByName(String projectName)
	{
		projectName = projectName.trim();
		Long result = new Long(-1L);
		if (StringUtils.isEmpty(projectName))
			return result;
		ProjectName ject = projectNameDao.getProjectByName(projectName);
		if (ject == null)
		{
			ProjectName project = new ProjectName();
			project.setProjectName(projectName);
			result = Long.valueOf(projectNameDao.saveProjectName(project));
		}
		return result;
	}

	public void checkAddProductCode(Map codeMap)
	{
		for (Iterator it = codeMap.keySet().iterator(); it.hasNext();)
		{
			String productCode = ((String)it.next()).toString();
			String productName = (String)codeMap.get(productCode);
			if (!StringUtils.isEmpty(productCode))
			{
				ProductCode codeResult = productCodeDao.getProductCodeListByCode(productCode);
				if (codeResult == null)
				{
					ProductCode productCodeObj = new ProductCode();
					productCodeObj.setProductCode(productCode);
					productCodeObj.setProductName(productName);
					productCodeDao.addProductCode(productCodeObj);
				}
			}
		}

	}

	public static boolean checkPolicyTime(long time)
	{
		Date dt = new Date();
		Date d1 = new Date(time);
		long curr = dt.getTime() - d1.getTime();
		return curr <= 0x5265c00L;
	}

	public static String cutCopyPolicayData(String savePath, String dirName, String importPath, FTPImportUtil ftpUtils)
	{
		String returnPath = "";
		String tempimportPath = DateUtil.getNow(ZonyConfig.getCreaPathformat());
		String trangePath = null;
		try
		{
			trangePath = getImportFileBackupspath((new StringBuilder(String.valueOf(savePath))).append(tempimportPath).append(dirName).append("\\").toString());
			ftpUtils.processdownload(trangePath, importPath);
			ftpUtils.deleteFile(importPath);
			returnPath = trangePath;
			returnPath = (new StringBuilder(String.valueOf(returnPath))).append(",").append(tempimportPath).toString();
		}
		catch (Exception e)
		{
			if (trangePath != null)
				try
				{
					File destfile = new File(trangePath);
					destfile.exists();
				}
				catch (Exception e2)
				{
					logger.error((new StringBuilder("执行剪切文件夹数据 时出错,失败时间：")).append(DateUtil.getNow()).append(" 异常信息：").append(ExceptionUtils.getErrorTraceMessage(e2)).toString());
					return null;
				}
			return null;
		}
		return returnPath;
	}

	public static String[] cutCopyPolicayDataByLoaclFile(String savePath, String dirName, String importPath)
	{
		String returnPath[] = new String[5];
		String tempimportPath = (new StringBuilder(String.valueOf(DateUtil.getNow(ZonyConfig.getCreaPathformat())))).append(dirName).append(File.separator).toString();
		String trangePath = null;
		try
		{
			trangePath = getImportFileBackupspath((new StringBuilder(String.valueOf(savePath))).append(tempimportPath).toString());
			FileOperteUtil.newFolder(trangePath);
			FileOperteUtil.moveFolder(importPath, trangePath);
			returnPath[0] = trangePath;
			returnPath[1] = tempimportPath;
		}
		catch (Exception e)
		{
			if (trangePath != null)
				try
				{
					File destfile = new File(trangePath);
					if (destfile.exists())
						FileOperteUtil.delFolder(trangePath);
				}
				catch (Exception e2)
				{
					logger.error((new StringBuilder("执行剪切文件夹数据 时出错,失败时间：")).append(DateUtil.getNow()).append(" 异常信息：").append(ExceptionUtils.getErrorTraceMessage(e2)).toString());
					return null;
				}
			return null;
		}
		return returnPath;
	}

	public static String getImportFileBackupspath(String fileName)
		throws IOException
	{
		File destfile = new File(fileName);
		if (destfile.exists())
			FileOperteUtil.deleteDirectory(fileName);
		return fileName;
	}

	public static Document getDocument(String xmlpath)
		throws FileNotFoundException
	{
		InputStreamReader isr = null;
		try
		{
			isr = new InputStreamReader(new FileInputStream(xmlpath), "gbk");
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return XmlUtils.getDocumentReader(isr);
	}

	public static void StartPolicyImportThread()
	{
		for (int i = 0; i < ZonyConfig.getCHECK_POLICYNOTICE_THREAD_COUNT(); i++)
		{
			PolicyImportThread t1 = new PolicyImportThread();
			t1.start();
		}

	}

}
