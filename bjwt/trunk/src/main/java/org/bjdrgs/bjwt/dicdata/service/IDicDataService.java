package org.bjdrgs.bjwt.dicdata.service;

import java.io.Serializable;
import java.util.List;

import org.bjdrgs.bjwt.core.web.Pagination;
import org.bjdrgs.bjwt.dicdata.model.DicItem;
import org.bjdrgs.bjwt.dicdata.model.DicType;
import org.bjdrgs.bjwt.dicdata.parameter.DicItemParam;
import org.bjdrgs.bjwt.dicdata.parameter.DicTypeParam;


public interface IDicDataService {
	//字典类型操作函数
	/**
	 * 保存字典类型
	 * @param entity
	 */
	void saveDicType(DicType entity);
	/**
	 * 根据字典类型ID获得字典类型对象
	 * @param id
	 * @return
	 */
	DicType getDicTypeById(Integer id);
	
	/**
	 * 根据字典类型编码获得字典类型对象
	 * @param code
	 * @return
	 */
	DicType getDicTypeByCode(String code);
	
	/**
	 * 根据字典类型ID删除字典类型
	 * @param id
	 */
	void deleteDicTypeById(Serializable id);
	/**
	 * 字典类型分页查询
	 * @param param
	 * @return
	 */
	Pagination<DicType> queryDicType(DicTypeParam param);

	
	//字典数据项操作函数
	/**
	 * 保存字典数据项
	 * @param entity
	 */
	void saveDicItem(DicItem entity);	
	/**
	 * 根据字典类型编码查询该字典的所有字典项
	 * @param type 字典类型编码
	 * @return
	 */
	List<DicItem> listDicItemsByType(String type);
	
	/**
	 * 获得字典项的所有下级字典项
	 * @param parent 字典项
	 * @return
	 */
	List<DicItem> getChildrenDicItem(DicItem parent);
	
	/**
	 * 根据字典类型编码、字典项编码和字典项上级编码获得字典项
	 * @param type 字典类型编码
	 * @param code 字典数据项编码
	 * @param parentCode 字典数据项上级编码
	 * @return
	 */
	DicItem getDicItem(String type, String code, String parentCode);
	
	/**
	 * 字典数据项分页查询
	 * @param param
	 * @return
	 */
	Pagination<DicItem> queryDicItem(DicItemParam param);

	/**
	 * 根据字典数据项ID删除字典数据项
	 * @param id
	 */
	void deleteDicItemById(Integer id);

	

}
