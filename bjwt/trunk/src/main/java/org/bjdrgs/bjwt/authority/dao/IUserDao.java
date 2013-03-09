package org.bjdrgs.bjwt.authority.dao;

import java.util.List;

import org.bjdrgs.bjwt.authority.model.User;
import org.bjdrgs.bjwt.authority.parameter.UserParam;
import org.bjdrgs.bjwt.core.dao.IBaseDao;
import org.bjdrgs.bjwt.core.web.Pagination;

public interface IUserDao extends IBaseDao<User> {
	/**
	 * 分页查询
	 * @param param
	 * @return 分页对象
	 */
	public Pagination<User> query(UserParam param);
	
	/**
	 * 根据用户名和密码查找用户
	 * @param username
	 * @param password
	 * @return
	 */
	public List<User> findUserByUP(String username,String password);

}
