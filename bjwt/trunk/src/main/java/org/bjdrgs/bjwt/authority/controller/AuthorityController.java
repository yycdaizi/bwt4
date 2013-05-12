package org.bjdrgs.bjwt.authority.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.bjdrgs.bjwt.authority.model.MenuTree;
import org.bjdrgs.bjwt.authority.model.User;
import org.bjdrgs.bjwt.authority.service.IAuthorityService;
import org.bjdrgs.bjwt.authority.utils.Constants;
import org.bjdrgs.bjwt.core.exception.BusinessLogicException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/authority")
public class AuthorityController {

	@Resource(name = "AuthorityService")
	private IAuthorityService authorityService;

	@RequestMapping("/index")
	public ModelAndView index() {
		return new ModelAndView("index");
	}

	@RequestMapping("/menu")
	@ResponseBody
	public Object menu(HttpServletRequest request) {
		Subject curUser = SecurityUtils.getSubject();
		User userInfo = (User) curUser.getSession().getAttribute(Constants.KEY_CURUSER);
		if(userInfo==null){
			throw new BusinessLogicException("用户没有登录，却进入了系统");
		}
		List<MenuTree> authedMenuTree = authorityService.getAuthedMenuTree(userInfo);
		return authedMenuTree;
	}

}
