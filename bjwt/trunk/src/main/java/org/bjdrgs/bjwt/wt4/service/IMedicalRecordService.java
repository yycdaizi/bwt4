package org.bjdrgs.bjwt.wt4.service;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import org.bjdrgs.bjwt.core.web.Pagination;
import org.bjdrgs.bjwt.wt4.model.MedicalRecord;
import org.bjdrgs.bjwt.wt4.parameter.MedicalRecordParam;
import org.bjdrgs.bjwt.wt4.viewmodel.ImportResult;
import org.dom4j.Document;


public interface IMedicalRecordService {
	String TEMPLATE_FOLDER = "/org/bjdrgs/bjwt/wt4/template";
	
	void save(MedicalRecord... entity);

	Pagination<MedicalRecord> query(MedicalRecordParam param);
	
	MedicalRecord get(Serializable id);

	Document toXML(Object entity) throws Exception;
	
	String exportToXML(List<MedicalRecord> entities) throws Exception;
	
	List<MedicalRecord> queryAll(MedicalRecordParam param);

	void delete(MedicalRecord entity);

	ImportResult importXmlFile(InputStream inputStream) throws Exception;

	ImportResult importZipFile(File zipFile) throws Exception;

	void exportToCSV(MedicalRecordParam param, File csvFile) throws Exception;
}
