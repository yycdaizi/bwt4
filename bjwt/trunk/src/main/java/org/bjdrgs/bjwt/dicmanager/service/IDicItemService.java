package org.bjdrgs.bjwt.dicmanager.service;

import java.io.Serializable;
import java.util.List;

import org.bjdrgs.bjwt.core.service.IBaseService;
import org.bjdrgs.bjwt.core.web.GridPage;
import org.bjdrgs.bjwt.dicmanager.model.DicItem;
import org.bjdrgs.bjwt.dicmanager.parameter.DicItemParam;

public interface IDicItemService extends IBaseService<DicItem> {

	/**
	 * datagrid分页查询
	 * @param param
	 * @return
	 */
	GridPage<DicItem> findPage(DicItemParam param);

	/**
	 * 根据字典类型的ID，删除属于该字典的所有字典数据项
	 * @param typeId
	 */
	void deleteByTypeId(Serializable typeId);

	/**
	 * 根据字典类型编码查询该字典的所有字典项
	 * @param type 字典类型编码
	 * @return
	 */
	List<DicItem> findByType(String type);
	/**
	 * 根据字典类型编码和字典项编码获得字典项
	 * @param type 字典类型编码
	 * @param code 字典数据项编码
	 * @return
	 */
	DicItem get(String type, String code);
}
