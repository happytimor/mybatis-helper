package io.github.happytimor.mybatis.helper.core.common;

import io.github.happytimor.mybatis.helper.core.metadata.ColumnFunction;
import io.github.happytimor.mybatis.helper.core.metadata.ColumnWrapper;

import java.util.HashMap;
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

    String LEFT_JOIN = "LEFT JOIN";
    String RIGHT_JOIN = "RIGHT JOIN";
    String INNER_JOIN = "INNER JOIN";
    String JOIN = "JOIN";


    /**
     * 字段映射关系 io.github.happytimor.mybatis.helper.single.database.test.domain.User.strangeName -&gt; aaa
     */
    Map<String, Map<String, String>> COLUMN_RELATION = new ConcurrentHashMap<>();

    ThreadLocal<Map<ColumnFunction<?, ?>, ColumnWrapper>> THREAD_COLUMN_FUNCTION = new ThreadLocal<>();
}
