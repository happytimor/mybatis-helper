package io.github.happytimor.mybatis.helper.core.function;

import io.github.happytimor.mybatis.helper.core.common.Constants;
import io.github.happytimor.mybatis.helper.core.common.SqlFunctionName;
import io.github.happytimor.mybatis.helper.core.metadata.ColumnFunction;
import io.github.happytimor.mybatis.helper.core.metadata.ColumnWrapper;
import io.github.happytimor.mybatis.helper.core.util.ColumnUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * sql function dic
 * <p>
 * wrap select sql with `max`,`distinct` function grammar
 *
 * @author chenpeng
 */
public class SqlFunction {

    public static <T, ALIAS> ColumnFunction<T, ?> replace(ColumnFunction<T, ?> columnFunction, String source, String target, ColumnFunction<ALIAS, ?> alias) {
        return replace(columnFunction, source, target, ColumnUtils.getFieldName(alias));
    }

    public static <T> ColumnFunction<T, ?> replace(ColumnFunction<T, ?> columnFunction, String source, String target) {
        Map<ColumnFunction<?, ?>, ColumnWrapper> functionMap = ensureMap();
        ColumnWrapper innerWrapper = functionMap.put(columnFunction, new ColumnWrapper(SqlFunctionName.REPLACE, new String[]{source, target}, ""));
        if (innerWrapper != null) {
            functionMap.get(columnFunction).setChildWrapper(innerWrapper);
        }
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> replace(ColumnFunction<T, ?> columnFunction, String source, String target, String alias) {
        Map<ColumnFunction<?, ?>, ColumnWrapper> functionMap = ensureMap();
        ColumnWrapper innerWrapper = functionMap.put(columnFunction, new ColumnWrapper(SqlFunctionName.REPLACE, new String[]{source, target}, alias));
        if (innerWrapper != null) {
            functionMap.get(columnFunction).setChildWrapper(innerWrapper);
        }
        return columnFunction;
    }

    public static <T, ALIAS> ColumnFunction<T, ?> as(ColumnFunction<T, ?> columnFunction, ColumnFunction<ALIAS, ?> alias) {
        return as(columnFunction, ColumnUtils.getFieldName(alias));
    }

    public static <T> ColumnFunction<T, ?> as(ColumnFunction<T, ?> columnFunction, String alias) {
        Map<ColumnFunction<?, ?>, ColumnWrapper> functionMap = ensureMap();
        functionMap.put(columnFunction, new ColumnWrapper(SqlFunctionName.AS, alias));
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> distinct(ColumnFunction<T, ?> columnFunction) {
        return distinct(columnFunction, "");
    }

    public static <T, ALIAS> ColumnFunction<T, ?> distinct(ColumnFunction<T, ?> columnFunction, ColumnFunction<ALIAS, ?> alias) {
        return distinct(columnFunction, ColumnUtils.getFieldName(alias));
    }

    public static <T> ColumnFunction<T, ?> distinct(ColumnFunction<T, ?> columnFunction, String alias) {
        Map<ColumnFunction<?, ?>, ColumnWrapper> functionMap = ensureMap();
        ColumnWrapper innerWrapper = functionMap.put(columnFunction, new ColumnWrapper(SqlFunctionName.DISTINCT, alias));
        if (innerWrapper != null) {
            functionMap.get(columnFunction).setChildWrapper(innerWrapper);
        }
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> max(ColumnFunction<T, ?> columnFunction) {
        return max(columnFunction, "");
    }

    public static <T, ALIAS> ColumnFunction<T, ?> max(ColumnFunction<T, ?> columnFunction, ColumnFunction<ALIAS, ?> alias) {
        return max(columnFunction, ColumnUtils.getFieldName(alias));
    }

    public static <T> ColumnFunction<T, ?> max(ColumnFunction<T, ?> columnFunction, String alias) {
        Map<ColumnFunction<?, ?>, ColumnWrapper> functionMap = ensureMap();
        ColumnWrapper innerWrapper = functionMap.put(columnFunction, new ColumnWrapper(SqlFunctionName.MAX, alias));
        if (innerWrapper != null) {
            functionMap.get(columnFunction).setChildWrapper(innerWrapper);
        }
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> min(ColumnFunction<T, ?> columnFunction) {
        return min(columnFunction, "");
    }

    public static <T, ALIAS> ColumnFunction<T, ?> min(ColumnFunction<T, ?> columnFunction, ColumnFunction<ALIAS, ?> alias) {
        return min(columnFunction, ColumnUtils.getFieldName(alias));
    }

    public static <T> ColumnFunction<T, ?> min(ColumnFunction<T, ?> columnFunction, String alias) {
        Map<ColumnFunction<?, ?>, ColumnWrapper> functionMap = ensureMap();
        ColumnWrapper innerWrapper = functionMap.put(columnFunction, new ColumnWrapper(SqlFunctionName.MIN, alias));
        if (innerWrapper != null) {
            functionMap.get(columnFunction).setChildWrapper(innerWrapper);
        }
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> avg(ColumnFunction<T, ?> columnFunction) {
        return avg(columnFunction, "");
    }

    public static <T, ALIAS> ColumnFunction<T, ?> avg(ColumnFunction<T, ?> columnFunction, ColumnFunction<ALIAS, ?> alias) {
        return avg(columnFunction, ColumnUtils.getFieldName(alias));
    }

    public static <T> ColumnFunction<T, ?> avg(ColumnFunction<T, ?> columnFunction, String alias) {
        Map<ColumnFunction<?, ?>, ColumnWrapper> functionMap = ensureMap();
        ColumnWrapper innerWrapper = functionMap.put(columnFunction, new ColumnWrapper(SqlFunctionName.AVG, alias));
        if (innerWrapper != null) {
            functionMap.get(columnFunction).setChildWrapper(innerWrapper);
        }
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> dateFormat(ColumnFunction<T, ?> columnFunction, String format) {
        return dateFormat(columnFunction, format, "");
    }

    public static <T, ALIAS> ColumnFunction<T, ?> dateFormat(ColumnFunction<T, ?> columnFunction, String format, ColumnFunction<ALIAS, ?> alias) {
        return dateFormat(columnFunction, format, ColumnUtils.getFieldName(alias));
    }

    public static <T> ColumnFunction<T, ?> dateFormat(ColumnFunction<T, ?> columnFunction, String format, String alias) {
        Map<ColumnFunction<?, ?>, ColumnWrapper> functionMap = ensureMap();
        ColumnWrapper innerWrapper = functionMap.put(columnFunction, new ColumnWrapper(SqlFunctionName.DATE_FORMAT, format, alias));
        if (innerWrapper != null) {
            functionMap.get(columnFunction).setChildWrapper(innerWrapper);
        }
        return columnFunction;
    }


    public static <T> ColumnFunction<T, ?> sum(ColumnFunction<T, ?> columnFunction) {
        return sum(columnFunction, "");
    }

    public static <T, ALIAS> ColumnFunction<T, ?> sum(ColumnFunction<T, ?> columnFunction, ColumnFunction<ALIAS, ?> alias) {
        return sum(columnFunction, ColumnUtils.getFieldName(alias));
    }

    public static <T> ColumnFunction<T, ?> sum(ColumnFunction<T, ?> columnFunction, String alias) {
        Map<ColumnFunction<?, ?>, ColumnWrapper> functionMap = ensureMap();
        ColumnWrapper innerWrapper = functionMap.put(columnFunction, new ColumnWrapper(SqlFunctionName.SUM, alias));
        if (innerWrapper != null) {
            functionMap.get(columnFunction).setChildWrapper(innerWrapper);
        }
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> count(ColumnFunction<T, ?> columnFunction) {
        return count(columnFunction, "");
    }

    public static <T, ALIAS> ColumnFunction<T, ?> count(ColumnFunction<T, ?> columnFunction, ColumnFunction<ALIAS, ?> alias) {
        return count(columnFunction, ColumnUtils.getFieldName(alias));
    }

    public static <T> ColumnFunction<T, ?> count(ColumnFunction<T, ?> columnFunction, String alias) {
        Map<ColumnFunction<?, ?>, ColumnWrapper> functionMap = ensureMap();
        ColumnWrapper innerWrapper = functionMap.put(columnFunction, new ColumnWrapper(SqlFunctionName.COUNT, alias));
        if (innerWrapper != null) {
            functionMap.get(columnFunction).setChildWrapper(innerWrapper);
        }
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> length(ColumnFunction<T, ?> columnFunction) {
        return length(columnFunction, "");
    }

    public static <T, ALIAS> ColumnFunction<T, ?> length(ColumnFunction<T, ?> columnFunction, ColumnFunction<ALIAS, ?> alias) {
        return length(columnFunction, ColumnUtils.getFieldName(alias));
    }

    public static <T> ColumnFunction<T, ?> length(ColumnFunction<T, ?> columnFunction, String alias) {
        Map<ColumnFunction<?, ?>, ColumnWrapper> functionMap = ensureMap();
        ColumnWrapper innerWrapper = functionMap.put(columnFunction, new ColumnWrapper(SqlFunctionName.LENGTH, alias));
        if (innerWrapper != null) {
            functionMap.get(columnFunction).setChildWrapper(innerWrapper);
        }
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> upper(ColumnFunction<T, ?> columnFunction) {
        return upper(columnFunction, "");
    }

    public static <T, ALIAS> ColumnFunction<T, ?> upper(ColumnFunction<T, ?> columnFunction, ColumnFunction<ALIAS, ?> alias) {
        return upper(columnFunction, ColumnUtils.getFieldName(alias));
    }

    public static <T> ColumnFunction<T, ?> upper(ColumnFunction<T, ?> columnFunction, String alias) {
        Map<ColumnFunction<?, ?>, ColumnWrapper> functionMap = ensureMap();
        ColumnWrapper innerWrapper = functionMap.put(columnFunction, new ColumnWrapper(SqlFunctionName.UPPER, alias));
        if (innerWrapper != null) {
            functionMap.get(columnFunction).setChildWrapper(innerWrapper);
        }
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> lower(ColumnFunction<T, ?> columnFunction) {
        return lower(columnFunction, "");
    }

    public static <T, ALIAS> ColumnFunction<T, ?> lower(ColumnFunction<T, ?> columnFunction, ColumnFunction<ALIAS, ?> alias) {
        return lower(columnFunction, ColumnUtils.getFieldName(alias));
    }

    public static <T> ColumnFunction<T, ?> lower(ColumnFunction<T, ?> columnFunction, String alias) {
        Map<ColumnFunction<?, ?>, ColumnWrapper> functionMap = ensureMap();
        ColumnWrapper innerWrapper = functionMap.put(columnFunction, new ColumnWrapper(SqlFunctionName.LOWER, alias));
        if (innerWrapper != null) {
            functionMap.get(columnFunction).setChildWrapper(innerWrapper);
        }
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> ceil(ColumnFunction<T, ?> columnFunction) {
        return ceil(columnFunction, "");
    }

    public static <T, ALIAS> ColumnFunction<T, ?> ceil(ColumnFunction<T, ?> columnFunction, ColumnFunction<ALIAS, ?> alias) {
        return ceil(columnFunction, ColumnUtils.getFieldName(alias));
    }

    public static <T> ColumnFunction<T, ?> ceil(ColumnFunction<T, ?> columnFunction, String alias) {
        Map<ColumnFunction<?, ?>, ColumnWrapper> functionMap = ensureMap();
        ColumnWrapper innerWrapper = functionMap.put(columnFunction, new ColumnWrapper(SqlFunctionName.CEIL, alias));
        if (innerWrapper != null) {
            functionMap.get(columnFunction).setChildWrapper(innerWrapper);
        }
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> abs(ColumnFunction<T, ?> columnFunction) {
        return abs(columnFunction, "");
    }

    public static <T, ALIAS> ColumnFunction<T, ?> abs(ColumnFunction<T, ?> columnFunction, ColumnFunction<ALIAS, ?> alias) {
        return abs(columnFunction, ColumnUtils.getFieldName(alias));
    }

    public static <T> ColumnFunction<T, ?> abs(ColumnFunction<T, ?> columnFunction, String alias) {
        Map<ColumnFunction<?, ?>, ColumnWrapper> functionMap = ensureMap();
        ColumnWrapper innerWrapper = functionMap.put(columnFunction, new ColumnWrapper(SqlFunctionName.ABS, alias));
        if (innerWrapper != null) {
            functionMap.get(columnFunction).setChildWrapper(innerWrapper);
        }
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> year(ColumnFunction<T, ?> columnFunction) {
        return year(columnFunction, "");
    }

    public static <T, ALIAS> ColumnFunction<T, ?> year(ColumnFunction<T, ?> columnFunction, ColumnFunction<ALIAS, ?> alias) {
        return year(columnFunction, ColumnUtils.getFieldName(alias));
    }

    public static <T> ColumnFunction<T, ?> year(ColumnFunction<T, ?> columnFunction, String alias) {
        Map<ColumnFunction<?, ?>, ColumnWrapper> functionMap = ensureMap();
        ColumnWrapper innerWrapper = functionMap.put(columnFunction, new ColumnWrapper(SqlFunctionName.YEAR, alias));
        if (innerWrapper != null) {
            functionMap.get(columnFunction).setChildWrapper(innerWrapper);
        }
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> month(ColumnFunction<T, ?> columnFunction) {
        return month(columnFunction, "");
    }

    public static <T, ALIAS> ColumnFunction<T, ?> month(ColumnFunction<T, ?> columnFunction, ColumnFunction<ALIAS, ?> alias) {
        return month(columnFunction, ColumnUtils.getFieldName(alias));
    }

    public static <T> ColumnFunction<T, ?> month(ColumnFunction<T, ?> columnFunction, String alias) {
        Map<ColumnFunction<?, ?>, ColumnWrapper> functionMap = ensureMap();
        ColumnWrapper innerWrapper = functionMap.put(columnFunction, new ColumnWrapper(SqlFunctionName.MONTH, alias));
        if (innerWrapper != null) {
            functionMap.get(columnFunction).setChildWrapper(innerWrapper);
        }
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> week(ColumnFunction<T, ?> columnFunction) {
        return week(columnFunction, "");
    }

    public static <T, ALIAS> ColumnFunction<T, ?> week(ColumnFunction<T, ?> columnFunction, ColumnFunction<ALIAS, ?> alias) {
        return week(columnFunction, ColumnUtils.getFieldName(alias));
    }

    public static <T> ColumnFunction<T, ?> week(ColumnFunction<T, ?> columnFunction, String alias) {
        Map<ColumnFunction<?, ?>, ColumnWrapper> functionMap = ensureMap();
        ColumnWrapper innerWrapper = functionMap.put(columnFunction, new ColumnWrapper(SqlFunctionName.WEEK, alias));
        if (innerWrapper != null) {
            functionMap.get(columnFunction).setChildWrapper(innerWrapper);
        }
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> hour(ColumnFunction<T, ?> columnFunction) {
        return hour(columnFunction, "");
    }

    public static <T, ALIAS> ColumnFunction<T, ?> hour(ColumnFunction<T, ?> columnFunction, ColumnFunction<ALIAS, ?> alias) {
        return hour(columnFunction, ColumnUtils.getFieldName(alias));
    }

    public static <T> ColumnFunction<T, ?> hour(ColumnFunction<T, ?> columnFunction, String alias) {
        Map<ColumnFunction<?, ?>, ColumnWrapper> functionMap = ensureMap();
        ColumnWrapper innerWrapper = functionMap.put(columnFunction, new ColumnWrapper(SqlFunctionName.HOUR, alias));
        if (innerWrapper != null) {
            functionMap.get(columnFunction).setChildWrapper(innerWrapper);
        }
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> minute(ColumnFunction<T, ?> columnFunction) {
        return minute(columnFunction, "");
    }

    public static <T, ALIAS> ColumnFunction<T, ?> minute(ColumnFunction<T, ?> columnFunction, ColumnFunction<ALIAS, ?> alias) {
        return minute(columnFunction, ColumnUtils.getFieldName(alias));
    }

    public static <T> ColumnFunction<T, ?> minute(ColumnFunction<T, ?> columnFunction, String alias) {
        Map<ColumnFunction<?, ?>, ColumnWrapper> functionMap = ensureMap();
        ColumnWrapper innerWrapper = functionMap.put(columnFunction, new ColumnWrapper(SqlFunctionName.MINUTE, alias));
        if (innerWrapper != null) {
            functionMap.get(columnFunction).setChildWrapper(innerWrapper);
        }
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> weekday(ColumnFunction<T, ?> columnFunction) {
        return weekday(columnFunction, "");
    }

    public static <T> ColumnFunction<T, ?> weekday(ColumnFunction<T, ?> columnFunction, String alias) {
        Map<ColumnFunction<?, ?>, ColumnWrapper> functionMap = ensureMap();
        ColumnWrapper innerWrapper = functionMap.put(columnFunction, new ColumnWrapper(SqlFunctionName.WEEK_DAY, alias));
        if (innerWrapper != null) {
            functionMap.get(columnFunction).setChildWrapper(innerWrapper);
        }
        return columnFunction;
    }

    public static <T, ALIAS> ColumnFunction<T, ?> weekday(ColumnFunction<T, ?> columnFunction, ColumnFunction<ALIAS, ?> alias) {
        return weekday(columnFunction, ColumnUtils.getFieldName(alias));
    }

    public static <T> ColumnFunction<T, ?> dayname(ColumnFunction<T, ?> columnFunction) {
        return dayname(columnFunction, "");
    }

    public static <T> ColumnFunction<T, ?> dayname(ColumnFunction<T, ?> columnFunction, String alias) {
        Map<ColumnFunction<?, ?>, ColumnWrapper> functionMap = ensureMap();
        ColumnWrapper innerWrapper = functionMap.put(columnFunction, new ColumnWrapper(SqlFunctionName.DAY_NAME, alias));
        if (innerWrapper != null) {
            functionMap.get(columnFunction).setChildWrapper(innerWrapper);
        }
        return columnFunction;
    }

    public static <T, ALIAS> ColumnFunction<T, ?> dayname(ColumnFunction<T, ?> columnFunction, ColumnFunction<ALIAS, ?> alias) {
        return dayname(columnFunction, ColumnUtils.getFieldName(alias));
    }

    public static Map<ColumnFunction<?, ?>, ColumnWrapper> ensureMap() {
        Map<ColumnFunction<?, ?>, ColumnWrapper> columnFuncationMap = Constants.THREAD_COLUMN_FUNCTION.get();
        if (columnFuncationMap == null) {
            columnFuncationMap = new HashMap<>(16);
            Constants.THREAD_COLUMN_FUNCTION.set(columnFuncationMap);
        }
        return columnFuncationMap;
    }
}
