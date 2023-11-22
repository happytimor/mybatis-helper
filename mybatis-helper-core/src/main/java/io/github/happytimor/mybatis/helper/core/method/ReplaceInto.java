package io.github.happytimor.mybatis.helper.core.method;

import io.github.happytimor.mybatis.helper.core.common.Params;
import io.github.happytimor.mybatis.helper.core.common.SqlMethod;
import io.github.happytimor.mybatis.helper.core.metadata.Result;
import io.github.happytimor.mybatis.helper.core.metadata.TableInfo;
import io.github.happytimor.mybatis.helper.core.util.SqlScriptUtils;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 插入时唯一索引冲突，直接替换(will not return id)
 *
 * @author chenpeng
 */
public class ReplaceInto extends AbstractMethod {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlMethod sqlMethod = SqlMethod.REPLACE_INTO;

        String columnScript = SqlScriptUtils.wrapTrim(this.generateColumnScript(tableInfo), "(", ")",
                ",");
        String valuesScript = SqlScriptUtils.wrapTrim(this.generateValueScript(tableInfo), "(", ")",
                ",");


        String script = String.format(sqlMethod.getSql(), this.parseTableName(), columnScript, valuesScript);
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, script, modelClass);
        KeyGenerator keyGenerator = NoKeyGenerator.INSTANCE;
        return this.addInsertMappedStatement(modelClass, sqlMethod.getMethod(), sqlSource, keyGenerator, null, null);
    }

    private String generateColumnScript(TableInfo tableInfo) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Result result : tableInfo.getResultList()) {
            if (result.getProperty().equals(tableInfo.getKeyProperty())) {
                stringBuilder.append(result.getColumn()).append(",\n");
            } else {
                stringBuilder
                        .append("<if test=\"" + Params.ENTITY + ".")
                        .append(result.getProperty())
                        .append(" != null\">`")
                        .append(result.getColumn())
                        .append("`,</if>\n");
            }
        }
        return stringBuilder.toString();
    }

    private String generateValueScript(TableInfo tableInfo) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Result result : tableInfo.getResultList()) {
            if (result.getProperty().equals(tableInfo.getKeyProperty())) {
                stringBuilder.append("#{" + Params.ENTITY + ".").append(result.getProperty()).append("},\n");
            } else {
                stringBuilder
                        .append("<if test=\"" + Params.ENTITY + ".")
                        .append(result.getProperty())
                        .append(" != null\">#{" + Params.ENTITY + ".")
                        .append(result.getProperty())
                        .append("},</if>\n");
            }
        }
        return stringBuilder.toString();
    }
}
