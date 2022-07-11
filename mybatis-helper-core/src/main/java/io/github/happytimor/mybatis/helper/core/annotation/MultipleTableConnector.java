package io.github.happytimor.mybatis.helper.core.annotation;

import java.lang.annotation.*;

/**
 * split table connector
 *
 * @author chenpeng
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface MultipleTableConnector {
    String value() default "_";
}
