package org.bjdrgs.bjwt.dicmanager.controller;

import java.util.Date;

import javax.annotation.Resource;

import org.bjdrgs.bjwt.dicmanager.model.DicType;
import org.bjdrgs.bjwt.dicmanager.service.IDicTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/dicmanager/dicType")
public class DicTypeController {

	@Resource(name="dicTypeService")
	private IDicTypeService dicTypeService;
	
	/*
	@RequestMapping("/add")
	@ResponseBody
	public DicType add(@RequestBody DicType entity){
		entity.setCreateTime(new Date());
		dicTypeService.save(entity);
		return entity;
	}
	*/
	@RequestMapping("/add")
	@ResponseBody
	public String add(@RequestBody String json){
		return json;
	}
}
