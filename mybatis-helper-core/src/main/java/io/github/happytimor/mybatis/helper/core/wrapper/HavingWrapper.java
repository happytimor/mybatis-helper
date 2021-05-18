package io.github.happytimor.mybatis.helper.core.wrapper;

import io.github.happytimor.mybatis.helper.core.metadata.ColumnFunction;
import io.github.happytimor.mybatis.helper.core.metadata.DefaultHavingWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenpeng
 */
public class HavingWrapper<T> extends OrderWrapper<T> implements DefaultHavingWrapper<T> {

    private final List<HavingCondition<T>> havingConditionList = new ArrayList<>();

    @Override
    public HavingWrapper<T> havingLt(boolean execute, ColumnFunction<T, ?> columnFunction, Object value) {
        return this.havingLt(execute, this.parseColumnName(columnFunction), value);
    }

    @Override
    public HavingWrapper<T> havingLt(boolean execute, String columnName, Object value) {
        if (execute) {
            this.havingConditionList.add(new HavingCondition<>(columnName, "<", value));
        }
        return this;
    }

    @Override
    public HavingWrapper<T> havingLe(boolean execute, ColumnFunction<T, ?> columnFunction, Object value) {
        return this.havingLe(execute, this.parseColumnName(columnFunction), value);
    }

    @Override
    public HavingWrapper<T> havingLe(boolean execute, String columnName, Object value) {
        if (execute) {
            this.havingConditionList.add(new HavingCondition<>(columnName, "<=", value));
        }
        return this;
    }

    @Override
    public HavingWrapper<T> havingEq(boolean execute, ColumnFunction<T, ?> columnFunction, Object value) {
        return this.havingEq(execute, this.parseColumnName(columnFunction), value);
    }

    @Override
    public HavingWrapper<T> havingEq(boolean execute, String columnName, Object value) {
        if (execute) {
            this.havingConditionList.add(new HavingCondition<>(columnName, "=", value));
        }
        return this;
    }

    @Override
    public HavingWrapper<T> havingGe(boolean execute, ColumnFunction<T, ?> columnFunction, Object value) {
        return this.havingGe(execute, this.parseColumnName(columnFunction), value);
    }

    @Override
    public HavingWrapper<T> havingGe(boolean execute, String columnName, Object value) {
        if (execute) {
            this.havingConditionList.add(new HavingCondition<>(columnName, ">=", value));
        }
        return this;
    }

    @Override
    public HavingWrapper<T> havingGt(boolean execute, ColumnFunction<T, ?> columnFunction, Object value) {
        return this.havingGt(execute, this.parseColumnName(columnFunction), value);
    }

    @Override
    public HavingWrapper<T> havingGt(boolean execute, String columnName, Object value) {
        if (execute) {
            this.havingConditionList.add(new HavingCondition<>(columnName, ">", value));
        }
        return this;
    }

    @Override
    public HavingWrapper<T> havingNe(boolean execute, ColumnFunction<T, ?> columnFunction, Object value) {
        return this.havingNe(execute, this.parseColumnName(columnFunction), value);
    }

    @Override
    public HavingWrapper<T> havingNe(boolean execute, String columnName, Object value) {
        if (execute) {
            this.havingConditionList.add(new HavingCondition<>(columnName, "<>", value));
        }
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
        return " HAVING " + stringBuilder;
    }


}


