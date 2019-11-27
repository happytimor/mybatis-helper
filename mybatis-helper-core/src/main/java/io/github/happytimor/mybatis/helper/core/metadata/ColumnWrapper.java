package io.github.happytimor.mybatis.helper.core.metadata;

/**
 * @author chenpeng
 */
public class ColumnWrapper {
    private String function;
    private String alias;

    private ColumnWrapper childWrapper;

    public ColumnWrapper(String function) {
        this.function = function;
    }

    public ColumnWrapper(String function, String alias) {
        this.function = function;
        this.alias = alias;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public ColumnWrapper getChildWrapper() {
        return childWrapper;
    }

    public void setChildWrapper(ColumnWrapper childWrapper) {
        this.childWrapper = childWrapper;
    }
}
