package io.github.happytimor.mybatis.helper.core.wrapper;

import io.github.happytimor.mybatis.helper.core.metadata.ColumnFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenpeng
 */
public class HavingWrapper<T> extends OrderWrapper<T> {

    private final List<HavingCondition<T>> havingConditionList = new ArrayList<>();

    public HavingWrapper<T> havingLt(ColumnFunction<T, ?> columnFunction, Object value) {
        this.havingConditionList.add(new HavingCondition<T>(columnFunction, "<", value));
        return this;
    }

    public HavingWrapper<T> havingLt(String columnName, Object value) {
        this.havingConditionList.add(new HavingCondition<T>(columnName, "<", value));
        return this;
    }

    public HavingWrapper<T> havingLe(ColumnFunction<T, ?> columnFunction, Object value) {
        this.havingConditionList.add(new HavingCondition<T>(columnFunction, "<=", value));
        return this;
    }

    public HavingWrapper<T> havingLe(String columnName, Object value) {
        this.havingConditionList.add(new HavingCondition<T>(columnName, "<=", value));
        return this;
    }

    public HavingWrapper<T> havingEq(ColumnFunction<T, ?> columnFunction, Object value) {
        this.havingConditionList.add(new HavingCondition<T>(columnFunction, "=", value));
        return this;
    }

    public HavingWrapper<T> havingEq(String columnName, Object value) {
        this.havingConditionList.add(new HavingCondition<T>(columnName, "=", value));
        return this;
    }

    public HavingWrapper<T> havingGe(ColumnFunction<T, ?> columnFunction, Object value) {
        this.havingConditionList.add(new HavingCondition<T>(columnFunction, ">=", value));
        return this;
    }

    public HavingWrapper<T> havingGe(String columnName, Object value) {
        this.havingConditionList.add(new HavingCondition<T>(columnName, ">=", value));
        return this;
    }

    public HavingWrapper<T> havingGt(ColumnFunction<T, ?> columnFunction, Object value) {
        this.havingConditionList.add(new HavingCondition<>(columnFunction, ">", value));
        return this;
    }

    public HavingWrapper<T> havingGt(String columnName, Object value) {
        this.havingConditionList.add(new HavingCondition<>(columnName, ">", value));
        return this;
    }

    public HavingWrapper<T> havingNe(ColumnFunction<T, ?> columnFunction, Object value) {
        this.havingConditionList.add(new HavingCondition<T>(columnFunction, "<>", value));
        return this;
    }

    public HavingWrapper<T> havingNe(String columnName, Object value) {
        this.havingConditionList.add(new HavingCondition<T>(columnName, "<>", value));
        return this;
    }

    static class HavingCondition<T> {
        private ColumnFunction<T, ?> columnFunction;
        private String columnName;
        private Object value;
        private String connector;

        public HavingCondition(ColumnFunction<T, ?> columnFunction, String connector, Object value) {
            this.columnFunction = columnFunction;
            this.connector = connector;
            this.value = value;
        }

        public HavingCondition(String columnName, String connector, Object value) {
            this.columnName = columnName;
            this.connector = connector;
            this.value = value;
        }

        public ColumnFunction<T, ?> getColumnFunction() {
            return columnFunction;
        }

        public void setColumnFunction(ColumnFunction<T, ?> columnFunction) {
            this.columnFunction = columnFunction;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public String getConnector() {
            return connector;
        }

        public void setConnector(String connector) {
            this.connector = connector;
        }

        public String getColumnName() {
            return columnName;
        }

        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }
    }

    /**
     * 获取分组过滤Segment
     *
     * @return 分组segment
     */
    public String getHavingSegment() {
        if (havingConditionList.isEmpty()) {
            return "";
        }


        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < havingConditionList.size(); i++) {
            HavingCondition<T> havingCondition = havingConditionList.get(i);
            String columnName = havingCondition.getColumnName();
            if (columnName == null || "".equals(columnName)) {
                columnName = this.parseColumnName(havingCondition.getColumnFunction());
            }

            stringBuilder.append(columnName).append(" ").append(havingCondition.getConnector())
                    .append(" ").append(havingCondition.getValue());
            if (i != havingConditionList.size() - 1) {
                stringBuilder.append(" AND ");
            }
        }
        return " HAVING " + stringBuilder.toString();
    }


}


