package org.bjdrgs.bjwt.authority.service.impl;

import javax.annotation.Resource;

import org.bjdrgs.bjwt.authority.dao.IPrevilegeDao;
import org.bjdrgs.bjwt.authority.model.Previlege;
import org.bjdrgs.bjwt.authority.parameter.PrevilegeParam;
import org.bjdrgs.bjwt.authority.service.IPrevilegeService;
import org.bjdrgs.bjwt.core.web.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("PrevilegeService")
public class PrevilegeServiceImpl implements IPrevilegeService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name = "PrevilegeDao")
	private IPrevilegeDao PrevilegeDao;

	@Override
	public Pagination<Previlege> queryPrevilege(PrevilegeParam param) {
		return PrevilegeDao.query(param);
	}

	@Override
	public void savePrevilege(Previlege entity) {
		PrevilegeDao.save(entity);
	}

	@Override
	public void deleteById(Integer Previlegeid) {
		PrevilegeDao.deleteById(Previlegeid);
	}

}

