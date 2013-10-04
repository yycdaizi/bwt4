package org.bjdrgs.bjwt.authority.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.subject.Subject;
import org.bjdrgs.bjwt.authority.model.User;

public class ContextUtils {
	/**
	 * 获取当前登录用户
	 * @return
	 */
	public static User getCurUser(){
		Subject curSubject = SecurityUtils.getSubject();
		if(curSubject == null){
			throw new UnauthenticatedException();
		}
		User curUser = (User) curSubject.getSession().getAttribute(Constants.KEY_CURUSER);
		if(curUser == null){
			throw new UnauthenticatedException();
		}
		return curUser;
	}
	
	public static boolean isPermitted(String url){
		Subject curSubject = SecurityUtils.getSubject();
		return curSubject.isPermitted(url);
	}
}
