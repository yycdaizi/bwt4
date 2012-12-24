package org.bjdrgs.bjwt.dicmanager.service.impl;

import org.bjdrgs.bjwt.core.service.impl.BaseServiceImpl;
import org.bjdrgs.bjwt.dicmanager.model.DicType;
import org.bjdrgs.bjwt.dicmanager.service.IDicTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("dicTypeService")
public class DicTypeServiceImpl extends BaseServiceImpl<DicType> implements	IDicTypeService {

}
