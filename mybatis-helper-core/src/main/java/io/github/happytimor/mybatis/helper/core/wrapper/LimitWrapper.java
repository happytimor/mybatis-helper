package io.github.happytimor.mybatis.helper.core.wrapper;


/**
 * @author chenpeng
 */
public class LimitWrapper<T> extends AbstractWrapper<T> {

    /**
     * limit语句
     */
    private String limit = "";

    /**
     * limit
     *
     * @param startRow 开始行
     * @param count    条数
     * @return wrapper
     */
    public final AbstractWrapper<T> limit(int startRow, int count) {
        limit = String.format("LIMIT %s, %s", startRow, count);
        return this;
    }

    /**
     * limit
     *
     * @param executeIf 是否执行
     * @param startRow  开始行
     * @param count     条数
     * @return wrapper
     */
    public final AbstractWrapper<T> limit(boolean executeIf, int startRow, int count) {
        if (executeIf) {
            return this.limit(startRow, count);
        }
        return this;
    }

    /**
     * limit
     *
     * @param count 条数
     * @return wrapper
     */
    public final AbstractWrapper<T> limit(int count) {
        limit = String.format("LIMIT %s", count);
        return this;
    }

    /**
     * limit
     *
     * @param executeIf 是否执行
     * @param count     条数
     * @return wrapper
     */
    public final AbstractWrapper<T> limit(boolean executeIf, int count) {
        if (executeIf) {
            return this.limit(count);
        }
        return this;
    }

    /**
     * 获取limit sql
     *
     * @return limit sql
     */
    public final String getLimitSegment() {
        return limit;
    }

}
