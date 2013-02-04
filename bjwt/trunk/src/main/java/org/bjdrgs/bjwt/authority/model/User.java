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
@Table(name = "b_user")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@GenericGenerator(name = "increment", strategy = "increment")
	@Id
	@GeneratedValue(generator = "increment")
	@Column(name = "menuid", nullable = false, unique = true)
	private Integer userid;

	@Length(max = 0, message = "{common.length.limit}")
	@Column(name = "orgid", nullable = false, length = 0)
	private Integer orgid;
   
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
	@Column(name = "telphone", nullable = false, length = 60)
	private String telphone;
   
	@Length(max = 60, message = "{common.length.limit}")
	@Column(name = "mobilephone", nullable = false, length = 60)
	private String mobilephone;
   
	@Length(max = 600, message = "{common.length.limit}")
	@Column(name = "email", nullable = false, length = 600)
	private String email;
   
    
	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}
   
	public Integer getOrgid() {
		return orgid;
	}

	public void setOrgid(Integer orgid) {
		this.orgid = orgid;
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
		return sex;
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
   
}

