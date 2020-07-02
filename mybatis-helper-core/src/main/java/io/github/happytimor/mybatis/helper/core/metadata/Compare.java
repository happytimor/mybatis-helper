package io.github.happytimor.mybatis.helper.core.metadata;

import io.github.happytimor.mybatis.helper.core.wrapper.AbstractWrapper;
import io.github.happytimor.mybatis.helper.core.wrapper.WhereWrapper;

import java.io.Serializable;
import java.util.Collection;
import java.util.function.Function;

/**
 * compare method interface
 *
 * @author chenpeng
 */
public interface Compare<Children, Column extends ColumnFunction<?, ?>> extends Serializable {

    /**
     * gt method(eg: where `id` > 1)
     *
     * @param executeIf true: execute the method, false: skip execution
     * @param column    lambda column name
     * @param value     given object
     * @return chain object
     */
    Children gt(boolean executeIf, Column column, Object value);

    /**
     * gt method for column(eg: where `grade_of_english` > `grade_of_math`)
     *
     * @param executeIf true: execute the method, false: skip execution
     * @param column    lambda column name
     * @param value     the compare column
     * @return chain object
     */
    Children gtColumn(boolean executeIf, Column column, Column value);

    /**
     * gt nested method(eg: where `grade` > (select `grade` form user where `id` = 1))
     *
     * @param executeIf true: execute the method, false: skip execution
     * @param column    lambda column name
     * @param function  nested expression
     * @return chain object
     */
    Children gtNested(boolean executeIf, Column column, Function<WhereWrapper<?>, AbstractWrapper<?>> function);

    /**
     * ge method(eg: where `id` >= 1)
     *
     * @param executeIf true: execute the method, false: skip execution
     * @param column    lambda column name
     * @param value     given object
     * @return chain object
     */
    Children ge(boolean executeIf, Column column, Object value);

    /**
     * ge method for column(eg: where `grade_of_english` >= `grade_of_math`)
     *
     * @param executeIf true: execute the method, false: skip execution
     * @param column    lambda column name
     * @param value     the compare column
     * @return chain object
     */
    Children geColumn(boolean executeIf, Column column, Column value);

    /**
     * ge nested method(eg: where `grade` >= (select `grade` form user where `id` = 1))
     *
     * @param executeIf true: execute the method, false: skip execution
     * @param column    lambda column name
     * @param function  nested expression
     * @return chain object
     */
    Children geNested(boolean executeIf, Column column, Function<WhereWrapper<?>, AbstractWrapper<?>> function);

    /**
     * eq method(eg: where `id` = 1)
     *
     * @param executeIf true: execute the method, false: skip execution
     * @param column    lambda column name
     * @param value     given object
     * @return chain object
     */
    Children eq(boolean executeIf, Column column, Object value);

    /**
     * eq method for column(eg: where `grade_of_english` = `grade_of_math`)
     *
     * @param executeIf true: execute the method, false: skip execution
     * @param column    lambda column name
     * @param value     the compare column
     * @return chain object
     */
    Children eqColumn(boolean executeIf, Column column, Column value);

    /**
     * eq nested method(eg: where `grade` = (select `grade` form user where `id` = 1))
     *
     * @param executeIf true: execute the method, false: skip execution
     * @param column    lambda column name
     * @param function  nested expression
     * @return chain object
     */
    Children eqNested(boolean executeIf, Column column, Function<WhereWrapper<?>, AbstractWrapper<?>> function);

    /**
     * le method(eg: where `id` <= 1)
     *
     * @param executeIf true: execute the method, false: skip execution
     * @param column    lambda column name
     * @param value     given object
     * @return chain object
     */
    Children le(boolean executeIf, Column column, Object value);

    /**
     * le method for column(eg: where `grade_of_english` <= `grade_of_math`)
     *
     * @param executeIf true: execute the method, false: skip execution
     * @param column    lambda column name
     * @param value     the compare column
     * @return chain object
     */
    Children leColumn(boolean executeIf, Column column, Column value);

    /**
     * le nested method(eg: where `grade` <= (select `grade` form user where `id` = 1))
     *
     * @param executeIf true: execute the method, false: skip execution
     * @param column    lambda column name
     * @param function  nested expression
     * @return chain object
     */
    Children leNested(boolean executeIf, Column column, Function<WhereWrapper<?>, AbstractWrapper<?>> function);

    /**
     * lt method(eg: where `id` < 1)
     *
     * @param executeIf true: execute the method, false: skip execution
     * @param column    lambda column name
     * @param value     given object
     * @return chain object
     */
    Children lt(boolean executeIf, Column column, Object value);

    /**
     * lt method for column(eg: where `grade_of_english` < `grade_of_math`)
     *
     * @param executeIf true: execute the method, false: skip execution
     * @param column    lambda column name
     * @param value     the compare column
     * @return chain object
     */
    Children ltColumn(boolean executeIf, Column column, Column value);

