package org.bjdrgs.bjwt.authority.parameter;

import org.bjdrgs.bjwt.core.web.GridParam;

public class OrgParam extends GridParam {
	//tree grid
	private Integer id;
	
	private String keyword;
	/**
	 * combogrid
	 */
	private String q;

	/**
	 * @return the keyword
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * @param keyword
	 *            the keyword to set
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
