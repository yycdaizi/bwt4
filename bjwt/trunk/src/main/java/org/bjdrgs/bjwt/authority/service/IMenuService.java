package org.bjdrgs.bjwt.authority.service;

import org.bjdrgs.bjwt.authority.model.Menu;
import org.bjdrgs.bjwt.authority.parameter.MenuParam;
import org.bjdrgs.bjwt.core.web.Pagination;

public interface IMenuService {

	public Pagination<Menu> queryMenu(MenuParam param);

	public void saveMenu(Menu entity);
}
