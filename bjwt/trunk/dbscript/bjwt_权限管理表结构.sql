/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2013/1/7 21:17:32                            */
/*==============================================================*/


drop table if exists b_org;

drop table if exists b_previlege;

drop table if exists b_role;

drop table if exists b_roleuser;

drop table if exists b_user;

/*==============================================================*/
/* Table: b_org                                                 */
/*==============================================================*/
create table b_org
(
   orgid                int not null,
   orgcode              varchar(50),
   orgname              varchar(50),
   orgaddr              varchar(500),
   primary key (orgid)
);

alter table b_org comment '机构';

/*==============================================================*/
/* Table: b_previlege                                           */
/*==============================================================*/
create table b_previlege
(
   previlegeid          int not null,
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
   roleid               int not null,
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
   roleuserid           int not null,
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
   userid               int not null,
   orgid                int,
   username             varchar(100),
   password             varchar(100),
   sex                  char(1),
   telphone             varchar(20),
   primary key (userid)
);

alter table b_user comment '用户表';

