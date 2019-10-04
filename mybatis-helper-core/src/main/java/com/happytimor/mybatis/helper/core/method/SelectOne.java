package com.happytimor.mybatis.helper.core.method;

import com.happytimor.mybatis.helper.core.common.SqlMethod;
import com.happytimor.mybatis.helper.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 只返回一条数据(加了一个limit1)
 *
 * @author chenpeng
 * @date 2019-08-25
 */
public class SelectOne extends AbstractMethod {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlMethod sqlMethod = SqlMethod.SELECT_ONE;
        String sql = String.format(sqlMethod.getSql(), "${selectSegment}", tableInfo.getTableName(), "${whereSegment}");
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, Object.class);
        return this.addMappedStatement(sqlMethod.getMethod(), sqlSource, tableInfo.getModelClass());
    }
}
