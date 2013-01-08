package org.bjdrgs.bjwt.dicmanager.service;

import java.io.Serializable;

import org.bjdrgs.bjwt.core.service.IBaseService;
import org.bjdrgs.bjwt.core.web.GridPage;
import org.bjdrgs.bjwt.dicmanager.model.DicItem;
import org.bjdrgs.bjwt.dicmanager.parameter.DicItemParam;

public interface IDicItemService extends IBaseService<DicItem> {

	GridPage<DicItem> findPage(DicItemParam param);

	void deleteByTypeId(Serializable typeId);

}
