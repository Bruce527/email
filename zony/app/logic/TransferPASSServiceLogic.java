// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TransferPASSServiceLogic.java

package com.zony.app.logic;

import com.zony.app.client.ESBEnvelope;
import com.zony.app.client.ESBEnvelopeESBHeader;
import com.zony.app.client.ESBEnvelopeMsgBody;
import com.zony.app.client.ESBEnvelopeResult;
import com.zony.app.client.ESBEnvelopeResultESBHeader;
import com.zony.app.client.ESBEnvelopeResultMsgBody;
import com.zony.app.client.ESBWebEntry;
import com.zony.app.client.ESBWebEntryLocator;
import com.zony.app.client.ESBWebEntrySoap_BindingStub;
import com.zony.app.client.ESBWebEntrySoap_PortType;
import com.zony.app.dao.PolicyInfoDao;
import com.zony.app.dao.TransferPassLogDao;
import com.zony.app.domain.PolicyInfo;
import com.zony.app.domain.TransferPassLog;
import com.zony.common.config.ZonyConfig;
import com.zony.common.util.DateUtil;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import net.sf.json.JSONObject;
import org.apache.axis.message.MessageElement;
import org.apache.axis.types.Time;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.DOMOutputter;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// Referenced classes of package com.zony.app.logic:
//			PolicyLogLogic

public class TransferPASSServiceLogic
{

	private static final Logger logger = Logger.getLogger(com/zony/app/logic/TransferPASSServiceLogic);
	private PolicyInfoDao policyInfoDao;
	private TransferPassLogDao transferPassLogDao;
	PolicyLogLogic policyLogLogic;

	public TransferPASSServiceLogic()
	{
	}

	public void setPassPolicyByLog()
	{
		try
		{
			int tranCount = Integer.parseInt(ZonyConfig.getTranCount());
			List logList = transferPassLogDao.getTransfailLogList(tranCount);
			PolicyInfo policyInfo;
			for (Iterator iterator = logList.iterator(); iterator.hasNext(); getPassWSDL(policyInfo))
			{
				TransferPassLog transferPassLog = (TransferPassLog)iterator.next();
				String cntrno = transferPassLog.getCntrno();
				policyInfo = policyInfoDao.getPolicyByCntrno(cntrno);
			}

		}
		catch (Exception e)
		{
			logger.error((new StringBuilder("link passSys webservice error：")).append(e.getMessage()).toString(), e);
			e.printStackTrace();
		}
	}

	public void getPassWSDL(PolicyInfo policy)
	{
		try
		{
			ESBEnvelope esbrequest = createESBEnvelope(policy);
			String endpoint = ZonyConfig.getEAIUrl();
			ESBWebEntry service = new ESBWebEntryLocator();
			ESBWebEntrySoap_PortType soap = null;
			soap = (ESBWebEntrySoap_BindingStub)service.getESBWebEntrySoap(new URL(endpoint));
			ESBEnvelopeResult esbresponse = soap.processMessage(esbrequest);
			parseResponseAddLog(esbresponse, policy);
		}
		catch (Exception e)
		{
			logger.error((new StringBuilder("link passSys webservice error：")).append(e.getMessage()).append("\n失败的保单号:").append(policy.getCntrno()).toString(), e);
			e.printStackTrace();
		}
	}

	public ESBEnvelope createESBEnvelope(PolicyInfo policy)
	{
		ESBEnvelope tESBEnvelope;
		String cntrno = policy.getCntrno();
		String policyId = (new StringBuilder()).append(policy.getId()).toString();
		String nowDate = DateUtil.getNow();
		String polDate = "";
		if (policy.getPol_AcknowledgementDate() != null && StringUtils.isNotEmpty(policy.getPol_AcknowledgementDate()))
			if (policy.getPol_AcknowledgementDate().length() == 17)
				polDate = DateUtil.getSampleDate(policy.getPol_AcknowledgementDate(), "yyyyMMdd HH:mm:ss", "yyyy-MM-dd");
			else
				polDate = DateUtil.getSampleDate(policy.getPol_AcknowledgementDate());
		String jsonStr = (new StringBuilder("[{'branch':'")).append(policy.getBrcd()).append("','policyNo':'").append(cntrno).append("','taskid':'").append(policyId).append("',").append("'secureCode':'TN01','receiptsReceiveDate':'").append(polDate).append("','receiptEnterDate':'").append(nowDate).append("','phoneConfirmReceiveDate':''}]").toString();
		Document msgDoc = new Document();
		Element msg = new Element("Msg");
		msg.addContent((new Element("serviceType")).addContent("Business"));
		msg.addContent((new Element("json")).addContent(jsonStr));
		msg.addContent((new Element("systemSources")).addContent(ZonyConfig.getEAILocalSystem()));
		msg.addContent((new Element("businessSources")).addContent(ZonyConfig.getEAILocalSystem()));
		msg.addContent((new Element("businessType")).addContent("Direct"));
		msg.addContent((new Element("workNo")).addContent(policyId));
		msg.addContent((new Element("applyCumtNo")).addContent(""));
		msg.addContent((new Element("applyDate")).addContent(polDate));
		msg.addContent((new Element("chdrsel")).addContent(cntrno));
		msg.addContent((new Element("appRole")).addContent(ZonyConfig.getEAIAppRole()));
		msg.addContent((new Element("attribute1")).addContent(""));
		msg.addContent((new Element("attribute2")).addContent(""));
		msgDoc.addContent(msg);
		DOMOutputter converter = new DOMOutputter();
		org.w3c.dom.Document domDocument = converter.output(msgDoc);
		MessageElement ttMessageElement = new MessageElement(domDocument.getDocumentElement());
		MessageElement tMessageElement[] = {
			ttMessageElement
		};
		ESBEnvelopeMsgBody tESBEnvelopeMsgBody = new ESBEnvelopeMsgBody(tMessageElement);
		tESBEnvelope = new ESBEnvelope();
		ESBEnvelopeESBHeader ESBHeader = new ESBEnvelopeESBHeader();
		ESBHeader.setSrvOpName(ZonyConfig.getEAISrvOpName());
		ESBHeader.setSenderID(ZonyConfig.getEAISenderID());
		ESBHeader.setReceiverID(ZonyConfig.getEAIReceiverID());
		ESBHeader.setSrvDate(new Date());
		ESBHeader.setSrvTime(new Time(Calendar.getInstance()));
		tESBEnvelope.setESBHeader(ESBHeader);
		tESBEnvelope.setMsgBody(tESBEnvelopeMsgBody);
		return tESBEnvelope;
		Exception e;
		e;
		logger.error((new StringBuilder("create ESBEnvelope model error ")).append(e.getMessage()).append("\n失败的保单号").append(policy.getCntrno()).toString(), e);
		e.printStackTrace();
		return null;
	}

