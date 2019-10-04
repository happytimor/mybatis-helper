package com.happytimor.mybatis.helper.core.method;

import com.happytimor.mybatis.helper.core.common.SqlMethod;
import com.happytimor.mybatis.helper.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 自定义条件更新
 *
 * @author chenpeng
 * @date 2019-08-25
 */
public class Update extends AbstractMethod {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlMethod sqlMethod = SqlMethod.UPDATE;
        String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), "${setSegment}", "${whereSegment}");
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return addUpdateMappedStatement(modelClass, sqlMethod.getMethod(), sqlSource);
    }
}
