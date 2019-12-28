package io.github.happytimor.mybatis.helper.core.wrapper;

import io.github.happytimor.mybatis.helper.core.common.SqlFunctionName;
import io.github.happytimor.mybatis.helper.core.function.SqlFunction;
import io.github.happytimor.mybatis.helper.core.metadata.ColumnFunction;
import io.github.happytimor.mybatis.helper.core.metadata.ColumnWrapper;
import io.github.happytimor.mybatis.helper.core.metadata.Condition;
import io.github.happytimor.mybatis.helper.core.util.ColumnUtils;

import java.beans.Introspector;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chenpeng
 */
public abstract class AbstractWrapper<T> {

    protected List<Condition> conditionList = new ArrayList<>();
    protected String tableName;
    protected Map<String, Object> paramNameValuePairs;
    protected AtomicInteger counter;
    protected String selectSegment = "*";

    public AbstractWrapper() {
        this.paramNameValuePairs = new HashMap<>();
        this.counter = new AtomicInteger(0);
    }

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

    protected String getColumnName(ColumnFunction<T, ?> column) {
        return getColumnName(column, true);
    }

    protected String getColumnName(ColumnFunction<T, ?> column, boolean wrap) {
        String columnName = ColumnUtils.camelCaseToUnderscore(column);
        return wrap ? "`" + columnName + "`" : columnName;
    }

    protected String wrapColumnName(String columnName) {
        return "`" + columnName + "`";
    }


    public Map<String, Object> getParamNameValuePairs() {
        return paramNameValuePairs;
    }

    public void setParamNameValuePairs(Map<String, Object> paramNameValuePairs) {
        this.paramNameValuePairs = paramNameValuePairs;
    }

    public AtomicInteger getCounter() {
        return counter;
    }

    public void setCounter(AtomicInteger counter) {
        this.counter = counter;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getSelectSegment() {
        return selectSegment;
    }

    public void setSelectSegment(String selectSegment) {
        this.selectSegment = selectSegment;
    }

    /**
     * 解析字段名
     *
     * @param columnFunction 字段函数
     * @return 字段名
     */
    protected String parseColumnName(ColumnFunction<T, ?> columnFunction) {
        String columnName = this.getColumnName(columnFunction);
        ColumnWrapper columnWrapper = SqlFunction.FUNCTION_MAP.remove(columnFunction);
        if (columnWrapper != null) {
            return this.wrapperFunctionColumn(columnWrapper, columnName);
        }
        return columnName;
    }

    /**
     * 解析带函数字段名
     *
     * @param columnWrapper 函数wrapper
     * @param columnName    字段名
     * @return 包装后的字段名
     */
    protected String wrapperFunctionColumn(ColumnWrapper columnWrapper, String columnName) {
        String function = columnWrapper.getFunction();
        String alias = "".equals(columnWrapper.getAlias()) ? "" : " AS '" + columnWrapper.getAlias() + "'";
        if (Objects.equals(function, SqlFunctionName.AS)) {
            return columnName + alias;
        }


        if (columnWrapper.getChildWrapper() != null) {
            columnName = this.wrapperFunctionColumn(columnWrapper.getChildWrapper(), columnName);
        }
        return function + "(" + columnName + ")" + alias;
    }
}
