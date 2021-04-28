package io.github.happytimor.mybatis.helper.core.metadata;


import io.github.happytimor.mybatis.helper.core.wrapper.AbstractWrapper;
import io.github.happytimor.mybatis.helper.core.wrapper.WhereWrapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;

/**
 * default implements of compare method interface, very similar to Compare
 *
 * @author chenpeng
 */
public interface DefaultCompare<Children> extends Compare<Children> {
    /**
     * gt method for Number(eg: where `id` > 1)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default <R> Children gt(ColumnFunction<R, ?> column, Number value) {
        return gt(true, column, value);
    }

    /**
     * gt method for String
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default <R> Children gt(ColumnFunction<R, ?> column, String value) {
        return gt(true, column, value);
    }

    /**
     * gt method for Date
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default <R> Children gt(ColumnFunction<R, ?> column, Date value) {
        return gt(true, column, value);
    }

    /**
     * gt method for LocalDate
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default <R> Children gt(ColumnFunction<R, ?> column, LocalDate value) {
        return gt(true, column, value);
    }

    /**
     * gt method for LocalDateTime
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default <R> Children gt(ColumnFunction<R, ?> column, LocalDateTime value) {
        return gt(true, column, value);
    }

    /**
     * gt method for Column(eg: where `grade_of_english` > `grade_of_math`)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default <R, V> Children gt(ColumnFunction<R, ?> column, ColumnFunction<V, ?> value) {
        return gt(true, column, value);
    }

    /**
     * gt method for nested function
     *
     * @param column   lambda column name
     * @param function nested function
     * @return chain object
     */
    default <R> Children gt(ColumnFunction<R, ?> column, Function<WhereWrapper<?>, AbstractWrapper<?>> function) {
        return gt(true, column, function);
    }

    /**
     * ge method for Number(eg: where `id` >= 1)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default <R> Children ge(ColumnFunction<R, ?> column, Number value) {
        return ge(true, column, value);
    }

    /**
     * ge method for String
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default <R> Children ge(ColumnFunction<R, ?> column, String value) {
        return ge(true, column, value);
    }

    /**
     * ge method for Date
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default <R> Children ge(ColumnFunction<R, ?> column, Date value) {
        return ge(true, column, value);
    }

    /**
     * ge method for LocalDate
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default <R> Children ge(ColumnFunction<R, ?> column, LocalDate value) {
        return ge(true, column, value);
    }

    /**
     * ge method for LocalDateTime
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default <R> Children ge(ColumnFunction<R, ?> column, LocalDateTime value) {
        return ge(true, column, value);
    }

    /**
     * ge method for Column
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default <R, V> Children ge(ColumnFunction<R, ?> column, ColumnFunction<V, ?> value) {
        return ge(true, column, value);
    }

    /**
     * ge method for nested function
     *
     * @param column   lambda column name
     * @param function nested function
     * @return chain object
     */
    default <R> Children ge(ColumnFunction<R, ?> column, Function<WhereWrapper<?>, AbstractWrapper<?>> function) {
        return ge(true, column, function);
    }

    /**
     * eq method for Number(eg: where `id` = 1)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default <R> Children eq(ColumnFunction<R, ?> column, Number value) {
        return eq(true, column, value);
    }

    /**
     * eq method for Boolean
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default <R> Children eq(ColumnFunction<R, ?> column, Boolean value) {
        return eq(true, column, value);
    }

    /**
     * eq method for String
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default <R> Children eq(ColumnFunction<R, ?> column, String value) {
        return eq(true, column, value);
    }

    /**
     * eq method for Date
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default <R> Children eq(ColumnFunction<R, ?> column, Date value) {
        return eq(true, column, value);
    }

    /**
     * eq method for LocalDate
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default <R> Children eq(ColumnFunction<R, ?> column, LocalDate value) {
        return eq(true, column, value);
    }

    /**
     * eq method for LocalDateTime
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default <R> Children eq(ColumnFunction<R, ?> column, LocalDateTime value) {
        return eq(true, column, value);
    }

    /**
     * eq method for Column
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default <R, V> Children eq(ColumnFunction<R, ?> column, ColumnFunction<V, ?> value) {
        return eq(true, column, value);
    }

    /**
     * eq method for nested function
     *
     * @param column   lambda column name
     * @param function nested function
     * @return chain object
     */
    default <R> Children eqNested(ColumnFunction<R, ?> column, Function<WhereWrapper<?>, AbstractWrapper<?>> function) {
        return eqNested(true, column, function);
    }

