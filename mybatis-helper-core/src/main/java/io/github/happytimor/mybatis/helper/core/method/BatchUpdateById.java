package io.github.happytimor.mybatis.helper.core.method;

import io.github.happytimor.mybatis.helper.core.common.Params;
import io.github.happytimor.mybatis.helper.core.common.SqlMethod;
import io.github.happytimor.mybatis.helper.core.metadata.Result;
import io.github.happytimor.mybatis.helper.core.metadata.TableInfo;
import io.github.happytimor.mybatis.helper.core.util.SqlScriptUtils;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 根据主键批量更新
 *
 * @author chenpeng
 */
public class BatchUpdateById extends AbstractMethod {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlMethod sqlMethod = SqlMethod.BATCH_UPDATE_BY_ID;
        String sql = "update `" + this.parseTableName() + "` " + this.generateSetScript(tableInfo) + " WHERE `" + tableInfo.getKeyProperty() + "`=#{item." + tableInfo.getKeyProperty() + "}";
        String allScript = SqlScriptUtils.convertForeach(sql, Params.LIST, null, null, null, "item", ";");
        String script = String.format(sqlMethod.getSql(), allScript);
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, script, modelClass);
        return this.addInsertMappedStatement(java.util.List.class, sqlMethod.getMethod(), sqlSource, new NoKeyGenerator(), null, null);
    }

    private String generateSetScript(TableInfo tableInfo) {
        StringBuilder sql = new StringBuilder();
        sql.append("<set>");
        for (Result result : tableInfo.getResultList()) {
            sql.append("<if test=\"item.").append(result.getProperty()).append(" != null\">\n `").append(result.getColumn()).append("`=#{item.").append(result.getProperty()).append("},\n  </if>");
        }
        sql.append("</set>");
        return sql.toString();
    }
}