    /**
     * lt nested method(eg: where `grade` < (select `grade` form user where `id` = 1))
     *
     * @param executeIf true: execute the method, false: skip execution
     * @param column    lambda column name
     * @param function  nested expression
     * @return chain object
     */
    Children ltNested(boolean executeIf, Column column, Function<WhereWrapper<?>, AbstractWrapper<?>> function);


    /**
     * ne method(eg: where `id` <> 1)
     *
     * @param executeIf true: execute the method, false: skip execution
     * @param column    lambda column name
     * @param value     given object
     * @return chain object
     */
    Children ne(boolean executeIf, Column column, Object value);

    /**
     * ne method for column(eg: where `grade_of_english` <> `grade_of_math`)
     *
     * @param executeIf true: execute the method, false: skip execution
     * @param column    lambda column name
     * @param value     the compare column
     * @return chain object
     */
    Children neColumn(boolean executeIf, Column column, Column value);

    /**
     * ne nested method(eg: where `grade` <> (select `grade` form user where `id` = 1))
     *
     * @param executeIf true: execute the method, false: skip execution
     * @param column    lambda column name
     * @param function  nested expression
     * @return chain object
     */
    Children neNested(boolean executeIf, Column column, Function<WhereWrapper<?>, AbstractWrapper<?>> function);

    /**
     * like method(eg: where `name` like '%zhangsan%')
     *
     * @param executeIf true: execute the method, false: skip execution
     * @param column    lambda column name
     * @param value     given object
     * @return chain object
     */
    Children like(boolean executeIf, Column column, String value);

    /**
     * like left method(eq: where `name` like 'zhangsan%')
     *
     * @param executeIf true: execute the method, false: skip execution
     * @param column    lambda column name
     * @param value     given object
     * @return chain object
     */
    Children likeLeft(boolean executeIf, Column column, String value);

    /**
     * like right method(eq: where `name` like '%zhangsan')
     *
     * @param executeIf true: execute the method, false: skip execution
     * @param column    lambda column name
     * @param value     given object
     * @return chain object
     */
    Children likeRight(boolean executeIf, Column column, String value);

    /**
     * not like method(eq: where `name` not like '%zhangsan%')
     *
     * @param executeIf true: execute the method, false: skip execution
     * @param column    lambda column name
     * @param value     given object
     * @return chain object
     */
    Children notLike(boolean executeIf, Column column, String value);

    /**
     * between method(eg: where `age` between 20 and 30)
     *
     * @param executeIf true: execute the method, false: skip execution
     * @param column    lambda column name
     * @param start     start value
     * @param end       end value
     * @return chain object
     */
    Children between(boolean executeIf, Column column, Object start, Object end);

    /**
     * not between method(eg: where `age` not between 20 and 30)
     *
     * @param executeIf true: execute the method, false: skip execution
     * @param column    lambda column name
     * @param start     start value
     * @param end       end value
     * @return chain object
     */
    Children notBetween(boolean executeIf, Column column, Object start, Object end);

    /**
     * isNull method(eg: where `age` is null)
     *
     * @param executeIf true: execute the method, false: skip execution
     * @param column    lambda column name
     * @return chain object
     */
    Children isNull(boolean executeIf, Column column);

    /**
     * isNotNull method(eg: where `age` is not null)
     *
     * @param executeIf true: execute the method, false: skip execution
     * @param column    lambda column name
     * @return chain object
     */
    Children isNotNull(boolean executeIf, Column column);

    /**
     * in method(eg: where `id` in (1, 2, 3, 4))
     *
     * @param executeIf true: execute the method, false: skip execution
     * @param column    lambda column name
     * @param values    given collection
     * @return chain object
     */
    Children in(boolean executeIf, Column column, Collection<?> values);


    /**
     * in nested method(eg: where `id` in (select `id` from user where age  > 20 ))
     *
     * @param executeIf true: execute the method, false: skip execution
     * @param column    lambda column name
     * @param function  nested expression
     * @return chain object
     */
    Children inNested(boolean executeIf, Column column, Function<WhereWrapper<?>, AbstractWrapper<?>> function);

    /**
     * not in method(eg: where `id` not in (1, 2, 3, 4))
     *
     * @param executeIf true: execute the method, false: skip execution
     * @param column    lambda column name
     * @param values    given collection
     * @return chain object
     */
    Children notIn(boolean executeIf, Column column, Collection<?> values);

    /**
     * not in nested method(eg: where `id` not in (select `id` from user where age  > 20 ))
     *
     * @param executeIf true: execute the method, false: skip execution
     * @param column    lambda column name
     * @param function  nested expression
     * @return chain object
     */
    Children notInNested(boolean executeIf, Column column, Function<WhereWrapper<?>, AbstractWrapper<?>> function);
}
