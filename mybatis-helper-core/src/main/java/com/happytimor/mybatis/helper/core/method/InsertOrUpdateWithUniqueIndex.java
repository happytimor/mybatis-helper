package com.happytimor.mybatis.helper.core.method;

import com.happytimor.mybatis.helper.core.util.SqlScriptUtils;
import com.happytimor.mybatis.helper.core.common.SqlMethod;
import com.happytimor.mybatis.helper.core.metadata.Result;
import com.happytimor.mybatis.helper.core.metadata.TableInfo;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.util.Objects;

/**
 * 插入时唯一索引冲突，插入改成更新
 *
 * @author chenpeng
 * @date 2019-09-03
 */
public class InsertOrUpdateWithUniqueIndex extends AbstractMethod {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlMethod sqlMethod = SqlMethod.INSERT_OR_UPDATE_WITH_UNIQUE_INDEX;

        String columnScript = SqlScriptUtils.wrapTrim(this.generateColumnScript(tableInfo), "(", ")", ",");
        String valuesScript = SqlScriptUtils.wrapTrim(this.generateValueScript(tableInfo), "(", ")", ",");
        String updateScript = SqlScriptUtils.wrapTrim(this.generateUpdateScript(tableInfo), "", "", ",");

        String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), columnScript, valuesScript, updateScript);
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        KeyGenerator keyGenerator = new NoKeyGenerator();
        String keyProperty = null;
        String keyColumn = null;
        // 表包含主键处理逻辑,如果不包含主键当普通字段处理
        if (tableInfo.getKeyProperty() != null && tableInfo.getKeyProperty().length() > 0) {
            keyGenerator = new Jdbc3KeyGenerator();
            keyProperty = tableInfo.getKeyProperty();
            keyColumn = tableInfo.getKeyColumn();
        }

        return this.addInsertMappedStatement(modelClass, sqlMethod.getMethod(), sqlSource, keyGenerator, keyProperty, keyColumn);
    }

    private String generateColumnScript(TableInfo tableInfo) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Result result : tableInfo.getResultList()) {
            stringBuilder.append("<if test=\"").append(result.getProperty()).append(" != null\">`").append(result.getColumn()).append("`,</if>\n");
        }
        return stringBuilder.toString();
    }

    private String generateValueScript(TableInfo tableInfo) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Result result : tableInfo.getResultList()) {
            stringBuilder.append("<if test=\"").append(result.getProperty()).append(" != null\">#{").append(result.getProperty()).append("},</if>\n");
        }
        return stringBuilder.toString();
    }

    private String generateUpdateScript(TableInfo tableInfo) {
        StringBuilder stringBuilder = new StringBuilder();

        for (Result result : tableInfo.getResultList()) {
            //过滤主键
            if (Objects.equals(result.getColumn(), tableInfo.getKeyColumn())) {
                continue;
            }
            stringBuilder.append("<if test=\"").append(result.getProperty()).append(" != null\">`").append(result.getColumn()).append("` = #{").append(result.getProperty()).append("},</if>\n");
        }
        return stringBuilder.toString();
    }
}
