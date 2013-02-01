package org.bjdrgs.bjwt.authority.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.bjdrgs.bjwt.authority.service.IAuthorityService;
import org.bjdrgs.bjwt.core.web.AjaxResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/authority")
public class AuthorityController {

//	@Resource(name = "authorityService")
	private IAuthorityService authorityService;

	@RequestMapping("/index")
	public ModelAndView index() {
		return new ModelAndView("index");
	}

	@RequestMapping("/menu")
	@ResponseBody
	public AjaxResult menu(HttpServletRequest request) {
		// User user = (User)
		// request.getSession().getAttribute(USER_INFO_SESSION);
		// return getUserMenu(user);
		return null;

	}

}
