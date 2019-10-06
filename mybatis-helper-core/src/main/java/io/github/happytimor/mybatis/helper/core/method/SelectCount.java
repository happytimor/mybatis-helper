package io.github.happytimor.mybatis.helper.core.method;

import io.github.happytimor.mybatis.helper.core.common.SqlMethod;
import io.github.happytimor.mybatis.helper.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 条数查询
 *
 * @author chenpeng
 * @date 2019-08-25
 */
public class SelectCount extends AbstractMethod {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlMethod sqlMethod = SqlMethod.SELECT_COUNT;
        String script = String.format(sqlMethod.getSql(), this.parseTableName(), "${wrapper.whereSegment}");
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, script, Object.class);
        return this.addMappedStatement(sqlMethod.getMethod(), sqlSource, java.lang.Long.class);
    }
}
