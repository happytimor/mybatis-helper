# 需要创建3个database(demo,database1,database2), 然后使用下面的sql直接建表即可

-- 单数据源和多数据源测试都会用到(SingleDatabaseBasicUseTests.java)
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
CREATE TABLE `user_no_key`
(
    `name`     varchar(32)    DEFAULT '',
    `age`      int(11)        DEFAULT '-1',
    `married`  tinyint(4)     DEFAULT '0',
    `user_grade` int(11)               DEFAULT '0',
    `birthday` timestamp NULL DEFAULT '2000-01-01 00:00:00'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;