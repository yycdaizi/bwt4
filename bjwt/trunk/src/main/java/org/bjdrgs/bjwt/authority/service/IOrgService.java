package org.bjdrgs.bjwt.authority.service;

import java.util.List;

import org.bjdrgs.bjwt.authority.model.Org;
import org.bjdrgs.bjwt.authority.parameter.OrgParam;
import org.bjdrgs.bjwt.core.web.Pagination;

public interface IOrgService {
	/**
	 * 分页查询
	 * @param param
	 * @return 分页查询结果
	 */
	public Pagination<Org> queryOrg(OrgParam param);
	/**
	 * 保存（新增或修改）
	 * @param entity
	 */
	public void saveOrg(Org entity);
	
	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(Integer id);
	
	/**
	 * 机构树数据查询
	 * @param parentId
	 * @return
	 */
	public List<Org> queryForTree(Integer parentId);
	
	/**
	 * 根据机构代码查找机构
	 * @param orgCode
	 * @return
	 */
	public Org getOrgByCode(String orgCode);
}
