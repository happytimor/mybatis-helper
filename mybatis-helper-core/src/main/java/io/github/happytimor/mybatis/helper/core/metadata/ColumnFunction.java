package io.github.happytimor.mybatis.helper.core.metadata;

import java.io.Serializable;
import java.util.function.Function;

/**
 * @author chenpeng
 */
@FunctionalInterface
public interface ColumnFunction<T, R> extends Function<T, R>, Serializable {

}
