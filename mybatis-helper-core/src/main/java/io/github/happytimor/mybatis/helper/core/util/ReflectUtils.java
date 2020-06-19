package io.github.happytimor.mybatis.helper.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

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
            M obj = clz.newInstance();
            Field[] declaredFields = obj.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                field.setAccessible(true);
                field.set(obj, map.get(field.getName()));
            }
            return obj;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
