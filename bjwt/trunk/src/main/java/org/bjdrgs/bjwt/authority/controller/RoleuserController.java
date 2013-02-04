package org.bjdrgs.bjwt.authority.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.bjdrgs.bjwt.authority.model.Roleuser;
import org.bjdrgs.bjwt.authority.parameter.RoleuserParam;
import org.bjdrgs.bjwt.authority.service.IRoleuserService;
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
@RequestMapping("/roleuser")
public class RoleuserController {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "RoleuserService")
	private IRoleuserService roleuserService;
	
	@RequestMapping("/page")
	@ResponseBody
	public GridPage<Roleuser> page(RoleuserParam param){
		GridPage<Roleuser> page =GridPage.valueOf(roleuserService.queryRoleuser(param));
		return page;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public AjaxResult save(@Valid Roleuser entity, BindingResult errors) {
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
			roleuserService.saveRoleuser(entity);
			result.setSuccess(true);
			result.setMessage(SpringContextUtils.getMessage("sys.save.success"));
		}
		return result;
	}
	
	@RequestMapping("/deleteById")
	@ResponseBody
	public AjaxResult deleteById(Integer id){
		roleuserService.deleteById(id);
		return new AjaxResult(true, SpringContextUtils.getMessage("sys.delete.success"));
	}

}
