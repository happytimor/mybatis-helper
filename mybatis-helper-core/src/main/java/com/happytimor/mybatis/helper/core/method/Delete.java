package com.happytimor.mybatis.helper.core.method;

import com.happytimor.mybatis.helper.core.common.SqlMethod;
import com.happytimor.mybatis.helper.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 自定义条件删除
 *
 * @author chenpeng
 * @date 2019-08-25
 */
public class Delete extends AbstractMethod {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        String sql;
        SqlMethod sqlMethod = SqlMethod.DELETE;
        sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), "${whereSegment}");
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, Object.class);
        return this.addDeleteMappedStatement(sqlMethod.getMethod(), sqlSource);
    }
}
