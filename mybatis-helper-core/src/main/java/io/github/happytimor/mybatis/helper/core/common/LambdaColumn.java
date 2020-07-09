package io.github.happytimor.mybatis.helper.core.common;

/**
 * @author chenpeng
 */
public class LambdaColumn {
    private String name;
    private boolean overrided;

    public LambdaColumn(String name) {
        this.name = name;
    }

    public LambdaColumn(String name, boolean overrided) {
        this.name = name;
        this.overrided = overrided;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOverrided() {
        return overrided;
    }

    public void setOverrided(boolean overrided) {
        this.overrided = overrided;
    }
}
