package org.bjdrgs.bjwt.authority.service;

import org.bjdrgs.bjwt.authority.model.Previlege;
import org.bjdrgs.bjwt.authority.parameter.PrevilegeParam;
import org.bjdrgs.bjwt.core.web.Pagination;

public interface IPrevilegeService {
	/**
	 * 分页查询
	 * @param param
	 * @return 分页查询结果
	 */
	public Pagination<Previlege> queryPrevilege(PrevilegeParam param);
	/**
	 * 保存（新增或修改）
	 * @param entity
	 */
	public void savePrevilege(Previlege entity);
	
	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(Integer id);
}
