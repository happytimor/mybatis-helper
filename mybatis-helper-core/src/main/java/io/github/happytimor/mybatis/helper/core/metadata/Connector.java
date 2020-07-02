package io.github.happytimor.mybatis.helper.core.metadata;

import io.github.happytimor.mybatis.helper.core.wrapper.WhereWrapper;

import java.util.function.Function;

/**
 * @author chenpeng
 */
public interface Connector<T, Children> {
    /**
     * 显示使用and连接符 =&gt; and (xxx)
     *
     * @param execute 是否执行片段
     * @param function  嵌入的片段
     * @return children
     */
    Children and(boolean execute, Function<WhereWrapper<T>, WhereWrapper<T>> function);

    /**
     * or连接符
     *
     * @param execute 是否执行片段
     * @return children
     */
    Children or(boolean execute);
}
