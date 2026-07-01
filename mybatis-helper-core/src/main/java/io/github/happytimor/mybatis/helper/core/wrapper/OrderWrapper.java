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
     * merge order conditions from another wrapper
     *
     * @param orderWrapper order wrapper
     * @return children
     */
    public OrderWrapper<T> orderMerge(OrderWrapper<?> orderWrapper) {
        if (orderWrapper != null) {
            this.orderList.addAll(orderWrapper.orderList);
        }
        return this;
    }

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
     * @param columnName column name
     * @return children
     */
    public OrderWrapper<T> orderByAsc(String columnName) {
        return orderByAsc(true, columnName);
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
     * @param execute    execute method if `execute` set true
     * @param columnName column name
     * @return children
     */
    public OrderWrapper<T> orderByAsc(boolean execute, String columnName) {
        return this.orderByAsc(execute, columnName, true);
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
     * asc order
     *
     * @param execute    execute method if `execute` set true
     * @param columnName column name
     * @param asc        asc if true
     * @return children
     */
    public OrderWrapper<T> orderByAsc(boolean execute, String columnName, Boolean asc) {
        if (execute) {
            this.orderList.add(new Order(columnName, asc != null && asc ? "ASC" : "DESC"));
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
     * @param columnName column name
     * @return children
     */
    public OrderWrapper<T> orderByDesc(String columnName) {
        return orderByDesc(true, columnName);
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
     * @param execute    execute method if `execute` set true
     * @param columnName column name
     * @return children
     */
    public OrderWrapper<T> orderByDesc(boolean execute, String columnName) {
        if (execute) {
            this.orderList.add(new Order(columnName, "DESC"));
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
     * @param execute    execute method if `execute` set true
     * @param columnName column name
     * @param desc       desc if true
     * @return children
     */
    public OrderWrapper<T> orderByDesc(boolean execute, String columnName, Boolean desc) {
        if (execute) {
            this.orderList.add(new Order(columnName, desc != null && desc ? "DESC" : "ASC"));
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

    /**
     * divide order
     *
     * @param numerator   numerator column
     * @param denominator denominator column
     * @param asc         asc if true
     * @param <N>         numerator model
     * @param <D>         denominator model
     * @return children
     */
    public <N, D> OrderWrapper<T> orderByDivide(ColumnFunction<N, ?> numerator, ColumnFunction<D, ?> denominator, Boolean asc) {
        return orderByDivide(true, numerator, denominator, asc);
    }

    /**
     * divide order
     *
     * @param numerator   numerator column
     * @param denominator denominator column
     * @param asc         asc if true
     * @return children
     */
    public OrderWrapper<T> orderByDivide(String numerator, String denominator, Boolean asc) {
        return orderByDivide(true, numerator, denominator, asc);
    }

    /**
     * divide order
     *
     * @param execute     execute method if `execute` set true
     * @param numerator   numerator column
     * @param denominator denominator column
     * @param asc         asc if true
     * @param <N>         numerator model
     * @param <D>         denominator model
     * @return children
     */
    public <N, D> OrderWrapper<T> orderByDivide(boolean execute, ColumnFunction<N, ?> numerator, ColumnFunction<D, ?> denominator, Boolean asc) {
        if (execute) {
            if (numerator == null) {
                throw new IllegalArgumentException("numerator column can not be null");
            }
            if (denominator == null) {
                throw new IllegalArgumentException("denominator column can not be null");
            }
            this.orderList.add(new Order(numerator, denominator, asc != null && asc ? "ASC" : "DESC"));
        }
        return this;
    }

    /**
     * divide order
     *
     * @param execute     execute method if `execute` set true
     * @param numerator   numerator column
     * @param denominator denominator column
     * @param asc         asc if true
     * @return children
     */
    public OrderWrapper<T> orderByDivide(boolean execute, String numerator, String denominator, Boolean asc) {
        if (execute) {
            if (numerator == null || "".equals(numerator)) {
                throw new IllegalArgumentException("numerator column can not be null");
            }
            if (denominator == null || "".equals(denominator)) {
                throw new IllegalArgumentException("denominator column can not be null");
            }
            this.orderList.add(new Order(numerator, denominator, asc != null && asc ? "ASC" : "DESC"));
        }
        return this;
    }


    static class Order {
        /**
         * column name
         */
        private ColumnFunction<?, ?> column;

        private ColumnFunction<?, ?> numerator;

        private ColumnFunction<?, ?> denominator;

        private String numeratorColumnName;

        private String denominatorColumnName;

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

        public Order(String columnName, String orderType) {
            this.columnName = columnName;
            this.orderType = orderType;
        }

        public Order(String numeratorColumnName, String denominatorColumnName, String orderType) {
            this.numeratorColumnName = numeratorColumnName;
            this.denominatorColumnName = denominatorColumnName;
            this.orderType = orderType;
        }

        public Order(ColumnFunction<?, ?> numerator, ColumnFunction<?, ?> denominator, String orderType) {
            this.numerator = numerator;
            this.denominator = denominator;
            this.orderType = orderType;
        }

        public ColumnFunction<?, ?> getColumn() {
            return column;
        }

        public void setColumn(ColumnFunction<?, ?> column) {
            this.column = column;
        }

        public ColumnFunction<?, ?> getNumerator() {
            return numerator;
        }

        public void setNumerator(ColumnFunction<?, ?> numerator) {
            this.numerator = numerator;
        }

        public ColumnFunction<?, ?> getDenominator() {
            return denominator;
        }

        public void setDenominator(ColumnFunction<?, ?> denominator) {
            this.denominator = denominator;
        }

        public String getNumeratorColumnName() {
            return numeratorColumnName;
        }

        public void setNumeratorColumnName(String numeratorColumnName) {
            this.numeratorColumnName = numeratorColumnName;
        }

        public String getDenominatorColumnName() {
            return denominatorColumnName;
        }

        public void setDenominatorColumnName(String denominatorColumnName) {
            this.denominatorColumnName = denominatorColumnName;
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
