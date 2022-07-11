package io.github.happytimor.mybatis.helper.core.annotation;

import java.lang.annotation.*;

/**
 * database table name annotation
 *
 * @author chenpeng
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface TableName {
    String value() default "";
}
