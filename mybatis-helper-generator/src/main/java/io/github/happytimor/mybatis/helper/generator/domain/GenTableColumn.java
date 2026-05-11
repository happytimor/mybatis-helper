package io.github.happytimor.mybatis.helper.generator.domain;

import io.github.happytimor.mybatis.helper.generator.util.StringUtils;
import lombok.Data;

/**
 * 表字段信息
 *
 * @author gaomingyuan
 */
@Data
public class GenTableColumn {

    /**
     * 列名称
     */
    private String columnName;

    /**
     * 列描述
     */
    private String columnComment;

    /**
     * 列类型
     */
    private String columnType;

    /**
     * JAVA类型
     */
    private String javaType;

    /**
     * JAVA字段名
     */
    private String javaField;

    /**
     * 是否主键
     */
    private boolean pk;

    /**
     * 查询方式（EQ等于、NE不等于、GT大于、LT小于、LIKE模糊、BETWEEN范围）
     */
    private String queryType;

    public String getCapJavaField() {
        return StringUtils.capitalize(javaField);
    }
}