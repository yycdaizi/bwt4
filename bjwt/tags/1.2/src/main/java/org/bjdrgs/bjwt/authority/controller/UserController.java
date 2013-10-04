package org.bjdrgs.bjwt.authority.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.security.auth.Subject;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.bjdrgs.bjwt.authority.model.User;
import org.bjdrgs.bjwt.authority.parameter.NewPassword;
import org.bjdrgs.bjwt.authority.parameter.UserParam;
import org.bjdrgs.bjwt.authority.service.IUserService;
import org.bjdrgs.bjwt.authority.utils.CipherUtil;
import org.bjdrgs.bjwt.authority.utils.Constants;
import org.bjdrgs.bjwt.authority.utils.ContentUtils;
import org.bjdrgs.bjwt.core.util.SpringContextUtils;
import org.bjdrgs.bjwt.core.web.AjaxResult;
import org.bjdrgs.bjwt.core.web.GridPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "UserService")
	private IUserService userService;
	
	@RequestMapping("/page")
	@ResponseBody
	public GridPage<User> page(UserParam param){
		GridPage<User> page =GridPage.valueOf(userService.queryUser(param));
		return page;
	}
	/**
	 * 内部新增一个用户，密码为预置密码
	 * @param entity
	 * @param errors
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public AjaxResult save(@Valid User entity, BindingResult errors) {
		AjaxResult result = new AjaxResult();
		if(errors.hasErrors()){
			StringBuilder msg = new StringBuilder();
			List<ObjectError> list= errors.getAllErrors();
			for (ObjectError err : list) {
				msg.append(err.getDefaultMessage());
				msg.append(";");
			}
			result.setSuccess(false);
			result.setMessage(msg.toString());
		}else{
			userService.saveUser(entity);
			result.setSuccess(true);
			result.setMessage(SpringContextUtils.getMessage("sys.save.success"));
		}
		return result;
	}
	/**
	 * 修改密码
	 * @param param
	 * @return
	 */
	@RequestMapping("/changepswd")
	@ResponseBody
	public AjaxResult changePswd(NewPassword param){
		AjaxResult result = new AjaxResult();
		User curUser = (User) SecurityUtils.getSubject().getSession().getAttribute(Constants.KEY_CURUSER);
		boolean isSame = userService.checkPassword(curUser,param);
		if(!isSame){
			result.setMessage("旧密码输入错误");
			result.setSuccess(false);
			return result;
		}
		String newPswd = param.getNewPswd();
		String newPswd2 = param.getNewPswd2();
		if(!ContentUtils.checkStringEqual(newPswd, newPswd2)){
			result.setMessage("两次输入的密码不一致");
			result.setSuccess(false);
		}
		curUser.setPassword(CipherUtil.generatePassword(newPswd));
		try{
			userService.saveUser(curUser);
		}catch (Exception e) {
			result.setMessage(e.getMessage());
			result.setSuccess(false);
			return result;
		}
		result.setMessage(SpringContextUtils.getMessage("sys.op.success"));
		result.setSuccess(true);
		return result;
	}
	
	@RequestMapping("/deleteById")
	@ResponseBody
	public AjaxResult deleteById(Integer userid){
		userService.deleteById(userid);
		return new AjaxResult(true, SpringContextUtils.getMessage("sys.delete.success"));
	}

}
