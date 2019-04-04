// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FunctionInfoLogic.java

package com.zony.app.logic;

import com.zony.app.dao.FunctionInfoDao;
import com.zony.app.dao.VroleFunDao;
import com.zony.app.model.treenode.FunctionInfoTreeNode;
import com.zony.app.model.treenode.FunctionInfoTreeNodeConverter;
import com.zony.common.util.tree.TreeConverter;
import com.zony.common.util.tree.TreeUtils;
import java.util.List;

// Referenced classes of package com.zony.app.logic:
//			RightManageLogic

public class FunctionInfoLogic
{

	private FunctionInfoDao functionInfoDao;
	private VroleFunDao vroleFunDao;
	private RightManageLogic rightManageLogic;

	public FunctionInfoLogic()
	{
	}

	public List getFunctionInfoModel()
		throws Exception
	{
		return functionInfoDao.getFunctionInfoModel();
	}

	public String generateFunctionInfoXMLByList(List modelList)
	{
		String functionInfoXML = "";
		String rootId = "0";
		TreeConverter treeConverter = new TreeConverter(new FunctionInfoTreeNodeConverter(), "0");
		FunctionInfoTreeNode rootNode = new FunctionInfoTreeNode("0", "系统功能");
		functionInfoXML = TreeUtils.convertSameClassTree(modelList, treeConverter, rootNode);
		return functionInfoXML;
	}
}
