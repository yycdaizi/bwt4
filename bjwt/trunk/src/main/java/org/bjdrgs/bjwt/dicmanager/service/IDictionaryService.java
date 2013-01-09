package org.bjdrgs.bjwt.dicmanager.service;

import java.util.List;

import org.bjdrgs.bjwt.dicmanager.model.DicItem;

public interface IDictionaryService {
	/**
	 * 根据字典类型编码查询该字典的所有字典项
	 * @param type 字典类型编码
	 * @return
	 */
	List<DicItem> listDicItemsByType(String type);
	/**
	 * 根据字典类型编码和字典项编码获得字典项
	 * @param type 字典类型编码
	 * @param code 字典数据项编码
	 * @return
	 */
	DicItem getDicItem(String type, String code);
}
