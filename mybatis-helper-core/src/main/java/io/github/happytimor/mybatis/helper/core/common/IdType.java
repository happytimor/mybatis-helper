package io.github.happytimor.mybatis.helper.core.common;

/**
 * @author chenpeng
 */
public enum IdType {
    /**
     * 数据库自增
     */
    AUTO,
    /**
     * 动态生成, 需要自定义主键生成算法(实现 IdentifierGenerator 接口)
     */
    DYNAMIC_GENERATE;
}
