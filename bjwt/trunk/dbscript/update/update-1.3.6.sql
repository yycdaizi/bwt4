-- 2014-01-12 机构添加区域代码；用户添加姓名字段
ALTER TABLE `b_org` ADD COLUMN `zone_code` VARCHAR(30);
ALTER TABLE `b_user` ADD COLUMN `display_name` VARCHAR(100);

SET SQL_SAFE_UPDATES=0;
update `b_user` set `display_name`=username where display_name is null;