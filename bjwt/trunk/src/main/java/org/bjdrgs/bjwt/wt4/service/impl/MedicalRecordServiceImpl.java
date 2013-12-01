package org.bjdrgs.bjwt.wt4.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.bjdrgs.bjwt.authority.model.User;
import org.bjdrgs.bjwt.authority.utils.SecurityUtils;
import org.bjdrgs.bjwt.core.exception.BaseException;
import org.bjdrgs.bjwt.core.web.Pagination;
import org.bjdrgs.bjwt.dicdata.model.DicItem;
import org.bjdrgs.bjwt.dicdata.service.IDicDataService;
import org.bjdrgs.bjwt.wt4.Wt4Constants;
import org.bjdrgs.bjwt.wt4.dao.IBirthDefectDao;
import org.bjdrgs.bjwt.wt4.dao.IDiagnoseDao;
import org.bjdrgs.bjwt.wt4.dao.IICUDao;
import org.bjdrgs.bjwt.wt4.dao.IMedicalRecordDao;
import org.bjdrgs.bjwt.wt4.dao.IOperationDao;
import org.bjdrgs.bjwt.wt4.dao.ISurgeryDao;
import org.bjdrgs.bjwt.wt4.model.BirthDefect;
import org.bjdrgs.bjwt.wt4.model.Diagnose;
import org.bjdrgs.bjwt.wt4.model.ICU;
import org.bjdrgs.bjwt.wt4.model.MedicalRecord;
import org.bjdrgs.bjwt.wt4.model.Operation;
import org.bjdrgs.bjwt.wt4.model.Surgery;
import org.bjdrgs.bjwt.wt4.parameter.MedicalRecordParam;
import org.bjdrgs.bjwt.wt4.service.IMedicalRecordService;
import org.bjdrgs.bjwt.wt4.viewmodel.ImportResult;
import org.bjdrgs.bjwt.wt4.xmlvisitor.BaseVisitor;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import au.com.bytecode.opencsv.CSVWriter;

