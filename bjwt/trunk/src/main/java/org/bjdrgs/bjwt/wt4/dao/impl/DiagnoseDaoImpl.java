package org.bjdrgs.bjwt.wt4.dao.impl;

import java.io.Serializable;

import org.bjdrgs.bjwt.core.dao.impl.BaseDaoImpl;
import org.bjdrgs.bjwt.wt4.dao.IDiagnoseDao;
import org.bjdrgs.bjwt.wt4.model.Diagnose;
import org.springframework.stereotype.Repository;

@Repository("diagnoseDao")
public class DiagnoseDaoImpl extends BaseDaoImpl<Diagnose> implements
		IDiagnoseDao {

}
