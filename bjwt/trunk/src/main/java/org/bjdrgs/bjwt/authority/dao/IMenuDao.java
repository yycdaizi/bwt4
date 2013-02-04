package org.bjdrgs.bjwt.authority.dao;

import org.bjdrgs.bjwt.authority.model.Menu;
import org.bjdrgs.bjwt.authority.parameter.MenuParam;
import org.bjdrgs.bjwt.core.dao.IBaseDao;
import org.bjdrgs.bjwt.core.web.Pagination;

public interface IMenuDao extends IBaseDao<Menu> {
	/**
	 * 分页查询
	 * @param param
	 * @return 分页
	 */
	public Pagination<Menu> query(MenuParam param);

}
