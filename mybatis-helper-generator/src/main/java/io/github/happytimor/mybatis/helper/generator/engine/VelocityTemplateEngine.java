package io.github.happytimor.mybatis.helper.generator.engine;

import io.github.happytimor.mybatis.helper.generator.config.GlobalConfig;
import io.github.happytimor.mybatis.helper.generator.config.PackageConfig;
import io.github.happytimor.mybatis.helper.generator.config.StrategyConfig;
import io.github.happytimor.mybatis.helper.generator.domain.GenTable;
import io.github.happytimor.mybatis.helper.generator.domain.GenTableColumn;
import io.github.happytimor.mybatis.helper.generator.util.GenUtils;
import io.github.happytimor.mybatis.helper.generator.util.RuntimeUtils;
import io.github.happytimor.mybatis.helper.generator.util.VelocityInitializer;
import io.github.happytimor.mybatis.helper.generator.util.VelocityUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Velocity模板引擎实现类
 *
 * @author gaomingyuan
 */
public class VelocityTemplateEngine implements TemplateEngine {

    private static final Logger log = LoggerFactory.getLogger(VelocityTemplateEngine.class);

    /**
     * 数据源URL
     */
    private String dataSourceUrl;

    /**
     * 数据源用户名
     */
    private String dataSourceUsername;

    /**
     * 数据源密码
     */
    private String dataSourcePassword;

    /**
     * 设置数据源
     *
     * @param url      数据库URL
     * @param username 用户名
     * @param password 密码
     */
    public void setDataSource(String url, String username, String password) {
        this.dataSourceUrl = url;
        this.dataSourceUsername = username;
        this.dataSourcePassword = password;
    }

    /**
     * 根据配置生成代码
     *
     * @param globalConfig   全局配置
     * @param packageConfig  包配置
     * @param strategyConfig 策略配置
     */
    @Override
    public void generate(GlobalConfig globalConfig, PackageConfig packageConfig, StrategyConfig strategyConfig) {
        try {
            // 初始化Velocity
            VelocityInitializer.initVelocity();

            // 遍历需要生成的表
            for (String tableName : strategyConfig.getTableNames()) {
                // 从数据库获取表信息
                GenTable genTable = getGenTableFromDatabase(tableName);
                if (genTable == null) {
                    log.warn("表 {} 不存在，跳过生成", tableName);
                    continue;
                }

                // 从数据库获取表列信息
                List<GenTableColumn> genTableColumns = getGenTableColumnsFromDatabase(tableName);
                
                // 过滤掉不需要的字段
                List<GenTableColumn> filteredColumns = new ArrayList<>();
                for (GenTableColumn column : genTableColumns) {
                    // 跳过gmt_modified字段
                    if (!"gmt_modified".equalsIgnoreCase(column.getColumnName())) {
                        filteredColumns.add(column);
                    }
                }
                
                // 初始化列字段信息
                for (GenTableColumn column : filteredColumns) {
                    GenUtils.initColumnField(column);
                }
                genTable.setColumns(filteredColumns);

                // 设置主键列
                for (GenTableColumn column : filteredColumns) {
                    if (column.isPk()) {
                        genTable.setPkColumn(column);
                        break;
                    }
                }

                // 初始化表信息
                initGenTable(genTable, globalConfig, packageConfig, strategyConfig);

                // 准备Velocity上下文
                VelocityContext context = prepareContext(genTable, packageConfig);

                // 生成代码
                generateCodeFiles(genTable, context, globalConfig, strategyConfig);
            }

            log.info("代码生成完成");

            // 打开输出目录（如果未禁止）
            if (!globalConfig.isDisableOpenDir()) {
                try {
                    RuntimeUtils.openDir(globalConfig.getOutputDir());
                } catch (IOException e) {
                    log.warn("打开输出目录失败：{}", e.getMessage());
                }
            }
        } catch (Exception e) {
            log.error("代码生成失败", e);
            throw new RuntimeException("代码生成失败", e);
        }
    }

    /**
     * 从数据库获取表信息
     *
     * @param tableName 表名
     * @return 表信息
     */
    private GenTable getGenTableFromDatabase(String tableName) {
        GenTable genTable = null;
        Connection connection = null;
        ResultSet resultSet = null;
        DatabaseMetaData metaData = null;

        try {
            connection = DriverManager.getConnection(dataSourceUrl, dataSourceUsername, dataSourcePassword);
            metaData = connection.getMetaData();

            // 获取当前连接的目录和模式
            String catalog = connection.getCatalog();
            String schema = connection.getSchema();

            // 使用实际的目录和模式查询表，避免查询所有数据库
            resultSet = metaData.getTables(catalog, schema, tableName, new String[]{"TABLE"});

            if (resultSet.next()) {
                genTable = new GenTable();
                genTable.setTableName(resultSet.getString("TABLE_NAME"));
                genTable.setTableComment(resultSet.getString("REMARKS"));
            }
        } catch (SQLException e) {
            log.error("获取表信息失败：{}", e.getMessage(), e);
        } finally {
            closeResources(resultSet, null, connection);
        }

        return genTable;
    }

