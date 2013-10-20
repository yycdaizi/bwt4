package org.bjdrgs.bjwt.common.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.bjdrgs.bjwt.authority.model.User;
import org.bjdrgs.bjwt.authority.utils.SecurityUtils;
import org.bjdrgs.bjwt.common.model.Person;
import org.bjdrgs.bjwt.common.parameter.PersonParam;
import org.bjdrgs.bjwt.common.service.IPersonService;
import org.bjdrgs.bjwt.core.util.SpringContextUtils;
import org.bjdrgs.bjwt.core.web.AjaxResult;
import org.bjdrgs.bjwt.core.web.GridPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/common/person")
public class PersonController {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name = "personService")
	private IPersonService personService;
	
	@RequestMapping("/page")
	@ResponseBody
	public GridPage<Person> page(PersonParam param){
		return GridPage.valueOf(personService.queryForPage(param));
	}
	
	@RequestMapping("/query")
	@ResponseBody
	public List<Person> query(PersonParam param){
		return personService.query(param);
	}
	
	@RequestMapping("/getPersonInSameOrg")
	@ResponseBody
	public List<Person> getPersonInSameOrg(PersonParam param){
		//只能查询本机构下面的人
		User user = SecurityUtils.getCurrentUser();
		param.setOrgId(user.getOrg().getOrgid());
		return personService.query(param);
	}

	@RequestMapping("/batchSave")
	@ResponseBody
	public AjaxResult batchSave(Person entity) {
		AjaxResult result = new AjaxResult();
		String nameStr = entity.getName();
		if(StringUtils.isEmpty(nameStr)){
			result.setSuccess(true);
			result.setMessage("人员姓名不能为空！");
		}else{
			String[] nameList = nameStr.split("#");
			List<Person> entities = new ArrayList<Person>();
			for(int i=0; i<nameList.length; i++){
				Person person = new Person();
				person.setName(nameList[i]);
				person.setOrg(entity.getOrg());
				person.setType(entity.getType());
				
				entities.add(person);
			}
			personService.save(entities);
			result.setSuccess(true);
			result.setMessage(SpringContextUtils.getMessage("sys.save.success"));
		}
		return result;
	}
	
	@RequestMapping("/deleteById")
	@ResponseBody
	public AjaxResult deleteById(Integer id){
		personService.delete(id);
		return new AjaxResult(true, SpringContextUtils.getMessage("sys.delete.success"));
	}
}
