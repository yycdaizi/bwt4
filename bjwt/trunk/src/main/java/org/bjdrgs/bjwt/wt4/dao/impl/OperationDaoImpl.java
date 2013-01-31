package org.bjdrgs.bjwt.wt4.dao.impl;

import java.io.Serializable;

import org.bjdrgs.bjwt.core.dao.impl.BaseDaoImpl;
import org.bjdrgs.bjwt.wt4.dao.IOperationDao;
import org.bjdrgs.bjwt.wt4.model.Operation;
import org.springframework.stereotype.Repository;

@Repository("operationDao")
public class OperationDaoImpl extends BaseDaoImpl<Operation> implements
		IOperationDao {

}
