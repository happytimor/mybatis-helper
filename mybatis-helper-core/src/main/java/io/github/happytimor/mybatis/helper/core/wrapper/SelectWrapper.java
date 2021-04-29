package io.github.happytimor.mybatis.helper.core.wrapper;


import io.github.happytimor.mybatis.helper.core.annotation.TableName;
import io.github.happytimor.mybatis.helper.core.common.SelectColumn;
import io.github.happytimor.mybatis.helper.core.metadata.ColumnFunction;
import io.github.happytimor.mybatis.helper.core.util.ColumnUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

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
    public final <E> SelectWrapper<T> select(ColumnFunction<E, ?>... functions) {
        for (ColumnFunction<E, ?> function : functions) {
            selectColumnList.add(new SelectColumn(function));
        }
        return this;
    }
}
