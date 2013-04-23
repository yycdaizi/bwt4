package org.bjdrgs.bjwt.authority.service;

import org.bjdrgs.bjwt.authority.model.User;
import org.bjdrgs.bjwt.authority.parameter.NewPassword;
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
	 * @param password -- 加密后的密码
	 * @return
	 */
	public User findUserByUP(String username,String password);
	/**
	 * 根据用户名称查找用户
	 * @param username
	 * @return
	 */
	public User findUserByName(String username);
	/**
	 * 校验旧密码是否正确
	 * @param userInfo
	 * @param param
	 */
	public boolean checkPassword(User userInfo, NewPassword param);
	
	/**
	 * 判断某用户是否拥有某角色
	 * @param user
	 * @param rolecode
	 * @return
	 */
	boolean hasRole(User user, String rolecode);
}
