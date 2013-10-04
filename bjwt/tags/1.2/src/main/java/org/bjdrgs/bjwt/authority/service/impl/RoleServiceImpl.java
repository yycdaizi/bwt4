package org.bjdrgs.bjwt.authority.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.bjdrgs.bjwt.authority.dao.IRoleDao;
import org.bjdrgs.bjwt.authority.model.Role;
import org.bjdrgs.bjwt.authority.model.User;
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
	private IRoleDao roleDao;

	@Override
	public Pagination<Role> queryRole(RoleParam param) {
		return roleDao.query(param);
	}

	@Override
	public void saveRole(Role entity) {
		roleDao.save(entity);
	}

	@Override
	public void deleteById(Integer Roleid) {
		roleDao.deleteById(Roleid);
	}
	
	@Override
	public List<Role> getRoleByUser(User user) {
		return roleDao.getRoleByUser(user);
	}
	
	public List<String> getRolePermissions(Role role){
		return null;
	}

}

