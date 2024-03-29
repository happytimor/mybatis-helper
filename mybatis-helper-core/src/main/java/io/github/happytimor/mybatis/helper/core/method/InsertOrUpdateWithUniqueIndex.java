package io.github.happytimor.mybatis.helper.core.method;

import io.github.happytimor.mybatis.helper.core.common.IdType;
import io.github.happytimor.mybatis.helper.core.common.Params;
import io.github.happytimor.mybatis.helper.core.common.SqlMethod;
import io.github.happytimor.mybatis.helper.core.mapper.MultipleTableMapper;
import io.github.happytimor.mybatis.helper.core.metadata.Result;
import io.github.happytimor.mybatis.helper.core.metadata.TableInfo;
import io.github.happytimor.mybatis.helper.core.util.SqlScriptUtils;
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
 */
public class InsertOrUpdateWithUniqueIndex extends AbstractMethod {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlMethod sqlMethod = SqlMethod.INSERT_OR_UPDATE_WITH_UNIQUE_INDEX;

        String columnScript = SqlScriptUtils.wrapTrim(this.generateColumnScript(tableInfo), "(", ")", ",");
        String valuesScript = SqlScriptUtils.wrapTrim(this.generateValueScript(tableInfo), "(", ")", ",");
        String updateScript = SqlScriptUtils.wrapTrim(this.generateUpdateScript(tableInfo), "", "", ",");

        String script = String.format(sqlMethod.getSql(), this.parseTableName(), columnScript, valuesScript, updateScript);
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, script, modelClass);
        KeyGenerator keyGenerator = NoKeyGenerator.INSTANCE;
        String keyProperty = null;
        String keyColumn = null;
        // 表包含主键处理逻辑,如果不包含主键当普通字段处理
        if (tableInfo.getKeyProperty() != null && tableInfo.getKeyProperty().length() > 0) {
            if (tableInfo.getIdType() != IdType.DYNAMIC_GENERATE) {
                keyGenerator = Jdbc3KeyGenerator.INSTANCE;
            }
            boolean splitTable = MultipleTableMapper.class.isAssignableFrom(mapperClass);
            keyProperty = tableInfo.getKeyProperty();
            if (splitTable) {
                keyProperty = Params.ENTITY + "." + keyProperty;
            }
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

    private String generateUpdateScript(TableInfo tableInfo) {
        StringBuilder stringBuilder = new StringBuilder();

        for (Result result : tableInfo.getResultList()) {
            //过滤主键
            if (Objects.equals(result.getColumn(), tableInfo.getKeyColumn())) {
                continue;
            }
            stringBuilder.append("<if test=\"" + Params.ENTITY + ".").append(result.getProperty()).append(" != null\">`").append(result.getColumn()).append("` = #{" + Params.ENTITY + ".").append(result.getProperty()).append("},</if>\n");
        }
        return stringBuilder.toString();
    }
}
