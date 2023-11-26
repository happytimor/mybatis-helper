package io.github.happytimor.mybatis.helper.core.wrapper;

import io.github.happytimor.mybatis.helper.core.annotation.TableName;
import io.github.happytimor.mybatis.helper.core.common.Constants;
import io.github.happytimor.mybatis.helper.core.common.SelectColumn;
import io.github.happytimor.mybatis.helper.core.common.SqlFunctionName;
import io.github.happytimor.mybatis.helper.core.metadata.ColumnFunction;
import io.github.happytimor.mybatis.helper.core.metadata.ColumnWrapper;
import io.github.happytimor.mybatis.helper.core.metadata.Condition;
import io.github.happytimor.mybatis.helper.core.metadata.JoinInfo;
import io.github.happytimor.mybatis.helper.core.util.ColumnUtils;
import io.github.happytimor.mybatis.helper.core.util.LambdaUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author chenpeng
 */
public abstract class AbstractWrapper<T> {

    protected List<Condition> conditionList = new ArrayList<>();
    protected String tableName;
    protected Map<String, Object> paramNameValuePairs;
    protected AtomicInteger counter;
    /**
     * 是否包含分组, 如果包含分组, selectPage里面的selectCount需要做特殊处理
     */
    protected boolean hasGrouping = false;
    /**
     * 排序字段以及排序方式
     */
    protected final List<OrderWrapper.Order> orderList = new ArrayList<>();

    protected final List<SelectColumn> selectColumnList = new ArrayList<>();
    /**
     * JoinWrapper
     */
    protected Map<Class<?>, Integer> subTable = new HashMap<>();
    /**
     * JoinWrapper: 表序号
     */
    protected int tableIndex = 2;
    /**
     * JoinWrapper
     */
    protected List<JoinInfo> joinInfoList = new ArrayList<>();

    /**
     * limit语句
     */
    protected String limit = "";

    /**
     * select for update
     */
    protected String forUpdateSegment = "";

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

    /**
     * 获取joinSegment片段
     *
     * @return join语句
     */
    public String getJoinSegment() {
        if (joinInfoList.isEmpty()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (JoinInfo joinInfo : joinInfoList) {
            stringBuilder.append(joinInfo.getKeyword())
                    .append(" ")
                    .append(this.getTableName(joinInfo.getJoinClazz()))
                    .append(" ")
                    .append(ColumnUtils.getTableAlias(this.subTable, joinInfo.getJoinClazz(), false))
                    .append(" ON ")
                    .append(ColumnUtils.getTableAlias(this.subTable, joinInfo.getJoinClazz(), true))
                    .append(ColumnUtils.getColumnName(joinInfo.getLeftColumn()))
                    .append(" = ")
                    .append(ColumnUtils.getTableAlias(this.subTable, joinInfo.getRightColumn()))
                    .append(ColumnUtils.getColumnName(joinInfo.getRightColumn()));
        }
        return stringBuilder.toString();
    }

    protected String getTableName(Class<?> clazz) {
        TableName tableNameAnnotation = clazz.getAnnotation(TableName.class);
        String tableName = tableNameAnnotation != null ? tableNameAnnotation.value() :
                ColumnUtils.camelCaseToUnderscore(clazz.getSimpleName());
        return "`" + tableName + "`";
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
            OrderWrapper.Order order = orderList.get(i);
            String columnName = order.getColumnName();
            if (StringUtils.isEmpty(columnName)) {
                columnName = ColumnUtils.getColumnName(order.getColumn());
            }
            if (order.getColumn() != null) {
                columnName = ColumnUtils.getTableAlias(this.subTable, order.getColumn()) + columnName;
            }

            stringBuffer.append(columnName).append(" ").append(order.getOrderType()).append(", ");
        }
        OrderWrapper.Order order = orderList.get(orderList.size() - 1);
        String columnName = order.getColumnName();
        if (StringUtils.isEmpty(columnName)) {
            columnName = ColumnUtils.getColumnName(order.getColumn());
        }
        if (order.getColumn() != null) {
            columnName = ColumnUtils.getTableAlias(this.subTable, order.getColumn()) + columnName;
        }
        stringBuffer.append(columnName).append(" ").append(order.getOrderType());
        return stringBuffer.toString();
    }

