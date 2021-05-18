package io.github.happytimor.mybatis.helper.core.metadata;

import io.github.happytimor.mybatis.helper.core.wrapper.HavingWrapper;

import java.io.Serializable;

/**
 * @author chenpeng
 */
public interface HavingMethod<T> extends Serializable {
    HavingWrapper<T> havingLt(boolean execute, ColumnFunction<T, ?> columnFunction, Object value);

    HavingWrapper<T> havingLt(boolean execute, String columnName, Object value);

    HavingWrapper<T> havingLe(boolean execute, ColumnFunction<T, ?> columnFunction, Object value);

    HavingWrapper<T> havingLe(boolean execute, String columnName, Object value);

    HavingWrapper<T> havingEq(boolean execute, ColumnFunction<T, ?> columnFunction, Object value);

    HavingWrapper<T> havingEq(boolean execute, String columnName, Object value);

    HavingWrapper<T> havingGe(boolean execute, ColumnFunction<T, ?> columnFunction, Object value);

    HavingWrapper<T> havingGe(boolean execute, String columnName, Object value);

    HavingWrapper<T> havingGt(boolean execute, ColumnFunction<T, ?> columnFunction, Object value);

    HavingWrapper<T> havingGt(boolean execute, String columnName, Object value);

    HavingWrapper<T> havingNe(boolean execute, ColumnFunction<T, ?> columnFunction, Object value);

    HavingWrapper<T> havingNe(boolean execute, String columnName, Object value);
}
