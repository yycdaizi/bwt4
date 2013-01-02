package org.bjdrgs.bjwt.core.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.bjdrgs.bjwt.core.web.GridPage;
import org.bjdrgs.bjwt.core.web.Pagination;
import org.hibernate.criterion.DetachedCriteria;

/**
 * 基础service接口
 * @author ying
 *
 * @param <T>
 */
public interface IBaseService<T> {
	
	/**
	 * 手工注入Class
	 * @param entityClass
	 * @see org.bjdrgs.bjwt.core.dao.IBaseDao#setEntityClass(Class)
	 */
	public abstract void setEntityClass(Class<T> entityClass);
	
	/**
	 * 强制提交刷新session
	 * @see org.bjdrgs.bjwt.core.dao.IBaseDao#flush()
	 */
	public abstract void flush();
	
	/**
	 * 强制清除session
	 * @see org.bjdrgs.bjwt.core.dao.IBaseDao#clear()
	 */
	public abstract void clear();
	
	/**
	 * 将对象实例从session缓存清除
	 * @param entity
	 * @see org.bjdrgs.bjwt.core.dao.IBaseDao#evict(Object)
	 */
	public abstract void evict(T entity);
	
	/**
	 * merge对象
	 * @param entity
	 * @see org.bjdrgs.bjwt.core.dao.IBaseDao#merge(Object)
	 */
	public abstract void merge(T entity);
	
	/**
	 * 保存或更新数据对象
	 * @param entity
	 * @see org.bjdrgs.bjwt.core.dao.IBaseDao#save(Object)
	 */
	public abstract void save(T entity);
	
	/**
	 * HQL方式更新数据
	 * @param hql
	 * @param parameters
	 * @see org.bjdrgs.bjwt.core.dao.IBaseDao#update(String, Object...)
	 */
	public abstract void update(String hql, Object... parameters);
	
	/**
	 * 保存或更新数据对象集合
	 * @param entities
	 * @see org.bjdrgs.bjwt.core.dao.IBaseDao#save(Collection)
	 */
	public abstract void save(Collection<T> entities);
	
	/**
	 * 根据Unique设置属性值
	 * @param unique
	 * @param property
	 * @param value
	 * @see org.bjdrgs.bjwt.core.dao.IBaseDao#updatePropertyByUnique(String, String, Object)
	 */
	public abstract void updatePropertyByUnique(String unique, String property, Object value);
	
	/**
	 * 删除数据对象
	 * @param entity
	 * @see org.bjdrgs.bjwt.core.dao.IBaseDao#delete(Object)
	 */
	public abstract void delete(T entity);
	
	/**
	 * HQL方式删除数据
	 * @param hql
	 * @param parameters
	 * @see org.bjdrgs.bjwt.core.dao.IBaseDao#delete(String, Object...)
	 */
	public abstract void delete(String hql, Object... parameters);
	
	/**
	 * 根据ID删除数据对象
	 * @param id
	 * @see org.bjdrgs.bjwt.core.dao.IBaseDao#deleteById(Serializable)
	 */
	public abstract void deleteById(Serializable id);
	
	/**
	 * 根据Unique删除数据对象
	 * @param unique
	 * @param value
	 * @see org.bjdrgs.bjwt.core.dao.IBaseDao#deleteByUnique(String, Object)
	 */
	public abstract void deleteByUnique(String unique, Object value);
	
	/**
	 * 删除数据对象集合
	 * @param entities
	 * @see org.bjdrgs.bjwt.core.dao.IBaseDao#delete(Collection)
	 */
	public abstract void delete(Collection<T> entities);
	
	/**
	 * 根据ID获得数据对象
	 * @param id
	 * @return
	 * @see org.bjdrgs.bjwt.core.dao.IBaseDao#findById(Serializable)
	 */
	public abstract T findById(Serializable id);
	
	
	/**
	 * 根据ID获得数据对象(直接使用get方法)
	 * @param id
	 * @return
	 * @see org.bjdrgs.bjwt.core.dao.IBaseDao#getById(Serializable)
	 */
	public abstract T getById(Serializable id);
	
	
	/**
	 * 根据Unique Property获得数据对象
	 * @param unique
	 * @param value
	 * @return
	 * @see org.bjdrgs.bjwt.core.dao.IBaseDao#findByUnique(String, Object)
	 */
	public abstract T findByUnique(String unique, Object value);
	
	/**
	 * 根据Property获得数据
	 * @param properties
	 * @param values
	 * @return
	 * @see org.bjdrgs.bjwt.core.dao.IBaseDao#findByProperties(String[], Object[])
	 */
	public abstract List<T> findByProperties(String[] properties, Object[] values);
	
	/**
	 * HQL方式查询数据
	 * @param hql
	 * @param parameters
	 * @return
	 * @see org.bjdrgs.bjwt.core.dao.IBaseDao#query(String, Object...)
	 */
	public abstract List<T> query(String hql, Object... parameters);
	
	/**
	 * DetachedCriteria方式查询数据
	 * @param detachedCriteria
	 * @return
	 * @see org.bjdrgs.bjwt.core.dao.IBaseDao#query(DetachedCriteria)
	 */
	public abstract List<T> query(final DetachedCriteria detachedCriteria);
	
	/**
	 * HQL方式分页查询数据
	 * @param hql
	 * @param pagination
	 * @return
	 * @see org.bjdrgs.bjwt.core.dao.IBaseDao#findPageByHql(Pagination, String, Object...)
	 */
	public abstract GridPage<T> findPageByHql(Pagination<T> pagination,String hql,Object... parameters);
	
	/**
	 * HQL方式分页查询数据
	 * @param hql
	 * @param parameters
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @see org.bjdrgs.bjwt.core.dao.IBaseDao#findPageByHql(int, int, String, Object...)
	 */
	public abstract GridPage<T> findPageByHql(final int pageNo, final int pageSize, final String hql, final Object... parameters);
	
	/**
	 * DetachedCriteria方式分页查询数据
	 * @param detachedCriteria
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @see org.bjdrgs.bjwt.core.dao.IBaseDao#findPageByDetachedCriteria(DetachedCriteria, Pagination)
	 */
	public abstract GridPage<T> findPageByDetachedCriteria(final DetachedCriteria detachedCriteria, final Pagination<T> pagination);
	
	/**
	 * 参数列表值操作方式
	 * 对in操作有帮助
	 * @param hql
	 * @param name 参数key值
	 * @param parameterList 参数列表值
	 * @see org.bjdrgs.bjwt.core.dao.IBaseDao#deleteByParamList(String, String, Object[])
	 */
	public abstract void deleteByParamList(String hql, String name, Object[] parameterList);
	
}
