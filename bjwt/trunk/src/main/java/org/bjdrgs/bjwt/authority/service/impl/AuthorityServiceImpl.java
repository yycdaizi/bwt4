package org.bjdrgs.bjwt.authority.service.impl;

import org.bjdrgs.bjwt.authority.dao.IAuthorityDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("authorityService")
public class AuthorityServiceImpl {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

//	@Resource(name = "authorityDao")
	private IAuthorityDao authorityDao;


}
