package io.github.happytimor.mybatis.helper.core.method;

import io.github.happytimor.mybatis.helper.core.common.Params;
import io.github.happytimor.mybatis.helper.core.common.SqlMethod;
import io.github.happytimor.mybatis.helper.core.metadata.TableInfo;
import io.github.happytimor.mybatis.helper.core.util.SqlScriptUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 根据主键批量删除
 *
 * @author chenpeng
 */
public class DeleteByIdList extends AbstractMethod {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlMethod sqlMethod = SqlMethod.DELETE_BY_ID_LIST;
        String script = String.format(sqlMethod.getSql(), this.parseTableName(), "`" + tableInfo.getKeyColumn() + "`",
                SqlScriptUtils.convertForeach("#{item}", Params.ID_LIST, "(", ")", null, "item", ","));
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, script, Object.class);
        return this.addDeleteMappedStatement(sqlMethod.getMethod(), sqlSource);
    }
}
