package org.bjdrgs.bjwt.authority.service.impl;

import java.awt.Menu;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.bjdrgs.bjwt.authority.dao.IAuthorityDao;
import org.bjdrgs.bjwt.authority.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("authorityService")
public class AuthorityServiceImpl {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "authorityDao")
	private IAuthorityDao authorityDao;

	public List<Menu> getAuthoritedMenu(User user) {
		List<Menu> menuList = new ArrayList<Menu>();
		
		return menuList;
	}

}
