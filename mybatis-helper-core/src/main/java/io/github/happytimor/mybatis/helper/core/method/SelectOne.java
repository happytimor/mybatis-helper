package io.github.happytimor.mybatis.helper.core.method;

import io.github.happytimor.mybatis.helper.core.common.Params;
import io.github.happytimor.mybatis.helper.core.common.SqlMethod;
import io.github.happytimor.mybatis.helper.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 只返回一条数据(加了一个limit1)
 *
 * @author chenpeng
 */
public class SelectOne extends AbstractMethod {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlMethod sqlMethod = SqlMethod.SELECT_ONE;
        String script = String.format(sqlMethod.getSql(),
                "${" + Params.WRAPPER + ".selectSegment}",
                this.parseTableName(),
                "${" + Params.WRAPPER + ".tableAliasSegment}",
                "${" + Params.WRAPPER + ".joinSegment}",
                "${" + Params.WRAPPER + ".whereSegment}",
                "${" + Params.WRAPPER + ".groupSegment}",
                "${" + Params.WRAPPER + ".havingSegment}",
                "${" + Params.WRAPPER + ".orderSegment}",
                "${" + Params.WRAPPER + ".forUpdateSegment}");
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, script, Object.class);
        return this.addMappedStatement(sqlMethod.getMethod(), sqlSource, tableInfo.getModelClass());
    }
}
