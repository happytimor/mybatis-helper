package io.github.happytimor.mybatis.helper.core.metadata;


import java.util.Collection;

/**
 * Compare的默认实现，如果不指定判断条件，默认会将column和value纳入查询条件内
 *
 * @author chenpeng
 */
public interface DefaultCompare<Children, Column> extends Compare<Children, Column> {
    /**
     * 大于
     *
     * @param column 字段
     * @param value  比较值
     * @return children
     */
    default Children gt(Column column, Object value) {
        return gt(true, column, value);
    }

    /**
     * 大于等于
     *
     * @param column 字段
     * @param value  比较值
     * @return children
     */
    default Children ge(Column column, Object value) {
        return ge(true, column, value);
    }


    /**
     * 等于
     *
     * @param column 字段
     * @param value  比较值
     * @return children
     */
    default Children eq(Column column, Object value) {
        return eq(true, column, value);
    }


    /**
     * 小于等于
     *
     * @param column 字段
     * @param value  比较值
     * @return children
     */
    default Children le(Column column, Object value) {
        return le(true, column, value);
    }

    /**
     * 小于
     *
     * @param column 字段
     * @param value  比较值
     * @return children
     */
    default Children lt(Column column, Object value) {
        return lt(true, column, value);
    }


    /**
     * 不等于
     *
     * @param column 字段
     * @param value  比较值
     * @return children
     */
    default Children ne(Column column, Object value) {
        return le(true, column, value);
    }

    /**
     * 模糊匹配
     *
     * @param column 字段
     * @param value  比较值
     * @return children
     */
    default Children like(Column column, String value) {
        return like(true, column, value);
    }

    /**
     * 左模糊匹配
     *
     * @param column 字段
     * @param value  比较值
     * @return children
     */
    default Children likeLeft(Column column, String value) {
        return likeLeft(true, column, value);
    }

    /**
     * 右模糊匹配
     *
     * @param column 字段
     * @param value  比较值
     * @return children
     */
    default Children likeRight(Column column, String value) {
        return likeRight(true, column, value);
    }

    /**
     * 反向模糊匹配
     *
     * @param column 字段
     * @param value  比较值
     * @return children
     */
    default Children notLike(Column column, String value) {
        return notLike(true, column, value);
    }

    /**
     * 区间查找
     *
     * @param column 字段
     * @param start  开始值
     * @param end    结束值
     * @return children
     */
    default Children between(Column column, Object start, Object end) {
        return between(true, column, start, end);
    }

    /**
     * 反向区间查找
     *
     * @param column 字段
     * @param start  开始值
     * @param end    结束值
     * @return children
     */
    default Children notBetween(Column column, Object start, Object end) {
        return notBetween(true, column, start, end);
    }

    /**
     * 为空
     *
     * @param column 字段
     * @return children
     */
    default Children isNull(Column column) {
        return isNull(true, column);
    }

    /**
     * 不为空
     *
     * @param column 字段
     * @return children
     */
    default Children isNotNull(Column column) {
        return isNotNull(true, column);
    }

    /**
     * in
     *
     * @param column 字段名称
     * @param values 对象列表
     * @return children
     */
    default Children in(Column column, Collection<?> values) {
        return in(true, column, values);
    }

    /**
     * notIn
     *
     * @param column 字段名称
     * @param values 对象列表
     * @return children
     */
    default Children notIn(Column column, Collection<?> values) {
        return notIn(true, column, values);
    }
}
