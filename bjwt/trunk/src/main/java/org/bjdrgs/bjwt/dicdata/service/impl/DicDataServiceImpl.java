package org.bjdrgs.bjwt.dicdata.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.bjdrgs.bjwt.core.web.Pagination;
import org.bjdrgs.bjwt.dicdata.dao.IDicItemDao;
import org.bjdrgs.bjwt.dicdata.dao.IDicTypeDao;
import org.bjdrgs.bjwt.dicdata.model.DicItem;
import org.bjdrgs.bjwt.dicdata.model.DicType;
import org.bjdrgs.bjwt.dicdata.parameter.DicItemParam;
import org.bjdrgs.bjwt.dicdata.parameter.DicTypeParam;
import org.bjdrgs.bjwt.dicdata.service.IDicDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("dicDataService")
public class DicDataServiceImpl implements IDicDataService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "dicTypeDao")
	private IDicTypeDao dicTypeDao;
	
	@Resource(name="dicItemDao")
	private IDicItemDao dicItemDao;
	
	//字典类型操作函数
	@Override
	public Pagination<DicType> queryDicType(DicTypeParam param) {
		return dicTypeDao.query(param);
	}

	@Override
	public void saveDicType(DicType entity) {
		if(entity.getCreateTime()==null){
			entity.setCreateTime(new Date());
		}
		dicTypeDao.save(entity);
	}

	@Override
	public void deleteDicTypeById(Serializable id) {
		dicItemDao.deleteByProperty("type.id", id);
		dicTypeDao.deleteById(id);
	}
	
	@Override
	public DicType getDicTypeById(Integer id) {
		return dicTypeDao.get(id);
	}

	@Override
	public DicType getDicTypeByCode(String code) {
		return dicTypeDao.getByUnique("code", code);
	}
	
	//字典数据项操作函数
	@Override
	public List<DicItem> listDicItemsByType(String type) {
		return dicItemDao.queryByProperty("type.code", type);
	}

	@Override
	public DicItem getDicItem(String type, String code) {
		return dicItemDao.get(type, code);
	}

	@Override
	public void saveDicItem(DicItem entity) {
		if(entity.getCreateTime()==null){
			entity.setCreateTime(new Date());
		}
		dicItemDao.save(entity);
	}

	@Override
	public Pagination<DicItem> queryDicItem(DicItemParam param) {
		return dicItemDao.query(param);
	}

	@Override
	public void deleteDicItemById(Integer id) {
		dicItemDao.deleteById(id);
	}
	
}
