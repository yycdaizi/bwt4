package org.bjdrgs.bjwt.authority.dao;

import org.bjdrgs.bjwt.authority.model.Menu;
import org.bjdrgs.bjwt.authority.parameter.MenuParam;
import org.bjdrgs.bjwt.core.dao.IBaseDao;
import org.bjdrgs.bjwt.core.web.Pagination;
import org.bjdrgs.bjwt.dicdata.model.DicType;

public interface IMenuDao extends IBaseDao<Menu> {
	public Pagination<Menu> query(MenuParam param);

}
