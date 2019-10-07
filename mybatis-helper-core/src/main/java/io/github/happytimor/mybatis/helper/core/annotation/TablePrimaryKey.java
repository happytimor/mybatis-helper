package io.github.happytimor.mybatis.helper.core.annotation;

import java.lang.annotation.*;

/**
 * 数据表主键注解
 *
 * @author chenpeng
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface TablePrimaryKey {
    String value() default "";
}
