package io.github.happytimor.mybatis.helper.core.metadata;

import org.apache.ibatis.type.JdbcType;

/**
 * relation between database table and java object
 *
 * @author chenpeng
 */
public class Result {
    /**
     * java object property name
     */
    private String property;
    /**
     * database column name
     */
    private String column;
    /**
     * java object type
     */
    private Class<?> javaType;
    /**
     * database jdbc type
     */
    private JdbcType jdbcType;

    /**
     * override column name
     */
    private boolean overrideColumn;

    public Result() {

    }

    public Result(String property, String column) {
        this.property = property;
        this.column = column;
    }

    public Result(String property, String column, boolean overrideColumn) {
        this.property = property;
        this.column = column;
        this.overrideColumn = overrideColumn;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public Class<?> getJavaType() {
        return javaType;
    }

    public void setJavaType(Class<?> javaType) {
        this.javaType = javaType;
    }

    public JdbcType getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(JdbcType jdbcType) {
        this.jdbcType = jdbcType;
    }

    public boolean isOverrideColumn() {
        return overrideColumn;
    }

    public void setOverrideColumn(boolean overrideColumn) {
        this.overrideColumn = overrideColumn;
    }
}
