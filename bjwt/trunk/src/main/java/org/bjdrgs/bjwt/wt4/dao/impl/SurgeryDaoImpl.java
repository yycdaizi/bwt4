package org.bjdrgs.bjwt.wt4.dao.impl;

import java.io.Serializable;

import org.bjdrgs.bjwt.core.dao.impl.BaseDaoImpl;
import org.bjdrgs.bjwt.wt4.dao.ISurgeryDao;
import org.bjdrgs.bjwt.wt4.model.Surgery;
import org.springframework.stereotype.Repository;

@Repository("surgeryDao")
public class SurgeryDaoImpl extends BaseDaoImpl<Surgery> implements
		ISurgeryDao {

}
