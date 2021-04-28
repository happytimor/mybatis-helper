package io.github.happytimor.mybatis.helper.core.metadata;

import java.io.Serializable;

/**
 * join method interface
 *
 * @author chenpeng
 */
public interface JoinMethod<Children> extends Serializable {
    <X, Y> Children leftJoin(boolean execute, Class<X> leftClazz, ColumnFunction<X, ?> leftColumn, ColumnFunction<Y, ?> rightColumn);

    <X, Y> Children rightJoin(boolean execute, Class<X> leftClazz, ColumnFunction<X, ?> leftColumn, ColumnFunction<Y, ?> rightColumn);

    <X, Y> Children innerJoin(boolean execute, Class<X> leftClazz, ColumnFunction<X, ?> leftColumn, ColumnFunction<Y, ?> rightColumn);

    <X, Y> Children join(boolean execute, Class<X> leftClazz, ColumnFunction<X, ?> leftColumn, ColumnFunction<Y, ?> rightColumn);

}
