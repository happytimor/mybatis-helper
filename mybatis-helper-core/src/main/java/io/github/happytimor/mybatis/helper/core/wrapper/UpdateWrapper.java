package io.github.happytimor.mybatis.helper.core.wrapper;


import io.github.happytimor.mybatis.helper.core.common.Constants;
import io.github.happytimor.mybatis.helper.core.common.DiySql;
import io.github.happytimor.mybatis.helper.core.common.Operation;
import io.github.happytimor.mybatis.helper.core.common.Params;
import io.github.happytimor.mybatis.helper.core.metadata.ColumnFunction;
import io.github.happytimor.mybatis.helper.core.metadata.ColumnWrapper;
import io.github.happytimor.mybatis.helper.core.util.ColumnUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author chenpeng
 */
public class UpdateWrapper<T> extends WhereWrapper<T> {

    private final List<String> set = new ArrayList<>();

    /**
     * set customized sql
     *
     * @param execute  will execute method if `execute` is true
     * @param function value function
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
     * set customized sql
     *
     * @param function value function
     * @param column   column
     * @return updateWrapper
     */
    public UpdateWrapper<T> setDiySql(ColumnFunction<T, ?> column, Function<UpdateWrapper<T>, DiySql<T>> function) {
        return this.setDiySql(true, column, function);
    }

    /**
     * set value for one column
     *
     * @param column database column
     * @param value  string value
     * @return updateWrapper
     */
    public UpdateWrapper<T> set(ColumnFunction<T, ?> column, String value) {
        return this.set(true, column, value);
    }

    /**
     * set value for one column only if the value is not blank
     *
     * @param column database column
     * @param value  string value
     * @return updateWrapper
     */
    public UpdateWrapper<T> setNotBlank(ColumnFunction<T, ?> column, String value) {
        return this.set(!StringUtils.isEmpty(value), column, value);
    }

    /**
     * set value for one column
     *
     * @param execute will execute method if `execute` is true
     * @param column  database column
     * @param value   string value
     * @return updateWrapper
     */
    public UpdateWrapper<T> set(boolean execute, ColumnFunction<T, ?> column, String value) {
        return this.setValue(execute, column, value);
    }

    /**
     * set function value for one column
     *
     * @param column         database column
     * @param columnFunction string value
     * @return updateWrapper
     */
    public UpdateWrapper<T> set(ColumnFunction<T, ?> column, ColumnFunction<?, ?> columnFunction) {
        return this.set(true, column, columnFunction);
    }

    /**
     * set function value for one column(update table set name = replace(`name`, 'a', 'n') where ...)
     *
     * @param execute        will execute method if `execute` is true
     * @param column         database column
     * @param columnFunction string value
     * @return updateWrapper
     */
    public UpdateWrapper<T> set(boolean execute, ColumnFunction<T, ?> column, ColumnFunction<?, ?> columnFunction) {
        return this.seColumnFunction(execute, column, columnFunction);
    }

    /**
     * set value for one column
     *
     * @param column database column
     * @param value  numberic value
     * @return updateWrapper
     */
    public UpdateWrapper<T> set(ColumnFunction<T, ?> column, Number value) {
        return this.set(true, column, value);
    }

    /**
     * set value for one column
     *
     * @param column database column
     * @param value  numberic value
     * @return updateWrapper
     */
    public UpdateWrapper<T> setNotBlank(ColumnFunction<T, ?> column, Number value) {
        return this.set(value != null, column, value);
    }

    /**
     * set value for one column
     *
     * @param execute will execute method if `execute` is true
     * @param column  database column
     * @param value   numberic value
     * @return updateWrapper
     */
    public UpdateWrapper<T> set(boolean execute, ColumnFunction<T, ?> column, Number value) {
        return this.setValue(execute, column, value);
    }

    /**
     * set value for one column
     *
     * @param column database column
     * @param value  date value
     * @return updateWrapper
     */
    public UpdateWrapper<T> set(ColumnFunction<T, ?> column, Date value) {
        return this.set(true, column, value);
    }

    /**
     * set value for one column only if value is not null
     *
     * @param column database column
     * @param value  date value
     * @return updateWrapper
     */
    public UpdateWrapper<T> setNotBlank(ColumnFunction<T, ?> column, Date value) {
        return this.set(value != null, column, value);
    }

    /**
     * set value for one column
     *
     * @param execute will execute method if `execute` is true
     * @param column  database column
     * @param value   date value
     * @return updateWrapper
     */
    public UpdateWrapper<T> set(boolean execute, ColumnFunction<T, ?> column, Date value) {
        return this.setValue(execute, column, value);
    }

    /**
     * set value for one column
     *
     * @param column database column
     * @param value  localDate value
     * @return updateWrapper
     */
    public UpdateWrapper<T> set(ColumnFunction<T, ?> column, LocalDate value) {
        return this.set(true, column, value);
    }

    /**
     * set value for one column
     *
     * @param column database column
     * @param value  localDate value
     * @return updateWrapper
     */
    public UpdateWrapper<T> setNotBlank(ColumnFunction<T, ?> column, LocalDate value) {
        return this.set(value != null, column, value);
    }

    /**
     * set value for one column
     *
     * @param execute will execute method if `execute` is true
     * @param column  database column
     * @param value   localDate value
     * @return updateWrapper
     */
    public UpdateWrapper<T> set(boolean execute, ColumnFunction<T, ?> column, LocalDate value) {
        return this.setValue(execute, column, value);
    }

