package io.github.happytimor.mybatis.helper.core.wrapper;


import io.github.happytimor.mybatis.helper.core.common.DiySql;
import io.github.happytimor.mybatis.helper.core.common.Operation;
import io.github.happytimor.mybatis.helper.core.common.Params;
import io.github.happytimor.mybatis.helper.core.metadata.ColumnFunction;
import io.github.happytimor.mybatis.helper.core.metadata.Condition;
import io.github.happytimor.mybatis.helper.core.util.ColumnUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

/**
 * @author chenpeng
 */
public class UpdateWrapper<T> extends WhereWrapper<T> {

    private final List<String> set = new ArrayList<>();

    /**
     * 设置自定义sql
     *
     * @param execute  是否执行
     * @param function 函数表达式
     * @param column   column
     * @return updateWrapper
     */
    public UpdateWrapper<T> setDiySql(boolean execute, ColumnFunction<T, ?> column, Function<UpdateWrapper<T>, DiySql<T>> function) {
        if (execute) {
            DiySql<?> diySql = function.apply(this);
            String sql = diySql.getSql();
            String columnName = ColumnUtils.getColumnName(column, true);

            this.set.add(columnName + " = (" + sql + ")");
        }
        return this;
    }

    /**
     * 设置自定义sql
     *
     * @param function 函数表达式
     * @param column   column
     * @return updateWrapper
     */
    public UpdateWrapper<T> setDiySql(ColumnFunction<T, ?> column, Function<UpdateWrapper<T>, DiySql<T>> function) {
        return this.setDiySql(true, column, function);
    }

    /**
     * 设置数据库字段值
     *
     * @param column 字段
     * @param value  设置值
     * @return updateWrapper
     */
    public UpdateWrapper<T> set(ColumnFunction<T, ?> column, String value) {
        return this.set(true, column, value);
    }

    /**
     * 设置数据库字段值
     *
     * @param execute 是否执行
     * @param column  字段
     * @param value   设置值
     * @return updateWrapper
     */
    public UpdateWrapper<T> set(boolean execute, ColumnFunction<T, ?> column, String value) {
        return this.setValue(execute, column, value);
    }


    /**
     * 设置数据库字段值
     *
     * @param column 字段
     * @param value  设置值
     * @return updateWrapper
     */
    public UpdateWrapper<T> set(ColumnFunction<T, ?> column, Number value) {
        return this.set(true, column, value);
    }

    /**
     * 设置数据库字段值
     *
     * @param execute 是否执行
     * @param column  字段
     * @param value   设置值
     * @return updateWrapper
     */
    public UpdateWrapper<T> set(boolean execute, ColumnFunction<T, ?> column, Number value) {
        return this.setValue(execute, column, value);
    }

    /**
     * 设置数据库字段值
     *
     * @param column 字段
     * @param value  设置值
     * @return updateWrapper
     */
    public UpdateWrapper<T> set(ColumnFunction<T, ?> column, Date value) {
        return this.set(true, column, value);
    }

    /**
     * 设置数据库字段值
     *
     * @param execute 是否执行
     * @param column  字段
     * @param value   设置值
     * @return updateWrapper
     */
    public UpdateWrapper<T> set(boolean execute, ColumnFunction<T, ?> column, Date value) {
        return this.setValue(execute, column, value);
    }

    /**
     * 设置数据库字段值
     *
     * @param column 字段
     * @param value  设置值
     * @return updateWrapper
     */
    public UpdateWrapper<T> set(ColumnFunction<T, ?> column, LocalDate value) {
        return this.set(true, column, value);
    }

    /**
     * 设置数据库字段值
     *
     * @param execute 是否执行
     * @param column  字段
     * @param value   设置值
     * @return updateWrapper
     */
    public UpdateWrapper<T> set(boolean execute, ColumnFunction<T, ?> column, LocalDate value) {
        return this.setValue(execute, column, value);
    }

    /**
     * 设置数据库字段值
     *
     * @param column 字段
     * @param value  设置值
     * @return updateWrapper
     */
    public UpdateWrapper<T> set(ColumnFunction<T, ?> column, LocalDateTime value) {
        return this.set(true, column, value);
    }

    /**
     * 设置数据库字段值
     *
     * @param execute 是否执行
     * @param column  字段
     * @param value   设置值
     * @return updateWrapper
     */
    public UpdateWrapper<T> set(boolean execute, ColumnFunction<T, ?> column, LocalDateTime value) {
        return this.setValue(execute, column, value);
    }

