package org.bjdrgs.bjwt.authority.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
@Service("authorityService")
public class AuthorityServiceImpl implements IAuthorityService{
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "AuthorityDao")
	private IAuthorityDao authDao;
	
	@Override
	public List<MenuTree> getAuthedMenuTree(User user) {
		Integer userid = user.getUserid();
		List<Menu> menuList = authDao.getAuthedMenu(userid);
		return buildMenuTree(menuList);
	}
	/**
	 * 构造树
	 * @param menuList
	 */
	private List<MenuTree> buildMenuTree(List<Menu> menuList){
		if(menuList == null || menuList.size()==0) return null;
		List<MenuTree> rootsMenu = new ArrayList<MenuTree>();
		Map<Integer,MenuTree> helpMap = new HashMap<Integer, MenuTree>();
		for(int i=0;i<menuList.size();i++){
			Menu menu = menuList.get(i);
			if(menu.getParentid()==null || menu.getParentid()==0){
				MenuTree menuTreeItem = menuToTreeItem(menu);
				rootsMenu.add(menuTreeItem);
				menuList.remove(i);
				helpMap.put(menu.getMenuid(), menuTreeItem);
			}
		}
		while(menuList.size()>0){
			for(int i=0;i<menuList.size();i++){
				Menu menu = menuList.get(i);
				Integer id = menu.getParentid();
				MenuTree treeItem = menuToTreeItem(menu);
				helpMap.get(id).addChildren(treeItem);
				helpMap.put(menu.getMenuid(), treeItem);
				menuList.remove(menu);
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
}
