package org.bjdrgs.bjwt.authority.dao;

import java.util.List;

import org.bjdrgs.bjwt.authority.model.Menu;

public interface IAuthorityDao {
	/**
	 * 根据用户ID查询授权的菜单
	 * @param userid
	 * @return
	 */
	public List<Menu> getAuthedMenu(Integer userid);
}
