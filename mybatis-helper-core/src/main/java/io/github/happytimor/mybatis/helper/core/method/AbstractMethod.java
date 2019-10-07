package io.github.happytimor.mybatis.helper.core.method;

import io.github.happytimor.mybatis.helper.core.common.Params;
import io.github.happytimor.mybatis.helper.core.metadata.Result;
import io.github.happytimor.mybatis.helper.core.metadata.TableInfo;
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

    /**
     * add
     *
     * @param id         ignore
     * @param sqlSource  ignore
     * @param resultType ignore
     * @return ignore
     */
    MappedStatement addMappedStatement(String id, SqlSource sqlSource, Class<?> resultType) {
        return addMappedStatement(id, sqlSource, SqlCommandType.SELECT, null, null, resultType, new NoKeyGenerator(), null, null);
    }

    /**
     * 删除
     *
     * @param id        ignore
     * @param sqlSource ignore
     * @return ignore
     */
    MappedStatement addDeleteMappedStatement(String id, SqlSource sqlSource) {
        return addMappedStatement(id, sqlSource, SqlCommandType.DELETE, null, null, Integer.class, new NoKeyGenerator(), null, null);
    }

    /**
     * 更新
     *
     * @param parameterType ignore
     * @param id            ignore
     * @param sqlSource     ignore
     * @return ignore
     */
    MappedStatement addUpdateMappedStatement(Class<?> parameterType, String id, SqlSource sqlSource) {
        return addMappedStatement(id, sqlSource, SqlCommandType.UPDATE, parameterType, null, Integer.class, new NoKeyGenerator(), null, null);
    }

    /**
     * 插入
     *
     * @param parameterType ignore
     * @param id            ignore
     * @param sqlSource     ignore
     * @param keyGenerator  ignore
     * @param keyProperty   ignore
     * @param keyColumn     ignore
     * @return ignore
     */
    MappedStatement addInsertMappedStatement(Class<?> parameterType, String id, SqlSource sqlSource, KeyGenerator keyGenerator, String keyProperty, String keyColumn) {
        return addMappedStatement(id, sqlSource, SqlCommandType.INSERT, parameterType, null, Integer.class, keyGenerator, keyProperty, keyColumn);
    }

    /**
     * 添加 MappedStatement 到 Mybatis 容器
     *
     * @param id             id
     * @param sqlSource      sqlSource
     * @param sqlCommandType sqlCommandType
     * @param parameterType  parameterType
     * @param resultMap      resultMap
     * @param resultType     resultType
     * @param keyGenerator   keyGenerator
     * @param keyProperty    keyProperty
     * @param keyColumn      keyColumn
     * @return mappedStatement
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

    /**
     * 生成set语句
     *
     * @param tableInfo tableInfo
     * @return set语句
     */
    String generateSingleSetSql(TableInfo tableInfo) {
        StringBuilder sql = new StringBuilder();
        sql.append("<set>");
        for (Result result : tableInfo.getResultList()) {
            sql.append("<if test=\"" + Params.ENTITY + ".").append(result.getProperty()).append(" != null\">").append("\n").append("`").append(result.getColumn()).append("`=#{" + Params.ENTITY + ".").append(result.getProperty()).append("},").append("\n").append("</if>");
        }
        sql.append("</set>");
        return sql.toString();
    }

    /**
     * 获取表名
     *
     * @return 表名
     */
    protected String parseTableName() {
        if (this.tableInfo.isMultipleTable()) {
            return this.tableInfo.getTableName() + this.tableInfo.getMultipleTableConnector() + "${" + Params.TABLE_NUM + "}";
        }
        return this.tableInfo.getTableName();
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
