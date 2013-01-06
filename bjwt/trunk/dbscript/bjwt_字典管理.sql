/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50515
Source Host           : localhost:3306
Source Database       : bjwt

Target Server Type    : MYSQL
Target Server Version : 50515
File Encoding         : 65001

Date: 2013-01-06 22:48:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `dic_item`
-- ----------------------------
DROP TABLE IF EXISTS `dic_item`;
CREATE TABLE `dic_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type_id` int(11) NOT NULL COMMENT '字典类型',
  `code` varchar(30) NOT NULL COMMENT '字典数据项编码',
  `text` varchar(60) DEFAULT NULL COMMENT '数据项显示值',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `description` varchar(100) DEFAULT NULL COMMENT '数据项描述',
  PRIMARY KEY (`id`),
  KEY `fk_dic_item_type_idx` (`type_id`),
  CONSTRAINT `fk_dic_item_type` FOREIGN KEY (`type_id`) REFERENCES `dic_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据字典的数据项';

-- ----------------------------
-- Records of dic_item
-- ----------------------------

-- ----------------------------
-- Table structure for `dic_type`
-- ----------------------------
DROP TABLE IF EXISTS `dic_type`;
CREATE TABLE `dic_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code` varchar(30) DEFAULT NULL COMMENT '编码',
  `name` varchar(60) DEFAULT NULL COMMENT '字典名称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COMMENT='数据字典类型';
