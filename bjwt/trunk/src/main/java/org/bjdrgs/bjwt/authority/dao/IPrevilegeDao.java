package org.bjdrgs.bjwt.authority.dao;

import org.bjdrgs.bjwt.authority.model.Previlege;
import org.bjdrgs.bjwt.authority.parameter.PrevilegeParam;
import org.bjdrgs.bjwt.core.dao.IBaseDao;
import org.bjdrgs.bjwt.core.web.Pagination;

public interface IPrevilegeDao extends IBaseDao<Previlege> {
	/**
	 * 分页查询
	 * @param param
	 * @return 分页对象
	 */
	public Pagination<Previlege> query(PrevilegeParam param);

}
