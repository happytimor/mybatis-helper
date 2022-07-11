package io.github.happytimor.mybatis.helper.core.metadata;

import io.github.happytimor.mybatis.helper.core.common.DiySql;
import io.github.happytimor.mybatis.helper.core.wrapper.WhereWrapper;

import java.util.function.Function;

/**
 * @author chenpeng
 */
public interface Connector<T, Children> {
    /**
     * use `and` connector, =&gt; and (xxx)
     *
     * @param execute  will execute function if `execute` is true
     * @param function basic function
     * @return children
     */
    Children and(boolean execute, Function<WhereWrapper<T>, WhereWrapper<T>> function);

    /**
     * use `and` connector, =&gt; and (xxx), and will append customized sql
     *
     * @param execute  will execute function if `execute` is true
     * @param function basic function
     * @return children
     */
    Children andDiySql(boolean execute, Function<WhereWrapper<T>, DiySql<T>> function);

    /**
     * use `or` connector
     *
     * @param execute will execute function if `execute` is true
     * @return children
     */
    Children or(boolean execute);
}
