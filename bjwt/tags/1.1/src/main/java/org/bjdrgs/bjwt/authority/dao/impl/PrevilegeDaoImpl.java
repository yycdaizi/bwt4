package org.bjdrgs.bjwt.authority.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.bjdrgs.bjwt.authority.dao.IPrevilegeDao;
import org.bjdrgs.bjwt.authority.model.Previlege;
import org.bjdrgs.bjwt.authority.parameter.PrevilegeParam;
import org.bjdrgs.bjwt.core.dao.impl.BaseDaoImpl;
import org.bjdrgs.bjwt.core.web.Pagination;
import org.springframework.stereotype.Repository;

@Repository("PrevilegeDao")
public class PrevilegeDaoImpl extends BaseDaoImpl<Previlege> implements IPrevilegeDao {
	
	@Override
	public Pagination<Previlege> query(PrevilegeParam param) {
		StringBuilder hql = new StringBuilder();
		hql.append("from ");
		hql.append(Previlege.class.getName());
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

}

