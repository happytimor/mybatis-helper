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
            this.orderList.add(new Order(column, asc != null && asc ? "ASC" : "DESC"));
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
            this.orderList.add(new Order(column, "DESC"));
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
            this.orderList.add(new Order(column, desc != null && desc ? "DESC" : "ASC"));
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
        this.orderList.add(new Order("RAND()"));
        return this;
    }


    static class Order {
        /**
         * 字段名
         */
        private ColumnFunction<?, ?> column;

        private String columnName;
        /**
         * ASC/DESC 升序或降序
         */
        private String orderType;

        public Order(ColumnFunction<?, ?> column, String orderType) {
            this.column = column;
            this.orderType = orderType;
        }

        public Order(String columnName) {
            this.columnName = columnName;
            this.orderType = "";
        }

        public ColumnFunction<?, ?> getColumn() {
            return column;
        }

        public void setColumn(ColumnFunction<?, ?> column) {
            this.column = column;
        }

        public String getOrderType() {
            return orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public String getColumnName() {
            return columnName;
        }

        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }
    }
}
