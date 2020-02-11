package io.github.happytimor.mybatis.helper.core.method;

import io.github.happytimor.mybatis.helper.core.common.Params;
import io.github.happytimor.mybatis.helper.core.common.SqlMethod;
import io.github.happytimor.mybatis.helper.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 自定义条件更新
 *
 * @author chenpeng
 */
public class Update extends AbstractMethod {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlMethod sqlMethod = SqlMethod.UPDATE;
        String script = String.format(sqlMethod.getSql(), this.parseTableName(), "${" + Params.WRAPPER + ".setSegment}", "${" + Params.WRAPPER + ".whereSegment}");
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, script, modelClass);
        return addUpdateMappedStatement(modelClass, sqlMethod.getMethod(), sqlSource);
    }
}
