package org.bjdrgs.bjwt.dicmanager.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.bjdrgs.bjwt.core.service.impl.BaseServiceImpl;
import org.bjdrgs.bjwt.core.web.GridPage;
import org.bjdrgs.bjwt.dicmanager.model.DicItem;
import org.bjdrgs.bjwt.dicmanager.parameter.DicItemParam;
import org.bjdrgs.bjwt.dicmanager.service.IDicItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("dicItemService")
public class DicItemServiceImpl extends BaseServiceImpl<DicItem> implements	IDicItemService {

	@Override
	public GridPage<DicItem> findPage(DicItemParam param) {
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
		
		return super.findPageByHql(param.getPage(), param.getRows(), hql.toString(), paramMap);
	}

	@Override
	public void save(DicItem entity) {
		if(entity.getCreateTime()==null){
			entity.setCreateTime(new Date());
		}
		super.save(entity);
	}

	@Override
	public void deleteByTypeId(Serializable typeId) {
		String hql = "delete from "+DicItem.class.getName()+" where type.id=?";
		getDao().delete(hql, typeId);
	}
}
