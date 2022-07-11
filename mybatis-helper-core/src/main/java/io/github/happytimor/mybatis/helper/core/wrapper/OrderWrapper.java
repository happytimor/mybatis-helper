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
     * asc order
     *
     * @param column column name
     * @param <E>    E
     * @return children
     */
    public <E> OrderWrapper<T> orderByAsc(ColumnFunction<E, ?> column) {
        return orderByAsc(true, column);
    }

    /**
     * asc order
     *
     * @param execute execute method if `execute` set true
     * @param column  column name
     * @param <E>     E
     * @return children
     */
    public <E> OrderWrapper<T> orderByAsc(boolean execute, ColumnFunction<E, ?> column) {
        return this.orderByAsc(execute, column, true);
    }

    /**
     * asc order
     *
     * @param execute execute method if `execute` set true
     * @param column  column name
     * @param asc     asc if true
     * @param <E>     E
     * @return children
     */
    public <E> OrderWrapper<T> orderByAsc(boolean execute, ColumnFunction<E, ?> column, Boolean asc) {
        if (execute) {
            this.orderList.add(new Order(column, asc != null && asc ? "ASC" : "DESC"));
        }
        return this;
    }

    /**
     * desc order
     *
     * @param column column name
     * @param <E>    E
     * @return children
     */
    public <E> OrderWrapper<T> orderByDesc(ColumnFunction<E, ?> column) {
        return orderByDesc(true, column);
    }

    /**
     * desc order
     *
     * @param execute execute method if `execute` set true
     * @param column  column name
     * @param <E>     E
     * @return children
     */
    public <E> OrderWrapper<T> orderByDesc(boolean execute, ColumnFunction<E, ?> column) {
        if (execute) {
            this.orderList.add(new Order(column, "DESC"));
        }
        return this;
    }

    /**
     * desc order
     *
     * @param execute execute method if `execute` set true
     * @param column  column name
     * @param desc    desc if true
     * @return children
     */
    public OrderWrapper<T> orderByDesc(boolean execute, ColumnFunction<T, ?> column, Boolean desc) {
        if (execute) {
            this.orderList.add(new Order(column, desc != null && desc ? "DESC" : "ASC"));
        }
        return this;
    }

    /**
     * desc order
     *
     * @param execute execute method if `execute` set true
     * @return children
     */
    public OrderWrapper<T> orderByRandom(boolean execute) {
        if (execute) {
            return this.orderByRandom();
        }
        return this;
    }

    /**
     * random order
     *
     * @return children
     */
    public OrderWrapper<T> orderByRandom() {
        this.orderList.add(new Order("RAND()"));
        return this;
    }


    static class Order {
        /**
         * column name
         */
        private ColumnFunction<?, ?> column;

        private String columnName;
        /**
         * ASC/DESC
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
