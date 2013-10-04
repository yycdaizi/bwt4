package org.bjdrgs.bjwt.authority.dao;

import java.util.List;

import org.bjdrgs.bjwt.authority.model.Role;
import org.bjdrgs.bjwt.authority.model.User;
import org.bjdrgs.bjwt.authority.parameter.RoleParam;
import org.bjdrgs.bjwt.core.dao.IBaseDao;
import org.bjdrgs.bjwt.core.web.Pagination;

public interface IRoleDao extends IBaseDao<Role> {
	/**
	 * 分页查询
	 * @param param
	 * @return 分页对象
	 */
	public Pagination<Role> query(RoleParam param);

	/**
	 * 根据用户获取角色
	 * @param user
	 * @return
	 */
	public List<Role> getRoleByUser(User user);

}
