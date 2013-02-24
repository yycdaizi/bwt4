package org.bjdrgs.bjwt.authority.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "b_org")
public class Org implements Serializable {
	private static final long serialVersionUID = 1L;

	@GenericGenerator(name = "increment", strategy = "increment")
	@Id
	@GeneratedValue(generator = "increment")
	@Column(name = "orgid", nullable = false, unique = true)
	private Integer orgid;

	@JsonIgnore
	@ManyToOne
	@Cascade({CascadeType.REFRESH})
	@JoinColumn(name="parentid")
	private Org parentOrg;
   
	@Length(max = 150, message = "{common.length.limit}")
	@Column(name = "orgcode", nullable = false, length = 150)
	private String orgcode;
   
	@Length(max = 150, message = "{common.length.limit}")
	@Column(name = "orgname", nullable = false, length = 150)
	private String orgname;
   
	@Length(max = 1500, message = "{common.length.limit}")
	@Column(name = "orgaddr", nullable = false, length = 1500)
	private String orgaddr;
   
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="orgmanager")
	private User orgmanager;
	
	@Column(name = "ts")
	private String ts;
    
	public Integer getOrgid() {
		return orgid;
	}

	public void setOrgid(Integer orgid) {
		this.orgid = orgid;
	}
   
	public Org getParentOrg() {
		return parentOrg;
	}

	public void setParentOrg(Org parentOrg) {
		this.parentOrg = parentOrg;
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

	public User getOrgmanager() {
		return orgmanager;
	}

	public void setOrgmanager(User orgmanager) {
		this.orgmanager = orgmanager;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}
//	供表格使用显示外键的值
	public Integer getParentOrg_orgid(){
		return this.getParentOrg()!=null ? getParentOrg().getOrgid():null;
	}
	public String getParentOrg_showname() {
		return this.getParentOrg()!=null ? getParentOrg().getOrgname():null;
	}
	public Integer getOrgmanager_userid(){
		return this.getOrgmanager()!=null?getOrgmanager().getUserid():null;
	}
	public String getOrgmanager_showname() {
		return this.getOrgmanager()!=null?getOrgmanager().getUsername():null;
	}
   
}

