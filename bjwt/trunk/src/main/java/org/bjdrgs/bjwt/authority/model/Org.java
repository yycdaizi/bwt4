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
@Table(name = "b_org")
public class Org implements Serializable {
	private static final long serialVersionUID = 1L;

	@GenericGenerator(name = "increment", strategy = "increment")
	@Id
	@GeneratedValue(generator = "increment")
	@Column(name = "menuid", nullable = false, unique = true)
	private Integer orgid;

	@NotBlank(message = "{common.text.notBlank}")
	@Length(max = 0, message = "{common.length.limit}")
	@Column(name = "parentid", nullable = false, length = 0)
	private Integer parentid;
   
	@Length(max = 150, message = "{common.length.limit}")
	@Column(name = "orgcode", nullable = false, length = 150)
	private String orgcode;
   
	@Length(max = 150, message = "{common.length.limit}")
	@Column(name = "orgname", nullable = false, length = 150)
	private String orgname;
   
	@Length(max = 1500, message = "{common.length.limit}")
	@Column(name = "orgaddr", nullable = false, length = 1500)
	private String orgaddr;
   
	@NotBlank(message = "{common.text.notBlank}")
	@Length(max = 0, message = "{common.length.limit}")
	@Column(name = "orgmanager", nullable = false, length = 0)
	private Integer orgmanager;
   
    
	public Integer getOrgid() {
		return orgid;
	}

	public void setOrgid(Integer orgid) {
		this.orgid = orgid;
	}
   
	public Integer getParentid() {
		return parentid;
	}

	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}
   
	public String getOrgcode() {
		return orgcode;
	}

	public void setOrgcode(String orgcode) {
		this.orgcode = orgcode;
	}
   
	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}
   
	public String getOrgaddr() {
		return orgaddr;
	}

	public void setOrgaddr(String orgaddr) {
		this.orgaddr = orgaddr;
	}
   
	public Integer getOrgmanager() {
		return orgmanager;
	}

	public void setOrgmanager(Integer orgmanager) {
		this.orgmanager = orgmanager;
	}
   
}

