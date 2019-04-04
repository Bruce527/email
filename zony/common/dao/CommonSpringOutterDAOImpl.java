// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CommonSpringOutterDAOImpl.java

package com.zony.common.dao;

import com.zony.common.util.PageUtil;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.hibernate.*;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

// Referenced classes of package com.zony.common.dao:
//			BaseDAO

public abstract class CommonSpringOutterDAOImpl extends BaseDAO
{

	private HibernateTemplate hibernateTemplate;

	public CommonSpringOutterDAOImpl()
	{
	}

	public void delete(Object t)
	{
		hibernateTemplate.delete(t);
	}

	public void deleteById(Serializable id)
	{
		Object object = hibernateTemplate.get(domainClass, id);
		hibernateTemplate.delete(object);
	}

	public Object getById(Serializable id)
	{
		return hibernateTemplate.get(domainClass, id);
	}

	public Object getModelById(Serializable id)
		throws Exception
	{
		return getModelById(id, null);
	}

	public Object getModelById(Serializable id, Map paramObj)
		throws Exception
	{
		Object d = hibernateTemplate.get(domainClass, id);
		Object m = convertFromDomain(d, paramObj);
		return m;
	}

	public transient Object getEntity(String hql, Object params[])
	{
		List tList = getEntityList(hql, params);
		if (tList.size() > 0)
			return tList.get(0);
		else
			return null;
	}

	public transient Object getModel(String hql, Object params[])
		throws Exception
	{
		return getModel(hql, params, null);
	}

	public Object getModel(String hql, Object params[], Object paramObj)
		throws Exception
	{
		List tList = getModelList(hql, new Object[] {
			params, paramObj
		});
		if (tList.size() > 0)
			return tList.get(0);
		else
			return null;
	}

	public transient Object getModelBySql(String sql, Object params[])
		throws Exception
	{
		return getModelBySql(sql, params, null);
	}

	public Object getModelBySql(String sql, Object params[], Object paramObj)
		throws Exception
	{
		List tList = getModelListBySql(sql, new Object[] {
			params, paramObj
		});
		if (tList.size() > 0)
			return tList.get(0);
		else
			return null;
	}

	public transient List getEntityList(String hql, Object params[])
	{
		return hibernateTemplate.find(hql, params);
	}

	public transient List getEntityListLimited(final String hql, final int maxResults, final Object params[])
	{
		return hibernateTemplate.executeFind(new HibernateCallback() {

			final CommonSpringOutterDAOImpl this$0;
			private final String val$hql;
			private final Object val$params[];
			private final int val$maxResults;

			public Object doInHibernate(Session session)
				throws HibernateException, SQLException
			{
				Query query = session.createQuery(hql);
				if (params != null)
				{
					for (int i = 0; i < params.length; i++)
						query.setParameter(i, params[i]);

				}
				query.setMaxResults(maxResults);
				return query.list();
			}

			
			{
				this$0 = CommonSpringOutterDAOImpl.this;
				hql = s;
				params = aobj;
				maxResults = i;
				super();
			}
		});
	}

	public transient List getEntityListBySql(final String sql, final Object params[])
	{
		return hibernateTemplate.executeFind(new HibernateCallback() {

			final CommonSpringOutterDAOImpl this$0;
			private final String val$sql;
			private final Object val$params[];

			public Object doInHibernate(Session session)
				throws HibernateException, SQLException
			{
				SQLQuery query = session.createSQLQuery(sql).addEntity(domainClass);
				if (params != null)
				{
					for (int i = 0; i < params.length; i++)
						query.setParameter(i, params[i]);

				}
				return query.list();
			}

			
			{
				this$0 = CommonSpringOutterDAOImpl.this;
				sql = s;
				params = aobj;
				super();
			}
		});
	}

	public transient List getModelList(String hql, Object params[])
		throws Exception
	{
		return getModelList(hql, params, null);
	}

	public transient List getModelListLimited(String hql, int maxResults, Object params[])
		throws Exception
	{
		return getModelListLimited(hql, params, maxResults, null);
	}

	public List getModelList(String hql, Object params[], Map paramObj)
		throws Exception
	{
		List dList = hibernateTemplate.find(hql, params);
		List mList = convertFromDomainList(dList, paramObj);
		return mList;
	}

	public List getModelListLimited(String hql, Object params[], int maxResults, Map paramObj)
		throws Exception
	{
		List dList = getEntityListLimited(hql, maxResults, new Object[0]);
		List mList = convertFromDomainList(dList, paramObj);
		return mList;
	}

