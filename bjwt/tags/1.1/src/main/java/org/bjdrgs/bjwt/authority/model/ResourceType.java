package org.bjdrgs.bjwt.authority.model;

/**
 * 权限中的资源类型
 * @author zhouwb
 *
 */
public enum ResourceType {
	MENU("menu")
	;
	private String resType;
	private ResourceType(String resType){
		this.resType = resType;
	}
	@Override
	public String toString() {
		return resType;
	}
}
