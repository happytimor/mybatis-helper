package io.github.happytimor.mybatis.helper.core.metadata;

import io.github.happytimor.mybatis.helper.core.wrapper.AbstractWrapper;
import io.github.happytimor.mybatis.helper.core.wrapper.WhereWrapper;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;

/**
 * compare method interface
 *
 * @author chenpeng
 */
public interface Compare<Children> extends Serializable {

    /**
     * gt method for Number(eg: where `id` > 1)
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    <R> Children gt(boolean execute, ColumnFunction<R, ?> column, Number value);

    /**
     * gt method for Number(eg: where `id` > 1)(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    <R> Children gtNotBlank(ColumnFunction<R, ?> column, Number value);

    /**
     * gt method for String
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    <R> Children gt(boolean execute, ColumnFunction<R, ?> column, String value);

    /**
     * gt method for String(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    <R> Children gtNotBlank(ColumnFunction<R, ?> column, String value);

    /**
     * gt method for Date
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    <R> Children gt(boolean execute, ColumnFunction<R, ?> column, Date value);

    /**
     * gt method for Date(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    <R> Children gtNotBlank(ColumnFunction<R, ?> column, Date value);

    /**
     * gt method for LocalDate
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    <R> Children gt(boolean execute, ColumnFunction<R, ?> column, LocalDate value);

    /**
     * gt method for LocalDate(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    <R> Children gtNotBlank(ColumnFunction<R, ?> column, LocalDate value);

    /**
     * gt method for LocalDateTime
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    <R> Children gt(boolean execute, ColumnFunction<R, ?> column, LocalDateTime value);

    /**
     * gt method for LocalDateTime(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    <R> Children gtNotBlank(ColumnFunction<R, ?> column, LocalDateTime value);

    /**
     * gt method for Column(eg: where `grade_of_english` > `grade_of_math`)
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    <R, V> Children gt(boolean execute, ColumnFunction<R, ?> column, ColumnFunction<V, ?> value);

    /**
     * gt method for Column(eg: where `grade_of_english` > `grade_of_math`)(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    <R, V> Children gtNotBlank(ColumnFunction<R, ?> column, ColumnFunction<V, ?> value);

    /**
     * gt method for nested function
     *
     * @param execute  true: execute the method, false: skip execution
     * @param column   lambda column name
     * @param function nested function
     * @return chain object
     */
    <R> Children gt(boolean execute, ColumnFunction<R, ?> column, Function<WhereWrapper<?>, AbstractWrapper<?>> function);

    /**
     * ge method for Number(eg: where `id` >= 1)
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    <R> Children ge(boolean execute, ColumnFunction<R, ?> column, Number value);

    /**
     * ge method for Number(eg: where `id` >= 1)(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    <R> Children geNotBlank(ColumnFunction<R, ?> column, Number value);

    /**
     * ge method for String
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    <R> Children ge(boolean execute, ColumnFunction<R, ?> column, String value);

    /**
     * ge method for String(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    <R> Children geNotBlank(ColumnFunction<R, ?> column, String value);

    /**
     * ge method for Date
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    <R> Children ge(boolean execute, ColumnFunction<R, ?> column, Date value);

    /**
     * ge method for Date(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    <R> Children geNotBlank(ColumnFunction<R, ?> column, Date value);

    /**
     * ge method for LocalDate
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    <R> Children ge(boolean execute, ColumnFunction<R, ?> column, LocalDate value);

    /**
     * ge method for LocalDate(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    <R> Children geNotBlank(ColumnFunction<R, ?> column, LocalDate value);

    /**
     * ge method for LocalDateTime
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    <R> Children ge(boolean execute, ColumnFunction<R, ?> column, LocalDateTime value);

    /**
     * ge method for LocalDateTime(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    <R> Children geNotBlank(ColumnFunction<R, ?> column, LocalDateTime value);

    /**
     * ge method for Column
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    <R, V> Children ge(boolean execute, ColumnFunction<R, ?> column, ColumnFunction<V, ?> value);

    /**
     * ge method for nested function
     *
     * @param execute  true: execute the method, false: skip execution
     * @param column   lambda column name
     * @param function nested function
     * @return chain object
     */
    <R> Children ge(boolean execute, ColumnFunction<R, ?> column, Function<WhereWrapper<?>, AbstractWrapper<?>> function);

