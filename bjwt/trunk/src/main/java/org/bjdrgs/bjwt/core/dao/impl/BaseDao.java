package org.bjdrgs.bjwt.core.dao.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.bjdrgs.bjwt.core.dao.DaoUtils;
import org.bjdrgs.bjwt.core.dao.IBaseDao;
import org.bjdrgs.bjwt.core.exception.ApplicationException;
import org.bjdrgs.bjwt.core.util.ReflectionUtils;
import org.bjdrgs.bjwt.core.web.Pagination;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.impl.CriteriaImpl;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * 基础DAO类
 * @author ying
 * @date 2012-12-12
 *
 * @param <T>
 */
public class BaseDao<T extends Serializable> extends HibernateDaoSupport implements IBaseDao<T> {
	
	private Class<T> entityClass;
	
	/**
	 * @constructor
	 */
	public BaseDao() {
		this.entityClass = ReflectionUtils.getSuperClassGenricType(this.getClass());
	}
	
	@Override
	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	@Override
	public void flush() {
		try {
			getHibernateTemplate().flush();
		} catch(RuntimeException e) {
			throw new ApplicationException(e);
		}
	}
	
	@Override
	public void clear() {
		try {
			getHibernateTemplate().clear();
		} catch(RuntimeException e) {
			throw new ApplicationException(e);
		}
	}
	
	@Override
	public void evict(T entity) {
		try {
			getHibernateTemplate().evict(entity);
		} catch(RuntimeException e) {
			throw new ApplicationException(e);
		}
	}
	
	@Override
	public void merge(T entity) {
		try {
			getHibernateTemplate().merge(entity);
		} catch(RuntimeException e) {
			throw new ApplicationException(e);
		}
	}

	@Override
	public void save(T entity) {
		try {
			getHibernateTemplate().saveOrUpdate(entity);
		} catch(RuntimeException e) {
			throw new ApplicationException(e);
		}
	}
	
	@Override
	public void update(String hql, Object... parameters) {
		try {
			Query query = this.getSession().createQuery(hql);
			
			this.applyParametersToQuery(query, parameters);
			
			query.executeUpdate();
		} catch(RuntimeException e) {
			throw new ApplicationException(e);
		}
	}
	
	@Override
	public void save(Collection<T> entities) {
		try {
			getHibernateTemplate().saveOrUpdateAll(entities);
		} catch(RuntimeException e) {
			throw new ApplicationException(e);
		}
	}
	
	@Override
	public void updatePropertyByUnique(String unique, String property, Object value) {
		try {
			String hql = "update " + entityClass.getName() + " as demo set demo." + property + "=?" 
				+ " where demo." + unique + "=?";
			Query query = this.getSession().createQuery(hql);
			query.setParameter(0, property);
			query.setParameter(1, unique);
			query.executeUpdate();
		} catch(RuntimeException e) {
			throw new ApplicationException(e);
		}
	}
	
	@Override
	public void delete(T entity) {
		try {
			getHibernateTemplate().delete(entity);
		} catch(RuntimeException e) {
			throw new ApplicationException(e);
		}
	}
	
	@Override
	public void delete(String hql, Object... parameters) {
		try {
			Query query = this.getSession().createQuery(hql);
			
			this.applyParametersToQuery(query, parameters);
			
			query.executeUpdate();
		} catch (RuntimeException re) {
			throw new ApplicationException(re);
		}
	}
	
	@Override
	public void deleteById(Serializable id) {
		try {
			this.delete(this.findById(id));
		} catch(RuntimeException e) {
			throw new ApplicationException(e);
		}
	}
	
	@Override
	public void deleteByUnique(String unique, Object value) {
		try {
			this.delete(this.findByUnique(unique, value));
		} catch(RuntimeException e) {
			throw new ApplicationException(e);
		}
	}
	
	@Override
	public void delete(Collection<T> entities) {
		try {
			getHibernateTemplate().deleteAll(entities);
		} catch(RuntimeException e) {
			throw new ApplicationException(e);
		}
	}
	
	@Override
	public T findById(Serializable id) {
		try {
			T entity = getHibernateTemplate().load(entityClass, id);
			
			if(entity == null) {
				this.clear();
				entity = getHibernateTemplate().get(entityClass, id);
			}
			
			return entity;
		} catch(RuntimeException e) {
			throw new ApplicationException(e);
		}
	}
	
