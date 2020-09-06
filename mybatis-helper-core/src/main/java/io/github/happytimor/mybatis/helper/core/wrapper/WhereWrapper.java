package io.github.happytimor.mybatis.helper.core.wrapper;


import io.github.happytimor.mybatis.helper.core.common.Params;
import io.github.happytimor.mybatis.helper.core.metadata.ColumnFunction;
import io.github.happytimor.mybatis.helper.core.metadata.Condition;
import io.github.happytimor.mybatis.helper.core.metadata.DefaultCompare;
import io.github.happytimor.mybatis.helper.core.metadata.DefaultConnector;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/**
 * @author chenpeng
 */
public class WhereWrapper<T> extends GroupWrapper<T>
        implements DefaultCompare<WhereWrapper<T>, ColumnFunction<T, ?>>,
        DefaultConnector<T, WhereWrapper<T>> {


    public WhereWrapper() {
    }

    public WhereWrapper(Map<String, Object> paramNameValuePairs, AtomicInteger counter) {
        this.paramNameValuePairs = paramNameValuePairs;
        this.counter = counter;
    }


    @Override
    public WhereWrapper<T> or(boolean execute) {
        if (execute) {
            this.conditionList.add(new Condition("OR", ""));
        }
        return this;
    }

    @Override
    public WhereWrapper<T> and(boolean execute, Function<WhereWrapper<T>, WhereWrapper<T>> function) {
        if (execute) {
            WhereWrapper<T> apply = function.apply(new WhereWrapper<>(this.paramNameValuePairs, counter));
            String whereSegment = apply.getWhereSegment(false);
            this.conditionList.add(new Condition("AND", "(" + whereSegment + ")"));
        }
        return this;
    }

    @Override
    public WhereWrapper<T> gt(boolean execute, ColumnFunction<T, ?> column, Number value) {
        if (execute) {
            this.addCondition(column, ">", value);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> gt(boolean execute, ColumnFunction<T, ?> column, String value) {
        if (execute) {
            this.addCondition(column, ">", value);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> gt(boolean execute, ColumnFunction<T, ?> column, Date value) {
        if (execute) {
            this.addCondition(column, ">", value);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> gt(boolean execute, ColumnFunction<T, ?> column, LocalDate value) {
        if (execute) {
            this.addCondition(column, ">", value);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> gt(boolean execute, ColumnFunction<T, ?> column, LocalDateTime value) {
        if (execute) {
            this.addCondition(column, ">", value);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> gt(boolean execute, ColumnFunction<T, ?> column, ColumnFunction<T, ?> value) {
        if (execute) {
            this.addCondition(column, ">", value);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> gt(boolean execute, ColumnFunction<T, ?> column, Function<WhereWrapper<?>, AbstractWrapper<?>> function) {
        if (execute) {
            AbstractWrapper<?> selectWrapper = function.apply(this);
            this.nestedExpression(column, ">", selectWrapper);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> ge(boolean execute, ColumnFunction<T, ?> column, Number value) {
        if (execute) {
            this.addCondition(column, ">=", value);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> ge(boolean execute, ColumnFunction<T, ?> column, String value) {
        if (execute) {
            this.addCondition(column, ">=", value);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> ge(boolean execute, ColumnFunction<T, ?> column, Date value) {
        if (execute) {
            this.addCondition(column, ">=", value);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> ge(boolean execute, ColumnFunction<T, ?> column, LocalDate value) {
        if (execute) {
            this.addCondition(column, ">=", value);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> ge(boolean execute, ColumnFunction<T, ?> column, LocalDateTime value) {
        if (execute) {
            this.addCondition(column, ">=", value);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> ge(boolean execute, ColumnFunction<T, ?> column, ColumnFunction<T, ?> value) {
        if (execute) {
            this.addCondition(column, ">=", value);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> ge(boolean execute, ColumnFunction<T, ?> column, Function<WhereWrapper<?>, AbstractWrapper<?>> function) {
        if (execute) {
            AbstractWrapper<?> selectWrapper = function.apply(this);
            this.nestedExpression(column, ">=", selectWrapper);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> eq(boolean execute, ColumnFunction<T, ?> column, Number value) {
        if (execute) {
            this.addCondition(column, "=", value);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> eq(boolean execute, ColumnFunction<T, ?> column, Boolean value) {
        if (execute) {
            this.addCondition(column, "=", value);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> eq(boolean execute, ColumnFunction<T, ?> column, String value) {
        if (execute) {
            this.addCondition(column, "=", value);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> eq(boolean execute, ColumnFunction<T, ?> column, Date value) {
        if (execute) {
            this.addCondition(column, "=", value);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> eq(boolean execute, ColumnFunction<T, ?> column, LocalDate value) {
        if (execute) {
            this.addCondition(column, "=", value);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> eq(boolean execute, ColumnFunction<T, ?> column, LocalDateTime value) {
        if (execute) {
            this.addCondition(column, "=", value);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> eq(boolean execute, ColumnFunction<T, ?> column, ColumnFunction<T, ?> value) {
        if (execute) {
            this.addCondition(column, "=", value);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> eqNested(boolean execute, ColumnFunction<T, ?> column, Function<WhereWrapper<?>, AbstractWrapper<?>> function) {
        if (execute) {
            AbstractWrapper<?> selectWrapper = function.apply(this);
            this.nestedExpression(column, "=", selectWrapper);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> le(boolean execute, ColumnFunction<T, ?> column, Number value) {
        if (execute) {
            this.addCondition(column, "<=", value);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> le(boolean execute, ColumnFunction<T, ?> column, String value) {
        if (execute) {
            this.addCondition(column, "<=", value);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> le(boolean execute, ColumnFunction<T, ?> column, Date value) {
        if (execute) {
            this.addCondition(column, "<=", value);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> le(boolean execute, ColumnFunction<T, ?> column, LocalDate value) {
        if (execute) {
            this.addCondition(column, "<=", value);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> le(boolean execute, ColumnFunction<T, ?> column, LocalDateTime value) {
        if (execute) {
            this.addCondition(column, "<=", value);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> le(boolean execute, ColumnFunction<T, ?> column, ColumnFunction<T, ?> value) {
        if (execute) {
            this.addCondition(column, "<=", value);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> le(boolean execute, ColumnFunction<T, ?> column, Function<WhereWrapper<?>, AbstractWrapper<?>> function) {
        if (execute) {
            AbstractWrapper<?> selectWrapper = function.apply(this);
            this.nestedExpression(column, "<=", selectWrapper);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> lt(boolean execute, ColumnFunction<T, ?> column, Number value) {
        if (execute) {
            this.addCondition(column, "<", value);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> lt(boolean execute, ColumnFunction<T, ?> column, String value) {
        if (execute) {
            this.addCondition(column, "<", value);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> lt(boolean execute, ColumnFunction<T, ?> column, Date value) {
        if (execute) {
            this.addCondition(column, "<", value);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> lt(boolean execute, ColumnFunction<T, ?> column, LocalDate value) {
        if (execute) {
            this.addCondition(column, "<", value);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> lt(boolean execute, ColumnFunction<T, ?> column, LocalDateTime value) {
        if (execute) {
            this.addCondition(column, "<", value);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> lt(boolean execute, ColumnFunction<T, ?> column, ColumnFunction<T, ?> value) {
        if (execute) {
            this.addCondition(column, "<", value);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> lt(boolean execute, ColumnFunction<T, ?> column, Function<WhereWrapper<?>, AbstractWrapper<?>> function) {
        if (execute) {
            AbstractWrapper<?> selectWrapper = function.apply(this);
            this.nestedExpression(column, "<", selectWrapper);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> ne(boolean execute, ColumnFunction<T, ?> column, Number value) {
        if (execute) {
            this.addCondition(column, "<>", value);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> ne(boolean execute, ColumnFunction<T, ?> column, String value) {
        if (execute) {
            this.addCondition(column, "<>", value);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> ne(boolean execute, ColumnFunction<T, ?> column, Date value) {
        if (execute) {
            this.addCondition(column, "<>", value);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> ne(boolean execute, ColumnFunction<T, ?> column, LocalDate value) {
        if (execute) {
            this.addCondition(column, "<>", value);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> ne(boolean execute, ColumnFunction<T, ?> column, LocalDateTime value) {
        if (execute) {
            this.addCondition(column, "<>", value);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> ne(boolean execute, ColumnFunction<T, ?> column, ColumnFunction<T, ?> value) {
        if (execute) {
            this.addCondition(column, "<>", value);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> ne(boolean execute, ColumnFunction<T, ?> column, Function<WhereWrapper<?>, AbstractWrapper<?>> function) {
        if (execute) {
            AbstractWrapper<?> selectWrapper = function.apply(this);
            this.nestedExpression(column, "<>", selectWrapper);
        }
        return this;
    }


    @Override
    public WhereWrapper<T> like(boolean execute, ColumnFunction<T, ?> column, String value) {
        if (execute) {
            this.addCondition(column, "LIKE", "%" + value + "%");
        }
        return this;
    }

    @Override
    public WhereWrapper<T> likeLeft(boolean execute, ColumnFunction<T, ?> column, String value) {
        if (execute) {
            this.addCondition(column, "LIKE", value + "%");
        }
        return this;
    }

    @Override
    public WhereWrapper<T> likeRight(boolean execute, ColumnFunction<T, ?> column, String value) {
        if (execute) {
            this.addCondition(column, "LIKE", "%" + value);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> notLike(boolean execute, ColumnFunction<T, ?> column, String value) {
        if (execute) {
            this.addCondition(column, "NOT LIKE", "%" + value + "%");
        }
        return this;
    }

    @Override
    public WhereWrapper<T> between(boolean execute, ColumnFunction<T, ?> column, Number start, Number end) {
        if (execute) {
            this.addCondition(column, "BETWEEN", start, "AND", end);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> notBetween(boolean execute, ColumnFunction<T, ?> column, Number start, Number end) {
        if (execute) {
            this.addCondition(column, "NOT BETWEEN", start, "AND", end);
        }
        return this;
    }


    @Override
    public WhereWrapper<T> isNull(boolean execute, ColumnFunction<T, ?> column) {
        if (execute) {
            conditionList.add(new Condition(this.getColumnName(column) + " IS NULL"));
        }
        return this;
    }

    @Override
    public WhereWrapper<T> isNotNull(boolean execute, ColumnFunction<T, ?> column) {
        if (execute) {
            conditionList.add(new Condition(this.getColumnName(column) + " IS NOT NULL"));
        }
        return this;
    }

    @Override
    public WhereWrapper<T> isEmpty(boolean execute, ColumnFunction<T, ?> column) {
        if (execute) {
            conditionList.add(new Condition(this.getColumnName(column) + " = ''"));
        }
        return this;
    }

    @Override
    public WhereWrapper<T> isNotEmpty(boolean execute, ColumnFunction<T, ?> column) {
        if (execute) {
            conditionList.add(new Condition(this.getColumnName(column) + " != ''"));
        }
        return this;
    }

    @Override
    public WhereWrapper<T> in(boolean execute, ColumnFunction<T, ?> column, Collection<?> values) {
        if (execute) {
            this.nestedExpression(column, "IN", values);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> in(boolean execute, ColumnFunction<T, ?> column, Function<WhereWrapper<?>, AbstractWrapper<?>> function) {
        if (execute) {
            AbstractWrapper<?> selectWrapper = function.apply(this);
            this.nestedExpression(column, "IN", selectWrapper);
        }
        return this;
    }


    /**
     * 生成嵌套子查询select
     *
     * @param modelClass model
     * @param <MODEL>    model
     * @return 子查询
     */
    public <MODEL> SelectWrapper<MODEL> applySelectWrapper(Class<MODEL> modelClass) {
        return new SelectWrapper<>(modelClass, this.paramNameValuePairs, this.counter);
    }

    @Override
    public WhereWrapper<T> notIn(boolean execute, ColumnFunction<T, ?> column, Collection<?> values) {
        if (execute) {
            this.nestedExpression(column, "NOT IN", values);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> notIn(boolean execute, ColumnFunction<T, ?> column, Function<WhereWrapper<?>, AbstractWrapper<?>> function) {
        if (execute) {
            AbstractWrapper<?> selectWrapper = function.apply(this);
            this.nestedExpression(column, "NOT IN", selectWrapper);
        }
        return this;
    }

    /**
     * in 和not in查询
     */
    private void nestedExpression(ColumnFunction<T, ?> column, String operator, Collection<?> values) {
        String columnName = this.getColumnName(column, false);

        //如果只有一个元素,退化成 = 或者 !=
        if (values.size() == 1) {
            this.addCondition(column, "IN".equals(operator) ? "=" : "!=", values.iterator().next());
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Object value : values) {
            String key = "params_" + counter.incrementAndGet() + "_" + columnName;
            stringBuilder.append("#{" + Params.WRAPPER + ".paramNameValuePairs.").append(key).append("}").append(",");
            paramNameValuePairs.put(key, value);
        }

        if (stringBuilder.length() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        conditionList.add(new Condition(wrapColumnName(columnName) + " " + operator + " (" + stringBuilder.toString() + ")"));
    }

    private void nestedExpression(ColumnFunction<T, ?> column, String operator, AbstractWrapper<?> selectWrapper) {
        String columnName = this.getColumnName(column, true);
        String segment = String.format("%s %s (SELECT %s FROM `%s` %s %s %s)", columnName, operator,
                selectWrapper.getSelectSegment(),
                selectWrapper.getTableName(),
                selectWrapper.getWhereSegment(),
                selectWrapper.getOrderSegment(),
                selectWrapper.getLimitSegment());
        conditionList.add(new Condition(segment));
    }

    private void addCondition(ColumnFunction<T, ?> column, String operator, Object value) {
        String columnName = this.getColumnName(column, false);
        int count = counter.incrementAndGet();
        String key = "params_" + count + "_" + columnName;
        paramNameValuePairs.put(key, value);
        conditionList.add(new Condition(wrapColumnName(columnName) + " " + operator + " #{" + Params.WRAPPER + ".paramNameValuePairs." + key + "}"));
    }

    private void addCondition(ColumnFunction<T, ?> column, String operator, ColumnFunction<T, ?> value) {
        conditionList.add(new Condition(this.getColumnName(column, true) + " " + operator + " " + this.getColumnName(value, true)));
    }

    private void addCondition(ColumnFunction<T, ?> column, String operator, Object start, String connector, Object end) {
        String columnName = this.getColumnName(column, false);
        String startKey = "params_start_" + columnName;
        paramNameValuePairs.put(startKey, start);

        String endKey = "params_end_" + columnName;
        paramNameValuePairs.put(endKey, end);

        conditionList.add(new Condition(wrapColumnName(columnName) + " " + operator + " #{" + Params.WRAPPER + ".paramNameValuePairs." + startKey + "} " + connector + " #{" + Params.WRAPPER + ".paramNameValuePairs." + endKey + "}"));
    }
}



