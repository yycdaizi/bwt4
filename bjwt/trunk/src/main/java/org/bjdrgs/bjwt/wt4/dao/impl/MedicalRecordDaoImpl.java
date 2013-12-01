package org.bjdrgs.bjwt.wt4.dao.impl;

import java.lang.reflect.Field;
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
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

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
		// 医院+病案号（AAA28）+出院日期（AAC01） 唯一标识
		String hql = "select count(*) from " + MedicalRecord.class.getName()
				+ " where ZA02C=:ZA02C and AAA28=:AAA28 and AAC01=:AAC01";
		Long count = (Long) this.getCurrentSession().createQuery(hql)
				.setString("ZA02C", entity.getZA02C())
				.setString("AAA28", entity.getAAA28())
				.setDate("AAC01", entity.getAAC01()).uniqueResult();
		return count > 0;
	}

	private static final int BATCH_CHECK_SIZE = 16;
	private static final String[] uniqueFieldNameGroup = { "ZA02C", "AAA28",
			"AAC01" };
	private static Field[] uniqueFieldGroup = null;
	private static String uniqueFieldGroupCondition = null;
	static {
		uniqueFieldGroup = new Field[uniqueFieldNameGroup.length];
		for (int i = 0; i < uniqueFieldNameGroup.length; i++) {
			Field field;
			try {
				field = MedicalRecord.class
						.getDeclaredField(uniqueFieldNameGroup[i]);
				field.setAccessible(true);
				uniqueFieldGroup[i] = field;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		//ZA02C=? and AAA28=? and AAC01=?
		uniqueFieldGroupCondition = org.bjdrgs.bjwt.core.util.CollectionUtils
				.join(uniqueFieldNameGroup, "=? and ") + "=?";
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Boolean> isExist(List<MedicalRecord> list) throws Exception {
		if (CollectionUtils.isEmpty(list)) {
			return new ArrayList<Boolean>(0);
		}

		StringBuilder mainClause = new StringBuilder();
		mainClause.append("select ");
		mainClause.append(org.bjdrgs.bjwt.core.util.CollectionUtils
				.join(uniqueFieldNameGroup));
		mainClause.append(" from ").append(MedicalRecord.class.getName());
		mainClause.append(" where ");

		List<Boolean> results = new ArrayList<Boolean>(list.size());
		for (int i = 0; i < list.size(); i += BATCH_CHECK_SIZE) {
			int size = BATCH_CHECK_SIZE;
			if (i + size > list.size()) {
				// 最后剩余部分
				size = list.size() - i;
			}

			StringBuilder hql = new StringBuilder(mainClause);

			for (int k = 0; k < size - 1; k++) {
				hql.append(uniqueFieldGroupCondition).append(" or ");
			}
			hql.append(uniqueFieldGroupCondition);

			Query query = this.getCurrentSession().createQuery(hql.toString());
			for (int j = 0; j < size; j++) {
				MedicalRecord mr = list.get(i + j);
				for (int x = 0; x < uniqueFieldGroup.length; x++) {
					query.setParameter(uniqueFieldGroup.length * j + x,
							uniqueFieldGroup[x].get(mr));
				}
			}
			List<Object[]> ret = query.list();
			// 判断记录是否存在
			for (int j = 0; j < size; j++) {
				MedicalRecord mr = list.get(i + j);
				boolean exist = false;
				for (Object[] r : ret) {
					boolean flag = true;
					for (int x = 0; x < uniqueFieldGroup.length; x++) {
						if (!uniqueFieldGroup[x].get(mr).equals(r[x])) {
							flag = false;
							break;
						}
					}
					if (flag) {
						exist = true;
						break;
					}
				}
				results.add(exist);
			}
		}
		return results;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> queryLimitedFields(MedicalRecordParam param,
			String[] fieldNames) {
		if (fieldNames.length == 0) {
			return new ArrayList<Object[]>(0);
		}

		StringBuilder hql = new StringBuilder();
		hql.append("select ");
		for (int i = 0; i < fieldNames.length - 1; i++) {
			hql.append(fieldNames[i]).append(",");
		}
		hql.append(fieldNames[fieldNames.length - 1]).append(" ");

		Map<String, Object> paramMap = new HashMap<String, Object>();
		this.buildQueryHql(param, hql, paramMap);

		Query query = getCurrentSession().createQuery(hql.toString());
		DaoUtils.applyParametersToQuery(query, paramMap);
		return query.list();
	}

}
