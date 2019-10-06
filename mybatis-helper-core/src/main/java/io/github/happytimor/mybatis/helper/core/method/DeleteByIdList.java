package io.github.happytimor.mybatis.helper.core.method;

import io.github.happytimor.mybatis.helper.core.common.SqlMethod;
import io.github.happytimor.mybatis.helper.core.metadata.TableInfo;
import io.github.happytimor.mybatis.helper.core.util.SqlScriptUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 根据主键批量删除
 *
 * @author chenpeng
 * @date 2019-08-25
 */
public class DeleteByIdList extends AbstractMethod {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        String sql;
        SqlMethod sqlMethod = SqlMethod.DELETE_BY_ID_LIST;
        sql = String.format(sqlMethod.getSql(), this.parseTableName(), tableInfo.getKeyColumn(),
                SqlScriptUtils.convertForeach("#{item}", "idList", "(", ")", null, "item", ","));
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, Object.class);
        return this.addDeleteMappedStatement(sqlMethod.getMethod(), sqlSource);
    }
}
