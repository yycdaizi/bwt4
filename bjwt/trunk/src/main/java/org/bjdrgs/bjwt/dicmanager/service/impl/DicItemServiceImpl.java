package org.bjdrgs.bjwt.dicmanager.service.impl;

import org.bjdrgs.bjwt.core.service.impl.BaseServiceImpl;
import org.bjdrgs.bjwt.dicmanager.model.DicItem;
import org.bjdrgs.bjwt.dicmanager.service.IDicItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("dicItemService")
public class DicItemServiceImpl extends BaseServiceImpl<DicItem> implements	IDicItemService {

}