	public transient List getModelListBySql(String sql, Object params[])
		throws Exception
	{
		return getModelListBySql(sql, params, null);
	}

	public List getModelListBySql(String sql, Object params[], Map paramObj)
		throws Exception
	{
		List entityList = getEntityListBySql(sql, params);
		List mList = convertFromDomainList(entityList, paramObj);
		return mList;
	}

	public void update(Object t)
	{
		hibernateTemplate.update(t);
	}

	public Serializable save(Object t)
	{
		return hibernateTemplate.save(t);
	}

	public void saveList(final List list)
	{
		hibernateTemplate.execute(new HibernateCallback() {

			final CommonSpringOutterDAOImpl this$0;
			private final List val$list;

			public Object doInHibernate(Session session)
				throws HibernateException, SQLException
			{
				for (int i = 0; i < list.size(); i++)
				{
					session.save(list.get(i));
					if (i % 20 == 0)
					{
						session.flush();
						session.clear();
					}
				}

				return null;
			}

			
			{
				this$0 = CommonSpringOutterDAOImpl.this;
				list = list1;
				super();
			}
		});
	}

	public int bulkUpdate(String hql)
	{
		return hibernateTemplate.bulkUpdate(hql, null);
	}

	public int bulkUpdate(String hql, Object params[])
	{
		return hibernateTemplate.bulkUpdate(hql, params);
	}

	public int bulkUpdate(String hql, Object param)
	{
		return hibernateTemplate.bulkUpdate(hql, param);
	}

	public List getAll()
	{
		return hibernateTemplate.loadAll(domainClass);
	}

	public List getByPage(final String hql, final Object params[], final PageUtil page)
	{
		return hibernateTemplate.executeFind(new HibernateCallback() {

			final CommonSpringOutterDAOImpl this$0;
			private final String val$hql;
			private final Object val$params[];
			private final PageUtil val$page;

			public Object doInHibernate(Session session)
				throws HibernateException, SQLException
			{
				String hqlCount = (new StringBuilder(" select count (*) ")).append(removeSelect(removeOrders(hql))).toString();
				Long totalCount = getCount(hqlCount, params);
				page.setTotalCount(totalCount.intValue());
				Query query = session.createQuery(hql);
				if (params != null)
				{
					for (int i = 0; i < params.length; i++)
						query.setParameter(i, params[i]);

				}
				query.setFirstResult(page.getFirstResult());
				query.setMaxResults(page.getPageSize());
				return query.list();
			}

			
			{
				this$0 = CommonSpringOutterDAOImpl.this;
				hql = s;
				params = aobj;
				page = pageutil;
				super();
			}
		});
	}

	public transient Long getCount(final String hql, final Object params[])
	{
		return (Long)hibernateTemplate.execute(new HibernateCallback() {

			final CommonSpringOutterDAOImpl this$0;
			private final String val$hql;
			private final Object val$params[];

			public Object doInHibernate(Session session)
				throws HibernateException, SQLException
			{
				Query query = session.createQuery(hql);
				if (params != null)
				{
					for (int i = 0; i < params.length; i++)
						query.setParameter(i, params[i]);

				}
				return query.uniqueResult();
			}

			
			{
				this$0 = CommonSpringOutterDAOImpl.this;
				hql = s;
				params = aobj;
				super();
			}
		});
	}

	public transient List getField(final String hql, final Object params[])
	{
		return hibernateTemplate.executeFind(new HibernateCallback() {

			final CommonSpringOutterDAOImpl this$0;
			private final String val$hql;
			private final Object val$params[];

			public Object doInHibernate(Session session)
				throws HibernateException, SQLException
			{
				Query query = session.createQuery(hql);
				if (params != null)
				{
					for (int i = 0; i < params.length; i++)
						query.setParameter(i, params[i]);

				}
				return query.list();
			}

			
			{
				this$0 = CommonSpringOutterDAOImpl.this;
				hql = s;
				params = aobj;
				super();
			}
		});
	}

	public transient List getFields(final String hql, final Object params[])
	{
		return hibernateTemplate.executeFind(new HibernateCallback() {

			final CommonSpringOutterDAOImpl this$0;
			private final String val$hql;
			private final Object val$params[];

			public Object doInHibernate(Session session)
				throws HibernateException, SQLException
			{
				Query query = session.createQuery(hql);
				if (params != null)
				{
					for (int i = 0; i < params.length; i++)
						query.setParameter(i, params[i]);

				}
				return query.list();
			}

			
			{
				this$0 = CommonSpringOutterDAOImpl.this;
				hql = s;
				params = aobj;
				super();
			}
		});
	}
}