    /**
     * set value for one column
     *
     * @param column database column
     * @param value  localDateTime value
     * @return updateWrapper
     */
    public UpdateWrapper<T> set(ColumnFunction<T, ?> column, LocalDateTime value) {
        return this.set(true, column, value);
    }

    /**
     * set value for one column
     *
     * @param column database column
     * @param value  localDateTime value
     * @return updateWrapper
     */
    public UpdateWrapper<T> setNotBlank(ColumnFunction<T, ?> column, LocalDateTime value) {
        return this.set(value != null, column, value);
    }

    /**
     * set value for one column
     *
     * @param execute will execute method if `execute` is true
     * @param column  database column
     * @param value   localDateTime value
     * @return updateWrapper
     */
    public UpdateWrapper<T> set(boolean execute, ColumnFunction<T, ?> column, LocalDateTime value) {
        return this.setValue(execute, column, value);
    }

    /**
     * set value for one column
     *
     * @param column database column
     * @param value  boolean value
     * @return updateWrapper
     */
    public UpdateWrapper<T> set(ColumnFunction<T, ?> column, Boolean value) {
        return this.set(true, column, value);
    }

    /**
     * set value for one column
     *
     * @param column database column
     * @param value  boolean value
     * @return updateWrapper
     */
    public UpdateWrapper<T> setNotBlank(ColumnFunction<T, ?> column, Boolean value) {
        return this.set(value != null, column, value);
    }

    /**
     * set value for one column
     *
     * @param execute will execute method if `execute` is true
     * @param column  database column
     * @param value   boolean value
     * @return updateWrapper
     */
    public UpdateWrapper<T> set(boolean execute, ColumnFunction<T, ?> column, Boolean value) {
        return this.setValue(execute, column, value);
    }

    /**
     * set value for one column
     *
     * @param execute   will execute method if `execute` is true
     * @param column    database column
     * @param operation value operation
     * @return updateWrapper
     */
    public UpdateWrapper<T> set(boolean execute, ColumnFunction<T, ?> column, Operation<?> operation) {
        return this.setColumnValue(execute, column, operation);
    }


    /**
     * set value for one column
     *
     * @param column    database column
     * @param operation value operation
     * @return updateWrapper
     */
    public UpdateWrapper<T> set(ColumnFunction<T, ?> column, Operation<?> operation) {
        return this.set(true, column, operation);
    }


    /**
     * set value for one column
     *
     * @param column database column
     * @param value  any value
     * @return updateWrapper
     */
    public UpdateWrapper<T> setObject(ColumnFunction<T, ?> column, Object value) {
        return this.setObject(true, column, value);
    }

    /**
     * set value for one column
     *
     * @param execute will execute method if `execute` is true
     * @param column  database column
     * @param value   any value
     * @return updateWrapper
     */
    public UpdateWrapper<T> setObject(boolean execute, ColumnFunction<T, ?> column, Object value) {
        return this.setValue(execute, column, value);
    }

    /**
     * plus operation for one column
     *
     * @param column database column
     * @param value  numberic value
     * @return updateWrapper object
     */
    public UpdateWrapper<T> plus(ColumnFunction<T, ?> column, Number value) {
        return this.plus(true, column, value);
    }

    /**
     * minus operation for one column
     *
     * @param column database column
     * @param value  numberic value
     * @return UpdateWrapper object
     */
    public UpdateWrapper<T> minus(ColumnFunction<T, ?> column, Number value) {
        return this.minus(true, column, value);
    }

    /**
     * plus operation for one column
     *
     * @param execute will execute method if `execute` is true
     * @param column  database column
     * @param value   numberic value
     * @return UpdateWrapper object
     */
    public UpdateWrapper<T> plus(boolean execute, ColumnFunction<T, ?> column, Number value) {
        return this.atomicOperation(execute, column, "+", value);
    }

    /**
     * minus operation for one column
     *
     * @param execute will execute method if `execute` is true
     * @param column  database column
     * @param value   numberic value
     * @return UpdateWrapper object
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
     * set value for one column
     *
     * @param execute will execute method if `execute` is true
     * @param column  database column
     * @param value   any value
     * @return updateWrapper object
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
     * set value for one column
     *
     * @param execute        will execute method if `execute` is true
     * @param column         database column
     * @param columnFunction any value
     * @return updateWrapper object
     */
    private UpdateWrapper<T> seColumnFunction(boolean execute, ColumnFunction<T, ?> column, ColumnFunction<?, ?> columnFunction) {
        if (execute) {
            String columnName = ColumnUtils.getColumnName(column, false);
            String value = this.parseColumnName(columnFunction, false);
            set.add(wrapColumnName(columnName) + " = " + value);
        } else {
            //remove useless function
            Map<ColumnFunction<?, ?>, ColumnWrapper> functionMap = Constants.THREAD_COLUMN_FUNCTION.get();
            if (functionMap != null) {
                functionMap.remove(columnFunction);
            }
        }
        return this;
    }

    /**
     * set value for one column
     *
     * @param execute will execute method if `execute` is true
     * @param column  database column
     * @return updateWrapper object
     */
    private UpdateWrapper<T> setColumnValue(boolean execute, ColumnFunction<T, ?> column, Operation<?> operation) {
        if (execute) {
            String columnName = ColumnUtils.getColumnName(column, false);
            set.add(wrapColumnName(columnName) + " = " + operation.getOperationStr());
        }
        return this;
    }
}
