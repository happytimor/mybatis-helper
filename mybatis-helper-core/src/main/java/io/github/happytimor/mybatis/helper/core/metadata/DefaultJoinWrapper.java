package io.github.happytimor.mybatis.helper.core.metadata;


/**
 * default implements of join method interface, very similar to JoinMethod
 *
 * @author chenpeng
 */
public interface DefaultJoinWrapper<Children> extends JoinMethod<Children> {

    /**
     * leftJoin
     *
     * @param leftClazz   leftClazz
     * @param leftColumn  leftColumn
     * @param rightColumn rightColumn
     * @param <X>         x
     * @param <Y>         y
     * @return Children
     */
    default <X, Y> Children leftJoin(Class<X> leftClazz, ColumnFunction<X, ?> leftColumn, ColumnFunction<Y, ?> rightColumn) {
        return leftJoin(true, leftClazz, leftColumn, rightColumn);
    }

    /**
     * rightJoin
     *
     * @param leftClazz   leftClazz
     * @param leftColumn  leftColumn
     * @param rightColumn rightColumn
     * @param <X>         x
     * @param <Y>         y
     * @return Children
     */
    default <X, Y> Children rightJoin(Class<X> leftClazz, ColumnFunction<X, ?> leftColumn, ColumnFunction<Y, ?> rightColumn) {
        return rightJoin(true, leftClazz, leftColumn, rightColumn);
    }

    /**
     * innerJoin
     *
     * @param leftClazz   leftClazz
     * @param leftColumn  leftColumn
     * @param rightColumn rightColumn
     * @param <X>         x
     * @param <Y>         y
     * @return Children
     */
    default <X, Y> Children innerJoin(Class<X> leftClazz, ColumnFunction<X, ?> leftColumn, ColumnFunction<Y, ?> rightColumn) {
        return innerJoin(true, leftClazz, leftColumn, rightColumn);
    }

    /**
     * join
     *
     * @param leftClazz   leftClazz
     * @param leftColumn  leftColumn
     * @param rightColumn rightColumn
     * @param <X>         x
     * @param <Y>         y
     * @return Children
     */
    default <X, Y> Children join(Class<X> leftClazz, ColumnFunction<X, ?> leftColumn, ColumnFunction<Y, ?> rightColumn) {
        return join(true, leftClazz, leftColumn, rightColumn);
    }
}
