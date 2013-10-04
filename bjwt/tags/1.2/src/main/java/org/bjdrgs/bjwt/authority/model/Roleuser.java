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

@Entity
@Table(name = "b_roleuser")
public class Roleuser implements Serializable {
	private static final long serialVersionUID = 1L;

	@GenericGenerator(name = "increment", strategy = "increment")
	@Id
	@GeneratedValue(generator = "increment")
	@Column(name = "roleuserid", nullable = false, unique = true)
	private Integer roleuserid;

	@ManyToOne
	@Cascade({ CascadeType.REFRESH })
	@JoinColumn(name = "orgid")
	private Org org;
   
	@ManyToOne
	@Cascade({ CascadeType.REFRESH })
	@JoinColumn(name = "roleid")
	private Role role;
   
	@ManyToOne
	@Cascade({ CascadeType.REFRESH })
	@JoinColumn(name = "userid")
	private User user;
   
	@Column(name = "ts")
	private String ts;
    
	public Integer getRoleuserid() {
		return roleuserid;
	}

	public void setRoleuserid(Integer roleuserid) {
		this.roleuserid = roleuserid;
	}
   
	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}
}

