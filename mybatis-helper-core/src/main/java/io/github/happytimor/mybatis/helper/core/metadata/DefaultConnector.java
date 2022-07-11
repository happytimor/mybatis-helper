package io.github.happytimor.mybatis.helper.core.metadata;

import io.github.happytimor.mybatis.helper.core.common.DiySql;
import io.github.happytimor.mybatis.helper.core.wrapper.WhereWrapper;

import java.util.function.Function;

/**
 * @author chenpeng
 */
public interface DefaultConnector<T, Children> extends Connector<T, Children> {

    /**
     * use `and` connector, =&gt; and (xxx)
     *
     * @param function basic function
     * @return children
     */
    default Children and(Function<WhereWrapper<T>, WhereWrapper<T>> function) {
        return and(true, function);
    }

    /**
     * use `and` connector =&gt; and (xxx)
     *
     * @param function basic function
     * @return children
     */
    default Children andDiySql(Function<WhereWrapper<T>, DiySql<T>> function) {
        return andDiySql(true, function);
    }

    /**
     * user `or` connector
     *
     * @return children
     */
    default Children or() {
        return or(true);
    }
}
