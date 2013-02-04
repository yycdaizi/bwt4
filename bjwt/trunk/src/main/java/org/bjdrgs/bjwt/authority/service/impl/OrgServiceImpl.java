package org.bjdrgs.bjwt.authority.service.impl;

import javax.annotation.Resource;

import org.bjdrgs.bjwt.authority.dao.IOrgDao;
import org.bjdrgs.bjwt.authority.model.Org;
import org.bjdrgs.bjwt.authority.parameter.OrgParam;
import org.bjdrgs.bjwt.authority.service.IOrgService;
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
	private IOrgDao OrgDao;

	@Override
	public Pagination<Org> queryOrg(OrgParam param) {
		return OrgDao.query(param);
	}

	@Override
	public void saveOrg(Org entity) {
		OrgDao.save(entity);
	}

	@Override
	public void deleteById(Integer Orgid) {
		OrgDao.deleteById(Orgid);
	}

}

