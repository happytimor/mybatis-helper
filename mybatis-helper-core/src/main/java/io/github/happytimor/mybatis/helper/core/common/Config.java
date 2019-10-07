package io.github.happytimor.mybatis.helper.core.common;

/**
 * @author chenpeng
 * @date 2019-10-07
 */
public class Config {
    /**
     * 开启驼峰转换
     */
    private boolean mapUnderscoreToCamelCase;

    public boolean isMapUnderscoreToCamelCase() {
        return mapUnderscoreToCamelCase;
    }

    public void setMapUnderscoreToCamelCase(boolean mapUnderscoreToCamelCase) {
        this.mapUnderscoreToCamelCase = mapUnderscoreToCamelCase;
    }
}
