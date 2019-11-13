package io.github.happytimor.mybatis.helper.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

/**
 * @author chenpeng
 */
public class ReflectUtils {
    /**
     * map转object
     *
     * @param map map数据
     * @param clz 对象class
     * @param <M> 对象类型声明
     * @return 对象
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
