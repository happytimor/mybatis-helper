package io.github.happytimor.mybatis.helper.core.util;

import io.github.happytimor.mybatis.helper.core.annotation.MultipleTableConnector;
import io.github.happytimor.mybatis.helper.core.annotation.TableColumn;
import io.github.happytimor.mybatis.helper.core.annotation.TableIdType;
import io.github.happytimor.mybatis.helper.core.annotation.TableName;
import io.github.happytimor.mybatis.helper.core.common.IdType;
import io.github.happytimor.mybatis.helper.core.metadata.ColumnFunction;
import io.github.happytimor.mybatis.helper.core.metadata.Result;
import io.github.happytimor.mybatis.helper.core.metadata.SerializedLambda;
import io.github.happytimor.mybatis.helper.core.metadata.TableInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.WeakReference;
import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chenpeng
 */
public final class LambdaUtils {
    private final static Logger logger = LoggerFactory.getLogger(LambdaUtils.class);

    private static final Map<String, WeakReference<SerializedLambda>> FUNC_CACHE = new ConcurrentHashMap<>();
    private static final Map<Class<?>, TableInfo> TABLE_INFO_CACHE = new ConcurrentHashMap<>();

    public static SerializedLambda resolve(ColumnFunction<?, ?> func) {
        Class<?> clazz = func.getClass();
        String name = clazz.getName();
        return Optional.ofNullable(FUNC_CACHE.get(name))
                .map(WeakReference::get)
                .orElseGet(() -> {
                    SerializedLambda lambda = SerializedLambda.resolve(func);
                    FUNC_CACHE.put(name, new WeakReference<>(lambda));
                    return lambda;
                });
    }

    /**
     * 提取泛型模型,多泛型的时候请将泛型T放在第一位
     *
     * @param mapperClass mapper 接口
     * @return mapper 泛型
     */
    public static Class<?> extractModelClass(Class<?> mapperClass) {
        Type[] types = mapperClass.getGenericInterfaces();
        ParameterizedType target = null;
        for (Type type : types) {
            if (type instanceof ParameterizedType) {
                Type[] typeArray = ((ParameterizedType) type).getActualTypeArguments();
                if (typeArray != null && typeArray.length > 0) {
                    Type t = typeArray[0];
                    if (t instanceof TypeVariable || t instanceof WildcardType) {
                        break;
                    } else {
                        target = (ParameterizedType) type;
                        break;
                    }
                }
                break;
            }
        }
        return target == null ? null : (Class<?>) target.getActualTypeArguments()[0];
    }

    /**
     * 从model中解析出table信息
     *
     * @param modelClass 对象模型
     * @return 表映射信息
     */
    public static TableInfo parseTableInfo(Class<?> modelClass) {
        TableInfo tableInfo = TABLE_INFO_CACHE.get(modelClass);
        if (tableInfo != null) {
            return tableInfo;
        }
        synchronized (LambdaUtils.class) {
            tableInfo = TABLE_INFO_CACHE.get(modelClass);
            if (tableInfo != null) {
                return tableInfo;
            }
            TableName tableNameAnnotation = modelClass.getAnnotation(TableName.class);
            //如果没有TableName注解, 则自动对model名称下划线处理, 拿到的表名(如果表名是t_user这种,则必须要有@TableName注解)
            String tableName = tableNameAnnotation != null
                    ? tableNameAnnotation.value() : ColumnUtils.camelCaseToUnderscore(modelClass.getSimpleName());

            TableIdType tableIdType = modelClass.getAnnotation(TableIdType.class);
            IdType idType = tableIdType != null ? tableIdType.value() : IdType.AUTO;
            String identity = tableIdType != null ? tableIdType.identity() : modelClass.getSimpleName();

            //分表连接符
            MultipleTableConnector multipleTableConnector = modelClass.getAnnotation(MultipleTableConnector.class);
            tableInfo = new TableInfo();
            tableInfo.setMultipleTableConnector(multipleTableConnector != null ? multipleTableConnector.value() : "_");

            tableInfo.setModelClass(modelClass);
            tableInfo.setTableName(tableName);
            tableInfo.setIdType(idType);
            tableInfo.setIdentity(identity);
            List<Result> resultList = new ArrayList<>();
            List<Field> declaredFields = new ArrayList<>();
            parseAlldeclaredFields(modelClass, declaredFields);
            for (Field declaredField : declaredFields) {
                //跳过final修饰变量
                if (java.lang.reflect.Modifier.isFinal(declaredField.getModifiers())) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("skip final field:{}.{}", modelClass.getSimpleName(), declaredField.getName());
                    }
                    continue;
                }

                //剔除不存在于数据库的字段
                TableColumn tableColumn = declaredField.getAnnotation(TableColumn.class);
                if (tableColumn != null && !tableColumn.exist()) {
                    continue;
                }

                //填充result映射
                String fieldName = declaredField.getName();
                boolean overrideColumn = tableColumn != null && !("".equals(tableColumn.value()));
                String columnName = overrideColumn ? tableColumn.value() : ColumnUtils.camelCaseToUnderscore(fieldName);
                if (overrideColumn) {
                    tableInfo.setOverrideColumn(true);
                }
                resultList.add(new Result(fieldName, columnName, overrideColumn));

                //覆盖默认主键
                if (tableColumn != null && tableColumn.primaryKey()) {
                    String keyColumn = "".equals(tableColumn.value()) ? fieldName : tableColumn.value();
                    tableInfo.setKeyColumn(keyColumn);
                    tableInfo.setKeyProperty(fieldName);
                }
            }
            tableInfo.setResultList(resultList);
            TABLE_INFO_CACHE.put(modelClass, tableInfo);
        }
        return tableInfo;
    }

    /**
     * 解析对象里面的字段, 如果是继承对象, 会遍历父级字段
     *
     * @param clz  class类
     * @param list field容器
     */
    private static void parseAlldeclaredFields(Class<?> clz, List<Field> list) {
        list.addAll(Arrays.asList(clz.getDeclaredFields()));
        if (clz.getSuperclass() != Object.class) {
            parseAlldeclaredFields(clz.getSuperclass(), list);
        }
    }
}
