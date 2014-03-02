package org.bjdrgs.bjwt.authority.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.bjdrgs.bjwt.authority.dao.IAuthorityDao;
import org.bjdrgs.bjwt.authority.model.Menu;
import org.bjdrgs.bjwt.authority.model.MenuTree;
import org.bjdrgs.bjwt.authority.model.User;
import org.bjdrgs.bjwt.authority.service.IAuthorityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("AuthorityService")
public class AuthorityServiceImpl implements IAuthorityService{
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "AuthorityDao")
	private IAuthorityDao authDao;
	
	@Override
	public List<MenuTree> getAuthedMenuTree(User user) {
		Integer userid = user.getUserid();
		Collection<Menu> menus = authDao.getAuthedMenu(userid);
		return buildMenuTree(menus);
	}
	/**
	 * 构造树
	 * @param menus
	 */
	private List<MenuTree> buildMenuTree(Collection<Menu> menus){
		if(menus == null || menus.size()==0) return null;
		List<MenuTree> rootsMenu = new ArrayList<MenuTree>();
		Map<Integer,MenuTree> helpMap = new HashMap<Integer, MenuTree>();
		for(Iterator<Menu> it = menus.iterator(); it.hasNext();){
			Menu menu = it.next();
			if(menu.getParentid()==null || menu.getParentid()==0){
				MenuTree menuTreeItem = menuToTreeItem(menu);
				rootsMenu.add(menuTreeItem);
				helpMap.put(menu.getMenuid(), menuTreeItem);
				it.remove();
			}
		}
		while(menus.size()>0){
			for(Iterator<Menu> it = menus.iterator(); it.hasNext();){
				Menu menu = it.next();
				MenuTree parent = helpMap.get(menu.getParentid());
				if(parent != null){
					MenuTree treeItem = menuToTreeItem(menu);
					parent.addChildren(treeItem);
					helpMap.put(menu.getMenuid(), treeItem);
					it.remove();
				}
			}
		}
		return rootsMenu;
	}
	
	private MenuTree menuToTreeItem(Menu menu){
		MenuTree treeItem = new MenuTree();
		treeItem.setId(menu.getMenuid());
		treeItem.setChecked(false);
		treeItem.setText(menu.getMenuname());
		Map<String, String> attrs = new HashMap<String, String>();
		attrs.put(MenuTree.PROP_URL, menu.getMenuurl());
		treeItem.setAttributes(attrs);
		return treeItem;
	}
	
	@Override
	public List<String> getPermissionsByUser(User user) {
		Collection<Menu> menuList = authDao.getAuthedMenu(user.getUserid());
		List<String> permissionList = new ArrayList<String>();
		for(Menu menu : menuList){
			permissionList.add(menu.getMenuid().toString());
		}
		return permissionList;
	}
}
