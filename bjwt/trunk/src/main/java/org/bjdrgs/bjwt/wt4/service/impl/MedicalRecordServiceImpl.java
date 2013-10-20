package org.bjdrgs.bjwt.wt4.service.impl;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.bjdrgs.bjwt.authority.model.User;
import org.bjdrgs.bjwt.authority.utils.SecurityUtils;
import org.bjdrgs.bjwt.core.exception.BaseException;
import org.bjdrgs.bjwt.core.web.Pagination;
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
import org.dom4j.Document;
import org.dom4j.DocumentException;
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

@Transactional
@Service("medicalRecordService")
public class MedicalRecordServiceImpl implements IMedicalRecordService{
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name="medicalRecordDao")
	private IMedicalRecordDao medicalRecordDao;
	
	@Resource(name="birthDefectDao")
	private IBirthDefectDao birthDefectDao;
	
	@Resource(name="diagnoseDao")
	private IDiagnoseDao diagnoseDao;
	
	@Resource(name="ICUDao")
	private IICUDao ICUDao;
	
	@Resource(name="operationDao")
	private IOperationDao operationDao;
	
	@Resource(name="surgeryDao")
	private ISurgeryDao surgeryDao;
	
	@Override
	public void save(MedicalRecord[] entities) {
		for (MedicalRecord medicalRecord : entities) {
			this.save(medicalRecord);
		}
	}	
	
	@Override
	public void save(MedicalRecord entity) {
		User user = SecurityUtils.getCurrentUser();
		//一些处理
		if(entity.getCreateTime()==null){
			entity.setCreateTime(new Date());
		}
		if(entity.getCreatedBy()==null){
			entity.setCreatedBy(user);
		}
		entity.setUpdateTime(new Date());
		entity.setUpdatedBy(user);
				
		//保存病案
		medicalRecordDao.save(entity);
		
		//保存其他诊断信息
		diagnoseDao.deleteByProperty("medicalRecordId", entity.getId());
		if(!CollectionUtils.isEmpty(entity.getABDS())){
			for(Diagnose diagnose : entity.getABDS()){
				diagnose.setId(null);
				diagnose.setMedicalRecordId(entity.getId());
				diagnoseDao.save(diagnose);
			}
		}
		
		//保存手术情况信息
		this.deleteSurgerysByMedicalRecordId(entity.getId());
		if(!CollectionUtils.isEmpty(entity.getACAS())){
			for(Surgery surgery : entity.getACAS()){
				surgery.setId(null);
				surgery.setMedicalRecordId(entity.getId());
				surgeryDao.save(surgery);
				if(!CollectionUtils.isEmpty(surgery.getACA09S())){
					for(Operation operation : surgery.getACA09S()){
						operation.setId(null);
						operation.setSurgeryId(surgery.getId());
						operationDao.save(operation);
					}
				}
			}
		}
		
		//保存ICU信息
		ICUDao.deleteByProperty("medicalRecordId", entity.getId());
		if(!CollectionUtils.isEmpty(entity.getAEKS())){
			for(ICU icu : entity.getAEKS()){
				icu.setId(null);
				icu.setMedicalRecordId(entity.getId());
				ICUDao.save(icu);
			}
		}
		
		//保存新生儿缺陷信息
		birthDefectDao.deleteByProperty("medicalRecordId", entity.getId());
		if(!CollectionUtils.isEmpty(entity.getAENS())){
			for(BirthDefect obj : entity.getAENS()){
				obj.setId(null);
				obj.setMedicalRecordId(entity.getId());
				birthDefectDao.save(obj);
			}
		}
	}
	
	private void deleteSurgerysByMedicalRecordId(Long medicalRecordId){
		List<Surgery> list = surgeryDao.queryByProperty("medicalRecordId", medicalRecordId);
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
		
		String path = TEMPLATE_FOLDER+File.separatorChar+entity.getClass().getSimpleName()+".xml";
		document = reader.read(this.getClass().getResourceAsStream(path));
			
		Field[] fields = entity.getClass().getDeclaredFields();
		
		for (Field field : fields) {
			field.setAccessible(true);
			if(field.get(entity)!=null){
				String key = field.getName();
				Node node = document.selectSingleNode("//"+key);
				if(node!=null){
					String value = field.get(entity).toString();
					String type = field.getGenericType().toString();
					if("class java.util.Date".equals(type)){
						if(field.isAnnotationPresent(DateTimeFormat.class)){
							DateTimeFormat format = field.getAnnotation(DateTimeFormat.class);
							value = new SimpleDateFormat(format.pattern()).format(field.get(entity));
						}
						node.setText(value);
					}else if("class java.lang.Double".equals(type)){
						if(field.isAnnotationPresent(NumberFormat.class)){
							NumberFormat format = field.getAnnotation(NumberFormat.class);
							value = new DecimalFormat(format.pattern()).format(field.get(entity));
						}
						node.setText(value);
					}else if(type.startsWith("java.util.List")){
						List list = (List) field.get(entity);
						for (Object object : list) {
							Document child = this.toXML(object);
							((Element)node).add(child.getRootElement());
						}
					}else{
						node.setText(value);
					}
				}
			}
		}
		//去除空节点
		removeEmptyNode(document.getRootElement());
		return document;
	}
	
	private void removeEmptyNode(Element element){
		if(element==null){
			return;
		}
		for (Object child : element.elements()) {
			removeEmptyNode((Element) child);
		}
		String content = element.getStringValue();
		if(StringUtils.isBlank(content)){
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
			if(node.getRootElement()!=null){
				root.add(node.getRootElement());	
			}
		}
		return document.asXML();
	}
	
	public <T> List<T> parseXML(Node document, Class<T> clazz) throws Exception{
		Field rootNameField = clazz.getDeclaredField("ROOT_NAME");
		rootNameField.setAccessible(true);
		String rootName = (String) rootNameField.get(null);
		
		List<T> objList = new ArrayList<T>();
		List<Node> rootList = document.selectNodes("//"+rootName);
		for (Node root : rootList) {
			T obj = clazz.newInstance();
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				String key = field.getName();
				Node node = root.selectSingleNode("//"+key);
				
				if(node==null){
					continue;
				}
				
				Object value = null;
				Type type = field.getGenericType();
				if(type instanceof Class){
					//Class typeClass = (Class) type;
					if(type == Date.class){
						if(field.isAnnotationPresent(DateTimeFormat.class)){
							DateTimeFormat format = field.getAnnotation(DateTimeFormat.class);
							value = new SimpleDateFormat(format.pattern()).parse(node.getText());
						}else{
							throw new BaseException("类"+clazz.getName()+"的字段"+key+"未找到日期格式化注解");
						}
					}else if(type == Double.class){
						value = Double.valueOf(node.getText());
					}else if(type == Integer.class){
						value = Integer.valueOf(node.getText());
					}else if(type == String.class){
						value = node.getText();
					}else{
						throw new BaseException("未识别类"+clazz.getName()+"的字段"+key+"的类型，无法处理");
					}
				}
				else if(type instanceof ParameterizedType){
					ParameterizedType ptype = (ParameterizedType) type;
					if(ptype.getRawType() == List.class){
						Type[] argTypes = ptype.getActualTypeArguments();
						Class argClazz = (Class) argTypes[0];
						value = parseXML(node, argClazz);
					}else{
						throw new BaseException("未识别类"+clazz.getName()+"的字段"+key+"的类型，无法处理");
					}
				}else{
					throw new BaseException("未识别类"+clazz.getName()+"的字段"+key+"的类型，无法处理");
				}
				field.set(obj, value);
			}
			objList.add(obj);
		}
		return objList;
	}
	
	@Override
	public List<MedicalRecord> importFile(InputStream inputStream) throws Exception{
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		List<MedicalRecord> list = parseXML(document, MedicalRecord.class);
		for (MedicalRecord medicalRecord : list) {
			medicalRecord.setState(MedicalRecord.STATE_UNVALIDATE);
			save(medicalRecord);
		}
		return list;
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
}
