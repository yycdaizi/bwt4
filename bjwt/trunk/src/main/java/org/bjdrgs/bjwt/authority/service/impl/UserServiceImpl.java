package org.bjdrgs.bjwt.authority.service.impl;

import javax.annotation.Resource;

import org.bjdrgs.bjwt.authority.dao.IUserDao;
import org.bjdrgs.bjwt.authority.model.User;
import org.bjdrgs.bjwt.authority.parameter.UserParam;
import org.bjdrgs.bjwt.authority.service.IUserService;
import org.bjdrgs.bjwt.core.web.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("UserService")
public class UserServiceImpl implements IUserService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name = "UserDao")
	private IUserDao UserDao;

	@Override
	public Pagination<User> queryUser(UserParam param) {
		return UserDao.query(param);
	}

	@Override
	public void saveUser(User entity) {
		UserDao.save(entity);
	}

	@Override
	public void deleteById(Integer Userid) {
		UserDao.deleteById(Userid);
	}

}

