package org.bjdrgs.bjwt.core.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.bjdrgs.bjwt.core.dao.IBaseDao;
import org.bjdrgs.bjwt.core.service.IBaseService;
import org.bjdrgs.bjwt.core.util.ReflectionUtils;
import org.bjdrgs.bjwt.core.web.Pagination;
import org.hibernate.criterion.DetachedCriteria;

/**
 * 基础Service类
 * @author Tim Lu
 * @date 2011-08-14
 * @company MediaShare Ltd.
 *
 */
public class BaseServiceImpl<T extends Serializable> implements IBaseService<T> {
	
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
	public void update(String hql, Object[] parameters) {
		dao.update(hql, parameters);
	}
	
	@Override
	public void update(String hql, Map<String, Object> parameters) {
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
	public void delete(String hql, Map<String, Object> parameters) {
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
	public List<T> query(String hql, Map<String, Object> parameters) {
		return dao.query(hql, parameters);
	}
	
	@Override
	public List<T> query(final DetachedCriteria detachedCriteria) {
		return dao.query(detachedCriteria);
	}
	
	@Override
	public Pagination<T> findPageByHql(String hql, Pagination<T> pagination) {
		return dao.findPageByHql(pagination, hql);
	}
	
	@Override
	public Pagination<T> findPageByHql(final int pageNo, final int pageSize,
			final String hql, final Object... parameters) {
		return dao.findPageByHql(pageNo, pageSize, hql, parameters);
	}
	
	@Override
	public Pagination<T> findPageByHql(final String hql, final Map<String, Object> parameters,
			final int pageNo, final int pageSize) {
		return dao.findPageByHql(pageNo, pageSize,hql, parameters);
	}
	
	@Override
	public Pagination<T> findPageByDetachedCriteria(final DetachedCriteria detachedCriteria,
			final Pagination<T> pagination) {
		return dao.findPageByDetachedCriteria(detachedCriteria, pagination);
	}
	
	@Override
	public void deleteByParamList(String hql, String name, Object[] parameterList) {
		dao.deleteByParamList(hql, name, parameterList);
	}
	
}
