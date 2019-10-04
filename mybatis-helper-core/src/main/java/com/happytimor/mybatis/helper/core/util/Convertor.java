package com.happytimor.mybatis.helper.core.util;

/**
 * @author chenpeng
 * @date 2019-08-25
 */
public class Convertor {
    public static String propertyToColumn(String property) {
        if (property == null || property.trim().length() == 0) {
            throw new RuntimeException("propertyToColumn error, property is empty");
        }

        int len = property.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = property.charAt(i);
            if (Character.isUpperCase(c) && i > 0) {
                sb.append("_");
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }
}
