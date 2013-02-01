package org.bjdrgs.bjwt.authority.service.impl;

import javax.annotation.Resource;

import org.bjdrgs.bjwt.authority.dao.IMenuDao;
import org.bjdrgs.bjwt.authority.model.Menu;
import org.bjdrgs.bjwt.authority.parameter.MenuParam;
import org.bjdrgs.bjwt.authority.service.IMenuService;
import org.bjdrgs.bjwt.core.web.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("menuService")
public class MenuServiceImpl implements IMenuService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name = "menuDao")
	private IMenuDao menuDao;

	@Override
	public Pagination<Menu> queryMenu(MenuParam param) {
		return menuDao.query(param);
	}

	@Override
	public void saveMenu(Menu entity) {
		menuDao.save(entity);
	}

}
