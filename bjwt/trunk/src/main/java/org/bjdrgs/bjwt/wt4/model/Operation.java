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
 * 术式信息
 * @author ying
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@Entity
@Table(name="b_wt4_operation")
public class Operation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "native")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "b_wt4_operation_id", nullable = false, unique = true)
	private Long id;
	
	//@ManyToOne
	//@JoinColumn(name="b_wt4_surgery_id")
	@Column(name="b_wt4_surgery_id")
	private Long surgeryId;

	/** 
	 * 手术及操作编码(ICD-9-CM3) 
	 */
	@Column(name="OPERATION_CODE")
	@JsonProperty private  String ACA0901C;
	
	/** 
	 * 手术及操作名称 
	 */
	@Column(name="OPERATION_NAME")
	@JsonProperty private  String ACA0901N;
	
	/** 
	 * 是否主要术式 
	 */
	@Column(name="ISSHUSHI")
	@JsonProperty private  String ACA0902C;
	
	/** 
	 * 是否主要手术或操作 
	 */
	@Column(name="ISPRIMARY")
	@JsonProperty private  String ACA0903C;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore public String getACA0901C() {
		return ACA0901C;
	}

	@JsonIgnore public void setACA0901C(String aCA0901C) {
		ACA0901C = aCA0901C;
	}

	@JsonIgnore public String getACA0901N() {
		return ACA0901N;
	}

	@JsonIgnore public void setACA0901N(String aCA0901N) {
		ACA0901N = aCA0901N;
	}

	@JsonIgnore public String getACA0902C() {
		return ACA0902C;
	}

	@JsonIgnore public void setACA0902C(String aCA0902C) {
		ACA0902C = aCA0902C;
	}

	@JsonIgnore public String getACA0903C() {
		return ACA0903C;
	}

	@JsonIgnore public void setACA0903C(String aCA0903C) {
		ACA0903C = aCA0903C;
	}

	public Long getSurgeryId() {
		return surgeryId;
	}

	public void setSurgeryId(Long surgeryId) {
		this.surgeryId = surgeryId;
	}
}
