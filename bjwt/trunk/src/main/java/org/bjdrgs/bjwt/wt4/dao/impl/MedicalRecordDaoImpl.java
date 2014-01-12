package org.bjdrgs.bjwt.wt4.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.bjdrgs.bjwt.core.dao.DaoUtils;
import org.bjdrgs.bjwt.core.dao.impl.BaseDaoImpl;
import org.bjdrgs.bjwt.core.util.BeanUtils;
import org.bjdrgs.bjwt.core.web.Pagination;
import org.bjdrgs.bjwt.wt4.dao.EntityReader;
import org.bjdrgs.bjwt.wt4.dao.IMedicalRecordDao;
import org.bjdrgs.bjwt.wt4.model.BirthDefect;
import org.bjdrgs.bjwt.wt4.model.Diagnose;
import org.bjdrgs.bjwt.wt4.model.ICU;
import org.bjdrgs.bjwt.wt4.model.MedicalRecord;
import org.bjdrgs.bjwt.wt4.model.Operation;
import org.bjdrgs.bjwt.wt4.model.Surgery;
import org.bjdrgs.bjwt.wt4.parameter.MedicalRecordParam;
import org.hibernate.CacheMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
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

		// getZA02C方法中控制了数据权限
		String ZA02C = param.getZA02C();
		if (StringUtils.isNotEmpty(ZA02C)) {
			hql.append(" and obj.ZA02C = :orgCode");
			paramMap.put("orgCode", ZA02C);
		}

		if (StringUtils.isNotEmpty(param.getSort())) {
			hql.append(" order by obj." + param.getSort() + " "
					+ param.getOrder());
		}
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

	private SQLQuery createSQLQuery(String sql) {
		return getCurrentSession().createSQLQuery(sql);
	}

	@Override
	public void deleteSubObjectBySQL(final List<Long> idList) {
		final int BATCH_SIZE = 50;

		List<Long> param = new ArrayList<Long>();
		for (int k = 0; k < idList.size(); k++) {
			param.add(idList.get(k));
			if ((k + 1) % BATCH_SIZE == 0 || k == idList.size() - 1) {
				createSQLQuery(Diagnose.deleteByMedicalRecordIdSQL)
						.setParameterList("ids", param).executeUpdate();

				createSQLQuery(ICU.deleteByMedicalRecordIdSQL)
						.setParameterList("ids", param).executeUpdate();

				createSQLQuery(BirthDefect.deleteByMedicalRecordIdSQL)
						.setParameterList("ids", param).executeUpdate();

				// 必须先删除操作，再删除手术
				createSQLQuery(Operation.deleteByMedicalRecordIdSQL)
						.setParameterList("ids", param).executeUpdate();

				createSQLQuery(Surgery.deleteByMedicalRecordIdSQL)
						.setParameterList("ids", param).executeUpdate();

				param = new ArrayList<Long>();
			}
		}
	}

	@Deprecated
	public void deleteSubObjectByJDBC(final List<Long> idList) {
		final int BATCH_SIZE = 50;
		final List<String> paramList = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();

		int count = 0;
		for (int k = 0; k < idList.size(); k++) {
			if (idList.get(k) != null) {
				sb.append(idList.get(k));
				count++;
				if (count % BATCH_SIZE == 0 || k == idList.size() - 1) {
					paramList.add(sb.toString());
					sb = new StringBuilder();
				} else {
					sb.append(",");
				}
			}
		}

		doWork(new Work() {
			@Override
			public void execute(Connection connection) throws SQLException {
				Statement stmt = null;
				try {
					stmt = connection.createStatement();
					for (int i = 0; i < paramList.size(); i++) {
						String deleteDiagnose = Diagnose.deleteByMedicalRecordIdSQL
								.replace(":ids", paramList.get(i));
						String deleteICU = ICU.deleteByMedicalRecordIdSQL
								.replace(":ids", paramList.get(i));
						String deleteBirthDefect = BirthDefect.deleteByMedicalRecordIdSQL
								.replace(":ids", paramList.get(i));
						String deleteOperation = Operation.deleteByMedicalRecordIdSQL
								.replace(":ids", paramList.get(i));
						String deleteSurgery = Surgery.deleteByMedicalRecordIdSQL
								.replace(":ids", paramList.get(i));

						stmt.addBatch(deleteDiagnose);
						logger.debug(deleteDiagnose);
						stmt.addBatch(deleteICU);
						logger.debug(deleteICU);
						stmt.addBatch(deleteBirthDefect);
						logger.debug(deleteBirthDefect);
						// 必须先删除操作，再删除手术
						stmt.addBatch(deleteOperation);
						logger.debug(deleteOperation);
						stmt.addBatch(deleteSurgery);
						logger.debug(deleteSurgery);

						// 每BATCH_SIZE执行一次
						if ((i + 1) % BATCH_SIZE == 0
								|| i == paramList.size() - 1) {
							stmt.executeBatch();
							stmt.clearBatch();
						}
					}
				} finally {
					JdbcUtils.closeStatement(stmt);
				}
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public void readAll(MedicalRecordParam param,
			EntityReader<MedicalRecord> reader) {
		final int batchSize = 64;
		Long gtId = Long.MIN_VALUE;

		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);

		List<MedicalRecord> list = Collections.EMPTY_LIST;

		param.setSort(null);
		param.setOrder(null);
		StringBuilder hql = new StringBuilder();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		this.buildQueryHql(param, hql, paramMap);
		// 防止导出过程中数据有变动而做的处理
		hql.append(" and obj.id > :gt_id order by obj.id asc");

		do {
			paramMap.put("gt_id", gtId);
			Query query = session.createQuery(hql.toString());
			query.setCacheable(false);
			DaoUtils.applyParametersToQuery(query, paramMap);
			query.setFirstResult(0);
			query.setMaxResults(batchSize);
			list = query.list();

			// 将gtId设为查出的记录的最大的id,因为已经排过序了，所以可以直接取；
			gtId = list.get(list.size() - 1).getId();

			for (MedicalRecord entity : list) {
				reader.read(entity);
			}
			session.clear();
		} while (list.size() == batchSize);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> listOrgCode(MedicalRecordParam param) {
		StringBuilder hql = new StringBuilder();
		hql.append("select distinct obj.ZA02C ");

		Map<String, Object> paramMap = new HashMap<String, Object>();
		this.buildQueryHql(param, hql, paramMap);

		Query query = getCurrentSession().createQuery(hql.toString());
		DaoUtils.applyParametersToQuery(query, paramMap);
		return query.list();
	}
}
