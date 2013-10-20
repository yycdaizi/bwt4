package org.bjdrgs.bjwt.authority.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.bjdrgs.bjwt.authority.dao.IOrgDao;
import org.bjdrgs.bjwt.authority.model.Org;
import org.bjdrgs.bjwt.authority.parameter.OrgParam;
import org.bjdrgs.bjwt.core.dao.impl.BaseDaoImpl;
import org.bjdrgs.bjwt.core.web.Pagination;
import org.springframework.stereotype.Repository;

@Repository("OrgDao")
public class OrgDaoImpl extends BaseDaoImpl<Org> implements IOrgDao {
	
	@Override
	public Pagination<Org> query(OrgParam param) {
		StringBuilder hql = new StringBuilder();
		hql.append("from ");
		hql.append(Org.class.getName());
		hql.append(" obj where 1=1");

		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(param.getKeyword())) {
			hql.append(" and obj.orgcode like :orgcode or obj.orgname like :orgname");
			String keyword = "%" + param.getKeyword() + "%";
			paramMap.put("orgcode", keyword);
			paramMap.put("orgname", keyword);
		}
		if(param.getId()!=null){
			if(param.getId()==0){
				hql.append(" and obj.parentOrg.orgid is null");
			}else{
				hql.append(" and obj.parentOrg.orgid = :parentid");
				paramMap.put("parentid", param.getId());
			}
		}
		if (StringUtils.isNotEmpty(param.getSort())) {
			hql.append(" order by obj." + param.getSort() + " "
					+ param.getOrder());
		}
		return this.queryForPage(hql.toString(), param.getPage(),
				param.getRows(), paramMap);
	}

	@Override
	public List<Org> getTopOrgs() {
		String hql = "from "+Org.class.getName()+" obj where obj.parentOrg.orgid is null";
		return this.query(hql);
	}

}

