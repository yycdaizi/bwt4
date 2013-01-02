package org.bjdrgs.bjwt.dicmanager.service;

import org.bjdrgs.bjwt.core.service.IBaseService;
import org.bjdrgs.bjwt.core.web.GridPage;
import org.bjdrgs.bjwt.dicmanager.model.DicType;
import org.bjdrgs.bjwt.dicmanager.parameter.DicTypeParam;

public interface IDicTypeService extends IBaseService<DicType> {

	GridPage<DicType> findPage(DicTypeParam param);

}
