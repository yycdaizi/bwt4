package org.bjdrgs.bjwt.authority.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.bjdrgs.bjwt.authority.model.Menu;
import org.bjdrgs.bjwt.authority.parameter.MenuParam;
import org.bjdrgs.bjwt.authority.service.IMenuService;
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
@RequestMapping("/menu")
public class MenuController {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "MenuService")
	private IMenuService menuService;
	
	@RequestMapping("/page")
	@ResponseBody
	public Object page(MenuParam param){
		GridPage<Menu> page = GridPage.valueOf(menuService.queryMenu(param));
		if(param.getId()==null || param.getId()==0){
			return page;
		}else{
			return page.getRows();
		}
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public AjaxResult save(@Valid Menu entity, BindingResult errors) {
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
			menuService.saveMenu(entity);
			result.setSuccess(true);
			result.setMessage(SpringContextUtils.getMessage("sys.save.success"));
		}
		return result;
	}
	
	@RequestMapping("/deleteById")
	@ResponseBody
	public AjaxResult deleteById(Integer menuid){
		menuService.deleteById(menuid);
		return new AjaxResult(true, SpringContextUtils.getMessage("sys.delete.success"));
	}

}
