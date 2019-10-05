package io.github.happytimor.mybatis.helper.core.metadata;

import io.github.happytimor.mybatis.helper.core.wrapper.WhereWrapper;

import java.util.function.Function;

/**
 * @author chenpeng
 * @date 2019-09-08
 */
public interface DefaultConnector<T, Children> extends Connector<T, Children> {

    /**
     * 显示使用and连接符 => and (xxx)
     *
     * @param function 嵌入的片段
     * @return children
     */
    default Children and(Function<WhereWrapper<T>, WhereWrapper<T>> function) {
        return and(true, function);
    }

    /**
     * or连接符
     *
     * @return children
     */
    default Children or() {
        return or(true);
    }
}
