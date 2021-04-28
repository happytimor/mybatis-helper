package io.github.happytimor.mybatis.helper.core.metadata;

/**
 * @author chenpeng
 */
public class JoinInfo {
    private String keyword;
    private Class<?> joinClazz;
    private ColumnFunction<?, ?> leftColumn;
    private ColumnFunction<?, ?> rightColumn;

    public JoinInfo() {

    }

    public JoinInfo(String keyword, Class<?> joinClazz, ColumnFunction<?, ?> leftColumn, ColumnFunction<?, ?> rightColumn) {
        this.keyword = keyword;
        this.joinClazz = joinClazz;
        this.leftColumn = leftColumn;
        this.rightColumn = rightColumn;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Class<?> getJoinClazz() {
        return joinClazz;
    }

    public void setJoinClazz(Class<?> joinClazz) {
        this.joinClazz = joinClazz;
    }

    public ColumnFunction<?, ?> getLeftColumn() {
        return leftColumn;
    }

    public void setLeftColumn(ColumnFunction<?, ?> leftColumn) {
        this.leftColumn = leftColumn;
    }

    public ColumnFunction<?, ?> getRightColumn() {
        return rightColumn;
    }

    public void setRightColumn(ColumnFunction<?, ?> rightColumn) {
        this.rightColumn = rightColumn;
    }
}
