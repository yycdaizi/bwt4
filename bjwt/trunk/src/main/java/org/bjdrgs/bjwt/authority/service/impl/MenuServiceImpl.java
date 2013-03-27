package org.bjdrgs.bjwt.authority.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.bjdrgs.bjwt.authority.dao.IMenuDao;
import org.bjdrgs.bjwt.authority.model.Menu;
import org.bjdrgs.bjwt.authority.parameter.MenuParam;
import org.bjdrgs.bjwt.authority.service.IMenuService;
import org.bjdrgs.bjwt.authority.utils.Constants;
import org.bjdrgs.bjwt.core.web.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("MenuService")
public class MenuServiceImpl implements IMenuService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name = "MenuDao")
	private IMenuDao menuDao;

	@Override
	public Pagination<Menu> queryMenu(MenuParam param) {
		//菜单参照
		if(param.getQuery_all()!=null){
			param.setId(null);
			return menuDao.query(param);
		}
		//菜单管理
		if(param.getId()==null){
			param.setId(0);
		}
		Pagination<Menu> menuPag = menuDao.query(param);
		List<Menu> menuList = menuPag.getResult();
		if(menuList!=null && menuList.size()>0){
			for(Menu menu : menuList){
				handleTreeState(menu);
			}
		}
		return menuPag;
	}

	private void handleTreeState(Menu menu) {
		Menu hasSubMenu = menuDao.getByUnique("parentid", menu.getMenuid());
		if(hasSubMenu!=null){
			menu.setState(Constants.EASYUI_TREE_CLOSED);
		}
	}

	@Override
	public void saveMenu(Menu entity) {
		if(entity.getParentid()==null){
			entity.setParentid(0);
		}
		menuDao.save(entity);
	}

	@Override
	public void deleteById(Integer Menuid) {
		menuDao.deleteById(Menuid);
	}

}

