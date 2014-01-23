package org.bjdrgs.bjwt.wt4.service.impl;

import static org.bjdrgs.bjwt.wt4.Wt4Constants.DEFAULT_ENCODING;
import static org.bjdrgs.bjwt.wt4.Wt4Constants.MR_XML_ROOT;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import net.sf.cglib.beans.BeanMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.bjdrgs.bjwt.authority.model.Org;
import org.bjdrgs.bjwt.authority.model.User;
import org.bjdrgs.bjwt.authority.service.IOrgService;
import org.bjdrgs.bjwt.authority.utils.SecurityUtils;
import org.bjdrgs.bjwt.core.exception.BaseException;
import org.bjdrgs.bjwt.core.util.SpringContextUtils;
import org.bjdrgs.bjwt.core.util.TempFileUtils;
import org.bjdrgs.bjwt.core.web.Pagination;
import org.bjdrgs.bjwt.dicdata.model.DicItem;
import org.bjdrgs.bjwt.dicdata.service.IDicDataService;
import org.bjdrgs.bjwt.wt4.Wt4Constants;
import org.bjdrgs.bjwt.wt4.dao.EntityReader;
import org.bjdrgs.bjwt.wt4.dao.IBirthDefectDao;
import org.bjdrgs.bjwt.wt4.dao.IDiagnoseDao;
import org.bjdrgs.bjwt.wt4.dao.IICUDao;
import org.bjdrgs.bjwt.wt4.dao.IMedicalRecordDao;
import org.bjdrgs.bjwt.wt4.dao.IOperationDao;
import org.bjdrgs.bjwt.wt4.dao.ISurgeryDao;
import org.bjdrgs.bjwt.wt4.exception.IllegalInputException;
import org.bjdrgs.bjwt.wt4.model.BirthDefect;
import org.bjdrgs.bjwt.wt4.model.Diagnose;
import org.bjdrgs.bjwt.wt4.model.ICU;
import org.bjdrgs.bjwt.wt4.model.MedicalRecord;
import org.bjdrgs.bjwt.wt4.model.Operation;
import org.bjdrgs.bjwt.wt4.model.Surgery;
import org.bjdrgs.bjwt.wt4.parameter.MedicalRecordParam;
import org.bjdrgs.bjwt.wt4.service.IMedicalRecordService;
import org.bjdrgs.bjwt.wt4.viewmodel.ImportResult;
import org.bjdrgs.bjwt.wt4.xml.XmlTransferUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.ElementHandler;
import org.dom4j.ElementPath;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.hibernate.validator.HibernateValidatorContext;
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

	@Resource(name = "OrgService")
	private IOrgService orgService;

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

		fillOrg(entity);
	}

	private void fillOrg(MedicalRecord entity) {
		if (StringUtils.isEmpty(entity.getZA02C())) {
			User user = SecurityUtils.getCurrentUser();
			entity.setZA02C(user.getOrg().getOrgcode());
			entity.setZA03(user.getOrg().getOrgname());
			if(user.getOrg().getOrgmanager() != null){
				entity.setZA04(user.getOrg().getOrgmanager().getDisplayName());
			}
		}
	}

	/**
	 * 最基础的save方法，封装了保持病案的逻辑，其他save方法都是调用的本方法。<br/>
	 * 此方法只执行持久化操作，而不进行其他任何处理。<br/>
	 * 此方法对批量处理进行了优化：<br/>
	 * <ul>
	 * <li>使用了批量处理，可设置hibernate.jdbc.batch_size的大小来调整</li>
	 * <li>它根据主键id判断是进行新增还是保存操作，当新增时,由于不用执行删除子表，提升了速度。</li>
	 * <li>及时清理session，防止内存溢出</li>
	 * </ul>
	 * 
	 * @param list
	 * @return
	 */
	private void doSave(List<MedicalRecord> entities) {

		// for (MedicalRecord entity : entities) {
		// if (entity.getId() != null) {
		// // 要更新的病案，删除病案原有的子表记录
		// diagnoseDao.deleteByProperty("medicalRecordId", entity.getId());
		// this.deleteSurgerysByMedicalRecordId(entity.getId());
		// ICUDao.deleteByProperty("medicalRecordId", entity.getId());
		// birthDefectDao.deleteByProperty("medicalRecordId",
		// entity.getId());
		// }
		// }

		List<Long> idList = new ArrayList<Long>();
		for (MedicalRecord entity : entities) {
			if (entity.getId() != null) {
				idList.add(entity.getId());
			}
		}
		// 删除病案原有的所有子表记录
		medicalRecordDao.deleteSubObjectBySQL(idList);

		medicalRecordDao.saveByBatch(entities);

		List<Diagnose> diagnoseList = new ArrayList<Diagnose>();
		List<Surgery> surgeryList = new ArrayList<Surgery>();
		List<Operation> operationList = new ArrayList<Operation>();
		List<ICU> icuList = new ArrayList<ICU>();
		List<BirthDefect> defectList = new ArrayList<BirthDefect>();
		for (MedicalRecord entity : entities) {
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
	}

	@Override
	public void save(MedicalRecord... entities) {
		List<MedicalRecord> list = Arrays.asList(entities);
		for (MedicalRecord entity : entities) {
			beforeSave(entity);
			// 如果不为草稿，则检测是否重复
			if (!MedicalRecord.STATE_DRAFT.equals(entity.getState())) {
				List<Long> isExistList = medicalRecordDao.isExist(list);
				for (int i = 0; i < isExistList.size(); i++) {
					if (isExistList.get(i) != null) {
						list.get(i).setId(isExistList.get(i));
					}
				}
			}
		}
		doSave(list);
	}

	@Override
	public void delete(MedicalRecord entity) {
		diagnoseDao.deleteByProperty("medicalRecordId", entity.getId());
		this.deleteSurgerysByMedicalRecordId(entity.getId());
		ICUDao.deleteByProperty("medicalRecordId", entity.getId());
		birthDefectDao.deleteByProperty("medicalRecordId", entity.getId());
		medicalRecordDao.delete(entity);
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
	public MedicalRecord get(Serializable id) {
		return medicalRecordDao.get(id);
	}

	@Override
	public Pagination<MedicalRecord> query(MedicalRecordParam param) {
		return medicalRecordDao.query(param);
	}

	@Override
	public List<MedicalRecord> queryAll(MedicalRecordParam param) {
		return medicalRecordDao.queryAll(param);
	}

	@Override
	public ImportResult importXmlFile(Collection<File> xmlFileList)
			throws Exception {
		ImportResult result = new ImportResult();
		if (xmlFileList == null) {
			return result;
		}
		for (File xmlFile : xmlFileList) {
			if (xmlFile == null) {
				continue;
			}
			SAXReader reader = new SAXReader();
			reader.setIgnoreComments(true);
			reader.setStripWhitespaceText(true);

			// 验证和预处理
			XmlPreprocessor preprocessor = new XmlPreprocessor();
			reader.setDefaultHandler(preprocessor);
			reader.read(xmlFile);

			if (!preprocessor.isValid()) {
				throw new IllegalInputException("xml数据有误！");
			}

			Map<String, Object> commonPropMap = preprocessor.getCommonPropMap();

			// 导入
			XmlImporter importer = new XmlImporter(commonPropMap);
			reader.setDefaultHandler(null);
			reader.addHandler("/" + MR_XML_ROOT, importer);
			reader.addHandler(
					"/" + MR_XML_ROOT + "/" + MedicalRecord.ROOT_NAME, importer);
			reader.read(xmlFile);

			result.addResult(importer.getResult());
		}
		return result;
	}

	class XmlImporter implements ElementHandler {
		private static final int BATCH_SIZE = 100;

		private Map<String, Object> commonPropMap = new HashMap<String, Object>();
		private List<MedicalRecord> dataList = new ArrayList<MedicalRecord>();

		Validator validator;
		ImportResult result = new ImportResult();

		public XmlImporter(Map<String, Object> commonPropMap) {
			this.commonPropMap = commonPropMap;
			ValidatorFactory validatorFactory = SpringContextUtils
					.getBean(ValidatorFactory.class);
			// validator = validatorFactory.getValidator();
			// 使用fail fast模式
			validator = ((HibernateValidatorContext) validatorFactory
					.usingContext()).failFast(true).getValidator();
		}

		@Override
		public void onStart(ElementPath elementPath) {

		}

		@Override
		public void onEnd(ElementPath elementPath) {
			Element element = elementPath.getCurrent();

			if (MedicalRecord.ROOT_NAME.equalsIgnoreCase(element.getName())) {
				List<MedicalRecord> plist = XmlTransferUtils.parseXML(element,
						MedicalRecord.class);
				for (MedicalRecord medicalRecord : plist) {
					fillCommonProperties(medicalRecord);
					dataList.add(medicalRecord);

					if (dataList.size() == BATCH_SIZE) {
						doImport(dataList);
						dataList.clear();
					}
				}
			} else if (Wt4Constants.MR_XML_ROOT.equalsIgnoreCase(element
					.getName())) {
				doImport(dataList);
				dataList.clear();
			}

			element.detach();
			element = null;
		}

		private void fillCommonProperties(MedicalRecord entity) {
			BeanMap beanMap = BeanMap.create(entity);
			for (Entry<String, Object> entry : commonPropMap.entrySet()) {
				beanMap.put(entity, entry.getKey(), entry.getValue());
			}
		}

		private void doImport(List<MedicalRecord> list) {
			for (MedicalRecord medicalRecord : list) {
				beforeSave(medicalRecord);
				// 验证病案
				Set<ConstraintViolation<MedicalRecord>> errors = validator
						.validate(medicalRecord);
				if (CollectionUtils.isEmpty(errors)) {
					medicalRecord.setState(MedicalRecord.STATE_COMPLETE);
				} else {
					medicalRecord.setState(MedicalRecord.STATE_VALIDATED_FAIL);
				}
			}

			List<Long> isExistList = medicalRecordDao.isExist(list);
			for (int i = 0; i < isExistList.size(); i++) {
				if (isExistList.get(i) != null) {
					list.get(i).setId(isExistList.get(i));
					result.addUpdated(1);
				} else {
					result.addInserted(1);
				}
			}

			doSave(list);
		}

		public ImportResult getResult() {
			return result;
		}
	}

	private Element buildXmlHeader(MedicalRecordParam param, long count) {
		Element header = DocumentHelper.createElement("Z");

		Element nodeZA = header.addElement("ZA");

		if (StringUtils.isNotEmpty(param.getZA02C())) {
			Org org = orgService.getOrgByCode(param.getZA02C());
			if (org != null) {
				// 行政区划代码
				if (org.getZoneCode() != null) {
					nodeZA.addElement("ZA01C").addText(org.getZoneCode());
				}
				// 组织机构代码
				nodeZA.addElement("ZA02C").addText(param.getZA02C());
				// 机构名称
				if (org.getOrgname() != null) {
					nodeZA.addElement("ZA03").addText(org.getOrgname());
				}
				// 单位负责人
				if (org.getOrgmanager() != null
						&& org.getOrgmanager().getDisplayName() != null) {
					nodeZA.addElement("ZA04").addText(
							org.getOrgmanager().getDisplayName());
				}
			} else {
				nodeZA.addElement("ZA02C").addText(param.getZA02C());
			}
		}

		Element nodeZB = header.addElement("ZB");
		nodeZB.addElement("ZB01C").addText("B_WT4-2012");

		// 只有查询整月的时候才设年月
		if (param.getLe_AAC01() != null && param.getGe_AAC01() != null) {
			Calendar start = DateUtils.toCalendar(param.getGe_AAC01());
			Calendar end = DateUtils.toCalendar(param.getLe_AAC01());
			if (start.get(Calendar.YEAR) == end.get(Calendar.YEAR)
					&& start.get(Calendar.MONTH) == end.get(Calendar.MONTH)
					&& start.get(Calendar.DAY_OF_MONTH) == 1) {
				// 此月最后一天
				start.add(Calendar.MONTH, 1);
				start.add(Calendar.DAY_OF_MONTH, -1);
				if (start.get(Calendar.DAY_OF_MONTH) == end
						.get(Calendar.DAY_OF_MONTH)) {
					// 数据年份月份
					nodeZB.addElement("ZB02").addText(
							String.valueOf(end.get(Calendar.YEAR)));
					nodeZB.addElement("ZB03").addText(
							String.valueOf(end.get(Calendar.MONTH) + 1));
				}
			}
		}
		User user = SecurityUtils.getCurrentUser();
		// 填报人
		if (user.getDisplayName() != null) {
			nodeZB.addElement("ZB04").addText(user.getDisplayName());
		}
		// 填报人联系电话
		if (user.getTelphone() != null) {
			nodeZB.addElement("ZB05").addText(user.getTelphone());
		}
		// 填报日期
		nodeZB.addElement("ZB06").addText(
				new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		// 记录数
		nodeZB.addElement("ZB07").addText(String.valueOf(count));
		// 邮箱
		if (user.getEmail() != null) {
			nodeZB.addElement("ZB08").addText(user.getEmail());
		}
		// 手机
		if (user.getMobilephone() != null) {
			nodeZB.addElement("ZB09").addText(user.getMobilephone());
		}

		XmlTransferUtils.removeEmptyNode(header);
		return header;
	}

	public void exportToSingleXML(MedicalRecordParam param, File xmlFile)
			throws Exception {
		if (StringUtils.isEmpty(param.getZA02C())) {
			throw new IllegalArgumentException("查询参数ZA02C不能为空");
		}

		OutputStream output = null;
		XMLWriter xmlWriter = null;
		XMLWriter tempWriter = null;
		File tempFile = null;
		try {
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding(DEFAULT_ENCODING);

			// 将所有病案节点写入临时文件
			tempFile = TempFileUtils.createTempFile();
			tempWriter = new XMLWriter(new BufferedWriter(new FileWriter(
					tempFile)), format);
			tempWriter.setIndentLevel(1);
			XmlExporter exporter = new XmlExporter(tempWriter);
			medicalRecordDao.readAll(param, exporter);
			tempWriter.flush();
			tempWriter.close();

			// 写完整的xml文件
			output = new FileOutputStream(xmlFile);
			xmlWriter = new XMLWriter(output, format);
			Element root = DocumentHelper.createElement(MR_XML_ROOT);
			xmlWriter.startDocument();
			xmlWriter.writeOpen(root);
			// ZA&ZB
			Element header = buildXmlHeader(param, exporter.getCount());
			xmlWriter.setIndentLevel(1);
			xmlWriter.write(header);
			xmlWriter.flush();

			FileUtils.copyFile(tempFile, output);

			xmlWriter.setIndentLevel(0);
			xmlWriter.println();
			xmlWriter.writeClose(root);
			xmlWriter.endDocument();
			xmlWriter.flush();
			xmlWriter.close();
		} finally {
			try {
				if (tempWriter != null)
					tempWriter.close();
			} catch (Exception e) {
			}
			try {
				if (xmlWriter != null)
					xmlWriter.close();
			} catch (Exception e) {
			}
			IOUtils.closeQuietly(output);
			FileUtils.deleteQuietly(tempFile);
		}
	}

	class XmlExporter implements EntityReader<MedicalRecord> {
		private long count = 0;
		private XMLWriter writer;

		public XmlExporter(XMLWriter writer) {
			this.writer = writer;
		}

		@Override
		public void read(MedicalRecord entity) {
			Document node = XmlTransferUtils.toXML(entity);
			try {
				writer.write(node.getRootElement());
			} catch (IOException e) {
				throw new BaseException(e);
			}
			count++;
		}

		public long getCount() {
			return count;
		}
	}

	private void writeEmptyXML(MedicalRecordParam param, File xmlFile)
			throws Exception {
		XMLWriter xmlWriter = null;
		try {
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement(MR_XML_ROOT);
			root.add(buildXmlHeader(param, 0));

			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding(DEFAULT_ENCODING);
			xmlWriter = new XMLWriter(new BufferedWriter(
					new FileWriter(xmlFile)), format);
			xmlWriter.write(document);
			xmlWriter.flush();
			xmlWriter.close();
		} finally {
			try {
				if (xmlWriter != null)
					xmlWriter.close();
			} catch (Exception e) {
			}
		}
	}

	@Override
	public List<File> exportToXML(MedicalRecordParam param, File baseDir)
			throws Exception {
		List<File> fileList = new ArrayList<File>();
		List<String> orgCodeList = medicalRecordDao.listOrgCode(param);

		if (CollectionUtils.isEmpty(orgCodeList)) {
			File xmlFile = new File(baseDir, "export-0.xml");
			writeEmptyXML(param, xmlFile);
			fileList.add(xmlFile);
			return fileList;
		}

		for (String orgCode : orgCodeList) {
			File xmlFile = new File(baseDir, "export-" + orgCode + ".xml");
			MedicalRecordParam singleParam = (MedicalRecordParam) param.clone();
			singleParam.setZA02C(orgCode);
			exportToSingleXML(singleParam, xmlFile);
			fileList.add(xmlFile);
		}
		return fileList;
	}

	@SuppressWarnings("resource")
	@Override
	public void exportToCSV(MedicalRecordParam param, File csvFile)
			throws Exception {
		String[] header = new String[] { "病案号", "姓名", "出院日期", "出院科室", "主要诊断",
				"离院方式", "mdc", "drg", "总费用", "住院天数", "权重" };
		String[] fieldNames = new String[] { "AAA28", "AAA01", "AAC01",
				"AAC02C", "ABC01C", "AEM01C", "mdc", "drg", "ADA01", "AAC04",
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

		List<DicItem> dicItemList = dicDataService
				.listDicItemsByType(Wt4Constants.DIC_MR_MDC_DRG);
		Map<String, String> weightMap = new HashMap<String, String>();
		for (DicItem dicItem : dicItemList) {
			if (dicItem.getParent() != null
					&& dicItem.getParent().getCode() != null) {
				String key = buildMdcAndDrgCombination(dicItem.getParent()
						.getCode(), dicItem.getCode());
				weightMap.put(key, dicItem.getRemark());
			}
		}

		List<Object[]> list = medicalRecordDao.queryLimitedFields(param,
				fieldNames);

		CSVWriter writer = new CSVWriter(new FileWriter(csvFile));
		try {
			writer.writeNext(header);
			for (Object[] mr : list) {
				String[] data = new String[header.length];
				for (int i = 0; i < mr.length; i++) {
					if (mr[i] == null || fields[i] == null) {
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
					} else if (type == Integer.class || type == Double.class
							|| type == BigDecimal.class) {
						if (fields[i].isAnnotationPresent(NumberFormat.class)) {
							NumberFormat format = fields[i]
									.getAnnotation(NumberFormat.class);
							valStr = new DecimalFormat(format.pattern())
									.format(value);
						} else {
							valStr = value.toString();
						}
					} else if (type == String.class) {
						valStr = (String) value;
					} else {
						throw new BaseException("未识别字段" + fieldNames[i]
								+ "的类型，无法处理");
					}
					data[i] = valStr;
				}
				// 权重
				data[10] = weightMap.get(buildMdcAndDrgCombination(data[6],
						data[7]));
				writer.writeNext(data);
			}
			writer.flush();
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}

	private String buildMdcAndDrgCombination(String mdc, String drg) {
		return "[" + mdc + ">>" + drg + "]";
	}
}
