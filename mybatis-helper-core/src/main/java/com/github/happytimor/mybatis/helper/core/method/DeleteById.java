package com.github.happytimor.mybatis.helper.core.method;

import com.github.happytimor.mybatis.helper.core.common.SqlMethod;
import com.github.happytimor.mybatis.helper.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 根据主键单条删除
 *
 * @author chenpeng
 * @date 2019-08-25
 */
public class DeleteById extends AbstractMethod {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        String sql;
        SqlMethod sqlMethod = SqlMethod.DELETE_BY_ID;
        sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), tableInfo.getKeyColumn(),
                tableInfo.getKeyProperty());
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, Object.class);
        return this.addDeleteMappedStatement(sqlMethod.getMethod(), sqlSource);
    }
}
