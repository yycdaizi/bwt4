package org.bjdrgs.bjwt.authority.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "b_previlege")
public class Previlege implements Serializable {
	private static final long serialVersionUID = 1L;

	@GenericGenerator(name = "increment", strategy = "increment")
	@Id
	@GeneratedValue(generator = "increment")
	@Column(name = "menuid", nullable = false, unique = true)
	private Integer previlegeid;

	@Length(max = 0, message = "{common.length.limit}")
	@Column(name = "orgid", nullable = false, length = 0)
	private Integer orgid;
   
	@Length(max = 150, message = "{common.length.limit}")
	@Column(name = "master", nullable = false, length = 150)
	private String master;
   
	@Length(max = 0, message = "{common.length.limit}")
	@Column(name = "mastervalue", nullable = false, length = 0)
	private Integer mastervalue;
   
	@Length(max = 150, message = "{common.length.limit}")
	@Column(name = "resource", nullable = false, length = 150)
	private String resource;
   
	@Length(max = 0, message = "{common.length.limit}")
	@Column(name = "resourcevalue", nullable = false, length = 0)
	private Integer resourcevalue;
   
	@Length(max = 150, message = "{common.length.limit}")
	@Column(name = "permission", nullable = false, length = 150)
	private String permission;
   
    
	public Integer getPrevilegeid() {
		return previlegeid;
	}

	public void setPrevilegeid(Integer previlegeid) {
		this.previlegeid = previlegeid;
	}
   
	public Integer getOrgid() {
		return orgid;
	}

	public void setOrgid(Integer orgid) {
		this.orgid = orgid;
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
   
}

