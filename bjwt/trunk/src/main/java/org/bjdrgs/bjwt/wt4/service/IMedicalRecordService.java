package org.bjdrgs.bjwt.wt4.service;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import org.bjdrgs.bjwt.core.web.Pagination;
import org.bjdrgs.bjwt.wt4.model.MedicalRecord;
import org.bjdrgs.bjwt.wt4.parameter.MedicalRecordParam;
import org.dom4j.Document;


public interface IMedicalRecordService {
	String TEMPLATE_FOLDER = "/org/bjdrgs/bjwt/wt4/template";
	
	void save(MedicalRecord entity);
	
	void save(MedicalRecord[] entities);

	Pagination<MedicalRecord> query(MedicalRecordParam param);
	
	MedicalRecord get(Serializable id);

	Document toXML(Object entity) throws Exception;
	
	String exportToXML(List<MedicalRecord> entities) throws Exception;
	
	List<MedicalRecord> queryAll(MedicalRecordParam param);

	void delete(MedicalRecord entity);

	List<MedicalRecord> importXmlFile(InputStream inputStream) throws Exception;

	List<MedicalRecord> importZipFile(File zipFile) throws Exception;

	void exportToCSV(List<MedicalRecord> list, File csvFile) throws Exception;
}
