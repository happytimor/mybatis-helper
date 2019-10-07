package io.github.happytimor.mybatis.helper.core.wrapper;


import io.github.happytimor.mybatis.helper.core.metadata.ColumnFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenpeng
 */
public class UpdateWrapper<T> extends WhereWrapper<T> {

    private List<String> set = new ArrayList<>();

    public UpdateWrapper<T> set(boolean executeIf, ColumnFunction<T, ?> column, Object value) {
        if (executeIf) {
            if (value instanceof String) {
                set.add(this.getColumnName(column) + "=\"" + value + "\"");
            } else {
                set.add(this.getColumnName(column) + "=" + value);
            }
        }
        return this;
    }

    public UpdateWrapper<T> set(ColumnFunction<T, ?> column, Object value) {
        return this.set(true, column, value);
    }

    public String getSetSegment() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SET ");
        for (String s : set) {
            stringBuilder.append(s).append(", ");
        }
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        return stringBuilder.toString();
    }
}
