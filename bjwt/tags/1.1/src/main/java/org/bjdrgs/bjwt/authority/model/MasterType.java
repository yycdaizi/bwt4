package org.bjdrgs.bjwt.authority.model;

/**
 * 权限主体的类型
 * @author zhouwb
 *
 */
public enum MasterType {
	ROLE("role"),
	USER("user")
	;
	private String typeName;
	private MasterType(String typeName){
		this.typeName = typeName;
	}
	@Override
	public String toString() {
		return typeName;
	}
}
