package org.bjdrgs.bjwt.wt4.service;

import java.io.File;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.bjdrgs.bjwt.core.web.Pagination;
import org.bjdrgs.bjwt.wt4.model.MedicalRecord;
import org.bjdrgs.bjwt.wt4.parameter.MedicalRecordParam;
import org.bjdrgs.bjwt.wt4.viewmodel.ImportResult;


public interface IMedicalRecordService {
	
	void save(MedicalRecord... entity);

	void delete(MedicalRecord entity);
	
	MedicalRecord get(Serializable id);
	
	Pagination<MedicalRecord> query(MedicalRecordParam param);
	
	List<MedicalRecord> queryAll(MedicalRecordParam param);
	
	ImportResult importXmlFile(Collection<File> xmlFileList);
	
	List<File> exportToXML(MedicalRecordParam param, File baseDir);
	
	void exportToCSV(MedicalRecordParam param, File csvFile);
}
