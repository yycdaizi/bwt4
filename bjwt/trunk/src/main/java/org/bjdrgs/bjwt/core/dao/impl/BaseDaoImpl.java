package org.bjdrgs.bjwt.core.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.bjdrgs.bjwt.core.dao.DaoUtils;
import org.bjdrgs.bjwt.core.dao.IBaseDao;
import org.bjdrgs.bjwt.core.util.ReflectionUtils;
import org.bjdrgs.bjwt.core.web.Pagination;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.springframework.util.CollectionUtils;

public abstract class BaseDaoImpl<T extends Serializable> implements IBaseDao<T> {

	private Class<T> entityClass;
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	/**
	 * @constructor
	 */
	public BaseDaoImpl() {
		this.entityClass = ReflectionUtils.getSuperClassGenricType(this.getClass());
	}
	
	public final SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public final void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Session getCurrentSession(){
		return this.getSessionFactory().getCurrentSession();
	}

	@Override
	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	@Override
	public void merge(T entity) {
		this.getCurrentSession().merge(entity);
	}

	@Override
	public void save(T entity) {
		this.getCurrentSession().saveOrUpdate(entity);
	}

	@Override
	public void save(Collection<T> entities) {
		for (T entity : entities) {
			this.save(entity);
		}
	}

	@Override
	public void saveByBatch(Collection<T> entities) {
		int count = 0;
		for (T entity : entities) {
			this.save(entity);
			count++;
			if(count%50 == 0){
				getCurrentSession().flush();
				getCurrentSession().clear();
			}
		}
	}

	@Override
	public void update(String hql, Map<String, Object> parameters) {
		this.executeUpdateHql(hql, parameters);
	}

	@Override
	public void update(String hql, Object... parameters) {
		this.executeUpdateHql(hql, parameters);
	}

	@Override
	public void delete(T entity) {
		this.getCurrentSession().delete(entity);
	}

	@Override
	public void deleteById(Serializable id) {
		this.delete(this.load(id));
	}

	@Override
	public void delete(Collection<T> entities) {
		for (T entity : entities) {
			this.delete(entity);
		}
	}

	@Override
	public void delete(String hql, Map<String, Object> parameters) {
		this.executeUpdateHql(hql, parameters);
	}

	@Override
	public void delete(String hql, Object... parameters) {
		this.executeUpdateHql(hql, parameters);
	}

	@Override
	public void deleteByProperty(String property, Object value) {
		String hql = "delete from " + this.entityClass.getName() + " where " + property +" = ?";
		this.delete(hql, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(Serializable id) {
		return (T) this.getCurrentSession().get(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T load(Serializable id) {
		return (T) this.getCurrentSession().load(entityClass, id);
	}

	@Override
	public T getByUnique(String unique, Object value) {
		List<T> result = this.queryByProperty(unique, value);
		if(!CollectionUtils.isEmpty(result)){
			return result.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> query(String hql, Map<String, Object> parameters) {
		Query query = this.createQuery(hql);
		DaoUtils.applyParametersToQuery(query, parameters);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> query(String hql, Object... parameters) {
		Query query = this.createQuery(hql);
		DaoUtils.applyParametersToQuery(query, parameters);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> query(DetachedCriteria detachedCriteria) {
		Criteria executableCriteria = detachedCriteria.getExecutableCriteria(this.getCurrentSession());
		return (List<T>) executableCriteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pagination<T> queryForPage(String hql, Pagination<T> pagination,
			Map<String, Object> parameters) {
		Query queryCount = this.createQuery(createCountHql(hql));
		DaoUtils.applyParametersToQuery(queryCount, parameters);
		Long count = (Long) queryCount.uniqueResult();
		pagination.setRecordCount(count.intValue());	
		
		Query queryResult = this.createQuery(hql);
		DaoUtils.applyParametersToQuery(queryResult, parameters);
		queryResult.setFirstResult(pagination.getFirst()-1);
		queryResult.setMaxResults(pagination.getPageSize());
	   	pagination.setResult(queryResult.list());
		return pagination;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pagination<T> queryForPage(String hql, Pagination<T> pagination, 
			Object... parameters) {
		//设置数据的总条数
		Query queryCount = this.createQuery(createCountHql(hql));
		DaoUtils.applyParametersToQuery(queryCount, parameters);
		Long count = (Long) queryCount.uniqueResult();
		pagination.setRecordCount(count.intValue());	
		
		Query queryResult = this.createQuery(hql);
		DaoUtils.applyParametersToQuery(queryResult, parameters);
		queryResult.setFirstResult(pagination.getFirst()-1);
		queryResult.setMaxResults(pagination.getPageSize());
	   	pagination.setResult(queryResult.list());
		return pagination;
	}

	@Override
	public List<T> queryByProperty(String property, Object value) {
		String hql = "from " + entityClass.getName() + " where "+property+" = ?";
		return this.query(hql, value);
	}

	@Override
	public Pagination<T> queryForPage(String hql, int pageNo, int pageSize,
			Map<String, Object> parameters) {
		// 查询总记录数
		Query queryCount = this.createQuery(createCountHql(hql));
		DaoUtils.applyParametersToQuery(queryCount, parameters);
		int recordCount = ((Long) queryCount.uniqueResult()).intValue();

		// 查询指定分页记录
		Query queryResult = this.createQuery(hql);
		DaoUtils.applyParametersToQuery(queryResult, parameters);
		return getResultPage(queryResult, pageNo, pageSize, recordCount);
	}

	@Override
	public Pagination<T> queryForPage(String hql, int pageNo, int pageSize, 
			Object... parameters) {
		// 查询总记录数
		Query queryCount = this.createQuery(createCountHql(hql));
		DaoUtils.applyParametersToQuery(queryCount, parameters);
		int recordCount = ((Long) queryCount.uniqueResult()).intValue();

		// 查询指定分页记录
		Query queryResult = this.createQuery(hql);
		DaoUtils.applyParametersToQuery(queryResult, parameters);
		return getResultPage(queryResult, pageNo, pageSize, recordCount);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pagination<T> queryForPage(Pagination<T> pagination,
			DetachedCriteria detachedCriteria) {
		Criteria criteria = detachedCriteria.getExecutableCriteria(this.getCurrentSession());  
        
        ProjectionList projectionList = Projections.projectionList(); 
        criteria.setProjection(projectionList);
        Long totalCount =(Long)criteria.setProjection(Projections.rowCount()).uniqueResult(); 
        pagination.setRecordCount(totalCount.intValue());
        
        criteria.setProjection(null);   
        detachedCriteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        List<T> items = criteria.setFirstResult(pagination.getFirst()-1).setMaxResults(pagination.getPageSize()).list();   
        pagination.setResult(items);
        return pagination;
	}
	
	private Query createQuery(String hql){
		return this.getCurrentSession().createQuery(hql);
	}
	
//	private Query createSQLQuery(String sql){
//		return this.getCurrentSession().createSQLQuery(sql);
//	}
	
	private void executeUpdateHql(String hql, Map<String, Object> parameters){
		Query query = this.createQuery(hql);		
		DaoUtils.applyParametersToQuery(query, parameters);		
		query.executeUpdate();
	}
	private void executeUpdateHql(String hql, Object... parameters){
		Query query = this.createQuery(hql);		
		DaoUtils.applyParametersToQuery(query, parameters);		
		query.executeUpdate();
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
			return new Pagination<T>(new ArrayList<T>(), pageSize, pageNo, recordCount, 0);
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

}
