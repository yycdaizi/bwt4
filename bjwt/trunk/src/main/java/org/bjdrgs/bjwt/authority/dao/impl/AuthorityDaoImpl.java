package org.bjdrgs.bjwt.authority.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bjdrgs.bjwt.authority.dao.IAuthorityDao;
import org.bjdrgs.bjwt.authority.model.Menu;
import org.bjdrgs.bjwt.core.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

@Repository("AuthorityDao")
public class AuthorityDaoImpl extends BaseDaoImpl<Menu> implements IAuthorityDao{
	
	public List<Menu> getAuthedMenu(Integer userid){
		String hql = "select menu from Menu menu,Previlege prev ,Roleuser ru where ru.user.userid=:userid "+
		"and ru.role.roleid=prev.mastervalue and menu.menuid = prev.resourcevalue";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", userid);
		return this.query(hql, params);
	}
}
