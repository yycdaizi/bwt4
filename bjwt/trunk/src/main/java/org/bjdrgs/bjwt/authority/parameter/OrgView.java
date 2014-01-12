package org.bjdrgs.bjwt.authority.parameter;

import org.bjdrgs.bjwt.authority.model.Org;
import org.springframework.beans.BeanUtils;

public class OrgView extends Org {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public OrgView(){};
	public OrgView(Org org){
		BeanUtils.copyProperties(org, this);
	}

	public Integer getParentOrg_orgid(){
		return this.getParentOrg()!=null?getParentOrg().getOrgid():null;
	}
	public String getParentOrg_showname() {
		return this.getParentOrg()!=null?getParentOrg().getOrgname():null;
	}
	public Integer getOrgmanager_userid(){
		return this.getOrgmanager()!=null?getOrgmanager().getUserid():null;
	}
	public String getOrgmanager_showname() {
		return this.getOrgmanager()!=null?getOrgmanager().getUsername():null;
	}

}
