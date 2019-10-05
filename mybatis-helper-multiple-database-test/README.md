# 多数据源测试

1. 数据库配置写在 `application.yml` 文件里面,需要注意的是`jdbc-url` 和 `driver-class-name` 这两个关键字与但数据库配置不一样
2. `MyHelperForMultipleDatabase.java` 里面不需要进行任何注入操作,注入操作放在了各个数据源的config文件里面了
3. 注入操作见 DataSource1Config.java 里面的 `myHelperForMultipleDatabase.regist(sqlSessionFactory, PACKAGE)`