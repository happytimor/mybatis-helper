package com.github.happytimor.mybatis.helper.core.metadata;

import java.io.Serializable;
import java.util.function.Function;

/**
 * @author chenpeng
 * @date 2019-08-26
 */
@FunctionalInterface
public interface ColumnFunction<T, R> extends Function<T, R>, Serializable {
}
