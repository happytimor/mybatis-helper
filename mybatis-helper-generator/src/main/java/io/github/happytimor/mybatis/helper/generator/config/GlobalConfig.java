package io.github.happytimor.mybatis.helper.generator.config;

import lombok.Data;

/**
 * 全局配置类
 *
 * @author gaomingyuan
 */
@Data
public class GlobalConfig {

    /**
     * 作者
     */
    private String author;

    /**
     * 注释日期格式
     */
    private String commentDate;

    /**
     * 输出目录
     */
    private String outputDir;

    /**
     * 是否禁止打开输出目录
     */
    private boolean disableOpenDir;

    /**
     * 自动去除表前缀
     */
    public boolean autoRemovePre;

    /**
     * 表前缀(类名不会包含表前缀,可多个逗号分割)
     */
    public String tablePrefix;

    /**
     * 配置消费者接口
     */
    @FunctionalInterface
    public interface ConfigConsumer {
        void accept(GlobalConfig config);
    }

    /**
     * 设置作者
     *
     * @param author 作者
     * @return 全局配置
     */
    public GlobalConfig author(String author) {
        this.author = author;
        return this;
    }

    /**
     * 设置注释日期格式
     *
     * @param commentDate 注释日期格式
     * @return 全局配置
     */
    public GlobalConfig commentDate(String commentDate) {
        this.commentDate = commentDate;
        return this;
    }

    /**
     * 设置输出目录
     *
     * @param outputDir 输出目录
     * @return 全局配置
     */
    public GlobalConfig outputDir(String outputDir) {
        this.outputDir = outputDir;
        return this;
    }

    /**
     * 设置是否禁止打开输出目录
     *
     * @param disableOpenDir 是否禁止打开输出目录
     * @return 全局配置
     */
    public GlobalConfig disableOpenDir(boolean disableOpenDir) {
        this.disableOpenDir = disableOpenDir;
        return this;
    }

    /**
     * 设置禁止打开输出目录
     *
     * @return 全局配置
     */
    public GlobalConfig disableOpenDir() {
        this.disableOpenDir = true;
        return this;
    }
}
