package io.github.happytimor.mybatis.helper.core.wrapper;


import io.github.happytimor.mybatis.helper.core.common.Params;
import io.github.happytimor.mybatis.helper.core.metadata.ColumnFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenpeng
 */
public class UpdateWrapper<T> extends WhereWrapper<T> {

    private List<String> set = new ArrayList<>();

    /**
     * 设置数据库字段值
     *
     * @param executeIf 是否执行
     * @param column    字段
     * @param value     设置值
     * @return updateWrapper
     */
    public UpdateWrapper<T> set(boolean executeIf, ColumnFunction<T, ?> column, Object value) {
        if (executeIf) {
            String columnName = this.getColumnName(column, false);
            int count = counter.incrementAndGet();
            String key = "params_" + count + "_" + columnName;
            paramNameValuePairs.put(key, value);
            set.add(wrapColumnName(columnName) + " = #{" + Params.WRAPPER + ".paramNameValuePairs." + key + "}");
        }
        return this;
    }

    /**
     * 设置数据库字段值
     *
     * @param column 字段
     * @param value  设置值
     * @return updateWrapper
     */
    public UpdateWrapper<T> set(ColumnFunction<T, ?> column, Object value) {
        return this.set(true, column, value);
    }

    /**
     * 字段自增操作
     *
     * @param column 字段名称
     * @param value  值
     * @return UpdateWrapper 对象
     */
    public UpdateWrapper<T> plus(ColumnFunction<T, ?> column, Object value) {
        return this.plus(true, column, value);
    }

    /**
     * 字段自减操作
     *
     * @param column 字段名称
     * @param value  值
     * @return UpdateWrapper 对象
     */
    public UpdateWrapper<T> minus(ColumnFunction<T, ?> column, Object value) {
        return this.minus(true, column, value);
    }

    /**
     * 字段自增操作
     *
     * @param executeIf 是否执行
     * @param column    字段名称
     * @param value     值
     * @return UpdateWrapper 对象
     */
    public UpdateWrapper<T> plus(boolean executeIf, ColumnFunction<T, ?> column, Object value) {
        return this.atomicOperation(executeIf, column, "+", value);
    }

    /**
     * 字段自减操作
     *
     * @param executeIf 是否执行
     * @param column    字段名称
     * @param value     值
     * @return UpdateWrapper 对象
     */
    public UpdateWrapper<T> minus(boolean executeIf, ColumnFunction<T, ?> column, Object value) {
        return this.atomicOperation(executeIf, column, "-", value);
    }

    public String getSetSegment() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SET ");
        for (String s : set) {
            stringBuilder.append(s).append(", ");
        }
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        return stringBuilder.toString();
    }

    private UpdateWrapper<T> atomicOperation(boolean executeIf, ColumnFunction<T, ?> column, String operation, Object value) {
        if (executeIf) {
            String columnName = this.getColumnName(column, false);
            int count = counter.incrementAndGet();
            String key = "params_" + count + "_" + columnName;
            paramNameValuePairs.put(key, value);
            set.add(wrapColumnName(columnName) + " = " + wrapColumnName(columnName) + " " + operation + " #{" + Params.WRAPPER + ".paramNameValuePairs." + key + "}");
        }
        return this;
    }
}
