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
 * 新生儿缺陷
 * @author ying
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@Entity
@Table(name="b_wt4_disabled")
public class BirthDefect implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "native")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "b_wt4_disabled_id", nullable = false, unique = true)
	private Long id;
	
	//病案ID
	@Column(name="b_wt4_id")
	private Long medicalRecordId;

	/** 
	 * 新生儿出生体重(克)
	 */
	@JsonProperty
	@Column(name="SF0101")
	private Integer AEN01;
	
	/** 
	 * 新生儿出生缺陷诊断编码(ICD-10)
	 */
	@JsonProperty
	@Column(name="DISABLED_DIAGNOSE_CODE")
	private String AEN02C;
	
	/** 
	 * 新生儿出生缺陷诊断名称
	 */
	@JsonProperty
	@Column(name="DISABLED_DIAGNOSE_NAME")
	private String AEN02N;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore public Integer getAEN01() {
		return AEN01;
	}

	@JsonIgnore public void setAEN01(Integer aEN01) {
		AEN01 = aEN01;
	}

	@JsonIgnore public String getAEN02C() {
		return AEN02C;
	}

	@JsonIgnore public void setAEN02C(String aEN02C) {
		AEN02C = aEN02C;
	}

	@JsonIgnore public String getAEN02N() {
		return AEN02N;
	}

	@JsonIgnore public void setAEN02N(String aEN02N) {
		AEN02N = aEN02N;
	}

	@JsonIgnore public Long getMedicalRecordId() {
		return medicalRecordId;
	}

	@JsonIgnore public void setMedicalRecordId(Long medicalRecordId) {
		this.medicalRecordId = medicalRecordId;
	}
}
