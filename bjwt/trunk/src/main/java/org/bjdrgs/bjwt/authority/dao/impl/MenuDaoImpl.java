package org.bjdrgs.bjwt.authority.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.bjdrgs.bjwt.authority.dao.IMenuDao;
import org.bjdrgs.bjwt.authority.model.Menu;
import org.bjdrgs.bjwt.authority.parameter.MenuParam;
import org.bjdrgs.bjwt.core.dao.impl.BaseDaoImpl;
import org.bjdrgs.bjwt.core.web.Pagination;
import org.springframework.stereotype.Repository;

@Repository("MenuDao")
public class MenuDaoImpl extends BaseDaoImpl<Menu> implements IMenuDao {
	
	@Override
	public Pagination<Menu> query(MenuParam param) {
		StringBuilder hql = new StringBuilder();
		hql.append("from ");
		hql.append(Menu.class.getName());
		hql.append(" obj where 1=1");

		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(param.getKeyword())) {
			hql.append(" and obj.menuname like :menuname");
			String keyword = "%" + param.getKeyword() + "%";
			paramMap.put("menuname", keyword);
		}

		if (StringUtils.isNotEmpty(param.getSort())) {
			hql.append(" order by obj." + param.getSort() + " "
					+ param.getOrder());
		}
		return this.queryForPage(hql.toString(), param.getPage(),
				param.getRows(), paramMap);
	}

}
