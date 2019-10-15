package io.github.happytimor.mybatis.helper.core;

import io.github.happytimor.mybatis.helper.core.annotation.MultipleTableConnector;
import io.github.happytimor.mybatis.helper.core.annotation.TableColumn;
import io.github.happytimor.mybatis.helper.core.annotation.TableName;
import io.github.happytimor.mybatis.helper.core.annotation.TablePrimaryKey;
import io.github.happytimor.mybatis.helper.core.common.Config;
import io.github.happytimor.mybatis.helper.core.common.Constants;
import io.github.happytimor.mybatis.helper.core.mapper.BaseMapper;
import io.github.happytimor.mybatis.helper.core.mapper.MultipleTableMapper;
import io.github.happytimor.mybatis.helper.core.mapper.NoPrimaryKeyMapper;
import io.github.happytimor.mybatis.helper.core.metadata.Result;
import io.github.happytimor.mybatis.helper.core.metadata.TableInfo;
import io.github.happytimor.mybatis.helper.core.method.*;
import io.github.happytimor.mybatis.helper.core.util.ColumnUtils;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.*;
import java.util.*;

/**
 * @author chenpeng
 */
public class MybatisHelper implements ApplicationContextAware {
    private final static Logger logger = LoggerFactory.getLogger(MybatisHelper.class);


    private ApplicationContext applicationContext;

    protected Config config = new Config();

    public MybatisHelper() {
        //自动开启驼峰转换,可以在子类关闭
        this.config.setMapUnderscoreToCamelCase(true);
    }

    /**
     * 待注入通用方法
     */
    private List<AbstractMethod> methodList = Arrays.asList(
            new Insert(),
            new BatchInsert(),

            new Delete(),
            new DeleteById(),
            new DeleteByIdList(),

            new UpdateById(),
            new BatchUpdateById(),
            new Update(),

            new SelectById(),
            new SelectByIdList(),
            new SelectList(),
            new SelectOne(),
            new SelectCount(),

            new InsertOrUpdateWithUniqueIndex()
    );

    /**
     * 无主键mapper跳过方法
     */
    private List<String> skipMethodListForNoPrimaryKeyMapper = Arrays.asList(
            DeleteById.class.getSimpleName(),
            DeleteByIdList.class.getSimpleName(),
            UpdateById.class.getSimpleName(),
            BatchUpdateById.class.getSimpleName(),
            SelectById.class.getSimpleName(),
            SelectByIdList.class.getSimpleName()
    );

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 单数据库获取sqlSessionFactory
     */
    public SqlSessionFactory parseSqlSessionFactory() {
        String[] beanNamesForType = applicationContext.getBeanNamesForType(SqlSessionFactory.class);
        if (beanNamesForType.length != 1) {
            logger.info("there is no database or multiple database, skip default regist");
            return null;
        }
        return (SqlSessionFactory) applicationContext.getBean(beanNamesForType[0]);
    }

