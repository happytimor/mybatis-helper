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
            Integer columnLength = getColumnLength(column.getColumnType());
            if (columnLength >= 500 || arraysContains(GenConstants.COLUMNTYPE_TEXT, dataType)) {
                column.setQueryType(GenConstants.QUERY_LIKE);
            }
        } else if (arraysContains(GenConstants.COLUMNTYPE_TIME, dataType)) {
            column.setJavaType(GenConstants.TYPE_DATE);
        } else if (arraysContains(GenConstants.COLUMNTYPE_NUMBER, dataType)) {
            // 如果是浮点型 统一用BigDecimal
            String[] str = null;
            String columnType = column.getColumnType();
            if (StringUtils.indexOf(columnType, "(") > 0) {
                String precisionStr = StringUtils.substringBetween(columnType, "(", ")");
                if (precisionStr != null) {
                    str = StringUtils.split(precisionStr, ",");
                }
            }
            // 如果是浮点型（带有小数位）
            if (str != null && str.length == 2 && Integer.parseInt(str[1]) > 0) {
                column.setJavaType(GenConstants.TYPE_BIGDECIMAL);
            }
            // 如果是小整数（长度<=10）
            else if (str != null && str.length == 1 && Integer.parseInt(str[0]) <= 10) {
                column.setJavaType(GenConstants.TYPE_INTEGER);
            }
            // 对于没有括号的类型（如int, integer, bigint等）或大整数
            else {
                // 直接根据数据类型判断
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
