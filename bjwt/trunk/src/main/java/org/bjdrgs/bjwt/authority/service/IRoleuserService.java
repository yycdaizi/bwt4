package org.bjdrgs.bjwt.authority.service;

import java.util.List;

import org.bjdrgs.bjwt.authority.model.Role;
import org.bjdrgs.bjwt.authority.model.Roleuser;
import org.bjdrgs.bjwt.authority.model.User;
import org.bjdrgs.bjwt.authority.parameter.RoleuserParam;
import org.bjdrgs.bjwt.core.web.Pagination;

public interface IRoleuserService {
	/**
	 * 分页查询
	 * @param param
	 * @return 分页查询结果
	 */
	public Pagination<Roleuser> queryRoleuser(RoleuserParam param);
	/**
	 * 保存（新增或修改）
	 * @param entity
	 */
	public void saveRoleuser(Roleuser entity);
	
	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(Integer id);
	
}
