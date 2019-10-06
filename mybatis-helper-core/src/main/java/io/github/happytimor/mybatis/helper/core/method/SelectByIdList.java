package io.github.happytimor.mybatis.helper.core.method;

import io.github.happytimor.mybatis.helper.core.common.SqlMethod;
import io.github.happytimor.mybatis.helper.core.metadata.TableInfo;
import io.github.happytimor.mybatis.helper.core.util.ColumnUtils;
import io.github.happytimor.mybatis.helper.core.util.SqlScriptUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 根据主键id批量查找
 *
 * @author chenpeng
 * @date 2019-08-21
 */
public class SelectByIdList extends AbstractMethod {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlMethod sqlMethod = SqlMethod.SELECT_BY_ID_LIST;
        String script = String.format(sqlMethod.getSql(), ColumnUtils.getAllColumnStr(tableInfo),
                this.parseTableName(), tableInfo.getKeyColumn(),
                SqlScriptUtils.convertForeach("#{item}", "idList", "(", ")", null, "item", ","));
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, script, Object.class);
        return this.addMappedStatement(sqlMethod.getMethod(), sqlSource, tableInfo.getModelClass());
    }
}