    /**
     * eq method for Number(eg: where `id` = 1)
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    <R> Children eq(boolean execute, ColumnFunction<R, ?> column, Number value);

    /**
     * eq method for Number(eg: where `id` = 1)(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    <R> Children eqNotBlank(ColumnFunction<R, ?> column, Number value);

    /**
     * eq method for Boolean
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    <R> Children eq(boolean execute, ColumnFunction<R, ?> column, Boolean value);

    /**
     * eq method for Boolean(execute only if value is not null)(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    <R> Children eqNotBlank(ColumnFunction<R, ?> column, Boolean value);

    /**
     * eq method for String
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    <R> Children eq(boolean execute, ColumnFunction<R, ?> column, String value);

    /**
     * eq method for String(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    <R> Children eqNotBlank(ColumnFunction<R, ?> column, String value);

    /**
     * eq method for Date
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    <R> Children eq(boolean execute, ColumnFunction<R, ?> column, Date value);

    /**
     * eq method for Date(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    <R> Children eqNotBlank(ColumnFunction<R, ?> column, Date value);

    /**
     * eq method for LocalDate
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    <R> Children eq(boolean execute, ColumnFunction<R, ?> column, LocalDate value);

    /**
     * eq method for LocalDate(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    <R> Children eqNotBlank(ColumnFunction<R, ?> column, LocalDate value);

    /**
     * eq method for LocalDateTime
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    <R> Children eq(boolean execute, ColumnFunction<R, ?> column, LocalDateTime value);

    /**
     * eq method for LocalDateTime(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    <R> Children eqNotBlank(ColumnFunction<R, ?> column, LocalDateTime value);

    /**
     * eq method for Column
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    <R, V> Children eq(boolean execute, ColumnFunction<R, ?> column, ColumnFunction<V, ?> value);

    /**
     * eq method for nested function
     *
     * @param execute  true: execute the method, false: skip execution
     * @param column   lambda column name
     * @param function nested function
     * @return chain object
     */
    <R> Children eqNested(boolean execute, ColumnFunction<R, ?> column, Function<WhereWrapper<?>, AbstractWrapper<?>> function);

    /**
     * le method for Number(eg: where `id` <= 1)
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    <R> Children le(boolean execute, ColumnFunction<R, ?> column, Number value);

    /**
     * le method for Number(eg: where `id` <= 1)(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    <R> Children leNotBlank(ColumnFunction<R, ?> column, Number value);

    /**
     * le method for String
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    <R> Children le(boolean execute, ColumnFunction<R, ?> column, String value);

    /**
     * le method for String(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    <R> Children leNotBlank(ColumnFunction<R, ?> column, String value);

    /**
     * le method for Date
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    <R> Children le(boolean execute, ColumnFunction<R, ?> column, Date value);

    /**
     * le method for Date(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    <R> Children leNotBlank(ColumnFunction<R, ?> column, Date value);

    /**
     * le method for LocalDate
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    <R> Children le(boolean execute, ColumnFunction<R, ?> column, LocalDate value);

    /**
     * le method for LocalDate(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    <R> Children leNotBlank(ColumnFunction<R, ?> column, LocalDate value);

    /**
     * le method for LocalDateTime
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    <R> Children le(boolean execute, ColumnFunction<R, ?> column, LocalDateTime value);

    /**
     * le method for LocalDateTime(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    <R> Children leNotBlank(ColumnFunction<R, ?> column, LocalDateTime value);

    /**
     * le method for Column
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    <R, V> Children le(boolean execute, ColumnFunction<R, ?> column, ColumnFunction<V, ?> value);

    /**
     * le method for nested function
     *
     * @param execute  true: execute the method, false: skip execution
     * @param column   lambda column name
     * @param function nested function
     * @return chain object
     */
    <R> Children le(boolean execute, ColumnFunction<R, ?> column, Function<WhereWrapper<?>, AbstractWrapper<?>> function);