    /**
     * 单数据库,可以拿到默认的 sqlSessionFactory, 直接指定 mapperSearchPath进行注入即可
     *
     * @param mapperSearchPath mapper类路径
     */
    public void registSingleDatabase(String mapperSearchPath) {
        SqlSessionFactory sqlSessionFactory = this.parseSqlSessionFactory();
        if (sqlSessionFactory == null || mapperSearchPath == null || "".equals(mapperSearchPath.trim())) {
            throw new RuntimeException("inject error");
        }
        try {
            this.regist(sqlSessionFactory, mapperSearchPath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void regist(SqlSessionFactory sqlSessionFactory, String mapperSearchPath) throws IOException {
        if (mapperSearchPath.contains(".")) {
            mapperSearchPath = mapperSearchPath.replace(".", "/");
        }

        mapperSearchPath = "classpath:" + mapperSearchPath + "/" + "*.class";

        if (this.config != null) {
            sqlSessionFactory.getConfiguration().setMapUnderscoreToCamelCase(this.config.isMapUnderscoreToCamelCase());
        }

        Set<Class> mapperClassList = this.parseMapper(mapperSearchPath);
        Set<String> registed = new HashSet<>();
        for (Class mapperClass : mapperClassList) {
            if (registed.contains(mapperClass.getName())) {
                continue;
            }
            registed.add(mapperClass.getName());
            inject(mapperClass, new MapperBuilderAssistant(sqlSessionFactory.getConfiguration(), ""));
        }
    }

    /**
     * 解析得到哪些接口继承了BaseMapper
     *
     * @param mapperSearchPath mapper class扫描路径
     * @return 继承了BaseMapper的接口列表
     */
    private Set<Class> parseMapper(String mapperSearchPath) throws IOException {
        Set<Class> classNameSet = new HashSet<>();
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources(mapperSearchPath);
        for (Resource resource : resources) {
            try {
                MetadataReader metadataReader = new CachingMetadataReaderFactory().getMetadataReader(resource);
                String[] interfaceNames = metadataReader.getClassMetadata().getInterfaceNames();
                for (String interfaceName : interfaceNames) {
                    if (Objects.equals(interfaceName, BaseMapper.class.getName())
                            || Objects.equals(interfaceName, MultipleTableMapper.class.getName())
                            || Objects.equals(interfaceName, NoPrimaryKeyMapper.class.getName())) {
                        classNameSet.add(Class.forName(metadataReader.getClassMetadata().getClassName()));
                        break;
                    }
                }
            } catch (Exception e) {
                logger.error("parse class failure:" + e.getMessage(), e);
            }
        }

        return classNameSet;
    }

    /**
     * 方法注入
     *
     * @param mapperClass            mapper类
     * @param mapperBuilderAssistant mapperBuilderAssistant
     */
    private void inject(Class<?> mapperClass, MapperBuilderAssistant mapperBuilderAssistant) {
        Class<?> modelClass = this.extractModelClass(mapperClass);
        if (modelClass == null) {
            return;
        }
        boolean isNoPrimaryKeyMapper = NoPrimaryKeyMapper.class.isAssignableFrom(mapperClass);

        Collection<String> mappedStatementNames = mapperBuilderAssistant.getConfiguration().getMappedStatementNames();
        TableInfo tableInfo = parseTableInfo(modelClass, !isNoPrimaryKeyMapper);
        if (tableInfo == null) {
            throw new RuntimeException("fail to parse tableInfo");
        }
        tableInfo.setMultipleTable(MultipleTableMapper.class.isAssignableFrom(mapperClass));
        mapperBuilderAssistant.setCurrentNamespace(mapperClass.getName());

        for (AbstractMethod abstractMethod : methodList) {
            if (isNoPrimaryKeyMapper && skipMethodListForNoPrimaryKeyMapper.contains(abstractMethod.getClass().getSimpleName())) {
                if (logger.isDebugEnabled()) {
                    logger.debug("{} has no primary key, skip inject", mapperClass.getName());
                }
            }
            String mapperdStatementId = mapperClass.getName() + "." + ColumnUtils.makeFirstCharacterLower(abstractMethod.getClass().getSimpleName());
            if (mappedStatementNames.contains(mapperdStatementId)) {
                if (logger.isWarnEnabled()) {
                    logger.warn("{} already contains method {}, skip inject", mapperClass.getName(), abstractMethod.getClass().getSimpleName());
                }
                continue;
            }
            if (logger.isDebugEnabled()) {
                logger.info("inject {} method for {}", abstractMethod.getClass().getSimpleName(), mapperClass.getName());
            }
            abstractMethod.inject(mapperBuilderAssistant, mapperClass, modelClass, tableInfo);
        }
    }


    /**
     * 从model中解析出table信息
     *
     * @param modelClass 对象模型
     * @return 表映射信息
     */
    private TableInfo parseTableInfo(Class<?> modelClass, boolean hasPrimaryKey) {
        TableName tableNameAnnotation = modelClass.getAnnotation(TableName.class);

        //如果没有TableName注解, 则自动对model名称下划线处理, 拿到的表名(如果表名是t_user这种,则必须要有@TableName注解)
        String tableName = tableNameAnnotation != null ? tableNameAnnotation.value() : ColumnUtils.camelCaseToUnderscore(modelClass.getSimpleName());
        //分表连接符
        MultipleTableConnector multipleTableConnector = modelClass.getAnnotation(MultipleTableConnector.class);
        TableInfo tableInfo = new TableInfo();
        tableInfo.setMultipleTableConnector(multipleTableConnector != null ? multipleTableConnector.value() : "_");

        tableInfo.setModelClass(modelClass);
        tableInfo.setTableName(tableName);
        if (hasPrimaryKey) {
            tableInfo.setKeyColumn(Constants.DEFAULT_KEY_COLUMN);
            tableInfo.setKeyProperty(Constants.DEFAULT_KEY_PROPERTY);
        }

        List<Result> resultList = new ArrayList<>();
        Field[] declaredFields = modelClass.getDeclaredFields();
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
            String columnName = (tableColumn != null) ? tableColumn.value() : ColumnUtils.camelCaseToUnderscore(fieldName);
            resultList.add(new Result(fieldName, columnName));

            //覆盖默认主键
            TablePrimaryKey tablePrimaryKey = declaredField.getAnnotation(TablePrimaryKey.class);
            if (tablePrimaryKey != null) {
                tableInfo.setKeyColumn(tablePrimaryKey.value());
                tableInfo.setKeyProperty(fieldName);
            }
        }
        tableInfo.setResultList(resultList);
        return tableInfo;
    }

    /**
     * 提取泛型模型,多泛型的时候请将泛型T放在第一位
     *
     * @param mapperClass mapper 接口
     * @return mapper 泛型
     */
    private Class<?> extractModelClass(Class<?> mapperClass) {
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
}
