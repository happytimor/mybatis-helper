package io.github.happytimor.mybatis.helper.core.metadata;


/**
 * default implements of join method interface, very similar to JoinMethod
 *
 * @author chenpeng
 */
public interface DefaultJoinWrapper<Children> extends JoinMethod<Children> {

    default <X, Y> Children leftJoin(Class<X> leftClazz,  ColumnFunction<X, ?> leftColumn, ColumnFunction<Y, ?> rightColumn) {
        return leftJoin(true, leftClazz,  leftColumn, rightColumn);
    }

    default <X, Y> Children rightJoin(Class<X> leftClazz,  ColumnFunction<X, ?> leftColumn, ColumnFunction<Y, ?> rightColumn) {
        return leftJoin(true, leftClazz,  leftColumn, rightColumn);
    }

    default <X, Y> Children innerJoin(Class<X> leftClazz,  ColumnFunction<X, ?> leftColumn, ColumnFunction<Y, ?> rightColumn) {
        return leftJoin(true, leftClazz,  leftColumn, rightColumn);
    }

    default <X, Y> Children join(Class<X> leftClazz,  ColumnFunction<X, ?> leftColumn, ColumnFunction<Y, ?> rightColumn) {
        return leftJoin(true, leftClazz,  leftColumn, rightColumn);
    }
}
