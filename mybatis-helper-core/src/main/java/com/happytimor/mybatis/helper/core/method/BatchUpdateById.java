package com.happytimor.mybatis.helper.core.method;

import com.happytimor.mybatis.helper.core.common.SqlMethod;
import com.happytimor.mybatis.helper.core.metadata.Result;
import com.happytimor.mybatis.helper.core.metadata.TableInfo;
import com.happytimor.mybatis.helper.core.util.SqlScriptUtils;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

/**
 * 根据主键批量更新
 *
 * @author chenpeng
 * @date 2019-08-25
 */
public class BatchUpdateById extends AbstractMethod {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {

        try {
            SqlMethod sqlMethod = SqlMethod.BATCH_UPDATE_BY_ID;

            String script = "update `" + tableInfo.getTableName() + "` " + this.generateSetScript(tableInfo) + " WHERE `" + tableInfo.getKeyProperty() + "`=#{item." + tableInfo.getKeyProperty() + "}";
            String allScript = SqlScriptUtils.convertForeach(script, "list", null, null, null, "item", ";");
            String sql = String.format(sqlMethod.getSql(), allScript);
            SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
            return this.addInsertMappedStatement(java.util.List.class, sqlMethod.getMethod(), sqlSource, new NoKeyGenerator(), null, null);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
