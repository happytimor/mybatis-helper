package io.github.happytimor.mybatis.helper.core.method;

import io.github.happytimor.mybatis.helper.core.common.Params;
import io.github.happytimor.mybatis.helper.core.common.SqlMethod;
import io.github.happytimor.mybatis.helper.core.metadata.TableInfo;
import io.github.happytimor.mybatis.helper.core.util.ColumnUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 根据主键查找
 *
 * @author chenpeng
 */
public class SelectSingleValue extends AbstractMethod {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlMethod sqlMethod = SqlMethod.SELECT_SINGLE_VALUE;
        String script = String.format(sqlMethod.getSql(),
                "${" + Params.WRAPPER + ".selectSegment}",
                this.parseTableName(),
                "${" + Params.WRAPPER + ".tableAliasSegment}",
                "${" + Params.WRAPPER + ".joinSegment}",
                "${" + Params.WRAPPER + ".whereSegment}",
                "${" + Params.WRAPPER + ".groupSegment}",
                "${" + Params.WRAPPER + ".orderSegment}",
                "${" + Params.WRAPPER + ".havingSegment}",
                "${" + Params.WRAPPER + ".limitSegment}");
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, script, Object.class);
        return this.addMappedStatement(sqlMethod.getMethod(), sqlSource, Object.class);
    }


}
