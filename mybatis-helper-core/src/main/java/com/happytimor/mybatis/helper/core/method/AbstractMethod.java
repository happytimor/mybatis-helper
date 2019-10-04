package com.happytimor.mybatis.helper.core.method;

import com.happytimor.mybatis.helper.core.metadata.Result;
import com.happytimor.mybatis.helper.core.metadata.TableInfo;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 抽象方法, 将 injectMappedStatement 交由子类实现
 *
 * @author chenpeng
 * @date 2019-08-21
 */
public abstract class AbstractMethod {
    private final static Logger logger = LoggerFactory.getLogger(AbstractMethod.class);

    protected Configuration configuration;
    private MapperBuilderAssistant builderAssistant;
    protected LanguageDriver languageDriver;

    protected TableInfo tableInfo;

    public void inject(MapperBuilderAssistant builderAssistant, Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        this.builderAssistant = builderAssistant;
        this.configuration = builderAssistant.getConfiguration();
        this.languageDriver = configuration.getDefaultScriptingLanguageInstance();
        this.tableInfo = tableInfo;

        injectMappedStatement(mapperClass, modelClass, tableInfo);
    }

    MappedStatement addMappedStatement(String id, SqlSource sqlSource, Class<?> resultType) {
        return addMappedStatement(id, sqlSource, SqlCommandType.SELECT, null, null, resultType, new NoKeyGenerator(), null, null);
    }

    /**
     * 删除
     */
    MappedStatement addDeleteMappedStatement(String id, SqlSource sqlSource) {
        return addMappedStatement(id, sqlSource, SqlCommandType.DELETE, null, null, Integer.class, new NoKeyGenerator(), null, null);
    }

    /**
     * 更新
     */
    MappedStatement addUpdateMappedStatement(Class<?> parameterType, String id, SqlSource sqlSource) {
        return addMappedStatement(id, sqlSource, SqlCommandType.UPDATE, parameterType, null, Integer.class, new NoKeyGenerator(), null, null);
    }

    /**
     * 插入
     */
    MappedStatement addInsertMappedStatement(Class<?> parameterType, String id, SqlSource sqlSource, KeyGenerator keyGenerator, String keyProperty, String keyColumn) {
        return addMappedStatement(id, sqlSource, SqlCommandType.INSERT, parameterType, null, Integer.class, keyGenerator, keyProperty, keyColumn);
    }

    /**
     * 添加 MappedStatement 到 Mybatis 容器
     */
    private MappedStatement addMappedStatement(String id, SqlSource sqlSource,
                                               SqlCommandType sqlCommandType, Class<?> parameterType,
                                               String resultMap, Class<?> resultType, KeyGenerator keyGenerator,
                                               String keyProperty, String keyColumn) {
        /* 缓存逻辑处理 */
        boolean isSelect = false;
        if (sqlCommandType == SqlCommandType.SELECT) {
            isSelect = true;
        }
        return builderAssistant.addMappedStatement(id, sqlSource, StatementType.PREPARED, sqlCommandType,
                null, null, null, parameterType, resultMap, resultType,
                null, !isSelect, isSelect, false, keyGenerator, keyProperty, keyColumn,
                configuration.getDatabaseId(), languageDriver, null);
    }

    String generateSingleSetSql(TableInfo tableInfo) {
        StringBuilder sql = new StringBuilder();
        sql.append("<set>");
        for (Result result : tableInfo.getResultList()) {
            sql.append("<if test=\"").append(result.getProperty()).append(" != null\">").append("\n").append("`").append(result.getColumn()).append("`=#{").append(result.getProperty()).append("},").append("\n").append("</if>");
        }
        sql.append("</set>");
        return sql.toString();
    }

    /**
     * 注入自定义 MappedStatement
     *
     * @param mapperClass mapper 接口
     * @param modelClass  mapper 泛型
     * @param tableInfo   数据库表反射信息
     * @return MappedStatement
     */
    public abstract MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo);

}
