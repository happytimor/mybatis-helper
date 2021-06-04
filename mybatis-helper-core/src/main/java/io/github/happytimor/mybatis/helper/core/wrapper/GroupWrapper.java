package io.github.happytimor.mybatis.helper.core.wrapper;

import io.github.happytimor.mybatis.helper.core.metadata.ColumnFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chenpeng
 */
public class GroupWrapper<T> extends HavingWrapper<T> {
    /**
     * 分组字段
     */
    private final List<ColumnFunction<T, ?>> groupList = new ArrayList<>();

    /**
     * 分组
     *
     * @param columnFunctions 字段function
     * @return GroupWrapper
     */
    @SafeVarargs
    public final GroupWrapper<T> groupBy(ColumnFunction<T, ?>... columnFunctions) {
        super.hasGrouping = true;
        groupList.addAll(Arrays.asList(columnFunctions));
        return this;
    }

    /**
     * 获取分组Segment
     *
     * @return 分组segment
     */
    public String getGroupSegment() {
        if (groupList.isEmpty()) {
            return "";
        }
        return " GROUP BY " + groupList.stream().map(column -> this.parseColumnName(column, true))
                .collect(Collectors.joining(","));
    }
}
