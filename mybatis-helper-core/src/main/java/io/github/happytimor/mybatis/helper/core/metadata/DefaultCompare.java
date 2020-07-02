package io.github.happytimor.mybatis.helper.core.metadata;


import io.github.happytimor.mybatis.helper.core.wrapper.AbstractWrapper;
import io.github.happytimor.mybatis.helper.core.wrapper.WhereWrapper;

import java.util.Collection;
import java.util.function.Function;

/**
 * default implements of compare method interface, very similar to Compare
 *
 * @author chenpeng
 */
public interface DefaultCompare<Children, Column extends ColumnFunction<?, ?>> extends Compare<Children, Column> {
    /**
     * gt method(eg: where `id` > 1)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default Children gt(Column column, Object value) {
        return gt(true, column, value);
    }

    /**
     * gt method for column(eg: where `grade_of_english` > `grade_of_math`)
     *
     * @param column lambda column name
     * @param value  the compare column
     * @return chain object
     */
    default Children gtColumn(Column column, Column value) {
        return gtColumn(true, column, value);
    }

    /**
     * gt nested method(eg: where `grade` > (select `grade` form user where `id` = 1))
     *
     * @param column   lambda column name
     * @param function nested expression
     * @return chain object
     */
    default Children gtNested(Column column, Function<WhereWrapper<?>, AbstractWrapper<?>> function) {
        return gtNested(true, column, function);
    }

    /**
     * ge method(eg: where `id` >= 1)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default Children ge(Column column, Object value) {
        return ge(true, column, value);
    }

    /**
     * ge method for column(eg: where `grade_of_english` >= `grade_of_math`)
     *
     * @param column lambda column name
     * @param value  the compare column
     * @return chain object
     */
    default Children geColumn(Column column, Column value) {
        return geColumn(true, column, value);
    }

    /**
     * ge nested method(eg: where `grade` >= (select `grade` form user where `id` = 1))
     *
     * @param column   lambda column name
     * @param function nested expression
     * @return chain object
     */
    default Children geNested(Column column, Function<WhereWrapper<?>, AbstractWrapper<?>> function) {
        return geNested(true, column, function);
    }

    /**
     * eq method(eg: where `id` = 1)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default Children eq(Column column, Object value) {
        return eq(true, column, value);
    }

    /**
     * eq method for column(eg: where `grade_of_english` = `grade_of_math`)
     *
     * @param column lambda column name
     * @param value  the compare column
     * @return chain object
     */
    default Children eqColumn(Column column, Column value) {
        return eqColumn(true, column, value);
    }

    /**
     * eq nested method(eg: where `grade` = (select `grade` form user where `id` = 1))
     *
     * @param column   lambda column name
     * @param function nested expression
     * @return chain object
     */
    default Children eqNested(Column column, Function<WhereWrapper<?>, AbstractWrapper<?>> function) {
        return eqNested(true, column, function);
    }

    /**
     * le method(eg: where `id` <= 1)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default Children le(Column column, Object value) {
        return le(true, column, value);
    }


    /**
     * le method for column(eg: where `grade_of_english` <= `grade_of_math`)
     *
     * @param column lambda column name
     * @param value  the compare column
     * @return chain object
     */
    default Children leColumn(Column column, Column value) {
        return leColumn(true, column, value);
    }

    /**
     * le nested method(eg: where `grade` <= (select `grade` form user where `id` = 1))
     *
     * @param column   lambda column name
     * @param function nested expression
     * @return chain object
     */
    default Children leNested(Column column, Function<WhereWrapper<?>, AbstractWrapper<?>> function) {
        return leNested(true, column, function);
    }

    /**
     * lt method(eg: where `id` < 1)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default Children lt(Column column, Object value) {
        return lt(true, column, value);
    }

    /**
     * lt method for column(eg: where `grade_of_english` < `grade_of_math`)
     *
     * @param column lambda column name
     * @param value  the compare column
     * @return chain object
     */
    default Children ltColumn(Column column, Column value) {
        return ltColumn(true, column, value);
    }

    /**
     * lt nested method(eg: where `grade` < (select `grade` form user where `id` = 1))
     *
     * @param column   lambda column name
     * @param function nested expression
     * @return chain object
     */
    default Children ltNested(Column column, Function<WhereWrapper<?>, AbstractWrapper<?>> function) {
        return ltNested(true, column, function);
    }

    /**
     * ne method(eg: where `id` <> 1)
     *
     * @param column lambda column name
     * @param value  given object
     * @return chain object
     */
    default Children ne(Column column, Object value) {
        return ne(true, column, value);
    }

    /**
     * ne method for column(eg: where `grade_of_english` <> `grade_of_math`)
     *
     * @param column lambda column name
     * @param value  the compare column
     * @return chain object
     */
    default Children neColumn(Column column, Column value) {
        return neColumn(true, column, value);
    }

    /**
     * ne nested method(eg: where `grade` <> (select `grade` form user where `id` = 1))
     *
     * @param column   lambda column name
     * @param function nested expression
     * @return chain object
     */
    default Children neNested(Column column, Function<WhereWrapper<?>, AbstractWrapper<?>> function) {
        return neNested(true, column, function);
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
    default Children between(Column column, Object start, Object end) {
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
    default Children notBetween(Column column, Object start, Object end) {
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
    default Children inNested(Column column, Function<WhereWrapper<?>, AbstractWrapper<?>> function) {
        return inNested(true, column, function);
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
    default Children notInNested(Column column, Function<WhereWrapper<?>, AbstractWrapper<?>> function) {
        return notInNested(true, column, function);
    }
}
