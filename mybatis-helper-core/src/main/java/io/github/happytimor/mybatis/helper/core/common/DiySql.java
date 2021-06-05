package io.github.happytimor.mybatis.helper.core.common;

import io.github.happytimor.mybatis.helper.core.metadata.ColumnFunction;
import io.github.happytimor.mybatis.helper.core.util.ColumnUtils;

import java.util.*;

/**
 * 构建自定义sql: update user set grade = grade_of_math + grade_of_science;
 *
 * @author chenpeng
 */
public class DiySql<T> {

    private String expression;
    private final List<ColumnFunction<?, ?>> list = new ArrayList<>();
    /**
     * JoinWrapper
     */
    protected Map<Class<?>, Integer> subTable = new HashMap<>();

    public DiySql() {

    }

    public DiySql(Map<Class<?>, Integer> subTable) {
        this.subTable = subTable;
    }

    public DiySql<T> setExpression(String expression) {
        this.expression = expression;
        return this;
    }

    @SafeVarargs
    public final DiySql<T> setColumns(ColumnFunction<T, ?>... columns) {
        list.addAll(Arrays.asList(columns));
        return this;
    }

    public String getSql() {
        if (list.isEmpty()) {
            return expression;
        }
        for (ColumnFunction<?, ?> column : list) {
            String tableAlias = ColumnUtils.getTableAlias(subTable, column);
            expression = expression.replaceFirst("\\{}", tableAlias + ColumnUtils.getColumnName(column));
        }
        return expression;
    }

    public String getExpression() {
        return expression;
    }

    public List<ColumnFunction<?, ?>> getList() {
        return list;
    }
}
