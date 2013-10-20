package org.bjdrgs.bjwt.common.parameter;

import org.bjdrgs.bjwt.core.web.GridParam;

public class PersonParam extends GridParam {

	private Integer orgId;
	private String eq_type;
	private String blike_name;

	/**
	 * @return the orgId
	 */
	public Integer getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	/**
	 * @return the eq_type
	 */
	public String getEq_type() {
		return eq_type;
	}

	/**
	 * @param eq_type the eq_type to set
	 */
	public void setEq_type(String eq_type) {
		this.eq_type = eq_type;
	}

	/**
	 * @return the blike_name
	 */
	public String getBlike_name() {
		return blike_name;
	}

	/**
	 * @param blike_name the blike_name to set
	 */
	public void setBlike_name(String blike_name) {
		this.blike_name = blike_name;
	}
}
