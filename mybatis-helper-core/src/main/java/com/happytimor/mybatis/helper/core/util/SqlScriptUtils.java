package com.happytimor.mybatis.helper.core.util;

/**
 * @author chenpeng
 * @date 2019-08-22
 */
public class SqlScriptUtils {
    private static final String QUOTE = "\"";
    private static final String NEWLINE = "\n";
    private static final String RIGHT_CHEV = ">";

    /**
     * <p>
     * 生成 foreach 标签的脚本
     * </p>
     *
     * @param sqlScript  foreach 内部的 sql 脚本
     * @param collection collection
     * @param index      index
     * @param item       item
     * @param separator  separator
     * @return foreach 脚本
     */
    public static String convertForeach(final String sqlScript, final String collection, String open, String close, final String index, final String item, final String separator) {
        StringBuilder sb = new StringBuilder("<foreach");
        if (isNotEmpty(collection)) {
            sb.append(" collection=\"").append(collection).append(QUOTE);
        }
        if (isNotEmpty(index)) {
            sb.append(" index=\"").append(index).append(QUOTE);
        }
        if (isNotEmpty(open)) {
            sb.append(" open=\"").append(open).append(QUOTE);
        }
        if (isNotEmpty(close)) {
            sb.append(" close=\"").append(close).append(QUOTE);
        }
        if (isNotEmpty(item)) {
            sb.append(" item=\"").append(item).append(QUOTE);
        }
        if (isNotEmpty(separator)) {
            sb.append(" separator=\"").append(separator).append(QUOTE);
        }
        return sb.append(RIGHT_CHEV).append(NEWLINE).append(sqlScript).append(NEWLINE).append("</foreach>").toString();
    }


    /**
     * <p>
     * 获取 带 trim 标签的脚本
     * </p>
     *
     * @param prefix          以...开头
     * @param suffix          以...结尾
     * @param suffixOverrides 干掉最后一个...
     * @param sqlScript       sql 脚本片段
     * @return trim 脚本
     */
    public static String wrapTrim(final String sqlScript, final String prefix, final String suffix, final String suffixOverrides) {
        StringBuilder sb = new StringBuilder("<trim");
        if (isNotEmpty(prefix)) {
            sb.append(" prefix=\"").append(prefix).append(QUOTE);
        }
        if (isNotEmpty(suffix)) {
            sb.append(" suffix=\"").append(suffix).append(QUOTE);
        }
        if (isNotEmpty(suffixOverrides)) {
            sb.append(" suffixOverrides=\"").append(suffixOverrides).append(QUOTE);
        }
        return sb.append(RIGHT_CHEV).append(NEWLINE).append(sqlScript).append(NEWLINE).append("</trim>").toString();
    }

    private static boolean isNotEmpty(String str) {
        return str != null && str.length() != 0;
    }
}
