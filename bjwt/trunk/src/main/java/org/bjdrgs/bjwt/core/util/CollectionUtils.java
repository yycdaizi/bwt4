package org.bjdrgs.bjwt.core.util;

import java.util.Collection;

public class CollectionUtils {
	private static final String DEFAULT_JOIN_SEPARATOR = ",";

	public static String join(Object[] array) {
		return join(array, DEFAULT_JOIN_SEPARATOR);
	}

	public static String join(Object[] array, String separator) {
		if (array == null || array.length == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < array.length - 1; i++) {
			sb.append(array[i]).append(separator);
		}
		sb.append(array[array.length - 1]);
		return sb.toString();
	}

	public static String join(Collection<?> collection) {
		return join(collection, DEFAULT_JOIN_SEPARATOR);
	}

	public static String join(Collection<?> collection, String separator) {
		if (collection == null || collection.size() == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (Object object : collection) {
			sb.append(object).append(separator);
		}
		sb.delete(sb.length() - separator.length(), sb.length());
		return sb.toString();
	}
}
