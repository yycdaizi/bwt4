package org.bjdrgs.bjwt.authority.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.bjdrgs.bjwt.authority.model.User;
import org.bjdrgs.bjwt.authority.parameter.UserParam;
import org.bjdrgs.bjwt.authority.service.IUserService;
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
	
	@RequestMapping("/save")
	@ResponseBody
	public AjaxResult save(@Valid User entity, BindingResult errors) {
		//TODO 看情况添加数据校验
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
	
	@RequestMapping("/deleteById")
	@ResponseBody
	public AjaxResult deleteById(Integer userid){
		userService.deleteById(userid);
		return new AjaxResult(true, SpringContextUtils.getMessage("sys.delete.success"));
	}

}
