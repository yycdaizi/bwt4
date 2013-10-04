package org.bjdrgs.bjwt.core.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.bjdrgs.bjwt.core.web.Pagination;
import org.hibernate.criterion.DetachedCriteria;

/**
 * 基础DAO接口
 * @param <T>
 * 
 * @author ying
 * @date 2013-01-20
 */
public interface IBaseDao<T extends Serializable> {

	// 默认每页记录数
	public static final int PAGE_SIZE = 20;

	/**
	 * 手工注入Class
	 * @param entityClass
	 */
	void setEntityClass(Class<T> entityClass);
	
	/**
	 * merge对象
	 * @param entity
	 */
	void merge(T entity);

	/**
	 * 保存或更新数据对象
	 * @param entity
	 */
	void save(T entity);

	/**
	 * 保存或更新数据对象集合
	 * @param entities
	 */
	void save(Collection<T> entities);

	/**
	 * HQL方式更新数据
	 * @param hql
	 * @param parameters
	 * @usage
	 * <pre>
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
	void update(String hql, Map<String, Object> parameters);
	
	/**
	 * HQL方式更新数据
	 * @param hql
	 * @param parameters 可以是：
	 * <ul>
	 * <li>0个或多个参数</li>
	 * <li>数组</li>
	 * </ul>
	 * @usage
	 * <pre>
	 *  String hql = "update User u set u.name = ?,u.age = ? where u.id = ?"; 
	 *  Object[] parameters = new Object[3];
	 *  String name = "test";
	 *  Integer age = 18;
	 *  Long id = 1; 
	 *  // 使用方法1
	 *  update(hql, name, age, id);
	 *  // 使用方法2
	 *  Object[] parameters = new Object[3];
	 *  parameters[0] = name;
	 *  parameters[1] = age;
	 *  parameters[2] = id;
	 *  update(hql, parameters);
	 * </pre>
	 */
	void update(String hql, Object... parameters);

	/**
	 * 删除数据对象
	 * @param entity
	 */
	void delete(T entity);

	/**
	 * 根据ID删除数据对象
	 * @param id
	 */
	void deleteById(Serializable id);

	/**
	 * 删除数据对象集合
	 * @param entities
	 */
	void delete(Collection<T> entities);

	/**
	 * HQL方式删除数据
	 * @param hql
	 * @param parameters
	 * @usage
	 * <pre>
	 * String hql = "delete from User u where u.id = :id or u.name = :name";
	 * Map<String, Object>  paratmers = new HashMap<String, Object>();
	 * Long id = 1;
	 * String name = "test";
	 * paratmers.put("id", id);
	 * paratmers.put("name", name);
	 * delete(hql, parameters);
	 * </pre> 
	 */
	void delete(String hql, Map<String, Object> parameters);
	/**
	 * HQL方式删除数据
	 * @param hql
	 * @param parameters 可以是：
	 * <ul>
	 * <li>0个或多个参数</li>
	 * <li>数组</li>
	 * </ul>
	 * @usage <pre>
	 * String hql = &quot;delete from User u where u.id = ? or u.name = ?&quot;;
	 * Long id = 1;
	 * String name = &quot;test&quot;;
	 * // 使用方法1
	 * delete(hql, id, name);
	 * // 使用方法2
	 * Object[] parameters = new Object[2];
	 * parameters[0] = id;
	 * parameters[1] = name;
	 * delete(hql, parameters);
	 * </pre>
	 */
	void delete(String hql, Object... parameters);

	/**
	 * 根据某一property删除数据对象。<br>
	 * 等同于执行hql: delete from EntityName where [property] = [value]
	 * @param property 属性名
	 * @param value 属性值
	 */
	void deleteByProperty(String property, Object value);

	/**
	 * 根据ID获得数据对象(使用get方法)
	 * @param id
	 * @return
	 */
	T get(Serializable id);

	/**
	 * 根据ID获得数据对象(使用load方法)
	 * @param id
	 * @return
	 */
	T load(Serializable id);

	/**
	 * 根据Unique Property获得数据对象
	 * @param unique 属性名，该属性有唯一性约束
	 * @param value 属性值
	 * @return
	 */
	T getByUnique(String unique, Object value);

	/**
	 * HQL方式查询数据
	 * @param hql
	 * @param parameters
	 * @return
	 */
	List<T> query(String hql, Map<String, Object> parameters);
	/**
	 * HQL方式查询数据
	 * @param hql
	 * @param parameters 可以是：
	 * <ul>
	 * <li>0个或多个参数</li>
	 * <li>数组</li>
	 * </ul>
	 * @return
	 */
	List<T> query(String hql, Object... parameters);

	/**
	 * DetachedCriteria方式查询数据
	 * @param detachedCriteria
	 * @return
	 */
	List<T> query(final DetachedCriteria detachedCriteria);
	
	/**
	 * 根据某一property的值查询数据对象。<br>
	 * 等同于执行hql: from EntityName where [property] = [value];
	 * @param property 属性名
	 * @param value 属性值
	 * @return
	 */
	List<T> queryByProperty(String property, Object value);

	/**
	 * HQL方式分页查询数据
	 * @param hql
	 * @param pagination
	 * @param parameters
	 * @return
	 */
	Pagination<T> queryForPage(String hql, Pagination<T> pagination, Map<String, Object> parameters);
	/**
	 * HQL方式分页查询数据
	 * @param hql
	 * @param pagination
	 * @param parameters 可以是：
	 * <ul>
	 * <li>0个或多个参数</li>
	 * <li>数组</li>
	 * </ul>
	 * @return
	 */
	Pagination<T> queryForPage(String hql, Pagination<T> pagination, 
			Object... parameters);

	/**
	 * HQL方式分页查询数据
	 * @param hql
	 * @param pageNo 当前页码
	 * @param pageSize 每页记录数
	 * @param parameters
	 * @return
	 */
	Pagination<T> queryForPage(final String hql, final int pageNo, final int pageSize,
			 final Map<String, Object> parameters);
	/**
	 * HQL方式分页查询数据
	 * @param hql
	 * @param pageNo 当前页码
	 * @param pageSize 每页记录数
	 * @param parameters 可以是：
	 * <ul>
	 * <li>0个或多个参数</li>
	 * <li>数组</li>
	 * </ul>
	 * @return
	 */
	Pagination<T> queryForPage(final String hql, final int pageNo, final int pageSize,
			 final Object... parameters);

	/**
	 * DetachedCriteria方式分页查询数据
	 * 
	 * @param detachedCriteria
	 * @param pageNo 当前页码
	 * @param pageSize 每页记录数
	 * @return
	 */
	Pagination<T> queryForPage(Pagination<T> pagination,
			final DetachedCriteria detachedCriteria);

}
