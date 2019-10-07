package io.github.happytimor.mybatis.helper.core.util;

import io.github.happytimor.mybatis.helper.core.metadata.Result;
import io.github.happytimor.mybatis.helper.core.metadata.TableInfo;

import java.util.List;

/**
 * @author chenpeng
 * @date 2019-09-08
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
        if (ch >= 'a' && ch <= 'z') {
            chars[0] -= 32;
            return new String(chars);
        }
        return str;
    }

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
        if (ch >= 'A' && ch <= 'Z') {
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

    public static void main(String... args) {

        System.out.println(ColumnUtils.camelCaseToUnderscore("UserNoKey"));
    }
}