    /**
     * le method for Number(eg: where `id` <= 1)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default <R> Children le(ColumnFunction<R, ?> column, Number value) {
        return le(true, column, value);
    }

    /**
     * le method for String
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default <R> Children le(ColumnFunction<R, ?> column, String value) {
        return le(true, column, value);
    }

    /**
     * le method for Date
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default <R> Children le(ColumnFunction<R, ?> column, Date value) {
        return le(true, column, value);
    }

    /**
     * le method for LocalDate
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default <R> Children le(ColumnFunction<R, ?> column, LocalDate value) {
        return le(true, column, value);
    }

    /**
     * le method for LocalDateTime
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default <R> Children le(ColumnFunction<R, ?> column, LocalDateTime value) {
        return le(true, column, value);
    }

    /**
     * le method for Column
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default <R, V> Children le(ColumnFunction<R, ?> column, ColumnFunction<V, ?> value) {
        return le(true, column, value);
    }

    /**
     * le method for nested function
     *
     * @param column   lambda column name
     * @param function nested function
     * @return chain object
     */
    default <R> Children le(ColumnFunction<R, ?> column, Function<WhereWrapper<?>, AbstractWrapper<?>> function) {
        return le(true, column, function);
    }

    /**
     * lt method for Number(eg: where `id` < 1)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default <R> Children lt(ColumnFunction<R, ?> column, Number value) {
        return lt(true, column, value);
    }

    /**
     * lt method for String
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default <R> Children lt(ColumnFunction<R, ?> column, String value) {
        return lt(true, column, value);
    }

    /**
     * lt method for Date
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default <R> Children lt(ColumnFunction<R, ?> column, Date value) {
        return lt(true, column, value);
    }

    /**
     * lt method for LocalDate
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default <R> Children lt(ColumnFunction<R, ?> column, LocalDate value) {
        return lt(true, column, value);
    }

    /**
     * lt method for LocalDateTime
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default <R> Children lt(ColumnFunction<R, ?> column, LocalDateTime value) {
        return lt(true, column, value);
    }

    /**
     * lt method for Column
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default <R, V> Children lt(ColumnFunction<R, ?> column, ColumnFunction<V, ?> value) {
        return lt(true, column, value);
    }

    /**
     * lt method for nested function
     *
     * @param column   lambda column name
     * @param function nested function
     * @return chain object
     */
    default <R> Children lt(ColumnFunction<R, ?> column, Function<WhereWrapper<?>, AbstractWrapper<?>> function) {
        return lt(true, column, function);
    }

    /**
     * ne method(eg: where `id` <> 1)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default <R> Children ne(ColumnFunction<R, ?> column, Number value) {
        return ne(true, column, value);
    }

    /**
     * ne method for String
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default <R> Children ne(ColumnFunction<R, ?> column, String value) {
        return ne(true, column, value);
    }

    /**
     * ne method for Date
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default <R> Children ne(ColumnFunction<R, ?> column, Date value) {
        return ne(true, column, value);
    }

    /**
     * ne method for LocalDate
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default <R> Children ne(ColumnFunction<R, ?> column, LocalDate value) {
        return ne(true, column, value);
    }

    /**
     * ne method for LocalDateTime
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default <R> Children ne(ColumnFunction<R, ?> column, LocalDateTime value) {
        return ne(true, column, value);
    }

    /**
     * ne method for Column
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default <R, V> Children ne(ColumnFunction<R, ?> column, ColumnFunction<V, ?> value) {
        return ne(true, column, value);
    }

    /**
     * ne method for nested function
     *
     * @param column   lambda column name
     * @param function nested function
     * @return chain object
     */
    default <R> Children ne(ColumnFunction<R, ?> column, Function<WhereWrapper<?>, AbstractWrapper<?>> function) {
        return ne(true, column, function);
    }


    /**
     * like method(eg: where `name` like '%zhangsan%')
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default <R> Children like(ColumnFunction<R, ?> column, String value) {
        return like(true, column, value);
    }

    /**
     * like left method(eq: where `name` like 'zhangsan%')
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default <R> Children likeLeft(ColumnFunction<R, ?> column, String value) {
        return likeLeft(true, column, value);
    }

    /**
     * like right method(eq: where `name` like '%zhangsan')
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default <R> Children likeRight(ColumnFunction<R, ?> column, String value) {
        return likeRight(true, column, value);
    }

    /**
     * not like method(eq: where `name` not like '%zhangsan%')
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default <R> Children notLike(ColumnFunction<R, ?> column, String value) {
        return notLike(true, column, value);
    }

    /**
     * between method(eg: where `age` between 20 and 30)
     *
     * @param column lambda column name
     * @param start  start value
     * @param end    end value
     * @return chain object
     */
    default <R> Children between(ColumnFunction<R, ?> column, Number start, Number end) {
        return between(true, column, start, end);
    }

