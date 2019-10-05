-- 单数据源和多数据源测试都会用到
CREATE TABLE `user`
(
    `id`       int(11) unsigned NOT NULL AUTO_INCREMENT,
    `name`     varchar(32)           DEFAULT '',
    `age`      int(11)               DEFAULT '-1',
    `married`  tinyint(4)            DEFAULT '0',
    `birthday` timestamp        NULL DEFAULT '2000-01-01 00:00:00',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


-- 多数据源测试用到
CREATE TABLE `user_info`
(
    `id`       int(11) unsigned NOT NULL AUTO_INCREMENT,
    `name`     varchar(32)           DEFAULT '',
    `age`      int(11)               DEFAULT '-1',
    `married`  tinyint(4)            DEFAULT '0',
    `birthday` timestamp        NULL DEFAULT '2000-01-01 00:00:00',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;