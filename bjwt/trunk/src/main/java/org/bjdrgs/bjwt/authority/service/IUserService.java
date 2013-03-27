package org.bjdrgs.bjwt.authority.service;

import org.bjdrgs.bjwt.authority.model.User;
import org.bjdrgs.bjwt.authority.parameter.UserParam;
import org.bjdrgs.bjwt.core.web.Pagination;

public interface IUserService {
	/**
	 * 分页查询
	 * @param param
	 * @return 分页查询结果
	 */
	public Pagination<User> queryUser(UserParam param);
	/**
	 * 保存（新增或修改）
	 * @param entity
	 */
	public void saveUser(User entity);
	
	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(Integer id);
	
	/**
	 * 根据用户名和密码查找用户
	 * @param username
	 * @param password
	 * @return
	 */
	public User findUserByUP(String username,String password);
	/**
	 * 根据用户名称查找用户
	 * @param username
	 * @return
	 */
	public User findUserByName(String username);
}
