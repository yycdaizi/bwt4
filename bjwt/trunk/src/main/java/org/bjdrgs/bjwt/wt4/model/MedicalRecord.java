package org.bjdrgs.bjwt.wt4.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.bjdrgs.bjwt.authority.model.User;
import org.bjdrgs.bjwt.core.format.DateDeserializer;
import org.bjdrgs.bjwt.core.format.DateSerializer;
import org.bjdrgs.bjwt.core.format.DateTimeDeserializer;
import org.bjdrgs.bjwt.core.format.DateTimeSerializer;
import org.bjdrgs.bjwt.core.validation.constraints.GreaterThan;
import org.bjdrgs.bjwt.core.validation.constraints.LessThan;
import org.bjdrgs.bjwt.wt4.validation.AgeFitBirthday;
import org.bjdrgs.bjwt.wt4.validation.CheckAAC01;
import org.bjdrgs.bjwt.wt4.validation.CheckExpenseSum;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

/**
 * 病案
 * @author ying
 *
 */
@AgeFitBirthday
@CheckAAC01
@CheckExpenseSum
@JsonIgnoreProperties(ignoreUnknown=true)
@Entity
@Table(name="b_wt4")
public class MedicalRecord implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String ROOT_NAME = "CASE";
	
	public static final String STATE_DRAFT = "0";//草稿
	public static final String STATE_COMPLETE = "1";//完成
	public static final String STATE_UNVALIDATED = "2";//未验证
	public static final String STATE_VALIDATED_FAIL = "3";//验证失败
	
	//是否可编辑
	@Transient
	private boolean editable;
	
	@Column(name = "state")
	private String state;
	
	@Column(name = "mdc")
	private String mdc;
	
	@Column(name = "drg")
	private String drg;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "increment")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "b_wt4_id", nullable = false, unique = true)
	private Long id;

	@JsonProperty
	@BatchSize(size=16)
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="medicalRecordId")
	//@JoinColumn(name="b_wt4_id")
	private List<Diagnose> ABDS = new ArrayList<Diagnose>();

	@JsonProperty
	@BatchSize(size=16)
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="medicalRecordId")
	//@JoinColumn(name="b_wt4_id")
	private List<BirthDefect> AENS = new ArrayList<BirthDefect>();

	@JsonProperty
	@BatchSize(size=16)
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="medicalRecordId")
	//@JoinColumn(name="b_wt4_id")
	private List<Surgery> ACAS = new ArrayList<Surgery>();

	@JsonProperty
	@BatchSize(size=16)
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy="medicalRecordId")
	//@JoinColumn(name="b_wt4_id")
	private List<ICU> AEKS = new ArrayList<ICU>();
	
	@JsonDeserialize(using=DateTimeDeserializer.class)
	@JsonSerialize(using= DateTimeSerializer.class)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created")
	private Date createTime;
	
	@ManyToOne
	@JoinColumn(name = "createdby")
	private User createdBy;
	
	@JsonDeserialize(using=DateTimeDeserializer.class)
	@JsonSerialize(using= DateTimeSerializer.class)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated")
	private Date updateTime;
	
	@ManyToOne
	@JoinColumn(name = "updatedby")
	private User updatedBy;

	/** 行政区划代码 */
	@Column(name = "ZONE_CODE")
	@JsonProperty private String ZA01C;
	
	/** 组织机构代码 */
	@Column(name = "ORG_CODE")
	@JsonProperty private String ZA02C;
	
	/** 机构名称 */
	@Column(name = "ORG_NAME")
	@JsonProperty private String ZA03;
	
	/** 单位负责人 */
	@Column(name = "CHIEF")
	@JsonProperty private String ZA04;
	
	//填报信息ZB都可以不要，导出的时候再生成==============
	/** 报表代码 */
	// @Column(name = "text")
	// @Transient
	// @JsonProperty private String ZB01C;
	
	/** 数据年份 */
	// @Column(name = "YEAR_TIME")
	// @JsonProperty private Integer ZB02;
	
	/** 数据月份 */
	// @Column(name = "MONTH_TIME")
	// @JsonProperty private Integer ZB03;
	
	/** 填报人 */
	// @Column(name = "TYPER")
	// @JsonProperty private String ZB04;
	
	/** 填报人联系电话 */
	// @Column(name = "TYPER_TEL")
	// @JsonProperty private String ZB05;
	
	/** 填报日期 */
	// @JsonDeserialize(using=DateTimeDeserializer.class)
	// @JsonSerialize(using= DateTimeSerializer.class)
	// @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	// @Temporal(TemporalType.TIMESTAMP)
	// @Column(name = "DATE_SUBMIT")
	// @JsonProperty private Date ZB06;
	
	/** 记录数 */
	// @Column(name = "text")
	// @Transient
	// @JsonProperty private Integer ZB07;
	
	/** 邮箱 */
	// @Column(name = "text")
	// @Transient
	// @JsonProperty private String ZB08;
	
	/** 手机 */
	// @Column(name = "text")
	// @Transient
	// @JsonProperty private String ZB09;
	
	//病案信息开始=================================
	/** 姓名 */
	@NotBlank
	@Column(name = "NAME_PATIENT")
	@JsonProperty private String AAA01;
	
	/** 性别代码 */
	@NotNull
	@Column(name = "GENDER")
	@JsonProperty private Integer AAA02C;
	
	/** 出生日期 */
	@JsonDeserialize(using=DateDeserializer.class)
	@JsonSerialize(using= DateSerializer.class)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name = "BIRTHDAY")
	@JsonProperty private Date AAA03;
	
	/** 年龄（岁） */
	@NotNull
	@Range(min=0, max=150)
	@Column(name = "AGE")
	@JsonProperty private Integer AAA04;
	
	/** 国籍代码 */
	@Column(name = "NATION")
	@JsonProperty private String AAA05C;
	
	/** 年龄不足1周岁天数 */
	@LessThan(365)
	@GreaterThan(0)
	@Column(name = "SF0100")
	@JsonProperty private Integer AAA40;
	
	/** 新生儿入院体重(克) */
	@Column(name = "SF0102")
	@JsonProperty private Integer AAA42;
	
	/** 民族代码 */
	@Column(name = "NATIONALITY")
	@JsonProperty private String AAA06C;
	
	/** 身份证号 */
	@Column(name = "RESIDENT_ID")
	@JsonProperty private String AAA07;
	
	/** 婚姻状况代码 */
	@Column(name = "MARRAGE")
	@JsonProperty private Integer AAA08C;
	
	/** 出生地省（区、市） */
	@Column(name = "VAR2")
	@JsonProperty private String AAA09;
	
	/** 出生地市 */
	@Column(name = "ADDR_BIRTH_CITY")
	@JsonProperty private String AAA10;
	
	/** 出生地县 */
	@Column(name = "ADDR_BIRTH_COUNTRY")
	@JsonProperty private String AAA11;
	
	/** 籍贯省（区、市） */
	@Column(name = "ADDR_ORIGINAL_PROVINCE")
	@JsonProperty private String AAA43;
	
	/** 籍贯市 */
	@Column(name = "ADDR_ORIGINAL_CITY")
	@JsonProperty private String AAA44;
	
	/** 户籍省（区、市） */
	@Column(name = "ADDR_HUKOU_PROVINCE")
	@JsonProperty private String AAA45;
	
	/** 户籍市 */
	@Column(name = "ADDR_HUKOU_CITY")
	@JsonProperty private String AAA46;
	
	/** 户籍县 */
	@Column(name = "ADDR_HUKOU_COUNTRY")
	@JsonProperty private String AAA47;
	
	/** 户籍地址区县编码 */
	@Column(name = "HUKOU_ZONE_CODE")
	@JsonProperty private String AAA13C;
	
	/** 户籍街道乡镇代码 */
	@Column(name = "STREETCODES")
	@JsonProperty private String AAA33C;
	
	/** 户籍地址邮政编码 */
	@Column(name = "HUKOU_ADDRESS_POSTCODE")
	@JsonProperty private String AAA14C;
	
	/** 现住址省（区、市）（居住半年以上） */
	@Column(name = "ADDR_PRESENT_PROVINCE")
	@JsonProperty private String AAA48;
	
	/** 现住址市 */
	@Column(name = "ADDR_PRESENT_CITY")
	@JsonProperty private String AAA49;
	
	/** 现住址县 */
	@Column(name = "ADDR_PRESENT_COUNTRY")
	@JsonProperty private String AAA50;
	
	/** 现住址区县编码（居住半年以上） */
	@Column(name = "ADDR_ID")
	@JsonProperty private String AAA16C;
	
	/** 现住址街道乡镇代码（居住半年以上） */
	@Column(name = "STREETCODES2")
	@JsonProperty private String AAA36C;
	
	/** 现住址电话 */
	@Column(name = "ADDR_PRESENT_TELNUMBER")
	@JsonProperty private String AAA51;
	
	/** 现住址邮政编码（居住半年以上） */
	@Column(name = "VAR1")
	@JsonProperty private String AAA17C;
	
	/** 职业代码 */
	@Column(name = "PROFESSION_CODE")
	@JsonProperty private String AAA18C;
	
	/** 工作单位及地址 */
	@Column(name = "COMPANY_ADDRESS")
	@JsonProperty private String AAA19;
	
	/** 工作单位电话 */
	@Column(name = "TELENUMBER")
	@JsonProperty private String AAA20;
	
	/** 工作单位邮政编码 */
	@Column(name = "POSTCODE")
	@JsonProperty private String AAA21C;
	
	/** 联系人姓名 */
	@Column(name = "CONTACT_PERSON")
	@JsonProperty private String AAA22;
	
	/** 联系人关系代码 */
	@Column(name = "RELATIONSHIP")
	@JsonProperty private String AAA23C;
	
	/** 联系人地址 */
	@Column(name = "CONTACT_ADDRESS")
	@JsonProperty private String AAA24;
	
	/** 联系人电话 */
	@Column(name = "CONTACT_TELE")
	@JsonProperty private String AAA25;
	
	/** 医疗付费方式代码 */
	@NotNull
	@Column(name = "PAYMENT_METHODS")
	@JsonProperty private Integer AAA26C;
	
	/** 医疗保险手册(卡)号(健康卡号) */
	@Column(name = "INSURANCE_CODE")
	@JsonProperty private String AAA27;
	
	/** 病案号 */
	@NotBlank
	@Column(name = "CASE_ID")
	@JsonProperty private String AAA28;
	
	/** 住院次数 */
	@Column(name = "INHOUSE_TREATMENT")
	@JsonProperty private Integer AAA29;
	
	/** 入院时间（时） */
	@NotNull
	@JsonDeserialize(using=DateTimeDeserializer.class)
	@JsonSerialize(using= DateTimeSerializer.class)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_INHOSPITAL")
	@JsonProperty private Date AAB01;
	
	/** 入院科别代码 */
	@NotBlank
	@Column(name = "DEPARTMENT_CODE1")
	@JsonProperty private String AAB02C;
	
	/** 入院病房 */
	@Column(name = "ROOM")
	@JsonProperty private String AAB03;
	
	/** 入院途径 */
	@Column(name = "IN_HOSPPATH")
	@JsonProperty private String AAB06C;
	
	/** 出院时间（时） */
	@NotNull
	@JsonDeserialize(using=DateTimeDeserializer.class)
	@JsonSerialize(using= DateTimeSerializer.class)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "OUT_DATE")
	@JsonProperty private Date AAC01;
	
	/** 出院科别代码 */
	@NotBlank
	@Column(name = "DEPARTMENT_CODE2")
	@JsonProperty private String AAC02C;
	
	/** 出院病房 */
	@Column(name = "ROOM1")
	@JsonProperty private String AAC03;
	
	/** 实际住院(天) */
	@Min(0)
	@Column(name = "ACCTUAL_DAYS")
	@JsonProperty private Integer AAC04;
	
	/** 转经科别代码 */
	@Column(name = "EXCHANGE_DEPARTMENT_CODE")
	@JsonProperty private String AAD01C;
	
	/** 门(急)诊诊断编码(ICD-10) */
	@Column(name = "DISEASE_CODE4")
	@JsonProperty private String ABA01C;
	
	/** 门（急）诊诊断名称 */
	@Column(name = "DISEASE_CODE4_DES")
	@JsonProperty private String ABA01N;
	
	/** 出院时主要诊断编码(ICD-10) */
	@NotBlank
	@Column(name = "DISEASE_CODE1")
	@JsonProperty private String ABC01C;
	
	/** 出院主要诊断名称 */
	@NotBlank
	@Column(name = "GOOUT_DIAGNOSE_NAME")
	@JsonProperty private String ABC01N;
	
	/** 入院病情 */
	@Column(name = "IN_STATUS")
	@JsonProperty private String ABC03C;

	/** 病理诊断编码(M码) */
	@Column(name = "DIAGNOSE_THEORY")
	@JsonProperty private String ABF01C;
	
	/** 病理诊断名称 */
	@Column(name = "DIAGNOSE_THEORY_NAME")
	@JsonProperty private String ABF01N;
	
	/** 病理号 */
	@Column(name = "PATHOLOGY_ID")
	@JsonProperty private String ABF04;
	
	/** 最高诊断依据代码 */
	@Column(name = "CAUSE_REASON")
	@JsonProperty private String ABF02C;
	
	/** 分化程度编码 */
	@Column(name = "FH_CODE")
	@JsonProperty private String ABF03C;
	
	/** 损伤和中毒外部原因编码(ICD-10) */
	@Column(name = "DISEASE_CODE3")
	@JsonProperty private String ABG01C;
	
	/** 损伤和中毒外部原因名称 */
	@Column(name = "EXTERNAL_NAME")
	@JsonProperty private String ABG01N;
	
	/** 肿瘤分期是否不详 */
	@Column(name = "IS_STAGING")
	@JsonProperty private String ABH01C;
	
	/** 肿瘤分期 T */
	@Column(name = "STAGING_T")
	@JsonProperty private String ABH0201C;
	
	/** 肿瘤分期 N */
	@Column(name = "STAGING_N")
	@JsonProperty private String ABH0202C;
	
	/** 肿瘤分期 M */
	@Column(name = "STAGING_M")
	@JsonProperty private String ABH0203C;
	
	/** 0～Ⅳ肿瘤分期 */
	@Column(name = "STAGING_SEQ")
	@JsonProperty private String ABH03C;

	/** 有无药物过敏 */
	@Column(name = "IS_MEDICINE_SENSE")
	@JsonProperty private String AEB02C;
	
	/** 过敏药物 */
	@Column(name = "MEDICINE_SENSE_NAME")
	@JsonProperty private String AEB01;
	
	/** 病案质量代码 */
	@Column(name = "CASE_QUALITY")
	@JsonProperty private String AED01C;
	
	/** 质控医师姓名 */
	@Column(name = "QC_DOCTOR")
	@JsonProperty private String AED02;
	
	/** 质控护士姓名 */
	@Column(name = "QC_NURSE")
	@JsonProperty private String AED03;
	
	/** 病案质量检查日期 */
	@JsonDeserialize(using=DateDeserializer.class)
	@JsonSerialize(using= DateSerializer.class)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name = "QC_DATE")
	@JsonProperty private Date AED04;
	
	/** 科主任姓名 */
	@Column(name = "CHERF_DEPARTMENT")
	@JsonProperty private String AEE01;
	
	/** 主(副主)任医师姓名 */
	@Column(name = "PROFESSOR")
	@JsonProperty private String AEE02;
	
	/** 主治医师姓名 */
	@Column(name = "ENGINEER")
	@JsonProperty private String AEE03;
	
	/** 主诊医师执业证书编码 */
	@Column(name = "DDOCTOR_CERTIFICATION")
	@JsonProperty private String AEE11;
	
	/** 主诊医师姓名（主管医师姓名） */
	@Column(name = "DIRECTOR_DOCTOR")
	@JsonProperty private String AEE09;
	
	/** 住院医师姓名 */
	@Column(name = "DOCTOR_NAME")
	@JsonProperty private String AEE04;
	
	/** 进修医师姓名 */
	@Column(name = "INTERN_DOCTOR_NAME")
	@JsonProperty private String AEE05;
	
	/** 实习医师姓名 */
	@Column(name = "INTERN")
	@JsonProperty private String AEE07;
	
	/** 编码员姓名 */
	@Column(name = "CODER")
	@JsonProperty private String AEE08;
	
	/** 责任护士姓名 */
	@Column(name = "DUTY_NURSENAME")
	@JsonProperty private String AEE10;
	
	/** 血型代码 */
	@Column(name = "BLOODTYPE")
	@JsonProperty private Integer AEG01C;
	
	/** Rh 代码 */
	@Column(name = "RH")
	@JsonProperty private Integer AEG02C;
	
	/** 红细胞(单位) */
	@Column(name = "REDCELL")
	@JsonProperty private Integer AEG04;
	
	/** 血小板(袋) */
	@Column(name = "PLAQUE")
	@JsonProperty private Integer AEG05;
	
	/** 血浆(ml) */
	@Column(name = "PLASMA")
	@JsonProperty private Integer AEG06;
	
	/** 全血(ml) */
	@Column(name = "TOTAL_BLOOD")
	@JsonProperty private Integer AEG07;
	
	/** 其它(ml) */
	@Column(name = "OTHERS")
	@JsonProperty private Integer AEG08;
	
	/** 颅脑损伤患者入院前昏迷时间（天） */
	@Column(name = "BEFORE_CEREBRUMHURT_DAYS")
	@JsonProperty private Integer AEJ01;
	
	/** 颅脑损伤患者入院前昏迷时间(小时) */
	@Column(name = "BEFORE_CEREBRUMHURT_HOURS")
	@JsonProperty private Integer AEJ02;
	
	/** 颅脑损伤患者入院前昏迷时间(分钟) */
	@Column(name = "BEFORE_CEREBRUMHURT_MINUTES")
	@JsonProperty private Integer AEJ03;
	
	/** 颅脑损伤患者入院后昏迷时间（天） */
	@Column(name = "AFTER_CEREBRUMHURT_DAYS")
	@JsonProperty private Integer AEJ04;
	
	/** 颅脑损伤患者入院后昏迷时间(小时) */
	@Column(name = "AFTER_CEREBRUMHURT_HOURS")
	@JsonProperty private Integer AEJ05;
	
	/** 颅脑损伤患者入院后昏迷时间(分钟) */
	@Column(name = "AFTER_CEREBRUMHURT_MINUTES")
	@JsonProperty private Integer AEJ06;

	/** 呼吸机使用时间(小时) */
	@Column(name = "SF0104")
	@JsonProperty private Integer AEL01;

	/** 离院方式代码 */
	@NotBlank
	@Column(name = "SF0108")
	@JsonProperty private String AEM01C;
	
	/** 医嘱转院、转社区、卫生院机构名称 */
	@Column(name = "TRANSTOORGNAME")
	@JsonProperty private String AEM02;
	
	/** 是否有出院31日内再住院计划 */
	@Column(name = "IS_INHOSP31")
	@JsonProperty private String AEM03C;
	
	/** 31日内再住院目的 */
	@Column(name = "INHOSP31OBJECT")
	@JsonProperty private String AEM04;
	
	/** 是否尸检代码 */
	@Column(name = "IS_BODYCHECK")
	@JsonProperty private String AEI01C;
	
	/** 日常生活能力评定量得分（入院） */
	@Column(name = "INHOSPSCORE")
	@JsonProperty private Integer AEI09;
	
	/** 日常生活能力评定量得分（出院） */
	@Column(name = "OUTHOSPSCORE")
	@JsonProperty private Integer AEI10;
	
	/** 备注 */
	@Column(name = "MEMO")
	@JsonProperty private String AEI08;
	
	/** 总费用 */
	@NotNull
	@GreaterThan(0)
	@NumberFormat(pattern="#.00")
	@Column(name = "TOTAL_EXPENSE")
	@JsonProperty private BigDecimal ADA01;
	
	/** 自付金额 */
	@Min(0)
	@NumberFormat(pattern="#.00")
	@Column(name = "SELFPAYMENT_EXPENSE")
	@JsonProperty private BigDecimal ADA0101;
	
	/** 诊察（诊疗）费 */
	@Min(0)
	@NumberFormat(pattern="#.00")
	@Column(name = "EXAMINE_EXPENSE")
	@JsonProperty private BigDecimal ADA11;
	
	/** 一般检查费 */
	@Min(0)
	@NumberFormat(pattern="#.00")
	@Column(name = "CHECK_NORMAL_EXPENSE")
	@JsonProperty private BigDecimal ADA21;
	
	/** 临床物理治疗费 */
	@Min(0)
	@NumberFormat(pattern="#.00")
	@Column(name = "CURE_CLINICAL_EXPENSE")
	@JsonProperty private BigDecimal ADA22;
	
	/** 介入治疗费 */
	@Min(0)
	@NumberFormat(pattern="#.00")
	@Column(name = "CURE_INTRUDE_EXPENSE")
	@JsonProperty private BigDecimal ADA23;
	
	/** 特殊治疗费 */
	@Min(0)
	@NumberFormat(pattern="#.00")
	@Column(name = "CURE_SPECIAL_EXPENSE")
	@JsonProperty private BigDecimal ADA24;
	
	/** 康复治疗费 */
	@Min(0)
	@NumberFormat(pattern="#.00")
	@Column(name = "CURE_RECOVER_EXPENSE")
	@JsonProperty private BigDecimal ADA25;
	
	/** 中医治疗费 */
	@Min(0)
	@NumberFormat(pattern="#.00")
	@Column(name = "CURE_CHINESE_EXPENSE")
	@JsonProperty private BigDecimal ADA26;
	
	/** 一般治疗费 */
	@Min(0)
	@NumberFormat(pattern="#.00")
	@Column(name = "CURE_NORMAL_EXPENSE")
	@JsonProperty private BigDecimal ADA27;
	
	/** 精神治疗费 */
	@Min(0)
	@NumberFormat(pattern="#.00")
	@Column(name = "CURE_MIND_EXPENSE")
	@JsonProperty private BigDecimal ADA28;
	
	/** 接生费 */
	@Min(0)
	@NumberFormat(pattern="#.00")
	@Column(name = "DELIVER_EXPENSE")
	@JsonProperty private BigDecimal ADA13;
	
	/** 麻醉费 */
	@Min(0)
	@NumberFormat(pattern="#.00")
	@Column(name = "HORUS_EXPENSE")
	@JsonProperty private BigDecimal ADA15;
	
	/** 手术费 */
	@Min(0)
	@NumberFormat(pattern="#.00")
	@Column(name = "OPERATION_EXPENSE")
	@JsonProperty private BigDecimal ADA12;
	
	/** 护理治疗费 */
	@Min(0)
	@NumberFormat(pattern="#.00")
	@Column(name = "CURE_TEND_EXPENSE")
	@JsonProperty private BigDecimal ADA29;
	
	/** 护理费 */
	@Min(0)
	@NumberFormat(pattern="#.00")
	@Column(name = "BED_TEND_EXPENSE")
	@JsonProperty private BigDecimal ADA03;
	
	/** 核素检查 */
	@Min(0)
	@NumberFormat(pattern="#.00")
	@Column(name = "CHECK_NUCLEUS_EXPENSE")
	@JsonProperty private BigDecimal ADA30;
	
	/** 核素治疗 */
	@Min(0)
	@NumberFormat(pattern="#.00")
	@Column(name = "CURE_NUCLEUS_EXPENSE")
	@JsonProperty private BigDecimal ADA31;
	
	/** 超声费 */
	@Min(0)
	@NumberFormat(pattern="#.00")
	@Column(name = "ULTRASOUND_EXPENSE")
	@JsonProperty private BigDecimal ADA32;
	
	/** 放射费 */
	@Min(0)
	@NumberFormat(pattern="#.00")
	@Column(name = "RADIATE_EXPENSE")
	@JsonProperty private BigDecimal ADA07;
	
	/** 化验费 */
	@Min(0)
	@NumberFormat(pattern="#.00")
	@Column(name = "CHECK_EXPENSE")
	@JsonProperty private BigDecimal ADA08;
	
	/** 病理费 */
	@Min(0)
	@NumberFormat(pattern="#.00")
	@Column(name = "PATHOLOGY_EXPENSE")
	@JsonProperty private BigDecimal ADA33;
	
	/** 监护及辅助呼吸费 */
	@Min(0)
	@NumberFormat(pattern="#.00")
	@Column(name = "AUXILIARY_EXPENSE")
	@JsonProperty private BigDecimal ADA34;
	
	/** 治疗用一次性医用材料费 */
	@Min(0)
	@NumberFormat(pattern="#.00")
	@Column(name = "STUFF_CURE_EXPENSE")
	@JsonProperty private BigDecimal ADA35;
	
	/** 介入用一次性医用材料费 */
	@Min(0)
	@NumberFormat(pattern="#.00")
	@Column(name = "STUFF_INTRUDE_EXPENSE")
	@JsonProperty private BigDecimal ADA36;
	
	/** 手术用一次性医用材料费 */
	@Min(0)
	@NumberFormat(pattern="#.00")
	@Column(name = "STUFF_SURGERY_EXPENSE")
	@JsonProperty private BigDecimal ADA37;
	
	/** 检查用一次性医用材料费 */
	@Min(0)
	@NumberFormat(pattern="#.00")
	@Column(name = "STUFF_CHECK_EXPENSE")
	@JsonProperty private BigDecimal ADA38;
	
	/** 床位费 */
	@Min(0)
	@NumberFormat(pattern="#.00")
	@Column(name = "BED_EXPENSE")
	@JsonProperty private BigDecimal ADA02;
	
	/** 挂号费 */
	@Min(0)
	@NumberFormat(pattern="#.00")
	@Column(name = "REGISTER_EXPENSE")
	@JsonProperty private BigDecimal ADA39;
	
	/** 输氧费 */
	@Min(0)
	@NumberFormat(pattern="#.00")
	@Column(name = "OXYGEN_EXPENSE")
	@JsonProperty private BigDecimal ADA09;
	
	/** 输血费 */
	@Min(0)
	@NumberFormat(pattern="#.00")
	@Column(name = "TRANSFUSION_EXPENSE")
	@JsonProperty private BigDecimal ADA10;
	
	/** 西药费 */
	@Min(0)
	@NumberFormat(pattern="#.00")
	@Column(name = "WESTERN_MEDICINE_EXPENSE")
	@JsonProperty private BigDecimal ADA04;
	
	/** 抗菌药物费 */
	@Min(0)
	@NumberFormat(pattern="#.00")
	@Column(name = "ANTISEPTIC_MEDICINE_EXPENSE")
	@JsonProperty private BigDecimal ADA40;
	
	/** 白蛋白类制品费 */
	@Min(0)
	@NumberFormat(pattern="#.00")
	@Column(name = "PRODUCT_ALBUMIN_EXPENSE")
	@JsonProperty private BigDecimal ADA41;
	
	/** 球蛋白类制品费 */
	@Min(0)
	@NumberFormat(pattern="#.00")
	@Column(name = "PRODUCT_GLOBULIN_EXPENSE")
	@JsonProperty private BigDecimal ADA42;
	
	/** 凝血因子类制品费 */
	@Min(0)
	@NumberFormat(pattern="#.00")
	@Column(name = "PRODUCT_BLOOD_EXPENSE")
	@JsonProperty private BigDecimal ADA43;
	
	/** 细胞因子类制品费 */
	@Min(0)
	@NumberFormat(pattern="#.00")
	@Column(name = "PRODUCT_CELL_EXPENSE")
	@JsonProperty private BigDecimal ADA44;
	
	/** 中成药费 */
	@Min(0)
	@NumberFormat(pattern="#.00")
	@Column(name = "CHINESE_MEDICINE_EXPENSE1")
	@JsonProperty private BigDecimal ADA05;
	
	/** 中草药费 */
	@Min(0)
	@NumberFormat(pattern="#.00")
	@Column(name = "CHINESE_MEDICINE_EXPENSE2")
	@JsonProperty private BigDecimal ADA06;
	
	/** 其他费用 */
	@Min(0)
	@NumberFormat(pattern="#.00")
	@Column(name = "OTHER_EXPENSE")
	@JsonProperty private BigDecimal ADA20;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore public List<Diagnose> getABDS() {
		return ABDS;
	}

	@JsonIgnore public void setABDS(List<Diagnose> aBDS) {
		ABDS = aBDS;
	}

	@JsonIgnore public List<BirthDefect> getAENS() {
		return AENS;
	}

	@JsonIgnore public void setAENS(List<BirthDefect> aENS) {
		AENS = aENS;
	}

	@JsonIgnore public List<Surgery> getACAS() {
		return ACAS;
	}

	@JsonIgnore public void setACAS(List<Surgery> aCAS) {
		ACAS = aCAS;
	}

	@JsonIgnore public List<ICU> getAEKS() {
		return AEKS;
	}

	@JsonIgnore public void setAEKS(List<ICU> aEKS) {
		AEKS = aEKS;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	@JsonIgnore public String getZA01C() {
		return ZA01C;
	}

	@JsonIgnore public void setZA01C(String zA01C) {
		ZA01C = zA01C;
	}

	@JsonIgnore public String getZA02C() {
		return ZA02C;
	}

	@JsonIgnore public void setZA02C(String zA02C) {
		ZA02C = zA02C;
	}

	@JsonIgnore public String getZA03() {
		return ZA03;
	}

	@JsonIgnore public void setZA03(String zA03) {
		ZA03 = zA03;
	}

	@JsonIgnore public String getZA04() {
		return ZA04;
	}

	@JsonIgnore public void setZA04(String zA04) {
		ZA04 = zA04;
	}

	@JsonIgnore public String getAAA01() {
		return AAA01;
	}

	@JsonIgnore public void setAAA01(String aAA01) {
		AAA01 = aAA01;
	}

	@JsonIgnore public Integer getAAA02C() {
		return AAA02C;
	}

	@JsonIgnore public void setAAA02C(Integer aAA02C) {
		AAA02C = aAA02C;
	}

	@JsonIgnore public Date getAAA03() {
		return AAA03;
	}

	@JsonIgnore public void setAAA03(Date aAA03) {
		AAA03 = aAA03;
	}

	@JsonIgnore public Integer getAAA04() {
		return AAA04;
	}

	@JsonIgnore public void setAAA04(Integer aAA04) {
		AAA04 = aAA04;
	}

	@JsonIgnore public String getAAA05C() {
		return AAA05C;
	}

	@JsonIgnore public void setAAA05C(String aAA05C) {
		AAA05C = aAA05C;
	}

	@JsonIgnore public Integer getAAA40() {
		return AAA40;
	}

	@JsonIgnore public void setAAA40(Integer aAA40) {
		AAA40 = aAA40;
	}

	@JsonIgnore public Integer getAAA42() {
		return AAA42;
	}

	@JsonIgnore public void setAAA42(Integer aAA42) {
		AAA42 = aAA42;
	}

	@JsonIgnore public String getAAA06C() {
		return AAA06C;
	}

	@JsonIgnore public void setAAA06C(String aAA06C) {
		AAA06C = aAA06C;
	}

	@JsonIgnore public String getAAA07() {
		return AAA07;
	}

	@JsonIgnore public void setAAA07(String aAA07) {
		AAA07 = aAA07;
	}

	@JsonIgnore public Integer getAAA08C() {
		return AAA08C;
	}

	@JsonIgnore public void setAAA08C(Integer aAA08C) {
		AAA08C = aAA08C;
	}

	@JsonIgnore public String getAAA09() {
		return AAA09;
	}

	@JsonIgnore public void setAAA09(String aAA09) {
		AAA09 = aAA09;
	}

	@JsonIgnore public String getAAA10() {
		return AAA10;
	}

	@JsonIgnore public void setAAA10(String aAA10) {
		AAA10 = aAA10;
	}

	@JsonIgnore public String getAAA11() {
		return AAA11;
	}

	@JsonIgnore public void setAAA11(String aAA11) {
		AAA11 = aAA11;
	}

	@JsonIgnore public String getAAA43() {
		return AAA43;
	}

	@JsonIgnore public void setAAA43(String aAA43) {
		AAA43 = aAA43;
	}

	@JsonIgnore public String getAAA44() {
		return AAA44;
	}

	@JsonIgnore public void setAAA44(String aAA44) {
		AAA44 = aAA44;
	}

	@JsonIgnore public String getAAA45() {
		return AAA45;
	}

	@JsonIgnore public void setAAA45(String aAA45) {
		AAA45 = aAA45;
	}

	@JsonIgnore public String getAAA46() {
		return AAA46;
	}

	@JsonIgnore public void setAAA46(String aAA46) {
		AAA46 = aAA46;
	}

	@JsonIgnore public String getAAA47() {
		return AAA47;
	}

	@JsonIgnore public void setAAA47(String aAA47) {
		AAA47 = aAA47;
	}

	@JsonIgnore public String getAAA13C() {
		return AAA13C;
	}

	@JsonIgnore public void setAAA13C(String aAA13C) {
		AAA13C = aAA13C;
	}

	@JsonIgnore public String getAAA33C() {
		return AAA33C;
	}

	@JsonIgnore public void setAAA33C(String aAA33C) {
		AAA33C = aAA33C;
	}

	@JsonIgnore public String getAAA14C() {
		return AAA14C;
	}

	@JsonIgnore public void setAAA14C(String aAA14C) {
		AAA14C = aAA14C;
	}

	@JsonIgnore public String getAAA48() {
		return AAA48;
	}

	@JsonIgnore public void setAAA48(String aAA48) {
		AAA48 = aAA48;
	}

	@JsonIgnore public String getAAA49() {
		return AAA49;
	}

	@JsonIgnore public void setAAA49(String aAA49) {
		AAA49 = aAA49;
	}

	@JsonIgnore public String getAAA50() {
		return AAA50;
	}

	@JsonIgnore public void setAAA50(String aAA50) {
		AAA50 = aAA50;
	}

	@JsonIgnore public String getAAA16C() {
		return AAA16C;
	}

	@JsonIgnore public void setAAA16C(String aAA16C) {
		AAA16C = aAA16C;
	}

	@JsonIgnore public String getAAA36C() {
		return AAA36C;
	}

	@JsonIgnore public void setAAA36C(String aAA36C) {
		AAA36C = aAA36C;
	}

	@JsonIgnore public String getAAA51() {
		return AAA51;
	}

	@JsonIgnore public void setAAA51(String aAA51) {
		AAA51 = aAA51;
	}

	@JsonIgnore public String getAAA17C() {
		return AAA17C;
	}

	@JsonIgnore public void setAAA17C(String aAA17C) {
		AAA17C = aAA17C;
	}

	@JsonIgnore public String getAAA18C() {
		return AAA18C;
	}

	@JsonIgnore public void setAAA18C(String aAA18C) {
		AAA18C = aAA18C;
	}

	@JsonIgnore public String getAAA19() {
		return AAA19;
	}

	@JsonIgnore public void setAAA19(String aAA19) {
		AAA19 = aAA19;
	}

	@JsonIgnore public String getAAA20() {
		return AAA20;
	}

	@JsonIgnore public void setAAA20(String aAA20) {
		AAA20 = aAA20;
	}

	@JsonIgnore public String getAAA21C() {
		return AAA21C;
	}

	@JsonIgnore public void setAAA21C(String aAA21C) {
		AAA21C = aAA21C;
	}

	@JsonIgnore public String getAAA22() {
		return AAA22;
	}

	@JsonIgnore public void setAAA22(String aAA22) {
		AAA22 = aAA22;
	}

	@JsonIgnore public String getAAA23C() {
		return AAA23C;
	}

	@JsonIgnore public void setAAA23C(String aAA23C) {
		AAA23C = aAA23C;
	}

	@JsonIgnore public String getAAA24() {
		return AAA24;
	}

	@JsonIgnore public void setAAA24(String aAA24) {
		AAA24 = aAA24;
	}

	@JsonIgnore public String getAAA25() {
		return AAA25;
	}

	@JsonIgnore public void setAAA25(String aAA25) {
		AAA25 = aAA25;
	}

	@JsonIgnore public Integer getAAA26C() {
		return AAA26C;
	}

	@JsonIgnore public void setAAA26C(Integer aAA26C) {
		AAA26C = aAA26C;
	}

	@JsonIgnore public String getAAA27() {
		return AAA27;
	}

	@JsonIgnore public void setAAA27(String aAA27) {
		AAA27 = aAA27;
	}

	@JsonIgnore public String getAAA28() {
		return AAA28;
	}

	@JsonIgnore public void setAAA28(String aAA28) {
		AAA28 = aAA28;
	}

	@JsonIgnore public Integer getAAA29() {
		return AAA29;
	}

	@JsonIgnore public void setAAA29(Integer aAA29) {
		AAA29 = aAA29;
	}

	@JsonIgnore public Date getAAB01() {
		return AAB01;
	}

	@JsonIgnore public void setAAB01(Date aAB01) {
		AAB01 = aAB01;
	}

	@JsonIgnore public String getAAB02C() {
		return AAB02C;
	}

	@JsonIgnore public void setAAB02C(String aAB02C) {
		AAB02C = aAB02C;
	}

	@JsonIgnore public String getAAB03() {
		return AAB03;
	}

	@JsonIgnore public void setAAB03(String aAB03) {
		AAB03 = aAB03;
	}

	@JsonIgnore public String getAAB06C() {
		return AAB06C;
	}

	@JsonIgnore public void setAAB06C(String aAB06C) {
		AAB06C = aAB06C;
	}

	@JsonIgnore public Date getAAC01() {
		return AAC01;
	}

	@JsonIgnore public void setAAC01(Date aAC01) {
		AAC01 = aAC01;
	}

	@JsonIgnore public String getAAC02C() {
		return AAC02C;
	}

	@JsonIgnore public void setAAC02C(String aAC02C) {
		AAC02C = aAC02C;
	}

	@JsonIgnore public String getAAC03() {
		return AAC03;
	}

	@JsonIgnore public void setAAC03(String aAC03) {
		AAC03 = aAC03;
	}

	@JsonIgnore public Integer getAAC04() {
		return AAC04;
	}

	@JsonIgnore public void setAAC04(Integer aAC04) {
		AAC04 = aAC04;
	}

	@JsonIgnore public String getAAD01C() {
		return AAD01C;
	}

	@JsonIgnore public void setAAD01C(String aAD01C) {
		AAD01C = aAD01C;
	}

	@JsonIgnore public String getABA01C() {
		return ABA01C;
	}

	@JsonIgnore public void setABA01C(String aBA01C) {
		ABA01C = aBA01C;
	}

	@JsonIgnore public String getABA01N() {
		return ABA01N;
	}

	@JsonIgnore public void setABA01N(String aBA01N) {
		ABA01N = aBA01N;
	}

	@JsonIgnore public String getABC01C() {
		return ABC01C;
	}

	@JsonIgnore public void setABC01C(String aBC01C) {
		ABC01C = aBC01C;
	}

	@JsonIgnore public String getABC01N() {
		return ABC01N;
	}

	@JsonIgnore public void setABC01N(String aBC01N) {
		ABC01N = aBC01N;
	}

	@JsonIgnore public String getABC03C() {
		return ABC03C;
	}

	@JsonIgnore public void setABC03C(String aBC03C) {
		ABC03C = aBC03C;
	}

	@JsonIgnore public String getABF01C() {
		return ABF01C;
	}

	@JsonIgnore public void setABF01C(String aBF01C) {
		ABF01C = aBF01C;
	}

	@JsonIgnore public String getABF01N() {
		return ABF01N;
	}

	@JsonIgnore public void setABF01N(String aBF01N) {
		ABF01N = aBF01N;
	}

	@JsonIgnore public String getABF04() {
		return ABF04;
	}

	@JsonIgnore public void setABF04(String aBF04) {
		ABF04 = aBF04;
	}

	@JsonIgnore public String getABF02C() {
		return ABF02C;
	}

	@JsonIgnore public void setABF02C(String aBF02C) {
		ABF02C = aBF02C;
	}

	@JsonIgnore public String getABF03C() {
		return ABF03C;
	}

	@JsonIgnore public void setABF03C(String aBF03C) {
		ABF03C = aBF03C;
	}

	@JsonIgnore public String getABG01C() {
		return ABG01C;
	}

	@JsonIgnore public void setABG01C(String aBG01C) {
		ABG01C = aBG01C;
	}

	@JsonIgnore public String getABG01N() {
		return ABG01N;
	}

	@JsonIgnore public void setABG01N(String aBG01N) {
		ABG01N = aBG01N;
	}

	@JsonIgnore public String getABH01C() {
		return ABH01C;
	}

	@JsonIgnore public void setABH01C(String aBH01C) {
		ABH01C = aBH01C;
	}

	@JsonIgnore public String getABH0201C() {
		return ABH0201C;
	}

	@JsonIgnore public void setABH0201C(String aBH0201C) {
		ABH0201C = aBH0201C;
	}

	@JsonIgnore public String getABH0202C() {
		return ABH0202C;
	}

	@JsonIgnore public void setABH0202C(String aBH0202C) {
		ABH0202C = aBH0202C;
	}

	@JsonIgnore public String getABH0203C() {
		return ABH0203C;
	}

	@JsonIgnore public void setABH0203C(String aBH0203C) {
		ABH0203C = aBH0203C;
	}

	@JsonIgnore public String getABH03C() {
		return ABH03C;
	}

	@JsonIgnore public void setABH03C(String aBH03C) {
		ABH03C = aBH03C;
	}

	@JsonIgnore public String getAEB02C() {
		return AEB02C;
	}

	@JsonIgnore public void setAEB02C(String aEB02C) {
		AEB02C = aEB02C;
	}

	@JsonIgnore public String getAEB01() {
		return AEB01;
	}

	@JsonIgnore public void setAEB01(String aEB01) {
		AEB01 = aEB01;
	}

	@JsonIgnore public String getAED01C() {
		return AED01C;
	}

	@JsonIgnore public void setAED01C(String aED01C) {
		AED01C = aED01C;
	}

	@JsonIgnore public String getAED02() {
		return AED02;
	}

	@JsonIgnore public void setAED02(String aED02) {
		AED02 = aED02;
	}

	@JsonIgnore public String getAED03() {
		return AED03;
	}

	@JsonIgnore public void setAED03(String aED03) {
		AED03 = aED03;
	}

	@JsonIgnore public Date getAED04() {
		return AED04;
	}

	@JsonIgnore public void setAED04(Date aED04) {
		AED04 = aED04;
	}

	@JsonIgnore public String getAEE01() {
		return AEE01;
	}

	@JsonIgnore public void setAEE01(String aEE01) {
		AEE01 = aEE01;
	}

	@JsonIgnore public String getAEE02() {
		return AEE02;
	}

	@JsonIgnore public void setAEE02(String aEE02) {
		AEE02 = aEE02;
	}

	@JsonIgnore public String getAEE03() {
		return AEE03;
	}

	@JsonIgnore public void setAEE03(String aEE03) {
		AEE03 = aEE03;
	}

	@JsonIgnore public String getAEE11() {
		return AEE11;
	}

	@JsonIgnore public void setAEE11(String aEE11) {
		AEE11 = aEE11;
	}

	@JsonIgnore public String getAEE09() {
		return AEE09;
	}

	@JsonIgnore public void setAEE09(String aEE09) {
		AEE09 = aEE09;
	}

	@JsonIgnore public String getAEE04() {
		return AEE04;
	}

	@JsonIgnore public void setAEE04(String aEE04) {
		AEE04 = aEE04;
	}

	@JsonIgnore public String getAEE05() {
		return AEE05;
	}

	@JsonIgnore public void setAEE05(String aEE05) {
		AEE05 = aEE05;
	}

	@JsonIgnore public String getAEE07() {
		return AEE07;
	}

	@JsonIgnore public void setAEE07(String aEE07) {
		AEE07 = aEE07;
	}

	@JsonIgnore public String getAEE08() {
		return AEE08;
	}

	@JsonIgnore public void setAEE08(String aEE08) {
		AEE08 = aEE08;
	}

	@JsonIgnore public String getAEE10() {
		return AEE10;
	}

	@JsonIgnore public void setAEE10(String aEE10) {
		AEE10 = aEE10;
	}

	@JsonIgnore public Integer getAEG01C() {
		return AEG01C;
	}

	@JsonIgnore public void setAEG01C(Integer aEG01C) {
		AEG01C = aEG01C;
	}

	@JsonIgnore public Integer getAEG02C() {
		return AEG02C;
	}

	@JsonIgnore public void setAEG02C(Integer aEG02C) {
		AEG02C = aEG02C;
	}

	@JsonIgnore public Integer getAEG04() {
		return AEG04;
	}

	@JsonIgnore public void setAEG04(Integer aEG04) {
		AEG04 = aEG04;
	}

	@JsonIgnore public Integer getAEG05() {
		return AEG05;
	}

	@JsonIgnore public void setAEG05(Integer aEG05) {
		AEG05 = aEG05;
	}

	@JsonIgnore public Integer getAEG06() {
		return AEG06;
	}

	@JsonIgnore public void setAEG06(Integer aEG06) {
		AEG06 = aEG06;
	}

	@JsonIgnore public Integer getAEG07() {
		return AEG07;
	}

	@JsonIgnore public void setAEG07(Integer aEG07) {
		AEG07 = aEG07;
	}

	@JsonIgnore public Integer getAEG08() {
		return AEG08;
	}

	@JsonIgnore public void setAEG08(Integer aEG08) {
		AEG08 = aEG08;
	}

	@JsonIgnore public Integer getAEJ01() {
		return AEJ01;
	}

	@JsonIgnore public void setAEJ01(Integer aEJ01) {
		AEJ01 = aEJ01;
	}

	@JsonIgnore public Integer getAEJ02() {
		return AEJ02;
	}

	@JsonIgnore public void setAEJ02(Integer aEJ02) {
		AEJ02 = aEJ02;
	}

	@JsonIgnore public Integer getAEJ03() {
		return AEJ03;
	}

	@JsonIgnore public void setAEJ03(Integer aEJ03) {
		AEJ03 = aEJ03;
	}

	@JsonIgnore public Integer getAEJ04() {
		return AEJ04;
	}

	@JsonIgnore public void setAEJ04(Integer aEJ04) {
		AEJ04 = aEJ04;
	}

	@JsonIgnore public Integer getAEJ05() {
		return AEJ05;
	}

	@JsonIgnore public void setAEJ05(Integer aEJ05) {
		AEJ05 = aEJ05;
	}

	@JsonIgnore public Integer getAEJ06() {
		return AEJ06;
	}

	@JsonIgnore public void setAEJ06(Integer aEJ06) {
		AEJ06 = aEJ06;
	}

	@JsonIgnore public Integer getAEL01() {
		return AEL01;
	}

	@JsonIgnore public void setAEL01(Integer aEL01) {
		AEL01 = aEL01;
	}

	@JsonIgnore public String getAEM01C() {
		return AEM01C;
	}

	@JsonIgnore public void setAEM01C(String aEM01C) {
		AEM01C = aEM01C;
	}

	@JsonIgnore public String getAEM02() {
		return AEM02;
	}

	@JsonIgnore public void setAEM02(String aEM02) {
		AEM02 = aEM02;
	}

	@JsonIgnore public String getAEM03C() {
		return AEM03C;
	}

	@JsonIgnore public void setAEM03C(String aEM03C) {
		AEM03C = aEM03C;
	}

	@JsonIgnore public String getAEM04() {
		return AEM04;
	}

	@JsonIgnore public void setAEM04(String aEM04) {
		AEM04 = aEM04;
	}

	@JsonIgnore public String getAEI01C() {
		return AEI01C;
	}

	@JsonIgnore public void setAEI01C(String aEI01C) {
		AEI01C = aEI01C;
	}

	@JsonIgnore public Integer getAEI09() {
		return AEI09;
	}

	@JsonIgnore public void setAEI09(Integer aEI09) {
		AEI09 = aEI09;
	}

	@JsonIgnore public Integer getAEI10() {
		return AEI10;
	}

	@JsonIgnore public void setAEI10(Integer aEI10) {
		AEI10 = aEI10;
	}

	@JsonIgnore public String getAEI08() {
		return AEI08;
	}

	@JsonIgnore public void setAEI08(String aEI08) {
		AEI08 = aEI08;
	}

	@JsonIgnore public BigDecimal getADA01() {
		return ADA01;
	}

	@JsonIgnore public void setADA01(BigDecimal aDA01) {
		ADA01 = aDA01;
	}

	@JsonIgnore public BigDecimal getADA0101() {
		return ADA0101;
	}

	@JsonIgnore public void setADA0101(BigDecimal aDA0101) {
		ADA0101 = aDA0101;
	}

	@JsonIgnore public BigDecimal getADA11() {
		return ADA11;
	}

	@JsonIgnore public void setADA11(BigDecimal aDA11) {
		ADA11 = aDA11;
	}

	@JsonIgnore public BigDecimal getADA21() {
		return ADA21;
	}

	@JsonIgnore public void setADA21(BigDecimal aDA21) {
		ADA21 = aDA21;
	}

	@JsonIgnore public BigDecimal getADA22() {
		return ADA22;
	}

	@JsonIgnore public void setADA22(BigDecimal aDA22) {
		ADA22 = aDA22;
	}

	@JsonIgnore public BigDecimal getADA23() {
		return ADA23;
	}

	@JsonIgnore public void setADA23(BigDecimal aDA23) {
		ADA23 = aDA23;
	}

	@JsonIgnore public BigDecimal getADA24() {
		return ADA24;
	}

	@JsonIgnore public void setADA24(BigDecimal aDA24) {
		ADA24 = aDA24;
	}

	@JsonIgnore public BigDecimal getADA25() {
		return ADA25;
	}

	@JsonIgnore public void setADA25(BigDecimal aDA25) {
		ADA25 = aDA25;
	}

	@JsonIgnore public BigDecimal getADA26() {
		return ADA26;
	}

	@JsonIgnore public void setADA26(BigDecimal aDA26) {
		ADA26 = aDA26;
	}

	@JsonIgnore public BigDecimal getADA27() {
		return ADA27;
	}

	@JsonIgnore public void setADA27(BigDecimal aDA27) {
		ADA27 = aDA27;
	}

	@JsonIgnore public BigDecimal getADA28() {
		return ADA28;
	}

	@JsonIgnore public void setADA28(BigDecimal aDA28) {
		ADA28 = aDA28;
	}

	@JsonIgnore public BigDecimal getADA13() {
		return ADA13;
	}

	@JsonIgnore public void setADA13(BigDecimal aDA13) {
		ADA13 = aDA13;
	}

	@JsonIgnore public BigDecimal getADA15() {
		return ADA15;
	}

	@JsonIgnore public void setADA15(BigDecimal aDA15) {
		ADA15 = aDA15;
	}

	@JsonIgnore public BigDecimal getADA12() {
		return ADA12;
	}

	@JsonIgnore public void setADA12(BigDecimal aDA12) {
		ADA12 = aDA12;
	}

	@JsonIgnore public BigDecimal getADA29() {
		return ADA29;
	}

	@JsonIgnore public void setADA29(BigDecimal aDA29) {
		ADA29 = aDA29;
	}

	@JsonIgnore public BigDecimal getADA03() {
		return ADA03;
	}

	@JsonIgnore public void setADA03(BigDecimal aDA03) {
		ADA03 = aDA03;
	}

	@JsonIgnore public BigDecimal getADA30() {
		return ADA30;
	}

	@JsonIgnore public void setADA30(BigDecimal aDA30) {
		ADA30 = aDA30;
	}

	@JsonIgnore public BigDecimal getADA31() {
		return ADA31;
	}

	@JsonIgnore public void setADA31(BigDecimal aDA31) {
		ADA31 = aDA31;
	}

	@JsonIgnore public BigDecimal getADA32() {
		return ADA32;
	}

	@JsonIgnore public void setADA32(BigDecimal aDA32) {
		ADA32 = aDA32;
	}

	@JsonIgnore public BigDecimal getADA07() {
		return ADA07;
	}

	@JsonIgnore public void setADA07(BigDecimal aDA07) {
		ADA07 = aDA07;
	}

	@JsonIgnore public BigDecimal getADA08() {
		return ADA08;
	}

	@JsonIgnore public void setADA08(BigDecimal aDA08) {
		ADA08 = aDA08;
	}

	@JsonIgnore public BigDecimal getADA33() {
		return ADA33;
	}

	@JsonIgnore public void setADA33(BigDecimal aDA33) {
		ADA33 = aDA33;
	}

	@JsonIgnore public BigDecimal getADA34() {
		return ADA34;
	}

	@JsonIgnore public void setADA34(BigDecimal aDA34) {
		ADA34 = aDA34;
	}

	@JsonIgnore public BigDecimal getADA35() {
		return ADA35;
	}

	@JsonIgnore public void setADA35(BigDecimal aDA35) {
		ADA35 = aDA35;
	}

	@JsonIgnore public BigDecimal getADA36() {
		return ADA36;
	}

	@JsonIgnore public void setADA36(BigDecimal aDA36) {
		ADA36 = aDA36;
	}

	@JsonIgnore public BigDecimal getADA37() {
		return ADA37;
	}

	@JsonIgnore public void setADA37(BigDecimal aDA37) {
		ADA37 = aDA37;
	}

	@JsonIgnore public BigDecimal getADA38() {
		return ADA38;
	}

	@JsonIgnore public void setADA38(BigDecimal aDA38) {
		ADA38 = aDA38;
	}

	@JsonIgnore public BigDecimal getADA02() {
		return ADA02;
	}

	@JsonIgnore public void setADA02(BigDecimal aDA02) {
		ADA02 = aDA02;
	}

	@JsonIgnore public BigDecimal getADA39() {
		return ADA39;
	}

	@JsonIgnore public void setADA39(BigDecimal aDA39) {
		ADA39 = aDA39;
	}

	@JsonIgnore public BigDecimal getADA09() {
		return ADA09;
	}

	@JsonIgnore public void setADA09(BigDecimal aDA09) {
		ADA09 = aDA09;
	}

	@JsonIgnore public BigDecimal getADA10() {
		return ADA10;
	}

	@JsonIgnore public void setADA10(BigDecimal aDA10) {
		ADA10 = aDA10;
	}

	@JsonIgnore public BigDecimal getADA04() {
		return ADA04;
	}

	@JsonIgnore public void setADA04(BigDecimal aDA04) {
		ADA04 = aDA04;
	}

	@JsonIgnore public BigDecimal getADA40() {
		return ADA40;
	}

	@JsonIgnore public void setADA40(BigDecimal aDA40) {
		ADA40 = aDA40;
	}

	@JsonIgnore public BigDecimal getADA41() {
		return ADA41;
	}

	@JsonIgnore public void setADA41(BigDecimal aDA41) {
		ADA41 = aDA41;
	}

	@JsonIgnore public BigDecimal getADA42() {
		return ADA42;
	}

	@JsonIgnore public void setADA42(BigDecimal aDA42) {
		ADA42 = aDA42;
	}

	@JsonIgnore public BigDecimal getADA43() {
		return ADA43;
	}

	@JsonIgnore public void setADA43(BigDecimal aDA43) {
		ADA43 = aDA43;
	}

	@JsonIgnore public BigDecimal getADA44() {
		return ADA44;
	}

	@JsonIgnore public void setADA44(BigDecimal aDA44) {
		ADA44 = aDA44;
	}

	@JsonIgnore public BigDecimal getADA05() {
		return ADA05;
	}

	@JsonIgnore public void setADA05(BigDecimal aDA05) {
		ADA05 = aDA05;
	}

	@JsonIgnore public BigDecimal getADA06() {
		return ADA06;
	}

	@JsonIgnore public void setADA06(BigDecimal aDA06) {
		ADA06 = aDA06;
	}

	@JsonIgnore public BigDecimal getADA20() {
		return ADA20;
	}

	@JsonIgnore public void setADA20(BigDecimal aDA20) {
		ADA20 = aDA20;
	}

	/**
	 * @return the editable
	 */
	public boolean isEditable() {
		return editable;
	}

	/**
	 * @param editable the editable to set
	 */
	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	public String getMdc() {
		return mdc;
	}

	public void setMdc(String mdc) {
		this.mdc = mdc;
	}

	public String getDrg() {
		return drg;
	}

	public void setDrg(String drg) {
		this.drg = drg;
	}
}
