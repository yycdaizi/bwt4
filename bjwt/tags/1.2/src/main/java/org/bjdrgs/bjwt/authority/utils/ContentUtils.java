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
	/**
	 * 校验两个字符串是否相等
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean checkStringEqual(String str1,String str2){
		if(str1==str2){
			return true;
		}
		if(str1!=null){
			return str1.equals(str2);
		}
		if(str2!=null){
			return str2.equals(str1);
		}
		return false;
	}
}
