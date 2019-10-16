package io.github.happytimor.mybatis.helper.core.metadata;

import io.github.happytimor.mybatis.helper.core.wrapper.AbstractWrapper;
import io.github.happytimor.mybatis.helper.core.wrapper.WhereWrapper;

import java.io.Serializable;
import java.util.Collection;
import java.util.function.Function;

/**
 * 值比较接口
 *
 * @author chenpeng
 */
public interface Compare<Children, Column> extends Serializable {

    /**
     * 大于
     *
     * @param executeIf 是否执行片段
     * @param column    字段名称
     * @param value     字段值
     * @return children
     */
    Children gt(boolean executeIf, Column column, Object value);

    /**
     * 大于等于
     *
     * @param executeIf 是否执行片段
     * @param column    字段名称
     * @param value     字段值
     * @return children
     */
    Children ge(boolean executeIf, Column column, Object value);

    /**
     * 等于
     *
     * @param executeIf 是否执行片段
     * @param column    字段名称
     * @param value     字段值
     * @return children
     */
    Children eq(boolean executeIf, Column column, Object value);

    /**
     * 小于等于
     *
     * @param executeIf 是否执行片段
     * @param column    字段名称
     * @param value     字段值
     * @return children
     */
    Children le(boolean executeIf, Column column, Object value);

    /**
     * 小于
     *
     * @param executeIf 是否执行片段
     * @param column    字段名称
     * @param value     字段值
     * @return children
     */
    Children lt(boolean executeIf, Column column, Object value);


    /**
     * 不等于
     *
     * @param executeIf 是否执行片段
     * @param column    字段名称
     * @param value     字段值
     * @return children
     */
    Children ne(boolean executeIf, Column column, Object value);

    /**
     * 模糊匹配
     *
     * @param executeIf 是否执行片段
     * @param column    字段名称
     * @param value     字段值
     * @return children
     */
    Children like(boolean executeIf, Column column, String value);

    /**
     * 左模糊匹配
     *
     * @param executeIf 是否执行片段
     * @param column    字段名称
     * @param value     字段值
     * @return children
     */
    Children likeLeft(boolean executeIf, Column column, String value);

    /**
     * 右模糊匹配
     *
     * @param executeIf 是否执行片段
     * @param column    字段名称
     * @param value     字段值
     * @return children
     */
    Children likeRight(boolean executeIf, Column column, String value);

    /**
     * 反向模糊匹配
     *
     * @param executeIf 是否执行片段
     * @param column    字段名称
     * @param value     字段值
     * @return children
     */
    Children notLike(boolean executeIf, Column column, String value);

    /**
     * 区间查找
     *
     * @param executeIf 是否执行片段
     * @param column    字段名称
     * @param start     开始值
     * @param end       结束值
     * @return children
     */
    Children between(boolean executeIf, Column column, Object start, Object end);

    /**
     * 反向区间查找
     *
     * @param executeIf 是否执行片段
     * @param column    字段名称
     * @param start     开始值
     * @param end       结束值
     * @return children
     */
    Children notBetween(boolean executeIf, Column column, Object start, Object end);

    /**
     * 为空
     *
     * @param executeIf 是否执行片段
     * @param column    字段名称
     * @return children
     */
    Children isNull(boolean executeIf, Column column);

    /**
     * 不为空
     *
     * @param executeIf 是否执行片段
     * @param column    字段名称
     * @return children
     */
    Children isNotNull(boolean executeIf, Column column);

    /**
     * in
     *
     * @param executeIf 是否执行片段
     * @param column    字段名称
     * @param values    对象列表
     * @return children
     */
    Children in(boolean executeIf, Column column, Collection<?> values);


    /**
     * 嵌套in查询
     *
     * @param executeIf 是否执行
     * @param column    字段
     * @param function  生成子查询
     * @return children
     */
    Children in(boolean executeIf, Column column, Function<WhereWrapper<?>, AbstractWrapper<?>> function);

    /**
     * 嵌套not in查询
     *
     * @param executeIf 是否执行
     * @param column    字段
     * @param function  生成子查询
     * @return children
     */
    Children notIn(boolean executeIf, Column column, Function<WhereWrapper<?>, AbstractWrapper<?>> function);

    /**
     * notIn
     *
     * @param executeIf 是否执行片段
     * @param column    字段名称
     * @param values    对象列表
     * @return children
     */
    Children notIn(boolean executeIf, Column column, Collection<?> values);
}
