package io.github.happytimor.mybatis.helper.multiple.database.test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 初始化h2数据库
 *
 * @author gaomingyuan
 */
@Service
@AutoConfigureAfter(DataSource.class) //DataSource创建完后才初始化此类
public class H2DataSourceConfig {
    //初始化sql
    private static final String schema = "classpath:mybatis-helper-test.sql";

    @Autowired
    @Qualifier("datasource1DataSource") // 注入 datasource1
    private DataSource dataSource1;

    @Autowired
    @Qualifier("datasource2DataSource") // 注入 datasource2
    private DataSource dataSource2;

    @Autowired
    private ApplicationContextRegister applicationContextRegister; //自定义注册器

    @PostConstruct
    public void init() throws Exception {
        System.out.println("--------------初始化h2数据----------------------");
        // 加载资源文件
        Resource dbSql = applicationContextRegister.getResource(schema);

        // 手动执行SQL语句
        try (Connection conn1 = dataSource1.getConnection();
             Connection conn2 = dataSource2.getConnection()) {
            ScriptUtils.executeSqlScript(conn1, dbSql);
            ScriptUtils.executeSqlScript(conn2, dbSql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to execute SQL script", e);
        }
    }
}
