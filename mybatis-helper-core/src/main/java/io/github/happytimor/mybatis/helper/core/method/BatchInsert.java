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
 * 批量插入方法
 *
 * @author chenpeng
 */
public class BatchInsert extends AbstractMethod {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlMethod sqlMethod = SqlMethod.BATCH_INSERT;
        String valuesScript = SqlScriptUtils.convertForeach(this.generateValueScript(tableInfo), Params.LIST, null, null, null, "item", ",");
        String script = String.format(sqlMethod.getSql(), this.parseTableName(), generateColumnScript(tableInfo), valuesScript);
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, script, modelClass);
        return this.addInsertMappedStatement(java.util.Map.class, sqlMethod.getMethod(), sqlSource, new NoKeyGenerator(), null, null);
    }

    private String generateColumnScript(TableInfo tableInfo) {
        StringBuilder sql = new StringBuilder();
        sql.append("(");
        for (Result result : tableInfo.getResultList()) {
            sql.append("`").append(result.getColumn()).append("`").append(",");
        }
        sql.delete(sql.length() - 1, sql.length());
        sql.append(")");
        return sql.toString();
    }

    private String generateValueScript(TableInfo tableInfo) {
        StringBuilder sql = new StringBuilder();
        sql.append("(");
        for (Result result : tableInfo.getResultList()) {
            sql.append("#{item.").append(result.getProperty()).append("},");
        }
        sql.delete(sql.length() - 1, sql.length());
        sql.append(")");
        return sql.toString();
    }
}
