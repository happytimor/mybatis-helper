package io.github.happytimor.mybatis.helper.generator.constant;

/**
 * 代码生成通用常量
 *
 * @author gaomingyuan
 */
public class GenConstants {

    /**
     * 数据库字符串类型
     */
    public static final String[] COLUMNTYPE_STR = {"char", "varchar", "nvarchar", "varchar2"};

    /**
     * 数据库文本类型
     */
    public static final String[] COLUMNTYPE_TEXT = {"tinytext", "text", "mediumtext", "longtext"};

    /**
     * 数据库时间类型
     */
    public static final String[] COLUMNTYPE_TIME = {"datetime", "time", "date", "timestamp"};

    /**
     * 数据库数字类型
     */
    public static final String[] COLUMNTYPE_NUMBER = {"tinyint", "smallint", "mediumint", "int", "number", "integer",
            "bit", "bigint", "float", "double", "decimal"};

    /**
     * 字符串类型
     */
    public static final String TYPE_STRING = "String";

    /**
     * 整型
     */
    public static final String TYPE_INTEGER = "Integer";

    /**
     * 长整型
     */
    public static final String TYPE_LONG = "Long";

    /**
     * 浮点型
     */
    public static final String TYPE_DOUBLE = "Double";

    /**
     * 高精度计算类型
     */
    public static final String TYPE_BIGDECIMAL = "BigDecimal";

    /**
     * 时间类型
     */
    public static final String TYPE_DATE = "LocalDateTime";

    /**
     * 模糊查询
     */
    public static final String QUERY_LIKE = "LIKE";

    /**
     * 相等查询
     */
    public static final String QUERY_EQ = "EQ";
}
