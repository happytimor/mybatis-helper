package io.github.happytimor.mybatis.helper.core.handler;

import io.github.happytimor.mybatis.helper.core.common.IdType;
import io.github.happytimor.mybatis.helper.core.common.Params;
import io.github.happytimor.mybatis.helper.core.metadata.TableInfo;
import io.github.happytimor.mybatis.helper.core.service.GlobalConfig;
import io.github.happytimor.mybatis.helper.core.util.LambdaUtils;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * @author chenpeng
 */
public class IdentifierGeneratorHandler {
    private final Configuration configuration;

    public IdentifierGeneratorHandler(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * 参数填充
     *
     * @param parameter      参数对象
     * @param sqlCommandType sql类型, 只针对insert和batchInsert进行主键替换
     */
    public void processParameter(Object parameter, SqlCommandType sqlCommandType) {
        if (parameter == null || SqlCommandType.INSERT != sqlCommandType) {
            return;
        }
        Object parameters = getParameters(parameter);
        if (parameters == null) {
            return;
        }
        if (parameters instanceof List) {
            List<?> list = (List<?>) parameters;
            Object firstEntity = list.get(0);
            TableInfo tableInfo = LambdaUtils.parseTableInfo(firstEntity.getClass());
            if (tableInfo != null && tableInfo.getIdType() == IdType.DYNAMIC_GENERATE) {
                String identity = tableInfo.getIdentity();
                List<Number> idList = GlobalConfig.getIdentifierGenerator().nextIdBatch(identity, list.size());
                for (int i = 0; i < list.size(); i++) {
                    populateKey(list.get(i), tableInfo, idList.get(i));
                }
            }
        } else {
            TableInfo tableInfo = LambdaUtils.parseTableInfo(parameters.getClass());
            if (tableInfo != null && tableInfo.getIdType() == IdType.DYNAMIC_GENERATE) {
                Number id = GlobalConfig.getIdentifierGenerator().nextId(tableInfo.getIdentity());
                populateKey(parameters, tableInfo, id);
            }
        }
    }

    /**
     * 主键key填充
     *
     * @param entity    entity
     * @param tableInfo table information
     * @param id        generated id
     */
    protected void populateKey(Object entity, TableInfo tableInfo, Number id) {
        MetaObject metaObject = this.configuration.newMetaObject(entity);

        final String keyProperty = tableInfo.getKeyProperty();

        Class<?> keyType = tableInfo.getKeyClass();
        Object idValue = metaObject.getValue(keyProperty);
        if (idValue != null) {
            //已填充主键，不需要重新填充
            return;
        }

        if (Number.class.isAssignableFrom(keyType)) {
            if (keyType == id.getClass()) {
                metaObject.setValue(keyProperty, id);
            } else if (Integer.class == keyType) {
                metaObject.setValue(keyProperty, id.intValue());
            } else if (Long.class == keyType) {
                metaObject.setValue(keyProperty, id.longValue());
            } else if (BigDecimal.class.isAssignableFrom(keyType)) {
                metaObject.setValue(keyProperty, new BigDecimal(id.longValue()));
            } else if (BigInteger.class.isAssignableFrom(keyType)) {
                metaObject.setValue(keyProperty, new BigInteger(id.toString()));
            } else {
                throw new RuntimeException("Key type '" + keyType + "' not supported");
            }
        }
    }


    /**
     * 获取参数对象列表
     *
     * @return 集合参数
     */
    protected Object getParameters(Object parameterObject) {
        if (parameterObject instanceof Map) {
            Map<?, ?> parameterMap = (Map<?, ?>) parameterObject;
            if (parameterMap.containsKey(Params.ENTITY)) {
                return parameterMap.get(Params.ENTITY);
            }
            if (parameterMap.containsKey(Params.LIST)) {
                return parameterMap.get(Params.LIST);
            }
        }
        return null;
    }
}
