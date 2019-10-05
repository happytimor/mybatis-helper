package io.github.happytimor.mybatis.helper.multiple.database.test.config.mysql;

import io.github.happytimor.mybatis.helper.multiple.database.test.MyHelperForMultipleDatabase;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;

/**
 * @author chenpeng
 * @date 2018/8/16
 */
@Configuration
@MapperScan(basePackages = Datasource2Config.PACKAGE,
        sqlSessionFactoryRef = Datasource2Config.SQL_SESSION_FACTORY_NAME,
        sqlSessionTemplateRef = Datasource2Config.SQL_SESSION_TEMPLATE_NAME)
public class Datasource2Config {

    @Resource
    private MyHelperForMultipleDatabase myHelperForMultipleDatabase;
    private static final String DATASOURCE_NAME = "datasource2";
    /**
     * mapper扫描路径
     */
    static final String PACKAGE = "io.github.happytimor.mybatis.helper.multiple.database.test.mapper." + DATASOURCE_NAME;

    /**
     * xml扫描路径
     */
    private static final String MAPPER_LOCATION = "classpath*:mybatis/mapper/" + DATASOURCE_NAME + "/*.xml";

    /**
     * 数据源配置路径
     */
    private static final String CONFIG_PATH = "spring.datasource." + DATASOURCE_NAME;

    /**
     * 数据源 SqlSessionFactory 名称
     */
    static final String SQL_SESSION_FACTORY_NAME = DATASOURCE_NAME + "SqlSessionFactory";

    /**
     * 数据源 SqlSessionTemplate 名称
     */
    static final String SQL_SESSION_TEMPLATE_NAME = DATASOURCE_NAME + "SqlSessionTemplate";


    /**
     * 数据源名称
     */
    private static final String BEAN_NAME_DATA_SOURCE = DATASOURCE_NAME + "DataSource";

    /**
     * 数据源 TransactionManager 名称
     */
    private static final String BEAN_NAME_TRANSACTION_MANAGER = DATASOURCE_NAME + "TransactionManager";


    @Bean(name = BEAN_NAME_DATA_SOURCE)
    @ConfigurationProperties(CONFIG_PATH)
    public DataSource initDataSource() {
        return DataSourceBuilder.create().build();

    }

    @Bean(name = BEAN_NAME_TRANSACTION_MANAGER)
    public DataSourceTransactionManager initTransactionManager(@Qualifier(BEAN_NAME_DATA_SOURCE) DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = SQL_SESSION_FACTORY_NAME)
    public SqlSessionFactory initSqlSessionFactory(@Qualifier(BEAN_NAME_DATA_SOURCE) DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(Datasource2Config.MAPPER_LOCATION));

        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        sessionFactory.setConfiguration(configuration);

        return sessionFactory.getObject();
    }

    @Bean(name = SQL_SESSION_TEMPLATE_NAME)
    public SqlSessionTemplate initSqlSessionTemplate(@Qualifier(SQL_SESSION_FACTORY_NAME) SqlSessionFactory sqlSessionFactory) throws IOException {
        //注入操作
        myHelperForMultipleDatabase.regist(sqlSessionFactory, PACKAGE);
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
