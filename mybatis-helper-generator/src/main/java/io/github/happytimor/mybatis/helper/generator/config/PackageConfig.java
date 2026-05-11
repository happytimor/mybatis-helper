package io.github.happytimor.mybatis.helper.generator.config;

import lombok.Getter;

/**
 * 包配置类
 *
 * @author gaomingyuan
 */
@Getter
public class PackageConfig {

    /**
     * 父包名
     */
    private String parent;

    /**
     * 实体包名
     */
    private String entity = "domain.entity";

    /**
     * 请求对象
     */
    private String request = "domain.vo.request";

    /**
     * 响应对象
     */
    private String response = "domain.vo.response";

    /**
     * Mapper包名
     */
    private String mapper = "mapper";

    /**
     * Service包名
     */
    private String service = "service";

    /**
     * Controller包名
     */
    private String controller = "controller";

    /**
     * 通用返回体包名+类名(针对不同使用者各自项目规范)
     */
    private String result = "common.Result";

    /**
     * 配置消费者接口
     */
    @FunctionalInterface
    public interface ConfigConsumer {
        void accept(PackageConfig config);
    }

    /**
     * 输出文件类型枚举
     */
    public enum OutputFile {
        /**
         * Mapper XML文件
         */
        xml,
        /**
         * 静态资源文件
         */
        resources,
        /**
         * 模板文件
         */
        templates
    }

    /**
     * 设置父包名
     *
     * @param parent 父包名
     * @return 包配置
     */
    public PackageConfig parent(String parent) {
        this.parent = parent;
        return this;
    }

    /**
     * 设置实体包名
     *
     * @param entity 实体包名
     * @return 包配置
     */
    public PackageConfig entity(String entity) {
        this.entity = entity;
        return this;
    }

    /**
     * 设置请求对象包名
     *
     * @param request 请求对象包名
     * @return 包配置
     */
    public PackageConfig request(String request) {
        this.request = request;
        return this;
    }

    /**
     * 设置响应对象包名
     *
     * @param response 响应对象包名
     * @return 包配置
     */
    public PackageConfig response(String response) {
        this.response = response;
        return this;
    }

    /**
     * 设置通用返回体包名+类名(针对不同使用者各自项目规范)
     *
     * @param result 通用返回体包名
     * @return 包配置
     */
    public PackageConfig result(String result) {
        this.result = result;
        return this;
    }

    /**
     * 设置Mapper包名
     *
     * @param mapper Mapper包名
     * @return 包配置
     */
    public PackageConfig mapper(String mapper) {
        this.mapper = mapper;
        return this;
    }


    /**
     * 设置Service包名
     *
     * @param service Service包名
     * @return 包配置
     */
    public PackageConfig service(String service) {
        this.service = service;
        return this;
    }

    /**
     * 设置Controller包名
     *
     * @param controller Controller包名
     * @return 包配置
     */
    public PackageConfig controller(String controller) {
        this.controller = controller;
        return this;
    }
}
