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
public interface Compare<Children, Column extends ColumnFunction<?, ?>> extends Serializable {

    /**
     * gt method for Number(eg: where `id` > 1)
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    Children gt(boolean execute, Column column, Number value);

    /**
     * gt method for Number(eg: where `id` > 1)(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    Children gtNotBlank(Column column, Number value);

    /**
     * gt method for String
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    Children gt(boolean execute, Column column, String value);

    /**
     * gt method for String(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    Children gtNotBlank(Column column, String value);

    /**
     * gt method for Date
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    Children gt(boolean execute, Column column, Date value);

    /**
     * gt method for Date(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    Children gtNotBlank(Column column, Date value);

    /**
     * gt method for LocalDate
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    Children gt(boolean execute, Column column, LocalDate value);

    /**
     * gt method for LocalDate(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    Children gtNotBlank(Column column, LocalDate value);

    /**
     * gt method for LocalDateTime
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    Children gt(boolean execute, Column column, LocalDateTime value);

    /**
     * gt method for LocalDateTime(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    Children gtNotBlank(Column column, LocalDateTime value);

    /**
     * gt method for Column(eg: where `grade_of_english` > `grade_of_math`)
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    Children gt(boolean execute, Column column, Column value);

    /**
     * gt method for Column(eg: where `grade_of_english` > `grade_of_math`)(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    Children gtNotBlank(Column column, Column value);

    /**
     * gt method for nested function
     *
     * @param execute  true: execute the method, false: skip execution
     * @param column   lambda column name
     * @param function nested function
     * @return chain object
     */
    Children gt(boolean execute, Column column, Function<WhereWrapper<?>, AbstractWrapper<?>> function);

    /**
     * ge method for Number(eg: where `id` >= 1)
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    Children ge(boolean execute, Column column, Number value);

    /**
     * ge method for Number(eg: where `id` >= 1)(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    Children geNotBlank(Column column, Number value);

    /**
     * ge method for String
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    Children ge(boolean execute, Column column, String value);

    /**
     * ge method for String(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    Children geNotBlank(Column column, String value);

    /**
     * ge method for Date
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    Children ge(boolean execute, Column column, Date value);

    /**
     * ge method for Date(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    Children geNotBlank(Column column, Date value);

    /**
     * ge method for LocalDate
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    Children ge(boolean execute, Column column, LocalDate value);

    /**
     * ge method for LocalDate(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    Children geNotBlank(Column column, LocalDate value);

    /**
     * ge method for LocalDateTime
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    Children ge(boolean execute, Column column, LocalDateTime value);

    /**
     * ge method for LocalDateTime(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    Children geNotBlank(Column column, LocalDateTime value);

    /**
     * ge method for Column
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    Children ge(boolean execute, Column column, Column value);

    /**
     * ge method for nested function
     *
     * @param execute  true: execute the method, false: skip execution
     * @param column   lambda column name
     * @param function nested function
     * @return chain object
     */
    Children ge(boolean execute, Column column, Function<WhereWrapper<?>, AbstractWrapper<?>> function);

    /**
     * eq method for Number(eg: where `id` = 1)
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    Children eq(boolean execute, Column column, Number value);

    /**
     * eq method for Number(eg: where `id` = 1)(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    Children eqNotBlank(Column column, Number value);

    /**
     * eq method for Boolean
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    Children eq(boolean execute, Column column, Boolean value);

    /**
     * eq method for Boolean(execute only if value is not null)(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    Children eqNotBlank(Column column, Boolean value);

    /**
     * eq method for String
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    Children eq(boolean execute, Column column, String value);

    /**
     * eq method for String(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    Children eqNotBlank(Column column, String value);

    /**
     * eq method for Date
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    Children eq(boolean execute, Column column, Date value);

    /**
     * eq method for Date(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    Children eqNotBlank(Column column, Date value);

    /**
     * eq method for LocalDate
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    Children eq(boolean execute, Column column, LocalDate value);

    /**
     * eq method for LocalDate(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    Children eqNotBlank(Column column, LocalDate value);

    /**
     * eq method for LocalDateTime
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    Children eq(boolean execute, Column column, LocalDateTime value);

    /**
     * eq method for LocalDateTime(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    Children eqNotBlank(Column column, LocalDateTime value);

    /**
     * eq method for Column
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    Children eq(boolean execute, Column column, Column value);

    /**
     * eq method for nested function
     *
     * @param execute  true: execute the method, false: skip execution
     * @param column   lambda column name
     * @param function nested function
     * @return chain object
     */
    Children eqNested(boolean execute, Column column, Function<WhereWrapper<?>, AbstractWrapper<?>> function);

