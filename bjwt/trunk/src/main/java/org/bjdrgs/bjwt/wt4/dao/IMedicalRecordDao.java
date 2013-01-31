package org.bjdrgs.bjwt.wt4.dao;

import org.bjdrgs.bjwt.core.dao.IBaseDao;
import org.bjdrgs.bjwt.core.web.Pagination;
import org.bjdrgs.bjwt.wt4.model.MedicalRecord;
import org.bjdrgs.bjwt.wt4.parameter.MedicalRecordParam;

public interface IMedicalRecordDao extends IBaseDao<MedicalRecord> {

	Pagination<MedicalRecord> query(MedicalRecordParam param);

}
