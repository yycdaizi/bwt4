package org.bjdrgs.bjwt.dicdata.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.bjdrgs.bjwt.core.format.DateTimeSerializer;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="dic_item")
public class DicItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@GenericGenerator(name="increment",strategy="increment")
	@Id
	@GeneratedValue(generator="increment")
	@Column(name="id",nullable=false,unique=true)
	private Integer id;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="type_id")
	private DicType type;
	
	@NotBlank(message="{DicItem.code.notBlank}")
	@Length(max=30,message="{DicItem.code.length}")
	@Column(name="code",nullable=false,length=30)
	private String code;
	
	@Length(max=60,message="{DicItem.text.length}")
	@Column(name="text",length=60)
	private String text;
	
	@Length(max=100,message="{DicItem.description.length}")
	@Column(name="description",length=100)
	private String description;
	
	@JsonSerialize(using= DateTimeSerializer.class)
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public DicType getType() {
		return type;
	}

	public void setType(DicType type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
