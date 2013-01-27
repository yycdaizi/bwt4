package org.bjdrgs.bjwt.authority.controller;

import javax.servlet.http.HttpServletRequest;

import org.bjdrgs.bjwt.authority.model.User;
import org.bjdrgs.bjwt.authority.service.IUserService;
import org.bjdrgs.bjwt.utils.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class LoginController {
	private IUserService userService;
	
	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView index(){
		return new ModelAndView("login");
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestParam String name,
			@RequestParam String password, Model model,
			HttpServletRequest request) throws Exception {
		if (name == null) {
			model.addAttribute("message", "用户不存在");
			return "login";
		} else if (password == null ) {
			model.addAttribute("message", "密码错误");
			return "login";
		} else if(name.equals("zhouwb") && password.equals("123")) {
			request.getSession().setAttribute(Constants.USER_INFO_SESSION,
					new User());
			return "welcome";
		}else{
			return "login";
		}
	}

}
