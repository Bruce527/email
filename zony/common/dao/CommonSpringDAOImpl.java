// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CommonSpringDAOImpl.java

package com.zony.common.dao;

import com.zony.common.util.PageUtil;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.*;
import org.hibernate.*;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

// Referenced classes of package com.zony.common.dao:
//			BaseDAO

public abstract class CommonSpringDAOImpl extends BaseDAO
{

	private HibernateTemplate hibernateTemplate;

	public CommonSpringDAOImpl()
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

	public List getPolicyInfoNum(String hql, int num)
	{
		hibernateTemplate.setMaxResults(num);
		return hibernateTemplate.find(hql);
	}

	public transient List getEntityListLimited(final String hql, final int maxResults, final Object params[])
	{
		return hibernateTemplate.executeFind(new HibernateCallback() {

			final CommonSpringDAOImpl this$0;
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
				this$0 = CommonSpringDAOImpl.this;
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

			final CommonSpringDAOImpl this$0;
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
				this$0 = CommonSpringDAOImpl.this;
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

			final CommonSpringDAOImpl this$0;
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
				this$0 = CommonSpringDAOImpl.this;
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

	public List getListBySql(final String sql, final Object params[], final Class T, final List list)
	{
		return hibernateTemplate.executeFind(new HibernateCallback() {

			final CommonSpringDAOImpl this$0;
			private final String val$sql;
			private final Object val$params[];
			private final Class val$T;
			private final List val$list;

			public Object doInHibernate(Session session)
				throws HibernateException, SQLException
			{
				SQLQuery query = session.createSQLQuery(sql);
				if (params != null)
				{
					for (int i = 0; i < params.length; i++)
						query.setParameter(i, params[i]);

				}
				CommonSpringDAOImpl.addSclar(query, T, list);
				query.setResultTransformer(Transformers.aliasToBean(T));
				return query.list();
			}

			
			{
				this$0 = CommonSpringDAOImpl.this;
				sql = s;
				params = aobj;
				T = class1;
				list = list1;
				super();
			}
		});
	}

	private static void addSclar(SQLQuery sqlQuery, Class clazz, List fieldList)
	{
		if (clazz == null)
			throw new NullPointerException("[clazz] could not be null!");
		if (fieldList != null && fieldList.size() > 0)
		{
			Field fields[] = clazz.getDeclaredFields();
			for (Iterator iterator = fieldList.iterator(); iterator.hasNext();)
			{
				String fieldName = (String)iterator.next();
				Field afield[];
				int j = (afield = fields).length;
				for (int i = 0; i < j; i++)
				{
					Field field = afield[i];
					if (fieldName.equals(field.getName()))
						if (field.getType() == Long.TYPE || field.getType() == java/lang/Long)
							sqlQuery.addScalar(field.getName(), Hibernate.LONG);
						else
						if (field.getType() == Integer.TYPE || field.getType() == java/lang/Integer)
							sqlQuery.addScalar(field.getName(), Hibernate.INTEGER);
						else
						if (field.getType() == Character.TYPE || field.getType() == java/lang/Character)
							sqlQuery.addScalar(field.getName(), Hibernate.CHARACTER);
						else
						if (field.getType() == Short.TYPE || field.getType() == java/lang/Short)
							sqlQuery.addScalar(field.getName(), Hibernate.SHORT);
						else
						if (field.getType() == Double.TYPE || field.getType() == java/lang/Double)
							sqlQuery.addScalar(field.getName(), Hibernate.DOUBLE);
						else
						if (field.getType() == Float.TYPE || field.getType() == java/lang/Float)
							sqlQuery.addScalar(field.getName(), Hibernate.FLOAT);
						else
						if (field.getType() == Boolean.TYPE || field.getType() == java/lang/Boolean)
							sqlQuery.addScalar(field.getName(), Hibernate.BOOLEAN);
						else
						if (field.getType() == java/lang/String)
							sqlQuery.addScalar(field.getName(), Hibernate.STRING);
						else
						if (field.getType() == java/util/Date)
							sqlQuery.addScalar(field.getName(), Hibernate.TIMESTAMP);
				}

			}

		}
	}

	public List getAll()
	{
		return hibernateTemplate.loadAll(domainClass);
	}

	public List getByPage(final String hql, final Object params[], final PageUtil page)
	{
		return hibernateTemplate.executeFind(new HibernateCallback() {

			final CommonSpringDAOImpl this$0;
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
				this$0 = CommonSpringDAOImpl.this;
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

			final CommonSpringDAOImpl this$0;
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
				this$0 = CommonSpringDAOImpl.this;
				hql = s;
				params = aobj;
				super();
			}
		});
	}

	public transient List getField(final String hql, final Object params[])
	{
		return hibernateTemplate.executeFind(new HibernateCallback() {

			final CommonSpringDAOImpl this$0;
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
				this$0 = CommonSpringDAOImpl.this;
				hql = s;
				params = aobj;
				super();
			}
		});
	}

	public transient List getFields(final String hql, final Object params[])
	{
		return hibernateTemplate.executeFind(new HibernateCallback() {

			final CommonSpringDAOImpl this$0;
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
				this$0 = CommonSpringDAOImpl.this;
				hql = s;
				params = aobj;
				super();
			}
		});
	}

}
