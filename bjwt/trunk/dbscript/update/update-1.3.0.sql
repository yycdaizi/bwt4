--更新时间:2013-10-06
--病案表添加状态字段，0 - 草稿；1 - 完成
ALTER TABLE `b_wt4` ADD COLUMN `state` VARCHAR(1) NOT NULL DEFAULT '1';
--新增字段，mdc，drg，都是字符，长度4
ALTER TABLE `b_wt4` ADD COLUMN `mdc` VARCHAR(4);
ALTER TABLE `b_wt4` ADD COLUMN `drg` VARCHAR(4);

DROP TABLE IF EXISTS `b_person`;
CREATE TABLE `b_person` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(60) NOT NULL COMMENT '姓名',
  `type` VARCHAR(2) NOT NULL COMMENT '类型',
  `org_id` int(11) NOT NULL COMMENT '所属机构',
  PRIMARY KEY (`id`))
ENGINE = InnoDB DEFAULT CHARSET=utf8
COMMENT = '人员表';

insert into b_menu (menuid, menuname, menuurl, menuicon, ts, parentid) values (13, '文件导入', 'wt4/upload.jsp', 'icon icon-nav', '2013-03-28 00:38:21', 8);
insert into b_menu (menuid, menuname, menuurl, menuicon, ts, parentid) values (14, '人员管理', 'common/personManage.jsp', 'icon icon-nav', '2013-03-28 00:38:21', 11);
insert into b_previlege (orgid, master, mastervalue, resource, resourcevalue, permission, ts) values (1, 'role', 1, 'menu', 13, '1', '2013-03-28 01:26:54');
insert into b_previlege (orgid, master, mastervalue, resource, resourcevalue, permission, ts) values (1, 'role', 1, 'menu', 14, '1', '2013-03-28 01:26:54');

--字典数据
INSERT INTO `dic_type` (`id`,`code`,`name`,`create_time`) VALUES (1,'MR-STATE','病案状态','2013-10-20 14:11:38');
INSERT INTO `dic_type` (`id`,`code`,`name`,`create_time`) VALUES (2,'PERSON-TYPE','人员类别','2013-10-20 14:13:08');
INSERT INTO `dic_type` (`id`,`code`,`name`,`create_time`) VALUES (3,'MR-MDC','病案mdc','2013-10-20 14:13:08');
INSERT INTO `dic_type` (`id`,`code`,`name`,`create_time`) VALUES (4,'MR-DRG','病案drg','2013-10-20 14:13:08');
INSERT INTO `dic_item` (`id`,`type_id`,`code`,`text`,`create_time`,`description`) VALUES (1,1,'0','草稿','2013-10-20 14:12:01','草稿');
INSERT INTO `dic_item` (`id`,`type_id`,`code`,`text`,`create_time`,`description`) VALUES (2,1,'1','完成','2013-10-20 14:12:20','已完成');
INSERT INTO `dic_item` (`id`,`type_id`,`code`,`text`,`create_time`,`description`) VALUES (3,1,'2','未验证','2013-10-20 14:12:40','未验证');
INSERT INTO `dic_item` (`id`,`type_id`,`code`,`text`,`create_time`,`description`) VALUES (4,2,'1','医生','2013-10-20 14:13:45','医生');
INSERT INTO `dic_item` (`id`,`type_id`,`code`,`text`,`create_time`,`description`) VALUES (5,2,'2','护士','2013-10-20 14:13:59','护士');
commit;