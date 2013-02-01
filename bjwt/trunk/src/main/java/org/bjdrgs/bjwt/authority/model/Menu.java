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
@Table(name = "b_menu")
public class Menu implements Serializable {
	private static final long serialVersionUID = 1L;

	@GenericGenerator(name = "increment", strategy = "increment")
	@Id
	@GeneratedValue(generator = "increment")
	@Column(name = "menuid", nullable = false, unique = true)
	private Integer menuid;

	@NotBlank(message = "{common.text.notBlank}")
	@Length(max = 30, message = "{common.length.limit}")
	@Column(name = "menuname", nullable = false, length = 30)
	private String menuname;

	@Length(max = 60, message = "{common.length.limit}")
	@Column(name = "menuurl", length = 60)
	private String menuurl;

	@Length(max = 60, message = "{common.length.limit}")
	@Column(name = "menuicon", length = 60)
	private String menuicon;
	
	@Column(name = "ts")
	private String ts;

	public Integer getMenuid() {
		return menuid;
	}

	public void setMenuid(Integer menuid) {
		this.menuid = menuid;
	}

	public String getMenuname() {
		return menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}

	public String getMenuurl() {
		return menuurl;
	}

	public void setMenuurl(String menuurl) {
		this.menuurl = menuurl;
	}

	public String getMenuicon() {
		return menuicon;
	}

	public void setMenuicon(String menuicon) {
		this.menuicon = menuicon;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}
	

}
