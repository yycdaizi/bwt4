package org.bjdrgs.bjwt.authority.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.bjdrgs.bjwt.authority.dao.IUserDao;
import org.bjdrgs.bjwt.authority.model.User;
import org.bjdrgs.bjwt.authority.parameter.UserParam;
import org.bjdrgs.bjwt.authority.service.IUserService;
import org.bjdrgs.bjwt.authority.utils.CipherUtil;
import org.bjdrgs.bjwt.authority.utils.Constants;
import org.bjdrgs.bjwt.authority.utils.ModelHelper;
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
		if (entity.getOrg() != null && entity.getOrg().getOrgid() == null) {
			entity.setOrg(null);
		}
		// 新增
		if (entity.getUserid() == null) {
			// 对密码加密
			String password = entity.getPassword();
			if (password == null) {
				password = Constants.DEFAULT_PASSWORD;
			}
			password = CipherUtil.generatePassword(password);
			entity.setPassword(password);
		} else {// 更新
			Integer userId = entity.getUserid();
			User originUser = UserDao.get(userId);
			ModelHelper.updateModel(entity, originUser);
			entity = originUser;
		}
		UserDao.save(entity);
	}

	@Override
	public void deleteById(Integer Userid) {
		UserDao.deleteById(Userid);
	}

	/**
	 * 根据用户名和密码查找用户
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public User findUserByUP(String username,String password){
		List<User> userList = UserDao.findUserByUP(username, password);
		if(userList!=null && userList.size() == 1){
			return userList.get(0);
		}else if(userList == null || userList.size()==0){
			logger.error("找不到任何用户,用户名："+username+",密码:"+password);
		}else if(userList.size()>1){
			logger.error("存在多个一个用户");
		}
		return null;
	}
}
