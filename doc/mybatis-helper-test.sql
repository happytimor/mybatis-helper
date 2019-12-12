# 需要创建3个database(mybatis_helper_demo,mybatis_helper_database1,mybatis_helper_database2)
# 直接复制sql语句执行即可, 会自动创建数据库建表

CREATE DATABASE IF NOT EXISTS `mybatis_helper_demo` ;
USE `mybatis_helper_demo`;

-- 单数据源和多数据源测试都会用到(SingleDatabaseBasicUseTests.java)
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`         int(11) unsigned NOT NULL AUTO_INCREMENT,
    `name`       varchar(32)           DEFAULT '',
    `age`        int(11)               DEFAULT '-1',
    `married`    tinyint(4)            DEFAULT '0',
    `user_grade` int(11)               DEFAULT '0',
    `birthday`   timestamp        NULL DEFAULT '2000-01-01 00:00:00',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- 多数据源测试用到(Datasource1Tests.java)
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`
(
    `id`       int(11) unsigned NOT NULL AUTO_INCREMENT,
    `name`     varchar(32)           DEFAULT '',
    `age`      int(11)               DEFAULT '-1',
    `married`  tinyint(4)            DEFAULT '0',
    `user_grade` int(11)               DEFAULT '0',
    `birthday` timestamp        NULL DEFAULT '2000-01-01 00:00:00',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- 分表测试用到(MultipleTableTests.java)
DROP TABLE IF EXISTS `user_01`;
CREATE TABLE `user_01`
(
    `id`       int(11) unsigned NOT NULL AUTO_INCREMENT,
    `name`     varchar(32)           DEFAULT '',
    `age`      int(11)               DEFAULT '-1',
    `married`  tinyint(4)            DEFAULT '0',
    `user_grade` int(11)               DEFAULT '0',
    `birthday` timestamp        NULL DEFAULT '2000-01-01 00:00:00',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- 主键名不为id测试用到(UserUidTests.java)
DROP TABLE IF EXISTS `user_uid`;
CREATE TABLE `user_uid`
(
    `uid`      int(11) unsigned NOT NULL AUTO_INCREMENT,
    `name`     varchar(32)           DEFAULT '',
    `age`      int(11)               DEFAULT '-1',
    `married`  tinyint(4)            DEFAULT '0',
    `user_grade` int(11)               DEFAULT '0',
    `birthday` timestamp        NULL DEFAULT '2000-01-01 00:00:00',
    PRIMARY KEY (`uid`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- 无主键测试(UserNoKeyTests.java)
DROP TABLE IF EXISTS `user_no_key`;
CREATE TABLE `user_no_key`
(
    `name`     varchar(32)    DEFAULT '',
    `age`      int(11)        DEFAULT '-1',
    `married`  tinyint(4)     DEFAULT '0',
    `user_grade` int(11)               DEFAULT '0',
    `birthday` timestamp NULL DEFAULT '2000-01-01 00:00:00'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE DATABASE IF NOT EXISTS `mybatis_helper_database1` ;
USE `mybatis_helper_database1`;
CREATE TABLE `user` LIKE `mybatis_helper_demo`.`user`;
CREATE TABLE `user_info` LIKE `mybatis_helper_demo`.`user_info`;
CREATE TABLE `user_01` LIKE `mybatis_helper_demo`.`user_01`;
CREATE TABLE `user_uid` LIKE `mybatis_helper_demo`.`user_uid`;
CREATE TABLE `user_no_key` LIKE `mybatis_helper_demo`.`user_no_key`;

CREATE DATABASE IF NOT EXISTS `mybatis_helper_database2` ;
USE `mybatis_helper_database2`;
CREATE TABLE `user` LIKE `mybatis_helper_demo`.`user`;
CREATE TABLE `user_info` LIKE `mybatis_helper_demo`.`user_info`;
CREATE TABLE `user_01` LIKE `mybatis_helper_demo`.`user_01`;
CREATE TABLE `user_uid` LIKE `mybatis_helper_demo`.`user_uid`;
CREATE TABLE `user_no_key` LIKE `mybatis_helper_demo`.`user_no_key`;