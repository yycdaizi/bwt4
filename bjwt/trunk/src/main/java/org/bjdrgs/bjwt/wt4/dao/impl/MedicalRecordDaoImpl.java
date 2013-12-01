package org.bjdrgs.bjwt.wt4.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.bjdrgs.bjwt.core.dao.DaoUtils;
import org.bjdrgs.bjwt.core.dao.impl.BaseDaoImpl;
import org.bjdrgs.bjwt.core.web.Pagination;
import org.bjdrgs.bjwt.wt4.dao.IMedicalRecordDao;
import org.bjdrgs.bjwt.wt4.model.MedicalRecord;
import org.bjdrgs.bjwt.wt4.parameter.MedicalRecordParam;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository("medicalRecordDao")
public class MedicalRecordDaoImpl extends BaseDaoImpl<MedicalRecord> implements
		IMedicalRecordDao {

	@Override
	public Pagination<MedicalRecord> query(MedicalRecordParam param) {
		StringBuilder hql = new StringBuilder();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		this.buildQueryHql(param, hql, paramMap);

		return this.queryForPage(hql.toString(), param.getPage(),
				param.getRows(), paramMap);
	}

	@Override
	public List<MedicalRecord> queryAll(MedicalRecordParam param) {
		StringBuilder hql = new StringBuilder();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		this.buildQueryHql(param, hql, paramMap);
		return this.query(hql.toString(), paramMap);
	}

	private void buildQueryHql(MedicalRecordParam param, StringBuilder hql,
			Map<String, Object> paramMap) {
		// StringBuilder hql = new StringBuilder();
		hql.append("from ");
		hql.append(MedicalRecord.class.getName());
		hql.append(" obj where 1=1");

		// Map<String, Object> paramMap = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(param.getBlike_AAA01())) {
			hql.append(" and obj.AAA01 like :AAA01");
			paramMap.put("AAA01", "%" + param.getBlike_AAA01() + "%");
		}
		if (StringUtils.isNotEmpty(param.getBlike_AAA28())) {
			hql.append(" and obj.AAA28 like :AAA28");
			paramMap.put("AAA28", "%" + param.getBlike_AAA28() + "%");
		}
		if (StringUtils.isNotEmpty(param.getEq_AAB02C())) {
			hql.append(" and obj.AAB02C = :AAB02C");
			paramMap.put("AAB02C", param.getEq_AAB02C());
		}
		if (StringUtils.isNotEmpty(param.getEq_AEM01C())) {
			hql.append(" and obj.AEM01C = :AEM01C");
			paramMap.put("AEM01C", param.getEq_AEM01C());
		}
		if (param.getLe_AAC01() != null) {
			hql.append(" and obj.AAC01 <= :le_AAC01");
			paramMap.put("le_AAC01", param.getLe_AAC01());
		}
		if (param.getGe_AAC01() != null) {
			hql.append(" and obj.AAC01 >= :ge_AAC01");
			paramMap.put("ge_AAC01", param.getGe_AAC01());
		}
		if (StringUtils.isNotEmpty(param.getEq_mdc())) {
			hql.append(" and obj.mdc = :eq_mdc");
			paramMap.put("eq_mdc", param.getEq_mdc());
		}
		if (StringUtils.isNotEmpty(param.getEq_drg())) {
			hql.append(" and obj.drg = :eq_drg");
			paramMap.put("eq_drg", param.getEq_drg());
		}
		if (StringUtils.isNotEmpty(param.getEq_state())) {
			hql.append(" and obj.state = :eq_state");
			paramMap.put("eq_state", param.getEq_state());
		}
		if (param.getOrgId() != null) {
			hql.append(" and obj.createdBy.org.orgid = :orgId");
			paramMap.put("orgId", param.getOrgId());
		}

		if (StringUtils.isNotEmpty(param.getSort())) {
			hql.append(" order by obj." + param.getSort() + " "
					+ param.getOrder());
		}
	}

	@Override
	public boolean isExist(MedicalRecord entity) {
		// TODO 医院+病案号（AAA28）+出院日期（AAC01） 唯一标识
		String hql = "select count(*) from " + MedicalRecord.class.getName()
				+ " where ZA02C=:ZA02C and AAA28=:AAA28 and AAC01=:AAC01";
		Long count = (Long) this.getCurrentSession().createQuery(hql)
				.setString("ZA02C", entity.getZA02C())
				.setString("AAA28", entity.getAAA28())
				.setDate("AAC01", entity.getAAC01()).uniqueResult();
		return count > 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> queryLimitedFields(MedicalRecordParam param, String[] fieldNames) {
		if(fieldNames.length == 0){
			return new ArrayList<Object[]>(0);
		}
		
		StringBuilder hql = new StringBuilder();
		hql.append("select ");
		for (int i=0; i<fieldNames.length-1; i++) {
			hql.append(fieldNames[i]).append(",");
		}
		hql.append(fieldNames[fieldNames.length-1]).append(" ");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		this.buildQueryHql(param, hql, paramMap);
		
		Query query = getCurrentSession().createQuery(hql.toString());
		DaoUtils.applyParametersToQuery(query, paramMap);
		return query.list();
	}

}
