package org.bjdrgs.bjwt.wt4.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
 * 手术信息
 * @author ying
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@Entity
@Table(name="b_wt4_surgery")
public class Surgery implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "native")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "b_wt4_surgery_id", nullable = false, unique = true)
	private Long id;
	
	//病案ID
	@Column(name="b_wt4_id")
	private Long medicalRecordId;
	
	@OneToMany(mappedBy="surgeryId", fetch=FetchType.EAGER)
	//@JoinColumn(name="b_wt4_surgery_id")
	@JsonProperty private List<Operation> ACA09S = new ArrayList<Operation>();

	/** 
	 * 手术日期时间（开始） 
	 */
	@JsonDeserialize(using=DateTimeDeserializer.class)
	@JsonSerialize(using= DateTimeSerializer.class)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="OPERATION_DATE")
	@JsonProperty private Date ACA01;
	
	/** 
	 * 手术日期时间（完成） 
	 */
	@JsonDeserialize(using=DateTimeDeserializer.class)
	@JsonSerialize(using= DateTimeSerializer.class)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="OPERATION_EDATE")
	@JsonProperty private Date ACA11;
	
	/** 
	 * 术者（手术操作医师姓名） 
	 */
	@Column(name="OPERATION_DOCTOR")
	@JsonProperty private String ACA02;
	
	/** 
	 * Ⅰ助姓名 
	 */
	@Column(name="FIRST_ASSISTANT")
	@JsonProperty private String ACA03;
	
	/** 
	 * Ⅱ助姓名 
	 */
	@Column(name="SECOND_ASSISTANT")
	@JsonProperty private String ACA04;
	
	/** 
	 * 麻醉方式代码
	 */
	@Column(name="HOCUS")
	@JsonProperty private String ACA06C;
	
	/** 
	 * 切口愈合等级代码 
	 */
	@Column(name="CLOSEUP")
	@JsonProperty private String ACA07C;
	
	/** 
	 * 麻醉医师姓名 
	 */
	@Column(name="HOCUS_DOCTOR")
	@JsonProperty private String ACA08;
	
	/** 
	 * 手术级别代码 
	 */
	@Column(name="OPERATION_LEVEL")
	@JsonProperty private String ACA10C;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore public List<Operation> getACA09S() {
		return ACA09S;
	}

	@JsonIgnore public void setACA09S(List<Operation> aCA09S) {
		ACA09S = aCA09S;
	}

	@JsonIgnore public Date getACA01() {
		return ACA01;
	}

	@JsonIgnore public void setACA01(Date aCA01) {
		ACA01 = aCA01;
	}

	@JsonIgnore public Date getACA11() {
		return ACA11;
	}

	@JsonIgnore public void setACA11(Date aCA11) {
		ACA11 = aCA11;
	}

	@JsonIgnore public String getACA02() {
		return ACA02;
	}

	@JsonIgnore public void setACA02(String aCA02) {
		ACA02 = aCA02;
	}

	@JsonIgnore public String getACA03() {
		return ACA03;
	}

	@JsonIgnore public void setACA03(String aCA03) {
		ACA03 = aCA03;
	}

	@JsonIgnore public String getACA04() {
		return ACA04;
	}

	@JsonIgnore public void setACA04(String aCA04) {
		ACA04 = aCA04;
	}

	@JsonIgnore public String getACA06C() {
		return ACA06C;
	}

	@JsonIgnore public void setACA06C(String aCA06C) {
		ACA06C = aCA06C;
	}

	@JsonIgnore public String getACA07C() {
		return ACA07C;
	}

	@JsonIgnore public void setACA07C(String aCA07C) {
		ACA07C = aCA07C;
	}

	@JsonIgnore public String getACA08() {
		return ACA08;
	}

	@JsonIgnore public void setACA08(String aCA08) {
		ACA08 = aCA08;
	}

	@JsonIgnore public String getACA10C() {
		return ACA10C;
	}

	@JsonIgnore public void setACA10C(String aCA10C) {
		ACA10C = aCA10C;
	}

	public Long getMedicalRecordId() {
		return medicalRecordId;
	}

	public void setMedicalRecordId(Long medicalRecordId) {
		this.medicalRecordId = medicalRecordId;
	}
	
}
