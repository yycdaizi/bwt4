/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2013/1/27 18:04:50                           */
/*==============================================================*/


drop table if exists b_menu;

drop table if exists b_org;

drop table if exists b_previlege;

drop table if exists b_role;

drop table if exists b_roleuser;

drop table if exists b_user;

/*==============================================================*/
/* Table: b_menu                                                */
/*==============================================================*/
create table b_menu
(
   menuid               int not null auto_increment,
   menuname             varchar(50),
   menuurl              varchar(200),
   menuicon             varchar(200),
   primary key (menuid)
);

/*==============================================================*/
/* Table: b_org                                                 */
/*==============================================================*/
create table b_org
(
   orgid                int not null auto_increment,
   parentid             int not null,
   orgcode              varchar(50),
   orgname              varchar(50),
   orgaddr              varchar(500),
   orgmanager           int not null,
   primary key (orgid)
);

alter table b_org comment '机构';

/*==============================================================*/
/* Table: b_previlege                                           */
/*==============================================================*/
create table b_previlege
(
   previlegeid          int not null auto_increment,
   orgid                int,
   master               varchar(50),
   mastervalue          int,
   resource             varchar(50),
   resourcevalue        int,
   permission           varchar(50),
   primary key (previlegeid)
);

alter table b_previlege comment '资源权限分配表';

/*==============================================================*/
/* Table: b_role                                                */
/*==============================================================*/
create table b_role
(
   roleid               int not null auto_increment,
   orgid                int not null,
   rolecode             varchar(50),
   rolename             varchar(50),
   note                 varchar(1000),
   primary key (roleid)
);

alter table b_role comment '角色表';

/*==============================================================*/
/* Table: b_roleuser                                            */
/*==============================================================*/
create table b_roleuser
(
   roleuserid           int not null auto_increment,
   orgid                int not null,
   roleid               int not null,
   userid               int not null,
   primary key (roleuserid)
);

alter table b_roleuser comment '用户角色关联表';

/*==============================================================*/
/* Table: b_user                                                */
/*==============================================================*/
create table b_user
(
   userid               int not null auto_increment,
   orgid                int,
   username             varchar(100),
   password             varchar(100),
   sex                  char(1),
   telphone             varchar(20),
   mobilephone          varchar(20),
   email                varchar(200),
   primary key (userid)
);

alter table b_user comment '用户表';

