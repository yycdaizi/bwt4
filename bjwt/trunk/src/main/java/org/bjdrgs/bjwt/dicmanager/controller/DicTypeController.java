package org.bjdrgs.bjwt.dicmanager.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.bjdrgs.bjwt.core.web.AjaxResult;
import org.bjdrgs.bjwt.core.web.GridPage;
import org.bjdrgs.bjwt.dicmanager.model.DicType;
import org.bjdrgs.bjwt.dicmanager.parameter.DicTypeParam;
import org.bjdrgs.bjwt.dicmanager.service.IDicTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.BindingResultUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/dicmanager/dicType")
public class DicTypeController {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "dicTypeService")
	private IDicTypeService dicTypeService;
	
	@RequestMapping("/findPage")
	@ResponseBody
	public GridPage<DicType> findPage(DicTypeParam param){
		GridPage<DicType> page = dicTypeService.findPage(param);
		return page;
	}

	@RequestMapping("/save")
	@ResponseBody
	public AjaxResult save(@Valid DicType entity, BindingResult errors) {
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
			dicTypeService.save(entity);
			result.setSuccess(true);
			result.setMessage("保存成功！");
		}
		return result;
	}
	
	@RequestMapping("/deleteById")
	@ResponseBody
	public AjaxResult deleteById(Integer id){
		dicTypeService.deleteById(id);
		return new AjaxResult(true, "删除成功！");
	}

	/*
	 * @RequestMapping("/add")
	 * 
	 * @ResponseBody public DicType add(@RequestBody DicType entity){
	 * entity.setCreateTime(new Date()); dicTypeService.save(entity); return
	 * entity; }
	 * 
	 * @RequestMapping("/add")
	 * 
	 * @ResponseBody public DicType add(DicType entity){ return entity; }
	 */
	@RequestMapping("/add")
	public ResponseEntity<DicType> add(DicType entity) {
		ResponseEntity<DicType> responseEntity = new ResponseEntity<DicType>(
				entity, HttpStatus.OK);
		return responseEntity;
	}
}
