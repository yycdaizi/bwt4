package org.bjdrgs.bjwt.authority.service.impl;

import javax.annotation.Resource;

import org.bjdrgs.bjwt.authority.dao.IRoleuserDao;
import org.bjdrgs.bjwt.authority.model.Roleuser;
import org.bjdrgs.bjwt.authority.parameter.RoleuserParam;
import org.bjdrgs.bjwt.authority.service.IRoleuserService;
import org.bjdrgs.bjwt.core.web.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("RoleuserService")
public class RoleuserServiceImpl implements IRoleuserService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name = "RoleuserDao")
	private IRoleuserDao roleuserDao;

	@Override
	public Pagination<Roleuser> queryRoleuser(RoleuserParam param) {
		return roleuserDao.query(param);
	}

	@Override
	public void saveRoleuser(Roleuser entity) {
		roleuserDao.save(entity);
	}

	@Override
	public void deleteById(Integer Roleuserid) {
		roleuserDao.deleteById(Roleuserid);
	}

}

