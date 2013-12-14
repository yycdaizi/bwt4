package org.bjdrgs.bjwt.wt4.dao;

import java.util.List;

import org.bjdrgs.bjwt.core.dao.IBaseDao;
import org.bjdrgs.bjwt.core.web.Pagination;
import org.bjdrgs.bjwt.wt4.model.MedicalRecord;
import org.bjdrgs.bjwt.wt4.parameter.MedicalRecordParam;

public interface IMedicalRecordDao extends IBaseDao<MedicalRecord> {

	Pagination<MedicalRecord> query(MedicalRecordParam param);

	List<MedicalRecord> queryAll(MedicalRecordParam param);

	boolean isExist(MedicalRecord entity);

	List<Long> isExist(List<MedicalRecord> list);
	
	List<Object[]> queryLimitedFields(MedicalRecordParam param, String[] fieldNames);


}
