package io.github.happytimor.mybatis.helper.core.wrapper;


import io.github.happytimor.mybatis.helper.core.annotation.TableName;
import io.github.happytimor.mybatis.helper.core.common.SqlFunctionName;
import io.github.happytimor.mybatis.helper.core.function.SqlFunction;
import io.github.happytimor.mybatis.helper.core.metadata.ColumnFunction;
import io.github.happytimor.mybatis.helper.core.metadata.ColumnWrapper;
import io.github.happytimor.mybatis.helper.core.util.ColumnUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author chenpeng
 */
public class SelectWrapper<T> extends WhereWrapper<T> {

    public SelectWrapper() {

    }

    public SelectWrapper(Class<?> modelClass, Map<String, Object> paramNameValuePairs, AtomicInteger counter) {
        TableName tableNameAnnotation = modelClass.getAnnotation(TableName.class);
        this.tableName = tableNameAnnotation != null ? tableNameAnnotation.value() : ColumnUtils.camelCaseToUnderscore(modelClass.getSimpleName());
        this.paramNameValuePairs = paramNameValuePairs;
        this.counter = counter;
    }

    @SafeVarargs
    public final WhereWrapper<T> select(ColumnFunction<T, ?>... functions) {
        this.selectSegment = Arrays.stream(functions).map(columnFunction -> {
            String columnName = this.getColumnName(columnFunction);
            ColumnWrapper columnWrapper = SqlFunction.FUNCTION_MAP.remove(columnFunction);
            if (columnWrapper != null) {
                return this.wrapperFunctionColumn(columnWrapper, columnName);
            }
            return columnName;
        }).collect(Collectors.joining(", "));
        return this;
    }

    public final WhereWrapper<T> select(String... columns) {
        this.selectSegment = String.join(",", columns);
        return this;
    }

    private String wrapperFunctionColumn(ColumnWrapper columnWrapper, String columnName) {
        String function = columnWrapper.getFunction();
        String alias = "".equals(columnWrapper.getAlias()) ? "" : " AS '" + columnWrapper.getAlias() + "'";
        if (Objects.equals(function, SqlFunctionName.AS)) {
            return columnName + alias;
        }


        if (columnWrapper.getChildWrapper() != null) {
            columnName = this.wrapperFunctionColumn(columnWrapper.getChildWrapper(), columnName);
        }
        return function + "(" + columnName + ")" + alias;
    }
}
