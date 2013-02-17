package org.bjdrgs.bjwt.wt4.dao.impl;

import org.bjdrgs.bjwt.core.dao.impl.BaseDaoImpl;
import org.bjdrgs.bjwt.wt4.dao.IICUDao;
import org.bjdrgs.bjwt.wt4.model.ICU;
import org.springframework.stereotype.Repository;

@Repository("ICUDao")
public class ICUDaoImpl extends BaseDaoImpl<ICU> implements
		IICUDao {
}
