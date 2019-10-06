package io.github.happytimor.mybatis.helper.core.annotation;

import java.lang.annotation.*;

/**
 * 分表连接符
 *
 * @author chenpeng
 * @date 2019-08-21
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface MultipleTableConnector {
    String value() default "_";
}
