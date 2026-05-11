package io.github.happytimor.mybatis.helper.generator.engine;

import io.github.happytimor.mybatis.helper.generator.config.GlobalConfig;
import io.github.happytimor.mybatis.helper.generator.config.PackageConfig;
import io.github.happytimor.mybatis.helper.generator.config.StrategyConfig;

/**
 * 模板引擎接口
 *
 * @author gaomingyuan
 */
public interface TemplateEngine {

    /**
     * 根据配置生成代码
     *
     * @param globalConfig   全局配置
     * @param packageConfig  包配置
     * @param strategyConfig 策略配置
     */
    void generate(GlobalConfig globalConfig, PackageConfig packageConfig, StrategyConfig strategyConfig);
}
