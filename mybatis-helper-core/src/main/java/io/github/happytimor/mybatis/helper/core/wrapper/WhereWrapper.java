package io.github.happytimor.mybatis.helper.core.wrapper;


import io.github.happytimor.mybatis.helper.core.metadata.DefaultCompare;
import io.github.happytimor.mybatis.helper.core.metadata.ColumnFunction;
import io.github.happytimor.mybatis.helper.core.metadata.DefaultConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/**
 * @author chenpeng
 * @date 2019-08-29
 */
public class WhereWrapper<T> extends OrderWrapper<T>
        implements DefaultCompare<WhereWrapper<T>, ColumnFunction<T, ?>>,
        DefaultConnector<T, WhereWrapper<T>> {

    private final static Logger logger = LoggerFactory.getLogger(WhereWrapper.class);
    private List<Condition> conditionList = new ArrayList<>();

    private Map<String, Object> paramNameValuePairs = new HashMap<>();

    /**
     * 获取where片段
     *
     * @return where片段
     */
    public String getWhereSegment() {
        return getWhereSegment(true);
    }

    public String getWhereSegment(boolean hasKeyWord) {
        if (conditionList.isEmpty()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (hasKeyWord) {
            stringBuilder.append("WHERE ");
        }
        stringBuilder.append(conditionList.get(0).getSegment());
        boolean prevIsConnector = false;
        for (int i = 1; i < conditionList.size(); i++) {
            Condition cond = this.conditionList.get(i);
            if (prevIsConnector) {
                prevIsConnector = false;
                stringBuilder.append(cond.getSegment());
            } else {
                stringBuilder.append(" ").append(cond.getConnector()).append(" ").append(cond.getSegment());
            }
            if (Objects.equals(cond.getSegment(), "")) {
                prevIsConnector = true;
            }
        }
        return stringBuilder.toString();
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
            WhereWrapper<T> apply = function.apply(new WhereWrapper<T>());
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
            this.addCondition(column, "LIKE", value + "%");
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

    private AtomicInteger counter = new AtomicInteger(0);

    private void addCondition(ColumnFunction<T, ?> column, String operator, Object value) {
        String columnName = this.getColumnName(column, false);
        int count = counter.incrementAndGet();
        String key = "params_" + count + "_" + columnName;
        paramNameValuePairs.put(key, value);
        conditionList.add(new Condition(wrapColumnName(columnName) + " " + operator + " #{paramNameValuePairs." + key + "}"));
    }

    private void addCondition(ColumnFunction<T, ?> column, String operator, Object start, String connector, Object end) {
        String columnName = this.getColumnName(column, false);
        String startKey = "params_start_" + columnName;
        paramNameValuePairs.put(startKey, start);

        String endKey = "params_end_" + columnName;
        paramNameValuePairs.put(endKey, end);

        conditionList.add(new Condition(wrapColumnName(columnName) + " " + operator + " #{paramNameValuePairs." + startKey + "} " + connector + " #{paramNameValuePairs." + endKey + "}"));
    }

    public Map<String, Object> getParamNameValuePairs() {
        return paramNameValuePairs;
    }

    public void setParamNameValuePairs(Map<String, Object> paramNameValuePairs) {
        this.paramNameValuePairs = paramNameValuePairs;
    }

    class Condition {
        /**
         * 连接符
         */
        private String connector;
        /**
         * sql片段
         */
        private String segment;

        public Condition(String segment) {
            this.segment = segment;
            this.connector = "AND";
        }

        public Condition(String connector, String segment) {
            this.segment = segment;
            this.connector = connector;
        }

        public String getConnector() {
            return connector;
        }

        public void setConnector(String connector) {
            this.connector = connector;
        }

        public String getSegment() {
            return segment;
        }

        public void setSegment(String segment) {
            this.segment = segment;
        }
    }
}



