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
@Table(name = "b_role")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@GenericGenerator(name = "increment", strategy = "increment")
	@Id
	@GeneratedValue(generator = "increment")
	@Column(name = "roleid", nullable = false, unique = true)
	private Integer roleid;

	@NotBlank(message = "{common.text.notBlank}")
	@Length(max = 0, message = "{common.length.limit}")
	@Column(name = "orgid", nullable = false, length = 0)
	private Integer orgid;
   
	@Length(max = 150, message = "{common.length.limit}")
	@Column(name = "rolecode", nullable = false, length = 150)
	private String rolecode;
   
	@Length(max = 150, message = "{common.length.limit}")
	@Column(name = "rolename", nullable = false, length = 150)
	private String rolename;
   
	@Length(max = 3000, message = "{common.length.limit}")
	@Column(name = "note", nullable = false, length = 3000)
	private String note;
   
    
	public Integer getRoleid() {
		return roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}
   
	public Integer getOrgid() {
		return orgid;
	}

	public void setOrgid(Integer orgid) {
		this.orgid = orgid;
	}
   
	public String getRolecode() {
		return rolecode;
	}

	public void setRolecode(String rolecode) {
		this.rolecode = rolecode;
	}
   
	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
   
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
   
}

