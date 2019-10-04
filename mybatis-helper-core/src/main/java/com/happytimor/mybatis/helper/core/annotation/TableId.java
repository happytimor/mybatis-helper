package com.happytimor.mybatis.helper.core.annotation;

import java.lang.annotation.*;

/**
 * 数据表主键注解
 *
 * @author chenpeng
 * @date 2019-08-21
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface TableId {
    String value() default "";
}
