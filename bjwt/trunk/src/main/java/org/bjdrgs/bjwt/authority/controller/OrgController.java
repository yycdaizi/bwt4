package org.bjdrgs.bjwt.authority.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.bjdrgs.bjwt.authority.model.Org;
import org.bjdrgs.bjwt.authority.parameter.OrgParam;
import org.bjdrgs.bjwt.authority.service.IOrgService;
import org.bjdrgs.bjwt.core.util.SpringContextUtils;
import org.bjdrgs.bjwt.core.web.AjaxResult;
import org.bjdrgs.bjwt.core.web.GridPage;
import org.bjdrgs.bjwt.core.web.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/org")
public class OrgController {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "OrgService")
	private IOrgService orgService;
	
	@RequestMapping("/page")
	@ResponseBody
	public Object page(OrgParam param){
		GridPage<Org> page =GridPage.valueOf(orgService.queryOrg(param));
		if(param.getId()==null || param.getId()==0){
			return page;
		}else{
			return page.getRows();
		}
	}
	
	@RequestMapping("/tree")
	@ResponseBody
	public List<TreeNode> tree(@RequestParam(required=false, value="id")Integer parentId){
		List<Org> orgList = orgService.queryForTree(parentId);
		
		List<TreeNode> nodeList = new ArrayList<TreeNode>();
		for(Org org : orgList){
			TreeNode node = new TreeNode();
			node.setId(org.getOrgid());
			node.setText(org.getOrgname());
			node.setState(org.getState());
			
			nodeList.add(node);
		}
		return nodeList;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public AjaxResult save(@Valid Org entity, BindingResult errors) {
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
			orgService.saveOrg(entity);
			result.setSuccess(true);
			result.setMessage(SpringContextUtils.getMessage("sys.save.success"));
		}
		return result;
	}
	
	
	@RequestMapping("/deleteById")
	@ResponseBody
	public AjaxResult deleteById(Integer orgid){
		if(orgid==null){
			return new AjaxResult(false,"orgid is null");
		}
		orgService.deleteById(orgid);
		return new AjaxResult(true, SpringContextUtils.getMessage("sys.delete.success"));
	}

}
