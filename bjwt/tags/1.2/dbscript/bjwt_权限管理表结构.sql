/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2013/2/23 18:19:36                           */
/*==============================================================*/
/*==============================================================*/
/* Table: b_menu                                                */
/*==============================================================*/

DROP TABLE IF EXISTS `b_menu`;
CREATE TABLE IF NOT EXISTS `b_menu` (
  `menuid` int(11) NOT NULL AUTO_INCREMENT,
  `menuname` varchar(50) DEFAULT NULL,
  `menuurl` varchar(200) DEFAULT NULL,
  `menuicon` varchar(200) DEFAULT NULL,
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `parentid` int(11) DEFAULT NULL,
  PRIMARY KEY (`menuid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=10 ;


/*==============================================================*/
/* Table: b_org                                                 */
/*==============================================================*/

DROP TABLE IF EXISTS `b_org`;
CREATE TABLE IF NOT EXISTS `b_org` (
  `orgid` int(11) NOT NULL AUTO_INCREMENT,
  `parentid` int(11) DEFAULT NULL,
  `orgcode` varchar(50) DEFAULT NULL,
  `orgname` varchar(50) DEFAULT NULL,
  `orgaddr` varchar(500) DEFAULT NULL,
  `orgmanager` int(11) DEFAULT NULL,
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`orgid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='机构' AUTO_INCREMENT=15 ;


/*==============================================================*/
/* Table: b_previlege                                           */
/*==============================================================*/
DROP TABLE IF EXISTS `b_previlege`;
CREATE TABLE IF NOT EXISTS `b_previlege` (
  `previlegeid` int(11) NOT NULL AUTO_INCREMENT,
  `orgid` int(11) DEFAULT NULL,
  `master` varchar(50) DEFAULT NULL,
  `mastervalue` int(11) DEFAULT NULL,
  `resource` varchar(50) DEFAULT NULL,
  `resourcevalue` int(11) DEFAULT NULL,
  `permission` varchar(50) DEFAULT NULL,
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`previlegeid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='权限表' AUTO_INCREMENT=4 ;


/*==============================================================*/
/* Table: b_role                                                */
/*==============================================================*/
DROP TABLE IF EXISTS `b_role`;
CREATE TABLE IF NOT EXISTS `b_role` (
  `roleid` int(11) NOT NULL AUTO_INCREMENT,
  `orgid` int(11) DEFAULT NULL,
  `rolecode` varchar(50) DEFAULT NULL,
  `rolename` varchar(50) DEFAULT NULL,
  `note` varchar(1000) DEFAULT NULL,
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`roleid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='角色'  AUTO_INCREMENT=8 ;


/*==============================================================*/
/* Table: b_roleuser                                            */
/*==============================================================*/
DROP TABLE IF EXISTS `b_roleuser`;
CREATE TABLE IF NOT EXISTS `b_roleuser` (
  `roleuserid` int(11) NOT NULL AUTO_INCREMENT,
  `orgid` int(11) DEFAULT NULL,
  `roleid` int(11) NOT NULL,
  `userid` int(11) NOT NULL,
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`roleuserid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='用户角色关联' AUTO_INCREMENT=3 ;

/*==============================================================*/
/* Table: b_user                                                */
/*==============================================================*/

DROP TABLE IF EXISTS `b_user`;
CREATE TABLE IF NOT EXISTS `b_user` (
  `userid` int(11) NOT NULL AUTO_INCREMENT,
  `orgid` int(11) DEFAULT NULL,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `sex` char(1) DEFAULT NULL,
  `telphone` varchar(20) DEFAULT NULL,
  `mobilephone` varchar(20) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `locked` int(1) DEFAULT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='用户表' AUTO_INCREMENT=8 ;


