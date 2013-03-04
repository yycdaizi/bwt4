package org.bjdrgs.bjwt.authority.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.bjdrgs.bjwt.authority.utils.ContentUtils;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "b_user")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@GenericGenerator(name = "increment", strategy = "increment")
	@Id
	@GeneratedValue(generator = "increment")
	@Column(name = "userid", nullable = false, unique = true)
	private Integer userid;

	@ManyToOne
	@Cascade({ CascadeType.REFRESH })
	@JoinColumn(name = "orgid")
	private Org org;

	@Length(max = 300, message = "{common.length.limit}")
	@Column(name = "username", nullable = false, length = 300)
	private String username;

	@Length(max = 300, message = "{common.length.limit}")
	@Column(name = "password", nullable = false, length = 300)
	private String password;

	@Length(max = 3, message = "{common.length.limit}")
	@Column(name = "sex", nullable = false, length = 3)
	private String sex;

	@Length(max = 60, message = "{common.length.limit}")
	@Column(name = "telphone", nullable = true, length = 60)
	private String telphone;

	@Length(max = 60, message = "{common.length.limit}")
	@Column(name = "mobilephone", nullable = true, length = 60)
	private String mobilephone;

	@Length(max = 600, message = "{common.length.limit}")
	@Column(name = "email", nullable = true, length = 600)
	private String email;

	@Column(name = "ts")
	private String ts;

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSex() {
		return ContentUtils.parseSexName(sex);
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	// girdview prop
	public String getOrg_orgname() {
		return org != null ? org.getOrgname() : null;
	}
}
