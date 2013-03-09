package org.bjdrgs.bjwt.authority.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.bjdrgs.bjwt.authority.service.IUserService;
import org.bjdrgs.bjwt.core.web.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class LoginController {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value = "/login",method=RequestMethod.GET)
	public ModelAndView index(){
		return new ModelAndView("login");
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult login(@RequestParam String username,
			@RequestParam String password,
			HttpServletRequest request) throws Exception {
		AjaxResult result = new AjaxResult();
		if (username == null) {
			result.setMessage("用户为空");
			result.setSuccess(false);
			return result;
		} else if (password == null ) {
			result.setMessage("密码为空");
			result.setSuccess(false);
			return result;
		}
		return doLogin(username, password);
	}
	/**
	 * 用户登录shiro实现
	 * @param username
	 * @param password
	 * @return 登录结果
	 */
	private AjaxResult doLogin(String username,String password){
		AjaxResult result = new AjaxResult();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		token.setRememberMe(true);
		Subject curUser = SecurityUtils.getSubject();
		try {
		    curUser.login(token);
		    result.setSuccess(true);
		}  catch ( AuthenticationException ae ) {
			result.setMessage("用户名或密码错误");
			result.setSuccess(false);
			logger.error(ae.getMessage(),ae);
		}
		return result;
	}

}
