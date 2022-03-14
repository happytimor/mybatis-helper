package io.github.happytimor.mybatis.helper.core.method;

import io.github.happytimor.mybatis.helper.core.common.Constants;
import io.github.happytimor.mybatis.helper.core.common.Params;
import io.github.happytimor.mybatis.helper.core.common.SqlMethod;
import io.github.happytimor.mybatis.helper.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 根据主键进行更新
 *
 * @author chenpeng
 */
public class UpdateById extends AbstractMethod {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlMethod sqlMethod = SqlMethod.UPDATE_BY_ID;
        String script = String.format(sqlMethod.getSql(), this.parseTableName(),
                generateSingleSetSql(tableInfo),
                tableInfo.getKeyColumn(), Params.ENTITY + Constants.DOT + tableInfo.getKeyProperty());
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, script, modelClass);
        return addUpdateMappedStatement(modelClass, sqlMethod.getMethod(), sqlSource);
    }
}
