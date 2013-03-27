package org.bjdrgs.bjwt.authority.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "b_previlege")
public class Previlege implements Serializable {
	private static final long serialVersionUID = 1L;

	@GenericGenerator(name = "increment", strategy = "increment")
	@Id
	@GeneratedValue(generator = "increment")
	@Column(name = "previlegeid", nullable = false, unique = true)
	private Integer previlegeid;

	@ManyToOne
	@Cascade({ CascadeType.REFRESH })
	@JoinColumn(name = "orgid")
	private Org org;
   
	@Length(max = 150, message = "{common.length.limit}")
	@Column(name = "master", nullable = true, length = 150)
	private String master;
   
	@Column(name = "mastervalue", nullable = true, length = 20)
	private Integer mastervalue;
   
	@Length(max = 150, message = "{common.length.limit}")
	@Column(name = "resource", nullable = true, length = 150)
	private String resource;
   
	@Column(name = "resourcevalue",nullable=true, length = 20)
	private Integer resourcevalue;
   
	@Length(max = 150, message = "{common.length.limit}")
	@Column(name = "permission",nullable=true, length = 150)
	private String permission;
   
	@Column(name = "ts")
	private String ts;
    
	public Integer getPrevilegeid() {
		return previlegeid;
	}

	public void setPrevilegeid(Integer previlegeid) {
		this.previlegeid = previlegeid;
	}
   
   
	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}

	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}
   
	public Integer getMastervalue() {
		return mastervalue;
	}

	public void setMastervalue(Integer mastervalue) {
		this.mastervalue = mastervalue;
	}
   
	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}
   
	public Integer getResourcevalue() {
		return resourcevalue;
	}

	public void setResourcevalue(Integer resourcevalue) {
		this.resourcevalue = resourcevalue;
	}
   
	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}
   
	
}

