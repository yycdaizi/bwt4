package org.bjdrgs.bjwt.authority.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.bjdrgs.bjwt.authority.model.User;
import org.bjdrgs.bjwt.authority.service.IAuthorityService;
import org.bjdrgs.bjwt.authority.service.IUserService;
import org.bjdrgs.bjwt.authority.utils.Constants;

public class HqlAuthorityRealm extends AuthorizingRealm {
	
	@Resource(name = "UserService")
	private IUserService userService;
	
	@Resource(name = "AuthorityService")
	private IAuthorityService authorityService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		User curUser = (User) principals.getPrimaryPrincipal();
		List<String> permissionList = authorityService.getPermissionsByUser(curUser);
		info.addStringPermissions(permissionList);
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		SimpleAuthenticationInfo info = null;  
        UsernamePasswordToken upt = (UsernamePasswordToken) token;  
        String username = upt.getUsername();
        String password = new String(upt.getPassword());
        //若为root
        if(Constants.ROOTUSER_NAME.equals(username)){
        	User user = userService.findUserByName(username);
        	if(user.getPassword()==null || user.getPassword().length() == 0){
        		user.setPassword("");
        		info = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
        		return info;
        	}
        }
        User user = userService.findUserByUP(username,password);
        if (user == null) {
            throw new AuthenticationException();
        }
        info = new SimpleAuthenticationInfo(user, user.getPassword(), getName());  
        return info;
	}

}