    /**
     * selectPage进行group分组是, selectCount方法要做前后包裹指定sql
     *
     * @return 前包裹sql
     */
    public String getSelectCountWrapperForBegin() {
        if (!hasGrouping) {
            return "";
        }
        return "SELECT COUNT(*) FROM (";
    }

    /**
     * selectPage进行group分组是, selectCount方法要做前后包裹指定sql
     *
     * @return 后包裹sql
     */
    public String getSelectCountWrapperForEnd() {
        if (!hasGrouping) {
            return "";
        }
        return ") a";
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
        try {
            if (selectColumnList.isEmpty()) {
                return "*";
            }
            return selectColumnList.stream().map(selectColumn -> {
                String columnName = selectColumn.getColumnFunction() != null ?
                        this.parseColumnName(selectColumn.getColumnFunction()) : selectColumn.getColumnName();
                if (subTable.isEmpty()) {
                    return columnName;
                }
                Class<?> clazz = selectColumn.getClazz() != null ?
                        selectColumn.getClazz() : this.parseClazz(selectColumn.getColumnFunction());
                String tableAlias = ColumnUtils.getTableAlias(this.subTable, clazz, true);
                return tableAlias + columnName + " ";
            }).collect(Collectors.joining(","));
        } finally {
            Constants.THREAD_COLUMN_FUNCTION.remove();
        }
    }

    /**
     * 获取表别名, join时候会用到
     *
     * @return 别名
     */
    public String getTableAliasSegment() {
        if (subTable.isEmpty()) {
            return "";
        }
        return " t1";
    }

    /**
     * 解析字段名
     *
     * @param columnFunction 字段函数
     * @return 字段名
     */
    protected <E> String parseColumnName(ColumnFunction<E, ?> columnFunction) {
        return this.parseColumnName(columnFunction, false);
    }

    /**
     * 解析字段名
     *
     * @param columnFunction 字段函数
     * @param appendAlias    true-字段带上别名, false-不带上别名
     * @return 字段名
     */
    protected <E> String parseColumnName(ColumnFunction<E, ?> columnFunction, boolean appendAlias) {
        String columnName = ColumnUtils.getColumnName(columnFunction);
        if (appendAlias) {
            String tableAlias = ColumnUtils.getTableAlias(this.subTable, columnFunction);
            columnName = tableAlias + columnName;
        }
        Map<ColumnFunction<?, ?>, ColumnWrapper> functionMap = Constants.THREAD_COLUMN_FUNCTION.get();
        if (functionMap != null) {
            ColumnWrapper columnWrapper = functionMap.remove(columnFunction);
            if (columnWrapper != null) {
                return this.wrapperFunctionColumn(columnWrapper, columnName);
            }
        }
        return columnName;
    }

    /**
     * 通过lambda表达式获取所属类
     *
     * @param columnFunction lambda表达式
     * @return 类class
     */
    protected Class<?> parseClazz(ColumnFunction<?, ?> columnFunction) {
        return LambdaUtils.resolve(columnFunction).getInstantiatedType();
    }

    /**
     * analysis sql function name
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
        String parameter = columnWrapper.getParameter();
        if ("".equals(parameter)) {
            return function + "(" + columnName + ")" + alias;
        }
        return function + "(" + columnName + "," + "'" + parameter + "')" + alias;
    }

    /**
     * 追加" FOR UPDATE"
     *
     * @return abstractWrapper
     */
    public AbstractWrapper<T> forUpdate() {
        this.forUpdateSegment = " FOR UPDATE";
        return this;
    }

    /**
     * 获取limit sql
     *
     * @return limit sql
     */
    public final String getLimitSegment() {
        return limit;
    }

    /**
     * 获取for update sql
     *
     * @return for update
     */
    public final String getForUpdateSegment() {
        return forUpdateSegment;
    }

}
