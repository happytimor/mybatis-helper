package io.github.happytimor.mybatis.helper.core.wrapper;


import io.github.happytimor.mybatis.helper.core.common.Constants;
import io.github.happytimor.mybatis.helper.core.metadata.ColumnFunction;
import io.github.happytimor.mybatis.helper.core.metadata.DefaultJoinWrapper;
import io.github.happytimor.mybatis.helper.core.metadata.JoinInfo;

/**
 * @author chenpeng
 */
public class JoinWrapper<T> extends WhereWrapper<T>
        implements DefaultJoinWrapper<JoinWrapper<T>> {

    public JoinWrapper() {

    }

    @Override
    public <X, Y> JoinWrapper<T> leftJoin(boolean execute, Class<X> leftClazz,
                                             ColumnFunction<X, ?> leftColumn,
                                             ColumnFunction<Y, ?> rightColumn) {
        return this.join(Constants.LEFT_JOIN, execute, leftClazz, leftColumn, rightColumn);
    }

    @Override
    public <X, Y> JoinWrapper<T> rightJoin(boolean execute, Class<X> leftClazz,
                                              ColumnFunction<X, ?> leftColumn, ColumnFunction<Y, ?> rightColumn) {
        return this.join(Constants.RIGHT_JOIN, execute, leftClazz, leftColumn, rightColumn);
    }

    @Override
    public <X, Y> JoinWrapper<T> innerJoin(boolean execute, Class<X> leftClazz,
                                              ColumnFunction<X, ?> leftColumn, ColumnFunction<Y, ?> rightColumn) {
        return this.join(Constants.INNER_JOIN, execute, leftClazz, leftColumn, rightColumn);
    }

    @Override
    public <X, Y> JoinWrapper<T> join(boolean execute, Class<X> leftClazz,
                                         ColumnFunction<X, ?> leftColumn, ColumnFunction<Y, ?> rightColumn) {
        return this.join(Constants.JOIN, execute, leftClazz, leftColumn, rightColumn);
    }

    private <X, Y> JoinWrapper<T> join(String keyword, boolean execute, Class<X> leftClazz,
                                          ColumnFunction<X, ?> leftColumn, ColumnFunction<Y, ?> rightColumn) {
        if (execute) {
            if (subTable.putIfAbsent(leftClazz, tableIndex) == null) {
                tableIndex++;
            }
            if (subTable.putIfAbsent(leftClazz, tableIndex) == null) {
                tableIndex++;
            }
            joinInfoList.add(new JoinInfo(keyword, leftClazz, leftColumn, rightColumn));
        }
        return this;
    }
}
