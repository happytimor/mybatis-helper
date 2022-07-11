package io.github.happytimor.mybatis.helper.core.annotation;

import java.lang.annotation.*;

/**
 * database table column annotation
 *
 * @author chenpeng
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface TableColumn {
    /**
     * column name
     *
     * @return marked java column to database column
     */
    String value() default "";

    /**
     * set if the column exist
     *
     * @return false for non-exist column(just for business logic)
     */
    boolean exist() default true;

    /**
     * set primark key
     *
     * @return true-is a primaryKey, false-is not a primary key
     */
    boolean primaryKey() default false;
}
