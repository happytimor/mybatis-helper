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
public interface DefaultCompare<Children, Column extends ColumnFunction<?, ?>> extends Compare<Children, Column> {
    /**
     * gt method for Number(eg: where `id` > 1)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default Children gt(Column column, Number value) {
        return gt(true, column, value);
    }

    /**
     * gt method for String
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default Children gt(Column column, String value) {
        return gt(true, column, value);
    }

    /**
     * gt method for Date
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default Children gt(Column column, Date value) {
        return gt(true, column, value);
    }

    /**
     * gt method for LocalDate
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default Children gt(Column column, LocalDate value) {
        return gt(true, column, value);
    }

    /**
     * gt method for LocalDateTime
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default Children gt(Column column, LocalDateTime value) {
        return gt(true, column, value);
    }

    /**
     * gt method for Column(eg: where `grade_of_english` > `grade_of_math`)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default Children gt(Column column, Column value) {
        return gt(true, column, value);
    }

    /**
     * gt method for nested function
     *
     * @param column   lambda column name
     * @param function nested function
     * @return chain object
     */
    default Children gt(Column column, Function<WhereWrapper<?>, AbstractWrapper<?>> function) {
        return gt(true, column, function);
    }

    /**
     * ge method for Number(eg: where `id` >= 1)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default Children ge(Column column, Number value) {
        return ge(true, column, value);
    }

    /**
     * ge method for String
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default Children ge(Column column, String value) {
        return ge(true, column, value);
    }

    /**
     * ge method for Date
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default Children ge(Column column, Date value) {
        return ge(true, column, value);
    }

    /**
     * ge method for LocalDate
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default Children ge(Column column, LocalDate value) {
        return ge(true, column, value);
    }

    /**
     * ge method for LocalDateTime
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default Children ge(Column column, LocalDateTime value) {
        return ge(true, column, value);
    }

    /**
     * ge method for Column
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default Children ge(Column column, Column value) {
        return ge(true, column, value);
    }

    /**
     * ge method for nested function
     *
     * @param column   lambda column name
     * @param function nested function
     * @return chain object
     */
    default Children ge(Column column, Function<WhereWrapper<?>, AbstractWrapper<?>> function) {
        return ge(true, column, function);
    }

    /**
     * eq method for Number(eg: where `id` = 1)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default Children eq(Column column, Number value) {
        return eq(true, column, value);
    }

    /**
     * eq method for Boolean
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default Children eq(Column column, Boolean value) {
        return eq(true, column, value);
    }

    /**
     * eq method for String
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default Children eq(Column column, String value) {
        return eq(true, column, value);
    }

    /**
     * eq method for Date
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default Children eq(Column column, Date value) {
        return eq(true, column, value);
    }

    /**
     * eq method for LocalDate
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default Children eq(Column column, LocalDate value) {
        return eq(true, column, value);
    }

    /**
     * eq method for LocalDateTime
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default Children eq(Column column, LocalDateTime value) {
        return eq(true, column, value);
    }

    /**
     * eq method for Column
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default Children eq(Column column, Column value) {
        return eq(true, column, value);
    }

    /**
     * eq method for nested function
     *
     * @param column   lambda column name
     * @param function nested function
     * @return chain object
     */
    default Children eq(Column column, Function<WhereWrapper<?>, AbstractWrapper<?>> function) {
        return eq(true, column, function);
    }

    /**
     * le method for Number(eg: where `id` <= 1)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default Children le(Column column, Number value) {
        return le(true, column, value);
    }

    /**
     * le method for String
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default Children le(Column column, String value) {
        return le(true, column, value);
    }

    /**
     * le method for Date
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default Children le(Column column, Date value) {
        return le(true, column, value);
    }

    /**
     * le method for LocalDate
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default Children le(Column column, LocalDate value) {
        return le(true, column, value);
    }

    /**
     * le method for LocalDateTime
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default Children le(Column column, LocalDateTime value) {
        return le(true, column, value);
    }

    /**
     * le method for Column
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default Children le(Column column, Column value) {
        return le(true, column, value);
    }

    /**
     * le method for nested function
     *
     * @param column   lambda column name
     * @param function nested function
     * @return chain object
     */
    default Children le(Column column, Function<WhereWrapper<?>, AbstractWrapper<?>> function) {
        return le(true, column, function);
    }

