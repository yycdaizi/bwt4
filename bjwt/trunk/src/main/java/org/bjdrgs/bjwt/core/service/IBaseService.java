package org.bjdrgs.bjwt.core.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.bjdrgs.bjwt.core.web.Pagination;
import org.hibernate.criterion.DetachedCriteria;

public interface IBaseService<T> {
	
	/**
	 * 手工注入Class
	 * @param entityClass
	 * @see cn.mediashare.framework.core.dao.IDAO#setEntityClass(Class)
	 */
	public abstract void setEntityClass(Class<T> entityClass);
	
	/**
	 * 强制提交刷新session
	 * @see cn.mediashare.framework.core.dao.IDAO#flush()
	 */
	public abstract void flush();
	
	/**
	 * 强制清除session
	 * @see cn.mediashare.framework.core.dao.IDAO#clear()
	 */
	public abstract void clear();
	
	/**
	 * 将对象实例从session缓存清除
	 * @param entity
	 * @see cn.mediashare.framework.core.dao.IDAO#evict(Object)
	 */
	public abstract void evict(T entity);
	
	/**
	 * merge对象
	 * @param entity
	 * @see cn.mediashare.framework.core.dao.IDAO#merge(Object)
	 */
	public abstract void merge(T entity);
	
	/**
	 * 保存或更新数据对象
	 * @param entity
	 * @see cn.mediashare.framework.core.dao.IDAO#save(Object)
	 */
	public abstract void save(T entity);
	
	/**
	 * HQL方式更新数据
	 * @param hql
	 * @param parameters
	 * @see cn.mediashare.framework.core.dao.IDAO#update(String, Object[])
	 */
	public abstract void update(String hql, Object[] parameters);
	
	/**
	 * HQL方式更新数据
	 * @param hql
	 * @param parameters
	 * @see cn.mediashare.framework.core.dao.IDAO#update(String, Map)
	 */
	public abstract void update(String hql, Map<String, Object> parameters);
	
	/**
	 * 保存或更新数据对象集合
	 * @param entities
	 * @see cn.mediashare.framework.core.dao.IDAO#save(Collection)
	 */
	public abstract void save(Collection<T> entities);
	
	/**
	 * 根据Unique设置属性值
	 * @param unique
	 * @param property
	 * @param value
	 * @see cn.mediashare.framework.core.dao.IDAO#updatePropertyByUnique(String, String, Object)
	 */
	public abstract void updatePropertyByUnique(String unique, String property, Object value);
	
	/**
	 * 删除数据对象
	 * @param entity
	 * @see cn.mediashare.framework.core.dao.IDAO#delete(Object)
	 */
	public abstract void delete(T entity);
	
	/**
	 * HQL方式删除数据
	 * @param hql
	 * @param parameters
	 * @see cn.mediashare.framework.core.dao.IDAO#delete(String, Object...)
	 */
	public abstract void delete(String hql, Object... parameters);
	
	/**
	 * HQL方式删除数据
	 * @param hql
	 * @param parameters
	 * @see cn.mediashare.framework.core.dao.IDAO#delete(String, Map) 
	 */
	public abstract void delete(String hql, Map<String, Object> parameters);
	
	/**
	 * 根据ID删除数据对象
	 * @param id
	 * @see cn.mediashare.framework.core.dao.IDAO#deleteById(Serializable)
	 */
	public abstract void deleteById(Serializable id);
	
	/**
	 * 根据Unique删除数据对象
	 * @param unique
	 * @param value
	 * @see cn.mediashare.framework.core.dao.IDAO#deleteByUnique(String, Object)
	 */
	public abstract void deleteByUnique(String unique, Object value);
	
	/**
	 * 删除数据对象集合
	 * @param entities
	 * @see cn.mediashare.framework.core.dao.IDAO#delete(Collection)
	 */
	public abstract void delete(Collection<T> entities);
	
	/**
	 * 根据ID获得数据对象
	 * @param id
	 * @return
	 * @see cn.mediashare.framework.core.dao.IDAO#findById(Serializable)
	 */
	public abstract T findById(Serializable id);
	
	
	/**
	 * 根据ID获得数据对象(直接使用get方法)
	 * @param id
	 * @return
	 * @see cn.mediashare.framework.core.dao.IDAO#getById(Serializable)
	 */
	public abstract T getById(Serializable id);
	
	
	/**
	 * 根据Unique Property获得数据对象
	 * @param unique
	 * @param value
	 * @return
	 * @see cn.mediashare.framework.core.dao.IDAO#findByUnique(String, Object)
	 */
	public abstract T findByUnique(String unique, Object value);
	
	/**
	 * 根据Property获得数据
	 * @param properties
	 * @param values
	 * @return
	 * @see cn.mediashare.framework.core.dao.IDAO#findByProperties(String[], Object[])
	 */
	public abstract List<T> findByProperties(String[] properties, Object[] values);
	
	/**
	 * HQL方式查询数据
	 * @param hql
	 * @param parameters
	 * @return
	 * @see cn.mediashare.framework.core.dao.IDAO#query(String, Object[])
	 */
	public abstract List<T> query(String hql, Object... parameters);
	
	/**
	 * HQL方式查询数据
	 * @param hql
	 * @param parameters
	 * @return
	 * @see cn.mediashare.framework.core.dao.IDAO#query(String, Map)
	 */
	public abstract List<T> query(String hql, Map<String, Object> parameters);
	
	/**
	 * DetachedCriteria方式查询数据
	 * @param detachedCriteria
	 * @return
	 * @see cn.mediashare.framework.core.dao.IDAO#query(DetachedCriteria)
	 */
	public abstract List<T> query(final DetachedCriteria detachedCriteria);
	
	/**
	 * HQL方式分页查询数据
	 * @param hql
	 * @param pagination
	 * @return
	 * @see cn.mediashare.framework.core.dao.IDAO#findPageByHql(String, Pagination)
	 */
	public abstract Pagination<T> findPageByHql(String hql, Pagination<T> pagination);
	
	/**
	 * HQL方式分页查询数据
	 * @param hql
	 * @param parameters
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @see cn.mediashare.framework.core.dao.IDAO#findPageByHql(String, Object[], int, int)
	 */
	public abstract Pagination<T> findPageByHql(final int pageNo, final int pageSize, final String hql, final Object... parameters);
	
	/**
	 * HQL方式分页查询数据
	 * @param hql
	 * @param parameters
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @see cn.mediashare.framework.core.dao.IDAO#findPageByHql(String, Map, int, int)
	 */
	public abstract Pagination<T> findPageByHql(final String hql, final Map<String, Object> parameters, final int pageNo, final int pageSize);
	
	/**
	 * DetachedCriteria方式分页查询数据
	 * @param detachedCriteria
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @see cn.mediashare.framework.core.dao.IDAO#findPageByDetachedCriteria(DetachedCriteria, int, int)
	 */
	public abstract Pagination<T> findPageByDetachedCriteria(final DetachedCriteria detachedCriteria, final Pagination<T> pagination);
	
	/**
	 * 参数列表值操作方式
	 * 对in操作有帮助
	 * @param hql67
	 * @param name 参数key值
	 * @param parameterList 参数列表值
	 * @see cn.mediashare.framework.core.dao.IDAO#deleteByParamList(String, String, Object[])
	 */
	public abstract void deleteByParamList(String hql, String name, Object[] parameterList);
	
}
