package io.github.happytimor.mybatis.helper.generator.config;

import lombok.Getter;

/**
 * 策略配置类
 *
 * @author gaomingyuan
 */
@Getter
public class StrategyConfig {

    /**
     * 需要生成的表名
     */
    private String[] tableNames;

    /**
     * 命名策略
     */
    private NamingStrategy naming = NamingStrategy.underline_to_camel;

    /**
     * 字段命名策略
     */
    private NamingStrategy columnNaming = NamingStrategy.underline_to_camel;

    /**
     * 是否覆盖已生成文件
     */
    private boolean enableFileOverride;

    /**
     * 配置消费者接口
     */
    @FunctionalInterface
    public interface ConfigConsumer {
        void accept(StrategyConfig config);
    }

    /**
     * 命名策略枚举
     */
    public enum NamingStrategy {
        /**
         * 下划线转驼峰
         */
        underline_to_camel,
        /**
         * 保持原命名
         */
        keep
    }

    /**
     * 设置需要生成的表名
     *
     * @param tableNames 需要生成的表名
     * @return 策略配置
     */
    public StrategyConfig addInclude(String... tableNames) {
        this.tableNames = tableNames;
        return this;
    }

    /**
     * 设置命名策略
     *
     * @param naming 命名策略
     * @return 策略配置
     */
    public StrategyConfig naming(NamingStrategy naming) {
        this.naming = naming;
        return this;
    }

    /**
     * 设置字段命名策略
     *
     * @param columnNaming 字段命名策略
     * @return 策略配置
     */
    public StrategyConfig columnNaming(NamingStrategy columnNaming) {
        this.columnNaming = columnNaming;
        return this;
    }

    /**
     * 设置是否覆盖已生成文件
     *
     * @param enableFileOverride 是否覆盖已生成文件
     * @return 策略配置
     */
    public StrategyConfig enableFileOverride(boolean enableFileOverride) {
        this.enableFileOverride = enableFileOverride;
        return this;
    }

    /**
     * 设置覆盖已生成文件
     *
     * @return 策略配置
     */
    public StrategyConfig enableFileOverride() {
        this.enableFileOverride = true;
        return this;
    }
}
