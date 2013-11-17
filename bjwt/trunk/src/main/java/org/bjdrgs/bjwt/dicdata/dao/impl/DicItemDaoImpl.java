package org.bjdrgs.bjwt.dicdata.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.bjdrgs.bjwt.core.dao.impl.BaseDaoImpl;
import org.bjdrgs.bjwt.core.web.Pagination;
import org.bjdrgs.bjwt.dicdata.dao.IDicItemDao;
import org.bjdrgs.bjwt.dicdata.model.DicItem;
import org.bjdrgs.bjwt.dicdata.parameter.DicItemParam;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository("dicItemDao")
public class DicItemDaoImpl extends BaseDaoImpl<DicItem> implements IDicItemDao {

	@Override
	public DicItem get(String type, String code, String parentCode) {
		String hql = "from "+DicItem.class.getName()+" where type.code=? and code=?";
		List<DicItem> list = null;
		if(StringUtils.isEmpty(parentCode)){
			hql += " and parent is null";
			list = this.query(hql, type, code);
		}else{
			hql += " and parent.code =?";
			list = this.query(hql, type, code, parentCode);
		}
		
		if(CollectionUtils.isEmpty(list)){
			return null;
		}else{
			return list.get(0);
		}
	}

	@Override
	public Pagination<DicItem> query(DicItemParam param) {
		StringBuilder hql = new StringBuilder();
		hql.append("from ");
		hql.append(DicItem.class.getName());
		hql.append(" where 1=1");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(param.getTypeId()!=null){
			hql.append(" and type.id= :typeId");
			paramMap.put("typeId", param.getTypeId());
		}
		
		if(StringUtils.isNotEmpty(param.getSort())){
			hql.append(" order by "+param.getSort()+" "+param.getOrder());
		}
		
		return this.queryForPage(hql.toString(), param.getPage(), param.getRows(), paramMap);
	}

	@Override
	public List<DicItem> getChildrenDicItem(DicItem parent) {
		String hql = "from "+DicItem.class.getName()+" where type.id=? and parent.id=?";
		return this.query(hql, parent.getType().getId(), parent.getParent().getId());
	}
}