    /**
     * 从数据库获取表列信息
     *
     * @param tableName 表名
     * @return 表列信息列表
     */
    private List<GenTableColumn> getGenTableColumnsFromDatabase(String tableName) {
        List<GenTableColumn> columns = new ArrayList<>();
        Connection connection = null;
        ResultSet resultSet = null;
        DatabaseMetaData metaData = null;

        try {
            connection = DriverManager.getConnection(dataSourceUrl, dataSourceUsername, dataSourcePassword);
            metaData = connection.getMetaData();

            // 获取当前连接的目录和模式
            String catalog = connection.getCatalog();
            String schema = connection.getSchema();

            // 先查主键
            Set<String> pkSet = new HashSet<>();
            try (ResultSet pkRs = metaData.getPrimaryKeys(catalog, schema, tableName)) {
                while (pkRs.next()) {
                    pkSet.add(pkRs.getString("COLUMN_NAME"));
                }
            }

            resultSet = metaData.getColumns(catalog, schema, tableName, null);

            while (resultSet.next()) {
                GenTableColumn column = new GenTableColumn();
                String columnName = resultSet.getString("COLUMN_NAME");
                column.setColumnName(columnName);
                column.setColumnType(resultSet.getString("TYPE_NAME"));
                column.setColumnComment(resultSet.getString("REMARKS"));
                column.setPk(pkSet.contains(columnName));
                columns.add(column);
            }
        } catch (SQLException e) {
            log.error("获取表列信息失败：{}", e.getMessage(), e);
        } finally {
            closeResources(resultSet, null, connection);
        }

        return columns;
    }

    /**
     * 初始化表信息
     *
     * @param genTable       表信息
     * @param globalConfig   全局配置
     * @param packageConfig  包配置
     * @param strategyConfig 策略配置
     */
    private void initGenTable(GenTable genTable, GlobalConfig globalConfig, PackageConfig packageConfig, StrategyConfig strategyConfig) {
        // 设置基本信息
        genTable.setClassName(GenUtils.convertClassName(globalConfig, genTable.getTableName()));
        genTable.setPackageName(packageConfig.getParent());
        genTable.setFunctionName(GenUtils.replaceText(genTable.getTableComment()));
        genTable.setFunctionAuthor(globalConfig.getAuthor());

        // 设置生成路径
        genTable.setGenPath(globalConfig.getOutputDir());
    }

    /**
     * 准备Velocity上下文
     *
     * @param genTable      表信息
     * @param packageConfig 包配置
     * @return Velocity上下文
     */
    private VelocityContext prepareContext(GenTable genTable, PackageConfig packageConfig) {
        // 使用原项目的VelocityUtils准备上下文
        VelocityContext context = VelocityUtils.prepareContext(genTable);
        context.put("responseClassPackage", packageConfig.getResult());
        context.put("responseClassName", VelocityUtils.getPackageSuffix(packageConfig.getResult()));

        context.put("entity", packageConfig.getEntity());
        context.put("mapper", packageConfig.getMapper());
        context.put("service", packageConfig.getService());
        context.put("controller", packageConfig.getController());
        context.put("request", packageConfig.getRequest());
        context.put("response", packageConfig.getResponse());

        // todo 补充逻辑删除,乐观锁
        // 添加策略配置相关信息
//        context.put("enableLombok", strategyConfig.isEnableLombok());
//        context.put("enableTableFieldAnnotation", strategyConfig.isEnableTableFieldAnnotation());
//
//        // 添加版本和逻辑删除信息
//        if (StringUtils.isNotEmpty(strategyConfig.getVersionColumnName())) {
//            context.put("versionColumnName", strategyConfig.getVersionColumnName());
//        }
//        if (StringUtils.isNotEmpty(strategyConfig.getLogicDeleteColumnName())) {
//            context.put("logicDeleteColumnName", strategyConfig.getLogicDeleteColumnName());
//        }

        return context;
    }

    /**
     * 生成代码文件
     *
     * @param genTable       表信息
     * @param context        Velocity上下文
     * @param globalConfig   全局配置
     * @param strategyConfig 策略配置
     * @throws IOException IO异常
     */
    private void generateCodeFiles(GenTable genTable, VelocityContext context, GlobalConfig globalConfig, StrategyConfig strategyConfig) throws IOException {
        // 获取模板列表
        List<String> templates = VelocityUtils.getTemplateList();

        for (String template : templates) {
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, sw);

            // 生成文件
            String fileName = VelocityUtils.getFileName(template, genTable, context);
            String filePath = globalConfig.getOutputDir() + File.separator + fileName;

            // 创建文件目录
            File file = new File(filePath);
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }

            // 写入文件（如果文件不存在或允许覆盖）
            if (!file.exists() || strategyConfig.isEnableFileOverride()) {
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    fos.write(sw.toString().getBytes(StandardCharsets.UTF_8));
                }
                log.info("生成文件：{}", filePath);
            } else {
                log.info("文件已存在，跳过生成：{}", filePath);
            }
        }
    }

    /**
     * 关闭资源
     *
     * @param resultSet  结果集
     * @param statement  语句
     * @param connection 连接
     */
    private void closeResources(ResultSet resultSet, Statement statement, Connection connection) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            log.error("关闭资源失败：{}", e.getMessage(), e);
        }
    }
}