	public void parseResponseAddLog(ESBEnvelopeResult esb, PolicyInfo policy)
	{
		try
		{
			logger.debug((new StringBuilder("返回头code:")).append(esb.getESBHeader().getESBRspCode()).append(",返回头desc:").append(esb.getESBHeader().getESBRspDec()).toString());
			String resultStr = esb.getMsgBody().get_any()[0].getElementsByTagName("Result").item(0).getFirstChild().getNodeValue();
			if (resultStr != null)
			{
				JSONObject result = JSONObject.fromObject(resultStr);
				logger.debug((new StringBuilder("返回代码:")).append(result.getString("code")).append("返回代码的描述:").append(result.getString("info")).toString());
				TransferPassLog transferPassLog = transferPassLogDao.getTransferPassLog(policy.getCntrno());
				if (result.getString("code").equals("00"))
				{
					policyLogLogic.saveLog(policy.getId(), "31", null);
					setTransferLog(transferPassLog, "1", policy.getCntrno());
				} else
				{
					policyLogLogic.saveLogWithNote(policy.getId(), "30", null, (new StringBuilder("code:")).append(result.getString("code")).append(",info:").append(result.getString("info")).toString());
					setTransferLog(transferPassLog, "2", policy.getCntrno());
				}
			}
		}
		catch (Exception e)
		{
			logger.error((new StringBuilder("解析PASS返回结果异常 ：")).append(e.getMessage()).append("\n失败的保单号:").append(policy.getCntrno()).toString(), e);
			e.printStackTrace();
		}
	}

	public void setTransferLog(TransferPassLog transferPassLog, String status, String cntrno)
	{
		if (transferPassLog != null)
		{
			int count = transferPassLog.getTransferCount() + 1;
			transferPassLog.setTransferTime(DateUtil.getNow());
			transferPassLog.setTransferStatus(status);
			transferPassLog.setTransferCount(count);
			transferPassLogDao.updateTransPassLog(transferPassLog);
		} else
		{
			transferPassLog = new TransferPassLog();
			transferPassLog.setCntrno(cntrno);
			transferPassLog.setTransferTime(DateUtil.getNow());
			transferPassLog.setTransferStatus(status);
			transferPassLog.setTransferCount(1);
			transferPassLogDao.saveTransferPassLog(transferPassLog);
		}
	}

	public List getPolicyInfoToPass()
	{
		String hql = (new StringBuilder("from PolicyInfo p where ( signStatus in ('2','5','4','3') and pol_AcknowledgementDate is not null ) and id in (select policyID from PolicyLog where logEvent in(12,14,15,16) and cntrno not in (select cntrno from TransferPassLog)) and pol_AcknowledgementDate > '")).append(ZonyConfig.getEAITransferBeginDate()).append("'").toString();
		return policyInfoDao.getPolicyInfoNum(hql, 20);
		Exception e;
		e;
		logger.error((new StringBuilder("获取调用pass系统保单出现异常:")).append(e.getMessage()).toString(), e);
		return null;
	}

	public void setPolicyInfoListToPass()
	{
		try
		{
			List policyList = getPolicyInfoToPass();
			PolicyInfo policyInfo;
			for (Iterator iterator = policyList.iterator(); iterator.hasNext(); getPassWSDL(policyInfo))
				policyInfo = (PolicyInfo)iterator.next();

		}
		catch (Exception e)
		{
			logger.error((new StringBuilder("loop link passSys webservice error：")).append(e.getMessage()).toString(), e);
			e.printStackTrace();
		}
	}

}
