/*用户权限管理菜单项*/
insert into b_menu (menuid, menuname, menuurl, menuicon, ts, parentid) values (1, '系统管理', null, 'icon icon-nav', '2013-03-28 00:38:21', 0);
insert into b_menu (menuid, menuname, menuurl, menuicon, ts, parentid) values (2, '菜单管理', 'authority/menumng.jsp', 'icon icon-nav', '2013-03-28 22:29:26', 1);
insert into b_menu (menuid, menuname, menuurl, menuicon, ts, parentid) values (3, '角色管理', 'authority/rolemng.jsp', 'icon icon-nav', '2013-03-28 22:29:26', 1);
insert into b_menu (menuid, menuname, menuurl, menuicon, ts, parentid) values (4, '用户管理', 'authority/usermng.jsp', 'icon icon-nav', '2013-03-28 22:29:52', 1);
insert into b_menu (menuid, menuname, menuurl, menuicon, ts, parentid) values (5, '机构管理', 'authority/orgmng.jsp', 'icon icon-nav', '2013-03-28 22:29:52', 1);
insert into b_menu (menuid, menuname, menuurl, menuicon, ts, parentid) values (6, '用户角色分配', 'authority/roleusermng.jsp', 'icon icon-nav', '2013-03-28 22:29:52', 1);
insert into b_menu (menuid, menuname, menuurl, menuicon, ts, parentid) values (7, '角色权限分配', 'authority/roleprevilegemng.jsp', 'icon icon-nav', '2013-03-28 22:29:52', 1);
/*预制机构*/
insert into b_org (orgid, parentid, orgcode, orgname, orgaddr, orgmanager, ts) values (1, null, '0001', '北京医院IT管理中心', '北京市XX区', 1, '2013-03-28 00:59:10');
/*预置系统超级管理员*/
insert into b_user (userid, orgid, username, password, sex, telphone, mobilephone, email, ts, locked) values (1, 1, 'root', 'e10adc3949ba59abbe56e057f20f883e', '男', null, null, null, '2013-04-01 23:19:19', 0);
/*超级管理员角色*/
insert into b_role (roleid, orgid, rolecode, rolename, note, ts) values (1, 1, 'superadmin', '超级管理员', '系统超级管理员', '2013-03-28 23:14:08');
/*用户角色分配*/
insert into b_roleuser (roleuserid, orgid, roleid, userid, ts) values (1, 1, 1, 1, '2013-03-28 23:15:02');
/*权限管理预置权限分配*/
insert into b_previlege (previlegeid, orgid, master, mastervalue, resource, resourcevalue, permission, ts) values (1, 1, 'role', 1, 'menu', 1, '1', '2013-03-28 01:26:54');
insert into b_previlege (previlegeid, orgid, master, mastervalue, resource, resourcevalue, permission, ts) values (2, 1, 'role', 1, 'menu', 2, '1', '2013-03-28 01:26:54');
insert into b_previlege (previlegeid, orgid, master, mastervalue, resource, resourcevalue, permission, ts) values (3, 1, 'role', 1, 'menu', 3, '1', '2013-03-28 01:26:54');
insert into b_previlege (previlegeid, orgid, master, mastervalue, resource, resourcevalue, permission, ts) values (4, 1, 'role', 1, 'menu', 4, '1', '2013-03-28 01:26:54');
insert into b_previlege (previlegeid, orgid, master, mastervalue, resource, resourcevalue, permission, ts) values (5, 1, 'role', 1, 'menu', 5, '1', '2013-03-28 01:26:54');
insert into b_previlege (previlegeid, orgid, master, mastervalue, resource, resourcevalue, permission, ts) values (6, 1, 'role', 1, 'menu', 6, '1', '2013-03-28 01:26:54');
insert into b_previlege (previlegeid, orgid, master, mastervalue, resource, resourcevalue, permission, ts) values (7, 1, 'role', 1, 'menu', 7, '1', '2013-03-28 01:26:54');
