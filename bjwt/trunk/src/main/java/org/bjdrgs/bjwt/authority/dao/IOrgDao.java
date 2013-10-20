package org.bjdrgs.bjwt.authority.dao;

import java.util.List;

import org.bjdrgs.bjwt.authority.model.Org;
import org.bjdrgs.bjwt.authority.parameter.OrgParam;
import org.bjdrgs.bjwt.core.dao.IBaseDao;
import org.bjdrgs.bjwt.core.web.Pagination;

public interface IOrgDao extends IBaseDao<Org> {
	/**
	 * 分页查询
	 * @param param
	 * @return 分页对象
	 */
	public Pagination<Org> query(OrgParam param);

	/**
	 * 查询所有顶级机构
	 * @return
	 */
	public List<Org> getTopOrgs();

}
