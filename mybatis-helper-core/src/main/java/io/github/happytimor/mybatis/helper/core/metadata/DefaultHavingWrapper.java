package io.github.happytimor.mybatis.helper.core.metadata;

import io.github.happytimor.mybatis.helper.core.wrapper.HavingWrapper;

/**
 * @author chenpeng
 */
public interface DefaultHavingWrapper<T> extends HavingMethod<T> {
    /**
     * havingLt
     *
     * @param columnFunction columnFunction
     * @param value          value
     * @return HavingWrapper
     */
    default HavingWrapper<T> havingLt(ColumnFunction<T, ?> columnFunction, Object value) {
        return havingLt(true, columnFunction, value);
    }

    /**
     * havingLt
     *
     * @param columnName columnName
     * @param value      value
     * @return HavingWrapper
     */
    default HavingWrapper<T> havingLt(String columnName, Object value) {
        return havingLt(true, columnName, value);
    }

    /**
     * havingLe
     *
     * @param columnFunction columnFunction
     * @param value          value
     * @return HavingWrapper
     */
    default HavingWrapper<T> havingLe(ColumnFunction<T, ?> columnFunction, Object value) {
        return havingLe(true, columnFunction, value);
    }

    /**
     * havingLe
     *
     * @param columnName columnName
     * @param value      value
     * @return HavingWrapper
     */
    default HavingWrapper<T> havingLe(String columnName, Object value) {
        return havingLe(true, columnName, value);
    }

    /**
     * havingEq
     *
     * @param columnFunction columnFunction
     * @param value          value
     * @return HavingWrapper
     */
    default HavingWrapper<T> havingEq(ColumnFunction<T, ?> columnFunction, Object value) {
        return havingEq(true, columnFunction, value);
    }

    /**
     * havingEq
     *
     * @param columnName columnName
     * @param value      value
     * @return HavingWrapper
     */
    default HavingWrapper<T> havingEq(String columnName, Object value) {
        return havingEq(true, columnName, value);
    }

    /**
     * havingGe
     *
     * @param columnFunction columnFunction
     * @param value          value
     * @return HavingWrapper
     */
    default HavingWrapper<T> havingGe(ColumnFunction<T, ?> columnFunction, Object value) {
        return havingGe(true, columnFunction, value);
    }

    /**
     * havingGe
     *
     * @param columnName columnName
     * @param value      value
     * @return HavingWrapper
     */
    default HavingWrapper<T> havingGe(String columnName, Object value) {
        return havingGe(true, columnName, value);
    }

    /**
     * havingGt
     *
     * @param columnFunction columnFunction
     * @param value          value
     * @return HavingWrapper
     */
    default HavingWrapper<T> havingGt(ColumnFunction<T, ?> columnFunction, Object value) {
        return havingGt(true, columnFunction, value);
    }

    /**
     * havingGt
     *
     * @param columnName columnName
     * @param value      value
     * @return HavingWrapper
     */
    default HavingWrapper<T> havingGt(String columnName, Object value) {
        return havingGt(true, columnName, value);
    }

    /**
     * havingNe
     *
     * @param columnFunction columnFunction
     * @param value          value
     * @return HavingWrapper
     */
    default HavingWrapper<T> havingNe(ColumnFunction<T, ?> columnFunction, Object value) {
        return havingNe(true, columnFunction, value);
    }

    /**
     * havingNe
     *
     * @param columnName columnName
     * @param value      value
     * @return HavingWrapper
     */
    default HavingWrapper<T> havingNe(String columnName, Object value) {
        return havingNe(true, columnName, value);
    }
}
