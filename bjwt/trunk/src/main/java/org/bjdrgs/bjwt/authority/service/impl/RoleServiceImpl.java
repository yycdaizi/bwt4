package org.bjdrgs.bjwt.authority.service.impl;

import javax.annotation.Resource;

import org.bjdrgs.bjwt.authority.dao.IRoleDao;
import org.bjdrgs.bjwt.authority.model.Role;
import org.bjdrgs.bjwt.authority.parameter.RoleParam;
import org.bjdrgs.bjwt.authority.service.IRoleService;
import org.bjdrgs.bjwt.core.web.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("RoleService")
public class RoleServiceImpl implements IRoleService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name = "RoleDao")
	private IRoleDao RoleDao;

	@Override
	public Pagination<Role> queryRole(RoleParam param) {
		return RoleDao.query(param);
	}

	@Override
	public void saveRole(Role entity) {
		RoleDao.save(entity);
	}

	@Override
	public void deleteById(Integer Roleid) {
		RoleDao.deleteById(Roleid);
	}

}

