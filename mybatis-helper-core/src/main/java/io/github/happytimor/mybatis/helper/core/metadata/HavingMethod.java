package io.github.happytimor.mybatis.helper.core.metadata;

import io.github.happytimor.mybatis.helper.core.wrapper.HavingWrapper;

import java.io.Serializable;

/**
 * @author chenpeng
 */
public interface HavingMethod<T> extends Serializable {
    /**
     * havingLt
     *
     * @param execute        是否执行
     * @param columnFunction columnFunction
     * @param value          value
     * @return HavingWrapper
     */
    HavingWrapper<T> havingLt(boolean execute, ColumnFunction<T, ?> columnFunction, Object value);

    /**
     * havingLt
     *
     * @param execute    是否执行
     * @param columnName columnFunction
     * @param value      value
     * @return HavingWrapper
     */
    HavingWrapper<T> havingLt(boolean execute, String columnName, Object value);

    /**
     * havingLe
     *
     * @param execute        是否执行
     * @param columnFunction columnFunction
     * @param value          value
     * @return HavingWrapper
     */
    HavingWrapper<T> havingLe(boolean execute, ColumnFunction<T, ?> columnFunction, Object value);

    /**
     * havingLe
     *
     * @param execute    是否执行
     * @param columnName columnName
     * @param value      value
     * @return HavingWrapper
     */
    HavingWrapper<T> havingLe(boolean execute, String columnName, Object value);

    /**
     * havingEq
     *
     * @param execute        是否执行
     * @param columnFunction columnFunction
     * @param value          value
     * @return HavingWrapper
     */
    HavingWrapper<T> havingEq(boolean execute, ColumnFunction<T, ?> columnFunction, Object value);

    /**
     * havingEq
     *
     * @param execute    是否执行
     * @param columnName columnName
     * @param value      value
     * @return HavingWrapper
     */
    HavingWrapper<T> havingEq(boolean execute, String columnName, Object value);

    /**
     * havingGe
     *
     * @param execute        是否执行
     * @param columnFunction columnFunction
     * @param value          value
     * @return HavingWrapper
     */
    HavingWrapper<T> havingGe(boolean execute, ColumnFunction<T, ?> columnFunction, Object value);

    /**
     * havingGe
     *
     * @param execute    是否执行
     * @param columnName columnName
     * @param value      value
     * @return HavingWrapper
     */
    HavingWrapper<T> havingGe(boolean execute, String columnName, Object value);

    /**
     * havingGt
     *
     * @param execute        是否执行
     * @param columnFunction columnFunction
     * @param value          value
     * @return HavingWrapper
     */
    HavingWrapper<T> havingGt(boolean execute, ColumnFunction<T, ?> columnFunction, Object value);

    /**
     * havingGt
     *
     * @param execute    是否执行
     * @param columnName columnName
     * @param value      value
     * @return HavingWrapper
     */
    HavingWrapper<T> havingGt(boolean execute, String columnName, Object value);

    /**
     * havingNe
     *
     * @param execute        是否执行
     * @param columnFunction columnFunction
     * @param value          value
     * @return HavingWrapper
     */
    HavingWrapper<T> havingNe(boolean execute, ColumnFunction<T, ?> columnFunction, Object value);

    /**
     * havingNe
     *
     * @param execute    是否执行
     * @param columnName columnName
     * @param value      value
     * @return HavingWrapper
     */
    HavingWrapper<T> havingNe(boolean execute, String columnName, Object value);
}
