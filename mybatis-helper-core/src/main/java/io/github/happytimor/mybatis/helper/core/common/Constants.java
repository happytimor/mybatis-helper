package io.github.happytimor.mybatis.helper.core.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chenpeng
 */
public interface Constants {
    /**
     * 默认数据库主键
     */
    String DEFAULT_KEY_COLUMN = "id";
    /**
     * 默认主键在对象的映射字段
     */
    String DEFAULT_KEY_PROPERTY = "id";

    /**
     * 字段映射关系 io.github.happytimor.mybatis.helper.single.database.test.domain.User.strangeName -> aaa
     */
    Map<String, Map<String, String>> COLUMN_RELATION = new ConcurrentHashMap<>();
}
