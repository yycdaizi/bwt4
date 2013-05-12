package org.bjdrgs.bjwt.authority.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.bjdrgs.bjwt.authority.dao.IRoleDao;
import org.bjdrgs.bjwt.authority.model.Role;
import org.bjdrgs.bjwt.authority.model.User;
import org.bjdrgs.bjwt.authority.parameter.RoleParam;
import org.bjdrgs.bjwt.core.dao.impl.BaseDaoImpl;
import org.bjdrgs.bjwt.core.web.Pagination;
import org.springframework.stereotype.Repository;

@Repository("RoleDao")
public class RoleDaoImpl extends BaseDaoImpl<Role> implements IRoleDao {
	
	@Override
	public Pagination<Role> query(RoleParam param) {
		StringBuilder hql = new StringBuilder();
		hql.append("from ");
		hql.append(Role.class.getName());
		hql.append(" obj where 1=1");

		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(param.getKeyword())) {
			//TODO add keyword query condition
		}

		if (StringUtils.isNotEmpty(param.getSort())) {
			hql.append(" order by obj." + param.getSort() + " "
					+ param.getOrder());
		}
		return this.queryForPage(hql.toString(), param.getPage(),
				param.getRows(), paramMap);
	}
	
	@Override
	public List<Role> getRoleByUser(User user) {
		String hql = "select role from Role role,Roleuser ru where ru.user.userid=:userid and ru.role.roleid=role.roleid";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", user.getUserid());
		return this.query(hql, params);
	}
	
}

