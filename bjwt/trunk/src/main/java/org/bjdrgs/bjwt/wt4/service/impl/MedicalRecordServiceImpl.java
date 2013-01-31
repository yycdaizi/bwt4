package org.bjdrgs.bjwt.wt4.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	public void save(MedicalRecord entity) {
		//一些处理
		if(entity.getCreateTime()==null){
			entity.setCreateTime(new Date());
		}
		if(entity.getCreatedBy()==null){
			//TODO
			entity.setCreatedBy(1);
		}
		entity.setUpdateTime(new Date());
		//TODO
		entity.setUpdatedBy(1);
				
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
}
