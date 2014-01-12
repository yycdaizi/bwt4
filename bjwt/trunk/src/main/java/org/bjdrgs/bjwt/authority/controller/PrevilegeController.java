package org.bjdrgs.bjwt.authority.controller;

import java.util.List;

import javax.annotation.Resource;

import org.bjdrgs.bjwt.authority.model.Previlege;
import org.bjdrgs.bjwt.authority.model.PrevilegeView;
import org.bjdrgs.bjwt.authority.parameter.PrevilegeParam;
import org.bjdrgs.bjwt.authority.service.IPrevilegeService;
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
@RequestMapping("/previlege")
public class PrevilegeController {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "PrevilegeService")
	private IPrevilegeService previlegeService;
	
	@RequestMapping("/page")
	@ResponseBody
	public GridPage<PrevilegeView> page(PrevilegeParam param){
		GridPage<PrevilegeView> page = GridPage.valueOf(previlegeService.queryPrevilegeView(param));
		return page;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public AjaxResult save(Previlege entity, BindingResult errors) {
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
			previlegeService.savePrevilege(entity);
			result.setSuccess(true);
			result.setMessage(SpringContextUtils.getMessage("sys.save.success"));
		}
		return result;
	}
	
	@RequestMapping("/deleteById")
	@ResponseBody
	public AjaxResult deleteById(Integer previlegeid){
		previlegeService.deleteById(previlegeid);
		return new AjaxResult(true, SpringContextUtils.getMessage("sys.delete.success"));
	}

}
