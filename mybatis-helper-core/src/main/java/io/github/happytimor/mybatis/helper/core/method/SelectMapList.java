package io.github.happytimor.mybatis.helper.core.method;

import io.github.happytimor.mybatis.helper.core.common.Params;
import io.github.happytimor.mybatis.helper.core.common.SqlMethod;
import io.github.happytimor.mybatis.helper.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.util.Map;

/**
 * map list查询
 *
 * @author chenpeng
 */
public class SelectMapList extends AbstractMethod {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlMethod sqlMethod = SqlMethod.SELECT_MAP_LIST;
        String script = String.format(sqlMethod.getSql(),
                "${" + Params.WRAPPER + ".selectSegment}",
                this.parseTableName(),
                "${" + Params.WRAPPER + ".tableAliasSegment}",
                "${" + Params.WRAPPER + ".joinSegment}",
                "${" + Params.WRAPPER + ".whereSegment}",
                "${" + Params.WRAPPER + ".groupSegment}",
                "${" + Params.WRAPPER + ".havingSegment}",
                "${" + Params.WRAPPER + ".orderSegment}",
                "${" + Params.WRAPPER + ".limitSegment}");
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, script, Object.class);
        return this.addMappedStatement(sqlMethod.getMethod(), sqlSource, Map.class);
    }
}
