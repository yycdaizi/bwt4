package org.bjdrgs.bjwt.core.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.bjdrgs.bjwt.core.web.Pagination;
import org.hibernate.criterion.DetachedCriteria;

/**
 * 基础DAO接口
 * @author ying
 * @date 2012-12-12
 *
 * @param <T>
 * 
 */
public interface IBaseDao<T> {
	
	// 默认每页记录数
	public static final int PAGE_SIZE = 20;
	
	/**
	 * 手工注入Class
	 * @param entityClass
	 */
	public abstract void setEntityClass(Class<T> entityClass);
	
	/**
	 * 强制提交刷新session
	 */
	public abstract void flush();
	
	/**
	 * 强制清除session
	 */
	public abstract void clear();
	
	/**
	 * 将对象实例从session缓存清除
	 * @param entity
	 */
	public abstract void evict(T entity);
	
	/**
	 * merge对象
	 * @param entity
	 */
	public abstract void merge(T entity);
	
	/**
	 * 保存或更新数据对象
	 * @param entity
	 */
	public abstract void save(T entity);
	
	/**
	 * HQL方式更新数据
	 * @param hql
	 * @param parameters 可以是0个或多个参数、数组、Map<br>
	 * 		如果传入的第一个参数是Map,则会使用该参数按key:value的方式设置hql中的值
	 * @usage
	 * <pre>
	 *  String hql = "update User u set u.name = ?,u.age = ? where u.id = ?";
	 *  String name = "test";
	 *  Integer age = 18;
	 *  Long id = 1; 
	 *  //使用方法1
	 *  update(hql,name,age,id);
	 *  //使用方法2
	 *  Object[] parameters = new Object[3];
	 *  parameters[0] = name;
	 *  parameters[1] = age;
	 *  parameters[2] = id;
	 *  update(hql,parameters);
	 *  
	 *  //使用方法3
	 * String hql = "update User u set u.name = :name,u.age = :age where u.id = :id";
	 * Map<String, Object>  paratmers = new HashMap<String, Object>();
	 * String name = "test";
	 * Integer age = 18;
	 * Long id = 1;
	 * paratmers.put("name", name);
	 * paratmers.put("age", age);
	 * paratmers.put("id", id);
	 * update(hql, parameters);
	 * </pre>
	 */
	public abstract void update(String hql, Object... parameters);
	
	/**
	 * 保存或更新数据对象集合
	 * @param entities
	 */
	public abstract void save(Collection<T> entities);
	
	/**
	 * 根据Unique设置属性值
	 * @param unique
	 * @param property
	 * @param value
	 */
	public abstract void updatePropertyByUnique(String unique, String property, Object value);
	
	/**
	 * 删除数据对象
	 * @param entity
	 */
	public abstract void delete(T entity);
	
	/**
	 * HQL方式删除数据
	 * @param hql
	 * @param parameters 可以是0个或多个参数、数组、Map<br>
	 * 		如果传入的第一个参数是Map,则会使用该参数按key:value的方式设置hql中的值
	 * @usage
	 * <pre>
	 *  String hql = "delete from User u where u.id = ? or u.name = ?"; 
	 *  Long id = 1;
	 *  String name = "test";
	 *  //使用方法1
	 *  delete(hql,id,name);
	 *  //使用方法2
	 *  Object[] parameters = new Object[2]; 
	 *  parameters[0] = id;
	 *  parameters[1] = name;
	 *  delete(hql,parameters);
	 *  
	 *  //使用方法3
	 * String hql = "delete from User u where u.id = :id or u.name = :name";
	 * Map<String, Object>  paratmers = new HashMap<String, Object>();
	 * Long id = 1;
	 * String name = "test";
	 * paratmers.put("id", id);
	 * paratmers.put("name", name);
	 * delete(hql, parameters);
	 * </pre>
	 */
	public abstract void delete(String hql, Object... parameters);
	
	/**
	 * 根据ID删除数据对象
	 * @param id
	 */
	public abstract void deleteById(Serializable id);
	
	/**
	 * 根据Unique删除数据对象
	 * @param unique
	 * @param value
	 */
	public abstract void deleteByUnique(String unique, Object value);
	
	/**
	 * 删除数据对象集合
	 * @param entities
	 */
	public abstract void delete(Collection<T> entities);
	
	/**
	 * 根据ID获得数据对象
	 * @param id
	 * @return
	 */
	public abstract T findById(Serializable id);	
	
	/**
	 * 根据ID获得数据对象(直接使用get方法)
	 * @param id
	 * @return
	 */
	public abstract T getById(Serializable id);
	
	/**
	 * 根据Unique Property获得数据对象
	 * @param unique
	 * @param value
	 * @return
	 */
	public abstract T findByUnique(String unique, Object value);
	
	/**
	 * 根据Property获得数据
	 * @param properties
	 * @param values
	 * @return
	 */
	public abstract List<T> findByProperties(String[] properties, Object[] values);
	
	/**
	 * HQL方式查询数据
	 * @param hql
	 * @param parameters 可以是0个或多个参数、数组、Map<br>
	 * 		如果传入的第一个参数是Map,则会使用该参数按key:value的方式设置hql中的值
	 * @return
	 */
	public abstract List<T> query(String hql, Object... parameters);
	
	/**
	 * DetachedCriteria方式查询数据
	 * @param detachedCriteria
	 * @return
	 */
	public abstract List<T> query(final DetachedCriteria detachedCriteria);
	
	/**
	 * HQL方式分页查询数据
	 * @param pagination
	 * @param hql
	 * @param parameters 可以是0个或多个参数、数组、Map<br>
	 * 		如果传入的第一个参数是Map,则会使用该参数按key:value的方式设置hql中的值
	 * @return
	 */
	public abstract Pagination<T> findPageByHql(Pagination<T> pagination,String hql,Object... parameters);
	
	/**
	 * HQL方式分页查询数据
	 * @param pageNo
	 * @param pageSize
	 * @param hql
	 * @param parameters 可以是0个或多个参数、数组、Map<br>
	 * 		如果传入的第一个参数是Map,则会使用该参数按key:value的方式设置hql中的值
	 * @return
	 */
	public abstract Pagination<T> findPageByHql(final int pageNo, final int pageSize,
			final String hql, final Object... parameters);
	
	/**
	 * DetachedCriteria方式分页查询数据
	 * @param detachedCriteria
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public abstract Pagination<T> findPageByDetachedCriteria(final DetachedCriteria detachedCriteria,
			Pagination<T> pagination);
	
	/**
	 * 参数列表值操作方式
	 * 对in操作有帮助
	 * @param hql67
	 * @param name 参数key值
	 * @param parameterList 参数列表值
	 * @usge
	 * <pre>
	 * Long[] ids = {1, 2, 3};
	 * String hql = "delete from demo where id in (:id)";
	 * String name = "id";
	 * deleteByParamList(hql, name, ids);
	 * </pre>
	 */
	public abstract void deleteByParamList(String hql, String name, Object[] parameterList);

}
