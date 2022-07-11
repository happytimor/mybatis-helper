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
     * default primary key column name
     */
    String DEFAULT_KEY_COLUMN = "id";
    /**
     * default primary key name in java
     */
    String DEFAULT_KEY_PROPERTY = "id";
    /**
     * left join keyword
     */
    String LEFT_JOIN = "LEFT JOIN";
    /**
     * right join keyword
     */
    String RIGHT_JOIN = "RIGHT JOIN";
    /**
     * inner join keyword
     */
    String INNER_JOIN = "INNER JOIN";
    /**
     * join keyword
     */
    String JOIN = "JOIN";
    /**
     * dot
     */
    String DOT = ".";

    /**
     * relation map: io.github.happytimor.mybatis.helper.single.database.test.domain.User.strangeName -&gt; aaa
     */
    Map<String, Map<String, String>> COLUMN_RELATION = new ConcurrentHashMap<>();

    ThreadLocal<Map<ColumnFunction<?, ?>, ColumnWrapper>> THREAD_COLUMN_FUNCTION = new ThreadLocal<>();
}