@Transactional
@Service("medicalRecordService")
public class MedicalRecordServiceImpl implements IMedicalRecordService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "medicalRecordDao")
	private IMedicalRecordDao medicalRecordDao;

	@Resource(name = "birthDefectDao")
	private IBirthDefectDao birthDefectDao;

	@Resource(name = "diagnoseDao")
	private IDiagnoseDao diagnoseDao;

	@Resource(name = "ICUDao")
	private IICUDao ICUDao;

	@Resource(name = "operationDao")
	private IOperationDao operationDao;

	@Resource(name = "surgeryDao")
	private ISurgeryDao surgeryDao;
	
	@Resource(name = "dicDataService")
	private IDicDataService dicDataService;

	private void beforeSave(MedicalRecord entity) {
		User user = SecurityUtils.getCurrentUser();
		// 一些处理
		if (entity.getCreateTime() == null) {
			entity.setCreateTime(new Date());
		}
		if (entity.getCreatedBy() == null) {
			entity.setCreatedBy(user);
		}
		entity.setUpdateTime(new Date());
		entity.setUpdatedBy(user);
		
		if(StringUtils.isEmpty(entity.getZA02C())){
			entity.setZA02C(user.getOrg().getOrgcode());
			entity.setZA03(user.getOrg().getOrgname());
			//entity.setZA04(user.getOrg().getOrgmanager_showname());
		}
	}

	@Override
	public void save(MedicalRecord[] entities) {
		for (MedicalRecord medicalRecord : entities) {
			this.save(medicalRecord);
		}
	}

	@Override
	public void save(MedicalRecord entity) {
		beforeSave(entity);//TODO 检测重复

		// 保存病案
		medicalRecordDao.save(entity);

		// 保存其他诊断信息
		diagnoseDao.deleteByProperty("medicalRecordId", entity.getId());
		if (!CollectionUtils.isEmpty(entity.getABDS())) {
			for (Diagnose diagnose : entity.getABDS()) {
				diagnose.setId(null);
				diagnose.setMedicalRecordId(entity.getId());
				diagnoseDao.save(diagnose);
			}
		}

		// 保存手术情况信息
		this.deleteSurgerysByMedicalRecordId(entity.getId());
		if (!CollectionUtils.isEmpty(entity.getACAS())) {
			for (Surgery surgery : entity.getACAS()) {
				surgery.setId(null);
				surgery.setMedicalRecordId(entity.getId());
				surgeryDao.save(surgery);
				if (!CollectionUtils.isEmpty(surgery.getACA09S())) {
					for (Operation operation : surgery.getACA09S()) {
						operation.setId(null);
						operation.setSurgeryId(surgery.getId());
						operationDao.save(operation);
					}
				}
			}
		}

		// 保存ICU信息
		ICUDao.deleteByProperty("medicalRecordId", entity.getId());
		if (!CollectionUtils.isEmpty(entity.getAEKS())) {
			for (ICU icu : entity.getAEKS()) {
				icu.setId(null);
				icu.setMedicalRecordId(entity.getId());
				ICUDao.save(icu);
			}
		}

		// 保存新生儿缺陷信息
		birthDefectDao.deleteByProperty("medicalRecordId", entity.getId());
		if (!CollectionUtils.isEmpty(entity.getAENS())) {
			for (BirthDefect obj : entity.getAENS()) {
				obj.setId(null);
				obj.setMedicalRecordId(entity.getId());
				birthDefectDao.save(obj);
			}
		}
	}

	private void deleteSurgerysByMedicalRecordId(Long medicalRecordId) {
		List<Surgery> list = surgeryDao.queryByProperty("medicalRecordId",
				medicalRecordId);
		for (Surgery surgery : list) {
			operationDao.deleteByProperty("surgeryId", surgery.getId());
		}
		surgeryDao.deleteByProperty("medicalRecordId", medicalRecordId);
	}

	@Override
	public Pagination<MedicalRecord> query(MedicalRecordParam param) {
		return medicalRecordDao.query(param);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Document toXML(Object entity) throws Exception {
		SAXReader reader = new SAXReader();
		Document document = null;

		String path = TEMPLATE_FOLDER + File.separatorChar
				+ entity.getClass().getSimpleName() + ".xml";
		document = reader.read(this.getClass().getResourceAsStream(path));

		Field[] fields = entity.getClass().getDeclaredFields();

		for (Field field : fields) {
			field.setAccessible(true);
			if (field.get(entity) != null) {
				String key = field.getName();
				Node node = document.selectSingleNode("//" + key);
				if (node != null) {
					String value = field.get(entity).toString();
					String type = field.getGenericType().toString();
					if ("class java.util.Date".equals(type)) {
						if (field.isAnnotationPresent(DateTimeFormat.class)) {
							DateTimeFormat format = field
									.getAnnotation(DateTimeFormat.class);
							value = new SimpleDateFormat(format.pattern())
									.format(field.get(entity));
						}
						node.setText(value);
					} else if ("class java.lang.Double".equals(type)) {
						if (field.isAnnotationPresent(NumberFormat.class)) {
							NumberFormat format = field
									.getAnnotation(NumberFormat.class);
							value = new DecimalFormat(format.pattern())
									.format(field.get(entity));
						}
						node.setText(value);
					} else if (type.startsWith("java.util.List")) {
						List list = (List) field.get(entity);
						for (Object object : list) {
							Document child = this.toXML(object);
							((Element) node).add(child.getRootElement());
						}
					} else {
						node.setText(value);
					}
				}
			}
		}
		// 去除空节点
		removeEmptyNode(document.getRootElement());
		return document;
	}

	private void removeEmptyNode(Element element) {
		if (element == null) {
			return;
		}
		for (Object child : element.elements()) {
			removeEmptyNode((Element) child);
		}
		String content = element.getStringValue();
		if (StringUtils.isBlank(content)) {
			element.detach();
		}
	}

	@Override
	public MedicalRecord get(Serializable id) {
		return medicalRecordDao.get(id);
	}

	@Override
	public String exportToXML(List<MedicalRecord> entities) throws Exception {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("CASES");

		for (MedicalRecord medicalRecord : entities) {
			Document node = toXML(medicalRecord);
			if (node.getRootElement() != null) {
				root.add(node.getRootElement());
			}
		}
		return document.asXML();
	}

	@Override
	public ImportResult importXmlFile(InputStream inputStream)
			throws Exception {
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		
		//处理ZA
		String[] zaKey = new String[]{"ZA01C","ZA02C","ZA03","ZA04"};
		Map<String, String> zaMap = new HashMap<String, String>();
		for(String key : zaKey){
			Node node = document.selectSingleNode("//" + key);
			if(node != null){
				zaMap.put(key, node.getText());
			}
		}

		//解析病案
		BaseVisitor<MedicalRecord> visitor = new BaseVisitor<MedicalRecord>(
				MedicalRecord.class);
		document.accept(visitor);
		List<MedicalRecord> list = visitor.getData();

		for (MedicalRecord medicalRecord : list) {
			medicalRecord.setZA01C(zaMap.get("ZA01C"));
			medicalRecord.setZA02C(zaMap.get("ZA02C"));
			medicalRecord.setZA03(zaMap.get("ZA03"));
			medicalRecord.setZA04(zaMap.get("ZA04"));
			
			medicalRecord.setState(MedicalRecord.STATE_UNVALIDATE);
		}
		return batchInsert(list);
	}

	/**
	 * 批量插入,由于不用执行删除，同时还使用了批量提交，速度比save快了近百倍
	 * 
	 * @param list
	 * @return 
	 * @throws Exception 
	 */
	public ImportResult batchInsert(List<MedicalRecord> entities) throws Exception {
		for (MedicalRecord medicalRecord : entities) {
			beforeSave(medicalRecord);
		}
		
		List<Boolean> isExistList = medicalRecordDao.isExist(entities);
		List<MedicalRecord> list = new ArrayList<MedicalRecord>(entities.size());
		for(int i=0; i<isExistList.size(); i++){
			if(!isExistList.get(i)){
				list.add(entities.get(i));
			}
		}
		
		medicalRecordDao.saveByBatch(list);

		List<Diagnose> diagnoseList = new ArrayList<Diagnose>();
		List<Surgery> surgeryList = new ArrayList<Surgery>();
		List<Operation> operationList = new ArrayList<Operation>();
		List<ICU> icuList = new ArrayList<ICU>();
		List<BirthDefect> defectList = new ArrayList<BirthDefect>();
		for (MedicalRecord entity : list) {
			if (!CollectionUtils.isEmpty(entity.getABDS())) {
				for (Diagnose diagnose : entity.getABDS()) {
					diagnose.setId(null);
					diagnose.setMedicalRecordId(entity.getId());
					diagnoseList.add(diagnose);
				}
			}

			if (!CollectionUtils.isEmpty(entity.getACAS())) {
				for (Surgery surgery : entity.getACAS()) {
					surgery.setId(null);
					surgery.setMedicalRecordId(entity.getId());
					surgeryList.add(surgery);
				}
			}

			if (!CollectionUtils.isEmpty(entity.getAEKS())) {
				for (ICU icu : entity.getAEKS()) {
					icu.setId(null);
					icu.setMedicalRecordId(entity.getId());
					icuList.add(icu);
				}
			}

			if (!CollectionUtils.isEmpty(entity.getAENS())) {
				for (BirthDefect obj : entity.getAENS()) {
					obj.setId(null);
					obj.setMedicalRecordId(entity.getId());
					defectList.add(obj);
				}
			}
		}

		diagnoseDao.saveByBatch(diagnoseList);
		surgeryDao.saveByBatch(surgeryList);
		ICUDao.saveByBatch(icuList);
		birthDefectDao.saveByBatch(defectList);

		for (Surgery surgery : surgeryList) {
			if (!CollectionUtils.isEmpty(surgery.getACA09S())) {
				for (Operation operation : surgery.getACA09S()) {
					operation.setId(null);
					operation.setSurgeryId(surgery.getId());
					operationList.add(operation);
				}
			}
		}
		operationDao.saveByBatch(operationList);
		
		ImportResult result = new ImportResult();
		result.setInserted(list.size());
		result.setIgnored(entities.size()-list.size());
		return result;
	}

	public void insert(MedicalRecord entity) {
		beforeSave(entity);

		// 保存病案
		medicalRecordDao.save(entity);

		// 保存其他诊断信息
		if (!CollectionUtils.isEmpty(entity.getABDS())) {
			for (Diagnose diagnose : entity.getABDS()) {
				diagnose.setId(null);
				diagnose.setMedicalRecordId(entity.getId());
				diagnoseDao.save(diagnose);
			}
		}

		// 保存手术情况信息
		if (!CollectionUtils.isEmpty(entity.getACAS())) {
			for (Surgery surgery : entity.getACAS()) {
				surgery.setId(null);
				surgery.setMedicalRecordId(entity.getId());
				surgeryDao.save(surgery);
				if (!CollectionUtils.isEmpty(surgery.getACA09S())) {
					for (Operation operation : surgery.getACA09S()) {
						operation.setId(null);
						operation.setSurgeryId(surgery.getId());
						operationDao.save(operation);
					}
				}
			}
		}

		// 保存ICU信息
		if (!CollectionUtils.isEmpty(entity.getAEKS())) {
			for (ICU icu : entity.getAEKS()) {
				icu.setId(null);
				icu.setMedicalRecordId(entity.getId());
				ICUDao.save(icu);
			}
		}

		// 保存新生儿缺陷信息
		if (!CollectionUtils.isEmpty(entity.getAENS())) {
			for (BirthDefect obj : entity.getAENS()) {
				obj.setId(null);
				obj.setMedicalRecordId(entity.getId());
				birthDefectDao.save(obj);
			}
		}
	}

	/*
	 * 批量插入的另一种备选，速度差不多，不过有内存溢出的风险。 另外还有一种方案是使用原生的jdbc批量执行。
	 * 通过sessionFactory.getClassMetadata(clazz)，得到的实际是AbstractEntityPersister
	 * 的子类，里面有个sqlInsertStrings属性可以获得插入sql public void
	 * batchInsert(List<MedicalRecord> list){ for (MedicalRecord medicalRecord :
	 * list) { insert(medicalRecord); } }
	 */

	@Override
	public ImportResult importZipFile(File file) throws Exception {
		ZipFile zipFile = new ZipFile(file);
		try {
			Enumeration<ZipEntry> enumeration = zipFile.getEntries();
			ZipEntry zipEntry = null;
			ImportResult result = new ImportResult();
			while (enumeration.hasMoreElements()) {
				zipEntry = (ZipEntry) enumeration.nextElement();
				if (zipEntry.isDirectory()) {
					continue;
				}
				String name = zipEntry.getName();
				// 只解析压缩文件中的xml文件
				if (name != null && name.matches("^.+\\.(?i)xml$")) {
					InputStream inputStream = zipFile.getInputStream(zipEntry);
					try {
						result.addResult(importXmlFile(inputStream));
					} finally {
						IOUtils.closeQuietly(inputStream);
					}
				}
			}
			return result;
		} finally {
			ZipFile.closeQuietly(zipFile);
		}
	}

	@Override
	public List<MedicalRecord> queryAll(MedicalRecordParam param) {
		return medicalRecordDao.queryAll(param);
	}

	@Override
	public void delete(MedicalRecord entity) {
		diagnoseDao.deleteByProperty("medicalRecordId", entity.getId());
		this.deleteSurgerysByMedicalRecordId(entity.getId());
		ICUDao.deleteByProperty("medicalRecordId", entity.getId());
		birthDefectDao.deleteByProperty("medicalRecordId", entity.getId());
		medicalRecordDao.delete(entity);
	}

	@SuppressWarnings("resource")
	@Override
	public void exportToCSV(MedicalRecordParam param, File csvFile)
			throws Exception {
		String[] header = new String[] { "病案号", "姓名", "出院日期", "出院科室", "主要诊断",
				"离院方式", "mdc", "drg", "总费用", "住院天数", "权重" };
		String[] fieldNames = new String[] { "AAA28", "AAA01", "AAC01",
				"AAC02C", "ABC01N", "AEM01C", "mdc", "drg", "ADA01", "AAC04",
				null };
		Field[] fields = new Field[fieldNames.length];
		for (int i = 0; i < fieldNames.length; i++) {
			if (fieldNames[i] != null) {
				Field field = MedicalRecord.class
						.getDeclaredField(fieldNames[i]);
				field.setAccessible(true);
				fields[i] = field;
			}
		}
		
		List<DicItem> dicItemList = dicDataService.listDicItemsByType(Wt4Constants.DIC_MR_MDC_DRG);
		Map<String, String> weightMap = new HashMap<String, String>();
		for (DicItem dicItem : dicItemList) {
			if(dicItem.getParent()!=null && dicItem.getParent().getCode()!=null){
				String key = buildMdcAndDrgCombination(dicItem.getParent().getCode(), dicItem.getCode());
				weightMap.put(key, dicItem.getRemark());
			}
		}
		
		List<Object[]> list = medicalRecordDao.queryLimitedFields(param, fieldNames);

		CSVWriter writer = new CSVWriter(new FileWriter(csvFile));
		try {
			writer.writeNext(header);
			for (Object[] mr : list) {
				String[] data = new String[header.length];
				for (int i = 0; i < mr.length; i++) {
					if(mr[i] == null||fields[i] == null){
						continue;
					}
					
					Object value = mr[i];
					String valStr = "";
					Class<?> type = fields[i].getType();
					if (type == Date.class) {
						if (fields[i].isAnnotationPresent(DateTimeFormat.class)) {
							DateTimeFormat format = fields[i]
									.getAnnotation(DateTimeFormat.class);
							valStr = new SimpleDateFormat(format.pattern())
									.format(value);
						} else {
							throw new BaseException("字段" + fieldNames[i]
									+ "未找到日期格式化注解");
						}
					} else if (type == Double.class) {
						if (fields[i].isAnnotationPresent(NumberFormat.class)) {
							NumberFormat format = fields[i]
									.getAnnotation(NumberFormat.class);
							valStr = new DecimalFormat(format.pattern())
									.format(value);
						} else {
							valStr = value.toString();
						}
					} else if (type == Integer.class) {
						valStr = value.toString();
					} else if (type == String.class) {
						valStr = (String) value;
					} else {
						throw new BaseException("未识别字段" + fieldNames[i]
								+ "的类型，无法处理");
					}
					data[i] = valStr;
				}
				//权重
				data[10] = weightMap.get(buildMdcAndDrgCombination(data[6], data[7]));
				writer.writeNext(data);
			}
			writer.flush();
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}
	
	private String buildMdcAndDrgCombination(String mdc, String drg){
		return "["+mdc + ">>" + drg + "]";
	}
}