    /**
     * lt method for Number(eg: where `id` < 1)
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    <R> Children lt(boolean execute, ColumnFunction<R, ?> column, Number value);

    /**
     * lt method for Number(eg: where `id` < 1)(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    <R> Children ltNotBlank(ColumnFunction<R, ?> column, Number value);

    /**
     * lt method for String
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    <R> Children lt(boolean execute, ColumnFunction<R, ?> column, String value);

    /**
     * lt method for String(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    <R> Children ltNotBlank(ColumnFunction<R, ?> column, String value);

    /**
     * lt method for Date
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    <R> Children lt(boolean execute, ColumnFunction<R, ?> column, Date value);

    /**
     * lt method for Date(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    <R> Children ltNotBlank(ColumnFunction<R, ?> column, Date value);

    /**
     * lt method for LocalDate
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    <R> Children lt(boolean execute, ColumnFunction<R, ?> column, LocalDate value);

    /**
     * lt method for LocalDate(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    <R> Children ltNotBlank(ColumnFunction<R, ?> column, LocalDate value);

    /**
     * lt method for LocalDateTime
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    <R> Children lt(boolean execute, ColumnFunction<R, ?> column, LocalDateTime value);

    /**
     * lt method for LocalDateTime(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    <R> Children ltNotBlank(ColumnFunction<R, ?> column, LocalDateTime value);

    /**
     * lt method for Column
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    <R, V> Children lt(boolean execute, ColumnFunction<R, ?> column, ColumnFunction<V, ?> value);

    /**
     * lt method for nested function
     *
     * @param execute  true: execute the method, false: skip execution
     * @param column   lambda column name
     * @param function nested function
     * @return chain object
     */
    <R> Children lt(boolean execute, ColumnFunction<R, ?> column, Function<WhereWrapper<?>, AbstractWrapper<?>> function);


    /**
     * ne method(eg: where `id` <> 1)
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    <R> Children ne(boolean execute, ColumnFunction<R, ?> column, Number value);

    /**
     * ne method(eg: where `id` <> 1)(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    <R> Children neNotBlank(ColumnFunction<R, ?> column, Number value);

    /**
     * ne method for String
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    <R> Children ne(boolean execute, ColumnFunction<R, ?> column, String value);

    /**
     * ne method for String(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    <R> Children neNotBlank(ColumnFunction<R, ?> column, String value);

    /**
     * ne method for Date
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    <R> Children ne(boolean execute, ColumnFunction<R, ?> column, Date value);

    /**
     * ne method for Date(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    <R> Children neNotBlank(ColumnFunction<R, ?> column, Date value);

    /**
     * ne method for LocalDate
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    <R> Children ne(boolean execute, ColumnFunction<R, ?> column, LocalDate value);

    /**
     * ne method for LocalDate(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    <R> Children neNotBlank(ColumnFunction<R, ?> column, LocalDate value);

    /**
     * ne method for LocalDateTime
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    <R> Children ne(boolean execute, ColumnFunction<R, ?> column, LocalDateTime value);

    /**
     * ne method for LocalDateTime(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    <R> Children neNotBlank(ColumnFunction<R, ?> column, LocalDateTime value);

    /**
     * ne method for Column
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    <R, V> Children ne(boolean execute, ColumnFunction<R, ?> column, ColumnFunction<V, ?> value);

    /**
     * ne method for nested function
     *
     * @param execute  true: execute the method, false: skip execution
     * @param column   lambda column name
     * @param function nested function
     * @return chain object
     */
    <R> Children ne(boolean execute, ColumnFunction<R, ?> column, Function<WhereWrapper<?>, AbstractWrapper<?>> function);

    /**
     * like method(eg: where `name` like '%zhangsan%')
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    <R> Children like(boolean execute, ColumnFunction<R, ?> column, String value);

    /**
     * like method(eg: where `name` like '%zhangsan%', execute only if value is not empty)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    <R> Children likeNotBlank(ColumnFunction<R, ?> column, String value);

    /**
     * like left method(eq: where `name` like 'zhangsan%')
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    <R> Children likeLeft(boolean execute, ColumnFunction<R, ?> column, String value);

    /**
     * like left method(eq: where `name` like 'zhangsan%', execute only if value is not empty)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    <R> Children likeLeftNotBlank(ColumnFunction<R, ?> column, String value);

    /**
     * like right method(eq: where `name` like '%zhangsan')
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    <R> Children likeRight(boolean execute, ColumnFunction<R, ?> column, String value);

    /**
     * not like method(eq: where `name` not like '%zhangsan%')
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    <R> Children notLike(boolean execute, ColumnFunction<R, ?> column, String value);

    /**
     * between method(eg: where `age` between 20 and 30)
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param start   start value
     * @param end     end value
     * @return chain object
     */
    <R> Children between(boolean execute, ColumnFunction<R, ?> column, Number start, Number end);

