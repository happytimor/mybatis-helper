package com.happytimor.mybatis.helper.core.annotation;

import java.lang.annotation.*;

/**
 * 数据库字段注解
 *
 * @author chenpeng
 * @date 2019-08-21
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface TableField {
    /**
     * 对应数据库字段名
     */
    String value() default "";

    /**
     * 是否存在于数据库
     */
    boolean exist() default true;
}
