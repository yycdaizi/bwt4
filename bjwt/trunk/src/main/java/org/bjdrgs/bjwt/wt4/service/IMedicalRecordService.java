package org.bjdrgs.bjwt.wt4.service;

import org.bjdrgs.bjwt.core.web.Pagination;
import org.bjdrgs.bjwt.wt4.model.MedicalRecord;
import org.bjdrgs.bjwt.wt4.parameter.MedicalRecordParam;


public interface IMedicalRecordService {

	void save(MedicalRecord entity);

	Pagination<MedicalRecord> query(MedicalRecordParam param);

}
