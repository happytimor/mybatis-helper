package io.github.happytimor.mybatis.helper.core.annotation;

import java.lang.annotation.*;

/**
 * 数据库字段注解
 *
 * @author chenpeng
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface TableColumn {
    /**
     * 对应数据库字段名
     *
     * @return 数据库字段名
     */
    String value() default "";

    /**
     * 是否存在于数据库
     *
     * @return true-存在 false-不存在
     */
    boolean exist() default true;

    /**
     * 是否是主键
     */
    boolean primaryKey() default false;
}