    /**
     * not between method(eg: where `age` not between 20 and 30)
     *
     * @param column lambda column name
     * @param start  start value
     * @param end    end value
     * @return chain object
     */
    default <R> Children notBetween(ColumnFunction<R, ?> column, Number start, Number end) {
        return notBetween(true, column, start, end);
    }

    /**
     * between method(eg: where `age` between 20 and 30)
     *
     * @param column lambda column name
     * @param start  start value
     * @param end    end value
     * @return chain object
     */
    default <R> Children between(ColumnFunction<R, ?> column, Date start, Date end) {
        return between(true, column, start, end);
    }

    /**
     * not between method(eg: where `age` not between 20 and 30)
     *
     * @param column lambda column name
     * @param start  start value
     * @param end    end value
     * @return chain object
     */
    default <R> Children notBetween(ColumnFunction<R, ?> column, Date start, Date end) {
        return notBetween(true, column, start, end);
    }

    /**
     * between method(eg: where `age` between 20 and 30)
     *
     * @param column lambda column name
     * @param start  start value
     * @param end    end value
     * @return chain object
     */
    default <R> Children between(ColumnFunction<R, ?> column, LocalDate start, LocalDate end) {
        return between(true, column, start, end);
    }

    /**
     * not between method(eg: where `age` not between 20 and 30)
     *
     * @param column lambda column name
     * @param start  start value
     * @param end    end value
     * @return chain object
     */
    default <R> Children notBetween(ColumnFunction<R, ?> column, LocalDate start, LocalDate end) {
        return notBetween(true, column, start, end);
    }

    /**
     * between method(eg: where `age` between 20 and 30)
     *
     * @param column lambda column name
     * @param start  start value
     * @param end    end value
     * @return chain object
     */
    default <R> Children between(ColumnFunction<R, ?> column, LocalDateTime start, LocalDateTime end) {
        return between(true, column, start, end);
    }

    /**
     * not between method(eg: where `age` not between 20 and 30)
     *
     * @param column lambda column name
     * @param start  start value
     * @param end    end value
     * @return chain object
     */
    default <R> Children notBetween(ColumnFunction<R, ?> column, LocalDateTime start, LocalDateTime end) {
        return notBetween(true, column, start, end);
    }

    /**
     * isNull method(eg: where `age` is null)
     *
     * @param column lambda column name
     * @return chain object
     */
    default <R> Children isNull(ColumnFunction<R, ?> column) {
        return isNull(true, column);
    }

    /**
     * isNotNull method(eg: where `age` is not null)
     *
     * @param column lambda column name
     * @return chain object
     */
    default <R> Children isNotNull(ColumnFunction<R, ?> column) {
        return isNotNull(true, column);
    }

    /**
     * isEmpty method(eg: where `name` = '')
     *
     * @param column lambda column name
     * @return chain object
     */
    default <R> Children isEmpty(ColumnFunction<R, ?> column) {
        return isEmpty(true, column);
    }

    /**
     * isNotEmpty method(eg: where `name` != '')
     *
     * @param column lambda column name
     * @return chain object
     */
    default <R> Children isNotEmpty(ColumnFunction<R, ?> column) {
        return isNotEmpty(true, column);
    }


    /**
     * in method(eg: where `id` in (1, 2, 3, 4))
     *
     * @param column lambda column name
     * @param values given collection
     * @return chain object
     */
    default <R> Children in(ColumnFunction<R, ?> column, Collection<?> values) {
        return in(true, column, values);
    }

    /**
     * in nested method(eg: where `id` in (select `id` from user where age  > 20 ))
     *
     * @param column   lambda column name
     * @param function nested expression
     * @return chain object
     */
    default <R> Children in(ColumnFunction<R, ?> column, Function<WhereWrapper<?>, AbstractWrapper<?>> function) {
        return in(true, column, function);
    }

    /**
     * not in method(eg: where `id` not in (1, 2, 3, 4))
     *
     * @param column lambda column name
     * @param values given collection
     * @return chain object
     */
    default <R> Children notIn(ColumnFunction<R, ?> column, Collection<?> values) {
        return notIn(true, column, values);
    }

    /**
     * not in nested method(eg: where `id` not in (select `id` from user where age  > 20 ))
     *
     * @param column   lambda column name
     * @param function nested expression
     * @return chain object
     */
    default <R> Children notIn(ColumnFunction<R, ?> column, Function<WhereWrapper<?>, AbstractWrapper<?>> function) {
        return notIn(true, column, function);
    }
}
