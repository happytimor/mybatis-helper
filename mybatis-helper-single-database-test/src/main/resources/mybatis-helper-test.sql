-- # 需要创建3个database(mybatis_helper_demo,mybatis_helper_database1,mybatis_helper_database2)
-- # 直接复制sql语句执行即可, 会自动创建数据库建表
-- DROP DATABASE IF EXISTS `mybatis_helper_demo` ;
-- CREATE DATABASE `mybatis_helper_demo` ;
-- USE `mybatis_helper_demo`;

-- 单数据源和多数据源测试都会用到(SingleDatabaseBasicUseTests.java)
CREATE TABLE `user` (
  `id` int(11)  NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT '',
  `greater_then_60s_a_b_c_3_A_NN_axiba` varchar(64) DEFAULT '',
  `age` int(11) DEFAULT '-1',
  `nullable_age` int(11) DEFAULT '-1',
  `married` tinyint(4) DEFAULT '0',
  `user_grade` int(11) DEFAULT '0',
  `grade_of_math` int(11) DEFAULT '0',
  `grade_of_science` int(11) DEFAULT '0',
  `last_login_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `flag` varchar(64) DEFAULT '',
  PRIMARY KEY (`id`)
) ;

-- 多数据源测试用到(Datasource1Tests.java)
CREATE TABLE `user_info` (
  `id` int(11)  NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT '',
  `greater_then_60s_a_b_c_3_A_NN_axiba` varchar(64) DEFAULT '',
  `age` int(11) DEFAULT '-1',
  `nullable_age` int(11) DEFAULT '-1',
  `married` tinyint(4) DEFAULT '0',
  `user_grade` int(11) DEFAULT '0',
  `grade_of_math` int(11) DEFAULT '0',
  `grade_of_science` int(11) DEFAULT '0',
  `last_login_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `flag` varchar(64) DEFAULT '',
  PRIMARY KEY (`id`)
) ;

-- 分表测试用到(MultipleTableTests.java)
CREATE TABLE `user_01` (
  `id` int(11)  NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT '',
  `greater_then_60s_a_b_c_3_A_NN_axiba` varchar(64) DEFAULT '',
  `age` int(11) DEFAULT '-1',
  `nullable_age` int(11) DEFAULT '-1',
  `married` tinyint(4) DEFAULT '0',
  `user_grade` int(11) DEFAULT '0',
  `grade_of_math` int(11) DEFAULT '0',
  `grade_of_science` int(11) DEFAULT '0',
  `last_login_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `flag` varchar(64) DEFAULT '',
  PRIMARY KEY (`id`)
) ;

-- 主键名不为id测试用到(UserUidTests.java)
CREATE TABLE `user_uid` (
  `uid` int(11)  NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT '',
  `greater_then_60s_a_b_c_3_A_NN_axiba` varchar(64) DEFAULT '',
  `age` int(11) DEFAULT '-1',
  `nullable_age` int(11) DEFAULT '-1',
  `married` tinyint(4) DEFAULT '0',
  `user_grade` int(11) DEFAULT '0',
  `grade_of_math` int(11) DEFAULT '0',
  `grade_of_science` int(11) DEFAULT '0',
  `last_login_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `flag` varchar(64) DEFAULT '',
  PRIMARY KEY (`uid`)
) ;

-- 无主键测试(UserNoKeyTests.java)
CREATE TABLE `user_no_key` (
  `name` varchar(64) DEFAULT '',
  `greater_then_60s_a_b_c_3_A_NN_axiba` varchar(64) DEFAULT '',
  `age` int(11) DEFAULT '-1',
  `nullable_age` int(11) DEFAULT '-1',
  `married` tinyint(4) DEFAULT '0',
  `user_grade` int(11) DEFAULT '0',
  `grade_of_math` int(11) DEFAULT '0',
  `grade_of_science` int(11) DEFAULT '0',
  `last_login_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `flag` varchar(64) DEFAULT ''
) ;

-- JOIN 测试
CREATE TABLE `course_info` (
  `id` bigint(20)  NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT '' COMMENT '课程名称',
  `teacher_id` int(11) DEFAULT '0' COMMENT '老师id',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '是否已删除',
  PRIMARY KEY (`id`)
) ;

CREATE TABLE `student` (
  `id` bigint(20)  NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `teacher_id` int(11) DEFAULT NULL,
  `best_course_id` int(11) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ;

CREATE TABLE `teacher_info` (
  `id` bigint(20)  NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(32) DEFAULT '' COMMENT '老师名称',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除',
  `offline` tinyint(1) DEFAULT '0' COMMENT '是否请假',
  PRIMARY KEY (`id`)
) ;

-- CREATE TABLE `user_unique_index` (
--   `id` bigint(20)  NOT NULL AUTO_INCREMENT,
--   `card_no` varchar(32) DEFAULT NULL,
--   `name` varchar(32) DEFAULT NULL,
--   PRIMARY KEY (`id`),
--   UNIQUE KEY `uni_card_no` (`card_no`)
-- ) ;

CREATE TABLE `user_unique_index_01` (
  `id` bigint(20)  NOT NULL AUTO_INCREMENT,
  `card_no` varchar(32) DEFAULT NULL,
  `name` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_card_no` (`card_no`) 
) ;


INSERT INTO `course_info` (`id`, `name`, `teacher_id`, `deleted`) VALUES
('1', '语文', '1', '0'),
('2', '数学', '2', '0'),
('3', '英语', '3', '0'),
('5', '化学', '5', '0'),
('8', '物理', '4', '0');

INSERT INTO `student` (`id`, `name`, `age`, `teacher_id`, `deleted`) VALUES
('1', 'zhangsan', '20', '1', '0'),
('2', 'lisi', '21', '0', '0'),
('3', 'wangwu', '22', '3', '1'),
('4', 'zhaoliu', '23', '4', '0'),
('5', 'tianqi', '23', '9', '0');

INSERT INTO `teacher_info` (`id`, `name`, `deleted`, `offline`) VALUES
('1', '史密斯', '0', '1'),
('2', '武则天', '0', '0'),
('3', '老子', '1', '0'),
('4', '孔子', '0', '0'),
('5', '孟子', '0', '0');

-- DROP DATABASE IF  EXISTS `mybatis_helper_database1` ;
-- CREATE DATABASE `mybatis_helper_database1` ;
-- USE `mybatis_helper_database1`;
-- CREATE TABLE `user` LIKE `mybatis_helper_demo`.`user`;
-- CREATE TABLE `user_info` LIKE `mybatis_helper_demo`.`user_info`;
-- CREATE TABLE `user_01` LIKE `mybatis_helper_demo`.`user_01`;
-- CREATE TABLE `user_uid` LIKE `mybatis_helper_demo`.`user_uid`;
-- CREATE TABLE `user_no_key` LIKE `mybatis_helper_demo`.`user_no_key`;
--
-- DROP DATABASE IF  EXISTS `mybatis_helper_database2` ;
-- CREATE DATABASE `mybatis_helper_database2` ;
-- USE `mybatis_helper_database2`;
-- CREATE TABLE `user` LIKE `mybatis_helper_demo`.`user`;
-- CREATE TABLE `user_info` LIKE `mybatis_helper_demo`.`user_info`;
-- CREATE TABLE `user_01` LIKE `mybatis_helper_demo`.`user_01`;
-- CREATE TABLE `user_uid` LIKE `mybatis_helper_demo`.`user_uid`;
-- CREATE TABLE `user_no_key` LIKE `mybatis_helper_demo`.`user_no_key`;