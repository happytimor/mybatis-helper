package io.github.happytimor.mybatis.helper.generator;

import io.github.happytimor.mybatis.helper.generator.config.GlobalConfig;
import io.github.happytimor.mybatis.helper.generator.config.PackageConfig;
import io.github.happytimor.mybatis.helper.generator.config.StrategyConfig;
import io.github.happytimor.mybatis.helper.generator.engine.TemplateEngine;
import io.github.happytimor.mybatis.helper.generator.engine.VelocityTemplateEngine;
import org.apache.commons.lang3.StringUtils;

/**
 * 代码生成器入口类，提供链式调用API
 *
 * @author gaomingyuan
 */
public class CodeGenerator {

    /**
     * 全局配置
     */
    private GlobalConfig globalConfig;

    /**
     * 包配置
     */
    private PackageConfig packageConfig;

    /**
     * 策略配置
     */
    private StrategyConfig strategyConfig;

    /**
     * 模板引擎
     */
    private final TemplateEngine templateEngine;

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
     * 私有化构造方法
     */
    private CodeGenerator() {
        // 默认使用Velocity模板引擎
        this.templateEngine = new VelocityTemplateEngine();
    }

    /**
     * 创建代码生成器实例
     *
     * @return 代码生成器实例
     */
    public static CodeGenerator create() {
        return new CodeGenerator();
    }

    /**
     * 配置数据源
     *
     * @param url      数据库URL
     * @param username 用户名
     * @param password 密码
     * @return 代码生成器实例
     */
    public CodeGenerator dataSource(String url, String username, String password) {
        this.dataSourceUrl = url;
        this.dataSourceUsername = username;
        this.dataSourcePassword = password;
        return this;
    }

    /**
     * 配置全局信息
     *
     * @param consumer 全局配置消费者
     * @return 代码生成器实例
     */
    public CodeGenerator globalConfig(GlobalConfig.ConfigConsumer consumer) {
        this.globalConfig = new GlobalConfig();
        consumer.accept(this.globalConfig);
        return this;
    }

    /**
     * 配置包信息
     *
     * @param consumer 包配置消费者
     * @return 代码生成器实例
     */
    public CodeGenerator packageConfig(PackageConfig.ConfigConsumer consumer) {
        this.packageConfig = new PackageConfig();
        consumer.accept(this.packageConfig);
        return this;
    }

    /**
     * 配置策略信息
     *
     * @param consumer 策略配置消费者
     * @return 代码生成器实例
     */
    public CodeGenerator strategyConfig(StrategyConfig.ConfigConsumer consumer) {
        this.strategyConfig = new StrategyConfig();
        consumer.accept(this.strategyConfig);
        return this;
    }

    /**
     * 执行代码生成
     */
    public void execute() {
        // 验证配置
        validateConfig();

        // 将数据源信息传递给模板引擎
        if (templateEngine instanceof VelocityTemplateEngine) {
            ((VelocityTemplateEngine) templateEngine).setDataSource(dataSourceUrl, dataSourceUsername, dataSourcePassword);
        }

        // 使用模板引擎执行生成
        templateEngine.generate(this.globalConfig, this.packageConfig, this.strategyConfig);
    }

    /**
     * 验证配置
     */
    private void validateConfig() {
        if (this.dataSourceUrl == null || this.dataSourceUrl.isEmpty()) {
            throw new IllegalArgumentException("DataSource URL is required");
        }
        if (this.dataSourceUsername == null || this.dataSourceUsername.isEmpty()) {
            throw new IllegalArgumentException("DataSource username is required");
        }
        if (this.globalConfig == null) {
            throw new IllegalArgumentException("GlobalConfig is required");
        }
        if (this.packageConfig == null) {
            throw new IllegalArgumentException("PackageConfig is required");
        }
        if (StringUtils.isBlank(this.packageConfig.getParent())) {
            throw new IllegalArgumentException("PackageConfig field parent is required");
        }

        if (this.strategyConfig == null) {
            throw new IllegalArgumentException("StrategyConfig is required");
        }
        // 验证表名是否配置
        if (this.strategyConfig.getTableNames() == null || this.strategyConfig.getTableNames().length == 0) {
            throw new IllegalArgumentException("Table names are required");
        }
    }
}
