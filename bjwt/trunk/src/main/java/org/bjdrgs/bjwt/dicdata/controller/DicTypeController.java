package org.bjdrgs.bjwt.dicdata.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.bjdrgs.bjwt.core.util.SpringContextUtils;
import org.bjdrgs.bjwt.core.web.AjaxResult;
import org.bjdrgs.bjwt.core.web.GridPage;
import org.bjdrgs.bjwt.dicdata.model.DicItem;
import org.bjdrgs.bjwt.dicdata.model.DicType;
import org.bjdrgs.bjwt.dicdata.parameter.DicTypeParam;
import org.bjdrgs.bjwt.dicdata.service.IDicDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/dicdata/dicType")
public class DicTypeController {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "dicDataService")
	private IDicDataService dicDataService;
	
	@RequestMapping("/page")
	@ResponseBody
	public GridPage<DicType> page(DicTypeParam param){
		GridPage<DicType> page =GridPage.valueOf(dicDataService.queryDicType(param));
		return page;
	}

	@RequestMapping("/save")
	@ResponseBody
	public AjaxResult save(@Valid DicType entity, BindingResult errors) {
		this.validateDicType(entity, errors);
		
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
			dicDataService.saveDicType(entity);
			result.setSuccess(true);
			result.setMessage(SpringContextUtils.getMessage("sys.save.success"));
		}
		return result;
	}
	
	@RequestMapping("/deleteById")
	@ResponseBody
	public AjaxResult deleteById(Integer id){
		dicDataService.deleteDicTypeById(id);
		return new AjaxResult(true, SpringContextUtils.getMessage("sys.delete.success"));
	}
	
	@RequestMapping("/getDicData")
	@ResponseBody
	public List<DicItem> getDicData(@RequestParam("type") String type){
		return dicDataService.listDicItemsByType(type);
	}
	
	private void validateDicType(DicType entity, BindingResult errors){
		if(!isCodeUnique(entity.getId(), entity.getCode())){
			errors.rejectValue("code", "DicType.code.unique", SpringContextUtils.getMessage("DicType.code.unique"));
		}
	}
	
	/**
	 * 判断字典类型的编码是否唯一
	 * @param typeId 字典类型ID，为null时表示新增操作，不为null时表示修改操作
	 * @param code 字典类型编码
	 * @return
	 */
	private boolean isCodeUnique(Integer typeId, String code){
		if(StringUtils.isNotEmpty(code)){
			DicType dicType = dicDataService.getDicTypeByCode(code);
			return dicType==null||dicType.getId().equals(typeId);
		}
		return true;
	}
}
