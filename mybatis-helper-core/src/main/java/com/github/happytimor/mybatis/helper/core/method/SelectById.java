package com.github.happytimor.mybatis.helper.core.method;

import com.github.happytimor.mybatis.helper.core.common.SqlMethod;
import com.github.happytimor.mybatis.helper.core.metadata.TableInfo;
import com.github.happytimor.mybatis.helper.core.util.ColumnUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.defaults.RawSqlSource;

/**
 * 根据主键查找
 *
 * @author chenpeng
 * @date 2019-08-21
 */
public class SelectById extends AbstractMethod {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlMethod sqlMethod = SqlMethod.SELECT_BY_ID;
        SqlSource sqlSource = new RawSqlSource(configuration,
                String.format(sqlMethod.getSql(), ColumnUtils.getAllColumnStr(tableInfo), tableInfo.getTableName(), tableInfo.getKeyColumn(), tableInfo.getKeyProperty()),
                Object.class);
        return this.addMappedStatement(sqlMethod.getMethod(), sqlSource, tableInfo.getModelClass());
    }


}
