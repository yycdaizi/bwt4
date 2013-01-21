package org.bjdrgs.bjwt.dicdata.dao;

import org.bjdrgs.bjwt.core.dao.IBaseDao;
import org.bjdrgs.bjwt.core.web.Pagination;
import org.bjdrgs.bjwt.dicdata.model.DicType;
import org.bjdrgs.bjwt.dicdata.parameter.DicTypeParam;

public interface IDicTypeDao extends IBaseDao<DicType> {

	/**
	 * 字典类型分页查询
	 * @param param
	 * @return
	 */
	Pagination<DicType> query(DicTypeParam param);
}
