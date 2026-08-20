package io.github.happytimor.mybatis.helper.generator.util;

import io.github.happytimor.mybatis.helper.generator.config.GlobalConfig;
import io.github.happytimor.mybatis.helper.generator.constant.GenConstants;
import io.github.happytimor.mybatis.helper.generator.domain.GenTableColumn;
import org.apache.commons.lang3.RegExUtils;

import java.util.Arrays;

/**
 * 代码生成器 工具类
 *
 * @author gaomingyuan
 */
public class GenUtils {

    /**
     * 初始化列属性字段
     */
    public static void initColumnField(GenTableColumn column) {
        String dataType = getDbType(column.getColumnType());
        String columnName = column.getColumnName();
        // 设置java字段名
        column.setJavaField(StringUtils.toCamelCase(columnName));
        // 设置默认类型
        column.setJavaType(GenConstants.TYPE_STRING);
        column.setQueryType(GenConstants.QUERY_EQ);

        if (arraysContains(GenConstants.COLUMNTYPE_STR, dataType) || arraysContains(GenConstants.COLUMNTYPE_TEXT, dataType)) {
            // 字符串长度超过500设置为文本域
            Integer columnLength = column.getColumnSize() != null
                    ? column.getColumnSize() : getColumnLength(column.getColumnType());
            if (columnLength >= 500 || arraysContains(GenConstants.COLUMNTYPE_TEXT, dataType)) {
                column.setQueryType(GenConstants.QUERY_LIKE);
            }
        } else if (arraysContains(GenConstants.COLUMNTYPE_TIME, dataType)) {
            column.setJavaType(GenConstants.TYPE_DATE);
        } else if (arraysContains(GenConstants.COLUMNTYPE_NUMBER, dataType)) {
            Integer columnSize = column.getColumnSize();
            Integer decimalDigits = column.getDecimalDigits();
            boolean decimalType = arraysContains(new String[]{"number", "numeric", "float", "double", "real", "decimal"}, dataType);
            // 浮点和高精度数字统一使用BigDecimal，避免精度丢失
            if (decimalType || (decimalDigits != null && decimalDigits > 0)) {
                column.setJavaType(GenConstants.TYPE_BIGDECIMAL);
            }
            // bigint始终使用Long，其余不超过10位的整数使用Integer
            else if (!"bigint".equals(dataType) && columnSize != null && columnSize <= 10) {
                column.setJavaType(GenConstants.TYPE_INTEGER);
            }
            else {
                if (arraysContains(new String[]{"tinyint", "smallint", "mediumint", "int", "integer"}, dataType)) {
                    column.setJavaType(GenConstants.TYPE_INTEGER);
                } else {
                    column.setJavaType(GenConstants.TYPE_LONG);
                }
            }
        }

        // 查询字段类型
        if (StringUtils.endsWithIgnoreCase(columnName, "name")) {
            column.setQueryType(GenConstants.QUERY_LIKE);
        }
    }

    /**
     * 校验数组是否包含指定值
     *
     * @param arr         数组
     * @param targetValue 值
     * @return 是否包含
     */
    public static boolean arraysContains(String[] arr, String targetValue) {
        return Arrays.asList(arr).contains(targetValue);
    }

    /**
     * 表名转换成Java类名
     *
     * @param tableName 表名称
     * @return 类名
     */
    public static String convertClassName(GlobalConfig globalConfig, String tableName) {
        boolean autoRemovePre = globalConfig.isAutoRemovePre();
        String tablePrefix = globalConfig.getTablePrefix();
        if (autoRemovePre && StringUtils.isNotEmpty(tablePrefix)) {
            String[] searchList = StringUtils.split(tablePrefix, ",");
            tableName = replaceFirst(tableName, searchList);
        }
        return StringUtils.convertToCamelCase(tableName);
    }

    /**
     * 批量替换前缀
     *
     * @param replacementm 替换值
     * @param searchList   替换列表
     * @return
     */
    public static String replaceFirst(String replacementm, String[] searchList) {
        String text = replacementm;
        for (String searchString : searchList) {
            if (replacementm.startsWith(searchString)) {
                text = replacementm.replaceFirst(searchString, "");
                break;
            }
        }
        return text;
    }

    /**
     * 关键字替换
     *
     * @param text 需要被替换的名字
     * @return 替换后的名字
     */
    public static String replaceText(String text) {
        // (?:表|xxx)
        return RegExUtils.replaceAll(text, "(?:表)", "");
    }

    /**
     * 获取数据库类型字段
     *
     * @param columnType 列类型
     * @return 截取后的列类型
     */
    public static String getDbType(String columnType) {
        String type = columnType;
        // 处理带括号的类型，如 int(11)
        if (StringUtils.indexOf(type, "(") > 0) {
            type = StringUtils.substringBefore(type, "(");
        }
        // 处理unsigned类型，如 int unsigned
        if (StringUtils.endsWithIgnoreCase(type, " unsigned")) {
            type = StringUtils.substringBeforeLast(type, " ");
        }
        return type.toLowerCase();
    }

    /**
     * 获取字段长度
     *
     * @param columnType 列类型
     * @return 截取后的列类型
     */
    public static Integer getColumnLength(String columnType) {
        if (StringUtils.indexOf(columnType, "(") > 0) {
            String length = StringUtils.substringBetween(columnType, "(", ")");
            return Integer.valueOf(length);
        } else {
            return 0;
        }
    }
}
