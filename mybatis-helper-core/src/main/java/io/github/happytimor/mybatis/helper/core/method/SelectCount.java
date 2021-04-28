package io.github.happytimor.mybatis.helper.core.method;

import io.github.happytimor.mybatis.helper.core.common.Params;
import io.github.happytimor.mybatis.helper.core.common.SqlMethod;
import io.github.happytimor.mybatis.helper.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 条数查询
 *
 * @author chenpeng
 */
public class SelectCount extends AbstractMethod {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlMethod sqlMethod = SqlMethod.SELECT_COUNT;
        String script = String.format(sqlMethod.getSql(),
                this.parseTableName(),
                "${" + Params.WRAPPER + ".tableAliasSegment}",
                "${" + Params.WRAPPER + ".joinSegment}",
                "${" + Params.WRAPPER + ".whereSegment}",
                "${" + Params.WRAPPER + ".groupSegment}",
                "${" + Params.WRAPPER + ".havingSegment}",
                "${" + Params.WRAPPER + ".orderSegment}",
                "${" + Params.WRAPPER + ".limitSegment}");
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, script, Object.class);
        return this.addMappedStatement(sqlMethod.getMethod(), sqlSource, java.lang.Long.class);
    }
}
