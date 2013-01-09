package org.bjdrgs.bjwt.dicmanager.service;

import org.bjdrgs.bjwt.core.service.IBaseService;
import org.bjdrgs.bjwt.core.web.GridPage;
import org.bjdrgs.bjwt.dicmanager.model.DicType;
import org.bjdrgs.bjwt.dicmanager.parameter.DicTypeParam;

public interface IDicTypeService extends IBaseService<DicType> {

	/**
	 * 根据参数分页查询
	 * @param param
	 * @return
	 */
	GridPage<DicType> findPage(DicTypeParam param);

	/**
	 * 根据字典类型编码获得字典类型对象
	 * @param code
	 * @return
	 */
	DicType getByCode(String code);
	
}
