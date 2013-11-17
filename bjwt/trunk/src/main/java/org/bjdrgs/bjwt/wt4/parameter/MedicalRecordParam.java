package org.bjdrgs.bjwt.wt4.parameter;

import java.util.Date;

import org.bjdrgs.bjwt.core.web.GridParam;
import org.springframework.format.annotation.DateTimeFormat;

public class MedicalRecordParam extends GridParam{
	//病案号
	private String blike_AAA28;
	//姓名
	private String blike_AAA01;
	//科室
	private String eq_AAB02C;
	//出院日期
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date le_AAC01;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date ge_AAC01;
	
	//离院方式
	private String eq_AEM01C;
	//病案状态
	private String eq_state;
	
	private String eq_mdc;
	private String eq_drg;

	private Integer orgId;
	
	public String getBlike_AAA28() {
		return blike_AAA28;
	}

	public void setBlike_AAA28(String blike_AAA28) {
		this.blike_AAA28 = blike_AAA28;
	}

	public String getBlike_AAA01() {
		return blike_AAA01;
	}

	public void setBlike_AAA01(String blike_AAA01) {
		this.blike_AAA01 = blike_AAA01;
	}

	public String getEq_AAB02C() {
		return eq_AAB02C;
	}

	public void setEq_AAB02C(String eq_AAB02C) {
		this.eq_AAB02C = eq_AAB02C;
	}

	public Date getLe_AAC01() {
		return le_AAC01;
	}

	public void setLe_AAC01(Date le_AAC01) {
		this.le_AAC01 = le_AAC01;
	}

	public Date getGe_AAC01() {
		return ge_AAC01;
	}

	public void setGe_AAC01(Date ge_AAC01) {
		this.ge_AAC01 = ge_AAC01;
	}

	public String getEq_AEM01C() {
		return eq_AEM01C;
	}

	public void setEq_AEM01C(String eq_AEM01C) {
		this.eq_AEM01C = eq_AEM01C;
	}

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
	 * @return the eq_state
	 */
	public String getEq_state() {
		return eq_state;
	}

	/**
	 * @param eq_state the eq_state to set
	 */
	public void setEq_state(String eq_state) {
		this.eq_state = eq_state;
	}

	/**
	 * @return the eq_mdc
	 */
	public String getEq_mdc() {
		return eq_mdc;
	}

	/**
	 * @param eq_mdc the eq_mdc to set
	 */
	public void setEq_mdc(String eq_mdc) {
		this.eq_mdc = eq_mdc;
	}

	/**
	 * @return the eq_drg
	 */
	public String getEq_drg() {
		return eq_drg;
	}

	/**
	 * @param eq_drg the eq_drg to set
	 */
	public void setEq_drg(String eq_drg) {
		this.eq_drg = eq_drg;
	}
}
