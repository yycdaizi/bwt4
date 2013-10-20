package org.bjdrgs.bjwt.common.dao;

import org.bjdrgs.bjwt.common.model.Person;
import org.bjdrgs.bjwt.common.parameter.PersonParam;
import org.bjdrgs.bjwt.core.dao.IBaseDao;
import org.bjdrgs.bjwt.core.web.Pagination;

public interface IPersonDao extends IBaseDao<Person> {

	Pagination<Person> query(PersonParam param);

}
