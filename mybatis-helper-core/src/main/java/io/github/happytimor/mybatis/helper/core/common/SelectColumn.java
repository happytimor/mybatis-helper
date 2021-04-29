package io.github.happytimor.mybatis.helper.core.common;

import io.github.happytimor.mybatis.helper.core.metadata.ColumnFunction;

/**
 * @author chenpeng
 */
public class SelectColumn {
    private ColumnFunction<?, ?> columnFunction;
    private Class<?> clazz;
    private String columnName;

    public SelectColumn() {

    }

    public SelectColumn(ColumnFunction<?, ?> columnFunction) {
        this.columnFunction = columnFunction;
    }

    public SelectColumn(Class<?> clazz, String columnName) {
        this.clazz = clazz;
        this.columnName = columnName;
    }

    public ColumnFunction<?, ?> getColumnFunction() {
        return columnFunction;
    }

    public void setColumnFunction(ColumnFunction<?, ?> columnFunction) {
        this.columnFunction = columnFunction;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
}
