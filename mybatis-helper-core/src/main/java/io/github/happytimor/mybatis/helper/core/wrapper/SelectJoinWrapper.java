package io.github.happytimor.mybatis.helper.core.wrapper;


import io.github.happytimor.mybatis.helper.core.annotation.TableName;
import io.github.happytimor.mybatis.helper.core.common.SelectColumn;
import io.github.happytimor.mybatis.helper.core.common.SqlFunctionName;
import io.github.happytimor.mybatis.helper.core.function.SqlFunction;
import io.github.happytimor.mybatis.helper.core.metadata.ColumnFunction;
import io.github.happytimor.mybatis.helper.core.metadata.ColumnWrapper;
import io.github.happytimor.mybatis.helper.core.metadata.Result;
import io.github.happytimor.mybatis.helper.core.metadata.TableInfo;
import io.github.happytimor.mybatis.helper.core.util.ColumnUtils;
import io.github.happytimor.mybatis.helper.core.util.LambdaUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chenpeng
 */
public class SelectJoinWrapper<T> extends JoinWrapper<T> {

    public SelectJoinWrapper() {

    }

    public SelectJoinWrapper(Class<?> modelClass, Map<String, Object> paramNameValuePairs, AtomicInteger counter) {
        TableName tableNameAnnotation = modelClass.getAnnotation(TableName.class);
        this.tableName = tableNameAnnotation != null ? tableNameAnnotation.value() : ColumnUtils.camelCaseToUnderscore(modelClass.getSimpleName());
        this.paramNameValuePairs = paramNameValuePairs;
        this.counter = counter;
    }

    @SafeVarargs
    public final <E> SelectJoinWrapper<T> select(ColumnFunction<E, ?>... columnFunctions) {
        for (ColumnFunction<E, ?> function : columnFunctions) {
            selectColumnList.add(new SelectColumn(function));
        }
        return this;
    }

    public final <E> SelectJoinWrapper<T> selectAll(Class<E> clazz) {
        TableInfo tableInfo = LambdaUtils.parseTableInfo(clazz);
        List<Result> resultList = tableInfo.getResultList();
        for (Result result : resultList) {
            selectColumnList.add(new SelectColumn(clazz,result.getColumn()));
        }
        return this;
    }

    public final <E, M> SelectJoinWrapper<T> selectAs(ColumnFunction<E, ?> columnFunction, ColumnFunction<M, ?> alias) {
        Map<ColumnFunction<?, ?>, ColumnWrapper> columnFunctionColumnWrapperMap
                = SqlFunction.ensureMap();
        columnFunctionColumnWrapperMap.put(columnFunction,
                new ColumnWrapper(SqlFunctionName.AS, ColumnUtils.getFieldName(alias)));
        selectColumnList.add(new SelectColumn(columnFunction));
        return this;
    }
}