    /**
     * 设置数据库字段值
     *
     * @param column 字段
     * @param value  设置值
     * @return updateWrapper
     */
    public UpdateWrapper<T> set(ColumnFunction<T, ?> column, Boolean value) {
        return this.set(true, column, value);
    }

    /**
     * 设置数据库字段值
     *
     * @param execute 是否执行
     * @param column  字段
     * @param value   设置值
     * @return updateWrapper
     */
    public UpdateWrapper<T> set(boolean execute, ColumnFunction<T, ?> column, Boolean value) {
        return this.setValue(execute, column, value);
    }

    /**
     * 设置数据库字段值
     *
     * @param execute   是否执行
     * @param column    字段
     * @param operation 设置值
     * @return updateWrapper
     */
    public UpdateWrapper<T> set(boolean execute, ColumnFunction<T, ?> column, Operation<?> operation) {
        return this.setColumnValue(execute, column, operation);
    }


    /**
     * 设置数据库字段值
     *
     * @param column    字段
     * @param operation 设置值
     * @return updateWrapper
     */
    public UpdateWrapper<T> set(ColumnFunction<T, ?> column, Operation<?> operation) {
        return this.set(true, column, operation);
    }


    /**
     * 设置数据库字段值
     *
     * @param column 字段
     * @param value  设置值
     * @return updateWrapper
     */
    public UpdateWrapper<T> setObject(ColumnFunction<T, ?> column, Object value) {
        return this.setObject(true, column, value);
    }

    /**
     * 设置数据库字段值
     *
     * @param execute 是否执行
     * @param column  字段
     * @param value   设置值
     * @return updateWrapper
     */
    public UpdateWrapper<T> setObject(boolean execute, ColumnFunction<T, ?> column, Object value) {
        return this.setValue(execute, column, value);
    }

    /**
     * 字段自增操作
     *
     * @param column 字段名称
     * @param value  值
     * @return UpdateWrapper 对象
     */
    public UpdateWrapper<T> plus(ColumnFunction<T, ?> column, Number value) {
        return this.plus(true, column, value);
    }

    /**
     * 字段自减操作
     *
     * @param column 字段名称
     * @param value  值
     * @return UpdateWrapper 对象
     */
    public UpdateWrapper<T> minus(ColumnFunction<T, ?> column, Number value) {
        return this.minus(true, column, value);
    }

    /**
     * 字段自增操作
     *
     * @param execute 是否执行
     * @param column  字段名称
     * @param value   值
     * @return UpdateWrapper 对象
     */
    public UpdateWrapper<T> plus(boolean execute, ColumnFunction<T, ?> column, Number value) {
        return this.atomicOperation(execute, column, "+", value);
    }

    /**
     * 字段自减操作
     *
     * @param execute 是否执行
     * @param column  字段名称
     * @param value   值
     * @return UpdateWrapper 对象
     */
    public UpdateWrapper<T> minus(boolean execute, ColumnFunction<T, ?> column, Number value) {
        return this.atomicOperation(execute, column, "-", value);
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

    private UpdateWrapper<T> atomicOperation(boolean execute, ColumnFunction<T, ?> column, String operation, Object value) {
        if (execute) {
            String columnName = ColumnUtils.getColumnName(column, false);
            int count = counter.incrementAndGet();
            String key = "params_" + count + "_" + columnName;
            paramNameValuePairs.put(key, value);
            set.add(wrapColumnName(columnName) + " = " + wrapColumnName(columnName) + " " + operation + " #{" + Params.WRAPPER + ".paramNameValuePairs." + key + "}");
        }
        return this;
    }

    /**
     * 设置数据库字段值
     *
     * @param execute 是否执行
     * @param column  字段
     * @param value   设置值
     * @return updateWrapper
     */
    private UpdateWrapper<T> setValue(boolean execute, ColumnFunction<T, ?> column, Object value) {
        if (execute) {
            String columnName = ColumnUtils.getColumnName(column, false);
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
     * @param execute 是否执行
     * @param column  字段
     * @return updateWrapper
     */
    private UpdateWrapper<T> setColumnValue(boolean execute, ColumnFunction<T, ?> column, Operation<?> operation) {
        if (execute) {
            String columnName = ColumnUtils.getColumnName(column, false);
            set.add(wrapColumnName(columnName) + " = " + operation.getOperationStr());
        }
        return this;
    }
}
