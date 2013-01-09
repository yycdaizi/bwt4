package org.bjdrgs.bjwt.dicmanager.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.bjdrgs.bjwt.core.service.impl.BaseServiceImpl;
import org.bjdrgs.bjwt.core.web.GridPage;
import org.bjdrgs.bjwt.dicmanager.model.DicItem;
import org.bjdrgs.bjwt.dicmanager.model.DicType;
import org.bjdrgs.bjwt.dicmanager.parameter.DicItemParam;
import org.bjdrgs.bjwt.dicmanager.service.IDicItemService;
import org.bjdrgs.bjwt.dicmanager.service.IDicTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Transactional
@Service("dicItemService")
public class DicItemServiceImpl extends BaseServiceImpl<DicItem> implements	IDicItemService {

	@Resource(name = "dicTypeService")
	private IDicTypeService dicTypeService;
	
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

	@Override
	public List<DicItem> findByType(String type) {
		String hql = "from "+DicItem.class.getName()+" where type.code=?";
		return getDao().query(hql, type);
	}

	@Override
	public DicItem get(String type, String code) {
		String hql = "from "+DicItem.class.getName()+" where type.code=? and code=?";
		List<DicItem> list = getDao().query(hql, type,code);
		if(CollectionUtils.isEmpty(list)){
			return null;
		}else{
			return list.get(0);
		}
	}

	@Override
	public boolean isCodeUnique(Integer typeId, String code) {
		if(StringUtils.isEmpty(code)){
			return true;
		}
		DicType dicType = dicTypeService.getById(typeId);
		if(dicType!=null){
			return get(dicType.getCode(), code)==null;
		}
		return false;
	}
}
