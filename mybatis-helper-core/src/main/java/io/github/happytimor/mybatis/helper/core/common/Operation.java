package io.github.happytimor.mybatis.helper.core.common;

import io.github.happytimor.mybatis.helper.core.metadata.ColumnFunction;
import io.github.happytimor.mybatis.helper.core.util.ColumnUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * operation sql: update user set grade = grade_of_math + grade_of_science;
 *
 * @author chenpeng
 */
public class Operation<T> {

    private final List<String> list = new ArrayList<>();

    public Operation() {

    }

    public Operation(ColumnFunction<T, ?> column) {
        list.add(String.format("`%s`", ColumnUtils.camelCaseToUnderscore(column)));
    }

    public Operation<T> plus(ColumnFunction<T, ?> column) {
        if (!list.isEmpty()) {
            list.add("+");
        }
        list.add(String.format("`%s`", ColumnUtils.camelCaseToUnderscore(column)));
        return this;
    }

    public Operation<T> plus(ColumnFunction<T, ?> column1, ColumnFunction<T, ?> column2) {
        if (!list.isEmpty()) {
            list.add("+");
        }
        list.add(String.format("`%s`", ColumnUtils.camelCaseToUnderscore(column1)));
        list.add("+");
        list.add(String.format("`%s`", ColumnUtils.camelCaseToUnderscore(column2)));
        return this;
    }

    public Operation<T> plus(Number value) {
        if (list.isEmpty()) {
            throw new RuntimeException("incorrect usage of plus a number");
        }
        list.add("+");
        list.add(value.toString());
        return this;
    }

    public Operation<T> minus(ColumnFunction<T, ?> column) {
        if (!list.isEmpty()) {
            list.add("-");
        }
        list.add(String.format("`%s`", ColumnUtils.camelCaseToUnderscore(column)));
        return this;
    }

    public Operation<T> minus(ColumnFunction<T, ?> column1, ColumnFunction<T, ?> column2) {
        if (!list.isEmpty()) {
            list.add("-");
        }
        list.add(String.format("`%s`", ColumnUtils.camelCaseToUnderscore(column1)));
        list.add("-");
        list.add(String.format("`%s`", ColumnUtils.camelCaseToUnderscore(column2)));
        return this;
    }

    public Operation<T> minus(Number value) {
        if (list.isEmpty()) {
            throw new RuntimeException("incorrect usage of plus a number");
        }
        list.add("-");
        list.add(value.toString());
        return this;
    }

    public String getOperationStr() {
        return String.join(" ", list);
    }
}
