package org.bjdrgs.bjwt.dicmanager.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.bjdrgs.bjwt.core.util.SpringContextUtils;
import org.bjdrgs.bjwt.core.web.AjaxResult;
import org.bjdrgs.bjwt.core.web.GridPage;
import org.bjdrgs.bjwt.dicmanager.model.DicItem;
import org.bjdrgs.bjwt.dicmanager.model.DicType;
import org.bjdrgs.bjwt.dicmanager.parameter.DicItemParam;
import org.bjdrgs.bjwt.dicmanager.service.IDicItemService;
import org.bjdrgs.bjwt.dicmanager.service.IDicTypeService;
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
	
	@Resource(name = "dicTypeService")
	private IDicTypeService dicTypeService;
	
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
		this.validateDicItem(entity, errors);
		
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
	
	private void validateDicItem(DicItem entity, BindingResult errors){
		if(entity.getType()==null||entity.getType().getId()==null){
			errors.rejectValue("type.id", "DicItem.type.id.notNull", SpringContextUtils.getMessage("DicItem.type.id.notNull"));
		}else{
			DicType dicType = dicTypeService.getById(entity.getType().getId());
			if(dicType==null){
				errors.rejectValue("type", "DicItem.type.exist", SpringContextUtils.getMessage("DicItem.type.exist"));
			}else{			
				if(!isCodeUnique(entity.getType().getId(), entity.getId(), entity.getCode())){
					errors.rejectValue("code", "DicItem.code.unique", SpringContextUtils.getMessage("DicItem.code.unique"));
				}
			}
		}
	}
	
	/**
	 * 判断某字典的字典项编码是否唯一
	 * @param typeId 字典类型ID
	 * @param itemId 字典项ID，为null时表示新增操作，不为null时表示修改操作
	 * @param itemId 字典项编码
	 * @return
	 */
	private boolean isCodeUnique(Integer typeId, Integer itemId, String code) {
		if(StringUtils.isEmpty(code)||typeId==null){
			return true;
		}
		DicType dicType = dicTypeService.getById(typeId);
		if(dicType==null){
			return true;
		}
		DicItem dicItem = dicItemService.get(dicType.getCode(), code);
		return dicItem==null||dicItem.getId().equals(itemId);
	}
}
