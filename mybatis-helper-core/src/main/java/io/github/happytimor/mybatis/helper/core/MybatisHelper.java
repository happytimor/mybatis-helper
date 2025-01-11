package io.github.happytimor.mybatis.helper.core;

import io.github.happytimor.mybatis.helper.core.common.Config;
import io.github.happytimor.mybatis.helper.core.common.Constants;
import io.github.happytimor.mybatis.helper.core.handler.MybatisXmlLanguageDriver;
import io.github.happytimor.mybatis.helper.core.mapper.*;
import io.github.happytimor.mybatis.helper.core.metadata.Result;
import io.github.happytimor.mybatis.helper.core.metadata.TableInfo;
import io.github.happytimor.mybatis.helper.core.method.*;
import io.github.happytimor.mybatis.helper.core.service.GlobalConfig;
import io.github.happytimor.mybatis.helper.core.service.IdentifierGenerator;
import io.github.happytimor.mybatis.helper.core.util.ColumnUtils;
import io.github.happytimor.mybatis.helper.core.util.LambdaUtils;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.reflection.MetaClass;
import org.apache.ibatis.reflection.Reflector;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.session.Configuration;
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
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.reflect.Field;
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
    private final List<AbstractMethod> methodList = Arrays.asList(
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

            new SelectMap(),
            new SelectMapList(),
            new SelectSingleValue()
    );

    /**
     * extra method list for unique index mapper to register
     */
    private final List<AbstractMethod> uniqueIndexMethodList = Arrays.asList(
            new ReplaceInto(),
            new InsertIgnoreInto(),
            new InsertOrUpdateWithUniqueIndex()
    );
    /**
     * skipped method list for no primary key mapper
     */
    private final List<String> skipMethodListForNoPrimaryKeyMapper = Arrays.asList(
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
     * sqlSessionFactory for single database
     *
     * @return SqlSessionFactory
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
     * register IdentifierGenerator
     */
    public void registerIdentifierGenerator() {
        String[] names = applicationContext.getBeanNamesForType(IdentifierGenerator.class);
        if (names.length == 0) {
            return;
        }
        IdentifierGenerator identifierGenerator = applicationContext.getBean(names[0], IdentifierGenerator.class);
        GlobalConfig.setIdentifierGenerator(identifierGenerator);
    }

    /**
     * 单数据库,可以拿到默认的 sqlSessionFactory, 直接指定 mapperSearchPath进行注入即可
     *
     * @param mapperSearchPath mapper类路径
     */
    public void registerSingleDatabase(String mapperSearchPath) {
        this.registerSingleDatabase(mapperSearchPath, false);
    }

    /**
     * 单数据库,可以拿到默认的 sqlSessionFactory, 直接指定 mapperSearchPath进行注入即可
     *
     * @param mapperSearchPath mapper类路径
     * @param enableIdGenerate 启用id自动生成
     */
    public void registerSingleDatabase(String mapperSearchPath, boolean enableIdGenerate) {
        this.registerIdentifierGenerator();
        SqlSessionFactory sqlSessionFactory = this.parseSqlSessionFactory();
        if (sqlSessionFactory == null || mapperSearchPath == null || "".equals(mapperSearchPath.trim())) {
            throw new RuntimeException("inject error");
        }
        try {
            this.regist(sqlSessionFactory, mapperSearchPath, enableIdGenerate);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 针对sqlSessionFactory进行注册
     *
     * @param sqlSessionFactory sqlSessionFactory对象
     * @param mapperSearchPath  mapper扫描路径
     * @throws IOException IO异常
     */
    public void regist(SqlSessionFactory sqlSessionFactory, String mapperSearchPath) throws IOException {
        this.regist(sqlSessionFactory, mapperSearchPath, false);
    }

    /**
     * 针对sqlSessionFactory进行注册
     *
     * @param sqlSessionFactory sqlSessionFactory对象
     * @param mapperSearchPath  mapper扫描路径
     * @param enableIdGenerate  是否启用id自动生成
     * @throws IOException IO异常
     */
    public void regist(SqlSessionFactory sqlSessionFactory, String mapperSearchPath, boolean enableIdGenerate)
            throws IOException {
        if (mapperSearchPath.contains(Constants.DOT)) {
            mapperSearchPath = mapperSearchPath.replace(Constants.DOT, "/");
        }
        mapperSearchPath = "classpath:" + mapperSearchPath + "/" + "*.class";
        if (this.config != null) {
            Configuration configuration = sqlSessionFactory.getConfiguration();
            configuration.setMapUnderscoreToCamelCase(this.config.isMapUnderscoreToCamelCase());
            if (enableIdGenerate) {
                configuration.getLanguageRegistry().setDefaultDriverClass(MybatisXmlLanguageDriver.class);
            }
        }
        Set<Class<?>> mapperClassList = this.parseMapper(mapperSearchPath);
        Set<String> registed = new HashSet<>();
        for (Class<?> mapperClass : mapperClassList) {
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
    private Set<Class<?>> parseMapper(String mapperSearchPath) throws IOException {
        Set<Class<?>> classNameSet = new HashSet<>();
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources(mapperSearchPath);
        for (Resource resource : resources) {
            try {
                MetadataReader metadataReader = new CachingMetadataReaderFactory().getMetadataReader(resource);
                String[] interfaceNames = metadataReader.getClassMetadata().getInterfaceNames();
                for (String interfaceName : interfaceNames) {
                    if (Objects.equals(interfaceName, BaseMapper.class.getName())
                            || Objects.equals(interfaceName, MultipleTableMapper.class.getName())
                            || Objects.equals(interfaceName, NoPrimaryKeyMapper.class.getName())
                            || Objects.equals(interfaceName, UniqueIndexEnhanceMapper.class.getName())
                            || Objects.equals(interfaceName, UniqueIndexEnhanceForMultipleTableMapper.class.getName())) {
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
     * inject mapper class
     *
     * @param mapperClass            mapper class
     * @param mapperBuilderAssistant mapperBuilderAssistant
     */
    private void inject(Class<?> mapperClass, MapperBuilderAssistant mapperBuilderAssistant) {
        Class<?> modelClass = LambdaUtils.extractModelClass(mapperClass);
        if (modelClass == null) {
            return;
        }
        boolean isNoPrimaryKeyMapper = NoPrimaryKeyMapper.class.isAssignableFrom(mapperClass);

        boolean isUniqueIndexMapper = UniqueIndexEnhanceMapper.class.isAssignableFrom(mapperClass);
        boolean isUniqueIndexForMultipleTableMapper = UniqueIndexEnhanceForMultipleTableMapper.class.isAssignableFrom(mapperClass);

        TableInfo tableInfo = LambdaUtils.parseTableInfo(modelClass);
        if (!isNoPrimaryKeyMapper) {
            if (StringUtils.isEmpty(tableInfo.getKeyColumn())) {
                tableInfo.setKeyColumn(Constants.DEFAULT_KEY_COLUMN);
            }
            if (StringUtils.isEmpty(tableInfo.getKeyProperty())) {
                tableInfo.setKeyProperty(Constants.DEFAULT_KEY_PROPERTY);
            }
            try {
                Field keyField = LambdaUtils.getFiledByName(modelClass, tableInfo.getKeyColumn());
                if (keyField != null) {
                    tableInfo.setKeyClass(keyField.getType());
                } else {
                    logger.warn("the class {} has no field named {}", modelClass.getName(), tableInfo.getKeyColumn());
                }
            } catch (Exception e) {
                logger.error("class: " + modelClass.getName() + "error: " + e.getMessage(), e);
            }
        }
        tableInfo.setMultipleTable(MultipleTableMapper.class.isAssignableFrom(mapperClass) || UniqueIndexEnhanceForMultipleTableMapper.class.isAssignableFrom(mapperClass));
        mapperBuilderAssistant.setCurrentNamespace(mapperClass.getName());

        Collection<String> mappedStatementNames = mapperBuilderAssistant.getConfiguration().getMappedStatementNames();
        for (AbstractMethod abstractMethod : methodList) {
            if (isNoPrimaryKeyMapper && skipMethodListForNoPrimaryKeyMapper.contains(abstractMethod.getClass().getSimpleName())) {
                if (logger.isDebugEnabled()) {
                    logger.debug("{} has no primary key, skip inject", mapperClass.getName());
                }
            }
            registerMethod(mapperClass, mapperBuilderAssistant, modelClass, tableInfo, mappedStatementNames, abstractMethod);
        }

        if (isUniqueIndexMapper || isUniqueIndexForMultipleTableMapper) {
            for (AbstractMethod abstractMethod : uniqueIndexMethodList) {
                registerMethod(mapperClass, mapperBuilderAssistant, modelClass, tableInfo, mappedStatementNames, abstractMethod);
            }
        }
        this.injectFieldRelation(modelClass, tableInfo, mapperBuilderAssistant.getConfiguration().getReflectorFactory());

    }

    /**
     * @param mapperClass            mapper
     * @param mapperBuilderAssistant assistant
     * @param modelClass             model class, such as User.java
     * @param tableInfo              table info for User.java
     * @param mappedStatementNames   mappedStatementName
     * @param abstractMethod         method to inject
     */
    private static void registerMethod(Class<?> mapperClass, MapperBuilderAssistant mapperBuilderAssistant, Class<?> modelClass, TableInfo tableInfo, Collection<String> mappedStatementNames, AbstractMethod abstractMethod) {
        String mapperdStatementId = mapperClass.getName() + "." + ColumnUtils.makeFirstCharacterLower(abstractMethod.getClass().getSimpleName());
        if (mappedStatementNames.contains(mapperdStatementId)) {
            if (logger.isWarnEnabled()) {
                logger.warn("{} already contains method {}, skip inject", mapperClass.getName(), abstractMethod.getClass().getSimpleName());
            }
            return;
        }
        if (logger.isDebugEnabled()) {
            logger.info("inject {} method for {}", abstractMethod.getClass().getSimpleName(), mapperClass.getName());
        }
        abstractMethod.inject(mapperBuilderAssistant, mapperClass, modelClass, tableInfo);
    }

    /**
     * 动态注入非标准字段映射
     *
     * @param modelClass       domain class name
     * @param tableInfo        table info
     * @param reflectorFactory reflectorFactory
     */
    private void injectFieldRelation(Class<?> modelClass, TableInfo tableInfo, ReflectorFactory reflectorFactory) {
        if (!tableInfo.isOverrideColumn()) {
            return;
        }
        MetaClass metaClass = MetaClass.forClass(modelClass, reflectorFactory);
        try {
            String filedName = ColumnUtils.makeFirstCharacterLower(Reflector.class.getSimpleName());
            Field field = MetaClass.class.getDeclaredField(filedName);
            field.setAccessible(true);
            Reflector reflector = (Reflector) field.get(metaClass);
            Field caseInsensitivePropertyMapField
                    = Reflector.class.getDeclaredField("caseInsensitivePropertyMap");
            caseInsensitivePropertyMapField.setAccessible(true);
            Map<String, String> caseInsensitivePropertyMap
                    = (Map<String, String>) caseInsensitivePropertyMapField.get(reflector);
            Map<String, String> relation = new HashMap<>(16);
            for (Result result : tableInfo.getResultList()) {
                if (!result.isOverrideColumn()) {
                    continue;
                }
                String column = result.getColumn().replaceAll("_", "").toUpperCase();
                logger.info("dymamic inject for class:{}, {} -> {}", modelClass.getName(), column, result.getProperty());
                //io/github/happytimor/mybatis/helper/single/database/test/domain/UserUid.strangeName -> abc
                caseInsensitivePropertyMap.put(column, result.getProperty());
                relation.put(result.getProperty(), result.getColumn());
            }
            putColumnRelation(modelClass, relation);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void putColumnRelation(Class<?> modelClass, Map<String, String> relation) {
        Constants.COLUMN_RELATION.put(modelClass.getName().replaceAll("\\.", "/"), relation);
        if (modelClass.getSuperclass() != Object.class) {
            putColumnRelation(modelClass.getSuperclass(), relation);
        }

    }

}
