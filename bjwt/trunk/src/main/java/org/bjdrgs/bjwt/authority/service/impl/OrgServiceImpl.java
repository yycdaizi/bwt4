package org.bjdrgs.bjwt.authority.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.bjdrgs.bjwt.authority.dao.IOrgDao;
import org.bjdrgs.bjwt.authority.model.Org;
import org.bjdrgs.bjwt.authority.parameter.OrgParam;
import org.bjdrgs.bjwt.authority.service.IOrgService;
import org.bjdrgs.bjwt.authority.utils.Constants;
import org.bjdrgs.bjwt.core.web.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("OrgService")
public class OrgServiceImpl implements IOrgService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name = "OrgDao")
	private IOrgDao orgDao;

	@Override
	public Pagination<Org> queryOrg(OrgParam param) {
		String keywords = "";
		if(param.getKeyword()!=null){
			keywords += param.getKeyword();
		}
		if(param.getQ()!=null){
			keywords+=param.getQ();
		}
		if(keywords!=null && keywords.length()>0){
			param.setKeyword(keywords);
		}
		Pagination<Org> orgPag = orgDao.query(param);
		//treegrid判断是否有子机构
		if(param.getId()!=null){
			List<Org> orgList = orgPag.getResult();
			if(orgList!=null && orgList.size()>0){
				for(Org org : orgList){
					handleOrgState(org);
				}
			}
		}
		return orgPag;
	}

	private void handleOrgState(Org org) {
		Org hasSubOrg = orgDao.getByUnique("parentOrg.orgid", org.getOrgid());
		if(hasSubOrg!=null){
			org.setState(Constants.EASYUI_TREE_CLOSED);
		}
	}

	@Override
	public void saveOrg(Org entity) {
		if(entity.getParentOrg()!=null && entity.getParentOrg().getOrgid()==null){
			entity.setParentOrg(null);
		}
		if(entity.getOrgmanager()!=null && entity.getOrgmanager().getUserid()==null){
			entity.setOrgmanager(null);
		}
		orgDao.save(entity);
	}

	@Override
	public void deleteById(Integer Orgid) {
		orgDao.deleteById(Orgid);
	}

	@Override
	public List<Org> queryForTree(Integer parentId) {
		List<Org> orgList = null;
		if(parentId == null){
			orgList = orgDao.getTopOrgs();
		}else{
			orgList = orgDao.queryByProperty("parentOrg.orgid", parentId);
		}
		for (Org org : orgList) {
			handleOrgState(org);
		}
		return orgList;
	}

}

