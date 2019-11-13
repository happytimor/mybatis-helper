package io.github.happytimor.mybatis.helper.core.function;

import io.github.happytimor.mybatis.helper.core.common.SqlFunctionName;
import io.github.happytimor.mybatis.helper.core.metadata.ColumnFunction;
import io.github.happytimor.mybatis.helper.core.metadata.ColumnWrapper;
import io.github.happytimor.mybatis.helper.core.util.ColumnUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * sql函数合集
 *
 * @author chenpeng
 */
public class SqlFunction {
    public static final Map<ColumnFunction, ColumnWrapper> FUNCTION_MAP = new ConcurrentHashMap<>();

    public static <T> ColumnFunction<T, ?> max(ColumnFunction<T, ?> columnFunction) {
        return max(columnFunction, "");
    }

    public static <T> ColumnFunction<T, ?> max(ColumnFunction<T, ?> columnFunction, String alias) {
        FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.MAX, alias));
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> max(ColumnFunction<T, ?> columnFunction, ColumnFunction<T, ?> alias) {
        FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.MAX, ColumnUtils.camelCaseToUnderscore(alias)));
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> min(ColumnFunction<T, ?> columnFunction) {
        return min(columnFunction, "");
    }

    public static <T> ColumnFunction<T, ?> min(ColumnFunction<T, ?> columnFunction, String alias) {
        FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.MIN, alias));
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> min(ColumnFunction<T, ?> columnFunction, ColumnFunction<T, ?> alias) {
        FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.MAX, ColumnUtils.camelCaseToUnderscore(alias)));
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> avg(ColumnFunction<T, ?> columnFunction) {
        return avg(columnFunction, "");
    }

    public static <T> ColumnFunction<T, ?> avg(ColumnFunction<T, ?> columnFunction, String alias) {
        FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.AVG, alias));
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> avg(ColumnFunction<T, ?> columnFunction, ColumnFunction<T, ?> alias) {
        FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.AVG, ColumnUtils.camelCaseToUnderscore(alias)));
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> sum(ColumnFunction<T, ?> columnFunction) {
        return sum(columnFunction, "");
    }

    public static <T> ColumnFunction<T, ?> sum(ColumnFunction<T, ?> columnFunction, String alias) {
        FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.SUM, alias));
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> sum(ColumnFunction<T, ?> columnFunction, ColumnFunction<T, ?> alias) {
        FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.SUM, ColumnUtils.camelCaseToUnderscore(alias)));
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> count(ColumnFunction<T, ?> columnFunction) {
        return count(columnFunction, "");
    }

    public static <T> ColumnFunction<T, ?> count(ColumnFunction<T, ?> columnFunction, String alias) {
        FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.COUNT, alias));
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> count(ColumnFunction<T, ?> columnFunction, ColumnFunction<T, ?> alias) {
        FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.COUNT, ColumnUtils.camelCaseToUnderscore(alias)));
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> length(ColumnFunction<T, ?> columnFunction) {
        return length(columnFunction, "");
    }

    public static <T> ColumnFunction<T, ?> length(ColumnFunction<T, ?> columnFunction, String alias) {
        FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.LENGTH, alias));
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> length(ColumnFunction<T, ?> columnFunction, ColumnFunction<T, ?> alias) {
        FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.LENGTH, ColumnUtils.camelCaseToUnderscore(alias)));
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> upper(ColumnFunction<T, ?> columnFunction) {
        return upper(columnFunction, "");
    }

    public static <T> ColumnFunction<T, ?> upper(ColumnFunction<T, ?> columnFunction, String alias) {
        FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.UPPER, alias));
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> upper(ColumnFunction<T, ?> columnFunction, ColumnFunction<T, ?> alias) {
        FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.UPPER, ColumnUtils.camelCaseToUnderscore(alias)));
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> lower(ColumnFunction<T, ?> columnFunction) {
        return lower(columnFunction, "");
    }

    public static <T> ColumnFunction<T, ?> lower(ColumnFunction<T, ?> columnFunction, String alias) {
        FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.LOWER, alias));
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> lower(ColumnFunction<T, ?> columnFunction, ColumnFunction<T, ?> alias) {
        FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.LOWER, ColumnUtils.camelCaseToUnderscore(alias)));
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> ceil(ColumnFunction<T, ?> columnFunction) {
        return ceil(columnFunction, "");
    }

    public static <T> ColumnFunction<T, ?> ceil(ColumnFunction<T, ?> columnFunction, String alias) {
        FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.CEIL, alias));
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> ceil(ColumnFunction<T, ?> columnFunction, ColumnFunction<T, ?> alias) {
        FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.CEIL, ColumnUtils.camelCaseToUnderscore(alias)));
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> abs(ColumnFunction<T, ?> columnFunction) {
        return abs(columnFunction, "");
    }

    public static <T> ColumnFunction<T, ?> abs(ColumnFunction<T, ?> columnFunction, String alias) {
        FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.ABS, alias));
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> abs(ColumnFunction<T, ?> columnFunction, ColumnFunction<T, ?> alias) {
        FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.ABS, ColumnUtils.camelCaseToUnderscore(alias)));
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> year(ColumnFunction<T, ?> columnFunction) {
        return year(columnFunction, "");
    }

    public static <T> ColumnFunction<T, ?> year(ColumnFunction<T, ?> columnFunction, String alias) {
        FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.YEAR, alias));
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> year(ColumnFunction<T, ?> columnFunction, ColumnFunction<T, ?> alias) {
        FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.YEAR, ColumnUtils.camelCaseToUnderscore(alias)));
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> month(ColumnFunction<T, ?> columnFunction) {
        return month(columnFunction, "");
    }

    public static <T> ColumnFunction<T, ?> month(ColumnFunction<T, ?> columnFunction, String alias) {
        FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.MONTH, alias));
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> month(ColumnFunction<T, ?> columnFunction, ColumnFunction<T, ?> alias) {
        FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.MONTH, ColumnUtils.camelCaseToUnderscore(alias)));
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> week(ColumnFunction<T, ?> columnFunction) {
        return week(columnFunction, "");
    }

    public static <T> ColumnFunction<T, ?> week(ColumnFunction<T, ?> columnFunction, String alias) {
        FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.WEEK, alias));
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> week(ColumnFunction<T, ?> columnFunction, ColumnFunction<T, ?> alias) {
        FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.WEEK, ColumnUtils.camelCaseToUnderscore(alias)));
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> hour(ColumnFunction<T, ?> columnFunction) {
        return hour(columnFunction, "");
    }

    public static <T> ColumnFunction<T, ?> hour(ColumnFunction<T, ?> columnFunction, String alias) {
        FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.HOUR, alias));
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> hour(ColumnFunction<T, ?> columnFunction, ColumnFunction<T, ?> alias) {
        FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.HOUR, ColumnUtils.camelCaseToUnderscore(alias)));
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> minute(ColumnFunction<T, ?> columnFunction) {
        return minute(columnFunction, "");
    }

    public static <T> ColumnFunction<T, ?> minute(ColumnFunction<T, ?> columnFunction, String alias) {
        FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.MINUTE, alias));
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> minute(ColumnFunction<T, ?> columnFunction, ColumnFunction<T, ?> alias) {
        FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.MINUTE, ColumnUtils.camelCaseToUnderscore(alias)));
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> weekday(ColumnFunction<T, ?> columnFunction) {
        return weekday(columnFunction, "");
    }

    public static <T> ColumnFunction<T, ?> weekday(ColumnFunction<T, ?> columnFunction, String alias) {
        FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.WEEK_DAY, alias));
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> weekday(ColumnFunction<T, ?> columnFunction, ColumnFunction<T, ?> alias) {
        FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.WEEK_DAY, ColumnUtils.camelCaseToUnderscore(alias)));
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> dayname(ColumnFunction<T, ?> columnFunction) {
        return dayname(columnFunction, "");
    }

    public static <T> ColumnFunction<T, ?> dayname(ColumnFunction<T, ?> columnFunction, String alias) {
        FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.DAY_NAME, alias));
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> dayname(ColumnFunction<T, ?> columnFunction, ColumnFunction<T, ?> alias) {
        FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.DAY_NAME, ColumnUtils.camelCaseToUnderscore(alias)));
        return columnFunction;
    }
}
