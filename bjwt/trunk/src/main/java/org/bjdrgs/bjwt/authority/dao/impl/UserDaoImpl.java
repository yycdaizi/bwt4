package org.bjdrgs.bjwt.authority.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.bjdrgs.bjwt.authority.dao.IUserDao;
import org.bjdrgs.bjwt.authority.model.User;
import org.bjdrgs.bjwt.authority.parameter.UserParam;
import org.bjdrgs.bjwt.core.dao.impl.BaseDaoImpl;
import org.bjdrgs.bjwt.core.web.Pagination;
import org.springframework.stereotype.Repository;

@Repository("UserDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao {
	
	@Override
	public Pagination<User> query(UserParam param) {
		StringBuilder hql = new StringBuilder();
		hql.append("from ");
		hql.append(User.class.getName());
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
	public List<User> findUserByUP(String username, String password) {
		StringBuilder hql = new StringBuilder();
		hql.append("from ");
		hql.append(User.class.getName());
		hql.append(" obj where obj.username=:username and obj.password=:password");
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("username", username);
		paramMap.put("password", password);
		return this.query(hql.toString(), paramMap);
	}

}

