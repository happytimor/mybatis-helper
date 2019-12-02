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
    private List<Order> orderList = new ArrayList<>();


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
     * @param executeIf 是否执行
     * @param column    字段名称
     * @return children
     */
    public OrderWrapper<T> orderByAsc(boolean executeIf, ColumnFunction<T, ?> column) {
        return this.orderByAsc(executeIf, column, true);
    }

    /**
     * 升序
     *
     * @param executeIf 是否执行
     * @param column    字段名称
     * @return children
     */
    public OrderWrapper<T> orderByAsc(boolean executeIf, ColumnFunction<T, ?> column, boolean asc) {
        if (executeIf) {
            this.orderList.add(new Order(this.getColumnName(column), asc ? "ASC" : "DESC"));
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
     * @param executeIf 是否执行
     * @param column    字段名称
     * @return children
     */
    public OrderWrapper<T> orderByDesc(boolean executeIf, ColumnFunction<T, ?> column) {
        if (executeIf) {
            this.orderList.add(new Order(this.getColumnName(column), "DESC"));
        }
        return this;
    }

    /**
     * 降序
     *
     * @param executeIf 是否执行
     * @param column    字段名称
     * @return children
     */
    public OrderWrapper<T> orderByDesc(boolean executeIf, ColumnFunction<T, ?> column, boolean desc) {
        if (executeIf) {
            this.orderList.add(new Order(this.getColumnName(column), desc ? "DESC" : "ASC"));
        }
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

    class Order {
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
