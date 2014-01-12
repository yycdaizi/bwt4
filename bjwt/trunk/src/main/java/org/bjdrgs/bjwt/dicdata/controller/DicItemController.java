package org.bjdrgs.bjwt.dicdata.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.bjdrgs.bjwt.core.util.SpringContextUtils;
import org.bjdrgs.bjwt.core.web.AjaxResult;
import org.bjdrgs.bjwt.core.web.GridPage;
import org.bjdrgs.bjwt.core.web.TreeNode;
import org.bjdrgs.bjwt.dicdata.model.DicItem;
import org.bjdrgs.bjwt.dicdata.model.DicType;
import org.bjdrgs.bjwt.dicdata.parameter.DicItemParam;
import org.bjdrgs.bjwt.dicdata.service.IDicDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/dicdata/dicItem")
public class DicItemController {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "dicDataService")
	private IDicDataService dicDataService;

	@RequestMapping("/page")
	@ResponseBody
	public GridPage<DicItem> page(DicItemParam param) {
		if (param.getTypeId() == null) {
			return GridPage.getEmptyPage();
		}
		return GridPage.valueOf(dicDataService.queryDicItem(param));
	}

	@RequestMapping("/tree")
	@ResponseBody
	public List<TreeNode> tree(
			@RequestParam("typeId") Integer typeId,
			@RequestParam(value = "id", required = false) Integer parentId,
			@RequestParam(value = "excludeId", required = false) Integer excludeId) {
		DicType dicType = dicDataService.getDicTypeById(typeId);
		List<DicItem> list = dicDataService.getChildrenDicItem(
				dicType.getCode(), parentId);
		List<TreeNode> nodeList = new ArrayList<TreeNode>();
		for (DicItem dicItem : list) {
			if (dicItem.getId().equals(excludeId)) {
				continue;
			}

			TreeNode node = new TreeNode();
			node.setId(dicItem.getId());
			node.setText(dicItem.getText());

			List<TreeNode> children = tree(typeId, dicItem.getId(), excludeId);
			if (CollectionUtils.isEmpty(children)) {
				node.setState(TreeNode.STATE_OPEN);
			} else {
				node.setState(TreeNode.STATE_CLOSED);
				node.setChildren(children);
			}
			node.setAttribute("code", dicItem.getCode());
			nodeList.add(node);
		}
		return nodeList;
	}

	@RequestMapping("/save")
	@ResponseBody
	public AjaxResult save(@Valid DicItem entity, BindingResult errors) {
		this.validateDicItem(entity, errors);

		AjaxResult result = new AjaxResult();
		if (errors.hasErrors()) {
			StringBuilder msg = new StringBuilder();
			List<ObjectError> list = errors.getAllErrors();
			for (ObjectError err : list) {
				msg.append(err.getDefaultMessage());
				msg.append(";");
			}
			result.setSuccess(false);
			result.setMessage(msg.toString());
		} else {
			dicDataService.saveDicItem(entity);
			result.setSuccess(true);
			result.setMessage(SpringContextUtils.getMessage("sys.save.success"));
		}
		return result;
	}

	@RequestMapping("/deleteById")
	@ResponseBody
	public AjaxResult deleteById(Integer id) {
		dicDataService.deleteDicItemById(id);
		return new AjaxResult(true,
				SpringContextUtils.getMessage("sys.delete.success"));
	}

	private void validateDicItem(DicItem entity, BindingResult errors) {
		if (entity.getType() == null || entity.getType().getId() == null) {
			errors.rejectValue("type.id", "DicItem.type.id.notNull",
					SpringContextUtils.getMessage("DicItem.type.id.notNull"));
		} else {
			DicType dicType = dicDataService.getDicTypeById(entity.getType()
					.getId());
			if (dicType == null) {
				errors.rejectValue("type", "DicItem.type.exist",
						SpringContextUtils.getMessage("DicItem.type.exist"));
			} else {
				if (!isCodeUnique(entity)) {
					errors.rejectValue("code", "DicItem.code.unique",
							SpringContextUtils
									.getMessage("DicItem.code.unique"));
				}
			}
		}
	}

	/**
	 * 判断某字典的字典项编码是否唯一
	 * 
	 * @param entity
	 *            字典项
	 * @return
	 */
	private boolean isCodeUnique(DicItem entity) {
		if (StringUtils.isEmpty(entity.getCode()) || entity.getType() == null
				|| entity.getType().getId() == null) {
			return true;
		}
		DicType dicType = dicDataService.getDicTypeById(entity.getType()
				.getId());
		if (dicType == null) {
			return true;
		}
		DicItem dicItem = dicDataService.getDicItem(dicType.getCode(),
				entity.getCode(), entity.getParentId());
		return dicItem == null || dicItem.getId().equals(entity.getId());
	}
}
