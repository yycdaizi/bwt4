package org.bjdrgs.bjwt.dicmanager.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.bjdrgs.bjwt.core.format.DateTimeSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="dic_type")
public class DicType implements Serializable {

	private static final long serialVersionUID = 1L;

	@GenericGenerator(name="increment",strategy="increment")
	@Id
	@GeneratedValue(generator="increment")
	@Column(name="id",nullable=false,unique=true)
	private Integer id;
	
	@NotBlank(message="{DicType.code.notBlank}")
	@Length(max=30,message="{DicType.code.length}")
	@Column(name="code",nullable=false,length=30)
	private String code;
	
	@Length(max=60,message="{DicType.name.length}")
	@Column(name="name",length=60)
	private String name;
	
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
