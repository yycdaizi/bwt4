-- 添加ZA相关字段
ALTER TABLE `b_wt4` ADD COLUMN `zone_code` VARCHAR(6);
ALTER TABLE `b_wt4` ADD COLUMN `org_code` VARCHAR(9);
ALTER TABLE `b_wt4` ADD COLUMN `org_name` VARCHAR(200);

SET SQL_SAFE_UPDATES=0;
update `dic_item` set `text`='不能正常入组' where type_id=3 and `code`='0000';

-- 2013-12-04 病案添加“验证失败”的状态
INSERT INTO `dic_item` (`type_id`,`code`,`text`,`create_time`,`description`) VALUES (1,'3','验证失败','2013-10-20 14:12:40','验证失败');


commit;