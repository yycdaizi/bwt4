package org.bjdrgs.bjwt.authority.parameter;

import org.bjdrgs.bjwt.core.web.GridParam;

public class MenuParam extends GridParam {
	
	/**
	 * 指定查询all
	 */
	private String query_all;
	
	private String keyword;
	
	/**
	 * TreeGird ID
	 */
	private Integer id;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getQuery_all() {
		return query_all;
	}

	public void setQuery_all(String query_all) {
		this.query_all = query_all;
	}
	
}
