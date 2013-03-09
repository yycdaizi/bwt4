package org.bjdrgs.bjwt.authority.service.impl;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.bjdrgs.bjwt.authority.dao.IUserDao;
import org.bjdrgs.bjwt.authority.model.User;
import org.bjdrgs.bjwt.authority.service.IUserService;

public class HqlAuthorityRealm extends AuthorizingRealm {
	
	@Resource(name = "UserService")
	private IUserService userService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		SimpleAuthenticationInfo info = null;  
        UsernamePasswordToken upt = (UsernamePasswordToken) token;  
        String username = upt.getUsername();
        String password = new String(upt.getPassword());
        User user = userService.findUserByUP(username,password);  
        if (user == null) {
            throw new AuthenticationException();
        }  
        info = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), getName());  
        return info;
	}

}
