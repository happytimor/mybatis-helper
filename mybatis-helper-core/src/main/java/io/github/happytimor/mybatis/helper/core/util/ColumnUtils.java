package io.github.happytimor.mybatis.helper.core.util;

import io.github.happytimor.mybatis.helper.core.common.Constants;
import io.github.happytimor.mybatis.helper.core.common.LambdaColumn;
import io.github.happytimor.mybatis.helper.core.metadata.ColumnFunction;
import io.github.happytimor.mybatis.helper.core.metadata.Result;
import io.github.happytimor.mybatis.helper.core.metadata.TableInfo;

import java.beans.Introspector;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chenpeng
 */
public class ColumnUtils {

    /**
     * 获取全部字段
     *
     * @param tableInfo 表映射信息
     * @return 字段信息 `id`,`name`,`age`
     */
    public static String getAllColumnStr(TableInfo tableInfo) {
        StringBuilder stringBuilder = new StringBuilder();

        List<Result> resultList = tableInfo.getResultList();
        for (Result result : resultList) {
            stringBuilder.append("`").append(result.getColumn()).append("`").append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    private static final char littleCharacterStart = 'a';
    private static final char littleCharacterEnd = 'z';

    /**
     * 首字母大写
     *
     * @param str 给定字符串
     * @return 处理完成后的字符串
     */
    public static String makeFirstCharacterUpper(String str) {
        if (str == null || "".equals(str.trim())) {
            return str;
        }
        char[] chars = str.toCharArray();
        char ch = chars[0];
        if (ch >= littleCharacterStart && ch <= littleCharacterEnd) {
            chars[0] -= 32;
            return new String(chars);
        }
        return str;
    }

    private static final char bigCharacterStart = 'A';
    private static final char bigCharacterEnd = 'Z';

    /**
     * 首字母小写
     *
     * @param str 给定字符串
     * @return 处理完成后的字符串
     */
    public static String makeFirstCharacterLower(String str) {
        if (str == null || "".equals(str.trim())) {
            return str;
        }
        char[] chars = str.toCharArray();
        char ch = chars[0];
        if (ch >= bigCharacterStart && ch <= bigCharacterEnd) {
            chars[0] += 32;
            return new String(chars);
        }
        return str;
    }

    /**
     * 驼峰转下划线
     *
     * @param camelCase 驼峰字段名称
     * @return 下划线名称
     */
    public static String camelCaseToUnderscore(String camelCase) {
        if (camelCase == null || camelCase.trim().length() == 0) {
            throw new RuntimeException("camelCaseToUnderscore error, camelCase is empty");
        }

        int len = camelCase.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = camelCase.charAt(i);
            if (Character.isUpperCase(c) && i > 0) {
                sb.append("_");
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }

    private static final Pattern LINE_PATTERN = Pattern.compile("_(\\w)");

    /**
     * underscore 2 camel
     *
     * @param underscore underscore string
     * @return camel string
     */
    public static String underscoreToCamelCase(String underscore) {
        Matcher matcher = LINE_PATTERN.matcher(underscore);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 驼峰转下划线
     *
     * @param column 字段名称
     * @return 下划线名称
     */
    public static String camelCaseToUnderscore(ColumnFunction<?, ?> column) {
        LambdaColumn lambdaColumn = getFieldNameColumn(column);
        return lambdaColumn.isOverrided() ? lambdaColumn.getName() : camelCaseToUnderscore(lambdaColumn.getName());
    }

    /**
     * 获取字段对应名称
     *
     * @param column 字段
     * @return 字段名称
     */
    public static String getFieldName(ColumnFunction<?, ?> column) {
        return getFieldNameColumn(column).getName();
    }

    public static LambdaColumn getFieldNameColumn(ColumnFunction<?, ?> column) {
        try {
            Method method = column.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(Boolean.TRUE);
            SerializedLambda serializedLambda = (SerializedLambda) method.invoke(column);
            String getter = serializedLambda.getImplMethodName();
            String prefix = getter.startsWith("is") ? "is" : "get";
            String fieldName = Introspector.decapitalize(getter.replaceFirst(prefix, ""));
            String implClass = serializedLambda.getImplClass();
            if (!Constants.COLUMN_RELATION.containsKey(implClass)) {
                return new LambdaColumn(fieldName);
            }
            String reflectColumn = Constants.COLUMN_RELATION.get(implClass).get(fieldName);
            return new LambdaColumn(reflectColumn == null ? fieldName : reflectColumn, reflectColumn != null);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取表别名
     *
     * @param clazz     表对应对象类
     * @param appendDot 是否直接"."
     * @param subTable  子表
     * @return 表别名
     */
    public static String getTableAlias(Map<Class<?>, Integer> subTable, Class<?> clazz, boolean appendDot) {
        if (subTable == null || subTable.isEmpty()) {
            return "";
        }
        int index = subTable.getOrDefault(clazz, 1);
        String tableAlias = "t" + index;
        return tableAlias + (appendDot ? "." : "");
    }

    public static String getTableAlias(Map<Class<?>, Integer> subTable, ColumnFunction<?, ?> column) {
        if (subTable == null || subTable.isEmpty() || column == null) {
            return "";
        }
        Class<?> clazz = LambdaUtils.resolve(column).getInstantiatedType();
        int index = subTable.getOrDefault(clazz, 1);
        String tableAlias = "t" + index;
        return tableAlias + ".";
    }

    public static <E> String getColumnName(ColumnFunction<E, ?> column) {
        return getColumnName(column, true);
    }

    public static <E> String getColumnName(ColumnFunction<E, ?> column, boolean wrap) {
        String columnName = ColumnUtils.camelCaseToUnderscore(column);
        return wrap ? "`" + columnName + "`" : columnName;
    }
}