package org.bjdrgs.bjwt.core.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 菜单树模型
 * 
 * @author zhouwb
 * 
 */
public class TreeNode {
	public static final String PROP_URL = "url";
	
	//树节点状态
	public static final String STATE_CLOSED = "closed";
	
	public static final String STATE_OPEN = "open";
	
	private Integer id;
	/**
	 * 显示名称
	 */
	private String text;
	/**
	 * 状态打开还是关闭
	 */
	private String state;
	/**
	 * 是否被选中
	 */
	private boolean checked;
	/**
	 * 子菜单
	 */
	private List<TreeNode> children;
	/**
	 * 用户自定义属性
	 */
	private Map<String, String> attributes;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
	
	public void addChildren(TreeNode child){
		if(this.children==null){
			this.children = new ArrayList<TreeNode>();
		}
		this.children.add(child);
	}

}