    /**
     * not between method(eg: where `age` not between 20 and 30)
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param start   start value
     * @param end     end value
     * @return chain object
     */
    <R> Children notBetween(boolean execute, ColumnFunction<R, ?> column, Number start, Number end);

    /**
     * between method(eg: where `birthday` between '1999-01-01 00:00:00' and '1999-12-31 23:59:59')
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param start   start value
     * @param end     end value
     * @return chain object
     */
    <R> Children between(boolean execute, ColumnFunction<R, ?> column, Date start, Date end);

    /**
     * not between method(eg: where `birthday` not between '1999-01-01 00:00:00' and '1999-12-31 23:59:59')
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param start   start value
     * @param end     end value
     * @return chain object
     */
    <R> Children notBetween(boolean execute, ColumnFunction<R, ?> column, Date start, Date end);

    /**
     * between method(eg: where `birthday` between '1999-01-01' and '1999-12-31')
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param start   start value
     * @param end     end value
     * @return chain object
     */
    <R> Children between(boolean execute, ColumnFunction<R, ?> column, LocalDate start, LocalDate end);

    /**
     * not between method(eg: where `birthday` not between '1999-01-01' and '1999-12-31')
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param start   start value
     * @param end     end value
     * @return chain object
     */
    <R> Children notBetween(boolean execute, ColumnFunction<R, ?> column, LocalDate start, LocalDate end);

    /**
     * between method(eg: where `birthday` between '1999-01-01 00:00:00' and '1999-12-31 23:59:59')
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param start   start value
     * @param end     end value
     * @return chain object
     */
    <R> Children between(boolean execute, ColumnFunction<R, ?> column, LocalDateTime start, LocalDateTime end);

    /**
     * not between method(eg: where `birthday` not between '1999-01-01 00:00:00' and '1999-12-31 23:59:59')
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param start   start value
     * @param end     end value
     * @return chain object
     */
    <R> Children notBetween(boolean execute, ColumnFunction<R, ?> column, LocalDateTime start, LocalDateTime end);

    /**
     * isNull method(eg: where `age` is null)
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @return chain object
     */
    <R> Children isNull(boolean execute, ColumnFunction<R, ?> column);

    /**
     * isNotNull method(eg: where `age` is not null)
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @return chain object
     */
    <R> Children isNotNull(boolean execute, ColumnFunction<R, ?> column);

    /**
     * isEmpty method(eg: where `name` = '')
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @return chain object
     */
    <R> Children isEmpty(boolean execute, ColumnFunction<R, ?> column);

    /**
     * isNotEmpty method(eg: where `name` != '')
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @return chain object
     */
    <R> Children isNotEmpty(boolean execute, ColumnFunction<R, ?> column);

    /**
     * in method(eg: where `id` in (1, 2, 3, 4))
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param values  given collection
     * @return chain object
     */
    <R> Children in(boolean execute, ColumnFunction<R, ?> column, Collection<?> values);

    /**
     * in method(eg: where `id` in (1, 2, 3, 4))
     *
     * @param column lambda column name
     * @param values given collection
     * @return chain object
     */
    <R> Children inNotBlank(ColumnFunction<R, ?> column, Collection<?> values);

    /**
     * in nested method(eg: where `id` in (select `id` from user where age  > 20 ))
     *
     * @param execute  true: execute the method, false: skip execution
     * @param column   lambda column name
     * @param function nested expression
     * @return chain object
     */
    <R> Children in(boolean execute, ColumnFunction<R, ?> column, Function<WhereWrapper<?>, AbstractWrapper<?>> function);

    /**
     * not in method(eg: where `id` not in (1, 2, 3, 4))
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param values  given collection
     * @return chain object
     */
    <R> Children notIn(boolean execute, ColumnFunction<R, ?> column, Collection<?> values);

    /**
     * not in nested method(eg: where `id` not in (select `id` from user where age  > 20 ))
     *
     * @param execute  true: execute the method, false: skip execution
     * @param column   lambda column name
     * @param function nested expression
     * @return chain object
     */
    <R> Children notIn(boolean execute, ColumnFunction<R, ?> column, Function<WhereWrapper<?>, AbstractWrapper<?>> function);
}
