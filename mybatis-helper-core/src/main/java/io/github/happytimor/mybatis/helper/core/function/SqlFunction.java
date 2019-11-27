package io.github.happytimor.mybatis.helper.core.function;

import io.github.happytimor.mybatis.helper.core.common.SqlFunctionName;
import io.github.happytimor.mybatis.helper.core.metadata.ColumnFunction;
import io.github.happytimor.mybatis.helper.core.metadata.ColumnWrapper;
import io.github.happytimor.mybatis.helper.core.util.ColumnUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * sql函数合集
 * <p>
 * 主要就是在select时候对函数进行包裹,加上 max、distinct等函数
 *
 * @author chenpeng
 */
public class SqlFunction {
    public static final Map<ColumnFunction, ColumnWrapper> FUNCTION_MAP = new ConcurrentHashMap<>();

    public static <T> ColumnFunction<T, ?> as(ColumnFunction<T, ?> columnFunction) {
        return as(columnFunction, ColumnUtils.getFieldName(columnFunction));
    }

    public static <T, M> ColumnFunction<T, ?> as(ColumnFunction<T, ?> columnFunction, ColumnFunction<M, ?> alias) {
        return as(columnFunction, ColumnUtils.getFieldName(alias));
    }

    public static <T> ColumnFunction<T, ?> as(ColumnFunction<T, ?> columnFunction, String alias) {
        FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.AS, alias));
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> distinct(ColumnFunction<T, ?> columnFunction) {
        return distinct(columnFunction, "");
    }

    public static <T, M> ColumnFunction<T, ?> distinct(ColumnFunction<T, ?> columnFunction, ColumnFunction<M, ?> alias) {
        return distinct(columnFunction, ColumnUtils.getFieldName(alias));
    }

    public static <T> ColumnFunction<T, ?> distinct(ColumnFunction<T, ?> columnFunction, String alias) {
        ColumnWrapper innerWrapper = FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.DISTINCT, alias));
        if (innerWrapper != null) {
            FUNCTION_MAP.get(columnFunction).setChildWrapper(innerWrapper);
        }
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> max(ColumnFunction<T, ?> columnFunction) {
        return max(columnFunction, "");
    }

    public static <T, M> ColumnFunction<T, ?> max(ColumnFunction<T, ?> columnFunction, ColumnFunction<M, ?> alias) {
        return max(columnFunction, ColumnUtils.getFieldName(alias));
    }

    public static <T> ColumnFunction<T, ?> max(ColumnFunction<T, ?> columnFunction, String alias) {
        ColumnWrapper innerWrapper = FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.MAX, alias));
        if (innerWrapper != null) {
            FUNCTION_MAP.get(columnFunction).setChildWrapper(innerWrapper);
        }
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> min(ColumnFunction<T, ?> columnFunction) {
        return min(columnFunction, "");
    }

    public static <T, M> ColumnFunction<T, ?> min(ColumnFunction<T, ?> columnFunction, ColumnFunction<M, ?> alias) {
        return min(columnFunction, ColumnUtils.getFieldName(alias));
    }

    public static <T> ColumnFunction<T, ?> min(ColumnFunction<T, ?> columnFunction, String alias) {
        ColumnWrapper innerWrapper = FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.MIN, alias));
        if (innerWrapper != null) {
            FUNCTION_MAP.get(columnFunction).setChildWrapper(innerWrapper);
        }
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> avg(ColumnFunction<T, ?> columnFunction) {
        return avg(columnFunction, "");
    }

    public static <T> ColumnFunction<T, ?> avg(ColumnFunction<T, ?> columnFunction, ColumnFunction<?, ?> alias) {
        return avg(columnFunction, ColumnUtils.getFieldName(alias));
    }

    public static <T> ColumnFunction<T, ?> avg(ColumnFunction<T, ?> columnFunction, String alias) {
        ColumnWrapper innerWrapper = FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.AVG, alias));
        if (innerWrapper != null) {
            FUNCTION_MAP.get(columnFunction).setChildWrapper(innerWrapper);
        }
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> sum(ColumnFunction<T, ?> columnFunction) {
        return sum(columnFunction, "");
    }

    public static <T> ColumnFunction<T, ?> sum(ColumnFunction<T, ?> columnFunction, ColumnFunction<?, ?> alias) {
        return sum(columnFunction, ColumnUtils.getFieldName(alias));
    }

    public static <T> ColumnFunction<T, ?> sum(ColumnFunction<T, ?> columnFunction, String alias) {
        ColumnWrapper innerWrapper = FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.SUM, alias));
        if (innerWrapper != null) {
            FUNCTION_MAP.get(columnFunction).setChildWrapper(innerWrapper);
        }
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> count(ColumnFunction<T, ?> columnFunction) {
        return count(columnFunction, "");
    }

    public static <T> ColumnFunction<T, ?> count(ColumnFunction<T, ?> columnFunction, ColumnFunction<?, ?> alias) {
        return count(columnFunction, ColumnUtils.getFieldName(alias));
    }

    public static <T> ColumnFunction<T, ?> count(ColumnFunction<T, ?> columnFunction, String alias) {
        ColumnWrapper innerWrapper = FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.COUNT, alias));
        if (innerWrapper != null) {
            FUNCTION_MAP.get(columnFunction).setChildWrapper(innerWrapper);
        }
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> length(ColumnFunction<T, ?> columnFunction) {
        return length(columnFunction, "");
    }

    public static <T> ColumnFunction<T, ?> length(ColumnFunction<T, ?> columnFunction, ColumnFunction<?, ?> alias) {
        return length(columnFunction, ColumnUtils.getFieldName(alias));
    }

    public static <T> ColumnFunction<T, ?> length(ColumnFunction<T, ?> columnFunction, String alias) {
        ColumnWrapper innerWrapper = FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.LENGTH, alias));
        if (innerWrapper != null) {
            FUNCTION_MAP.get(columnFunction).setChildWrapper(innerWrapper);
        }
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> upper(ColumnFunction<T, ?> columnFunction) {
        return upper(columnFunction, "");
    }

    public static <T> ColumnFunction<T, ?> upper(ColumnFunction<T, ?> columnFunction, ColumnFunction<?, ?> alias) {
        return upper(columnFunction, ColumnUtils.getFieldName(alias));
    }

    public static <T> ColumnFunction<T, ?> upper(ColumnFunction<T, ?> columnFunction, String alias) {
        ColumnWrapper innerWrapper = FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.UPPER, alias));
        if (innerWrapper != null) {
            FUNCTION_MAP.get(columnFunction).setChildWrapper(innerWrapper);
        }
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> lower(ColumnFunction<T, ?> columnFunction) {
        return lower(columnFunction, "");
    }

    public static <T> ColumnFunction<T, ?> lower(ColumnFunction<T, ?> columnFunction, ColumnFunction<?, ?> alias) {
        return lower(columnFunction, ColumnUtils.getFieldName(alias));
    }

    public static <T> ColumnFunction<T, ?> lower(ColumnFunction<T, ?> columnFunction, String alias) {
        ColumnWrapper innerWrapper = FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.LOWER, alias));
        if (innerWrapper != null) {
            FUNCTION_MAP.get(columnFunction).setChildWrapper(innerWrapper);
        }
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> ceil(ColumnFunction<T, ?> columnFunction) {
        return ceil(columnFunction, "");
    }

    public static <T> ColumnFunction<T, ?> ceil(ColumnFunction<T, ?> columnFunction, ColumnFunction<?, ?> alias) {
        return ceil(columnFunction, ColumnUtils.getFieldName(alias));
    }

    public static <T> ColumnFunction<T, ?> ceil(ColumnFunction<T, ?> columnFunction, String alias) {
        ColumnWrapper innerWrapper = FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.CEIL, alias));
        if (innerWrapper != null) {
            FUNCTION_MAP.get(columnFunction).setChildWrapper(innerWrapper);
        }
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> abs(ColumnFunction<T, ?> columnFunction) {
        return abs(columnFunction, "");
    }

    public static <T> ColumnFunction<T, ?> abs(ColumnFunction<T, ?> columnFunction, ColumnFunction<?, ?> alias) {
        return abs(columnFunction, ColumnUtils.getFieldName(alias));
    }

    public static <T> ColumnFunction<T, ?> abs(ColumnFunction<T, ?> columnFunction, String alias) {
        ColumnWrapper innerWrapper = FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.ABS, alias));
        if (innerWrapper != null) {
            FUNCTION_MAP.get(columnFunction).setChildWrapper(innerWrapper);
        }
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> year(ColumnFunction<T, ?> columnFunction) {
        return year(columnFunction, "");
    }

    public static <T> ColumnFunction<T, ?> year(ColumnFunction<T, ?> columnFunction, ColumnFunction<?, ?> alias) {
        return year(columnFunction, ColumnUtils.getFieldName(alias));
    }

    public static <T> ColumnFunction<T, ?> year(ColumnFunction<T, ?> columnFunction, String alias) {
        ColumnWrapper innerWrapper = FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.YEAR, alias));
        if (innerWrapper != null) {
            FUNCTION_MAP.get(columnFunction).setChildWrapper(innerWrapper);
        }
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> month(ColumnFunction<T, ?> columnFunction) {
        return month(columnFunction, "");
    }

    public static <T> ColumnFunction<T, ?> month(ColumnFunction<T, ?> columnFunction, ColumnFunction<?, ?> alias) {
        return month(columnFunction, ColumnUtils.getFieldName(alias));
    }

    public static <T> ColumnFunction<T, ?> month(ColumnFunction<T, ?> columnFunction, String alias) {
        ColumnWrapper innerWrapper = FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.MONTH, alias));
        if (innerWrapper != null) {
            FUNCTION_MAP.get(columnFunction).setChildWrapper(innerWrapper);
        }
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> week(ColumnFunction<T, ?> columnFunction) {
        return week(columnFunction, "");
    }

    public static <T> ColumnFunction<T, ?> week(ColumnFunction<T, ?> columnFunction, ColumnFunction<?, ?> alias) {
        return week(columnFunction, ColumnUtils.getFieldName(alias));
    }

    public static <T> ColumnFunction<T, ?> week(ColumnFunction<T, ?> columnFunction, String alias) {
        ColumnWrapper innerWrapper = FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.WEEK, alias));
        if (innerWrapper != null) {
            FUNCTION_MAP.get(columnFunction).setChildWrapper(innerWrapper);
        }
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> hour(ColumnFunction<T, ?> columnFunction) {
        return hour(columnFunction, "");
    }

    public static <T> ColumnFunction<T, ?> hour(ColumnFunction<T, ?> columnFunction, ColumnFunction<?, ?> alias) {
        return hour(columnFunction, ColumnUtils.getFieldName(alias));
    }

    public static <T> ColumnFunction<T, ?> hour(ColumnFunction<T, ?> columnFunction, String alias) {
        ColumnWrapper innerWrapper = FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.HOUR, alias));
        if (innerWrapper != null) {
            FUNCTION_MAP.get(columnFunction).setChildWrapper(innerWrapper);
        }
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> minute(ColumnFunction<T, ?> columnFunction) {
        return minute(columnFunction, "");
    }

    public static <T> ColumnFunction<T, ?> minute(ColumnFunction<T, ?> columnFunction, ColumnFunction<?, ?> alias) {
        return minute(columnFunction, ColumnUtils.getFieldName(alias));
    }

    public static <T> ColumnFunction<T, ?> minute(ColumnFunction<T, ?> columnFunction, String alias) {
        ColumnWrapper innerWrapper = FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.MINUTE, alias));
        if (innerWrapper != null) {
            FUNCTION_MAP.get(columnFunction).setChildWrapper(innerWrapper);
        }
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> weekday(ColumnFunction<T, ?> columnFunction) {
        return weekday(columnFunction, "");
    }

    public static <T> ColumnFunction<T, ?> weekday(ColumnFunction<T, ?> columnFunction, String alias) {
        ColumnWrapper innerWrapper = FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.WEEK_DAY, alias));
        if (innerWrapper != null) {
            FUNCTION_MAP.get(columnFunction).setChildWrapper(innerWrapper);
        }
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> weekday(ColumnFunction<T, ?> columnFunction, ColumnFunction<?, ?> alias) {
        return weekday(columnFunction, ColumnUtils.getFieldName(alias));
    }

    public static <T> ColumnFunction<T, ?> dayname(ColumnFunction<T, ?> columnFunction) {
        return dayname(columnFunction, "");
    }

    public static <T> ColumnFunction<T, ?> dayname(ColumnFunction<T, ?> columnFunction, String alias) {
        ColumnWrapper innerWrapper = FUNCTION_MAP.put(columnFunction, new ColumnWrapper(SqlFunctionName.DAY_NAME, alias));
        if (innerWrapper != null) {
            FUNCTION_MAP.get(columnFunction).setChildWrapper(innerWrapper);
        }
        return columnFunction;
    }

    public static <T> ColumnFunction<T, ?> dayname(ColumnFunction<T, ?> columnFunction, ColumnFunction<?, ?> alias) {
        return dayname(columnFunction, ColumnUtils.getFieldName(alias));
    }
}
