package org.bjdrgs.bjwt.core.dao;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.bjdrgs.bjwt.core.exception.ApplicationException;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.type.Type;

/**
 * DAO工具类
 * @author Tim Lu
 * @date 2011-08-22
 * 
 */
public abstract class DaoUtils {

	/**
	 * @param query
	 * @param parameters
	 */
	public static void applyMapParameterToQuery(Query query, Map<String, Object> parameters) {

		Iterator<String> keyIterator = parameters.keySet().iterator();
		while (keyIterator.hasNext()) {
			String key = keyIterator.next();
			Object value = parameters.get(key);
			applyNamedParameterToQuery(query, key, value, null);
		}
	}

	/**
	 * @param query
	 * @param paramName
	 * @param value
	 * @param type
	 * @throws HibernateException
	 */
	public static void applyNamedParameterToQuery(Query query,
			String paramName, Object value, Type type)
			throws HibernateException {

		if (value instanceof Collection<?>) {
			if (type != null) {
				query.setParameterList(paramName, (Collection<?>) value, type);
			} else {
				query.setParameterList(paramName, (Collection<?>) value);
			}
		} else if (value instanceof Object[]) {
			if (type != null) {
				query.setParameterList(paramName, (Object[]) value, type);
			} else {
				query.setParameterList(paramName, (Object[]) value);
			}
		} else {
			if (type != null) {
				query.setParameter(paramName, value, type);
			} else {
				query.setParameter(paramName, value);
			}
		}
	}

	public static String formatToMapHql(String hql, boolean mapFlag) {
		String result = hql.trim();

		if (mapFlag) {
			if (mapFlag && !result.toLowerCase().startsWith("select")) {
				throw new ApplicationException(
						"to return map result mast use \"select\" to specify column");
			} else if (mapFlag) {
				String newHql = hql.trim();

				newHql = newHql.substring("select".length());
				newHql = "select new map(" + newHql;
				int idx = newHql.indexOf("from");
				result = newHql.substring(0, idx) + ") "
						+ newHql.substring(idx);
			}
		}

		return result;
	}
}
