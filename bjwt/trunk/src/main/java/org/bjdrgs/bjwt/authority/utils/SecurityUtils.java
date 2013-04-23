package org.bjdrgs.bjwt.authority.utils;

import org.apache.shiro.subject.Subject;
import org.bjdrgs.bjwt.authority.model.User;

public class SecurityUtils {

	/**
	 * 取得当前用户
	 * @return
	 */
	public static User getCurrentUser(){
		Subject curUser = org.apache.shiro.SecurityUtils.getSubject();
		User userInfo = (User) curUser.getSession().getAttribute(Constants.KEY_CURUSER);
		return userInfo;
	}
}
