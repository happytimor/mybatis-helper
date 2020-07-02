package io.github.happytimor.mybatis.helper.core.wrapper;

import io.github.happytimor.mybatis.helper.core.metadata.ColumnFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenpeng
 */
public class OrderWrapper<T> extends LimitWrapper<T> {

    /**
     * 排序字段以及排序方式
     */
    private final List<Order> orderList = new ArrayList<>();


    /**
     * 升序
     *
     * @param column 字段名称
     * @return children
     */
    public OrderWrapper<T> orderByAsc(ColumnFunction<T, ?> column) {
        return orderByAsc(true, column);
    }

    /**
     * 升序
     *
     * @param execute 是否执行
     * @param column    字段名称
     * @return children
     */
    public OrderWrapper<T> orderByAsc(boolean execute, ColumnFunction<T, ?> column) {
        return this.orderByAsc(execute, column, true);
    }

    /**
     * 升序
     *
     * @param execute 是否执行
     * @param column    字段名称
     * @param asc       是否升序 true-升序 false-降序
     * @return children
     */
    public OrderWrapper<T> orderByAsc(boolean execute, ColumnFunction<T, ?> column, Boolean asc) {
        if (execute) {
            this.orderList.add(new Order(this.getColumnName(column), asc != null && asc ? "ASC" : "DESC"));
        }
        return this;
    }

    /**
     * 降序
     *
     * @param column 字段名称
     * @return children
     */
    public OrderWrapper<T> orderByDesc(ColumnFunction<T, ?> column) {
        return orderByDesc(true, column);
    }

    /**
     * 降序
     *
     * @param execute 是否执行
     * @param column    字段名称
     * @return children
     */
    public OrderWrapper<T> orderByDesc(boolean execute, ColumnFunction<T, ?> column) {
        if (execute) {
            this.orderList.add(new Order(this.getColumnName(column), "DESC"));
        }
        return this;
    }

    /**
     * 降序
     *
     * @param execute 是否执行
     * @param column    字段名称
     * @param desc      是否降序 true-降序 false-升序
     * @return children
     */
    public OrderWrapper<T> orderByDesc(boolean execute, ColumnFunction<T, ?> column, Boolean desc) {
        if (execute) {
            this.orderList.add(new Order(this.getColumnName(column), desc != null && desc ? "DESC" : "ASC"));
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

    /**
     * 获取排序sql
     *
     * @return 排序sql
     */
    public String getOrderSegment() {
        if (orderList.isEmpty()) {
            return "";
        }
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append("ORDER BY ");
        for (int i = 0; i < orderList.size() - 1; i++) {
            Order order = orderList.get(i);
            stringBuffer.append(order.getColumn()).append(" ").append(order.getOrderType()).append(", ");
        }
        Order order = orderList.get(orderList.size() - 1);
        stringBuffer.append(order.getColumn()).append(" ").append(order.getOrderType());

        return stringBuffer.toString();
    }

    static class Order {
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