	@Override
	public T getById(Serializable id) {
		try {
			this.clear();
			return getHibernateTemplate().get(entityClass, id);
		} catch(RuntimeException e) {
			throw new ApplicationException(e);
		}
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public T findByUnique(String unique, Object value) {
		try {
			T entity = null;
			
			String hql = "from " + entityClass.getName() + " as demo where demo." + unique + "=?";
			Object[] parameters = new Object[1];
			parameters[0] = value;
			
			List<T> result = getHibernateTemplate().find(hql, parameters);
			if(result != null && result.size() > 0) {
				entity = result.get(0);
			}
			
			return entity;
		} catch (RuntimeException re) {
			throw new ApplicationException(re);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByProperties(String[] properties, Object[] values) {
		try {
			List<T> result = null;
			
			if(properties == null || values == null || properties.length == 0 || values.length == 0
					|| properties.length != values.length) {
				return result;
			}
			
			String hql = "from " + entityClass.getName() + " as demo where 1=1";
			for(int i=0,c=properties.length;i<c;i++) {
				hql += " and demo." + properties[i] + "=?";
			}
			
			result = getHibernateTemplate().find(hql, values);		
			return result;
		} catch (RuntimeException re) {
			throw new ApplicationException(re);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> query(String hql, Object... parameters) {
		try {
			List<T> result = null;
			Query query = this.getSession().createQuery(hql);
			this.applyParametersToQuery(query, parameters);

			prepareQuery(query);
			result = query.list();
			return result;
		} catch (RuntimeException re) {
			throw new ApplicationException(re);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> query(final DetachedCriteria detachedCriteria) {
		return (List<T>) getHibernateTemplate().execute(new HibernateCallback<T>() {
			public T doInHibernate(Session session) throws HibernateException,
					SQLException {
				Criteria executableCriteria = detachedCriteria.getExecutableCriteria(session);
				prepareCriteria(executableCriteria);
				return (T) executableCriteria.list();
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Pagination<T> findPageByHql(Pagination<T> pagination, final String hql, Object... parameters) {
		//设置数据的总条数
		Query queryCount = this.getSession().createQuery(createCountHql(hql));
		this.applyParametersToQuery(queryCount, parameters);
		Long count = (Long) queryCount.uniqueResult();
		pagination.setRecordCount(count.intValue());	
		
		Query queryResult = this.getSession().createQuery(hql);
		this.applyParametersToQuery(queryResult, parameters);
		queryResult.setFirstResult(pagination.getFirst()-1);
		queryResult.setMaxResults(pagination.getPageSize());
	   	pagination.setResult(queryResult.list());
		return pagination;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Pagination<T> findPageByHql(final int pageNo, final int pageSize,
			final String hql, final Object... parameters) {
		return (Pagination<T>) getHibernateTemplate().execute(new HibernateCallback<T>() {
			public T doInHibernate(Session session) throws HibernateException {
				// 查询总记录数
				Query queryCount = session.createQuery(createCountHql(hql));
				applyParametersToQuery(queryCount, parameters);
				int recordCount = ((Long) queryCount.uniqueResult()).intValue();

				// 查询指定分页记录
				Query queryResult = session.createQuery(hql);
				applyParametersToQuery(queryResult, parameters);
				return (T) getResultPage(queryResult, pageNo, pageSize, recordCount);
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Pagination<T> findPageByDetachedCriteria(final DetachedCriteria detachedCriteria,
			final Pagination<T> pagination) {
		/*return (Pagination<T>)getHibernateTemplate().execute(new HibernateCallback<T>() {
			public T doInHibernate(Session session) throws HibernateException, SQLException {
				
				Criteria executableCriteria = detachedCriteria.getExecutableCriteria(session);
				prepareCriteria(executableCriteria);
				int count = countQueryResult(executableCriteria);
				int index = (pageNo - 1) * pageSize;
				if (count <= 0) {
					//return new Pagination(index, count, pageSize, null);
					return null;
				} else {
					executableCriteria.setFirstResult(index).setMaxResults(pageSize);
					//List list = executableCriteria.list();
					//return new Pagination(index, count, pageSize, list);
					return null;
				}
			}
		});*/
		 return (Pagination<T>) getHibernateTemplate().execute(new HibernateCallback<Pagination<T>>() {   
			            public Pagination<T> doInHibernate(Session session) throws HibernateException {   
			                Criteria criteria = detachedCriteria.getExecutableCriteria(session);  
			              
			                ProjectionList projectionList = Projections.projectionList(); 
			                
			                criteria.setProjection(projectionList);   

			                Long totalCount =(Long)criteria.setProjection(Projections.rowCount()).uniqueResult();   
			                criteria.setProjection(null);   
			                detachedCriteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
			                List<T> items = criteria.setFirstResult(pagination.getFirst()-1).setMaxResults(pagination.getPageSize()).list();   
			                pagination.setResult(items);
			                pagination.setRecordCount(totalCount.intValue());
			                return pagination;
			            }   
			        });   

	}
	
	@Override
	public void deleteByParamList(String hql, String name, Object[] parameterList) {
		try {
			Query query = this.getSession().createQuery(hql);
			query.setParameterList(name, parameterList).executeUpdate();
		} catch (RuntimeException re) {
			throw new ApplicationException(re);
		}
	}
	
	/**
	 * 自动构建查询记录总数
	 * @param criteria
	 * @return
	 */
	@SuppressWarnings("unused")
	private int countQueryResult(Criteria criteria) {
		CriteriaImpl impl = (CriteriaImpl)criteria;
		org.hibernate.criterion.Projection projection = impl.getProjection();
		org.hibernate.transform.ResultTransformer transformer = impl.getResultTransformer();
		//List orderEntries;
		try {
			//orderEntries = (List)ClassUtil.forceGetProperty(impl, "orderEntries");
			//ClassUtil.forceSetProperty(impl, "orderEntries", new ArrayList());
		} catch (Exception e) {
			throw new InternalError(" Runtime Exception impossibility throw ");
		}
		int totalCount = ((Integer)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
		if (totalCount < 1)
			return 0;
		criteria.setProjection(projection);
		if (projection == null)
			criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		if (transformer != null)
			criteria.setResultTransformer(transformer);
		try {
			//ClassUtil.forceSetProperty(impl, "orderEntries", orderEntries);
		} catch (Exception e) {
			throw new InternalError(" Runtime Exception impossibility throw ");
		}
		return totalCount;
	}
	
	/**
	 * 根据hql语句自动构建查询记录总数的hql语句
	 * @param hql
	 * @return
	 */
	private String createCountHql(String hql) {

		String countHQL;
		String temp = hql;
		temp = temp.toUpperCase();
		int fromIndex = temp.indexOf("FROM");
		int orderIndex = temp.indexOf("ORDER BY");
		if (orderIndex > 0) {
			countHQL = "select count(*) " + hql.substring(fromIndex, orderIndex);
		} else {
			countHQL = "select count(*) " + hql.substring(fromIndex);
		}
		
		return countHQL;
	}
	
	/**
	 * 查询指定分页记录
	 * @param query
	 * @param pageNo
	 * @param pageSize
	 * @param recordCount
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Pagination<T> getResultPage(Query query, int pageNo, int pageSize, int recordCount) {
		
		if(recordCount < 1) {
			return new Pagination<T>(null, pageSize, pageNo, recordCount, 0);
		}

		int newPageSize = pageSize;
		int idx = pageNo;

		if (newPageSize <= 0) {
			newPageSize = PAGE_SIZE;
		}
		if (idx <= 0) {
			idx = 1;
		}

		int pageCount = recordCount / newPageSize;
		if (recordCount % newPageSize > 0) {
			pageCount++;
		}
		// 如果输入的页数大于总页数，则取最后一页
		if (idx > pageCount) {
			idx = pageCount;
		}
		
		int startIndex = (idx - 1) * newPageSize;
		query.setFirstResult(startIndex);
		query.setMaxResults(newPageSize);
		List<T> result = (List<T>) query.list();

		return new Pagination<T>(result, newPageSize, idx, recordCount, pageCount);
	}
	

	/**
	 * Query系统优化
	 * @param queryObject
	 */
	private void prepareQuery(Query queryObject) {
		
		if (getHibernateTemplate().isCacheQueries()) {
			queryObject.setCacheable(true);
			if (getHibernateTemplate().getQueryCacheRegion() != null) {
				queryObject.setCacheRegion(getHibernateTemplate().getQueryCacheRegion());
			}
		}
		
		if (getHibernateTemplate().getFetchSize() > 0) {
			queryObject.setFetchSize(getHibernateTemplate().getFetchSize());
		}
		
		if (getHibernateTemplate().getMaxResults() > 0) {
			queryObject.setMaxResults(getHibernateTemplate().getMaxResults());
		}
		
		SessionFactoryUtils.applyTransactionTimeout(queryObject, getHibernateTemplate().getSessionFactory());
	}
	
	/**
	 * Criteria系统优化
	 * @param criteria
	 */
	private void prepareCriteria(Criteria criteria) {
		
		if (getHibernateTemplate().isCacheQueries()) {
			criteria.setCacheable(true);
			if (getHibernateTemplate().getQueryCacheRegion() != null) {
				criteria.setCacheRegion(getHibernateTemplate().getQueryCacheRegion());
			}
		}
		
		if (getHibernateTemplate().getFetchSize() > 0) {
			criteria.setFetchSize(getHibernateTemplate().getFetchSize());
		}
		
		if (getHibernateTemplate().getMaxResults() > 0) {
			criteria.setMaxResults(getHibernateTemplate().getMaxResults());
		}
		
		SessionFactoryUtils.applyTransactionTimeout(criteria, getHibernateTemplate().getSessionFactory());
	}
	
	@SuppressWarnings("unchecked")
	private void applyParametersToQuery(Query query, Object[] parameters){
		if(parameters!=null){
			if(parameters.length>0&&parameters[0] instanceof Map<?, ?>){
				DaoUtils.applyMapParameterToQuery(query, (Map<String, Object>) parameters[0]);
			}else{
					for(int i=0;i<parameters.length;i++) {
						query.setParameter(i, parameters[i]);
					}
			}
		}
	}
}
