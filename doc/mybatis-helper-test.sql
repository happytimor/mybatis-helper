# 需要创建3个database(mybatis_helper_demo,mybatis_helper_database1,mybatis_helper_database2)
# 直接复制sql语句执行即可, 会自动创建数据库建表
DROP DATABASE IF EXISTS `mybatis_helper_demo` ;
CREATE DATABASE `mybatis_helper_demo` ;
USE `mybatis_helper_demo`;

-- 单数据源和多数据源测试都会用到(SingleDatabaseBasicUseTests.java)
CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 多数据源测试用到(Datasource1Tests.java)
CREATE TABLE `user_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 分表测试用到(MultipleTableTests.java)
CREATE TABLE `user_01` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 主键名不为id测试用到(UserUidTests.java)
CREATE TABLE `user_uid` (
  `uid` int(11) unsigned NOT NULL AUTO_INCREMENT,
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP DATABASE IF  EXISTS `mybatis_helper_database1` ;
CREATE DATABASE `mybatis_helper_database1` ;
USE `mybatis_helper_database1`;
CREATE TABLE `user` LIKE `mybatis_helper_demo`.`user`;
CREATE TABLE `user_info` LIKE `mybatis_helper_demo`.`user_info`;
CREATE TABLE `user_01` LIKE `mybatis_helper_demo`.`user_01`;
CREATE TABLE `user_uid` LIKE `mybatis_helper_demo`.`user_uid`;
CREATE TABLE `user_no_key` LIKE `mybatis_helper_demo`.`user_no_key`;

DROP DATABASE IF  EXISTS `mybatis_helper_database2` ;
CREATE DATABASE `mybatis_helper_database2` ;
USE `mybatis_helper_database2`;
CREATE TABLE `user` LIKE `mybatis_helper_demo`.`user`;
CREATE TABLE `user_info` LIKE `mybatis_helper_demo`.`user_info`;
CREATE TABLE `user_01` LIKE `mybatis_helper_demo`.`user_01`;
CREATE TABLE `user_uid` LIKE `mybatis_helper_demo`.`user_uid`;
CREATE TABLE `user_no_key` LIKE `mybatis_helper_demo`.`user_no_key`;