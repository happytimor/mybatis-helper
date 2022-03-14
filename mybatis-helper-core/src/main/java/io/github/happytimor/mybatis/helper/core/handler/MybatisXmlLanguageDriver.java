package io.github.happytimor.mybatis.helper.core.handler;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;

/**
 * @author chenpeng
 */
public class MybatisXmlLanguageDriver extends XMLLanguageDriver {
    @Override
    public ParameterHandler createParameterHandler(MappedStatement mappedStatement,
                                                   Object parameterObject, BoundSql boundSql) {
        return new MybatisParameterHandler(mappedStatement, parameterObject, boundSql);
    }

    @Override
    public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {
        return super.createSqlSource(configuration, script, parameterType);
    }
}
