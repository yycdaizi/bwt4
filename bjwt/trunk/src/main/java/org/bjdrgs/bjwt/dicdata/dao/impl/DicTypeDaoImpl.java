package org.bjdrgs.bjwt.dicdata.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.bjdrgs.bjwt.core.dao.impl.BaseDaoImpl;
import org.bjdrgs.bjwt.core.web.Pagination;
import org.bjdrgs.bjwt.dicdata.dao.IDicTypeDao;
import org.bjdrgs.bjwt.dicdata.model.DicType;
import org.bjdrgs.bjwt.dicdata.parameter.DicTypeParam;
import org.springframework.stereotype.Repository;

@Repository("dicTypeDao")
public class DicTypeDaoImpl extends BaseDaoImpl<DicType> implements IDicTypeDao {

	@Override
	public Pagination<DicType> query(DicTypeParam param) {
		StringBuilder hql = new StringBuilder();
		hql.append("from ");
		hql.append(DicType.class.getName());
		hql.append(" obj where 1=1");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(param.getKeyword())){
			hql.append(" and obj.code like :code or obj.name like :name");
			String keyword = "%"+param.getKeyword()+"%";
			paramMap.put("code", keyword);
			paramMap.put("name", keyword);
		}
		
		if(StringUtils.isNotEmpty(param.getSort())){
			hql.append(" order by obj."+param.getSort()+" "+param.getOrder());
		}
		
		return this.queryForPage(hql.toString(), param.getPage(), param.getRows(), paramMap);
	}

}
