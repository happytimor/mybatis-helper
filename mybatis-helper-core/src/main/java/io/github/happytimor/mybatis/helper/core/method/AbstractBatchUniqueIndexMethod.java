package io.github.happytimor.mybatis.helper.core.method;

import io.github.happytimor.mybatis.helper.core.common.Constants;
import io.github.happytimor.mybatis.helper.core.common.IdType;
import io.github.happytimor.mybatis.helper.core.common.Params;
import io.github.happytimor.mybatis.helper.core.common.SqlMethod;
import io.github.happytimor.mybatis.helper.core.metadata.Result;
import io.github.happytimor.mybatis.helper.core.metadata.TableInfo;
import io.github.happytimor.mybatis.helper.core.util.SqlScriptUtils;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * Base implementation for unique-index-aware batch insert methods.
 *
 * @author chenpeng
 */
abstract class AbstractBatchUniqueIndexMethod extends AbstractMethod {
    private final SqlMethod sqlMethod;
    private final boolean generateKeys;
    private final boolean updateOnDuplicate;

    AbstractBatchUniqueIndexMethod(SqlMethod sqlMethod, boolean generateKeys, boolean updateOnDuplicate) {
        this.sqlMethod = sqlMethod;
        this.generateKeys = generateKeys;
        this.updateOnDuplicate = updateOnDuplicate;
    }

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        String valuesScript = SqlScriptUtils.convertForeach(generateValueScript(tableInfo), Params.LIST,
                null, null, null, "item", ",");
        String script;
        if (updateOnDuplicate) {
            script = String.format(sqlMethod.getSql(), parseTableName(), generateColumnScript(tableInfo),
                    valuesScript, generateUpdateScript(tableInfo));
        } else {
            script = String.format(sqlMethod.getSql(), parseTableName(), generateColumnScript(tableInfo), valuesScript);
        }
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, script, modelClass);

        KeyGenerator keyGenerator = NoKeyGenerator.INSTANCE;
        String keyProperty = null;
        String keyColumn = null;
        if (generateKeys && tableInfo.getKeyProperty() != null && !tableInfo.getKeyProperty().isEmpty()) {
            if (tableInfo.getIdType() != IdType.DYNAMIC_GENERATE) {
                keyGenerator = Jdbc3KeyGenerator.INSTANCE;
            }
            keyProperty = Params.LIST + Constants.DOT + tableInfo.getKeyProperty();
            keyColumn = tableInfo.getKeyColumn();
        }
        return addInsertMappedStatement(java.util.Map.class, sqlMethod.getMethod(), sqlSource, keyGenerator,
                keyProperty, keyColumn);
    }

    private String generateColumnScript(TableInfo tableInfo) {
        StringBuilder sql = new StringBuilder("(");
        for (Result result : tableInfo.getResultList()) {
            sql.append('`').append(result.getColumn()).append("`,");
        }
        sql.deleteCharAt(sql.length() - 1).append(')');
        return sql.toString();
    }

    private String generateValueScript(TableInfo tableInfo) {
        StringBuilder sql = new StringBuilder("(");
        for (Result result : tableInfo.getResultList()) {
            sql.append("#{item.").append(result.getProperty()).append("},");
        }
        sql.deleteCharAt(sql.length() - 1).append(')');
        return sql.toString();
    }

    private String generateUpdateScript(TableInfo tableInfo) {
        StringBuilder sql = new StringBuilder();
        for (Result result : tableInfo.getResultList()) {
            if (result.getColumn().equals(tableInfo.getKeyColumn())) {
                continue;
            }
            sql.append('`').append(result.getColumn()).append("`=VALUES(`")
                    .append(result.getColumn()).append("`),");
        }
        sql.deleteCharAt(sql.length() - 1);
        return sql.toString();
    }
}
