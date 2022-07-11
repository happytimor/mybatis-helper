package io.github.happytimor.mybatis.helper.core.metadata;

import java.io.Serializable;

/**
 * join method interface
 *
 * @author chenpeng
 */
public interface JoinMethod<Children> extends Serializable {
    /**
     * leftJoin
     *
     * @param execute     will execute method if `execute` is true
     * @param leftClazz   leftClazz
     * @param leftColumn  leftColumn
     * @param rightColumn rightColumn
     * @param <X>         x
     * @param <Y>         y
     * @return Children
     */
    <X, Y> Children leftJoin(boolean execute, Class<X> leftClazz, ColumnFunction<X, ?> leftColumn, ColumnFunction<Y, ?> rightColumn);

    /**
     * rightJoin
     *
     * @param execute     will execute method if `execute` is true
     * @param leftClazz   leftClazz
     * @param leftColumn  leftColumn
     * @param rightColumn rightColumn
     * @param <X>         x
     * @param <Y>         y
     * @return Children
     */
    <X, Y> Children rightJoin(boolean execute, Class<X> leftClazz, ColumnFunction<X, ?> leftColumn, ColumnFunction<Y, ?> rightColumn);

    /**
     * innerJoin
     *
     * @param execute     will execute method if `execute` is true
     * @param leftClazz   leftClazz
     * @param leftColumn  leftColumn
     * @param rightColumn rightColumn
     * @param <X>         x
     * @param <Y>         y
     * @return Children
     */
    <X, Y> Children innerJoin(boolean execute, Class<X> leftClazz, ColumnFunction<X, ?> leftColumn, ColumnFunction<Y, ?> rightColumn);

    /**
     * join
     *
     * @param execute     will execute method if `execute` is true
     * @param leftClazz   leftClazz
     * @param leftColumn  leftColumn
     * @param rightColumn rightColumn
     * @param <X>         x
     * @param <Y>         y
     * @return Children
     */
    <X, Y> Children join(boolean execute, Class<X> leftClazz, ColumnFunction<X, ?> leftColumn, ColumnFunction<Y, ?> rightColumn);

}
