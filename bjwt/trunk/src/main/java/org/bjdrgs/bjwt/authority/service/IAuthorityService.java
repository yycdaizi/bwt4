package org.bjdrgs.bjwt.authority.service;

import java.util.List;

import org.bjdrgs.bjwt.authority.model.MenuTree;
import org.bjdrgs.bjwt.authority.model.User;

public interface IAuthorityService {
	/**
	 * 获取授权的菜单树
	 * @param user
	 * @return
	 */
	List<MenuTree> getAuthedMenuTree(User user);
	
	/**
	 * 获取用户的所有权限（menu id 列表）
	 * @param role
	 * @return
	 */
	List<String> getPermissionsByUser(User user);
}