    /**
     * lt method for Number(eg: where `id` < 1)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default Children lt(Column column, Number value) {
        return lt(true, column, value);
    }

    /**
     * lt method for String
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default Children lt(Column column, String value) {
        return lt(true, column, value);
    }

    /**
     * lt method for Date
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default Children lt(Column column, Date value) {
        return lt(true, column, value);
    }

    /**
     * lt method for LocalDate
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default Children lt(Column column, LocalDate value) {
        return lt(true, column, value);
    }

    /**
     * lt method for LocalDateTime
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default Children lt(Column column, LocalDateTime value) {
        return lt(true, column, value);
    }

    /**
     * lt method for Column
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default Children lt(Column column, Column value) {
        return lt(true, column, value);
    }

    /**
     * lt method for nested function
     *
     * @param column   lambda column name
     * @param function nested function
     * @return chain object
     */
    default Children lt(Column column, Function<WhereWrapper<?>, AbstractWrapper<?>> function) {
        return lt(true, column, function);
    }

    /**
     * ne method(eg: where `id` <> 1)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default Children ne(Column column, Number value) {
        return ne(true, column, value);
    }

    /**
     * ne method for String
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default Children ne(Column column, String value) {
        return ne(true, column, value);
    }

    /**
     * ne method for Date
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default Children ne(Column column, Date value) {
        return ne(true, column, value);
    }

    /**
     * ne method for LocalDate
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default Children ne(Column column, LocalDate value) {
        return ne(true, column, value);
    }

    /**
     * ne method for LocalDateTime
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default Children ne(Column column, LocalDateTime value) {
        return ne(true, column, value);
    }

    /**
     * ne method for Column
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default Children ne(Column column, Column value) {
        return ne(true, column, value);
    }

    /**
     * ne method for nested function
     *
     * @param column   lambda column name
     * @param function nested function
     * @return chain object
     */
    default Children ne(Column column, Function<WhereWrapper<?>, AbstractWrapper<?>> function) {
        return ne(true, column, function);
    }


    /**
     * like method(eg: where `name` like '%zhangsan%')
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default Children like(Column column, String value) {
        return like(true, column, value);
    }

    /**
     * like left method(eq: where `name` like 'zhangsan%')
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default Children likeLeft(Column column, String value) {
        return likeLeft(true, column, value);
    }

    /**
     * like right method(eq: where `name` like '%zhangsan')
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default Children likeRight(Column column, String value) {
        return likeRight(true, column, value);
    }

    /**
     * not like method(eq: where `name` not like '%zhangsan%')
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default Children notLike(Column column, String value) {
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
    default Children between(Column column, Number start, Number end) {
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
    default Children notBetween(Column column, Number start, Number end) {
        return notBetween(true, column, start, end);
    }

    /**
     * isNull method(eg: where `age` is null)
     *
     * @param column lambda column name
     * @return chain object
     */
    default Children isNull(Column column) {
        return isNull(true, column);
    }

    /**
     * isNotNull method(eg: where `age` is not null)
     *
     * @param column lambda column name
     * @return chain object
     */
    default Children isNotNull(Column column) {
        return isNotNull(true, column);
    }

    /**
     * isEmpty method(eg: where `name` = '')
     *
     * @param column lambda column name
     * @return chain object
     */
    default Children isEmpty(Column column) {
        return isEmpty(true, column);
    }

    /**
     * isNotEmpty method(eg: where `name` != '')
     *
     * @param column lambda column name
     * @return chain object
     */
    default Children isNotEmpty(Column column) {
        return isNotEmpty(true, column);
    }


    /**
     * in method(eg: where `id` in (1, 2, 3, 4))
     *
     * @param column lambda column name
     * @param values given collection
     * @return chain object
     */
    default Children in(Column column, Collection<?> values) {
        return in(true, column, values);
    }

    /**
     * in nested method(eg: where `id` in (select `id` from user where age  > 20 ))
     *
     * @param column   lambda column name
     * @param function nested expression
     * @return chain object
     */
    default Children in(Column column, Function<WhereWrapper<?>, AbstractWrapper<?>> function) {
        return in(true, column, function);
    }

    /**
     * not in method(eg: where `id` not in (1, 2, 3, 4))
     *
     * @param column lambda column name
     * @param values given collection
     * @return chain object
     */
    default Children notIn(Column column, Collection<?> values) {
        return notIn(true, column, values);
    }

    /**
     * not in nested method(eg: where `id` not in (select `id` from user where age  > 20 ))
     *
     * @param column   lambda column name
     * @param function nested expression
     * @return chain object
     */
    default Children notIn(Column column, Function<WhereWrapper<?>, AbstractWrapper<?>> function) {
        return notIn(true, column, function);
    }
}
