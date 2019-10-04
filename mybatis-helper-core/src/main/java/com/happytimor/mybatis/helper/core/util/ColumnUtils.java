package com.happytimor.mybatis.helper.core.util;

import com.happytimor.mybatis.helper.core.metadata.Result;
import com.happytimor.mybatis.helper.core.metadata.TableInfo;

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
}
