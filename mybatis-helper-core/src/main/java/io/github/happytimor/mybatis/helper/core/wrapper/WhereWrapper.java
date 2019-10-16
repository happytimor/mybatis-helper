package io.github.happytimor.mybatis.helper.core.wrapper;


import io.github.happytimor.mybatis.helper.core.common.Params;
import io.github.happytimor.mybatis.helper.core.metadata.ColumnFunction;
import io.github.happytimor.mybatis.helper.core.metadata.Condition;
import io.github.happytimor.mybatis.helper.core.metadata.DefaultCompare;
import io.github.happytimor.mybatis.helper.core.metadata.DefaultConnector;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/**
 * @author chenpeng
 */
public class WhereWrapper<T> extends OrderWrapper<T>
        implements DefaultCompare<WhereWrapper<T>, ColumnFunction<T, ?>>,
        DefaultConnector<T, WhereWrapper<T>> {


    public WhereWrapper() {
    }

    public WhereWrapper(Map<String, Object> paramNameValuePairs, AtomicInteger counter) {
        this.paramNameValuePairs = paramNameValuePairs;
        this.counter = counter;
    }


    @Override
    public WhereWrapper<T> or(boolean executeIf) {
        if (executeIf) {
            this.conditionList.add(new Condition("OR", ""));
        }
        return this;
    }

    @Override
    public WhereWrapper<T> and(boolean executeIf, Function<WhereWrapper<T>, WhereWrapper<T>> function) {
        if (executeIf) {
            WhereWrapper<T> apply = function.apply(new WhereWrapper<>(this.paramNameValuePairs, counter));
            String whereSegment = apply.getWhereSegment(false);
            this.conditionList.add(new Condition("AND", "(" + whereSegment + ")"));
        }
        return this;
    }


    @Override
    public WhereWrapper<T> gt(boolean executeIf, ColumnFunction<T, ?> column, Object value) {
        if (executeIf) {
            this.addCondition(column, ">", value);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> ge(boolean executeIf, ColumnFunction<T, ?> column, Object value) {
        if (executeIf) {
            this.addCondition(column, ">=", value);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> eq(boolean executeIf, ColumnFunction<T, ?> column, Object value) {
        if (executeIf) {
            this.addCondition(column, "=", value);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> le(boolean executeIf, ColumnFunction<T, ?> column, Object value) {
        if (executeIf) {
            this.addCondition(column, "<=", value);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> lt(boolean executeIf, ColumnFunction<T, ?> column, Object value) {
        if (executeIf) {
            this.addCondition(column, "<", value);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> ne(boolean executeIf, ColumnFunction<T, ?> column, Object value) {
        if (executeIf) {
            this.addCondition(column, "<>", value);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> like(boolean executeIf, ColumnFunction<T, ?> column, String value) {
        if (executeIf) {
            this.addCondition(column, "LIKE", "%" + value + "%");
        }
        return this;
    }

    @Override
    public WhereWrapper<T> likeLeft(boolean executeIf, ColumnFunction<T, ?> column, String value) {
        if (executeIf) {
            this.addCondition(column, "LIKE", "%" + value);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> likeRight(boolean executeIf, ColumnFunction<T, ?> column, String value) {
        if (executeIf) {
            this.addCondition(column, "LIKE", value + "%");
        }
        return this;
    }

    @Override
    public WhereWrapper<T> notLike(boolean executeIf, ColumnFunction<T, ?> column, String value) {
        if (executeIf) {
            this.addCondition(column, "LIKE", "%" + value + "%");
        }
        return this;
    }

    @Override
    public WhereWrapper<T> between(boolean executeIf, ColumnFunction<T, ?> column, Object start, Object end) {
        if (executeIf) {
            this.addCondition(column, "BETWEEN", start, "AND", end);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> notBetween(boolean executeIf, ColumnFunction<T, ?> column, Object start, Object end) {
        if (executeIf) {
            this.addCondition(column, "NOT BETWEEN", start, "AND", end);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> isNull(boolean executeIf, ColumnFunction<T, ?> column) {
        if (executeIf) {
            conditionList.add(new Condition(this.getColumnName(column) + " IS NULL"));
        }
        return this;
    }

    @Override
    public WhereWrapper<T> isNotNull(boolean executeIf, ColumnFunction<T, ?> column) {
        if (executeIf) {
            conditionList.add(new Condition(this.getColumnName(column) + " IS NOT NULL"));
        }
        return this;
    }

    @Override
    public WhereWrapper<T> in(boolean executeIf, ColumnFunction<T, ?> column, Collection<?> values) {
        if (executeIf) {
            this.inExpression(column, "IN", values);
        }
        return this;
    }

    @Override
    public WhereWrapper<T> in(boolean executeIf, ColumnFunction<T, ?> column, Function<WhereWrapper<?>, AbstractWrapper<?>> function) {
        if (executeIf) {
            AbstractWrapper<?> selectWrapper = function.apply(this);
            this.inExpression(column, "IN", selectWrapper.getTableName(), selectWrapper.getSelectSegment(), selectWrapper.getWhereSegment());
        }
        return this;
    }

    @Override
    public WhereWrapper<T> notIn(boolean executeIf, ColumnFunction<T, ?> column, Function<WhereWrapper<?>, AbstractWrapper<?>> function) {
        if (executeIf) {
            AbstractWrapper<?> selectWrapper = function.apply(this);
            this.inExpression(column, "NOT IN", selectWrapper.getTableName(), selectWrapper.getSelectSegment(), selectWrapper.getWhereSegment());
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
    public WhereWrapper<T> notIn(boolean executeIf, ColumnFunction<T, ?> column, Collection<?> values) {
        if (executeIf) {
            this.inExpression(column, "NOT IN", values);
        }
        return this;
    }

    /**
     * in 和not in查询
     */
    private void inExpression(ColumnFunction<T, ?> column, String operator, Collection<?> values) {
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

    private void inExpression(ColumnFunction<T, ?> column, String operator, String innerTableName, String innerColumn, String innerWhereSql) {
        String columnName = this.getColumnName(column, true);
        conditionList.add(new Condition(columnName + " " + operator + " (SELECT " + innerColumn + " FROM `" + innerTableName + "` " + innerWhereSql + ")"));
    }

    private void addCondition(ColumnFunction<T, ?> column, String operator, Object value) {
        String columnName = this.getColumnName(column, false);
        int count = counter.incrementAndGet();
        String key = "params_" + count + "_" + columnName;
        paramNameValuePairs.put(key, value);
        conditionList.add(new Condition(wrapColumnName(columnName) + " " + operator + " #{" + Params.WRAPPER + ".paramNameValuePairs." + key + "}"));
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



