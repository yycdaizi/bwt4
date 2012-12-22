package org.bjdrgs.bjwt.dicmanager.model;

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

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="dic_item")
public class DicItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@GenericGenerator(name="increment",strategy="increment")
	@Id
	@GeneratedValue(generator="increment")
	@Column(name="id",nullable=false,unique=true)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="type_id")
	private DicType type;
	
	@Column(name="code",nullable=false,length=30)
	private String code;
	
	@Column(name="text",length=60)
	private String text;
	
	@Column(name="description",length=100)
	private String description;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
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
