package org.bjdrgs.bjwt.authority.service;

import java.util.List;

import org.bjdrgs.bjwt.authority.model.Role;
import org.bjdrgs.bjwt.authority.model.User;
import org.bjdrgs.bjwt.authority.parameter.RoleParam;
import org.bjdrgs.bjwt.core.web.Pagination;

public interface IRoleService {
	/**
	 * 分页查询
	 * @param param
	 * @return 分页查询结果
	 */
	public Pagination<Role> queryRole(RoleParam param);
	/**
	 * 保存（新增或修改）
	 * @param entity
	 */
	public void saveRole(Role entity);
	
	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(Integer id);
	
	/**
	 * 获取用户所属角色
	 * @param user
	 */
	public List<Role> getRoleByUser(User user);
	/**
	 * 获取角色的所有权限（menu id 列表）
	 * @param role
	 * @return
	 */
	public List<String> getRolePermissions(Role role);

	
}
