package org.bjdrgs.bjwt.common.service;

import java.util.List;

import org.bjdrgs.bjwt.common.model.Person;
import org.bjdrgs.bjwt.common.parameter.PersonParam;
import org.bjdrgs.bjwt.core.web.Pagination;

public interface IPersonService {

	Pagination<Person> query(PersonParam param);

	void save(List<Person> entities);

	void delete(Integer id);

}
