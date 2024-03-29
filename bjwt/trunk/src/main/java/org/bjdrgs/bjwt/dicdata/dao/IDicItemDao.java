package org.bjdrgs.bjwt.dicdata.dao;

import java.util.List;

import org.bjdrgs.bjwt.core.dao.IBaseDao;
import org.bjdrgs.bjwt.core.web.Pagination;
import org.bjdrgs.bjwt.dicdata.model.DicItem;
import org.bjdrgs.bjwt.dicdata.parameter.DicItemParam;

public interface IDicItemDao extends IBaseDao<DicItem> {

	/**
	 * 根据字典类型编码和字典项编码获得字典项
	 * @param type 字典类型编码
	 * @param code 字典数据项编码
	 * @param parentId 字典数据项上级Id
	 * @return
	 */
	DicItem get(String type, String code, Integer parentId);

	Pagination<DicItem> query(DicItemParam param);

	List<DicItem> getChildrenDicItem(DicItem parent);

	List<DicItem> getChildrenDicItem(String type, Integer parentId);
}
