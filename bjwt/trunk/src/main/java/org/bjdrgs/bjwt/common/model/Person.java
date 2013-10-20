package org.bjdrgs.bjwt.common.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.bjdrgs.bjwt.authority.model.Org;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="b_person")
public class Person implements Serializable {
	
	private static final long serialVersionUID = 1L;


	@GenericGenerator(name="increment",strategy="increment")
	@Id
	@GeneratedValue(generator="increment")
	@Column(name="id",nullable=false,unique=true)
	private Integer id;
	
	@NotBlank(message="{Person.name.notBlank}")
	@Length(max=60,message="{Person.name.length}")
	@Column(name="name",length=60)
	private String name;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="org_id")
	private Org org;
	
	@NotBlank(message="{Person.type.notBlank}")
	@Column(name="type",nullable=false)
	private String type;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
