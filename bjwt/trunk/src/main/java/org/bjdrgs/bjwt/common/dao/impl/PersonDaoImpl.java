package org.bjdrgs.bjwt.common.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.bjdrgs.bjwt.common.dao.IPersonDao;
import org.bjdrgs.bjwt.common.model.Person;
import org.bjdrgs.bjwt.common.parameter.PersonParam;
import org.bjdrgs.bjwt.core.dao.impl.BaseDaoImpl;
import org.bjdrgs.bjwt.core.web.Pagination;
import org.springframework.stereotype.Repository;

@Repository("personDao")
public class PersonDaoImpl extends BaseDaoImpl<Person> implements IPersonDao {

	@Override
	public List<Person> query(PersonParam param) {
		StringBuilder hql = new StringBuilder();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		buildHql(param, hql, paramMap);
		
		return query(hql.toString(), paramMap);
	}

	@Override
	public Pagination<Person> queryForPage(PersonParam param) {
		StringBuilder hql = new StringBuilder();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		buildHql(param, hql, paramMap);
		
		return queryForPage(hql.toString(), param.getPage(), param.getRows(), paramMap);
	}

	private void buildHql(PersonParam param, StringBuilder hql, Map<String, Object> paramMap){
		hql.append("from ");
		hql.append(Person.class.getName());
		hql.append(" obj where 1=1");
		
		if(param.getOrgId()!=null){
			hql.append(" and obj.org.orgid= :orgId");
			paramMap.put("orgId", param.getOrgId());
		}
		if(StringUtils.isNotEmpty(param.getEq_type())){
			hql.append(" and obj.type= :type");
			paramMap.put("type", param.getEq_type());
		}
		if(StringUtils.isNotEmpty(param.getBlike_name())){
			hql.append(" and obj.name like :name");
			paramMap.put("name", "%"+param.getBlike_name()+"%");
		}
		
		if(StringUtils.isNotEmpty(param.getSort())){
			hql.append(" order by "+param.getSort()+" "+param.getOrder());
		}
	}
}
