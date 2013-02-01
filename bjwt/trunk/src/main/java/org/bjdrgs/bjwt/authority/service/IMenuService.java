package org.bjdrgs.bjwt.authority.service;

import java.awt.Menu;
import java.util.ArrayList;
import java.util.List;

import org.bjdrgs.bjwt.authority.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("menuService")
public class IMenuService {
	public List<Menu> getAuthoritedMenu(User user) {
		List<Menu> menuList = new ArrayList<Menu>();

		return menuList;
	}
}
