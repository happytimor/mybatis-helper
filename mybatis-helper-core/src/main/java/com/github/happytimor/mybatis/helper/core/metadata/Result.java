package com.github.happytimor.mybatis.helper.core.metadata;

import org.apache.ibatis.type.JdbcType;

/**
 * 数据库与对象的映射
 *
 * @author chenpeng
 * @date 2019-08-25
 */
public class Result {
    /**
     * 字段名称
     */
    private String property;
    /**
     * 对应数据库字段名称
     */
    private String column;
    /**
     * java类型
     */
    private Class<?> javaType;
    /**
     * 数据库类型
     */
    private JdbcType jdbcType;

    public Result() {

    }

    public Result(String property, String column) {
        this.property = property;
        this.column = column;
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
}
