package io.github.happytimor.mybatis.helper.core.handler;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;

/**
 * @author chenpeng
 */
public class MybatisParameterHandler extends DefaultParameterHandler {
    public MybatisParameterHandler(MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql) {
        super(mappedStatement, parameterObject, boundSql);
        IdentifierGeneratorHandler idHandler = new IdentifierGeneratorHandler(mappedStatement.getConfiguration());
        idHandler.processParameter(parameterObject, mappedStatement.getSqlCommandType());
    }
}
