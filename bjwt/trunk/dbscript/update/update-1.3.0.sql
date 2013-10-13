--更新时间:2013-10-06
--病案表添加状态字段，0 - 草稿；1 - 完成
ALTER TABLE `b_wt4` ADD COLUMN `state` VARCHAR(1) NOT NULL DEFAULT '1';