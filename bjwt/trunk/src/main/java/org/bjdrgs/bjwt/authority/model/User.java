package org.bjdrgs.bjwt.authority.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="b_user")
public class User implements Serializable{
	private static final long serialVersionUID = 4652993222976643511L;
	
	private Integer userid;
	private Integer orgid;
	private String username;
	private String password;
	private String sex;
	private String telphone;
	
	private List<Integer> roleIds;

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getOrgid() {
		return orgid;
	}

	public void setOrgid(int orgid) {
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

	public List<Integer> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<Integer> roleIds) {
		this.roleIds = roleIds;
	}
	
}
