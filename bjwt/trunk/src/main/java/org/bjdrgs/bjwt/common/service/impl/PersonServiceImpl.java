package org.bjdrgs.bjwt.common.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.bjdrgs.bjwt.common.dao.IPersonDao;
import org.bjdrgs.bjwt.common.model.Person;
import org.bjdrgs.bjwt.common.parameter.PersonParam;
import org.bjdrgs.bjwt.common.service.IPersonService;
import org.bjdrgs.bjwt.core.web.Pagination;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("personService")
public class PersonServiceImpl implements IPersonService {

	@Resource(name = "personDao")
	private IPersonDao personDao;

	@Override
	public Pagination<Person> query(PersonParam param) {
		return personDao.query(param);
	}

	@Override
	public void save(List<Person> entities) {
		personDao.save(entities);
	}

	@Override
	public void delete(Integer id) {
		personDao.deleteById(id);
	}
}
