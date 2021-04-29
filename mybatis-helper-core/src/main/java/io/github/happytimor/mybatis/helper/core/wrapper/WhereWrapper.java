package io.github.happytimor.mybatis.helper.core.wrapper;


import io.github.happytimor.mybatis.helper.core.common.Params;
import io.github.happytimor.mybatis.helper.core.metadata.ColumnFunction;
import io.github.happytimor.mybatis.helper.core.metadata.Condition;
import io.github.happytimor.mybatis.helper.core.metadata.DefaultCompare;
import io.github.happytimor.mybatis.helper.core.metadata.DefaultConnector;
import io.github.happytimor.mybatis.helper.core.util.LambdaUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/**
 * @author chenpeng
 */
public class WhereWrapper<T> extends GroupWrapper<T>
        implements DefaultCompare<WhereWrapper<T>>,
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
    public <R> WhereWrapper<T> gt(boolean execute, ColumnFunction<R, ?> column, Number value) {
        if (execute) {
            this.addCondition(column, ">", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> gtNotBlank(ColumnFunction<R, ?> column, Number value) {
        if (value != null) {
            this.addCondition(column, ">", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> gt(boolean execute, ColumnFunction<R, ?> column, String value) {
        if (execute) {
            this.addCondition(column, ">", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> gtNotBlank(ColumnFunction<R, ?> column, String value) {
        if (!StringUtils.isEmpty(value)) {
            this.addCondition(column, ">", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> gt(boolean execute, ColumnFunction<R, ?> column, Date value) {
        if (execute) {
            this.addCondition(column, ">", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> gtNotBlank(ColumnFunction<R, ?> column, Date value) {
        if (value != null) {
            this.addCondition(column, ">", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> gt(boolean execute, ColumnFunction<R, ?> column, LocalDate value) {
        if (execute) {
            this.addCondition(column, ">", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> gtNotBlank(ColumnFunction<R, ?> column, LocalDate value) {
        if (value != null) {
            this.addCondition(column, ">", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> gt(boolean execute, ColumnFunction<R, ?> column, LocalDateTime value) {
        if (execute) {
            this.addCondition(column, ">", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> gtNotBlank(ColumnFunction<R, ?> column, LocalDateTime value) {
        if (value != null) {
            this.addCondition(column, ">", value);
        }
        return this;
    }

    @Override
    public <R, V> WhereWrapper<T> gt(boolean execute, ColumnFunction<R, ?> column, ColumnFunction<V, ?> value) {
        if (execute) {
            this.addCondition(column, ">", value);
        }
        return this;
    }

    @Override
    public <R, V> WhereWrapper<T> gtNotBlank(ColumnFunction<R, ?> column, ColumnFunction<V, ?> value) {
        if (value != null) {
            this.addCondition(column, ">", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> gt(boolean execute, ColumnFunction<R, ?> column, Function<WhereWrapper<?>, AbstractWrapper<?>> function) {
        if (execute) {
            AbstractWrapper<?> selectWrapper = function.apply(this);
            this.nestedExpression(column, ">", selectWrapper);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> ge(boolean execute, ColumnFunction<R, ?> column, Number value) {
        if (execute) {
            this.addCondition(column, ">=", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> geNotBlank(ColumnFunction<R, ?> column, Number value) {
        if (value != null) {
            this.addCondition(column, ">=", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> ge(boolean execute, ColumnFunction<R, ?> column, String value) {
        if (execute) {
            this.addCondition(column, ">=", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> geNotBlank(ColumnFunction<R, ?> column, String value) {
        if (!StringUtils.isEmpty(value)) {
            this.addCondition(column, ">=", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> ge(boolean execute, ColumnFunction<R, ?> column, Date value) {
        if (execute) {
            this.addCondition(column, ">=", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> geNotBlank(ColumnFunction<R, ?> column, Date value) {
        if (value != null) {
            this.addCondition(column, ">=", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> ge(boolean execute, ColumnFunction<R, ?> column, LocalDate value) {
        if (execute) {
            this.addCondition(column, ">=", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> geNotBlank(ColumnFunction<R, ?> column, LocalDate value) {
        if (value != null) {
            this.addCondition(column, ">=", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> ge(boolean execute, ColumnFunction<R, ?> column, LocalDateTime value) {
        if (execute) {
            this.addCondition(column, ">=", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> geNotBlank(ColumnFunction<R, ?> column, LocalDateTime value) {
        if (value != null) {
            this.addCondition(column, ">=", value);
        }
        return this;
    }

    @Override
    public <R, V> WhereWrapper<T> ge(boolean execute, ColumnFunction<R, ?> column, ColumnFunction<V, ?> value) {
        if (execute) {
            this.addCondition(column, ">=", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> ge(boolean execute, ColumnFunction<R, ?> column, Function<WhereWrapper<?>, AbstractWrapper<?>> function) {
        if (execute) {
            AbstractWrapper<?> selectWrapper = function.apply(this);
            this.nestedExpression(column, ">=", selectWrapper);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> eq(boolean execute, ColumnFunction<R, ?> column, Number value) {
        if (execute) {
            this.addCondition(column, "=", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> eqNotBlank(ColumnFunction<R, ?> column, Number value) {
        if (value != null) {
            this.addCondition(column, "=", value);
        }
        return this;
    }

    @Override
    public <E> WhereWrapper<T> eq(boolean execute, ColumnFunction<E, ?> column, Boolean value) {
        if (execute) {
            this.addCondition(column, "=", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> eqNotBlank(ColumnFunction<R, ?> column, Boolean value) {
        if (value != null) {
            this.addCondition(column, "=", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> eq(boolean execute, ColumnFunction<R, ?> column, String value) {
        if (execute) {
            this.addCondition(column, "=", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> eqNotBlank(ColumnFunction<R, ?> column, String value) {
        if (!StringUtils.isEmpty(value)) {
            this.addCondition(column, "=", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> eq(boolean execute, ColumnFunction<R, ?> column, Date value) {
        if (execute) {
            this.addCondition(column, "=", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> eqNotBlank(ColumnFunction<R, ?> column, Date value) {
        if (value != null) {
            this.addCondition(column, "=", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> eq(boolean execute, ColumnFunction<R, ?> column, LocalDate value) {
        if (execute) {
            this.addCondition(column, "=", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> eqNotBlank(ColumnFunction<R, ?> column, LocalDate value) {
        if (value != null) {
            this.addCondition(column, "=", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> eq(boolean execute, ColumnFunction<R, ?> column, LocalDateTime value) {
        if (execute) {
            this.addCondition(column, "=", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> eqNotBlank(ColumnFunction<R, ?> column, LocalDateTime value) {
        if (value != null) {
            this.addCondition(column, "=", value);
        }
        return this;
    }

    @Override
    public <R, V> WhereWrapper<T> eq(boolean execute, ColumnFunction<R, ?> column, ColumnFunction<V, ?> value) {
        if (execute) {
            this.addCondition(column, "=", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> eqNested(boolean execute, ColumnFunction<R, ?> column, Function<WhereWrapper<?>, AbstractWrapper<?>> function) {
        if (execute) {
            AbstractWrapper<?> selectWrapper = function.apply(this);
            this.nestedExpression(column, "=", selectWrapper);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> le(boolean execute, ColumnFunction<R, ?> column, Number value) {
        if (execute) {
            this.addCondition(column, "<=", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> leNotBlank(ColumnFunction<R, ?> column, Number value) {
        if (value != null) {
            this.addCondition(column, "<=", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> le(boolean execute, ColumnFunction<R, ?> column, String value) {
        if (execute) {
            this.addCondition(column, "<=", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> leNotBlank(ColumnFunction<R, ?> column, String value) {
        if (!StringUtils.isEmpty(value)) {
            this.addCondition(column, "<=", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> le(boolean execute, ColumnFunction<R, ?> column, Date value) {
        if (execute) {
            this.addCondition(column, "<=", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> leNotBlank(ColumnFunction<R, ?> column, Date value) {
        if (value != null) {
            this.addCondition(column, "<=", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> le(boolean execute, ColumnFunction<R, ?> column, LocalDate value) {
        if (execute) {
            this.addCondition(column, "<=", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> leNotBlank(ColumnFunction<R, ?> column, LocalDate value) {
        if (value != null) {
            this.addCondition(column, "<=", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> le(boolean execute, ColumnFunction<R, ?> column, LocalDateTime value) {
        if (execute) {
            this.addCondition(column, "<=", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> leNotBlank(ColumnFunction<R, ?> column, LocalDateTime value) {
        if (value != null) {
            this.addCondition(column, "<=", value);
        }
        return this;
    }

    @Override
    public <R, V> WhereWrapper<T> le(boolean execute, ColumnFunction<R, ?> column, ColumnFunction<V, ?> value) {
        if (execute) {
            this.addCondition(column, "<=", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> le(boolean execute, ColumnFunction<R, ?> column, Function<WhereWrapper<?>, AbstractWrapper<?>> function) {
        if (execute) {
            AbstractWrapper<?> selectWrapper = function.apply(this);
            this.nestedExpression(column, "<=", selectWrapper);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> lt(boolean execute, ColumnFunction<R, ?> column, Number value) {
        if (execute) {
            this.addCondition(column, "<", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> ltNotBlank(ColumnFunction<R, ?> column, Number value) {
        if (value != null) {
            this.addCondition(column, "<", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> lt(boolean execute, ColumnFunction<R, ?> column, String value) {
        if (execute) {
            this.addCondition(column, "<", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> ltNotBlank(ColumnFunction<R, ?> column, String value) {
        if (!StringUtils.isEmpty(value)) {
            this.addCondition(column, "<", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> lt(boolean execute, ColumnFunction<R, ?> column, Date value) {
        if (execute) {
            this.addCondition(column, "<", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> ltNotBlank(ColumnFunction<R, ?> column, Date value) {
        if (value != null) {
            this.addCondition(column, "<", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> lt(boolean execute, ColumnFunction<R, ?> column, LocalDate value) {
        if (execute) {
            this.addCondition(column, "<", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> ltNotBlank(ColumnFunction<R, ?> column, LocalDate value) {
        if (value != null) {
            this.addCondition(column, "<", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> lt(boolean execute, ColumnFunction<R, ?> column, LocalDateTime value) {
        if (execute) {
            this.addCondition(column, "<", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> ltNotBlank(ColumnFunction<R, ?> column, LocalDateTime value) {
        if (value != null) {
            this.addCondition(column, "<", value);
        }
        return this;
    }

    @Override
    public <R, V> WhereWrapper<T> lt(boolean execute, ColumnFunction<R, ?> column, ColumnFunction<V, ?> value) {
        if (execute) {
            this.addCondition(column, "<", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> lt(boolean execute, ColumnFunction<R, ?> column, Function<WhereWrapper<?>, AbstractWrapper<?>> function) {
        if (execute) {
            AbstractWrapper<?> selectWrapper = function.apply(this);
            this.nestedExpression(column, "<", selectWrapper);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> ne(boolean execute, ColumnFunction<R, ?> column, Number value) {
        if (execute) {
            this.addCondition(column, "<>", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> neNotBlank(ColumnFunction<R, ?> column, Number value) {
        if (value != null) {
            this.addCondition(column, "<>", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> ne(boolean execute, ColumnFunction<R, ?> column, String value) {
        if (execute) {
            this.addCondition(column, "<>", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> neNotBlank(ColumnFunction<R, ?> column, String value) {
        if (!StringUtils.isEmpty(value)) {
            this.addCondition(column, "<>", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> ne(boolean execute, ColumnFunction<R, ?> column, Date value) {
        if (execute) {
            this.addCondition(column, "<>", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> neNotBlank(ColumnFunction<R, ?> column, Date value) {
        if (value != null) {
            this.addCondition(column, "<>", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> ne(boolean execute, ColumnFunction<R, ?> column, LocalDate value) {
        if (execute) {
            this.addCondition(column, "<>", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> neNotBlank(ColumnFunction<R, ?> column, LocalDate value) {
        if (value != null) {
            this.addCondition(column, "<>", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> ne(boolean execute, ColumnFunction<R, ?> column, LocalDateTime value) {
        if (execute) {
            this.addCondition(column, "<>", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> neNotBlank(ColumnFunction<R, ?> column, LocalDateTime value) {
        if (value != null) {
            this.addCondition(column, "<>", value);
        }
        return this;
    }

    @Override
    public <R, V> WhereWrapper<T> ne(boolean execute, ColumnFunction<R, ?> column, ColumnFunction<V, ?> value) {
        if (execute) {
            this.addCondition(column, "<>", value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> ne(boolean execute, ColumnFunction<R, ?> column, Function<WhereWrapper<?>, AbstractWrapper<?>> function) {
        if (execute) {
            AbstractWrapper<?> selectWrapper = function.apply(this);
            this.nestedExpression(column, "<>", selectWrapper);
        }
        return this;
    }


    @Override
    public <R> WhereWrapper<T> like(boolean execute, ColumnFunction<R, ?> column, String value) {
        if (execute) {
            this.addCondition(column, "LIKE", "%" + value + "%");
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> likeNotBlank(ColumnFunction<R, ?> column, String value) {
        if (!StringUtils.isEmpty(value)) {
            this.addCondition(column, "LIKE", "%" + value + "%");
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> likeLeft(boolean execute, ColumnFunction<R, ?> column, String value) {
        if (execute) {
            this.addCondition(column, "LIKE", value + "%");
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> likeLeftNotBlank(ColumnFunction<R, ?> column, String value) {
        if (!StringUtils.isEmpty(value)) {
            this.addCondition(column, "LIKE", value + "%");
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> likeRight(boolean execute, ColumnFunction<R, ?> column, String value) {
        if (execute) {
            this.addCondition(column, "LIKE", "%" + value);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> notLike(boolean execute, ColumnFunction<R, ?> column, String value) {
        if (execute) {
            this.addCondition(column, "NOT LIKE", "%" + value + "%");
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> between(boolean execute, ColumnFunction<R, ?> column, Number start, Number end) {
        if (execute) {
            this.addCondition(column, "BETWEEN", start, "AND", end);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> notBetween(boolean execute, ColumnFunction<R, ?> column, Number start, Number end) {
        if (execute) {
            this.addCondition(column, "NOT BETWEEN", start, "AND", end);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> between(boolean execute, ColumnFunction<R, ?> column, Date start, Date end) {
        if (execute) {
            this.addCondition(column, "BETWEEN", start, "AND", end);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> notBetween(boolean execute, ColumnFunction<R, ?> column, Date start, Date end) {
        if (execute) {
            this.addCondition(column, "NOT BETWEEN", start, "AND", end);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> between(boolean execute, ColumnFunction<R, ?> column, LocalDate start, LocalDate end) {
        if (execute) {
            this.addCondition(column, "BETWEEN", start, "AND", end);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> notBetween(boolean execute, ColumnFunction<R, ?> column, LocalDate start, LocalDate end) {
        if (execute) {
            this.addCondition(column, "NOT BETWEEN", start, "AND", end);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> between(boolean execute, ColumnFunction<R, ?> column, LocalDateTime start, LocalDateTime end) {
        if (execute) {
            this.addCondition(column, "BETWEEN", start, "AND", end);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> notBetween(boolean execute, ColumnFunction<R, ?> column, LocalDateTime start, LocalDateTime end) {
        if (execute) {
            this.addCondition(column, "NOT BETWEEN", start, "AND", end);
        }
        return this;
    }


    @Override
    public <R> WhereWrapper<T> isNull(boolean execute, ColumnFunction<R, ?> column) {
        if (execute) {
            conditionList.add(new Condition(this.getColumnName(column) + " IS NULL"));
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> isNotNull(boolean execute, ColumnFunction<R, ?> column) {
        if (execute) {
            conditionList.add(new Condition(this.getColumnName(column) + " IS NOT NULL"));
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> isEmpty(boolean execute, ColumnFunction<R, ?> column) {
        if (execute) {
            conditionList.add(new Condition(this.getColumnName(column) + " = ''"));
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> isNotEmpty(boolean execute, ColumnFunction<R, ?> column) {
        if (execute) {
            conditionList.add(new Condition(this.getColumnName(column) + " != ''"));
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> in(boolean execute, ColumnFunction<R, ?> column, Collection<?> values) {
        if (execute) {
            this.nestedExpression(column, "IN", values);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> inNotBlank(ColumnFunction<R, ?> column, Collection<?> values) {
        if (values != null && values.size() > 0) {
            this.nestedExpression(column, "IN", values);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> in(boolean execute, ColumnFunction<R, ?> column, Function<WhereWrapper<?>, AbstractWrapper<?>> function) {
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
    public <R> WhereWrapper<T> notIn(boolean execute, ColumnFunction<R, ?> column, Collection<?> values) {
        if (execute) {
            this.nestedExpression(column, "NOT IN", values);
        }
        return this;
    }

    @Override
    public <R> WhereWrapper<T> notIn(boolean execute, ColumnFunction<R, ?> column, Function<WhereWrapper<?>, AbstractWrapper<?>> function) {
        if (execute) {
            AbstractWrapper<?> selectWrapper = function.apply(this);
            this.nestedExpression(column, "NOT IN", selectWrapper);
        }
        return this;
    }

    /**
     * in 和not in查询
     */
    private <R> void nestedExpression(ColumnFunction<R, ?> column, String operator, Collection<?> values) {
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

    private <R> void nestedExpression(ColumnFunction<R, ?> column, String operator, AbstractWrapper<?> selectWrapper) {
        String columnName = this.getColumnName(column, true);
        String segment = String.format("%s %s (SELECT %s FROM `%s` %s %s %s)", columnName, operator,
                selectWrapper.getSelectSegment(),
                selectWrapper.getTableName(),
                selectWrapper.getWhereSegment(),
                selectWrapper.getOrderSegment(),
                selectWrapper.getLimitSegment());
        conditionList.add(new Condition(segment));
    }

    private <E> void addCondition(ColumnFunction<E, ?> column, String operator, Object value) {
        String columnName = this.getColumnName(column, false);
        String tableAlias = super.getTableAlias(LambdaUtils.resolve(column).getInstantiatedType(), true);
        int count = counter.incrementAndGet();
        String key = "params_" + count + "_" + columnName;
        paramNameValuePairs.put(key, value);
        conditionList.add(new Condition(tableAlias + wrapColumnName(columnName) + " " + operator + " #{" + Params.WRAPPER + ".paramNameValuePairs." + key + "}"));
    }

    private <R> void addCondition(ColumnFunction<R, ?> column, String operator, ColumnFunction<T, ?> value) {
        conditionList.add(new Condition(this.getColumnName(column, true) + " " + operator + " " + this.getColumnName(value, true)));
    }

    private <R> void addCondition(ColumnFunction<R, ?> column, String operator, Object start, String connector, Object end) {
        String columnName = this.getColumnName(column, false);
        int count = counter.incrementAndGet();

        String startKey = "params_start_" + count + "_" + columnName;
        paramNameValuePairs.put(startKey, start);

        String endKey = "params_end_" + count + "_" + columnName;
        paramNameValuePairs.put(endKey, end);

        conditionList.add(new Condition(wrapColumnName(columnName) + " " + operator + " #{" + Params.WRAPPER + ".paramNameValuePairs." + startKey + "} " + connector + " #{" + Params.WRAPPER + ".paramNameValuePairs." + endKey + "}"));
    }
}



