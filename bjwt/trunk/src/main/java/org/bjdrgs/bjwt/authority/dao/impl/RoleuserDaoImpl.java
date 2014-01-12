package org.bjdrgs.bjwt.authority.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.bjdrgs.bjwt.authority.dao.IRoleuserDao;
import org.bjdrgs.bjwt.authority.model.Roleuser;
import org.bjdrgs.bjwt.authority.parameter.RoleuserParam;
import org.bjdrgs.bjwt.core.dao.impl.BaseDaoImpl;
import org.bjdrgs.bjwt.core.web.Pagination;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository("RoleuserDao")
public class RoleuserDaoImpl extends BaseDaoImpl<Roleuser> implements IRoleuserDao {
	
	@Override
	public Pagination<Roleuser> query(RoleuserParam param) {
		StringBuilder hql = new StringBuilder();
		hql.append("from ");
		hql.append(Roleuser.class.getName());
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
	public Roleuser get(Integer userid, String rolecode) {
		String hql = "from " + Roleuser.class.getName()+" where user.userid=:userid and role.rolecode=:rolecode";
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userid", userid);
		paramMap.put("rolecode", rolecode);
		List<Roleuser> list = this.query(hql, paramMap);
		if(!CollectionUtils.isEmpty(list)){
			return list.get(0);
		}else{
			return null;
		}
	}

}

