package io.github.happytimor.mybatis.helper.core.util;

import io.github.happytimor.mybatis.helper.core.annotation.TableColumn;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author chenpeng
 */
public class ReflectUtils {
    /**
     * transfer map to object
     *
     * @param map map data
     * @param clz object class
     * @param <M> result model
     * @return result object
     */
    public static <M> M map2Obj(Map<String, Object> map, Class<M> clz) {
        try {
            if (map == null || map.isEmpty()) {
                return null;
            }
            if (Integer.class.getName().equals(clz.getName())
                    || String.class.getName().equals(clz.getName())
                    || Long.class.getName().equals(clz.getName())
                    || Double.class.getName().equals(clz.getName())
                    || Float.class.getName().equals(clz.getName())
                    || Short.class.getName().equals(clz.getName())
                    || Number.class.getName().equals(clz.getName())
                    || BigDecimal.class.getName().equals(clz.getName())
                    || Date.class.getName().equals(clz.getName())
                    || LocalDate.class.getName().equals(clz.getName())
                    || Timestamp.class.getName().equals(clz.getName())
                    || LocalDateTime.class.getName().equals(clz.getName())
                    || Boolean.class.getName().equals(clz.getName())) {
                return (M) castIfNotNull(map.values().toArray()[0], clz);
            }
            Map<String, Object> extraMap = new HashMap<>();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String column = entry.getKey();
                if (column.contains("_") && entry.getValue() != null) {
                    //下划线自动转驼峰
                    extraMap.put(ColumnUtils.underscoreToCamelCase(column), map.get(column));
                }
            }
            M obj = clz.newInstance();
            Field[] declaredFields = obj.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                field.setAccessible(true);
                TableColumn tableColumn = field.getAnnotation(TableColumn.class);
                if (tableColumn != null && !"".equals(tableColumn.value())) {
                    field.set(obj, map.get(tableColumn.value()));
                    continue;
                }
                Object object = map.get(field.getName());
                Object returnObject = (object != null) ? object : extraMap.get(field.getName());
                field.set(obj, castIfNotNull(returnObject, field.getType()));
            }
            return obj;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object castIfNotNull(Object returnObject, Class<?> type) {
        if (returnObject == null) {
            return null;
        }
        if (returnObject.getClass() == java.lang.Long.class && type == java.lang.Integer.class) {
            return ((Long) returnObject).intValue();
        }
        if (returnObject.getClass() == java.math.BigDecimal.class) {
            if (type == java.lang.Long.class) {
                return ((BigDecimal) returnObject).longValue();
            }
            if (type == java.lang.Integer.class) {
                return ((BigDecimal) returnObject).intValue();
            }
        }
        if (returnObject.getClass() == java.lang.Integer.class && type == java.lang.Boolean.class) {
            return (Integer) returnObject == 1;
        }
        if (returnObject.getClass() == java.sql.Timestamp.class) {
            if (type == java.time.LocalDateTime.class) {
                return ((Timestamp) returnObject).toLocalDateTime();
            }
            if (type == java.time.LocalDate.class) {
                return ((Timestamp) returnObject).toLocalDateTime().toLocalDate();
            }
            if (type == java.util.Date.class) {
                return new Date(((Timestamp) returnObject).getTime());
            }
        }
        return type.cast(returnObject);
    }
}
