package org.bjdrgs.bjwt.authority.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "b_role")
public class Role {
	private Integer roleId;
	private Integer orgId;
	private String roleCode;
	private String roleName;

}
