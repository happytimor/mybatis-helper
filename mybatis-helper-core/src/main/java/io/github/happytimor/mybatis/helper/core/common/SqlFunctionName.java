package io.github.happytimor.mybatis.helper.core.common;

/**
 * sql function name dic
 *
 * @author chenpeng
 */
public interface SqlFunctionName {
    String AS = "AS";
    String DISTINCT = "DISTINCT";
    /**
     * 文本替换
     */
    String REPLACE = "REPLACE";
    String MAX = "MAX";
    String MIN = "MIN";
    String AVG = "AVG";
    String SUM = "SUM";
    /**
     * date_format
     */
    String DATE_FORMAT = "DATE_FORMAT";
    String COUNT = "COUNT";
    String LENGTH = "LENGTH";
    String UPPER = "UPPER";
    String LOWER = "LOWER";
    String CEIL = "CEIL";
    String ABS = "ABS";
    String YEAR = "YEAR";
    String MONTH = "MONTH";
    String WEEK = "WEEK";
    String HOUR = "HOUR";
    String MINUTE = "MINUTE";
    String WEEK_DAY = "WEEKDAY";
    String DAY_NAME = "DAYNAME";

}