    /**
     * le method for Number(eg: where `id` <= 1)
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    Children le(boolean execute, Column column, Number value);

    /**
     * le method for Number(eg: where `id` <= 1)(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    Children leNotBlank(Column column, Number value);

    /**
     * le method for String
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    Children le(boolean execute, Column column, String value);

    /**
     * le method for String(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    Children leNotBlank(Column column, String value);

    /**
     * le method for Date
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    Children le(boolean execute, Column column, Date value);

    /**
     * le method for Date(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    Children leNotBlank(Column column, Date value);

    /**
     * le method for LocalDate
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    Children le(boolean execute, Column column, LocalDate value);

    /**
     * le method for LocalDate(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    Children leNotBlank(Column column, LocalDate value);

    /**
     * le method for LocalDateTime
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    Children le(boolean execute, Column column, LocalDateTime value);

    /**
     * le method for LocalDateTime(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    Children leNotBlank(Column column, LocalDateTime value);

    /**
     * le method for Column
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    Children le(boolean execute, Column column, Column value);

    /**
     * le method for nested function
     *
     * @param execute  true: execute the method, false: skip execution
     * @param column   lambda column name
     * @param function nested function
     * @return chain object
     */
    Children le(boolean execute, Column column, Function<WhereWrapper<?>, AbstractWrapper<?>> function);

    /**
     * lt method for Number(eg: where `id` < 1)
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    Children lt(boolean execute, Column column, Number value);

    /**
     * lt method for Number(eg: where `id` < 1)(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    Children ltNotBlank(Column column, Number value);

    /**
     * lt method for String
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    Children lt(boolean execute, Column column, String value);

    /**
     * lt method for String(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    Children ltNotBlank(Column column, String value);

    /**
     * lt method for Date
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    Children lt(boolean execute, Column column, Date value);

    /**
     * lt method for Date(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    Children ltNotBlank(Column column, Date value);

    /**
     * lt method for LocalDate
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    Children lt(boolean execute, Column column, LocalDate value);

    /**
     * lt method for LocalDate(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    Children ltNotBlank(Column column, LocalDate value);

    /**
     * lt method for LocalDateTime
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    Children lt(boolean execute, Column column, LocalDateTime value);

    /**
     * lt method for LocalDateTime(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    Children ltNotBlank(Column column, LocalDateTime value);

    /**
     * lt method for Column
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    Children lt(boolean execute, Column column, Column value);

    /**
     * lt method for nested function
     *
     * @param execute  true: execute the method, false: skip execution
     * @param column   lambda column name
     * @param function nested function
     * @return chain object
     */
    Children lt(boolean execute, Column column, Function<WhereWrapper<?>, AbstractWrapper<?>> function);


    /**
     * ne method(eg: where `id` <> 1)
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    Children ne(boolean execute, Column column, Number value);

    /**
     * ne method(eg: where `id` <> 1)(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    Children neNotBlank(Column column, Number value);

    /**
     * ne method for String
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    Children ne(boolean execute, Column column, String value);

    /**
     * ne method for String(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    Children neNotBlank(Column column, String value);

    /**
     * ne method for Date
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    Children ne(boolean execute, Column column, Date value);

    /**
     * ne method for Date(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    Children neNotBlank(Column column, Date value);

    /**
     * ne method for LocalDate
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    Children ne(boolean execute, Column column, LocalDate value);

    /**
     * ne method for LocalDate(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    Children neNotBlank(Column column, LocalDate value);

    /**
     * ne method for LocalDateTime
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    Children ne(boolean execute, Column column, LocalDateTime value);

    /**
     * ne method for LocalDateTime(execute only if value is not null)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    Children neNotBlank(Column column, LocalDateTime value);

    /**
     * ne method for Column
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    Children ne(boolean execute, Column column, Column value);

    /**
     * ne method for nested function
     *
     * @param execute  true: execute the method, false: skip execution
     * @param column   lambda column name
     * @param function nested function
     * @return chain object
     */
    Children ne(boolean execute, Column column, Function<WhereWrapper<?>, AbstractWrapper<?>> function);

