package org.bjdrgs.bjwt.dicmanager.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.bjdrgs.bjwt.core.service.impl.BaseServiceImpl;
import org.bjdrgs.bjwt.core.web.GridPage;
import org.bjdrgs.bjwt.dicmanager.model.DicType;
import org.bjdrgs.bjwt.dicmanager.parameter.DicTypeParam;
import org.bjdrgs.bjwt.dicmanager.service.IDicTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("dicTypeService")
public class DicTypeServiceImpl extends BaseServiceImpl<DicType> implements	IDicTypeService {

	@Override
	public GridPage<DicType> findPage(DicTypeParam param) {
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
		
		return super.findPageByHql(param.getPage(), param.getRows(), hql.toString(),paramMap);
	}

	@Override
	public void save(DicType entity) {
		if(entity.getCreateTime()==null){
			entity.setCreateTime(new Date());
		}
		super.save(entity);
	}

}
