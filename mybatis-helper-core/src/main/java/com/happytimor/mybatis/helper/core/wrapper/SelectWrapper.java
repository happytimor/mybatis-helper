package com.happytimor.mybatis.helper.core.wrapper;


import com.happytimor.mybatis.helper.core.metadata.ColumnFunction;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author chenpeng
 * @date 2019-08-26
 */
public class SelectWrapper<T> extends WhereWrapper<T> {

    private String selectSegment = "*";

    /**
     * 获取select片段
     *
     * @return select片段
     */
    public String getSelectSegment() {
        return selectSegment;
    }

    @SafeVarargs
    public final WhereWrapper<T> select(ColumnFunction<T, ?>... functions) {
        selectSegment = Arrays.stream(functions).map(this::getColumnName).collect(Collectors.joining(","));
        return this;
    }

    public final WhereWrapper<T> select(String... columns) {
        selectSegment = String.join(",", columns);
        return this;
    }
}
