package org.bjdrgs.bjwt.authority.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.bjdrgs.bjwt.authority.dao.IRoleuserDao;
import org.bjdrgs.bjwt.authority.dao.IUserDao;
import org.bjdrgs.bjwt.authority.model.Roleuser;
import org.bjdrgs.bjwt.authority.model.User;
import org.bjdrgs.bjwt.authority.parameter.NewPassword;
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
	private IUserDao userDao;
	
	@Resource(name = "RoleuserDao")
	private IRoleuserDao roleuserDao;

	@Override
	public Pagination<User> queryUser(UserParam param) {
		return userDao.query(param);
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
			User originUser = userDao.get(userId);
			ModelHelper.updateModel(entity, originUser);
			entity = originUser;
		}
		userDao.save(entity);
	}
	

	@Override
	public void deleteById(Integer Userid) {
		userDao.deleteById(Userid);
	}

	/**
	 * 根据用户名和密码查找用户
	 * 
	 * @param username
	 * @param password -- 加密后的密码
	 * @return
	 */
	public User findUserByUP(String username,String password){
		List<User> userList = userDao.findUserByUP(username, password);
		if(userList!=null && userList.size() == 1){
			return userList.get(0);
		}else if(userList == null || userList.size()==0){
			logger.error("找不到任何用户,用户名："+username+",密码:"+password);
		}else if(userList.size()>1){
			logger.error("存在多个一个用户");
		}
		return null;
	}
	
	@Override
	public User findUserByName(String username) {
		return userDao.getByUnique("username", username);
	}

	@Override
	public boolean checkPassword(User userInfo, NewPassword param) {
		Integer userid = userInfo.getUserid();
		String oldPswd = param.getOldPswd();
		User dbInfo = userDao.get(userid);
		if(dbInfo.getPassword()==null || dbInfo.getPassword().length()==0){
			return oldPswd==null || oldPswd.length()==0;
		}
		return CipherUtil.validatePassword(dbInfo.getPassword(), oldPswd);
	}

	@Override
	public boolean hasRole(User user, String rolecode) {
		Roleuser roleuser = roleuserDao.get(user.getUserid(), rolecode);
		return roleuser != null;
	}
}
