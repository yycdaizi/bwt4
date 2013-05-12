package org.bjdrgs.bjwt.authority.service;

import org.bjdrgs.bjwt.authority.model.Menu;
import org.bjdrgs.bjwt.authority.parameter.MenuParam;
import org.bjdrgs.bjwt.core.web.Pagination;

public interface IMenuService {
	/**
	 * 分页查询
	 * @param param
	 * @return 分页查询结果
	 */
	public Pagination<Menu> queryMenu(MenuParam param);
	/**
	 * 保存（新增或修改）
	 * @param entity
	 */
	public void saveMenu(Menu entity);
	
	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(Integer id);
}
