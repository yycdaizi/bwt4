package org.bjdrgs.bjwt.dicmanager.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.bjdrgs.bjwt.core.util.SpringContextUtils;
import org.bjdrgs.bjwt.core.web.AjaxResult;
import org.bjdrgs.bjwt.core.web.GridPage;
import org.bjdrgs.bjwt.dicmanager.model.DicItem;
import org.bjdrgs.bjwt.dicmanager.parameter.DicItemParam;
import org.bjdrgs.bjwt.dicmanager.service.IDicItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/dicmanager/dicItem")
public class DicItemController {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name = "dicItemService")
	private IDicItemService dicItemService;
	
	@RequestMapping("/findPage")
	@ResponseBody
	public GridPage<DicItem> findPage(DicItemParam param){
		if(param.getTypeId()==null){
			return GridPage.getEmptyPage();
		}
		return dicItemService.findPage(param);
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public AjaxResult save(@Valid DicItem entity, BindingResult errors) {
		if(entity.getType()==null||entity.getType().getId()==null){
			errors.rejectValue("type.id", "DicItem.type.id.notNull", SpringContextUtils.getMessage("DicItem.type.id.notNull"));
		}else{
			if(!dicItemService.isCodeUnique(entity.getType().getId(), entity.getCode())){
				errors.rejectValue("code", "DicItem.code.unique", SpringContextUtils.getMessage("DicItem.code.unique"));
			}
		}
		
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
			dicItemService.save(entity);
			result.setSuccess(true);
			result.setMessage(SpringContextUtils.getMessage("sys.save.success"));
		}
		return result;
	}
	
	@RequestMapping("/deleteById")
	@ResponseBody
	public AjaxResult deleteById(Integer id){
		dicItemService.deleteById(id);
		return new AjaxResult(true, SpringContextUtils.getMessage("sys.delete.success"));
	}
}
