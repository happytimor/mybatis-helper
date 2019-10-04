package com.happytimor.mybatis.helper.core.annotation;

import java.lang.annotation.*;

/**
 * 数据库表名注解
 *
 * @author chenpeng
 * @date 2019-08-21
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface TableName {
    String value() default "";
}
