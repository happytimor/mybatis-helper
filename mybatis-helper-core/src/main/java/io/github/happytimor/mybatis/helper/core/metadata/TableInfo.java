package io.github.happytimor.mybatis.helper.core.metadata;

import java.util.List;

/**
 * @author chenpeng
 * @date 2019-08-21
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
     * java字段映射sql字段
     */
    private List<Result> resultList;


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
}
