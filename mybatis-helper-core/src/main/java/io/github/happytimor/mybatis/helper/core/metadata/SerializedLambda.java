package io.github.happytimor.mybatis.helper.core.metadata;

import org.apache.ibatis.io.Resources;
import org.springframework.util.SerializationUtils;

import java.io.*;

/**
 * @author chenpeng
 */
public class SerializedLambda implements Serializable {

    private static final long serialVersionUID = 8025925345765570181L;

    private Class<?> capturingClass;
    private String functionalInterfaceClass;
    private String functionalInterfaceMethodName;
    private String functionalInterfaceMethodSignature;
    private String implClass;
    private String implMethodName;
    private String implMethodSignature;
    private int implMethodKind;
    private String instantiatedMethodType;
    private Object[] capturedArgs;

    /**
     * 通过反序列化转换 lambda 表达式，该方法只能序列化 lambda 表达式，不能序列化接口实现或者正常非 lambda 写法的对象
     *
     * @param lambda lambda对象
     * @return 返回解析后的 SerializedLambda
     */
    public static SerializedLambda resolve(ColumnFunction<?, ?> lambda) {
        if (!lambda.getClass().isSynthetic()) {
            throw new RuntimeException("the input param must be lambda");
        }
        try (ObjectInputStream objIn = new ObjectInputStream(new ByteArrayInputStream(SerializationUtils.serialize(lambda))) {
            @Override
            protected Class<?> resolveClass(ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {
                Class<?> clazz;
                try {
                    clazz = toClassConfident(objectStreamClass.getName());
                } catch (Exception ex) {
                    clazz = super.resolveClass(objectStreamClass);
                }
                return clazz == java.lang.invoke.SerializedLambda.class ? SerializedLambda.class : clazz;
            }
        }) {
            return (SerializedLambda) objIn.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * obtain interface class
     *
     * @return class name of interface
     */
    public String getFunctionalInterfaceClassName() {
        return normalizedName(functionalInterfaceClass);
    }

    /**
     * 获取实现的 class
     *
     * @return 实现类
     */
    public Class<?> getImplClass() {
        return toClassConfident(getImplClassName());
    }

    /**
     * 获取 class 的名称
     *
     * @return 类名
     */
    public String getImplClassName() {
        return normalizedName(implClass);
    }

    /**
     * 获取实现者的方法名称
     *
     * @return 方法名称
     */
    public String getImplMethodName() {
        return implMethodName;
    }

    /**
     * 正常化类名称，将类名称中的 / 替换为 .
     *
     * @param name 名称
     * @return 正常的类名
     */
    private String normalizedName(String name) {
        return name.replace('/', '.');
    }

    /**
     * @return 获取实例化方法的类型
     */
    public Class<?> getInstantiatedType() {
        String instantiatedTypeName = normalizedName(instantiatedMethodType.substring(2, instantiatedMethodType.indexOf(';')));
        return toClassConfident(instantiatedTypeName);
    }

    public static Class<?> toClassConfident(String name) {
        try {
            return Resources.classForName(name);
        } catch (ClassNotFoundException e) {
            try {
                return Class.forName(name);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * @return 字符串形式
     */
    @Override
    public String toString() {
        String interfaceName = getFunctionalInterfaceClassName();
        String implName = getImplClassName();
        return String.format("%s -> %s::%s",
                interfaceName.substring(interfaceName.lastIndexOf('.') + 1),
                implName.substring(implName.lastIndexOf('.') + 1),
                implMethodName);
    }
}
