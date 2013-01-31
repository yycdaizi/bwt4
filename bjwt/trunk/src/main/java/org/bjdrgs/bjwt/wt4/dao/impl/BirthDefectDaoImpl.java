package org.bjdrgs.bjwt.wt4.dao.impl;

import java.io.Serializable;

import org.bjdrgs.bjwt.core.dao.impl.BaseDaoImpl;
import org.bjdrgs.bjwt.wt4.dao.IBirthDefectDao;
import org.bjdrgs.bjwt.wt4.model.BirthDefect;
import org.springframework.stereotype.Repository;

@Repository("birthDefectDao")
public class BirthDefectDaoImpl extends BaseDaoImpl<BirthDefect> implements
		IBirthDefectDao {

}
