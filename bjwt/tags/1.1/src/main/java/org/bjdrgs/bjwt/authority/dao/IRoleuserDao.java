package org.bjdrgs.bjwt.authority.dao;

import org.bjdrgs.bjwt.authority.model.Roleuser;
import org.bjdrgs.bjwt.authority.parameter.RoleuserParam;
import org.bjdrgs.bjwt.core.dao.IBaseDao;
import org.bjdrgs.bjwt.core.web.Pagination;

public interface IRoleuserDao extends IBaseDao<Roleuser> {
	/**
	 * 分页查询
	 * @param param
	 * @return 分页对象
	 */
	public Pagination<Roleuser> query(RoleuserParam param);

	/**
	 * 通过用户id和角色代码取得Roleuser
	 * @param userid
	 * @param rolecode
	 */
	public Roleuser get(Integer userid, String rolecode);

}
