package org.bjdrgs.bjwt.wt4.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.annotations.GenericGenerator;

/**
 * 出院时其他诊断信息
 * @author ying
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@Entity
@Table(name="b_wt4_diagnose")
public class Diagnose implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "native")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "b_wt4_diagnose_id", nullable = false, unique = true)
	private Long id;
	
	//病案ID
	@Column(name="b_wt4_id")
	private Long medicalRecordId;

	/** 
	 * 出院时其他诊断编码(ICD-10) 
	 */
	@JsonProperty
	@Column(name="DIAGNOSE_CODE")
	private String ABD01C;
	
	/** 
	 * 出院其他诊断名称 
	 */
	@JsonProperty
	@Column(name="DIAGNOSE_NAME")
	private String ABD01N;
	
	/** 
	 * 入院病情 
	 */
	@JsonProperty
	@Column(name="IN_STATUS")
	private String ABD03C;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore public String getABD01C() {
		return ABD01C;
	}

	@JsonIgnore public void setABD01C(String aBD01C) {
		ABD01C = aBD01C;
	}

	@JsonIgnore public String getABD01N() {
		return ABD01N;
	}

	@JsonIgnore public void setABD01N(String aBD01N) {
		ABD01N = aBD01N;
	}

	@JsonIgnore public String getABD03C() {
		return ABD03C;
	}

	@JsonIgnore public void setABD03C(String aBD03C) {
		ABD03C = aBD03C;
	}

	public Long getMedicalRecordId() {
		return medicalRecordId;
	}

	public void setMedicalRecordId(Long medicalRecordId) {
		this.medicalRecordId = medicalRecordId;
	}
}
