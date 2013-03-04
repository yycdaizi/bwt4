package org.bjdrgs.bjwt.authority.utils;

public final class ContentUtils {
	public static String parseSexName(String sexId) {
		if (sexId == null)
			return null;
		if ("0".equals(sexId)) {
			return "未知";
		} else if ("1".equals(sexId)) {
			return "男";
		} else if ("2".equals(sexId)) {
			return "女";
		}
		return null;
	}
}
