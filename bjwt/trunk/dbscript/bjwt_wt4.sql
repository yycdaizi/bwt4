-- phpMyAdmin SQL Dump
-- version 2.10.3
-- http://www.phpmyadmin.net
-- 
-- 主机: localhost
-- 生成日期: 2012 年 10 月 10 日 12:18
-- 服务器版本: 5.0.51
-- PHP 版本: 5.2.6

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

-- 
-- 数据库: `db_wt4`
-- 

-- --------------------------------------------------------

-- 
-- 表的结构 `b_wt4`
-- 

CREATE TABLE `b_wt4` (
  `b_wt4_id` bigint(20) NOT NULL auto_increment,
  `lots` decimal(22,0) default NULL,
  `org_id` decimal(22,0) default NULL,
  `year_time` decimal(22,0) default NULL,
  `payment_methods` decimal(22,0) default NULL,
  `inhouse_treatment` decimal(22,0) default NULL,
  `case_id` varchar(50) character set utf8 collate utf8_bin default NULL,
  `name_patient` varchar(40) character set utf8 collate utf8_bin default NULL,
  `gender` decimal(22,0) default NULL,
  `birthday` datetime default NULL,
  `age` decimal(22,0) default NULL,
  `marrage` decimal(22,0) default NULL,
  `profession_code` varchar(20) character set utf8 collate utf8_bin default NULL,
  `nationality` varchar(20) character set utf8 collate utf8_bin default NULL,
  `resident_id` varchar(20) character set utf8 collate utf8_bin default NULL,
  `insurance_code` varchar(40) character set utf8 collate utf8_bin default NULL,
  `gate_code` varchar(40) character set utf8 collate utf8_bin default NULL,
  `x_code` varchar(40) character set utf8 collate utf8_bin default NULL,
  `company_address` varchar(200) character set utf8 collate utf8_bin default NULL,
  `hukou_zone_code` varchar(20) character set utf8 collate utf8_bin default NULL,
  `hukou_address` varchar(200) character set utf8 collate utf8_bin default NULL,
  `hukou_address_postcode` varchar(20) character set utf8 collate utf8_bin default NULL,
  `contact_person` varchar(50) character set utf8 collate utf8_bin default NULL,
  `contact_address` varchar(200) character set utf8 collate utf8_bin default NULL,
  `contact_tele` varchar(30) character set utf8 collate utf8_bin default NULL,
  `nation` varchar(40) character set utf8 collate utf8_bin default NULL,
  `telenumber` varchar(40) character set utf8 collate utf8_bin default NULL,
  `postcode` varchar(40) character set utf8 collate utf8_bin default NULL,
  `relationship` varchar(40) character set utf8 collate utf8_bin default NULL,
  `addr_id` varchar(6) character set utf8 collate utf8_bin default NULL,
  `streetcodes` varchar(100) character set utf8 collate utf8_bin default NULL,
  `streetcodes2` varchar(100) character set utf8 collate utf8_bin default NULL,
  `var1` varchar(50) character set utf8 collate utf8_bin default NULL,
  `var2` varchar(150) character set utf8 collate utf8_bin default NULL,
  `var3` varchar(255) character set utf8 collate utf8_bin default NULL,
  `total_expense` decimal(10,2) default NULL,
  `bed_expense` decimal(10,2) default NULL,
  `bed_tend_expense` decimal(10,2) default NULL,
  `western_medicine_expense` decimal(10,2) default NULL,
  `chinese_medicine_expense1` decimal(10,2) default NULL,
  `chinese_medicine_expense2` decimal(10,2) default NULL,
  `radiate_expense` decimal(10,2) default NULL,
  `check_expense` decimal(10,2) default NULL,
  `oxygen_expense` decimal(10,2) default NULL,
  `transfusion_expense` decimal(10,2) default NULL,
  `examine_expense` decimal(10,2) default NULL,
  `operation_expense` decimal(10,2) default NULL,
  `deliver_expense` decimal(10,2) default NULL,
  `examine_expense1` decimal(10,2) default NULL,
  `horus_expense` decimal(10,2) default NULL,
  `child_expense` decimal(10,2) default NULL,
  `accomany_expense` decimal(10,2) default NULL,
  `temp_adjust_expense` decimal(10,2) default NULL,
  `diagnose_expense` decimal(10,2) default NULL,
  `other_expense` decimal(10,2) default NULL,
  `bloodtype` decimal(22,0) default NULL,
  `redcell` decimal(22,0) default NULL,
  `plaque` decimal(22,0) default NULL,
  `plasma` decimal(22,0) default NULL,
  `total_blood` decimal(22,0) default NULL,
  `others` decimal(22,0) default NULL,
  `date_inhospital` datetime default NULL,
  `department_code1` varchar(40) character set utf8 collate utf8_bin default NULL,
  `out_date` datetime default NULL,
  `department_code2` varchar(40) character set utf8 collate utf8_bin default NULL,
  `status_out` decimal(22,0) default NULL,
  `disease_code` varchar(100) character set utf8 collate utf8_bin default NULL,
  `diagnose_name` varchar(100) character set utf8 collate utf8_bin default NULL,
  `date_diagnose` datetime default NULL,
  `disease_code2` varchar(200) character set utf8 collate utf8_bin default NULL,
  `infected_name` varchar(200) character set utf8 collate utf8_bin default NULL,
  `treatment_effect1` varchar(100) character set utf8 collate utf8_bin default NULL,
  `diagnose_theory` varchar(50) character set utf8 collate utf8_bin default NULL,
  `diagnose_theory_name` varchar(100) character set utf8 collate utf8_bin default NULL,
  `disease_code3` varchar(50) character set utf8 collate utf8_bin default NULL,
  `external_name` varchar(100) character set utf8 collate utf8_bin default NULL,
  `salvage_times` decimal(22,0) default NULL,
  `salvage_success_times` decimal(22,0) default NULL,
  `room` varchar(40) character set utf8 collate utf8_bin default NULL,
  `room1` varchar(20) character set utf8 collate utf8_bin default NULL,
  `exchange_department_code` varchar(100) character set utf8 collate utf8_bin default NULL,
  `exchange_department` varchar(100) character set utf8 collate utf8_bin default NULL,
  `acctual_days` decimal(22,0) default NULL,
  `disease_code4` varchar(40) character set utf8 collate utf8_bin default NULL,
  `disease_code4_des` varchar(100) character set utf8 collate utf8_bin default NULL,
  `medicine_sense_name` varchar(200) character set utf8 collate utf8_bin default NULL,
  `hbsag` decimal(22,0) default NULL,
  `hcv_ab` decimal(22,0) default NULL,
  `hiv_ab` decimal(22,0) default NULL,
  `gate_out` decimal(22,0) default NULL,
  `in_out` decimal(22,0) default NULL,
  `pre_pro` decimal(22,0) default NULL,
  `practice_theory` decimal(22,0) default NULL,
  `radiation_theory` decimal(22,0) default NULL,
  `cherf_department` varchar(40) character set utf8 collate utf8_bin default NULL,
  `professor` varchar(40) character set utf8 collate utf8_bin default NULL,
  `engineer` varchar(40) character set utf8 collate utf8_bin default NULL,
  `director_doctor` varchar(40) character set utf8 collate utf8_bin default NULL,
  `doctor_name` varchar(40) character set utf8 collate utf8_bin default NULL,
  `intern_doctor_name` varchar(40) character set utf8 collate utf8_bin default NULL,
  `gradute_intern` varchar(40) character set utf8 collate utf8_bin default NULL,
  `intern` varchar(40) character set utf8 collate utf8_bin default NULL,
  `coder` varchar(40) character set utf8 collate utf8_bin default NULL,
  `case_quality` varchar(40) character set utf8 collate utf8_bin default NULL,
  `qc_doctor` varchar(40) character set utf8 collate utf8_bin default NULL,
  `qc_nurse` varchar(40) character set utf8 collate utf8_bin default NULL,
  `qc_date` datetime default NULL,
  `special_care_days` decimal(22,0) default NULL,
  `first_care_days` decimal(22,0) default NULL,
  `second_care_days` decimal(22,0) default NULL,
  `third_care_days` decimal(22,0) default NULL,
  `icu_days` decimal(22,0) default NULL,
  `ccu_days` decimal(22,0) default NULL,
  `is_bodycheck` char(1) character set utf8 collate utf8_bin default NULL,
  `is_first_time` char(1) character set utf8 collate utf8_bin default NULL,
  `is_follow` char(1) character set utf8 collate utf8_bin default NULL,
  `follow_weeks` decimal(22,0) default NULL,
  `follow_months` decimal(22,0) default NULL,
  `follow_years` decimal(22,0) default NULL,
  `is_teaching_case` char(1) character set utf8 collate utf8_bin default NULL,
  `rh` decimal(22,0) default NULL,
  `transfusion_reaction` decimal(22,0) default NULL,
  `cause_reason` varchar(200) character set utf8 collate utf8_bin default NULL,
  `fh_code` varchar(50) character set utf8 collate utf8_bin default NULL,
  `chief` varchar(20) character set utf8 collate utf8_bin default NULL,
  `typer` varchar(20) character set utf8 collate utf8_bin default NULL,
  `typer_tel` varchar(50) character set utf8 collate utf8_bin default NULL,
  `date_submit` datetime default NULL,
  `memo` varchar(500) character set utf8 collate utf8_bin default NULL,
  `month_time` decimal(22,0) default NULL,
  `season_time` decimal(22,0) default NULL,
  `report_manner` char(1) character set utf8 collate utf8_bin default 'D',
  `isread` char(1) character set utf8 collate utf8_bin default NULL,
  `status` char(2) character set utf8 collate utf8_bin default 'DR',
  `processed` char(1) character set utf8 collate utf8_bin default NULL,
  `s_tasks_id` decimal(22,0) default NULL,
  `s_task_orglist_id` decimal(22,0) default NULL,
  `b_first_tasklist_id` decimal(22,0) default NULL,
  `b_second_tasklist_id` decimal(22,0) default NULL,
  `b_agentys_tasklist_id` decimal(22,0) default NULL,
  `b_agentzs_tasklist_id` decimal(22,0) default NULL,
  `bt_zsys` char(1) character set utf8 collate utf8_bin default NULL,
  `bt_zsdr` char(1) character set utf8 collate utf8_bin default NULL,
  `bt_dataanalysis` char(1) character set utf8 collate utf8_bin default NULL,
  `bt_drzs` char(1) character set utf8 collate utf8_bin default NULL,
  `bt_yszs` char(1) character set utf8 collate utf8_bin default NULL,
  `returnreason` varchar(800) character set utf8 collate utf8_bin default NULL,
  `returntel` varchar(20) character set utf8 collate utf8_bin default NULL,
  `ad_org_id` bigint(10) NOT NULL default '11',
  `ad_client_id` bigint(10) NOT NULL default '11',
  `createdby` bigint(10) NOT NULL default '11',
  `updatedby` bigint(10) NOT NULL default '11',
  `created` datetime NOT NULL default '0000-00-00 00:00:00',
  `updated` datetime NOT NULL default '0000-00-00 00:00:00',
  `isactive` char(1) character set utf8 collate utf8_bin default 'Y',
  `reportchief` varchar(50) character set utf8 collate utf8_bin default NULL,
  `ispasszl` char(1) character set utf8 collate utf8_bin default 'N',
  `pathology_id` varchar(50) character set utf8 collate utf8_bin default NULL,
  `is_staging` varchar(10) character set utf8 collate utf8_bin default NULL,
  `staging_t` varchar(10) character set utf8 collate utf8_bin default NULL,
  `staging_n` varchar(10) character set utf8 collate utf8_bin default NULL,
  `staging_m` varchar(10) character set utf8 collate utf8_bin default NULL,
  `staging_seq` varchar(10) character set utf8 collate utf8_bin default NULL,
  `is_medicine_sense` varchar(10) character set utf8 collate utf8_bin default NULL,
  `ddoctor_certification` varchar(50) character set utf8 collate utf8_bin default NULL,
  `duty_nursename` varchar(20) character set utf8 collate utf8_bin default NULL,
  `inhospscore` decimal(10,2) default NULL,
  `outhospscore` decimal(10,2) default NULL,
  `selfpayment_expense` decimal(10,2) default NULL,
  `check_normal_expense` decimal(10,2) default NULL,
  `cure_clinical_expense` decimal(10,2) default NULL,
  `cure_intrude_expense` decimal(10,2) default NULL,
  `cure_special_expense` decimal(10,2) default NULL,
  `cure_recover_expense` decimal(10,2) default NULL,
  `cure_chinese_expense` decimal(10,2) default NULL,
  `cure_normal_expense` decimal(10,2) default NULL,
  `cure_mind_expense` decimal(10,2) default NULL,
  `auxiliary_expense` decimal(10,2) default NULL,
  `stuff_cure_expense` decimal(10,2) default NULL,
  `stuff_intrude_expense` decimal(10,2) default NULL,
  `stuff_surgery_expense` decimal(10,2) default NULL,
  `stuff_check_expense` decimal(10,2) default NULL,
  `register_expense` decimal(10,2) default NULL,
  `antiseptic_medicine_expense` decimal(10,2) default NULL,
  `product_albumin_expense` decimal(10,2) default NULL,
  `product_globulin_expense` decimal(10,2) default NULL,
  `product_blood_expense` decimal(10,2) default NULL,
  `product_cell_expense` decimal(10,2) default NULL,
  `check_nucleus_expense` decimal(10,2) default NULL,
  `cure_nucleus_expense` decimal(10,2) default NULL,
  `ultrasound_expense` decimal(10,2) default NULL,
  `pathology_expense` decimal(10,2) default NULL,
  `cure_tend_expense` decimal(10,2) default NULL,
  `addr_hukou_province` varchar(100) character set utf8 collate utf8_bin default NULL,
  `addr_hukou_city` varchar(100) character set utf8 collate utf8_bin default NULL,
  `addr_hukou_country` varchar(100) character set utf8 collate utf8_bin default NULL,
  `addr_present_province` varchar(100) character set utf8 collate utf8_bin default NULL,
  `addr_present_city` varchar(100) character set utf8 collate utf8_bin default NULL,
  `addr_present_country` varchar(100) character set utf8 collate utf8_bin default NULL,
  `addr_present_telnumber` varchar(100) character set utf8 collate utf8_bin default NULL,
  `addr_birth_city` varchar(100) character set utf8 collate utf8_bin default NULL,
  `addr_birth_country` varchar(100) character set utf8 collate utf8_bin default NULL,
  `addr_original_province` varchar(100) character set utf8 collate utf8_bin default NULL,
  `addr_original_city` varchar(100) character set utf8 collate utf8_bin default NULL,
  `in_hosppath` varchar(10) character set utf8 collate utf8_bin default NULL,
  `sf0100` int(4) default NULL,
  `sf0102` int(6) default NULL,
  `sf0104` bigint(10) default NULL,
  `sf0108` varchar(1) character set utf8 collate utf8_bin default NULL,
  `embed_b_wt4_icu_id` decimal(22,0) default NULL,
  `before_cerebrumhurt_days` decimal(22,0) default NULL,
  `before_cerebrumhurt_hours` decimal(22,0) default NULL,
  `before_cerebrumhurt_minutes` decimal(22,0) default NULL,
  `after_cerebrumhurt_days` decimal(22,0) default NULL,
  `after_cerebrumhurt_hours` decimal(22,0) default NULL,
  `after_cerebrumhurt_minutes` decimal(22,0) default NULL,
  `transtoorgname` varchar(100) character set utf8 collate utf8_bin default NULL,
  `is_inhosp31` varchar(10) character set utf8 collate utf8_bin default NULL,
  `inhosp31object` varchar(500) character set utf8 collate utf8_bin default NULL,
  `disease_code1` varchar(50) character set utf8 collate utf8_bin default NULL,
  `goout_diagnose_name` varchar(100) character set utf8 collate utf8_bin default NULL,
  `in_status` varchar(20) character set utf8 collate utf8_bin default NULL,
  `state` varchar(1) not null default '1',
  PRIMARY KEY  (`b_wt4_id`),
  KEY `idx_luo_insurance_code` (`insurance_code`),
  KEY `index_luo_task_id` (`s_task_orglist_id`),
  KEY `n_luo_stasksid` (`s_tasks_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=28 ;

-- --------------------------------------------------------

-- 
-- 表的结构 `b_wt4_diagnose`
-- 

CREATE TABLE `b_wt4_diagnose` (
  `b_wt4_diagnose_id` bigint(20) NOT NULL auto_increment,
  `b_wt4_id` bigint(20) NOT NULL,
  `created` datetime default NULL,
  `createdby` bigint(10) default NULL,
  `updated` datetime default NULL,
  `updatedby` bigint(10) default NULL,
  `isactive` char(1) character set utf8 collate utf8_bin default NULL,
  `diagnose_code` varchar(20) character set utf8 collate utf8_bin default NULL,
  `diagnose_name` varchar(100) character set utf8 collate utf8_bin default NULL,
  `isprimary` char(1) character set utf8 collate utf8_bin default NULL,
  `cure_effect` varchar(100) character set utf8 collate utf8_bin default NULL,
  `in_status` varchar(20) character set utf8 collate utf8_bin default NULL,
  PRIMARY KEY  (`b_wt4_diagnose_id`),
  KEY `b_wt4_v1_id` (`b_wt4_id`),
  KEY `diagnose_code` (`diagnose_code`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=53 ;

-- --------------------------------------------------------

-- 
-- 表的结构 `b_wt4_disabled`
-- 

CREATE TABLE `b_wt4_disabled` (
  `B_WT4_DISABLED_ID` bigint(20) NOT NULL auto_increment,
  `B_WT4_ID` bigint(20) default NULL,
  `DISABLED_DIAGNOSE_NAME` varchar(100) default NULL,
  `DISABLED_DIAGNOSE_CODE` varchar(100) default NULL,
  `SF0101` decimal(4,0) default NULL,
  PRIMARY KEY  (`B_WT4_DISABLED_ID`),
  KEY `B_WT4_V1_ID` (`B_WT4_ID`),
  KEY `DISABLED_DIAGNOSE_CODE` (`DISABLED_DIAGNOSE_CODE`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=41 ;

-- --------------------------------------------------------

-- 
-- 表的结构 `b_wt4_icu`
-- 

CREATE TABLE `b_wt4_icu` (
  `B_WT4_ICU_ID` bigint(20) NOT NULL auto_increment,
  `B_WT4_ID` bigint(20) default NULL,
  `UNITCODE` varchar(10) default NULL,
  `INDATE` datetime default NULL,
  `OUTDATE` datetime default NULL,
  PRIMARY KEY  (`B_WT4_ICU_ID`),
  KEY `B_WT4_V1_ID` (`B_WT4_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=49 ;

-- --------------------------------------------------------

-- 
-- 表的结构 `b_wt4_operation`
-- 

CREATE TABLE `b_wt4_operation` (
  `b_wt4_operation_id` bigint(20) NOT NULL auto_increment,
  `b_wt4_surgery_id` bigint(20) default NULL,
  `created` datetime default NULL,
  `createdby` bigint(10) default NULL,
  `updated` datetime default NULL,
  `updatedby` bigint(10) default NULL,
  `isactive` char(1) character set utf8 collate utf8_bin default NULL,
  `operation_code` varchar(20) character set utf8 collate utf8_bin default NULL,
  `operation_name` varchar(100) character set utf8 collate utf8_bin default NULL,
  `isprimary` char(1) character set utf8 collate utf8_bin default NULL,
  `isshushi` char(1) character set utf8 collate utf8_bin default NULL,
  PRIMARY KEY  (`b_wt4_operation_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=157 ;

-- --------------------------------------------------------

-- 
-- 表的结构 `b_wt4_surgery`
-- 

CREATE TABLE `b_wt4_surgery` (
  `b_wt4_surgery_id` bigint(20) NOT NULL auto_increment,
  `b_wt4_id` bigint(20) default NULL,
  `created` datetime default NULL,
  `createdby` bigint(10) default NULL,
  `updated` datetime default NULL,
  `updatedby` bigint(10) default NULL,
  `isactive` char(1) character set utf8 collate utf8_bin default NULL,
  `operation_date` datetime default NULL,
  `operation_doctor` varchar(20) character set utf8 collate utf8_bin default NULL,
  `first_assistant` varchar(20) character set utf8 collate utf8_bin default NULL,
  `second_assistant` varchar(20) character set utf8 collate utf8_bin default NULL,
  `hocus` varchar(100) character set utf8 collate utf8_bin default NULL,
  `closeup` varchar(100) character set utf8 collate utf8_bin default NULL,
  `hocus_doctor` varchar(20) character set utf8 collate utf8_bin default NULL,
  `nurse` varchar(20) character set utf8 collate utf8_bin default NULL,
  `isoperation` char(1) character set utf8 collate utf8_bin default NULL,
  `flg_index` decimal(22,0) default NULL,
  `operation_edate` datetime default NULL,
  `operation_level` varchar(10) character set utf8 collate utf8_bin default NULL,
  PRIMARY KEY  (`b_wt4_surgery_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=80 ;
