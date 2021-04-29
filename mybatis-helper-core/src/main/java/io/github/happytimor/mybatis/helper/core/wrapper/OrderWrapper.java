package io.github.happytimor.mybatis.helper.core.wrapper;

import io.github.happytimor.mybatis.helper.core.metadata.ColumnFunction;
import io.github.happytimor.mybatis.helper.core.util.LambdaUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenpeng
 */
public class OrderWrapper<T> extends LimitWrapper<T> {


    /**
     * 升序
     *
     * @param column 字段名称
     * @return children
     */
    public <E> OrderWrapper<T> orderByAsc(ColumnFunction<E, ?> column) {
        return orderByAsc(true, column);
    }

    /**
     * 升序
     *
     * @param execute 是否执行
     * @param column  字段名称
     * @return children
     */
    public <E> OrderWrapper<T> orderByAsc(boolean execute, ColumnFunction<E, ?> column) {
        return this.orderByAsc(execute, column, true);
    }

    /**
     * 升序
     *
     * @param execute 是否执行
     * @param column  字段名称
     * @param asc     是否升序 true-升序 false-降序
     * @return children
     */
    public <E> OrderWrapper<T> orderByAsc(boolean execute, ColumnFunction<E, ?> column, Boolean asc) {
        if (execute) {
            Class<?> clazz = LambdaUtils.resolve(column).getInstantiatedType();
            this.orderList.add(new Order(clazz, this.getColumnName(column), asc != null && asc ? "ASC" : "DESC"));
        }
        return this;
    }

    /**
     * 降序
     *
     * @param column 字段名称
     * @return children
     */
    public <E> OrderWrapper<T> orderByDesc(ColumnFunction<E, ?> column) {
        return orderByDesc(true, column);
    }

    /**
     * 降序
     *
     * @param execute 是否执行
     * @param column  字段名称
     * @return children
     */
    public <E> OrderWrapper<T> orderByDesc(boolean execute, ColumnFunction<E, ?> column) {
        if (execute) {
            Class<?> clazz = LambdaUtils.resolve(column).getInstantiatedType();
            this.orderList.add(new Order(clazz, this.getColumnName(column), "DESC"));
        }
        return this;
    }

    /**
     * 降序
     *
     * @param execute 是否执行
     * @param column  字段名称
     * @param desc    是否降序 true-降序 false-升序
     * @return children
     */
    public OrderWrapper<T> orderByDesc(boolean execute, ColumnFunction<T, ?> column, Boolean desc) {
        if (execute) {
            Class<?> clazz = LambdaUtils.resolve(column).getInstantiatedType();
            this.orderList.add(new Order(clazz, this.getColumnName(column), desc != null && desc ? "DESC" : "ASC"));
        }
        return this;
    }

    /**
     * 降序
     *
     * @param execute 是否执行
     * @return children
     */
    public OrderWrapper<T> orderByRandom(boolean execute) {
        if (execute) {
            return this.orderByRandom();
        }
        return this;
    }

    /**
     * 随机排序
     *
     * @return children
     */
    public OrderWrapper<T> orderByRandom() {
        this.orderList.add(new Order("RAND()", ""));
        return this;
    }


    static class Order {
        private Class<?> clazz;
        /**
         * 字段名
         */
        private String column;
        /**
         * ASC/DESC 升序或降序
         */
        private String orderType;

        public Order(String column, String orderType) {
            this.column = column;
            this.orderType = orderType;
        }

        public Order(Class<?> clazz, String column, String orderType) {
            this.clazz = clazz;
            this.column = column;
            this.orderType = orderType;
        }

        public Class<?> getClazz() {
            return clazz;
        }

        public void setClazz(Class<?> clazz) {
            this.clazz = clazz;
        }

        public String getColumn() {
            return column;
        }

        public void setColumn(String column) {
            this.column = column;
        }

        public String getOrderType() {
            return orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }
    }
}
