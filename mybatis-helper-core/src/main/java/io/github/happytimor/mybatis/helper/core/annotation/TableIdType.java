package io.github.happytimor.mybatis.helper.core.annotation;

import io.github.happytimor.mybatis.helper.core.common.IdType;

import java.lang.annotation.*;

/**
 * @author chenpeng
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface TableIdType {
    /**
     * primary key type
     *
     * @return 主键策略
     */
    IdType value() default IdType.DYNAMIC_GENERATE;

    /**
     * identity id for primary key.
     * can be used to return different value for primary key
     *
     * @return identify id
     */
    String identity();
}
