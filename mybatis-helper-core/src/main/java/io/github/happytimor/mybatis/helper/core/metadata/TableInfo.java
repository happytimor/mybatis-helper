package io.github.happytimor.mybatis.helper.core.metadata;

import io.github.happytimor.mybatis.helper.core.common.IdType;

import java.util.List;

/**
 * database table info
 *
 * @author chenpeng
 */
public class TableInfo {
    private Class<?> modelClass;

    private String tableName;
    /**
     * 表主键ID 字段名
     */
    private String keyColumn;

    /**
     * 表主键ID 属性名
     */
    private String keyProperty;
    /**
     * 主键id类型
     */
    private Class<?> keyClass;
    /**
     * 主键生成策略
     */
    private IdType idType;
    /**
     * 主键区分
     */
    private String identity;
    /**
     * java字段映射sql字段
     */
    private List<Result> resultList;

    /**
     * 需要做映射干预
     */
    private boolean overrideColumn;
    /**
     * 是否分表, user_01,user_02
     */
    private boolean multipleTable;
    /**
     * 分表连接符
     */
    private String multipleTableConnector;

    public Class<?> getModelClass() {
        return modelClass;
    }

    public void setModelClass(Class<?> modelClass) {
        this.modelClass = modelClass;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getKeyColumn() {
        return keyColumn;
    }

    public void setKeyColumn(String keyColumn) {
        this.keyColumn = keyColumn;
    }

    public String getKeyProperty() {
        return keyProperty;
    }

    public void setKeyProperty(String keyProperty) {
        this.keyProperty = keyProperty;
    }

    public List<Result> getResultList() {
        return resultList;
    }

    public void setResultList(List<Result> resultList) {
        this.resultList = resultList;
    }

    public boolean isMultipleTable() {
        return multipleTable;
    }

    public void setMultipleTable(boolean multipleTable) {
        this.multipleTable = multipleTable;
    }

    public String getMultipleTableConnector() {
        return multipleTableConnector;
    }

    public void setMultipleTableConnector(String multipleTableConnector) {
        this.multipleTableConnector = multipleTableConnector;
    }

    public boolean isOverrideColumn() {
        return overrideColumn;
    }

    public void setOverrideColumn(boolean overrideColumn) {
        this.overrideColumn = overrideColumn;
    }

    public Class<?> getKeyClass() {
        return keyClass;
    }

    public void setKeyClass(Class<?> keyClass) {
        this.keyClass = keyClass;
    }

    public IdType getIdType() {
        return idType;
    }

    public void setIdType(IdType idType) {
        this.idType = idType;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }
}
