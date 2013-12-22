package org.bjdrgs.bjwt.wt4.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.bjdrgs.bjwt.authority.model.User;
import org.bjdrgs.bjwt.authority.utils.Constants;
import org.bjdrgs.bjwt.authority.utils.SecurityUtils;
import org.bjdrgs.bjwt.core.dao.DaoUtils;
import org.bjdrgs.bjwt.core.dao.impl.BaseDaoImpl;
import org.bjdrgs.bjwt.core.util.BeanUtils;
import org.bjdrgs.bjwt.core.web.Pagination;
import org.bjdrgs.bjwt.wt4.dao.IMedicalRecordDao;
import org.bjdrgs.bjwt.wt4.model.BirthDefect;
import org.bjdrgs.bjwt.wt4.model.Diagnose;
import org.bjdrgs.bjwt.wt4.model.ICU;
import org.bjdrgs.bjwt.wt4.model.MedicalRecord;
import org.bjdrgs.bjwt.wt4.model.Operation;
import org.bjdrgs.bjwt.wt4.model.Surgery;
import org.bjdrgs.bjwt.wt4.parameter.MedicalRecordParam;
import org.hibernate.Query;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository("medicalRecordDao")
public class MedicalRecordDaoImpl extends BaseDaoImpl<MedicalRecord> implements
		IMedicalRecordDao {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

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

		// 控制数据权限
		User user = SecurityUtils.getCurrentUser();
		if (param.isEnableAuthority()
				&& !Constants.ROOTUSER_NAME.equals(user.getUsername())) {
			hql.append(" and obj.ZA02C = :orgCode");
			paramMap.put("orgCode", user.getOrg().getOrgcode());
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
	// ZA02C=? and AAA28=? and AAC01=?
	private static final String uniqueFieldGroupCondition = org.bjdrgs.bjwt.core.util.CollectionUtils
			.join(MedicalRecord.uniqueKey, "=? and ") + "=?";

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> isExist(List<MedicalRecord> list) {
		if (CollectionUtils.isEmpty(list)) {
			return new ArrayList<Long>(0);
		}

		StringBuilder mainClause = new StringBuilder();
		mainClause.append("select ");
		mainClause.append(org.bjdrgs.bjwt.core.util.CollectionUtils
				.join(MedicalRecord.uniqueKey));
		mainClause.append(",id from ").append(MedicalRecord.class.getName());
		mainClause.append(" where ");

		Long[] results = new Long[list.size()];

		for (int i = 0; i < list.size(); i += BATCH_CHECK_SIZE) {
			int size = BATCH_CHECK_SIZE;
			if (i + size > list.size()) {
				// 最后剩余部分
				size = list.size() - i;
			}

			StringBuilder hql = new StringBuilder(mainClause);
			// 拼接size个查询条件
			for (int k = 0; k < size - 1; k++) {
				hql.append(uniqueFieldGroupCondition).append(" or ");
			}
			hql.append(uniqueFieldGroupCondition);

			Query query = this.getCurrentSession().createQuery(hql.toString());
			// 设置查询参数，总共size组参数，每组有MedicalRecord.uniqueFieldGroup.length个
			for (int j = 0; j < size; j++) {
				MedicalRecord mr = list.get(i + j);
				for (int x = 0; x < MedicalRecord.uniqueKey.length; x++) {
					Object value = BeanUtils.getProperty(mr,
							MedicalRecord.uniqueKey[x]);
					query.setParameter(MedicalRecord.uniqueKey.length * j + x,
							value);
				}
			}

			List<Object[]> ret = query.list();
			// 判断记录是否存在
			for (int j = 0; j < size; j++) {
				int index = i + j;
				MedicalRecord mr = list.get(index);
				for (Object[] r : ret) {
					boolean flag = true;
					for (int x = 0; x < MedicalRecord.uniqueKey.length; x++) {
						Object value = BeanUtils.getProperty(mr,
								MedicalRecord.uniqueKey[x]);
						if (!value.equals(r[x])) {
							flag = false;
							break;
						}
					}
					if (flag) {
						results[index] = (Long) r[MedicalRecord.uniqueKey.length];
						break;
					}
				}
			}
		}
		return Arrays.asList(results);
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

	@Override
	public void deleteSubObjectBySQL(final List<Long> idList) {
		doWork(new Work() {

			@Override
			public void execute(Connection connection) throws SQLException {
				PreparedStatement[] stmtList = new PreparedStatement[5];
				try {
					stmtList[0] = connection
							.prepareStatement(Diagnose.deleteByMedicalRecordIdSQL);
					stmtList[1] = connection
							.prepareStatement(Surgery.deleteByMedicalRecordIdSQL);
					stmtList[2] = connection
							.prepareStatement(ICU.deleteByMedicalRecordIdSQL);
					stmtList[3] = connection
							.prepareStatement(BirthDefect.deleteByMedicalRecordIdSQL);
					stmtList[4] = connection
							.prepareStatement(Operation.deleteByMedicalRecordIdSQL);

					for (int i = 0; i < idList.size(); i++) {
						for (PreparedStatement stmt : stmtList) {
							stmt.setLong(1, idList.get(i));
							stmt.addBatch();
						}
						// 每200执行一次
						if ((i + 1) % 200 == 0 || i == idList.size() - 1) {
							for (PreparedStatement stmt : stmtList) {
								stmt.executeBatch();
								stmt.clearBatch();
							}
							logger.debug("已删除" + (i + 1) + "条病案的子记录");
						}
					}
				} finally {
					for (PreparedStatement stmt : stmtList) {
						JdbcUtils.closeStatement(stmt);
					}
				}
			}
		});
	}
}
