package io.github.happytimor.mybatis.helper.core.common;

/**
 * @author chenpeng
 */
public enum IdType {
    /**
     * aoto increasement id
     */
    AUTO,
    /**
     * dynamic generate id (@see io.github.happytimor.mybatis.helper.core.service.IdentifierGenerator)
     */
    DYNAMIC_GENERATE;
}
