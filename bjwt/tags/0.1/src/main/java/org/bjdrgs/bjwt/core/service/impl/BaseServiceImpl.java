package org.bjdrgs.bjwt.core.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.bjdrgs.bjwt.core.dao.IBaseDao;
import org.bjdrgs.bjwt.core.service.IBaseService;
import org.bjdrgs.bjwt.core.util.ReflectionUtils;
import org.bjdrgs.bjwt.core.web.GridPage;
import org.bjdrgs.bjwt.core.web.Pagination;
import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基础Service类
 * @author ying
 * @date 2012-12-14
 *
 */
public class BaseServiceImpl<T extends Serializable> implements IBaseService<T> {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private Class<T> entityClass;

	private IBaseDao<T> dao;

	public IBaseDao<T> getDao() {
		return dao;
	}
	
	@Resource(name="baseDao")
	public void setDao(IBaseDao<T> dao) {
		this.dao = dao;
		dao.setEntityClass(entityClass);
	}
	
	/**
	 * 构造器
	 */
	public BaseServiceImpl() {
		entityClass = ReflectionUtils.getSuperClassGenricType(this.getClass());
	}
	
	@Override
	public void setEntityClass(Class<T> entityClass) {
		if(dao!=null){
			dao.setEntityClass(entityClass);
		}
		this.entityClass = entityClass;
	}
	
	@Override
	public void flush() {
		dao.flush();
	}
	
	@Override
	public void clear() {
		dao.clear();
	}
	
	@Override
	public void evict(T entity) {
		dao.evict(entity);
	}
	
	@Override
	public void merge(T entity) {
		dao.merge(entity);
	}
	
	@Override
	public void save(T entity) {
		dao.save(entity);
	}
	
	@Override
	public void update(String hql, Object... parameters) {
		dao.update(hql, parameters);
	}
	
	@Override
	public void save(Collection<T> entities) {
		dao.save(entities);
	}
	
	@Override
	public void updatePropertyByUnique(String unique, String property, Object value) {
		dao.updatePropertyByUnique(unique, property, value);
	}
	
	@Override
	public void delete(T entity) {
		dao.delete(entity);
	}
	
	@Override
	public void delete(String hql, Object... parameters) {
		dao.delete(hql, parameters);
	}
	
	@Override
	public void deleteById(Serializable id) {
		dao.deleteById(id);
	}
	
	@Override
	public void deleteByUnique(String unique, Object value) {
		dao.deleteByUnique(unique, value);
	}
	
	@Override
	public void delete(Collection<T> entities) {
		dao.delete(entities);
	}
	
	@Override
	public T findById(Serializable id) {
		return dao.findById(id);
	}
	
	@Override
	public T getById(Serializable id) {
		return dao.getById(id);
	}	
	
	@Override
	public T findByUnique(String unique, Object value) {
		return dao.findByUnique(unique, value);
	}
	
	@Override
	public List<T> findByProperties(String[] properties, Object[] values) {
		return dao.findByProperties(properties, values);
	}
	
	@Override
	public List<T> query(String hql, Object... parameters) {
		return dao.query(hql, parameters);
	}
	
	@Override
	public List<T> query(final DetachedCriteria detachedCriteria) {
		return dao.query(detachedCriteria);
	}
	
	@Override
	public GridPage<T> findPageByHql(Pagination<T> pagination, String hql, Object... parameters) {
		return new GridPage<T>(dao.findPageByHql(pagination, hql, parameters));
	}
	
	@Override
	public GridPage<T> findPageByHql(final int pageNo, final int pageSize,
			final String hql, final Object... parameters) {
		return new GridPage<T>(dao.findPageByHql(pageNo, pageSize, hql, parameters));
	}
	
	@Override
	public GridPage<T> findPageByDetachedCriteria(final DetachedCriteria detachedCriteria,
			final Pagination<T> pagination) {
		return new GridPage<T>(dao.findPageByDetachedCriteria(detachedCriteria, pagination));
	}
	
	@Override
	public void deleteByParamList(String hql, String name, Object[] parameterList) {
		dao.deleteByParamList(hql, name, parameterList);
	}
	
}