    /**
     * like method(eg: where `name` like '%zhangsan%')
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    Children like(boolean execute, Column column, String value);

    /**
     * like left method(eq: where `name` like 'zhangsan%')
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    Children likeLeft(boolean execute, Column column, String value);

    /**
     * like right method(eq: where `name` like '%zhangsan')
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    Children likeRight(boolean execute, Column column, String value);

    /**
     * not like method(eq: where `name` not like '%zhangsan%')
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param value   given object
     * @return chain object
     */
    Children notLike(boolean execute, Column column, String value);

    /**
     * between method(eg: where `age` between 20 and 30)
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param start   start value
     * @param end     end value
     * @return chain object
     */
    Children between(boolean execute, Column column, Number start, Number end);

    /**
     * not between method(eg: where `age` not between 20 and 30)
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param start   start value
     * @param end     end value
     * @return chain object
     */
    Children notBetween(boolean execute, Column column, Number start, Number end);

    /**
     * between method(eg: where `birthday` between '1999-01-01 00:00:00' and '1999-12-31 23:59:59')
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param start   start value
     * @param end     end value
     * @return chain object
     */
    Children between(boolean execute, Column column, Date start, Date end);

    /**
     * not between method(eg: where `birthday` not between '1999-01-01 00:00:00' and '1999-12-31 23:59:59')
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param start   start value
     * @param end     end value
     * @return chain object
     */
    Children notBetween(boolean execute, Column column, Date start, Date end);

    /**
     * between method(eg: where `birthday` between '1999-01-01' and '1999-12-31')
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param start   start value
     * @param end     end value
     * @return chain object
     */
    Children between(boolean execute, Column column, LocalDate start, LocalDate end);

    /**
     * not between method(eg: where `birthday` not between '1999-01-01' and '1999-12-31')
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param start   start value
     * @param end     end value
     * @return chain object
     */
    Children notBetween(boolean execute, Column column, LocalDate start, LocalDate end);

    /**
     * between method(eg: where `birthday` between '1999-01-01 00:00:00' and '1999-12-31 23:59:59')
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param start   start value
     * @param end     end value
     * @return chain object
     */
    Children between(boolean execute, Column column, LocalDateTime start, LocalDateTime end);

    /**
     * not between method(eg: where `birthday` not between '1999-01-01 00:00:00' and '1999-12-31 23:59:59')
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param start   start value
     * @param end     end value
     * @return chain object
     */
    Children notBetween(boolean execute, Column column, LocalDateTime start, LocalDateTime end);

    /**
     * isNull method(eg: where `age` is null)
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @return chain object
     */
    Children isNull(boolean execute, Column column);

    /**
     * isNotNull method(eg: where `age` is not null)
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @return chain object
     */
    Children isNotNull(boolean execute, Column column);

    /**
     * isEmpty method(eg: where `name` = '')
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @return chain object
     */
    Children isEmpty(boolean execute, Column column);

    /**
     * isNotEmpty method(eg: where `name` != '')
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @return chain object
     */
    Children isNotEmpty(boolean execute, Column column);

    /**
     * in method(eg: where `id` in (1, 2, 3, 4))
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param values  given collection
     * @return chain object
     */
    Children in(boolean execute, Column column, Collection<?> values);

    /**
     * in method(eg: where `id` in (1, 2, 3, 4))
     *
     * @param column lambda column name
     * @param values given collection
     * @return chain object
     */
    Children inNotBlank(Column column, Collection<?> values);

    /**
     * in nested method(eg: where `id` in (select `id` from user where age  > 20 ))
     *
     * @param execute  true: execute the method, false: skip execution
     * @param column   lambda column name
     * @param function nested expression
     * @return chain object
     */
    Children in(boolean execute, Column column, Function<WhereWrapper<?>, AbstractWrapper<?>> function);

    /**
     * not in method(eg: where `id` not in (1, 2, 3, 4))
     *
     * @param execute true: execute the method, false: skip execution
     * @param column  lambda column name
     * @param values  given collection
     * @return chain object
     */
    Children notIn(boolean execute, Column column, Collection<?> values);

    /**
     * not in nested method(eg: where `id` not in (select `id` from user where age  > 20 ))
     *
     * @param execute  true: execute the method, false: skip execution
     * @param column   lambda column name
     * @param function nested expression
     * @return chain object
     */
    Children notIn(boolean execute, Column column, Function<WhereWrapper<?>, AbstractWrapper<?>> function);
}
