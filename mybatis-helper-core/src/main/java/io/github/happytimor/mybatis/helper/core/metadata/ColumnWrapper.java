package io.github.happytimor.mybatis.helper.core.metadata;

/**
 * @author chenpeng
 */
public class ColumnWrapper {
    private String function;
    private String alias;

    /**
     * extra parameter, eg: DATE_FORMAT(`date`, '%y')
     */
    private String parameter;
    private String[] parameterArray;
    private ColumnWrapper childWrapper;

    public ColumnWrapper(String function) {
        this.function = function;
    }

    public ColumnWrapper(String function, String alias) {
        this.function = function;
        this.alias = alias;
        this.parameter = "";
    }

    public ColumnWrapper(String function, String parameter, String alias) {
        this.function = function;
        this.parameter = parameter;
        this.alias = alias;
    }
    public ColumnWrapper(String function, String[] parameterArray, String alias) {
        this.function = function;
        this.parameterArray = parameterArray;
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

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String[] getParameterArray() {
        return parameterArray;
    }
}
