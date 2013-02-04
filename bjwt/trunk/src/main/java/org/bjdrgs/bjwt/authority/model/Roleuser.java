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
@Table(name = "b_roleuser")
public class Roleuser implements Serializable {
	private static final long serialVersionUID = 1L;

	@GenericGenerator(name = "increment", strategy = "increment")
	@Id
	@GeneratedValue(generator = "increment")
	@Column(name = "roleuserid", nullable = false, unique = true)
	private Integer roleuserid;

	@NotBlank(message = "{common.text.notBlank}")
	@Length(max = 0, message = "{common.length.limit}")
	@Column(name = "orgid", nullable = false, length = 0)
	private Integer orgid;
   
	@NotBlank(message = "{common.text.notBlank}")
	@Length(max = 0, message = "{common.length.limit}")
	@Column(name = "roleid", nullable = false, length = 0)
	private Integer roleid;
   
	@NotBlank(message = "{common.text.notBlank}")
	@Length(max = 0, message = "{common.length.limit}")
	@Column(name = "userid", nullable = false, length = 0)
	private Integer userid;
   
	@Column(name = "ts")
	private String ts;
    
	public Integer getRoleuserid() {
		return roleuserid;
	}

	public void setRoleuserid(Integer roleuserid) {
		this.roleuserid = roleuserid;
	}
   
	public Integer getOrgid() {
		return orgid;
	}

	public void setOrgid(Integer orgid) {
		this.orgid = orgid;
	}
   
	public Integer getRoleid() {
		return roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}
   
	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}
	
	
   
}

