package io.github.happytimor.mybatis.helper.core.method;

import io.github.happytimor.mybatis.helper.core.common.Params;
import io.github.happytimor.mybatis.helper.core.common.SqlMethod;
import io.github.happytimor.mybatis.helper.core.mapper.MultipleTableMapper;
import io.github.happytimor.mybatis.helper.core.mapper.NoPrimaryKeyMapper;
import io.github.happytimor.mybatis.helper.core.metadata.Result;
import io.github.happytimor.mybatis.helper.core.metadata.TableInfo;
import io.github.happytimor.mybatis.helper.core.util.SqlScriptUtils;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 单条插入方法
 *
 * @author chenpeng
 */
public class Insert extends AbstractMethod {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlMethod sqlMethod = SqlMethod.INSERT_ONE;

        String columnScript = SqlScriptUtils.wrapTrim(this.generateColumnScript(tableInfo), "(", ")", ",");
        String valuesScript = SqlScriptUtils.wrapTrim(this.generateValueScript(tableInfo), "(", ")", ",");


        String script = String.format(sqlMethod.getSql(), this.parseTableName(), columnScript, valuesScript);
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, script, modelClass);
        KeyGenerator keyGenerator = new NoKeyGenerator();
        String keyProperty = null;
        String keyColumn = null;
        // 表包含主键处理逻辑,如果不包含主键当普通字段处理
        if (tableInfo.getKeyProperty() != null && tableInfo.getKeyProperty().length() > 0) {
            keyGenerator = new Jdbc3KeyGenerator();
            keyProperty = tableInfo.getKeyProperty();
            keyProperty = Params.ENTITY + "." + keyProperty;
            keyColumn = tableInfo.getKeyColumn();
        }

        return this.addInsertMappedStatement(modelClass, sqlMethod.getMethod(), sqlSource, keyGenerator, keyProperty, keyColumn);
    }

    private String generateColumnScript(TableInfo tableInfo) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Result result : tableInfo.getResultList()) {
            stringBuilder.append("<if test=\"" + Params.ENTITY + ".").append(result.getProperty()).append(" != null\">`").append(result.getColumn()).append("`,</if>\n");
        }
        return stringBuilder.toString();
    }

    private String generateValueScript(TableInfo tableInfo) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Result result : tableInfo.getResultList()) {
            stringBuilder.append("<if test=\"" + Params.ENTITY + ".").append(result.getProperty()).append(" != null\">#{" + Params.ENTITY + ".").append(result.getProperty()).append("},</if>\n");
        }
        return stringBuilder.toString();
    }
}
