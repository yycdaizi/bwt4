package org.bjdrgs.bjwt.wt4.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.bjdrgs.bjwt.core.format.DateTimeDeserializer;
import org.bjdrgs.bjwt.core.format.DateTimeSerializer;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;
/**
 * 重症监护情况
 * @author ying
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@Entity
@Table(name="b_wt4_icu")
public class ICU implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String ROOT_NAME = "AEK";
	public static final String deleteByMedicalRecordIdSQL = "delete from b_wt4_icu where b_wt4_id=?";
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "increment")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "b_wt4_icu_id", nullable = false, unique = true)
	private Long id;
	
	//病案ID
	@Column(name="b_wt4_id")
	private Long medicalRecordId;	

	/** 
	 * 重症监护室代码 
	 */
	@JsonProperty
	@Column(name="UNITCODE")
	private String AEK01C;
	
	/** 
	 * 监护室进入日期时间 
	 */
	@JsonProperty
	@JsonDeserialize(using=DateTimeDeserializer.class)
	@JsonSerialize(using= DateTimeSerializer.class)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="INDATE")
	private Date AEK02;
	
	/** 
	 * 监护室退出日期时间 
	 */
	@JsonProperty
	@JsonDeserialize(using=DateTimeDeserializer.class)
	@JsonSerialize(using= DateTimeSerializer.class)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="OUTDATE")
	private Date AEK03;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the AEK01C
	 */
	@JsonIgnore public String getAEK01C() {
		return AEK01C;
	}

	/**
	 * @param AEK01C the AEK01C to set
	 */
	@JsonIgnore public void setAEK01C(String AEK01C) {
		this.AEK01C = AEK01C;
	}

	/**
	 * @return the AEK02
	 */
	@JsonIgnore public Date getAEK02() {
		return AEK02;
	}

	/**
	 * @param AEK02 the AEK02 to set
	 */
	@JsonIgnore public void setAEK02(Date AEK02) {
		this.AEK02 = AEK02;
	}

	/**
	 * @return the AEK03
	 */
	@JsonIgnore public Date getAEK03() {
		return AEK03;
	}

	/**
	 * @param AEK03 the AEK03 to set
	 */
	@JsonIgnore public void setAEK03(Date AEK03) {
		this.AEK03 = AEK03;
	}

	public Long getMedicalRecordId() {
		return medicalRecordId;
	}

	public void setMedicalRecordId(Long medicalRecordId) {
		this.medicalRecordId = medicalRecordId;
	}
}
