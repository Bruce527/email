// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PageUtil.java

package com.zony.common.util;


public class PageUtil
{

	private int pageIndex;
	private final int pageSize;
	private int totalCount;
	private int pageCount;
	public static final int DEFAULT_PAGE_SIZE = 10;

	public PageUtil(int pageIndex, int pageSize)
	{
		if (pageIndex < 1)
			pageIndex = 1;
		if (pageSize < 1)
			pageSize = 1;
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
	}

	public PageUtil(int pageIndex)
	{
		this(pageIndex, 10);
	}

	public int getTotalCount()
	{
		return totalCount;
	}

	public int getPageIndex()
	{
		return pageIndex;
	}

	public int getPageSize()
	{
		return pageSize;
	}

	public int getPageCount()
	{
		return pageCount;
	}

	public void setTotalCount(int totalCount)
	{
		this.totalCount = totalCount;
		pageCount = totalCount / pageSize + (totalCount % pageSize != 0 ? 1 : 0);
		if (this.totalCount == 0 && pageIndex != 1)
			pageIndex = 1;
	}

	public boolean isEmpty()
	{
		return totalCount == 0;
	}

	public boolean getHasNext()
	{
		return pageIndex < pageCount;
	}

	public boolean getHasPre()
	{
		return pageIndex > 1;
	}

	public int getFirstResult()
	{
		return (pageIndex - 1) * pageSize;
	}
}
