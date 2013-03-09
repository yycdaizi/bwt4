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
	
	public static String parsePermission(String permission){
		if("1".equals(permission)){
			return "启用";
		}else if("0".equals(permission)){
			return "锁定";
		}
		return "其他";
	}
}
