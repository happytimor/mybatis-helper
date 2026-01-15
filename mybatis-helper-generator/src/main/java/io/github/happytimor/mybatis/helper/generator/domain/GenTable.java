package io.github.happytimor.mybatis.helper.generator.domain;

import lombok.Data;

import java.util.List;

/**
 * 表信息
 *
 * @author gaomingyuan
 */
@Data
public class GenTable {
    /**
     * 表名称
     */
    private String tableName;

    /**
     * 表描述
     */
    private String tableComment;

    /**
     * 实体类名称(首字母大写)
     */
    private String className;

    /**
     * 生成包路径
     */
    private String packageName;

    /**
     * 生成功能名
     */
    private String functionName;

    /**
     * 生成作者
     */
    private String functionAuthor;

    /**
     * 生成代码方式（0zip压缩包 1自定义路径）
     */
    private String genType;

    /**
     * 生成路径（不填默认项目路径）
     */
    private String genPath;

    /**
     * 主键信息
     */
    private GenTableColumn pkColumn;

    /**
     * 表列信息
     */
    private List<GenTableColumn> columns;

}