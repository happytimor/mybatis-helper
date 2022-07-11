package io.github.happytimor.mybatis.helper.core.metadata;

/**
 * @author chenpeng
 */
public class Condition {
    /**
     * connector for multiple
     */
    private String connector;
    /**
     * sql segment
     */
    private String segment;

    public Condition(String segment) {
        this.segment = segment;
        this.connector = "AND";
    }

    public Condition(String connector, String segment) {
        this.segment = segment;
        this.connector = connector;
    }

    public String getConnector() {
        return connector;
    }

    public void setConnector(String connector) {
        this.connector = connector;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }
}
