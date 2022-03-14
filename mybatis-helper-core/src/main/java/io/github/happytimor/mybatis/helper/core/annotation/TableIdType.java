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
     * 主键id类型
     *
     * @return 主键策略
     */
    IdType value() default IdType.DYNAMIC_GENERATE;

    /**
     * 区分id
     *
     * @return string类型的区分id, 可以针对不同的表，给不同的增长策略
     */
    String identity();
}
