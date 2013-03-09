package org.bjdrgs.bjwt.authority.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.bjdrgs.bjwt.authority.dao.IMenuDao;
import org.bjdrgs.bjwt.authority.dao.IPrevilegeDao;
import org.bjdrgs.bjwt.authority.dao.IRoleDao;
import org.bjdrgs.bjwt.authority.dao.IUserDao;
import org.bjdrgs.bjwt.authority.model.MasterType;
import org.bjdrgs.bjwt.authority.model.Menu;
import org.bjdrgs.bjwt.authority.model.Previlege;
import org.bjdrgs.bjwt.authority.model.PrevilegeView;
import org.bjdrgs.bjwt.authority.model.ResourceType;
import org.bjdrgs.bjwt.authority.model.Role;
import org.bjdrgs.bjwt.authority.model.User;
import org.bjdrgs.bjwt.authority.parameter.PrevilegeParam;
import org.bjdrgs.bjwt.authority.service.IPrevilegeService;
import org.bjdrgs.bjwt.authority.utils.ContentUtils;
import org.bjdrgs.bjwt.core.web.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.qos.logback.core.db.dialect.MsSQLDialect;

@Transactional
@Service("PrevilegeService")
public class PrevilegeServiceImpl implements IPrevilegeService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "PrevilegeDao")
	private IPrevilegeDao PrevilegeDao;

	@Resource(name = "RoleDao")
	private IRoleDao roleDao;

	@Resource(name = "UserDao")
	private IUserDao userDao;

	@Resource(name = "MenuDao")
	private IMenuDao menuDao;

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

	@Override
	public Pagination<PrevilegeView> queryPrevilegeView(PrevilegeParam param) {
		Pagination<Previlege> previlegeAll = queryPrevilege(param);
		List<Previlege> previlegeList = previlegeAll.getResult();
		List<PrevilegeView> viewList = new ArrayList<PrevilegeView>();
		if (previlegeList != null && previlegeList.size() > 0) {
			for (Previlege previlege : previlegeList) {
				PrevilegeView view = new PrevilegeView();
				BeanUtils.copyProperties(previlege, view);
				// 权限主体
				String master = previlege.getMaster();
				Integer masterId = previlege.getMastervalue();
				if (MasterType.ROLE.toString().equals(master)) {
					Role role = roleDao.get(masterId);
					view.setRole(role);
				} else if (MasterType.USER.toString().equals(master)) {
					User user = userDao.get(masterId);
					view.setUser(user);
				} else{
					logger.error("不是用户和角色对象"+master);
				}
				// 权限资源类型
				String resource = previlege.getResource();
				Integer resourceId = previlege.getResourcevalue();
				if (ResourceType.MENU.toString().equals(resource)) {
					Menu menu = menuDao.get(resourceId);
					view.setMenu(menu);
				}
				//权限字符串内容
				String permission = ContentUtils.parsePermission(view.getPermission());
				view.setPermission(permission);
				viewList.add(view);
			}
		}
		Pagination<PrevilegeView> viewPagi = new Pagination<PrevilegeView>(
				viewList, previlegeAll.getPageSize(), previlegeAll.getPageNo(),
				previlegeAll.getRecordCount());
		return viewPagi;
	}

}
