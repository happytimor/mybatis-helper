package io.github.happytimor.mybatis.helper.core.wrapper;

import io.github.happytimor.mybatis.helper.core.metadata.ColumnFunction;
import io.github.happytimor.mybatis.helper.core.util.ColumnUtils;

import java.beans.Introspector;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;

/**
 * @author chenpeng
 */
public abstract class AbstractWrapper<T> {

    protected String getColumnName(ColumnFunction<T, ?> column) {
        return getColumnName(column, true);
    }

    protected String getColumnName(ColumnFunction<T, ?> column, boolean wrap) {
        String columnName = ColumnUtils.camelCaseToUnderscore(this.getFieldName(column));
        return wrap ? "`" + columnName + "`" : columnName;
    }

    protected String wrapColumnName(String columnName) {
        return "`" + columnName + "`";
    }

    private String getFieldName(ColumnFunction<T, ?> column) {
        try {
            Method method = column.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(Boolean.TRUE);
            SerializedLambda serializedLambda = (SerializedLambda) method.invoke(column);
            String getter = serializedLambda.getImplMethodName();
            String prefix = getter.startsWith("is") ? "is" : "get";
            return Introspector.decapitalize(getter.replace(prefix, ""));
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}
