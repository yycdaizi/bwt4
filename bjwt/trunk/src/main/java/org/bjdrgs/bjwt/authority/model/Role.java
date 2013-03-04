package org.bjdrgs.bjwt.authority.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
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

	@JsonIgnore
	@ManyToOne
	@Cascade({ CascadeType.REFRESH })
	@JoinColumn(name = "orgid")
	private Org org;

	@Length(max = 150, message = "{common.length.limit}")
	@Column(name = "rolecode", nullable = false, length = 150)
	private String rolecode;

	@Length(max = 150, message = "{common.length.limit}")
	@Column(name = "rolename", nullable = false, length = 150)
	private String rolename;

	@Length(max = 3000, message = "{common.length.limit}")
	@Column(name = "note", nullable = false, length = 3000)
	private String note;

	@Column(name = "ts")
	private String ts;

	public Integer getRoleid() {
		return roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
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

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	// view prop
	public String getOrg_orgid(){
		return org != null ? org.getOrgid().toString() : null;
	}
	public String getOrg_orgname() {
		return org != null ? org.getOrgname() : null;
	}

}
